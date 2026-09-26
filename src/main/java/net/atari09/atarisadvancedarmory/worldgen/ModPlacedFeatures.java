package net.atari09.atarisadvancedarmory.worldgen;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.worldgen.modifiers.IcyCavesHeightmapPlacementModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> FROZEN_TAIGA_TREES_PLACED_KEY = registerKey("frozen_taiga_trees_placed");
    public static final ResourceKey<PlacedFeature> FROZEN_SURFACE_PLACED_KEY = registerKey("frozen_surface_placed");


    public static void bootstrap(BootstrapContext<PlacedFeature> context){
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, FROZEN_TAIGA_TREES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TAIGA_TREES_UNDERGROUND),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), IcyCavesHeightmapPlacementModifier.INSTANCE, BiomeFilter.biome()));

        register(context, FROZEN_SURFACE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.FREEZE_SURFACE_UNDERGROUND),
                List.of(IcyCavesHeightmapPlacementModifier.INSTANCE, BiomeFilter.biome()));

    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, AtarisAdvancedArmory.res(name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
