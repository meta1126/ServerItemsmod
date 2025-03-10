package com.meta.serveritemsmod.gui;

import com.meta.serveritemsmod.ServerItemsMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ServerItemsMod.MODID);

    public static final RegistryObject<MenuType<CustomCorpseMenu>> CUSTOM_CORPSE_MENU =
            MENUS.register("custom_corpse_menu",
                    () -> IForgeMenuType.create((windowId, inv, data) ->
                            new CustomCorpseMenu(windowId, inv, null)));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
