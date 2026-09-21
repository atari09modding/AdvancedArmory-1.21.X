package net.atari09.atarisadvancedarmory.worldgen.dimension;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.worldgen.chunkgen.IcyCavesChunkGenerator;
import net.atari09.atarisadvancedarmory.worldgen.dimension.effects.ModDimensionSpecialEffects;
import net.atari09.atarisadvancedarmory.worldgen.noise.ModNoiseSettings;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class ModDimensions {

    public static final ResourceKey<LevelStem> ICY_CAVES_KEY = ResourceKey.create(Registries.LEVEL_STEM, AtarisAdvancedArmory.res("icy_caves"));

    public static final ResourceKey<Level> ICY_CAVES_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,AtarisAdvancedArmory.res("icy_caves"));

    public static final ResourceKey<DimensionType> ICY_CAVES_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,AtarisAdvancedArmory.res("icy_caves"));


    public static void bootstrapType(BootstrapContext<DimensionType> context){
        context.register(ICY_CAVES_TYPE, new DimensionType(
                OptionalLong.empty(), // fixedTime
                false, // hasSkylight
                true, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY -> has to match noisegenerator setting
                256, // height -> has to match noisegenerator setting
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                ModDimensionSpecialEffects.ICY_CAVES_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    //take a look at this https://misode.github.io/dimension/


    public static void bootstrapStem(BootstrapContext<LevelStem> context){
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        IcyCavesChunkGenerator icyCavesChunkGenerator = new IcyCavesChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(Biomes.ICE_SPIKES)),
                noiseGenSettings.getOrThrow(ModNoiseSettings.ICY_CAVES)
        );

        LevelStem stemIcyCaves = new LevelStem(dimTypes.getOrThrow(ICY_CAVES_TYPE),icyCavesChunkGenerator);

        context.register(ICY_CAVES_KEY,stemIcyCaves);


    }


}
