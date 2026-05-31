package net.atari09.atarisadvancedarmory.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.atari09.atarisadvancedarmory.block.entity.WeaponSmithBlockEntity;
import net.atari09.atarisadvancedarmory.item.custom.SpecialSmithingTemplateItem;
import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
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

public record WeaponSmithTemplateTypeRecipe(Ingredient inputItem, Ingredient inputItem2, SpecialSmithingTemplateType template, ItemStack output)
        implements Recipe<WeaponSmithRecipeTemplateTypeInput>, WeaponSmithBlockEntity.WeaponSmithingRecipe<WeaponSmithRecipeTemplateTypeInput> {


    @Override
    public boolean matches(WeaponSmithRecipeTemplateTypeInput input, Level level) {
        if (level.isClientSide)return false;
        return inputItem.test(input.getItem(0)) && inputItem2.test(input.getItem(1)) && template.check(input.template());
    }

    @Override
    public ItemStack assemble(WeaponSmithRecipeTemplateTypeInput input, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list =NonNullList.create();
        list.add(inputItem);
        list.add(inputItem2);
        //list.add(template);
        return list;
    }

    public SpecialSmithingTemplateType getTemplateType(){
        return template;
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
        return ModRecipes.WEAPONSMITH_TT_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.WEAPONSMITH_TT_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<WeaponSmithTemplateTypeRecipe> {
        public static final MapCodec<WeaponSmithTemplateTypeRecipe> CODEC = RecordCodecBuilder.mapCodec(inst->inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(WeaponSmithTemplateTypeRecipe::inputItem),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient2").forGetter(WeaponSmithTemplateTypeRecipe::inputItem2),
                SpecialSmithingTemplateType.CODEC.fieldOf("template").forGetter(WeaponSmithTemplateTypeRecipe::template),
                ItemStack.CODEC.fieldOf("result").forGetter(WeaponSmithTemplateTypeRecipe::output)
        ).apply(inst, WeaponSmithTemplateTypeRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, WeaponSmithTemplateTypeRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, WeaponSmithTemplateTypeRecipe::inputItem,
                        Ingredient.CONTENTS_STREAM_CODEC, WeaponSmithTemplateTypeRecipe::inputItem2,
                        SpecialSmithingTemplateType.STREAM_CODEC, WeaponSmithTemplateTypeRecipe::template,
                        ItemStack.STREAM_CODEC, WeaponSmithTemplateTypeRecipe::output,
                        WeaponSmithTemplateTypeRecipe::new);

        @Override
        public MapCodec<WeaponSmithTemplateTypeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, WeaponSmithTemplateTypeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
