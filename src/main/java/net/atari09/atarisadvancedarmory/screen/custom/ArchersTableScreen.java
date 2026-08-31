package net.atari09.atarisadvancedarmory.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ArchersTableScreen extends AbstractContainerScreen<ArchersTableMenu> {
    public static final ResourceLocation GUI_TEXTURE = AtarisAdvancedArmory.res("textures/gui/archerstable/archerstable_gui.png");

    public ArchersTableScreen(ArchersTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);


        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, 165);



    }
}
