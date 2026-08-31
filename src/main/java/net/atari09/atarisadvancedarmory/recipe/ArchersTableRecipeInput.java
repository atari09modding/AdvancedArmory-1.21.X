package net.atari09.atarisadvancedarmory.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ArchersTableRecipeInput(ItemStack input, ItemStack input2, ItemStack input3) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return switch (i){
            case 0->input;
            case 1-> input2;
            case 2 -> input3;
            default -> input;
        };
    }

    @Override
    public int size() {
        return 1;
    }
}

