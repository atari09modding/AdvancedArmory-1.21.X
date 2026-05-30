package net.atari09.atarisadvancedarmory.block.custom;

import com.mojang.serialization.MapCodec;
import net.atari09.atarisadvancedarmory.block.ModBlocks;
import net.atari09.atarisadvancedarmory.block.entity.ModBlockEntities;
import net.atari09.atarisadvancedarmory.block.entity.WeaponSmithBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeaponSmithBaseBlock extends BaseEntityBlock {
    public MapCodec<WeaponSmithBaseBlock> CODEC = simpleCodec(WeaponSmithBaseBlock::new);
    public static final DirectionProperty FACING;
    public static final BooleanProperty WORKING = BooleanProperty.create("working");

    public WeaponSmithBaseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH).setValue(WORKING, false)));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WeaponSmithBlockEntity(blockPos,blockState);
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()){
            return null;
        }
        return createTickerHelper(blockEntityType,ModBlockEntities.WEAPONSMITHBLOCK_BE.get(),
                (level1,blockPos,blockState, blockEntity)->blockEntity.tick(level1,blockPos,blockState));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Direction facing = state.getValue(WeaponSmithBaseBlock.FACING);
        Direction direction = facing.getClockWise().getOpposite();
        BlockPos particlePos = pos.relative(direction);
        level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                particlePos.getX()+switch (facing){case EAST -> 0.3; case WEST -> 0.7;default -> 0.5;},
                particlePos.getY()+0.7,
                particlePos.getZ()+switch (facing){case NORTH -> 0.7; case SOUTH -> 0.3;default -> 0.5;},
                0,0.05,0);
        super.animateTick(state, level, pos, random);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WORKING);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {

        if (state.getBlock() != newState.getBlock() && !(newState.getBlock() instanceof WeaponSmithBaseBlock)) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof WeaponSmithBlockEntity be) {
                be.drops();
                level.removeBlockEntity(pos);
            }
            if(!level.isClientSide()){
                destroyChildren(level, pos, state);
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    public List<BlockPos> getStructure(Level level, BlockPos pos) {
        Direction d = level.getBlockState(pos).getValue(FACING).getOpposite();
        Direction right = d.getClockWise();
        Direction left = d.getCounterClockWise();

        return List.of(
                pos.relative(d),
                pos.relative(d).relative(right).relative(d.getOpposite()),
                pos.relative(d).relative(left),
                pos.relative(d).above()
        );
    }

    public List<BlockPos> getStructureforState(Level level, BlockState state, BlockPos pos){
        Direction d = state.getValue(FACING).getOpposite();
        Direction right = d.getClockWise();
        Direction left = d.getCounterClockWise();

        return List.of(
                pos.relative(d),
                pos.relative(d).relative(right).relative(d.getOpposite()),
                pos.relative(d).relative(left),
                pos.relative(d).above()
        );
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if(!level.isClientSide()){
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity instanceof WeaponSmithBlockEntity weaponSmithBlockEntity){
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(weaponSmithBlockEntity, Component.literal("Weaponsmith")),pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing");
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide()){
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity instanceof WeaponSmithBlockEntity weaponSmithBlockEntity){
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(weaponSmithBlockEntity, Component.literal("Weaponsmith")),pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing");
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    public void openMenu(Level level, BlockPos pos, Player player){
        if(!level.isClientSide()){
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity instanceof WeaponSmithBlockEntity weaponSmithBlockEntity){
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(weaponSmithBlockEntity, Component.literal("Weaponsmith")),pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing");
            }
        }
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return false;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Direction direction = context.getHorizontalDirection();
        BlockPos pos2 = pos.relative(direction);
        Direction right =  direction.getClockWise();
        Direction left =  direction.getClockWise().getOpposite();

        if (!level.getBlockState(pos2).canBeReplaced()
                ||!level.getBlockState(pos2.above()).canBeReplaced()
                ||!level.getBlockState(pos2.relative(right).relative(direction)).canBeReplaced()
                ||!level.getBlockState(pos2.relative(left)).canBeReplaced()
        ) {
            return null;
        }

        return this.defaultBlockState().setValue(FACING, direction.getOpposite());
    }



    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if(!level.isClientSide()) placeChildren(level,pos);
    }


    public void placeChildren(Level level, BlockPos pos){
        if(!level.isClientSide()){
            for(BlockPos p : getStructure(level,pos)){
                level.setBlock(p, ModBlocks.WEAPONSMITHPIECEBLOCK.get().defaultBlockState(),3);
            }

        }
    }

    public void destroyChildren(Level level, BlockPos pos,BlockState state){
        if(!level.isClientSide() && state.getValues().containsKey(FACING)){
            for(BlockPos p : getStructureforState(level,state,pos)){
                if(level.getBlockState(p).getBlock() instanceof WeaponSmithPieceBlock){
                    level.removeBlock(p,false);

                }
            }

        }
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
        if(!level.isClientSide()){
        ((ServerLevel) level).sendParticles(ParticleTypes.POOF, pos.getX(),pos.getY(),pos.getZ(),50,0,0,0,1);
        }
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
    }
}
