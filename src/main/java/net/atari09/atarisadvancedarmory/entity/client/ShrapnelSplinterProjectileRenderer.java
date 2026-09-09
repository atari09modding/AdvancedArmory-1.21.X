package net.atari09.atarisadvancedarmory.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.entity.renderer.SimpleCubeModel;
import net.atari09.atarisadvancedarmory.entity.custom.projectile.ShrapnelSplinterProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ShrapnelSplinterProjectileRenderer extends ArrowRenderer<ShrapnelSplinterProjectile> {

    public static final ResourceLocation TEXTURE = AtarisAdvancedArmory.res("textures/entity/white.png");
    private final SimpleCubeModel cubeModel;

    public ShrapnelSplinterProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.cubeModel = new SimpleCubeModel(context.bakeLayer(SimpleCubeModel.LAYER_LOCATION));
    }

    @Override
    public void render(ShrapnelSplinterProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        float f9 = (float)entity.shakeTime - partialTick;
        if (f9 > 0.0F) {
            float f10 = -Mth.sin(f9 * 3.0F) * f9;
            poseStack.mulPose(Axis.ZP.rotationDegrees(f10));
        }
        VertexConsumer cubeVc = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        cubeModel.renderToBuffer(poseStack,cubeVc,packedLight, OverlayTexture.NO_OVERLAY,0xFF505050);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ShrapnelSplinterProjectile entity) {
        return TEXTURE;
    }
}
