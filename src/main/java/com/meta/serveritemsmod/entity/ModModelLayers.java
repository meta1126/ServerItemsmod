
package com.meta.serveritemsmod.entity;

import com.meta.serveritemsmod.ServerItemsMod;
import com.meta.serveritemsmod.entity.CustomCorpseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ServerItemsMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModModelLayers {

    public static final ModelLayerLocation CUSTOM_CORPSE_LAYER =
            new ModelLayerLocation(new ResourceLocation(ServerItemsMod.MODID, "custom_corpse"), "main");

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            System.out.println("Registering model layer: " + CUSTOM_CORPSE_LAYER);
            EntityRenderersEvent.RegisterLayerDefinitions layerEvent = new EntityRenderersEvent.RegisterLayerDefinitions();
            layerEvent.registerLayerDefinition(CUSTOM_CORPSE_LAYER, CustomCorpseModel::createBodyLayer);
        });
    }
}
