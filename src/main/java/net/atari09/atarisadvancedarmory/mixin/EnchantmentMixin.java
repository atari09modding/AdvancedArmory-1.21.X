package net.atari09.atarisadvancedarmory.mixin;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(Enchantment.EnchantmentDefinition.class)
public class EnchantmentMixin {
    @Redirect(method = "<clinit>",at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/codecs/RecordCodecBuilder;mapCodec(Ljava/util/function/Function;)Lcom/mojang/serialization/MapCodec;"))
    private static MapCodec<?> modifyMaxValue(Function<RecordCodecBuilder.Instance<?>, ? extends App<RecordCodecBuilder.Mu<?>, ?>> builder){

        MapCodec<Enchantment.EnchantmentDefinition> CODEC = RecordCodecBuilder.mapCodec((p_344890_) -> p_344890_.group(RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("supported_items").forGetter(Enchantment.EnchantmentDefinition::supportedItems),
                RegistryCodecs.homogeneousList(Registries.ITEM).optionalFieldOf("primary_items").forGetter(Enchantment.EnchantmentDefinition::primaryItems),
                ExtraCodecs.intRange(1, 1024).fieldOf("weight").forGetter(Enchantment.EnchantmentDefinition::weight), ExtraCodecs.intRange(1, Integer.MAX_VALUE).fieldOf("max_level").forGetter((def)->Integer.MAX_VALUE),
                Enchantment.Cost.CODEC.fieldOf("min_cost").forGetter(Enchantment.EnchantmentDefinition::minCost), Enchantment.Cost.CODEC.fieldOf("max_cost").forGetter(Enchantment.EnchantmentDefinition::maxCost),
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("anvil_cost").forGetter(Enchantment.EnchantmentDefinition::anvilCost),
                EquipmentSlotGroup.CODEC.listOf().fieldOf("slots").forGetter(Enchantment.EnchantmentDefinition::slots))
                .apply(p_344890_, Enchantment.EnchantmentDefinition::new));
        return CODEC;

    }
}
