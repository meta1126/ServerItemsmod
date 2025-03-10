package com.meta.serveritemsmod.gui.client;

import com.meta.serveritemsmod.gui.CustomCorpseMenu;
import com.meta.serveritemsmod.gui.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModMenuScreens {
    public static void registerScreens(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.CUSTOM_CORPSE_MENU.get(), CustomCorpseScreen::new);
        });
    }
}

