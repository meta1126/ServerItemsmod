package com.meta.serveritemsmod.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public class CustomCorpseRenderer extends EntityRenderer<CustomCorpseEntity>{

    private static final ResourceLocation TEXTURE = new ResourceLocation("serveritemsmod", "textures/entity/custom_corpse.png");

    public CustomCorpseRenderer(EntityRendererProvider.Context context) {
        super(context);
}

    @Override
    public ResourceLocation getTextureLocation(CustomCorpseEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(CustomCorpseEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

}
