package net.atari09.atarisadvancedarmory.worldgen.structure;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.util.ModTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.SnowyVillagePools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

public class ModStructures {
    public static final ResourceKey<Structure> VILLAGE_SNOWY_ICY_CAVES = createKey("village_snowy_icy_caves");


    public static void bootstrap(BootstrapContext<Structure> context){
        HolderGetter<Biome> biomeLookup = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> templatePoolLookup = context.lookup(Registries.TEMPLATE_POOL);

        context.register(
                VILLAGE_SNOWY_ICY_CAVES,
                new JigsawStructure(
                        new Structure.StructureSettings.Builder(biomeLookup.getOrThrow(ModTags.Biomes.HAS_VILLAGE_SNOWY_ICY_CAVES))
                                .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                                .build(),
                        templatePoolLookup.getOrThrow(SnowyVillagePools.START),
                        6,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        true,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
    }

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, AtarisAdvancedArmory.res(name));
    }
}
