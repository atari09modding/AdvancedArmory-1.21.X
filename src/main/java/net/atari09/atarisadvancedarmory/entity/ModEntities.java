package net.atari09.atarisadvancedarmory.entity;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.entity.custom.BlockProjectileEntity;
import net.atari09.atarisadvancedarmory.entity.custom.RopeEntity;
import net.atari09.atarisadvancedarmory.entity.custom.projectile.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, AtarisAdvancedArmory.MOD_ID);

    public static final Supplier<EntityType<BlockProjectileEntity>> BLOCK_PROJECTILE_ENTITY = ENTITY_TYPES.register("block_projectile_entity",
            ()->EntityType.Builder.<BlockProjectileEntity>of((e,level)->new BlockProjectileEntity(e,level, Blocks.DIRT.defaultBlockState(),1), MobCategory.MISC).sized(1.0f,1.0f)
                    .build("block_projectile_entity"));

    public static final Supplier<EntityType<ShrapnelArrow>> SHRAPNEL_ARROW = arrow("shrapnel_arrow",ShrapnelArrow::new);

    public static final Supplier<EntityType<ExplosiveArrow>> EXPLOSIVE_ARROW = arrow("explosive_arrow",ExplosiveArrow::new);

    public static final Supplier<EntityType<SmokeArrow>> SMOKE_ARROW = arrow("smoke_arrow", SmokeArrow::new);

    public static final Supplier<EntityType<RopeArrow>> ROPE_ARROW = arrow("rope_arrow", RopeArrow::new);
    public static final Supplier<EntityType<ShulkerArrow>> SHULKER_ARROW = arrow("shulker_arrow", ShulkerArrow::new);

    public static final Supplier<EntityType<ShrapnelSplinterProjectile>> SHRAPNEL_SPLINTER = ENTITY_TYPES.register("shrapnel_splinter",
            ()->EntityType.Builder.<ShrapnelSplinterProjectile>of(ShrapnelSplinterProjectile::new,MobCategory.MISC).sized(0.5f,0.5f)
                    .build("shrapnel_splinter"));

    public static final Supplier<EntityType<RopeEntity>> ROPE = ENTITY_TYPES.register("rope",
            ()->EntityType.Builder.<RopeEntity>of(RopeEntity::new,MobCategory.MISC).sized(0.5f,0.5f)
                    .build("rope"));


    public  static <T extends AbstractArrow>  Supplier<EntityType<T>> arrow(String name, EntityType.EntityFactory<T> s){
        return  ENTITY_TYPES.register(name,()->EntityType.Builder.<T>of(s,MobCategory.MISC).sized(0.5f,0.5f).build(name));
    }

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
