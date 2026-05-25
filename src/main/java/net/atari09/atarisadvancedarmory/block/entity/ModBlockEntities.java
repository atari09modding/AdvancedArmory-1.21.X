package net.atari09.atarisadvancedarmory.block.entity;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AtarisAdvancedArmory.MOD_ID);

    public static final Supplier<BlockEntityType<WeaponSmithBlockEntity>> WEAPONSMITHBLOCK_BE =
            BLOCK_ENTITIES.register("weaponsmithblock_be",() -> BlockEntityType.Builder.of(
                    WeaponSmithBlockEntity::new, ModBlocks.WEAPONSMITHBASEBLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
