package net.atari09.atarisadvancedarmory.screen;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public record ItemListTooltipComponent(NonNullList<ItemStack> stacks, boolean hasBackgroundSlot) implements TooltipComponent {
}
