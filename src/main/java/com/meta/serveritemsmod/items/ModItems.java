package com.meta.serveritemsmod.items;

import com.meta.serveritemsmod.ServerItemsMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ServerItemsMod.MODID);

    public static final RegistryObject<Item> NOMALHELPTICKET = ITEMS.register("nomalhelpticket",
            ()-> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SUPERHELPTICKET = ITEMS.register("superhelpticket",
            ()-> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NOMALGATETICKET = ITEMS.register("nomalgateticket",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAERGATETICKET = ITEMS.register("raergateticket",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SUPERRAERGATETICKET = ITEMS.register("superraergateticket",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ULTRARAERGATETICKET = ITEMS.register("ultraraergateticket",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SOULBALL = ITEMS.register("soulball", () -> new SoulBallItem(new Item.Properties().stacksTo(16)));

    public static void register(IEventBus eventBus) {ITEMS.register(eventBus);}
}
