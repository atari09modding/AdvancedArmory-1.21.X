package net.atari09.atarisadvancedarmory.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.atari09.atarisadvancedarmory.entity.custom.RopeEntity;
import net.atari09.atarisadvancedarmory.mixin.EntityRendererInvoker;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class RopeEntityRenderer extends EntityRenderer<RopeEntity> {


    public RopeEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRender(RopeEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void render(RopeEntity rope, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if(this instanceof EntityRendererInvoker invoker){
            Entity entity = rope.getStationaryEntity();
            Entity entity2 = rope.getNonStationaryEntity();
            if(entity == null ||entity2==null) return;
            invoker.invokeRenderLeash(entity,partialTick,poseStack,bufferSource,entity2);
        }
    }

    @Override
    public @Nullable ResourceLocation getTextureLocation(RopeEntity entity) {
        return null;
    }


}
