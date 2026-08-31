package net.atari09.atarisadvancedarmory.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.network.payload.StartCraftPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

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

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, 203);
        renderButton(guiGraphics);
        renderArrow(guiGraphics);

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics,mouseX,mouseY);
    }

    private void renderButton(GuiGraphics guiGraphics){
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        if(menu.hasRecipe()){
            guiGraphics.blit(GUI_TEXTURE,x+77,y+67,0,207,17,17);
        }
    }

    private void renderArrow(GuiGraphics guiGraphics){
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        if(menu.hasArrow()){
            guiGraphics.blit(GUI_TEXTURE,x+48,y+42,0,234,85,21);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if(mouseOnCraftButton(mouseX,mouseY)){
            startCrafting();
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean mouseOnCraftButton(double mouseX, double mouseY){
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        int xmin = x+77;
        int ymin = y+67;
        int xmax = x+94;
        int ymax = y+84;
        return xmin <= mouseX && mouseX <= xmax && ymin <= mouseY && mouseY <= ymax;
    }

    private void startCrafting(){
        PacketDistributor.sendToServer(new StartCraftPacket(menu.getPos()));
    }
}
