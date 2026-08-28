package net.atari09.atarisadvancedarmory.screen;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ItemClientTooltipComponent implements ClientTooltipComponent {

    private final NonNullList<ItemStack> stacks;
    private final boolean hasBackgroundSlot;
    private final int count;

    public ItemClientTooltipComponent(ItemTooltipComponent component) {
        this(component.toListComponent());
    }

    public ItemClientTooltipComponent(ItemListTooltipComponent component) {
        this.stacks = component.stacks();
        this.hasBackgroundSlot = component.hasBackgroundSlot();
        this.count = stacks.size();
    }




    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public int getWidth(Font font) {
        return 18*count;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        ClientTooltipComponent.super.renderImage(font, x, y, guiGraphics);
        for(int i = 0; i<count;i++){
            if(hasBackgroundSlot) guiGraphics.blitSprite(ResourceLocation.withDefaultNamespace("container/bundle/slot"),x + 18*i, y,18,20);
            guiGraphics.renderItem(stacks.get(i),x+1 + 18*i,y+1);
            guiGraphics.renderItemDecorations(font, stacks.get(i),x + 18*i,y);
        }
    }
}
