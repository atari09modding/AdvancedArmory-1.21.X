package net.atari09.atarisadvancedarmory.component;

import com.mojang.serialization.Codec;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.item.util.ElementalVariant;
import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, AtarisAdvancedArmory.MOD_ID);




    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SpecialSmithingTemplateType>> SPECIALSMITHINGTEMPLATETYPES = register("specialsmithingtemplatetypes",
            builder -> builder.persistent(SpecialSmithingTemplateType.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ElementalVariant>> ELEMENTAL_VARIANT = register("elemental_variant",
            builder -> builder.persistent(ElementalVariant.CODEC));

    public static DeferredHolder<DataComponentType<?>,DataComponentType<Integer>> ELEMENTAL_LEVEL = register("elemental_level",
            builder -> builder.persistent(Codec.INT));

    public static DeferredHolder<DataComponentType<?>,DataComponentType<Integer>> ABILITY_COOLDOWN = register("ability_cooldown",
            builder -> builder.persistent(Codec.INT));

    public static DeferredHolder<DataComponentType<?>,DataComponentType<Boolean>> DO_CRIT = register("do_crit",
            builder->builder.persistent(Codec.BOOL));





    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                           UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return DATA_COMPONENT_TYPES.register(name,()->builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus){
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
