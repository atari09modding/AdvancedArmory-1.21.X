package net.atari09.atarisadvancedarmory.worldgen.structure.pools;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.worldgen.structure.processor.ModProcessorLists;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.function.Function;

public class IcyCavesVillagePools {

    public static  final ResourceKey<StructureTemplatePool> START = ModPools.createKey("icy_caves_village/start");
    public static  final ResourceKey<StructureTemplatePool> START_P2 = ModPools.createKey("icy_caves_village/start_p2");
    public static  final ResourceKey<StructureTemplatePool> HOUSES = ModPools.createKey("icy_caves_village/houses");
    public static  final ResourceKey<StructureTemplatePool> CONNECTIONS = ModPools.createKey("icy_caves_village/connections");
    public static  final ResourceKey<StructureTemplatePool> CONNECTIONS_NO_LADDER = ModPools.createKey("icy_caves_village/connections_no_ladder");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> poolLookup = context.lookup(Registries.TEMPLATE_POOL);
        HolderGetter<StructureProcessorList> processorLookup = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureTemplatePool> empty = poolLookup.getOrThrow(Pools.EMPTY);

        context.register(START,new StructureTemplatePool(empty, ImmutableList.of(
                Pair.of(element("start",processorLookup),100)
        ), StructureTemplatePool.Projection.RIGID));

        context.register(START_P2,new StructureTemplatePool(empty, ImmutableList.of(
                Pair.of(element("start_p2",processorLookup),100)
        ), StructureTemplatePool.Projection.RIGID));


        context.register(HOUSES,new StructureTemplatePool(empty, ImmutableList.of(
                Pair.of(element("house_1",processorLookup),80),
                Pair.of(element("house_2",processorLookup),150),
                Pair.of(element("house_3",processorLookup),80),
                Pair.of(element("house_4",processorLookup),80),
                Pair.of(element("house_5",processorLookup),80),
                Pair.of(element("house_6",processorLookup),50)
        ), StructureTemplatePool.Projection.RIGID));

        context.register(CONNECTIONS,new StructureTemplatePool(empty, ImmutableList.of(
                Pair.of(element("stand_1",processorLookup),80),
                Pair.of(element("stand_2",processorLookup),5),
                Pair.of(element("stand_3",processorLookup),80),
                Pair.of(element("stand_4",processorLookup),5),
                Pair.of(element("connection_1",processorLookup),80),
                Pair.of(element("connection_2",processorLookup),80),
                Pair.of(element("connection_3",processorLookup),80),
                Pair.of(element("connection_4",processorLookup),100),
                Pair.of(element("connection_5",processorLookup),80),
                Pair.of(element("connection_6",processorLookup),100),
                Pair.of(element("connection_7",processorLookup),50),
                Pair.of(element("connection_8",processorLookup),150),
                Pair.of(element("connection_9",processorLookup),80),
                Pair.of(element("connection_10",processorLookup),80),
                Pair.of(element("connection_11",processorLookup),80),
                Pair.of(element("connection_12",processorLookup),100),
                Pair.of(element("connection_13",processorLookup),80),
                Pair.of(element("connection_14",processorLookup),80),
                Pair.of(element("connection_15",processorLookup),50)
        ), StructureTemplatePool.Projection.RIGID));//change Rigid to custom one here later

        context.register(CONNECTIONS_NO_LADDER,new StructureTemplatePool(empty, ImmutableList.of(
                Pair.of(element("stand_1",processorLookup),80),
                Pair.of(element("stand_2",processorLookup),5),
                Pair.of(element("stand_3",processorLookup),80),
                Pair.of(element("stand_4",processorLookup),5),
                Pair.of(elementWallMatching("connection_1",processorLookup),80),
                Pair.of(elementWallMatching("connection_2",processorLookup),80),
                Pair.of(elementWallMatching("connection_3",processorLookup),80),
                Pair.of(elementWallMatching("connection_4",processorLookup),100),
                Pair.of(elementWallMatching("connection_5",processorLookup),80),
                Pair.of(elementWallMatching("connection_6",processorLookup),100),
                Pair.of(elementWallMatching("connection_7",processorLookup),50),
                Pair.of(elementWallMatching("connection_9",processorLookup),80),
                Pair.of(elementWallMatching("connection_10",processorLookup),80),
                Pair.of(elementWallMatching("connection_11",processorLookup),80),
                Pair.of(elementWallMatching("connection_12",processorLookup),100),
                Pair.of(elementWallMatching("connection_13",processorLookup),80),
                Pair.of(elementWallMatching("connection_14",processorLookup),80),
                Pair.of(elementWallMatching("connection_15",processorLookup),50)
        ), StructureTemplatePool.Projection.RIGID));//change Rigid to custom one here later

    }

    private static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> element(String name,HolderGetter<StructureProcessorList> lookup){
        return StructurePoolElement.legacy(make(name),lookup.getOrThrow(ModProcessorLists.WALL_ALIGN));
    }

    private static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> elementWallMatching(String name, HolderGetter<StructureProcessorList> lookup){
        return StructurePoolElement.legacy(make(name),lookup.getOrThrow(ModProcessorLists.WALL_ALIGN_MATCHING));
    }

    private static String make(String name){
        return AtarisAdvancedArmory.MOD_ID + ":icy_caves_village/" + name;
    }


}
