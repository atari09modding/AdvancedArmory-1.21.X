package net.atari09.atarisadvancedarmory.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.atari09.atarisadvancedarmory.block.custom.CustomRenderingPortalBlock;
import net.atari09.atarisadvancedarmory.block.entity.CustomRenderingPortalBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class CustomRenderingPortalBlockEntityRenderer implements BlockEntityRenderer<CustomRenderingPortalBlockEntity> {

    public CustomRenderingPortalBlockEntityRenderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public AABB getRenderBoundingBox(CustomRenderingPortalBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(5f);
    }

    @Override
    public void render(CustomRenderingPortalBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        assert blockEntity.getLevel() != null;

        BlockState blockState = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos());
        if (!(blockState.getBlock() instanceof CustomRenderingPortalBlock block)) return;

        Direction direction = blockState.getValue(CustomRenderingPortalBlock.FACING);
        float rot = 0;
        switch (direction){
            case WEST -> rot = 270;
            case SOUTH -> rot = 180;
            case EAST -> rot = 90;
        }
        poseStack.translate(0.5f,0f,0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(rot));
        poseStack.translate(-0.5f,0f,-0.5f);


        block.portalRenderConsumer.run(blockEntity,partialTick,poseStack,bufferSource,packedLight,packedOverlay);

    }
}
