package net.atari09.atarisadvancedarmory.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.entity.ArchersTableBlockEntity;
import net.atari09.atarisadvancedarmory.network.payload.StartCraftPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ArchersTableScreen extends AbstractContainerScreen<ArchersTableMenu> {
    public static final ResourceLocation GUI_TEXTURE = AtarisAdvancedArmory.res("textures/gui/archerstable/archerstable_gui.png");

    public ArchersTableScreen(ArchersTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    private static final PotionContents DEFAULTPOTIONEFFECTS = new PotionContents(Optional.of(Potions.AWKWARD),Optional.of(0x000000), List.of());


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


        renderPotionFlow_left(guiGraphics);
        renderPotionFlow_middle(guiGraphics);
        renderPotionFlow_right(guiGraphics);
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

    private void renderPotionFlow_left(GuiGraphics guiGraphics){
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        if(menu.getProgress() > 0 && !menu.blockEntity.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_1).isEmpty()){
            int fh1 = 13;
            int fw = 13;
            int fh2 = 9;
            double s1 = Math.min(menu.getProgress()*3f,1);
            double s2 = Math.min(Math.max(menu.getProgress()*2d-0.33f,0),1);
            double s3 = Math.min(Math.max(menu.getProgress()-0.66f,0),1);



            guiGraphics.fill(x+44,y+23,x+45,y+23+(int)(fh1*s1),
                    Objects.requireNonNull(menu.blockEntity.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_1).getOrDefault(DataComponents.POTION_CONTENTS,DEFAULTPOTIONEFFECTS)).getColor());

            guiGraphics.fill(x+44,y+35,x+44+(int)(fw*s2),y+36,
                    Objects.requireNonNull(menu.blockEntity.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_1).getOrDefault(DataComponents.POTION_CONTENTS,DEFAULTPOTIONEFFECTS)).getColor());

            guiGraphics.fill(x+57,y+35,x+58,y+35+(int)(fh2*s3),
                    Objects.requireNonNull(menu.blockEntity.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_1).getOrDefault(DataComponents.POTION_CONTENTS,DEFAULTPOTIONEFFECTS)).getColor());
        }
    }

    private void renderPotionFlow_middle(GuiGraphics guiGraphics){
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        if(menu.getProgress() > 0 && !menu.blockEntity.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_2).isEmpty()){
            double progress = menu.getProgress();
            int fh = 28;
            guiGraphics.fill(x+85,y+23,x+86,y+23+(int)(fh*progress),
                    Objects.requireNonNull(menu.blockEntity.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_2).getOrDefault(DataComponents.POTION_CONTENTS,DEFAULTPOTIONEFFECTS)).getColor());

        }
    }

    private void renderPotionFlow_right(GuiGraphics guiGraphics){
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        if(menu.getProgress() > 0 && !menu.blockEntity.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_3).isEmpty()){
            int fh1 = 13;
            int fw = 17;
            int fh2 = 17;
            double s1 = Math.min(menu.getProgress()*3d,1);
            double s2 = Math.min(Math.max(menu.getProgress()*2d-0.33f,0),1);
            double s3 = Math.min(Math.max(menu.getProgress()-0.66f,0),1);

            guiGraphics.fill(x+124,y+23,x+125,y+23+(int)(fh1*s1),
                    Objects.requireNonNull(menu.blockEntity.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_3).getOrDefault(DataComponents.POTION_CONTENTS,DEFAULTPOTIONEFFECTS)).getColor());

            guiGraphics.fill(x+124-(int)(fw*s2),y+35,x+124,y+36,
                    Objects.requireNonNull(menu.blockEntity.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_3).getOrDefault(DataComponents.POTION_CONTENTS,DEFAULTPOTIONEFFECTS)).getColor());

            guiGraphics.fill(x+107,y+35,x+108,y+35+(int)(fh2*s3),
                    Objects.requireNonNull(menu.blockEntity.itemHandler.getStackInSlot(ArchersTableBlockEntity.POTION_SLOT_3).getOrDefault(DataComponents.POTION_CONTENTS,DEFAULTPOTIONEFFECTS)).getColor());
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
