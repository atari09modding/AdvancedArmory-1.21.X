package net.atari09.atarisadvancedarmory.item.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

public class SpecialSmithingTemplateItemRenderer extends BlockEntityWithoutLevelRenderer {


    public SpecialSmithingTemplateItemRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemRenderer itemrenderer = Minecraft.getInstance().getItemRenderer();

        itemrenderer.renderStatic(stack,displayContext,packedLight, OverlayTexture.NO_OVERLAY,poseStack,buffer,null,0);

        SpecialSmithingTemplateType type = stack.get(ModDataComponents.SPECIALSMITHINGTEMPLATETYPES);

        if(type!=null&& !type.check(SpecialSmithingTemplateType.NONE)){


            poseStack.pushPose();


            VertexConsumer vc = buffer.getBuffer(RenderType.entityTranslucent(type.getTexture()));

            vc.addVertex(poseStack.last(), 0, 0, 0)
                    .setColor(255,255,255,255)
                    .setUv(0,0);

            vc.addVertex(poseStack.last(), 16, 0, 0)
                    .setColor(255,255,255,255)
                    .setUv(1,0);

            vc.addVertex(poseStack.last(), 16, 16, 0)
                    .setColor(255,255,255,255)
                    .setUv(1,1);

            vc.addVertex(poseStack.last(), 0, 16, 0)
                    .setColor(255,255,255,255)
                    .setUv(0,1);


            poseStack.popPose();
        }
    }
}
