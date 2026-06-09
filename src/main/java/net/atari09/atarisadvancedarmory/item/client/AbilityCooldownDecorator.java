package net.atari09.atarisadvancedarmory.item.client;

import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.util.ElementalWeapon;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.IItemDecorator;

public class AbilityCooldownDecorator implements IItemDecorator {


    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int x, int y) {
        if(stack.has(ModDataComponents.ABILITY_COOLDOWN) && stack.getItem() instanceof ElementalWeapon item){


            double len = 13;
            double maxCooldown =  item.getElement().abilityCooldown;
            double currCooldown = stack.get(ModDataComponents.ABILITY_COOLDOWN.get());
            if (currCooldown==0)return false;

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0,0,1000);
            guiGraphics.fill(x+2,y+11, x+2+(int) (len*(currCooldown/maxCooldown)),y+12,0xFFd40b0b);
            guiGraphics.pose().popPose();

        }

        return true;
    }
}
