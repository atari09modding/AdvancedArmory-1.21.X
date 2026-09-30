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

public class WallAlignInvertedProcessor extends StructureProcessor {

    public static final WallAlignInvertedProcessor INSTANCE = new WallAlignInvertedProcessor();
    public static final MapCodec<WallAlignInvertedProcessor> CODEC = MapCodec.unit(INSTANCE);

    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructureProcessorTypes.WALL_ALIGN.get();
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

        Direction direction = Direction.EAST;

        Rotation rotation = settings.getRotation();

        switch (rotation){
            case CLOCKWISE_90 -> direction = direction.getClockWise();
            case COUNTERCLOCKWISE_90 -> direction = direction.getCounterClockWise();
            case CLOCKWISE_180 ->  direction = direction.getOpposite();
        }

        RandomState randomState = worldGenLevel.getLevel().getChunkSource().randomState();
        BlockPos blockPos = relativeBlockInfo.pos();

        boolean found = false;
        int i = 1;
        do{

            int wallY = icyGen.sampleHeightIce(blockPos.relative(direction,i).getX(), blockPos.relative(direction,i).getZ(), randomState);
            if(wallY >=blockPos.getY()){
                found = true;
            } else {
                i++;
            }

        } while (!found);

        if(icyGen.sampleHeightIce(blockPos.getX(), blockPos.getZ(), randomState)>=blockPos.getY()){
            found = false;
            do{

                int wallY = icyGen.sampleHeightIce(blockPos.relative(direction,i).getX(), blockPos.relative(direction,i).getZ(), randomState);
                if(wallY <=blockPos.getY()){
                    found = true;
                    i--;
                } else {
                    i--;
                }

            } while (!found);
        }



        BlockPos newPos = blockPos.relative(direction,i);
        if(direction == Direction.WEST ||direction == Direction.EAST){
            newPos = newPos.offset(offset.getX(),0,0);
        } else {
            newPos = newPos.offset(0,0,offset.getZ());
        }

        return new StructureTemplate.StructureBlockInfo(newPos, relativeBlockInfo.state(), relativeBlockInfo.nbt());
    }


}
