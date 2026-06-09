package net.atari09.atarisadvancedarmory.entity;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.entity.custom.BlockProjectileEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, AtarisAdvancedArmory.MOD_ID);

    public static final Supplier<EntityType<BlockProjectileEntity>> BLOCK_PROJECTILE_ENTITY = ENTITY_TYPES.register("block_projectile_entity",
            ()->EntityType.Builder.<BlockProjectileEntity>of(BlockProjectileEntity::new, MobCategory.MISC).sized(1,1).build("block_projectile_entity"));



    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
