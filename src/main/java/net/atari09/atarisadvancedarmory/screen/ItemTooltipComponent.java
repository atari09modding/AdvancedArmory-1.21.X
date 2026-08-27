package net.atari09.atarisadvancedarmory.screen;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public record ItemTooltipComponent(ItemStack stack, boolean hasBackgroundSlot) implements TooltipComponent {
}
