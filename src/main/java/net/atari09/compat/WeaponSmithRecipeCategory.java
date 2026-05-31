package net.atari09.compat;

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
import net.atari09.atarisadvancedarmory.recipe.WeaponSmithRecipe;
import net.atari09.atarisadvancedarmory.screen.custom.WeaponSmithScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class WeaponSmithRecipeCategory implements IRecipeCategory<WeaponSmithRecipe> {
    public static final ResourceLocation UID = AtarisAdvancedArmory.res("weaponsmith");
    public static final ResourceLocation TEXTURE = WeaponSmithScreen.GUI_TEXTURE;

    public static final RecipeType<WeaponSmithRecipe> WEAPON_SMITH_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, WeaponSmithRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public WeaponSmithRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,176,55);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.WEAPONSMITHBASEBLOCK.get()));
    }

    @Override
    public RecipeType<WeaponSmithRecipe> getRecipeType() {
        return WEAPON_SMITH_RECIPE_RECIPE_TYPE;
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
    public void setRecipe(IRecipeLayoutBuilder builder, WeaponSmithRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,34,13).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT,34,36).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT,65,29).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.OUTPUT,103,13).addItemStack(recipe.getResultItem(null));
    }

    @SuppressWarnings("removal")
    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }
}
