package net.atari09.atarisadvancedarmory.recipe;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
        DeferredRegister.create(Registries.RECIPE_SERIALIZER, AtarisAdvancedArmory.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, AtarisAdvancedArmory.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<WeaponSmithRecipe>> WEAPONSMITH_SERIALIZER =
            SERIALIZERS.register("weaponsmith", WeaponSmithRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<WeaponSmithRecipe>> WEAPONSMITH_TYPE =
            TYPES.register("weaponsmith", ()-> new RecipeType<WeaponSmithRecipe>() {
                @Override
                public String toString() {
                    return "weaponsmith";
                }
            });







    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
