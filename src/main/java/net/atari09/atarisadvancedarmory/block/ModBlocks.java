package net.atari09.atarisadvancedarmory.block;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.custom.WeaponSmithPieceBlock;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.atari09.atarisadvancedarmory.block.custom.WeaponSmithBaseBlock;

import java.util.Properties;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(AtarisAdvancedArmory.MOD_ID);

    public static final DeferredBlock<Block> WEAPONSMITHBASEBLOCK = BLOCKS.register("weaponsmithbaseblock",
            ()->new WeaponSmithBaseBlock(BlockBehaviour.Properties.of().strength(4f).sound(SoundType.ANVIL).noOcclusion()));

    public static final DeferredBlock<Block> WEAPONSMITHPIECEBLOCK = BLOCKS.register("weaponsmithpieceblock",
            ()->new WeaponSmithPieceBlock(BlockBehaviour.Properties.of().strength(4f).sound(SoundType.ANVIL).noOcclusion().noLootTable()));





    private static <T extends Block> DeferredBlock registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {

        ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
