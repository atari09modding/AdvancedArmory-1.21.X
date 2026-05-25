package net.atari09.atarisadvancedarmory.item.client;

import net.atari09.atarisadvancedarmory.item.custom.WeaponSmithBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class WeaponSmithBlockItemRenderer extends GeoItemRenderer<WeaponSmithBlockItem> {
    public WeaponSmithBlockItemRenderer() {
        super(new WeaponSmithItemModel());
    }
}
