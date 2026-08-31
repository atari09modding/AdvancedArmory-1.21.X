package net.atari09.atarisadvancedarmory.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.atari09.atarisadvancedarmory.block.entity.WeaponSmithBlockEntity;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record ArchersTableRecipe(Ingredient inputItem, Ingredient inputItem2, Ingredient inputItem3, ItemStack output) implements Recipe<ArchersTableRecipeInput> {

    @Override
    public boolean matches(ArchersTableRecipeInput input, Level level) {
        if (level.isClientSide)return false;
        boolean c1 = inputItem.test(input.getItem(0)) && inputItem2.test(input.getItem(1)) && inputItem3.test(input.getItem(2));
        boolean c2 = inputItem.test(input.getItem(0)) && inputItem2.test(input.getItem(2)) && inputItem3.test(input.getItem(1));
        return c1||c2;
    }

    @Override
    public ItemStack assemble(ArchersTableRecipeInput recipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list =NonNullList.create();
        list.add(inputItem);
        list.add(inputItem2);
        list.add(inputItem3);
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ARCHERSTABLE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ARCHERSTABLE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ArchersTableRecipe> {
        public static final MapCodec<ArchersTableRecipe> CODEC = RecordCodecBuilder.mapCodec(inst->inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(ArchersTableRecipe::inputItem),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient2").forGetter(ArchersTableRecipe::inputItem2),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient3").forGetter(ArchersTableRecipe::inputItem3),
                ItemStack.CODEC.fieldOf("result").forGetter(ArchersTableRecipe::output)
        ).apply(inst, ArchersTableRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ArchersTableRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, ArchersTableRecipe::inputItem,
                        Ingredient.CONTENTS_STREAM_CODEC, ArchersTableRecipe::inputItem2,
                        Ingredient.CONTENTS_STREAM_CODEC, ArchersTableRecipe::inputItem3,
                        ItemStack.STREAM_CODEC, ArchersTableRecipe::output,
                        ArchersTableRecipe::new);

        @Override
        public MapCodec<ArchersTableRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ArchersTableRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
