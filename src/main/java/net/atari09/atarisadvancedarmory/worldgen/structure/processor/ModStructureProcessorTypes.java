package net.atari09.atarisadvancedarmory.worldgen.structure.processor;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModStructureProcessorTypes {
    public static final DeferredRegister<StructureProcessorType<?>> TYPES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_PROCESSOR, AtarisAdvancedArmory.MOD_ID);

    public static final Supplier<StructureProcessorType<WallAlignMatchingProcessor>> WALL_ALIGN_MATCHING = TYPES.register("wall_align_matching", ()->()-> WallAlignMatchingProcessor.CODEC);

    public static final Supplier<StructureProcessorType<WallAlignProcessor>> WALL_ALIGN = TYPES.register("wall_align", ()->()-> WallAlignProcessor.CODEC);
    public static final Supplier<StructureProcessorType<WallAlignInvertedProcessor>> WALL_ALIGN_INVERTED = TYPES.register("wall_align_inverted", ()->()-> WallAlignInvertedProcessor.CODEC);

    public static void register(IEventBus eventBus){
        TYPES.register(eventBus);
    }
}
