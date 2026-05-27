package net.atari09.atarisadvancedarmory.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WeaponSmithScreen extends AbstractContainerScreen<WeaponSmithMenu> {
    private static final ResourceLocation GUI_TEXTURE = AtarisAdvancedArmory.res("textures/gui/weaponsmith/weapon_smith_gui.png");


    public WeaponSmithScreen(WeaponSmithMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);


        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, 165);

        renderProgressArrow(guiGraphics,x,y);

        renderSmithingButton(guiGraphics,x,y,mouseX,mouseY);


    }

    private void renderSmithingButton(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY) {
        boolean active = menu.hasRecipe();
        boolean hover = mouseX >= x+99 && mouseX <= x+117 && mouseY >= y+34 && mouseY <= y+53;
        if(active){
            if(hover){
                guiGraphics.blit(GUI_TEXTURE,x+99,y+34,0,218,19,20);
            } else{
                guiGraphics.blit(GUI_TEXTURE,x+99,y+34,0,198,19,20);

            }
        } else if (hover) {
            guiGraphics.blit(GUI_TEXTURE,x+99,y+34,0,178,19,20);

        }
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()){
            guiGraphics.blit(GUI_TEXTURE, x+64, y+8, 26, 176, menu.getScaledArrowProgress(), 17); //might have to change the last two to the actual dimensions of the png not sure
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics,mouseX,mouseY);
    }
}
