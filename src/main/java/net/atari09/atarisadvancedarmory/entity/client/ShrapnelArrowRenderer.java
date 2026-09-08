package net.atari09.atarisadvancedarmory.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.entity.renderer.SimpleCubeModel;
import net.atari09.atarisadvancedarmory.entity.custom.projectile.ShrapnelArrow;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ShrapnelArrowRenderer extends ArrowRenderer<ShrapnelArrow> {

    public static final ResourceLocation NORMAL_ARROW_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/projectiles/arrow.png");
    public static final ResourceLocation TEXTURE = AtarisAdvancedArmory.res("textures/entity/white.png");
    private final SimpleCubeModel cubeModel;


    public ShrapnelArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.cubeModel = new SimpleCubeModel(context.bakeLayer(SimpleCubeModel.LAYER_LOCATION));

    }

    @Override
    public void render(ShrapnelArrow entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        float f9 = (float)entity.shakeTime - partialTicks;
        if (f9 > 0.0F) {
            float f10 = -Mth.sin(f9 * 3.0F) * f9;
            poseStack.mulPose(Axis.ZP.rotationDegrees(f10));
        }

        //poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
        poseStack.scale(0.5f, 0.6f, 0.5f);
        poseStack.translate(0.25F, 0.275F, 0.0F);
        poseStack.scale(1.3f, 1.3f, 1.3f);


        VertexConsumer cubeVc = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        cubeModel.renderToBuffer(poseStack,cubeVc,packedLight,OverlayTexture.NO_OVERLAY,0xFF505050);

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ShrapnelArrow entity) {
        return NORMAL_ARROW_LOCATION;
    }
}
