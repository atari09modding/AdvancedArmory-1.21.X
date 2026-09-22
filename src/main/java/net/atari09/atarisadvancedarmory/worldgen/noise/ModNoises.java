package net.atari09.atarisadvancedarmory.worldgen.noise;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;



public class ModNoises {
    public static final ResourceKey<NormalNoise.NoiseParameters> ICY_CAVES_WALLS = createKey("icy_caves_walls");
    public static final ResourceKey<NormalNoise.NoiseParameters> ICY_CAVES_DETAILS_BOTTOM = createKey("icy_caves_walls_detail_bottom");
    public static final ResourceKey<NormalNoise.NoiseParameters> ICY_CAVES_DETAILS_TOP = createKey("icy_caves_walls_detail_top");
    public static final ResourceKey<NormalNoise.NoiseParameters> ICY_CAVES_DIRT = createKey("icy_caves_dirt");

    private static ResourceKey<NormalNoise.NoiseParameters> createKey(String key) {
        return ResourceKey.create(Registries.NOISE, AtarisAdvancedArmory.res(key));
    }


}
