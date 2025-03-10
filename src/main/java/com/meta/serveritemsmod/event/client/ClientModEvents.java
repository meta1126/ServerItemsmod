package com.meta.serveritemsmod.event.client;

import com.meta.serveritemsmod.ModEntities;
import com.meta.serveritemsmod.entity.CustomCorpseModel;
import com.meta.serveritemsmod.entity.CustomCorpseRenderer;
import com.meta.serveritemsmod.entity.ModModelLayers;
import com.meta.serveritemsmod.gui.client.ModMenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModMenuScreens.registerScreens(event);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.CUSTOM_CORPSE.get(), CustomCorpseRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.CUSTOM_CORPSE_LAYER, CustomCorpseModel::createBodyLayer);
    }
}

