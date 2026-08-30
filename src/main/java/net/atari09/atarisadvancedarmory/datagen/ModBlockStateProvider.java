package net.atari09.atarisadvancedarmory.datagen;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;

import net.atari09.atarisadvancedarmory.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.checkerframework.checker.units.qual.A;

import java.util.function.Function;


public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AtarisAdvancedArmory.MOD_ID, exFileHelper);
    }

    private static String name(DeferredBlock<Block> block){
        return block.getRegisteredName().replace(AtarisAdvancedArmory.MOD_ID+":","");
    }


    // vanilla variant of this class is called BlockModelGenerators -> look up what uses what there
    @Override
    protected void registerStatesAndModels() {

        blockWithTopBottomSpecificTextures(ModBlocks.ARCHERSTABLEBLOCK);




    }

    private void blockWithTopBottomSpecificTextures(DeferredBlock<Block> block){
        BlockModelBuilder model = models().cubeBottomTop(
                name(block), blockTexture(block.get()),
                modLoc("block/" + name(block) + "_bottom"),
                modLoc("block/" + name(block) + "_top"));
        simpleBlock(block.get(), model);
        simpleBlockItem(block.get(), model);
    }

    private void saplingBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void leavesBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock){
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("atarisnewmegamodproject:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("atarisnewmegamodproject:block/" + deferredBlock.getId().getPath() + appendix));
    }
}
