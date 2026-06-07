package net.atari09.atarisadvancedarmory.item.custom;

import net.atari09.atarisadvancedarmory.item.util.ElementalVariant;
import net.atari09.atarisadvancedarmory.item.util.ElementalWeapon;

public class ElementalMaceItem extends ModMaceItem implements ElementalWeapon {
    public ElementalMaceItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getElementalLevel() {
        return 0;
    }

    @Override
    public ElementalVariant getElement() {
        return null;
    }


}
