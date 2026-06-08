package net.atari09.atarisadvancedarmory.item.util;

import net.minecraft.world.item.Item;

public class ElementalProperties extends Item.Properties {
    public ElementalVariant element;
    int level;

    public ElementalProperties setElement(ElementalVariant element){
        this.element = element;
        return this;
    }

    public ElementalProperties setLevel(int level){
        this.level = level;
        return this;
    }


}
