package net.atari09.atarisadvancedarmory.worldgen.structure.pools;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.function.Function;

public class IcyCavesVillagePools {

    public static  final ResourceKey<StructureTemplatePool> START = ModPools.createKey("icy_caves_village/start");
    public static  final ResourceKey<StructureTemplatePool> START_P2 = ModPools.createKey("icy_caves_village/start_p2");
    public static  final ResourceKey<StructureTemplatePool> HOUSES = ModPools.createKey("icy_caves_village/houses");
    public static  final ResourceKey<StructureTemplatePool> CONNECTIONS = ModPools.createKey("icy_caves_village/connections");
    public static  final ResourceKey<StructureTemplatePool> CONNECTIONS_NO_LADDER = ModPools.createKey("icy_caves_village/connections_no_ladder");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> poolLookup = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> empty = poolLookup.getOrThrow(Pools.EMPTY);

        context.register(START,new StructureTemplatePool(empty, ImmutableList.of(
                Pair.of(element("start"),100)
        ), StructureTemplatePool.Projection.RIGID));

        context.register(START_P2,new StructureTemplatePool(empty, ImmutableList.of(
                Pair.of(element("start_p2"),100)
        ), StructureTemplatePool.Projection.RIGID));


        context.register(HOUSES,new StructureTemplatePool(empty, ImmutableList.of(
                Pair.of(element("house_1"),80),
                Pair.of(element("house_2"),150),
                Pair.of(element("house_3"),80),
                Pair.of(element("house_4"),80),
                Pair.of(element("house_5"),80),
                Pair.of(element("house_6"),50)
        ), StructureTemplatePool.Projection.RIGID));

        context.register(CONNECTIONS,new StructureTemplatePool(empty, ImmutableList.of(
                Pair.of(element("stand_1"),80),
                Pair.of(element("stand_2"),5),
                Pair.of(element("stand_3"),80),
                Pair.of(element("stand_4"),5),
                Pair.of(element("connection_1"),80),
                Pair.of(element("connection_2"),80),
                Pair.of(element("connection_3"),80),
                Pair.of(element("connection_4"),100),
                Pair.of(element("connection_5"),80),
                Pair.of(element("connection_6"),100),
                Pair.of(element("connection_7"),50),
                Pair.of(element("connection_8"),150),
                Pair.of(element("connection_9"),80),
                Pair.of(element("connection_10"),80),
                Pair.of(element("connection_11"),80),
                Pair.of(element("connection_12"),100),
                Pair.of(element("connection_13"),80),
                Pair.of(element("connection_14"),80),
                Pair.of(element("connection_15"),50)
        ), StructureTemplatePool.Projection.RIGID));//change Rigid to custom one here later

        context.register(CONNECTIONS_NO_LADDER,new StructureTemplatePool(empty, ImmutableList.of(
                Pair.of(element("stand_1"),80),
                Pair.of(element("stand_2"),5),
                Pair.of(element("stand_3"),80),
                Pair.of(element("stand_4"),5),
                Pair.of(element("connection_1"),80),
                Pair.of(element("connection_2"),80),
                Pair.of(element("connection_3"),80),
                Pair.of(element("connection_4"),100),
                Pair.of(element("connection_5"),80),
                Pair.of(element("connection_6"),100),
                Pair.of(element("connection_7"),50),
                Pair.of(element("connection_9"),80),
                Pair.of(element("connection_10"),80),
                Pair.of(element("connection_11"),80),
                Pair.of(element("connection_12"),100),
                Pair.of(element("connection_13"),80),
                Pair.of(element("connection_14"),80),
                Pair.of(element("connection_15"),50)
        ), StructureTemplatePool.Projection.RIGID));//change Rigid to custom one here later

    }

    private static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> element(String name){
        return StructurePoolElement.legacy(make(name));
    }

    private static String make(String name){
        return AtarisAdvancedArmory.MOD_ID + ":icy_caves_village/" + name;
    }
}
