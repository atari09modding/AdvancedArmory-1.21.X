package net.atari09.atarisadvancedarmory.worldgen.dimension.effects;

import com.mojang.blaze3d.vertex.PoseStack;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class ModDimensionSpecialEffects{

    public static final ResourceLocation ICY_CAVES_EFFECTS = AtarisAdvancedArmory.res("icy_caves");

    public static class IcyCavesEffects extends DimensionSpecialEffects {

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

        @Override
        public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f modelViewMatrix, Matrix4f projectionMatrix) {
            return true; // somehow true means that you don't render clouds
        }
    }

}


