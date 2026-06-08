package net.atari09.atarisadvancedarmory.item.util;


import net.minecraft.world.item.ItemStack;

public interface ElementalWeapon  {
    int getElementalLevel(ItemStack stack);
    ElementalVariant getElement();


}
