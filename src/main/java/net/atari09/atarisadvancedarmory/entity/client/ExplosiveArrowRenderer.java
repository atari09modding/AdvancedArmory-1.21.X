package net.atari09.atarisadvancedarmory.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.entity.renderer.SimpleCubeModel;
import net.atari09.atarisadvancedarmory.entity.custom.projectile.ExplosiveArrow;
import net.atari09.atarisadvancedarmory.entity.custom.projectile.ShrapnelArrow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.data.ModelData;

public class ExplosiveArrowRenderer extends ArrowRenderer<ExplosiveArrow> {

    public static final ResourceLocation NORMAL_ARROW_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/projectiles/arrow.png");
    public static final ResourceLocation TEXTURE = AtarisAdvancedArmory.res("textures/entity/white.png");
    private final SimpleCubeModel cubeModel;


    public ExplosiveArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.cubeModel = new SimpleCubeModel(context.bakeLayer(SimpleCubeModel.LAYER_LOCATION));

    }

    @Override
    public void render(ExplosiveArrow entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        float f9 = (float)entity.shakeTime - partialTicks;
        if (f9 > 0.0F) {
            float f10 = -Mth.sin(f9 * 3.0F) * f9;
            poseStack.mulPose(Axis.ZP.rotationDegrees(f10));
        }

        poseStack.scale(0.3f, 0.3f, 0.3f);
        poseStack.translate(0F, -0.5F, 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(45.0F));

        //poseStack.scale(1.3f, 1.3f, 1.3f);

        BlockRenderDispatcher br = Minecraft.getInstance().getBlockRenderer();
        br.renderSingleBlock(Blocks.TNT.defaultBlockState(),poseStack,buffer,packedLight,OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ExplosiveArrow entity) {
        return NORMAL_ARROW_LOCATION;
    }
}
