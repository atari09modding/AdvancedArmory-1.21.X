package net.atari09.atarisadvancedarmory.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.atari09.atarisadvancedarmory.entity.custom.BlockProjectileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BlockProjectileEntityRenderer extends EntityRenderer<BlockProjectileEntity> {
    public BlockProjectileEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BlockProjectileEntity entity) {
        return null;
    }

    @Override
    public void render(BlockProjectileEntity p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        poseStack.pushPose();
        poseStack.translate(-0.5, (double) -2 /16,-0.5);

        blockRenderer.renderSingleBlock(p_entity.getState(),poseStack,bufferSource,packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
