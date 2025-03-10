package com.meta.serveritemsmod.proxy;

import com.meta.serveritemsmod.ServerItemsMod;
import com.meta.serveritemsmod.entity.CustomCorpseRenderer;
import com.meta.serveritemsmod.ModEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(modid = ServerItemsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientProxy {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.CUSTOM_CORPSE.get(), CustomCorpseRenderer::new);
    }
}
