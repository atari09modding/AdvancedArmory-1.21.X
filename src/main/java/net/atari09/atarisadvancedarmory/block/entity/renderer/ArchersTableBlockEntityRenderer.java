package net.atari09.atarisadvancedarmory.block.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.ModBlocks;
import net.atari09.atarisadvancedarmory.block.custom.ArchersTableBlock;
import net.atari09.atarisadvancedarmory.block.entity.ArchersTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class ArchersTableBlockEntityRenderer implements BlockEntityRenderer<ArchersTableBlockEntity> {
    private static final PotionContents DEFAULTPOTIONEFFECTS = new PotionContents(Optional.of(Potions.AWKWARD),Optional.of(0xFF0000), List.of());

    public static final ResourceLocation GLASS_TEXTURE = AtarisAdvancedArmory.res("textures/entity/potion_bottle_model.png");
    public static final ResourceLocation LIQUID_TEXTURE = AtarisAdvancedArmory.res("textures/entity/white.png");


    private final BottleModel<Entity> glassModel;
    private final SimpleCubeModel liquidModel;

    public ArchersTableBlockEntityRenderer (BlockEntityRendererProvider.Context context){
        this.glassModel = new BottleModel<>(context.bakeLayer(BottleModel.LAYER_LOCATION));
        this.liquidModel = new SimpleCubeModel(context.bakeLayer(SimpleCubeModel.LAYER_LOCATION));
    }


    @Override
    public void render(ArchersTableBlockEntity be, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        ItemStack stack = be.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_1);
        ItemStack stack2 = be.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_2);
        ItemStack stack3 = be.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_3);


        if(!stack.isEmpty()){
            int c = stack.getOrDefault(DataComponents.POTION_CONTENTS,DEFAULTPOTIONEFFECTS).getColor();
            Vec3 offset = new Vec3(0.8,1.8,0.2);
            renderBottle(be,poseStack,multiBufferSource,c,packedLight,packedOverlay,offset);
        }
        if(!stack2.isEmpty()){
            int c = stack2.getOrDefault(DataComponents.POTION_CONTENTS,DEFAULTPOTIONEFFECTS).getColor();
            Vec3 offset = new Vec3(0.5,1.8,0.2);

            renderBottle(be,poseStack,multiBufferSource,c,packedLight,packedOverlay,offset);
        }
        if(!stack3.isEmpty()){
            int c = stack3.getOrDefault(DataComponents.POTION_CONTENTS,DEFAULTPOTIONEFFECTS).getColor();
            Vec3 offset = new Vec3(0.2,1.8,0.2);

            renderBottle(be,poseStack,multiBufferSource,c,packedLight,packedOverlay,offset);
        }
    }

    private void renderBottle(ArchersTableBlockEntity be, PoseStack poseStack, MultiBufferSource bufferSource, int color, int packedLight, int packedOverlay, Vec3 offset){
        poseStack.pushPose();
        BlockPos pos = be.getBlockPos();
        int light =15728880;
        if (be.getLevel() != null) {
            light = LevelRenderer.getLightColor(be.getLevel(), pos.above());
        }

        BlockState state = be.getLevel().getBlockState(pos);
        if(state.is(ModBlocks.ARCHERSTABLEBLOCK.get())){
            switch (state.getValue(ArchersTableBlock.FACING)){
                case NORTH -> offset = new Vec3(1,offset.y*2,1).subtract(offset);
                case EAST -> offset = new Vec3(offset.z,offset.y,offset.x);
                case WEST -> offset = new Vec3(1,offset.y,1).subtract(offset.z,0,offset.x);
            }
        }



        poseStack.pushPose();
        poseStack.translate(offset.x, offset.y-0.55, offset.z);
        poseStack.scale(0.5f,0.5f,0.5f);

        VertexConsumer liquidVc = bufferSource.getBuffer(RenderType.entityCutout(LIQUID_TEXTURE));
        liquidModel.renderToBuffer(poseStack, liquidVc, light, packedOverlay,
                FastColor.ARGB32.color(255, color));
        poseStack.popPose();

        poseStack.translate(offset.x, offset.y, offset.z);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        VertexConsumer glassVc = bufferSource.getBuffer(RenderType.entityTranslucent(GLASS_TEXTURE));
        glassModel.renderToBuffer(poseStack, glassVc, light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);




        poseStack.popPose();
    }
}
