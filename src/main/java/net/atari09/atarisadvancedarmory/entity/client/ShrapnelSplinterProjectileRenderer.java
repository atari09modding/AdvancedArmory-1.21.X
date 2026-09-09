package net.atari09.atarisadvancedarmory.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.entity.renderer.SimpleCubeModel;
import net.atari09.atarisadvancedarmory.entity.custom.projectile.ShrapnelSplinterProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ShrapnelSplinterProjectileRenderer extends EntityRenderer<ShrapnelSplinterProjectile> {

    public static final ResourceLocation TEXTURE = AtarisAdvancedArmory.res("textures/entity/white.png");
    private final SimpleCubeModel cubeModel;

    public ShrapnelSplinterProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.cubeModel = new SimpleCubeModel(context.bakeLayer(SimpleCubeModel.LAYER_LOCATION));
    }

    @Override
    public void render(ShrapnelSplinterProjectile p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        VertexConsumer cubeVc = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        cubeModel.renderToBuffer(poseStack,cubeVc,packedLight, OverlayTexture.NO_OVERLAY,0xFF505050);
    }

    @Override
    public ResourceLocation getTextureLocation(ShrapnelSplinterProjectile entity) {
        return null;
    }
}
