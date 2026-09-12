package net.atari09.atarisadvancedarmory.entity;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.entity.custom.BlockProjectileEntity;
import net.atari09.atarisadvancedarmory.entity.custom.projectile.ShrapnelArrow;
import net.atari09.atarisadvancedarmory.entity.custom.projectile.ShrapnelSplinterProjectile;
import net.atari09.atarisadvancedarmory.entity.custom.projectile.SmokeArrow;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, AtarisAdvancedArmory.MOD_ID);

    public static final Supplier<EntityType<BlockProjectileEntity>> BLOCK_PROJECTILE_ENTITY = ENTITY_TYPES.register("block_projectile_entity",
            ()->EntityType.Builder.<BlockProjectileEntity>of((e,level)->new BlockProjectileEntity(e,level, Blocks.DIRT.defaultBlockState(),1), MobCategory.MISC).sized(1.0f,1.0f)
                    .build("block_projectile_entity"));

    public static final Supplier<EntityType<ShrapnelArrow>> SHRAPNEL_ARROW = ENTITY_TYPES.register("shrapnel_arrow",
            ()->EntityType.Builder.<ShrapnelArrow>of(ShrapnelArrow::new,MobCategory.MISC).sized(0.5f,0.5f)
                    .build("shrapnel_arrow"));

    public static final Supplier<EntityType<SmokeArrow>> SMOKE_ARROW = ENTITY_TYPES.register("smoke_arrow",
            ()->EntityType.Builder.<SmokeArrow>of(SmokeArrow::new,MobCategory.MISC).sized(0.5f,0.5f)
                    .build("smoke_arrow"));

    public static final Supplier<EntityType<ShrapnelSplinterProjectile>> SHRAPNEL_SPLINTER = ENTITY_TYPES.register("shrapnel_splinter",
            ()->EntityType.Builder.<ShrapnelSplinterProjectile>of(ShrapnelSplinterProjectile::new,MobCategory.MISC).sized(0.5f,0.5f)
                    .build("shrapnel_arrow"));


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
