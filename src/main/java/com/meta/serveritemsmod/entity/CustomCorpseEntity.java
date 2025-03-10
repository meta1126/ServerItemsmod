package com.meta.serveritemsmod.entity;

import com.meta.serveritemsmod.gui.CustomCorpseMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class CustomCorpseEntity extends Entity implements MenuProvider {


    private static final EntityDataAccessor<Boolean> DATA_CUSTOM_VISIBLE =
            SynchedEntityData.defineId(CustomCorpseEntity.class, EntityDataSerializers.BOOLEAN);


    private static final Logger LOGGER = LogManager.getLogger();

    private final ItemStackHandler inventory = new ItemStackHandler(54); // 54スロットのインベントリ

    public CustomCorpseEntity(EntityType<? extends CustomCorpseEntity> type, Level level) {
        super(type, level);
    }



    public boolean addItemToInventory(ItemStack stack) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack remainder = inventory.insertItem(i, stack, false);
            if (remainder.isEmpty()) {
                return true; // アイテムが正常に追加された
            }
        }
        return false; // 追加できなかった
    }


    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_CUSTOM_VISIBLE, true);
    }

    public boolean isCorpseVisible() {
        return this.entityData.get(DATA_CUSTOM_VISIBLE);
    }

    public void setCorpseVisible(boolean visible) {
        this.entityData.set(DATA_CUSTOM_VISIBLE, visible);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(tag.getCompound("Inventory"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("Inventory", inventory.serializeNBT());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.literal("Corpse");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new CustomCorpseMenu(id, playerInventory, this.inventory);
    }

    public void openInventoryGUI(ServerPlayer player) {
        NetworkHooks.openScreen(player, this, buf -> buf.writeInt(this.getId()));
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (!this.level().isClientSide) {
                this.openInventoryGUI(serverPlayer);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick() {
        super.tick();

    }
    public boolean isPickable() {
        return true; // ヒット判定を有効にする
    }

    @Override
    public boolean isPushable() {
        return false; // 他のエンティティに押されないようにする
    }
    }



