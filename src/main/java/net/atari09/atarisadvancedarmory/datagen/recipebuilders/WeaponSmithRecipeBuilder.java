package net.atari09.atarisadvancedarmory.datagen.recipebuilders;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.recipe.WeaponSmithRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class WeaponSmithRecipeBuilder extends SimpleRecipeBuilder {
    private final Ingredient inputItem;
    private final Ingredient inputItem2;
    private final Ingredient template;

    public WeaponSmithRecipeBuilder(ItemStack result, Ingredient i1, Ingredient i2, Ingredient t) {
        super(result);
        this.inputItem = i1;
        this.inputItem2 = i2;
        this.template = t;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation key) {

        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                .rewards(AdvancementRewards.Builder.recipe(key))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancement::addCriterion);

        WeaponSmithRecipe recipe = new WeaponSmithRecipe(this.inputItem,this.inputItem2,this.template, this.result);

        output.accept(ResourceLocation.fromNamespaceAndPath(key.getNamespace(), key.getPath()+"_weaponsmith"),recipe,advancement.build(key.withPrefix("recipes/")));

    }
}
