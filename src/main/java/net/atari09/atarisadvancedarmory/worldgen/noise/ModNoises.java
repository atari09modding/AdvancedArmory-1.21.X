package net.atari09.atarisadvancedarmory.worldgen.noise;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class ModNoises {
    public static final ResourceKey<NormalNoise.NoiseParameters> ICY_CAVES_WALLS = createKey("icy_caves_wally");

    private static ResourceKey<NormalNoise.NoiseParameters> createKey(String key) {
        return ResourceKey.create(Registries.NOISE, AtarisAdvancedArmory.res(key));
    }
}
