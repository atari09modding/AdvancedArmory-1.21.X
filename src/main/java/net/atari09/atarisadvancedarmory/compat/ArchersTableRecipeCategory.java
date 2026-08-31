package net.atari09.atarisadvancedarmory.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.ModBlocks;
import net.atari09.atarisadvancedarmory.recipe.ArchersTableRecipe;
import net.atari09.atarisadvancedarmory.screen.custom.ArchersTableScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ArchersTableRecipeCategory implements IRecipeCategory<ArchersTableRecipe> {
    public static final ResourceLocation UID = AtarisAdvancedArmory.res("archerstable");
    public static final ResourceLocation TEXTURE = ArchersTableScreen.GUI_TEXTURE;

    public static final RecipeType<ArchersTableRecipe> ARCHERSTABLE_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, ArchersTableRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public ArchersTableRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,176,112);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ARCHERSTABLEBLOCK.get()));
    }

    @Override
    public RecipeType<ArchersTableRecipe> getRecipeType() {
        return ARCHERSTABLE_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.atarisadvancedarmory.weaponsmithbaseblock");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }



    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ArchersTableRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,14,43).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT,32,68).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT,144,43).addIngredients(recipe.getIngredients().get(2));

        builder.addSlot(RecipeIngredientRole.OUTPUT,103,13).addItemStack(recipe.getResultItem(null));
    }

    @SuppressWarnings("removal")
    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }
}
