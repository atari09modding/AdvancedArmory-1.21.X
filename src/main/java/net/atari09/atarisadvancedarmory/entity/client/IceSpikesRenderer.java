package net.atari09.atarisadvancedarmory.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.entity.custom.projectile.IceSpikesEntity;
import net.minecraft.client.model.EvokerFangsModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EvokerFangsRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.EvokerFangs;

public class IceSpikesRenderer extends EvokerFangsRenderer {
    private static final ResourceLocation TEXTURE_LOCATION = AtarisAdvancedArmory.res("textures/entity/ice_spike.png");
    private final IceSpikeModel<IceSpikesEntity> model;

    public IceSpikesRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new IceSpikeModel<>(context.bakeLayer(IceSpikeModel.LAYER_LOCATION));

    }

    @Override
    public void render(EvokerFangs fangs, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(!(fangs instanceof IceSpikesEntity entity)) return;
        float f = entity.getAnimationProgress(partialTicks);
        if (f != 0.0F) {
            float f1 = 2.0F;
            if (f > 0.9F) {
                f1 *= (1.0F - f) / 0.1F;
            }

            poseStack.pushPose();
            poseStack.mulPose(Axis.XP.rotationDegrees(180));
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - entity.getYRot()));
            poseStack.scale(f1, f1, f1);
            //float f2 = 0.03125F;
            poseStack.translate(0.0, -0.626, 0.0);
            poseStack.scale(1F, 1F, 1F);
            this.model.setupAnim(entity, f, 0.0F, 0.0F, entity.getYRot(), entity.getXRot());
            VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(TEXTURE_LOCATION));
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
            //super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }
    }
}
