package net.atari09.atarisadvancedarmory.recipe;

import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record WeaponSmithRecipeTemplateTypeInput(ItemStack input, ItemStack input2, SpecialSmithingTemplateType template) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return switch (i){
            case 1-> input2;
            default -> input;
        };
    }


    @Override
    public int size() {
        return 1;
    }
}
