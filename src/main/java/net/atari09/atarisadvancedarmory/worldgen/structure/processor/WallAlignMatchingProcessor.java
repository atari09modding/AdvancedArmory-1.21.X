package net.atari09.atarisadvancedarmory.worldgen.structure.processor;

import com.mojang.serialization.MapCodec;
import net.atari09.atarisadvancedarmory.worldgen.chunkgen.IcyCavesChunkGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class WallAlignMatchingProcessor extends StructureProcessor {
    public static final WallAlignMatchingProcessor INSTANCE = new WallAlignMatchingProcessor();
    public static final MapCodec<WallAlignMatchingProcessor> CODEC = MapCodec.unit(INSTANCE);
    public static final int MAX_SEARCH = 64;



    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructureProcessorTypes.WALL_ALIGN_MATCHING.get();
    }

    @Override
    public @Nullable StructureTemplate.StructureBlockInfo processBlock(
            LevelReader level, BlockPos offset, BlockPos pos,
            StructureTemplate.StructureBlockInfo blockInfo,
            StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings) {

        if (!(level instanceof WorldGenLevel worldGenLevel)) {
            return relativeBlockInfo; // Fallback, z.B. im Structure-Block-Editor/Preview
        }

        ChunkGenerator generator = worldGenLevel.getLevel().getChunkSource().getGenerator();
        if (!(generator instanceof IcyCavesChunkGenerator icyGen)) {
            return relativeBlockInfo;
        }

        Direction direction = Direction.WEST;

        Rotation rotation = settings.getRotation();

        switch (rotation){
            case CLOCKWISE_90 -> direction = direction.getClockWise();
            case COUNTERCLOCKWISE_90 -> direction = direction.getCounterClockWise();
            case CLOCKWISE_180 ->  direction = direction.getOpposite();
        }

        RandomState randomState = worldGenLevel.getLevel().getChunkSource().randomState();
        BlockPos blockPos = relativeBlockInfo.pos();

        /*
        boolean found = false;
        int i = 1;
        do{

            int wallY = icyGen.sampleHeightIce(blockPos.relative(direction,i).getX(), blockPos.relative(direction,i).getZ(), randomState);
            int ceilY = icyGen.sampleCeiling(blockPos.relative(direction,i).getX(), blockPos.relative(direction,i).getZ(), randomState);
            if(wallY <=blockPos.getY() && blockPos.getY() <= ceilY){
                found = true;
            } else {
                i++;
            }

        } while (!found);

        if(icyGen.sampleHeightIce(blockPos.getX(), blockPos.getZ(), randomState)>=blockPos.getY() || icyGen.sampleCeiling(blockPos.relative(direction,i).getX(), blockPos.relative(direction,i).getZ(), randomState)<=blockPos.getY()) {
            found = false;
            do{

                int wallY = icyGen.sampleHeightIce(blockPos.relative(direction,i).getX(), blockPos.relative(direction,i).getZ(), randomState);
                int ceilY = icyGen.sampleCeiling(blockPos.relative(direction,i).getX(), blockPos.relative(direction,i).getZ(), randomState);
                if(wallY <=blockPos.getY() && blockPos.getY() <= ceilY){
                    found = true;
                    i--;
                } else {
                    i--;
                }

            } while (!found);
        }*/

        int shift = findWallShift(icyGen, randomState, blockPos, direction);





        BlockPos newPos = blockPos.relative(direction,shift);
        if(direction == Direction.WEST ||direction == Direction.EAST){
            newPos = newPos.offset(blockPos.getX(),0,0);
        } else {
            newPos = newPos.offset(0,0,blockPos.getZ());
        }

        return new StructureTemplate.StructureBlockInfo(newPos, relativeBlockInfo.state(), relativeBlockInfo.nbt());
    }

    private static int findWallShift(IcyCavesChunkGenerator icyGen, RandomState randomState, BlockPos anchor, Direction direction) {
        for (int i = 1; i <= MAX_SEARCH; i++) {
            BlockPos test = anchor.relative(direction, i);
            int floorY = icyGen.sampleHeightIce(test.getX(), test.getZ(), randomState);
            int ceilY = icyGen.sampleCeiling(test.getX(), test.getZ(), randomState);

            if (floorY <= anchor.getY() && anchor.getY() <= ceilY) {
                return i;
            }
        }
        return 0; // nichts gefunden: keine Verschiebung statt Endlosschleife/falscher Wert
    }
}
