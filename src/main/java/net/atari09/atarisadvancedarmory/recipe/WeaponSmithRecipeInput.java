package net.atari09.atarisadvancedarmory.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record WeaponSmithRecipeInput(ItemStack input, ItemStack input2, ItemStack template) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return switch (i){
            case 0->input;
            case 1-> input2;
            case 2 -> template;
            default -> input;
        };
    }

    @Override
    public int size() {
        return 1;
    }
}
