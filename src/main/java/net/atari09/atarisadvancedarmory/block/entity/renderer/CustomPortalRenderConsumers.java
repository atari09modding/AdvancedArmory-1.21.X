package net.atari09.atarisadvancedarmory.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.atari09.atarisadvancedarmory.block.entity.ArchersTableBlockEntity;
import net.atari09.atarisadvancedarmory.block.entity.CustomRenderingPortalBlockEntity;
import net.atari09.atarisadvancedarmory.client.ModRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import org.joml.Matrix4f;

public class CustomPortalRenderConsumers {

    public static final CustomPortalRenderConsumer DEBUG = (be,v,pose,buffer,light,overlay)->{return;};
    public static final CustomPortalRenderConsumer ICY_CAVES_PORTAL = CustomPortalRenderConsumers::renderIcyCavesPortal;






    private static void renderIcyCavesPortal(CustomRenderingPortalBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay){
        VertexConsumer vc = buffer.getBuffer(ModRenderTypes.ICY_CAVES_PORTAL);
        Matrix4f mat = poseStack.last().pose();


        vc.addVertex(mat,0,0,0).setUv(0,0);
        vc.addVertex(mat,0,2,0).setUv(0,2);
        vc.addVertex(mat,2,0,0).setUv(2,0);
        vc.addVertex(mat,2,2,0).setUv(2,2);
    }










    @FunctionalInterface
    public interface CustomPortalRenderConsumer{
        void run(CustomRenderingPortalBlockEntity be, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay);
    }
}
