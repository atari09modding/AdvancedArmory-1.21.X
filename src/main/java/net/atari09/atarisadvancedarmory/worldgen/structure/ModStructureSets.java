package net.atari09.atarisadvancedarmory.worldgen.structure;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class ModStructureSets {
    public static final ResourceKey<StructureSet> ICY_CAVES_VILLAGES = createKey("icy_caves_villages");

    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structureLookup = context.lookup(Registries.STRUCTURE);
        HolderGetter<Biome> biomeLookup = context.lookup(Registries.BIOME);

        context.register(
                ICY_CAVES_VILLAGES,
                new StructureSet(
                        structureLookup.getOrThrow(ModStructures.VILLAGE_SNOWY_ICY_CAVES),
                        new RandomSpreadStructurePlacement(34, 8, RandomSpreadType.LINEAR, 982889362)
                )
        );
    }

    private static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, AtarisAdvancedArmory.res(name));
    }
}
