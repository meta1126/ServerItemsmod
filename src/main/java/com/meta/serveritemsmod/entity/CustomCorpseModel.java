package com.meta.serveritemsmod.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CustomCorpseModel extends EntityModel<CustomCorpseEntity> {

    private final ModelPart body;

    public CustomCorpseModel(ModelPart root) {
        this.body = root.getChild("body");  // "body" を取得（エラーが起きた部分）
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // "body" を定義
        root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F),  // キューブの定義
                PartPose.offset(0.0F, 12.0F, 0.0F) // モデルのオフセット位置
        );
        System.out.println("CustomCorpseModel: createBodyLayer() called");

        return LayerDefinition.create(mesh, 64, 64); // テクスチャサイズを指定
    }

    @Override
    public void setupAnim(CustomCorpseEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // 何もしない（アニメーションなし）
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(stack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
