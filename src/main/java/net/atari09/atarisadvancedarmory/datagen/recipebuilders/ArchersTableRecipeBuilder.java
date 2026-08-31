package net.atari09.atarisadvancedarmory.datagen.recipebuilders;

import net.atari09.atarisadvancedarmory.recipe.ArchersTableRecipe;
import net.atari09.atarisadvancedarmory.recipe.WeaponSmithRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ArchersTableRecipeBuilder extends SimpleRecipeBuilder {
    protected final Ingredient inputItem;
    protected final Ingredient inputItem2;
    protected final Ingredient inputItem3;

    public ArchersTableRecipeBuilder(ItemStack result, Ingredient i1, Ingredient i2, Ingredient i3) {
        super(result);
        this.inputItem = i1;
        this.inputItem2 = i2;
        this.inputItem3 = i3;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation key) {

        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                .rewards(AdvancementRewards.Builder.recipe(key))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancement::addCriterion);

        ArchersTableRecipe recipe = new ArchersTableRecipe(this.inputItem,this.inputItem2,this.inputItem3, this.result);

        output.accept(ResourceLocation.fromNamespaceAndPath(key.getNamespace(), key.getPath()+"_archerstable"),recipe,advancement.build(key.withPrefix("recipes/")));

    }
}
