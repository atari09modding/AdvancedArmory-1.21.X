package net.atari09.atarisadvancedarmory.item.custom;

import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.component.ContainerItemContent;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ContainerItem extends Item {
    public ContainerItem(int containerSize, Properties properties) {
        super(properties.component(ModDataComponents.CONTENT,new ContainerItemContent(NonNullList.withSize(containerSize, ItemStack.EMPTY))));
    }
}
