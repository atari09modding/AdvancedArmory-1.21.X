package net.atari09.atarisadvancedarmory.worldgen.chunkgen;

import com.mojang.serialization.MapCodec;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModChunkGenerators {

    public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS =
            DeferredRegister.create(BuiltInRegistries.CHUNK_GENERATOR, AtarisAdvancedArmory.MOD_ID);

    public static final Supplier<MapCodec<IcyCavesChunkGenerator>> ICY_CAVES =
            CHUNK_GENERATORS.register("icy_caves", ()->IcyCavesChunkGenerator.CODEC);


    public static void register(IEventBus eventBus){
        CHUNK_GENERATORS.register(eventBus);
    }
}
