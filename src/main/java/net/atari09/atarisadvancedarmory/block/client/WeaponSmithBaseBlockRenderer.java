package net.atari09.atarisadvancedarmory.block.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.atari09.atarisadvancedarmory.block.custom.WeaponSmithBaseBlock;
import net.atari09.atarisadvancedarmory.block.entity.WeaponSmithBlockEntity;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.screen.custom.WeaponSmithMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class WeaponSmithBaseBlockRenderer extends GeoBlockRenderer<WeaponSmithBlockEntity> {
    public WeaponSmithBaseBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new WeaponSmithBaseBlockModel());
    }

    @Override
    public AABB getRenderBoundingBox(WeaponSmithBlockEntity blockEntity) {
        return super.getRenderBoundingBox(blockEntity).inflate(5d);
    }

    @Override
    public void render(WeaponSmithBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        if(!(animatable.getLevel().getBlockState(animatable.getBlockPos()).getBlock() instanceof WeaponSmithBaseBlock)) return;
        renderItems(animatable,partialTick,poseStack,bufferSource,packedLight,packedOverlay);

    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    private void renderItems(WeaponSmithBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay){
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemRenderer itemRenderer2 = Minecraft.getInstance().getItemRenderer();
        ItemRenderer itemRenderertemplate = Minecraft.getInstance().getItemRenderer();
        ItemRenderer itemRendererout = Minecraft.getInstance().getItemRenderer();
        BlockRenderDispatcher fireRenderer = Minecraft.getInstance().getBlockRenderer();

        ItemStack stack = animatable.itemHandler.getStackInSlot(WeaponSmithBlockEntity.INPUT_SLOT_1);
        ItemStack stack2 = animatable.itemHandler.getStackInSlot(WeaponSmithBlockEntity.INPUT_SLOT_2);
        ItemStack stacktemplate = animatable.itemHandler.getStackInSlot(WeaponSmithBlockEntity.TEMPLATE_SLOT);
        ItemStack stackout = animatable.itemHandler.getStackInSlot(WeaponSmithBlockEntity.OUTPUT_SLOT);
        BlockState fire = Blocks.FIRE.defaultBlockState();

        assert animatable.getLevel() != null;
        Direction facing = animatable.getLevel().getBlockState(animatable.getBlockPos()).getValue(HorizontalDirectionalBlock.FACING);

        //ITEMRENDERER 1
        poseStack.pushPose();
        switch (facing){
            case NORTH:
                poseStack.translate(0.5f+0.2f, 13.5/16f, 0.5f);
                poseStack.mulPose(Axis.ZP.rotationDegrees(90));

                break;
            case SOUTH:
                poseStack.translate(0.5f-0.2f, 13.5/16f, 0.5f);
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.mulPose(Axis.ZP.rotationDegrees(90));

                break;
            case EAST:
                poseStack.translate(0.5f, 13.5/16f, 0.5f+0.2f);
                poseStack.mulPose(Axis.YP.rotationDegrees(-90));
                poseStack.mulPose(Axis.ZP.rotationDegrees(90));

                break;
            case WEST:
                poseStack.translate(0.5f, 13.5/16f, 0.5f-0.2f);
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                poseStack.mulPose(Axis.ZP.rotationDegrees(90));

                break;
        }
        poseStack.scale(0.5f, 0.5f, 0.5f);
        itemRenderer.renderStatic(stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, getLightLevel(animatable.getLevel(),
                animatable.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, animatable.getLevel(), 1);
        poseStack.popPose();

        //ITEMRENDERER 2
        poseStack.pushPose();
        switch (facing) {
            case NORTH:
                poseStack.translate(0.5f + 0.2, 14 / 16f, 0.5f - 0.3);

                poseStack.mulPose(Axis.XP.rotationDegrees(90));

                break;
            case SOUTH:
                poseStack.translate(0.5f - 0.2, 14 / 16f, 0.5f + 0.3);

                poseStack.mulPose(Axis.XP.rotationDegrees(-90));
                poseStack.mulPose(Axis.YP.rotationDegrees(180));

                break;
            case EAST:
                poseStack.translate(0.5f + 0.3, 14 / 16f, 0.5f + 0.2);

                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                poseStack.mulPose(Axis.ZP.rotationDegrees(90));

                break;
            case WEST:
                poseStack.translate(0.5f - 0.3, 14 / 16f, 0.5f - 0.2);
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90));

                break;
        }
        poseStack.scale(0.5f, 0.5f, 0.5f);
        itemRenderer2.renderStatic(stack2, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, getLightLevel(animatable.getLevel(),
                animatable.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, animatable.getLevel(), 1);
        poseStack.popPose();

        //ITEMRENDERER TEMPLATE
        poseStack.pushPose();
        poseStack.translate(0.5f, 1.15f, 0.5f);
        switch(facing){
            case NORTH:
                break;
            case SOUTH:
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                break;
            case EAST:
                poseStack.mulPose(Axis.YP.rotationDegrees(-90));
                break;
            case WEST:
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                break;
        }
        poseStack.scale(0.5f, 0.5f, 0.5f);
        if(stackout.isEmpty()){
            itemRenderertemplate.renderStatic(stacktemplate, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, getLightLevel(animatable.getLevel(),
                    animatable.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, animatable.getLevel(), 1);
        }
        poseStack.popPose();

        //ITEMRENDERER OUTPUT

        poseStack.pushPose();
        poseStack.translate(0.5f, 1.15f+animatable.getRenderingExtraHeightForOutput(), 0.5f);
        switch(facing){
            case NORTH:
                poseStack.mulPose(Axis.YP.rotationDegrees(90+animatable.getRenderingRotationForOutput()));
                break;
            case SOUTH:
                poseStack.mulPose(Axis.YP.rotationDegrees(-90+animatable.getRenderingRotationForOutput()));
                break;
            case EAST:
                poseStack.mulPose(Axis.YP.rotationDegrees(animatable.getRenderingRotationForOutput()));
                break;
            case WEST:
                poseStack.mulPose(Axis.YP.rotationDegrees(180+animatable.getRenderingRotationForOutput()));
                break;
        }
        poseStack.scale(0.5f, 0.5f, 0.5f);
        itemRendererout.renderStatic(stackout, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, getLightLevel(animatable.getLevel(),
                animatable.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, animatable.getLevel(), 1);
        poseStack.popPose();


        //Render fire
        poseStack.pushPose();
        switch(facing){
            case NORTH:
                poseStack.translate(-14.5/16f,8/16f,10.5/16f);

                poseStack.mulPose(Axis.YP.rotationDegrees(42.5f));
                break;
            case SOUTH:
                poseStack.translate(1f,0f,1f);
                poseStack.translate(14.5/16f,8/16f,-10.5/16f);


                poseStack.mulPose(Axis.YP.rotationDegrees(-137.5f));
                break;
            case EAST:
                poseStack.translate(1f,0f,0f);
                poseStack.translate(-10.5/16f ,8/16f,-14.5/16f);


                poseStack.mulPose(Axis.YP.rotationDegrees(-47.5f));
                break;
            case WEST:
                poseStack.translate(0f,0f,1f);
                poseStack.translate(10.5/16f ,8/16f,14.5/16f);


                poseStack.mulPose(Axis.YP.rotationDegrees(132.5f));
                break;
        }
        poseStack.scale(0.5f,0.5f,0.5f);
        fireRenderer.renderSingleBlock(fire,poseStack,bufferSource,getLightLevel(animatable.getLevel(),animatable.getBlockPos()),OverlayTexture.NO_OVERLAY);
        poseStack.popPose();


    }
}
