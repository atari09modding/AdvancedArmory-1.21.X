package net.atari09.atarisadvancedarmory.worldgen.chunkgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.worldgen.noise.FastNoiseLite;
import net.atari09.atarisadvancedarmory.worldgen.noise.IceFloeNoise;
import net.atari09.atarisadvancedarmory.worldgen.noise.ModNoises;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.synth.NormalNoise;


import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IcyCavesChunkGenerator extends ChunkGenerator {

    public static final MapCodec<IcyCavesChunkGenerator> CODEC = RecordCodecBuilder.mapCodec((p) ->
            p.group(BiomeSource.CODEC.fieldOf("biome_source")
                    .forGetter((generator) -> generator.biomeSource), NoiseGeneratorSettings.CODEC.fieldOf("settings")
                    .forGetter((generator) -> generator.settings)).apply(p, p.stable(IcyCavesChunkGenerator::new)));

    private final Holder<NoiseGeneratorSettings> settings;

    private final int BASE_HEIGHT = 20;






    public IcyCavesChunkGenerator(BiomeSource biomeSource,Holder<NoiseGeneratorSettings> settings) {
        super(biomeSource);
        this.settings = settings;
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    /**
     * main method called to get height
     * called in fillfromnoise, getbaseheight, and getbasecolumn
     */
    private int sampleHeight(int x, int z, RandomState randomState) {


        NormalNoise continentalnessNoise = randomState.getOrCreateNoise(Noises.CONTINENTALNESS);
        NormalNoise jaggednessNoise = randomState.getOrCreateNoise(Noises.JAGGED);

        NormalNoise wallsNoise = randomState.getOrCreateNoise(ModNoises.ICY_CAVES_WALLS);
        NormalNoise wallsDetailNoise = randomState.getOrCreateNoise(ModNoises.ICY_CAVES_DETAILS_BOTTOM);

        double noiseContinentalnessValue = continentalnessNoise.getValue(x ,0, z );
        double noiseJaggedValue = jaggednessNoise.getValue(x ,0, z );
        double wallsValue = wallHeightMap(wallsNoise.getValue(x,0,z),-0.2d,0.2d);
        double wallsValueSmooth = wallHeightMap(wallsNoise.getValue(x,0,z),-0.5d,0.5d);

        double wallsDetailValue = wallHeightMap(wallsDetailNoise.getValue(x,0,z),-0.5d,0.5d) * (wallsValue>=0.2d? 1:0);


        int continentalness = (int)(Math.round(noiseContinentalnessValue *10));
        int jagged = (int)(Math.round(noiseJaggedValue * 8));

        int walls = (int)(Math.round(wallsValue*1000));
        int wallsSmooth = (int)(Math.round(wallsValueSmooth*100));
        int wallsDetail = (int)(Math.round(wallsDetailValue*20));






        return BASE_HEIGHT + continentalness + jagged + walls + wallsDetail + wallsSmooth;
    }

    private boolean isCave(int x, int y, int z, RandomState random){
        int caveheight = getSeaLevel()+10;



        return false;
    }


    private int sampleCeiling(int x, int z, RandomState randomState) {
        int maxY = getMinY() + getGenDepth();

        NormalNoise continentalnessNoise = randomState.getOrCreateNoise(Noises.CONTINENTALNESS);
        NormalNoise jaggednessNoise = randomState.getOrCreateNoise(Noises.JAGGED);

        NormalNoise wallsNoise = randomState.getOrCreateNoise(ModNoises.ICY_CAVES_WALLS);
        NormalNoise wallsDetailNoise = randomState.getOrCreateNoise(ModNoises.ICY_CAVES_DETAILS_TOP);



        double noiseContinentalnessValue = continentalnessNoise.getValue(x ,0, z );
        double noiseJaggedValue = jaggednessNoise.getValue(x ,0, z );
        double wallsValue = wallHeightMap(wallsNoise.getValue(x,0,z),-0.2d,0.2d);
        double wallsValueSmooth = wallHeightMap(wallsNoise.getValue(x,0,z),-0.5d,0.5d);

        double wallsDetailValue = wallHeightMap(wallsDetailNoise.getValue(x,0,z),-0.5d,0.5d)* (wallsValue>=0.2d? 1:0);





        int continentalness = (int)(Math.round(noiseContinentalnessValue *10));
        int jagged = (int)(Math.round(noiseJaggedValue * 8));

        int walls = (int)(Math.round(wallsValue*1000));
        int wallsSmooth = (int)(Math.round(wallsValueSmooth*100));
        int wallsDetail = (int)(Math.round(wallsDetailValue*20));



        return  maxY - (BASE_HEIGHT + continentalness + jagged + walls + wallsDetail + wallsSmooth);
    }

    private double wallHeightMap(double d, double min, double max){
        if(d >= min && d <=max){
            double mid =(max+min)/2d;
            return max-Math.abs(d-mid);
        } else {
            return 0d;
        }
    }

    @Override
    public void applyCarvers(WorldGenRegion level, long seed, RandomState random, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk, GenerationStep.Carving step) {

    }


    // runs AFTER fillFromNoise and replaces surface with certain blocks

    @Override
    public void buildSurface(WorldGenRegion level, StructureManager structureManager, RandomState random, ChunkAccess chunk) {
        int chunkX = chunk.getPos().getMinBlockX();
        int chunkZ = chunk.getPos().getMinBlockZ();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunkX + x;
                int worldZ = chunkZ + z;
                int surfaceY = sampleHeight(worldX, worldZ,random);

                //here suface stuff later
            }
        }
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion level) {

    }

    @Override
    public int getGenDepth() {
        return ((NoiseGeneratorSettings)this.settings.value()).noiseSettings().height();
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunk) {
        return CompletableFuture.supplyAsync(()->{
            int chunkX = chunk.getPos().getMinBlockX();
            int chunkZ = chunk.getPos().getMinBlockZ();
            int maxY = getMinY() + getGenDepth();

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int worldX = chunkX + x;
                    int worldZ = chunkZ + z;
                    int surfaceY = sampleHeight(worldX, worldZ,randomState);
                    int ceilY = sampleCeiling(worldX,worldZ,randomState);

                    //make bedrock floor
                    chunk.setBlockState(new BlockPos(x, getMinY()+1, z),
                            Blocks.BEDROCK.defaultBlockState(), false);

                    //make bedrock ceiling
                    chunk.setBlockState(new BlockPos(x, maxY-1, z),
                            Blocks.BEDROCK.defaultBlockState(), false);


                    for (int y = getMinY()+1; y < surfaceY; y++) {
                        chunk.setBlockState(new BlockPos(x, y, z),
                                Blocks.STONE.defaultBlockState(), false);
                    }

                    for(int y = maxY-1; y>ceilY; y--){
                        chunk.setBlockState(new BlockPos(x, y, z),
                                Blocks.STONE.defaultBlockState(), false);

                    }



                    long floeSeed = randomState
                            .getOrCreateRandomFactory(AtarisAdvancedArmory.res("ice_floes"))
                            .at(0, 0, 0)
                            .nextLong();
                    IceFloeNoise floes = new IceFloeNoise(floeSeed);

                    if(chunk.getBlockState(new BlockPos(x, getSeaLevel(), z)).isEmpty() && floes.isFloe(worldX,worldZ)){
                        chunk.setBlockState(new BlockPos(x, getSeaLevel(), z), Blocks.PACKED_ICE.defaultBlockState(), false);
                        if(floes.floeThickness(worldX,worldZ)>1){
                            if(chunk.getBlockState(new BlockPos(x, getSeaLevel()+1, z)).isEmpty()){
                                chunk.setBlockState(new BlockPos(x, getSeaLevel()+1, z), Blocks.PACKED_ICE.defaultBlockState(), false);

                            }
                            if(chunk.getBlockState(new BlockPos(x, getSeaLevel()-1, z)).isEmpty()){
                                chunk.setBlockState(new BlockPos(x, getSeaLevel()-1, z), Blocks.PACKED_ICE.defaultBlockState(), false);

                            }
                        }
                    }

                    // fill with Water if sealevel lower
                    for (int y = surfaceY; y < getSeaLevel(); y++) {
                        chunk.setBlockState(new BlockPos(x, y, z), Blocks.WATER.defaultBlockState(), false);
                    }
                }
            }
            return chunk;
        });
    }


    @Override
    public void applyBiomeDecoration(WorldGenLevel level, ChunkAccess chunk, StructureManager structureManager) {

    }

    @Override
    public int getSeaLevel() {
        return ((NoiseGeneratorSettings)this.settings.value()).seaLevel();
    }

    @Override
    public int getMinY() {
        return ((NoiseGeneratorSettings)this.settings.value()).noiseSettings().minY();
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor level, RandomState random) {
        return sampleHeight(x,z,random);
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor height, RandomState random) {
        int surfaceY = sampleHeight(x, z,random);
        var states = new net.minecraft.world.level.block.state.BlockState[height.getHeight()];
        for (int i = 0; i < states.length; i++) {
            int y = height.getMinBuildHeight() + i;
            if (y < surfaceY) {
                states[i] = Blocks.STONE.defaultBlockState();
            } else if (y < getSeaLevel()) {
                states[i] = Blocks.WATER.defaultBlockState();
            } else {
                states[i] = Blocks.AIR.defaultBlockState();
            }
        }
        return new NoiseColumn(height.getMinBuildHeight(), states);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState random, BlockPos pos) {
        NormalNoise walls = random.getOrCreateNoise(ModNoises.ICY_CAVES_WALLS);
        NormalNoise wallsDetailTop = random.getOrCreateNoise(ModNoises.ICY_CAVES_DETAILS_TOP);
        NormalNoise wallsDetailBottom = random.getOrCreateNoise(ModNoises.ICY_CAVES_DETAILS_BOTTOM);
        info.add("IcyCaves:");
        info.add("Walls:" + walls.getValue(pos.getX(),0,pos.getZ()));
        info.add("WallsDetailTop:" + wallHeightMap(wallsDetailTop.getValue(pos.getX(),0,pos.getZ()),-0.2,0.2));
        info.add("WallsDetailBottom:" + wallsDetailBottom.getValue(pos.getX(),0,pos.getZ()));
    }
}
