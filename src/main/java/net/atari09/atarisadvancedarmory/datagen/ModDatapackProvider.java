package net.atari09.atarisadvancedarmory.datagen;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.worldgen.ModConfiguredFeatures;
import net.atari09.atarisadvancedarmory.worldgen.ModPlacedFeatures;
import net.atari09.atarisadvancedarmory.worldgen.biome.ModBiomes;
import net.atari09.atarisadvancedarmory.worldgen.dimension.ModDimensions;
import net.atari09.atarisadvancedarmory.worldgen.noise.ModNoiseSettings;
import net.atari09.atarisadvancedarmory.worldgen.structure.ModStructureSets;
import net.atari09.atarisadvancedarmory.worldgen.structure.ModStructures;
import net.atari09.atarisadvancedarmory.worldgen.structure.pools.ModPools;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapType)
            .add(Registries.NOISE_SETTINGS, ModNoiseSettings::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(Registries.STRUCTURE, ModStructures::bootstrap)
            .add(Registries.STRUCTURE_SET, ModStructureSets::bootstrap)
            .add(Registries.TEMPLATE_POOL, ModPools::bootstrap)
            .add(Registries.BIOME, ModBiomes::bootstrap)
            .add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem);




    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER,Set.of(AtarisAdvancedArmory.MOD_ID));
    }


}
