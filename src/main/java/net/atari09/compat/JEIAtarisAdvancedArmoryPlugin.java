package net.atari09.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.recipe.ModRecipes;
import net.atari09.atarisadvancedarmory.recipe.WeaponSmithRecipe;
import net.atari09.atarisadvancedarmory.screen.custom.WeaponSmithScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIAtarisAdvancedArmoryPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return AtarisAdvancedArmory.res("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new WeaponSmithRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<WeaponSmithRecipe> growthChamberRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.WEAPONSMITH_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(WeaponSmithRecipeCategory.WEAPON_SMITH_RECIPE_RECIPE_TYPE, growthChamberRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(WeaponSmithScreen.class, 63, 12, 26, 14,
                WeaponSmithRecipeCategory.WEAPON_SMITH_RECIPE_RECIPE_TYPE); // this controls which area to click to show JEI recipes
    }
}
