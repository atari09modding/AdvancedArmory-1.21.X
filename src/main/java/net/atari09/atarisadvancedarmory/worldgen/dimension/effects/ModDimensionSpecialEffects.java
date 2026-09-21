package net.atari09.atarisadvancedarmory.worldgen.dimension.effects;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.IDimensionSpecialEffectsExtension;

public class ModDimensionSpecialEffects{

    public static final ResourceLocation ICY_CAVES_EFFECTS = AtarisAdvancedArmory.res("icy_caves");

    public static class IcyCavesEffects extends DimensionSpecialEffects implements IDimensionSpecialEffectsExtension {

        public IcyCavesEffects() {
            super(Float.NaN, true, SkyType.NORMAL, true, false);
        }

        @Override
        public Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float brightness) {
            return fogColor;
        }

        @Override
        public boolean isFoggyAt(int x, int y) {
            return false;
        }
    }

}


