package net.atari09.atarisadvancedarmory.entity.client;

import net.atari09.atarisadvancedarmory.entity.ModEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class ModEntityRenderers {

    public static void register(){
        EntityRenderers.register(ModEntities.BLOCK_PROJECTILE_ENTITY.get(), BlockProjectileEntityRenderer::new);
        EntityRenderers.register(ModEntities.SHRAPNEL_ARROW.get(), ShrapnelArrowRenderer::new);
        EntityRenderers.register(ModEntities.EXPLOSIVE_ARROW.get(), ExplosiveArrowRenderer::new);
        EntityRenderers.register(ModEntities.SHRAPNEL_SPLINTER.get(), ShrapnelSplinterProjectileRenderer::new);
        EntityRenderers.register(ModEntities.SMOKE_ARROW.get(), BasicCustomArrowRenderer::new);
        EntityRenderers.register(ModEntities.ROPE_ARROW.get(), BasicCustomArrowRenderer::new);
        EntityRenderers.register(ModEntities.ROPE.get(), RopeEntityRenderer::new);
        EntityRenderers.register(ModEntities.SHULKER_ARROW.get(), ShulkerArrowRenderer::new);
    }
}
