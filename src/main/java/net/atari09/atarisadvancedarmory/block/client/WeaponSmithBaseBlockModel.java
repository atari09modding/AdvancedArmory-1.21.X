package net.atari09.atarisadvancedarmory.block.client;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.entity.WeaponSmithBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WeaponSmithBaseBlockModel extends GeoModel<WeaponSmithBlockEntity> {
    @Override
    public ResourceLocation getModelResource(WeaponSmithBlockEntity animatable) {
        return AtarisAdvancedArmory.res("geo/block/weaponsmithbaseblock/weaponsmith.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WeaponSmithBlockEntity animatable) {
        return AtarisAdvancedArmory.res("textures/block/weaponsmith.png");

    }

    @Override
    public ResourceLocation getAnimationResource(WeaponSmithBlockEntity animatable) {
        return AtarisAdvancedArmory.res("animations/block/weaponsmithbaseblock/weaponsmith.animation.json");

    }
}
