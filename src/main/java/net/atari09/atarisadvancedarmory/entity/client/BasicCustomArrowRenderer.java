package net.atari09.atarisadvancedarmory.entity.client;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;

public class BasicCustomArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<T> {
    public BasicCustomArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T t) {
        return TippableArrowRenderer.NORMAL_ARROW_LOCATION;

    }

}
