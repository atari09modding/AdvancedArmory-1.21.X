package net.atari09.atarisadvancedarmory.worldgen.noise;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;

public class ModNoiseSettings {

    public static final ResourceKey<NoiseGeneratorSettings> ICY_CAVES = ResourceKey.create(
            Registries.NOISE_SETTINGS, AtarisAdvancedArmory.res("icy_caves")
    );



    protected static final NoiseSettings ICY_CAVES_NOISE_SETTINGS = NoiseSettings.create(0, 256, 1, 2);


    public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context){
        context.register(ICY_CAVES,icyCaves(context));
    }

    public static NoiseGeneratorSettings icyCaves(BootstrapContext<?> context) {
        return new NoiseGeneratorSettings(
                ICY_CAVES_NOISE_SETTINGS,
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                NoiseGeneratorSettings.overworld(context,false,false).noiseRouter(),
                SurfaceRuleData.overworld(),
                new OverworldBiomeBuilder().spawnTarget(),
                32,
                false,
                true,
                true,
                false
        );
    }
}
