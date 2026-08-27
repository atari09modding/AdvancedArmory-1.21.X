package net.atari09.atarisadvancedarmory.screen;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ItemClientTooltipComponent implements ClientTooltipComponent {

    private final ItemStack stack;
    private final boolean hasBackgroundSlot;

    public ItemClientTooltipComponent(ItemTooltipComponent component) {
        this.stack = component.stack();
        this.hasBackgroundSlot = component.hasBackgroundSlot();
    }


    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public int getWidth(Font font) {
        return 18;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        ClientTooltipComponent.super.renderImage(font, x, y, guiGraphics);
        if(hasBackgroundSlot) guiGraphics.blitSprite(ResourceLocation.withDefaultNamespace("container/bundle/slot"),x, y,18,20);
        guiGraphics.renderItem(stack,x+1,y+1);
        guiGraphics.renderItemDecorations(font,stack,x,y);

    }
}
