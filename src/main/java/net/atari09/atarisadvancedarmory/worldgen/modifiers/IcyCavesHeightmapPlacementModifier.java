package net.atari09.atarisadvancedarmory.worldgen.modifiers;

import com.mojang.serialization.MapCodec;
import net.atari09.atarisadvancedarmory.worldgen.chunkgen.IcyCavesChunkGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.Random;
import java.util.stream.Stream;

public class IcyCavesHeightmapPlacementModifier extends PlacementModifier {
    public static final IcyCavesHeightmapPlacementModifier INSTANCE = new IcyCavesHeightmapPlacementModifier();
    public static final MapCodec<IcyCavesHeightmapPlacementModifier> CODEC = MapCodec.unit(INSTANCE);
    public static final PlacementModifierType<IcyCavesHeightmapPlacementModifier> TYPE = () -> CODEC;


    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
        int surfaceY = /* Aufruf deines Generators, siehe unten */
                ((IcyCavesChunkGenerator) context.generator()).sampleHeightIce(pos.getX(), pos.getZ(), context.getLevel().getLevel().getChunkSource().randomState());

        return surfaceY >= context.generator().getSeaLevel()? Stream.of(new BlockPos(pos.getX(), surfaceY, pos.getZ())) : Stream.empty();
    }

    @Override
    public PlacementModifierType<?> type() {
        return TYPE;
    }
}
