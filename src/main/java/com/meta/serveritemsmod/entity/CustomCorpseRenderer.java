package com.meta.serveritemsmod.entity;

import com.meta.serveritemsmod.entity.CustomCorpseEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

public class CustomCorpseRenderer extends EntityRenderer<CustomCorpseEntity> {

    private final BlockRenderDispatcher blockRenderer;

    public CustomCorpseRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(CustomCorpseEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // エンティティの位置を調整
        poseStack.translate(-0.5, 0.0, -0.5);

        // ブロックを描画（ここでは `Blocks.CHEST.defaultBlockState()` を指定）
        blockRenderer.renderSingleBlock(Blocks.CHEST.defaultBlockState(), poseStack, buffer, packedLight, 0);

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(CustomCorpseEntity entity) {
        return null; // ブロック描画なのでテクスチャ不要
    }
}
