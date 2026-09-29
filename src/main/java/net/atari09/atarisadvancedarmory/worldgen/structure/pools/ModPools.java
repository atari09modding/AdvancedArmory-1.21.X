package net.atari09.atarisadvancedarmory.worldgen.structure.pools;

import com.google.common.collect.ImmutableList;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class ModPools {

    public static ResourceKey<StructureTemplatePool> createKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, AtarisAdvancedArmory.res(name));
    }

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        IcyCavesVillagePools.bootstrap(context);
    }
}
