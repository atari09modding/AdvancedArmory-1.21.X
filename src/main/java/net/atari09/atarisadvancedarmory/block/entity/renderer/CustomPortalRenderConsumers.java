package net.atari09.atarisadvancedarmory.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.atari09.atarisadvancedarmory.block.entity.ArchersTableBlockEntity;
import net.atari09.atarisadvancedarmory.block.entity.CustomRenderingPortalBlockEntity;
import net.atari09.atarisadvancedarmory.client.ModRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;

public class CustomPortalRenderConsumers {

    public static final CustomPortalRenderConsumer DEBUG = (be,v,pose,buffer,light,overlay)->{return;};
    public static final CustomPortalRenderConsumer ICY_CAVES_PORTAL = CustomPortalRenderConsumers::renderIcyCavesPortal;






    private static void renderIcyCavesPortal(CustomRenderingPortalBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay){
        VertexConsumer vc = buffer.getBuffer(ModRenderTypes.ICY_CAVES_PORTAL);
        Matrix4f mat = poseStack.last().pose();


        vc.addVertex(mat,0,1,1).setUv(0,0);
        vc.addVertex(mat,0,3,1).setUv(0,2);
        vc.addVertex(mat,1,3,1).setUv(2,2);
        vc.addVertex(mat,1,1,1).setUv(2,0);




/*
        vc.addVertex(mat, 0, 0, 0).setUv(0, 0);
        vc.addVertex(mat, 0, 1, 0).setUv(0, 1);
        vc.addVertex(mat, 1, 1, 0).setUv(1, 1);
        vc.addVertex(mat, 1, 0, 0).setUv(1, 0);

        vc.addVertex(mat, 0, 1, 1).setUv(1, 1);
        vc.addVertex(mat, 0, 0, 1).setUv(1, 0);
        vc.addVertex(mat, 1, 0, 1).setUv(0, 0);
        vc.addVertex(mat, 1, 1, 1).setUv(0, 1);

        vc.addVertex(mat, 0, 0, 1).setUv(0, 0);
        vc.addVertex(mat, 0, 1, 1).setUv(0, 1);
        vc.addVertex(mat, 0, 1, 0).setUv(1, 1);
        vc.addVertex(mat, 0, 0, 0).setUv(1, 0);

        vc.addVertex(mat, 1, 1, 1).setUv(1, 1);
        vc.addVertex(mat, 1, 0, 1).setUv(1, 0);
        vc.addVertex(mat, 1, 0, 0).setUv(0, 0);
        vc.addVertex(mat, 1, 1, 0).setUv(0, 1);


        vc.addVertex(mat, 0, 0, 0).setUv(0, 0);
        vc.addVertex(mat, 1, 0, 0).setUv(1, 0);
        vc.addVertex(mat, 1, 0, 1).setUv(1, 1);
        vc.addVertex(mat, 0, 0, 1).setUv(0, 1);


        vc.addVertex(mat, 0, 1, 0).setUv(0, 0);
        vc.addVertex(mat, 0, 1, 1).setUv(0, 1);
        vc.addVertex(mat, 1, 1, 1).setUv(1, 1);
        vc.addVertex(mat, 1, 1, 0).setUv(1, 0);*/
    }










    @FunctionalInterface
    public interface CustomPortalRenderConsumer{
        void run(CustomRenderingPortalBlockEntity be, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay);
    }
}
