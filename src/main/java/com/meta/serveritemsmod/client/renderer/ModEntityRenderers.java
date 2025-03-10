package com.meta.serveritemsmod.client.renderer;

import com.meta.serveritemsmod.ModEntities;
import com.meta.serveritemsmod.entity.CustomCorpseEntity;
import com.meta.serveritemsmod.entity.CustomCorpseRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityRenderers {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.CUSTOM_CORPSE.get(), CustomCorpseRenderer::new);
    }
}
