package net.atari09.atarisadvancedarmory.component;

import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Optional;

public interface CraftingBlockEntity {

    void startCrafting();

    boolean hasRecipe();

    boolean isWorking();

    Optional<RecipeHolder<?>> getCurrentRecipe();
}
