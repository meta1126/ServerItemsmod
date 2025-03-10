package com.meta.serveritemsmod;

import com.meta.serveritemsmod.entity.CustomCorpseEntity;
import com.meta.serveritemsmod.event.common.effects.FracturedSoulEffect;
import com.meta.serveritemsmod.event.common.effects.ModEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import com.meta.serveritemsmod.ModEntities;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.ArrayList;
import java.util.List;


@Mod.EventBusSubscriber(modid = ServerItemsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DeathPenaltySystem {

    private static final int MAX_FRACTURED_SOUL_LEVEL = 5;
    private static final MobEffect FRACTURED_SOUL = new FracturedSoulEffect();


    private static final List<Class<? extends Item>> KEEP_ITEM_CLASSES = List.of(
            ArmorItem.class, SwordItem.class, AxeItem.class, PickaxeItem.class, ShovelItem.class, HoeItem.class
    );

    private static void applyFracturedSoul(Player player) {
        CompoundTag persistentData = player.getPersistentData();
        int currentLevel = persistentData.getInt("fractured_soul_level");
        int newLevel = Math.min(currentLevel + 1, 5);

        persistentData.putInt("fractured_soul_level", newLevel);
        int duration = 12000;
        MobEffectInstance currentEffect = player.getEffect(ModEffects.FRACTURED_SOUL.get());
        player.addEffect(new MobEffectInstance(ModEffects.FRACTURED_SOUL.get(), duration, newLevel, false, true));
        LOGGER.info("Applied Fractured_Soul effect to player {} at level{}", player.getName().getString(), newLevel);
    }


    private static final TagKey<Item> DROP_ITEMS_TAG = TagKey.create(Registries.ITEM, new ResourceLocation("serveritemsmod", "drop_items"));

    private static boolean shouldDrop(ItemStack stack, Player player) {
        boolean isDrop = stack.is(DROP_ITEMS_TAG);
        MobEffectInstance fracturedSoul =
                player.getEffect(ModEffects.FRACTURED_SOUL.get());
        if (fracturedSoul != null) {
            int level = fracturedSoul.getAmplifier() + 1;

            if (level >= 3) {
                if (stack.getItem() instanceof TieredItem ||
                        stack.getItem() instanceof EnchantedBookItem) {
                    isDrop = isDrop || (Math.random() < level * 0.4);
                }
            }
            isDrop = isDrop || (Math.random() < level * 0.1);
        }
        LOGGER.info("Checking if item should drop: {}, result: {}",
                ForgeRegistries.ITEMS.getKey(stack.getItem()), isDrop);
        return isDrop;
    }


    private static boolean shouldKeepItem(ItemStack itemStack, Player player) {
        Item item = itemStack.getItem();
        boolean isKeepItemClass = KEEP_ITEM_CLASSES.stream().anyMatch(cls -> cls.isInstance(item));
        boolean isNotDropItem = !shouldDrop(itemStack, player);
        LOGGER.info("Checking item: {}, isKeepItemClass: {}, isNotDropItem: {}",
                ForgeRegistries.ITEMS.getKey(item), isKeepItemClass, isNotDropItem);
        return isKeepItemClass || isNotDropItem;
    }

    private static int calculateExperienceDrop(Player player) {
        MobEffectInstance fracturedSoul = player.getEffect(ModEffects.FRACTURED_SOUL.get());
        if (fracturedSoul != null && fracturedSoul.getAmplifier() + 1 >= 3) {
            // レベル3以上で経験値をドロップ
            return (int) (player.experienceLevel * 0.3 * (fracturedSoul.getAmplifier() + 1));
        }
        return 0;
    }

    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.Clone event) {
        if (event.getEntity().level() == null) return;
        Level level = event.getEntity().level();
        if (level.isClientSide()) return;

        Vec3 pos = event.getOriginal().position();
        if (pos == null) pos = event.getEntity().position();
        LOGGER.info("Player Clone event triggered");
        if (event.isWasDeath() && !event.getEntity().level().isClientSide()) {
            Player originalPlayer = event.getOriginal();
            Player newPlayer = event.getEntity();
            double x = originalPlayer.getX();
            double y = originalPlayer.getY();
            double z = originalPlayer.getZ();

            ServerLevel serverLevel = (ServerLevel) level;

            CustomCorpseEntity corpse =  ModEntities.CUSTOM_CORPSE.get().create(serverLevel);
            if (corpse != null) {
                corpse.setPos(x, y, z);
                boolean success = level.addFreshEntity(corpse);

                if (success) {
                    LOGGER.info("✅ CustomCorpseEntity successfully spawned at ({}, {}, {})", x, y, z);
                } else {
                    LOGGER.error("❌ Failed to spawn CustomCorpseEntity at ({}, {}, {})", x, y, z);
                }

                List<ItemStack> itemstoKeep = new ArrayList<>();

                for (int i = 0; i < originalPlayer.getInventory().getContainerSize(); i++) {
                    ItemStack itemStack = originalPlayer.getInventory().getItem(i);
                    if (!itemStack.isEmpty()) {
                        if (shouldDrop(itemStack, originalPlayer)) {
                            boolean added = corpse.addItemToInventory(itemStack.copy());
                            if (added) {
                                originalPlayer.getInventory().setItem(i, ItemStack.EMPTY);
                            }
                        } else {
                            itemstoKeep.add(itemStack.copy());
                        }

                    }
                }
                newPlayer.getInventory().items.clear();
                for (ItemStack itemtoKeep : itemstoKeep) {
                    newPlayer.getInventory().add(itemtoKeep);
                }
                LOGGER.info("Player's items successfully moved to corpse inventory.");
                processCurios(originalPlayer, newPlayer, corpse);

                int expToDrop = calculateExperienceDrop(originalPlayer);
                if (expToDrop > 0) {
                    ExperienceOrb.award((ServerLevel) level, originalPlayer.position(), expToDrop);
                    newPlayer.giveExperiencePoints(-expToDrop);
                }
            } else {
                LOGGER.error("❌ `CustomCorpseEntity` instance is null! Check ModEntities registration.");
            }
        }else{
            LOGGER.info("Player clone event triggered, but not bue to death or on client side.");
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            Player original = event.getOriginal();
            Player player = event.getEntity();
            CompoundTag originalData = original.getPersistentData();
            CompoundTag newData = player.getPersistentData();

            if (originalData.contains("fractured_soul_level")) {
                int level = originalData.getInt("fractured_soul_level");
                newData.putInt("fractured_soul_level", level);
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) {
            applyFracturedSoul(player);
        }
    }


    // Process curios slots
    private static void processCurios(Player originalPlayer, Player newPlayer, CustomCorpseEntity corpse) {
        CuriosApi.getCuriosHelper().getCuriosHandler(originalPlayer).ifPresent(originalHandler -> {
            CuriosApi.getCuriosHelper().getCuriosHandler(newPlayer).ifPresent(newHandler -> {
                    originalHandler.getCurios().forEach((id,OriginalStacks) -> {
                        ICurioStacksHandler newStacks = newHandler.getCurios().get(id);
                        if (newStacks != null){
                                for (int i = 0; i < OriginalStacks.getSlots(); i++) {
                                    ItemStack stack = OriginalStacks.getStacks().getStackInSlot(i);
                                    if (!stack.isEmpty()) {
                                        if (shouldDrop(stack, originalPlayer)) {
                                            corpse.addItemToInventory(stack.copy());
                                            OriginalStacks.getStacks().setStackInSlot(i, ItemStack.EMPTY);
                                            LOGGER.info("Dropped curio: {}", ForgeRegistries.ITEMS.getKey(stack.getItem()));
                                        } else {
                                            newStacks.getStacks().setStackInSlot(i, stack.copy());
                                            LOGGER.info("Kept curio: {}", ForgeRegistries.ITEMS.getKey(stack.getItem()));
                                        }
                                    }
                                }
                            }
                        });
                    });
              });
        LOGGER.info("Inventory processing complete.");
    }

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            MobEffect expiredEffect = event.getEffectInstance().getEffect();
            if (expiredEffect == ModEffects.FRACTURED_SOUL.get()) {
                // エフェクトが消えたらレベルをリセット
                CompoundTag persistentData = player.getPersistentData();
                persistentData.remove("fractured_soul_level");
                LOGGER.info("Fractured Soul effect expired for player {}. Level reset.", player.getName().getString());
            }
        }
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            MobEffect removedEffect = event.getEffect();
            if (removedEffect == ModEffects.FRACTURED_SOUL.get()) {
                CompoundTag persistentData = player.getPersistentData();
                persistentData.remove("fractured_soul_level");
                LOGGER.info("Fractured Soul effect forcibly removed from player {}. Level reset.", player.getName().getString());
            }
        }
    }

    @SubscribeEvent
    public static void onEffectRemoval(MobEffectEvent.Remove event) {
        if (event.getEffect() instanceof FracturedSoulEffect && event.getEntity() instanceof Player)  {
            LivingEntity entity = event.getEntity();
            Player player = (Player) event.getEntity();
            ItemStack usedItem = player.getMainHandItem();/* アイテムの使用を検出するロジック */;

            if (!((FracturedSoulEffect) event.getEffect()).canBeRemovedBy(entity, usedItem)) {
                event.setCanceled(true); // エフェクトの除去をキャンセル
            }
        }
    }


    private static ItemStack findBackpack(Player player) {
        // Implement logic to find the backpack in the player's inventory
        // This is a placeholder and needs to be implemented based on your backpack mod
        return ItemStack.EMPTY;
    }

    //@SubscribeEvent
    //public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
     //   Player player = event.getEntity();
   //     if (player != null && !player.level().isClientSide()) {
     //       List<ItemStack> itemsToKeep = new ArrayList<>();
       //     for (ItemStack itemStack : player.getInventory().items) {
         //       if (shouldKeepItem(itemStack)) {
           //         itemsToKeep.add(itemStack.copy());
             //   }
            //}
    //player.getInventory().items.clear();
      //      player.getInventory().items.addAll(itemsToKeep);

            // Remove items from curios slots that should not be kept
          //  CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
            //    for (ICurioStacksHandler stacks : handler.getCurios().values()) {
              //      for (int i = 0; i < stacks.getSlots(); i++) {
                //        ItemStack stack = stacks.getStacks().getStackInSlot(i);
                  //      if (!stack.isEmpty() && !shouldKeepItem(stack)) {
                    //        stacks.getStacks().setStackInSlot(i, ItemStack.EMPTY);
                      //  }
                    //}

    private static final Logger LOGGER = LogManager.getLogger(DeathPenaltySystem.class);

}
// Process backpack (assuming a specific backpack item)
           // });
        //}

    //}
//}