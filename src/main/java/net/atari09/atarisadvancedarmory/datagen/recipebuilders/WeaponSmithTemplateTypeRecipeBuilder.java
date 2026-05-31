package net.atari09.atarisadvancedarmory.datagen.recipebuilders;

import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.atari09.atarisadvancedarmory.recipe.WeaponSmithRecipe;
import net.atari09.atarisadvancedarmory.recipe.WeaponSmithTemplateTypeRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class WeaponSmithTemplateTypeRecipeBuilder extends WeaponSmithRecipeBuilder{
    private final SpecialSmithingTemplateType templateType;

    public WeaponSmithTemplateTypeRecipeBuilder(ItemStack result, Ingredient i1, Ingredient i2, SpecialSmithingTemplateType t) {
        super(result, i1, i2, null);
        this.templateType = t;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation key) {
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                .rewards(AdvancementRewards.Builder.recipe(key))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancement::addCriterion);

        WeaponSmithTemplateTypeRecipe recipe = new WeaponSmithTemplateTypeRecipe(this.inputItem,this.inputItem2,this.templateType, this.result);

        output.accept(ResourceLocation.fromNamespaceAndPath(key.getNamespace(), key.getPath()+"_weaponsmith"),recipe,advancement.build(key.withPrefix("recipes/")));
    }
}
