package net.atari09.atarisadvancedarmory.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityRenderer.class)
public interface LivingEntityRendererInvoker {


    @Invoker("renderLeash")
    void invokeRenderLeash(Entity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, Entity leashHolder);
}
