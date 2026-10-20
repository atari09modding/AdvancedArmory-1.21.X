package net.atari09.atarisadvancedarmory.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.atari09.atarisadvancedarmory.block.entity.ArchersTableBlockEntity;
import net.atari09.atarisadvancedarmory.block.entity.CustomRenderingPortalBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;

public class CustomPortalRenderConsumers {

    public static final CustomPortalRenderConsumer DEBUG = (be,v,pose,buffer,light,overlay)->{return;};

    @FunctionalInterface
    public interface CustomPortalRenderConsumer{
        void run(CustomRenderingPortalBlockEntity be, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay);
    }
}
