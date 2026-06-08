package net.atari09.atarisadvancedarmory.datagen;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.datagen.recipebuilders.WeaponSmithRecipeBuilder;
import net.atari09.atarisadvancedarmory.datagen.recipebuilders.WeaponSmithTemplateTypeRecipeBuilder;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.atari09.atarisadvancedarmory.recipe.WeaponSmithRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

    new WeaponSmithRecipeBuilder(
            new ItemStack(Items.NETHERITE_SWORD),
            Ingredient.of(Items.DIAMOND_SWORD), // 1st Ingredient
            Ingredient.of(Items.NETHERITE_INGOT), // 2nd Ingredient
            Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE) // TEMPLATE
    ).unlockedBy("has_ugrade_smithing_template",has(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)).save(recipeOutput);

    new WeaponSmithTemplateTypeRecipeBuilder(
            new ItemStack(Items.DIAMOND_AXE),
            Ingredient.of(Items.WOODEN_AXE),
            Ingredient.of(Items.DIAMOND),
            SpecialSmithingTemplateType.TEMPORARY_DEBUG2
    ).unlockedBy("has_diamond",has(Items.DIAMOND)).save(recipeOutput);

        new WeaponSmithTemplateTypeRecipeBuilder(
                new ItemStack(ModItems.INFERNAL_MACE.get()),
                Ingredient.of(Items.MACE),
                Ingredient.of(Items.MAGMA_BLOCK),
                SpecialSmithingTemplateType.INFERNAL
        ).unlockedBy("has_mace",has(Items.MACE)).save(recipeOutput);



    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, AtarisAdvancedArmory.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }


}
