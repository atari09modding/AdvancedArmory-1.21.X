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

public record WeaponSmithRecipe(Ingredient inputItem, Ingredient inputItem2, Ingredient template, ItemStack output) implements Recipe<WeaponSmithRecipeInput>,
        WeaponSmithBlockEntity.WeaponSmithingRecipe<WeaponSmithRecipeInput> {

    @Override
    public boolean matches(WeaponSmithRecipeInput input, Level level) {
        if (level.isClientSide)return false;
        return inputItem.test(input.getItem(0)) && inputItem2.test(input.getItem(1)) && template.test(input.getItem(2));
    }

    @Override
    public ItemStack assemble(WeaponSmithRecipeInput weaponSmithRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list =NonNullList.create();
        list.add(inputItem);
        list.add(inputItem2);
        list.add(template);
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
        return ModRecipes.WEAPONSMITH_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.WEAPONSMITH_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<WeaponSmithRecipe> {
        public static final MapCodec<WeaponSmithRecipe> CODEC = RecordCodecBuilder.mapCodec(inst->inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(WeaponSmithRecipe::inputItem),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient2").forGetter(WeaponSmithRecipe::inputItem2),
                Ingredient.CODEC_NONEMPTY.fieldOf("template").forGetter(WeaponSmithRecipe::template),
                ItemStack.CODEC.fieldOf("result").forGetter(WeaponSmithRecipe::output)
        ).apply(inst,WeaponSmithRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, WeaponSmithRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, WeaponSmithRecipe::inputItem,
                        Ingredient.CONTENTS_STREAM_CODEC, WeaponSmithRecipe::inputItem2,
                        Ingredient.CONTENTS_STREAM_CODEC, WeaponSmithRecipe::template,
                        ItemStack.STREAM_CODEC, WeaponSmithRecipe::output,
                        WeaponSmithRecipe::new);

        @Override
        public MapCodec<WeaponSmithRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, WeaponSmithRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
