package net.atari09.atarisadvancedarmory.item.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class ModCuriousRenderer implements ICurioRenderer{
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack, SlotContext slotContext, PoseStack poseStack,
            RenderLayerParent<T, M> renderLayerParent, MultiBufferSource bufferSource,
            int light, float limbSwing, float limbSwingAmount, float partialTicks,
            float ageInTicks, float netHeadYaw, float headPitch) {

        if (stack.is(ModItems.SCABBARD)) {
            LivingEntity livingEntity = slotContext.entity();
            slotContext.identifier() // check if belt or back

            renderScabbard(livingEntity,)
        }

    }
}
