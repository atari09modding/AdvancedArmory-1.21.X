package net.atari09.atarisadvancedarmory.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.SimpleMapCodec;
import net.atari09.atarisadvancedarmory.block.entity.ModBlockEntities;
import net.atari09.atarisadvancedarmory.block.entity.WeaponSmithBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class WeaponSmithBaseBlock extends BaseEntityBlock {
    public MapCodec<WeaponSmithBaseBlock> CODEC = simpleCodec(WeaponSmithBaseBlock::new);
    public static final DirectionProperty FACING;

    public WeaponSmithBaseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)));
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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {

        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof WeaponSmithBlockEntity be) {
                be.drops();
                level.removeBlockEntity(pos);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
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



    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return false;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
    }
}
