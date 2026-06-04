package net.atari09.atarisadvancedarmory.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.atari09.atarisadvancedarmory.network.payload.CraftTemplatePacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;


public class SpecialSmithingTemplateScreen extends AbstractContainerScreen<SpecialSmithingTemplateMenu> {
    public static final ResourceLocation GUI_TEXTURE = AtarisAdvancedArmory.res("textures/gui/specialsmithingtemplate/specialsmithingtemplate_gui.png");
    private float scroll = 0f;
    private Component hoveredTooltip = null;
    private boolean isDraggingScroller = false;
    private SpecialSmithingTemplateType selected = null;
    private boolean isMouseClicked = false;
    private Button craftButton;

    public SpecialSmithingTemplateScreen(SpecialSmithingTemplateMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, Component.empty());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);


        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        renderTemplates(guiGraphics,mouseX,mouseY,x,y);
        renderScroller(guiGraphics,x,y);
        if(selected!=null){
            renderLeftPart(guiGraphics,mouseX,mouseY,x,y);
        }



        //tooltips down here
        if (hoveredTooltip != null){
            guiGraphics.renderComponentTooltip(this.font, List.of(hoveredTooltip),mouseX,mouseY);
        }

    }

    private void renderScroller(GuiGraphics guiGraphics, int x, int y) {
        int h = 62-6;
        int ydisplacement = (int)(h*scroll);
        guiGraphics.blit(GUI_TEXTURE,x+166,y+6+ydisplacement,0,0,196,5,6,256,256);

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        double scrollDelta = scrollY * 0.05;
        this.scroll = ((float) Math.clamp(this.scroll - scrollDelta, 0, 1));
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        int h = 62-7;
        int ydisplacement = (int)(h*scroll);
        boolean hover = mouseX >= x+ 166&&mouseX<=x+166+5 && mouseY >= y+6+ydisplacement && mouseY<= y+6+ydisplacement+6;
        double addscroll = ydisplacement+  dragY;
        if(hover || isDraggingScroller){
            this.scroll = ((float) Math.clamp(this.scroll +dragY/h, 0, 1));
            isDraggingScroller = true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isMouseClicked = false;
        isDraggingScroller = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        isMouseClicked = true;

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void init() {
        super.init();

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        craftButton = addRenderableWidget(Button.builder(Component.literal("CRAFT"),
                button->{
                    if(selected!=null){
                        if(this.menu.container.getItem(0).getItem().equals(selected.cost.getItem()) && this.menu.container.getItem(0).getCount() >= selected.cost.getCount()){
                            PacketDistributor.sendToServer(new CraftTemplatePacket(selected));
                            this.menu.container.getItem(0).shrink(selected.cost.getCount());
                        }

                    }
                }
        ).bounds(x+5,y+25+this.font.lineHeight*3,40,this.font.lineHeight+8).build());
        craftButton.visible = false;

    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {}//keep empty if not needed

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics,mouseX,mouseY);
    }

    private void renderTemplates(GuiGraphics guiGraphics,int mouseX,int mouseY,int x, int y){
        hoveredTooltip = null;
        int rowLength = 4;
        int spacing = 2;
        int spacingSides = 5;
        int windowHeight = 64;
        int rowCount = ((int) Math.ceil(((double) SpecialSmithingTemplateType.values().length-1) / rowLength));
        int height = rowCount*(18+spacing);
        float verticalDisplacement = -(height-windowHeight )*((float) this.scroll);
        int slotsDrawn = 1;
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(73,verticalDisplacement,0);
        for(int yrow = 0; yrow<rowCount; yrow++){
            for (int xcol = 0; xcol < 4; xcol++){
                int coordsX = x+ xcol * ( 18 + spacing ) + spacingSides;
                int coordsY =y+ yrow * ( 18 + spacing ) + spacing;
                if(slotsDrawn<SpecialSmithingTemplateType.values().length-1){
                    guiGraphics.enableScissor(x+71+spacingSides,51,x+500,y+5+64);
                    boolean hover = mouseX >=coordsX+73 && mouseX <= coordsX+18+73 && mouseY>=coordsY+verticalDisplacement &&mouseY<=coordsY+18+verticalDisplacement;
                    boolean showTooltip = mouseX >= x+71+spacingSides && mouseY >= 51 && mouseX <= x+500 && mouseY <=y+5+64;
                    guiGraphics.blit(GUI_TEXTURE,coordsX,coordsY,0, 178,18,18);
                    ItemStack stack = new ItemStack(ModItems.SPECIAL_SMITHING_TEMPLATE.get(),1);
                    stack.set(ModDataComponents.SPECIALSMITHINGTEMPLATETYPES,SpecialSmithingTemplateType.values()[slotsDrawn]);
                    guiGraphics.renderItem(stack,coordsX,coordsY+1);
                    guiGraphics.renderItemDecorations(this.font,stack,coordsX,coordsY+1);
                    guiGraphics.disableScissor();
                    if(hover&&showTooltip){
                        hoveredTooltip = Component.literal("Type: ").append(Component.translatable("atarisadvancedarmory.specialsmithingtemplatetype." + SpecialSmithingTemplateType.values()[slotsDrawn].name).getString());
                        if(isMouseClicked) selected = SpecialSmithingTemplateType.values()[slotsDrawn];
                    }

                    slotsDrawn++;
                }
            }
        }


        pose.popPose();
    }

    private void renderLeftPart(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y){
        x += 5;
        y+= 5;
        guiGraphics.drawString(this.font,Component.translatable("atarisadvancedarmory.specialsmithingtemplatetype."+selected.name).getString(),x,y,0xFFFFFF);//mb make different color for different "rarenesses" here
        y+=this.font.lineHeight+5;

        guiGraphics.drawString(this.font,"Cost:",x,y,0xFFFFFF);
        y+=this.font.lineHeight+5;

        ItemStack cost = selected.cost;
        guiGraphics.renderItem(cost,x,y);
        guiGraphics.renderItemDecorations(this.font,cost,x,y);
        guiGraphics.blit(GUI_TEXTURE,x+30,y,0, 178,18,18);

        craftButton.visible = this.menu.container.getItem(0).is(selected.cost.getItem()) && this.menu.container.getItem(0).getCount() >= selected.cost.getCount();




    }


}
