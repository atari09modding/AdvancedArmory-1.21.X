package net.atari09.atarisadvancedarmory.item.component;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface ContainerAccessor {

    NonNullList<ItemStack> getContent();
}
