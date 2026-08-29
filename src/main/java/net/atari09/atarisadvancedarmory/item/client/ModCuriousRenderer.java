package net.atari09.atarisadvancedarmory.item.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.Map;
import java.util.function.Consumer;

public class ModCuriousRenderer implements ICurioRenderer{
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack, SlotContext slotContext, PoseStack poseStack,
            RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer,
            int light, float limbSwing, float limbSwingAmount, float partialTicks,
            float ageInTicks, float netHeadYaw, float headPitch) {


        LivingEntity livingEntity = slotContext.entity();


        if (stack.is(ModItems.SCABBARD)) {
            String slot = slotContext.identifier(); // check if belt or back
            renderScabbard(stack,livingEntity,slot,poseStack,renderLayerParent, buffer,light);
        }
        if(stack.is(ModItems.QUIVER)){
            renderQuiver(stack,livingEntity,poseStack,renderLayerParent, buffer,light);
        }

    }

    private <M extends EntityModel<T>, T extends LivingEntity> void renderScabbard(ItemStack stack,
            LivingEntity livingEntity, String slot,
            PoseStack poseStack, RenderLayerParent<T,M> renderLayerParent,
            MultiBufferSource buffer, int light) {

        M model = renderLayerParent.getModel();
        if(model instanceof HumanoidModel<?> humanoidModel){
            poseStack.pushPose();

            humanoidModel.body.translateAndRotate(poseStack);

            if(slot.equals("back")){
                poseStack.translate(0,0.5,0.15);
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.mulPose(Axis.ZP.rotationDegrees(180));

            } else if (slot.equals("belt")) {
                poseStack.translate(0.25,0.9,0);
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                poseStack.mulPose(Axis.ZP.rotationDegrees(180));

            }


            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderStatic(livingEntity,stack, ItemDisplayContext.FIXED,false,
                    poseStack,buffer,livingEntity.level(),light, OverlayTexture.NO_OVERLAY,1);

            if(stack.has(ModDataComponents.CONTENT)){
                if(!stack.get(ModDataComponents.CONTENT).getContent().isEmpty()){
                    ItemStack content = stack.get(ModDataComponents.CONTENT).getContent().get(0);
                    if(!content.isEmpty()){
                        if(slot.equals("back")){
                            poseStack.translate(-0.1,0.1,-0.05);
                            poseStack.mulPose(Axis.ZP.rotationDegrees(180));
                        } else if (slot.equals("belt")) {
                            poseStack.translate(-0.1,0.1,0.05);
                            poseStack.mulPose(Axis.ZP.rotationDegrees(180));
                        }

                        itemRenderer.renderStatic(livingEntity,content, ItemDisplayContext.FIXED,false,
                                poseStack,buffer,livingEntity.level(),light, OverlayTexture.NO_OVERLAY,1);
                    }
                }
            }

            poseStack.popPose();
        }
    }


    private <M extends EntityModel<T>, T extends LivingEntity> void renderQuiver(ItemStack stack,
                                                                                   LivingEntity livingEntity,
                                                                                   PoseStack poseStack, RenderLayerParent<T,M> renderLayerParent,
                                                                                   MultiBufferSource buffer, int light){

        M model = renderLayerParent.getModel();
        if(model instanceof HumanoidModel<?> humanoidModel){
            poseStack.pushPose();

            humanoidModel.body.translateAndRotate(poseStack);

            poseStack.translate(0,0.5,0.15);
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));


            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderStatic(livingEntity,stack, ItemDisplayContext.FIXED,false,
                    poseStack,buffer,livingEntity.level(),light, OverlayTexture.NO_OVERLAY,1);

            if(stack.has(ModDataComponents.CONTENT)){
                if(!stack.get(ModDataComponents.CONTENT).getContent().isEmpty()){
                    NonNullList<ItemStack> contents = stack.get(ModDataComponents.CONTENT).getContent();

                    Map<Integer, Consumer<PoseStack>> arrow_translations = Map.of(
                            0,p ->{poseStack.translate(0.1,0,0);},
                            1,p ->{poseStack.translate(0.1,-0.1,0);},
                            2,p ->{poseStack.translate(0.1,0,0.1);},
                            3,p ->{poseStack.translate(0.1,0.1,0);},
                            4,p ->{poseStack.translate(0.1,0,-0.1);}
                    );
                    int i = 0;
                    for(ItemStack content:contents){
                        arrow_translations.get(i).accept(poseStack);
                        itemRenderer.renderStatic(livingEntity,content, ItemDisplayContext.FIXED,false,
                                poseStack,buffer,livingEntity.level(),light, OverlayTexture.NO_OVERLAY,1);
                        i++;
                    }
                }
            }

            poseStack.popPose();
        }



    }


}




/*
        LivingEntity livingEntity = slotContext.entity();

        M model = renderLayerParent.getModel();

        if(model instanceof HumanoidModel<?> humanoidModel){

            poseStack.pushPose();
            humanoidModel.rightArm.translateAndRotate(poseStack);
            poseStack.translate(0,0.5,-0.13);
            poseStack.mulPose(Axis.XP.rotationDegrees(180));
            poseStack.scale(0.5F,0.5F,0.5F);

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderStatic(livingEntity,stack,ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,false,
                    poseStack,renderTypeBuffer,livingEntity.level(),light,OverlayTexture.NO_OVERLAY,1);
            poseStack.popPose();
        }
 */