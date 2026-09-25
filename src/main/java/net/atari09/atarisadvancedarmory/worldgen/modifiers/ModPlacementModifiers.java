package net.atari09.atarisadvancedarmory.worldgen.modifiers;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModPlacementModifiers {
    public static final DeferredRegister<PlacementModifierType<?>> MODIFIERS = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, AtarisAdvancedArmory.MOD_ID);

    public static final Supplier<PlacementModifierType<IcyCavesHeightmapPlacementModifier>> ICY_CAVES_HEIGHTMAP =
            MODIFIERS.register("icy_caves_heightmap", ()-> IcyCavesHeightmapPlacementModifier.TYPE);

    public static void register(IEventBus eventBus){
        MODIFIERS.register(eventBus);
    }
}
