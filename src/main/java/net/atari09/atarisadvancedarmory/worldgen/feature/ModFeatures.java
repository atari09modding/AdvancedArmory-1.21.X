package net.atari09.atarisadvancedarmory.worldgen.feature;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE,AtarisAdvancedArmory.MOD_ID);

    public static final Supplier<Feature<NoneFeatureConfiguration>> FREEZE_SURFACE_UNDERGROUND = FEATURES.register("freeze_surface_underground",
            () -> new UnderGroundSnowAndFreezeFeature(NoneFeatureConfiguration.CODEC));




    public static void register(IEventBus eventBus){
        FEATURES.register(eventBus);
    }



}
