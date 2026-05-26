package net.atari09.atarisadvancedarmory.item.client;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.entity.WeaponSmithBlockEntity;
import net.atari09.atarisadvancedarmory.item.custom.WeaponSmithBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class WeaponSmithItemModel extends GeoModel<WeaponSmithBlockItem> {
    @Override
    public ResourceLocation getModelResource(WeaponSmithBlockItem animatable) {
        return AtarisAdvancedArmory.res("geo/block/weaponsmithbaseblock/weaponsmith.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(WeaponSmithBlockItem animatable) {
        return AtarisAdvancedArmory.res("textures/block/weaponsmith.png");

    }

    @Override
    public ResourceLocation getAnimationResource(WeaponSmithBlockItem animatable) {
        return AtarisAdvancedArmory.res("animations/block/weaponsmithbaseblock/weaponsmith.animation.json");

    }
}
