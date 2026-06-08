package net.atari09.atarisadvancedarmory.effect;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, AtarisAdvancedArmory.MOD_ID);
/*
    public static final Holder<MobEffect> SLIMEY_EFFECT = MOB_EFFECTS.register("slimey",
            ()-> new SlimeyEffect(MobEffectCategory.NEUTRAL, 0x36ebab)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            ResourceLocation.fromNamespaceAndPath(AtariMod.MOD_ID, "slimey"),
                            -0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));*/

    public static final Holder<MobEffect> FREEZE_EFFECT = MOB_EFFECTS.register("freezing",
            ()->new FreezeEffect(MobEffectCategory.HARMFUL,0x00D0FF)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            AtarisAdvancedArmory.res("freezing"),
                            -0.2f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
