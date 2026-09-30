package net.atari09.atarisadvancedarmory.worldgen.structure.processor;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;

public class ModProcessorLists {
    public static final ResourceKey<StructureProcessorList> WALL_ALIGN_MATCHING =
            ResourceKey.create(Registries.PROCESSOR_LIST, AtarisAdvancedArmory.res("wall_align_matching"));

    public static final ResourceKey<StructureProcessorList> WALL_ALIGN =
            ResourceKey.create(Registries.PROCESSOR_LIST, AtarisAdvancedArmory.res("wall_align"));

    public static final ResourceKey<StructureProcessorList> WALL_ALIGN_INVERTED =
            ResourceKey.create(Registries.PROCESSOR_LIST, AtarisAdvancedArmory.res("wall_align_inverted"));

    public static void bootstrap(BootstrapContext<StructureProcessorList> context) {
        context.register(WALL_ALIGN_MATCHING,
                new StructureProcessorList(List.of(WallAlignMatchingProcessor.INSTANCE)));

        context.register(WALL_ALIGN,
                new StructureProcessorList(List.of(WallAlignProcessor.INSTANCE)));

        context.register(WALL_ALIGN_INVERTED,
                new StructureProcessorList(List.of(WallAlignInvertedProcessor.INSTANCE)));
    }
}
