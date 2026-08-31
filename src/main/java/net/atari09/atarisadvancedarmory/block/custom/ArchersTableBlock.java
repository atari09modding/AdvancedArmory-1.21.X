package net.atari09.atarisadvancedarmory.block.custom;

import com.mojang.serialization.MapCodec;
import net.atari09.atarisadvancedarmory.block.entity.ArchersTableBlockEntity;
import net.atari09.atarisadvancedarmory.block.entity.ModBlockEntities;
import net.atari09.atarisadvancedarmory.block.entity.WeaponSmithBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ArchersTableBlock extends BaseEntityBlock {
    public MapCodec<ArchersTableBlock> CODEC = simpleCodec(ArchersTableBlock::new);

    public ArchersTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ArchersTableBlockEntity(blockPos,blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()){
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlockEntities.ARCHERSTABLE_BE.get(),
                (level1,blockPos,blockState, blockEntity)->blockEntity.tick(level1,blockPos,blockState));
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide()){
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity instanceof ArchersTableBlockEntity be){
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(be, be.getDisplayName()),pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing");
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide()){
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity instanceof ArchersTableBlockEntity be){
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(be, be.getDisplayName()),pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing");
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

}
