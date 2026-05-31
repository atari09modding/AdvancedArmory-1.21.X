package net.atari09.atarisadvancedarmory.block.custom;

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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class WeaponSmithPieceBlock extends Block {


    public WeaponSmithPieceBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }




    public void destroyParent(Level level,BlockPos pos, WeaponSmithBaseBlock block){
        BlockPos parentPos = getParentPos(level, pos);
        if(parentPos == null) return;

        block.destroyChildren(level,parentPos,level.getBlockState(pos));
        level.destroyBlock(parentPos, true);

    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if(level instanceof Level level1) {
            BlockPos parentPos = getParentPos(level1, pos);
            if (parentPos == null) return;
            destroyParent(level1, pos, ((WeaponSmithBaseBlock) level.getBlockState(parentPos).getBlock()));
        }
    }


    public BlockPos getParentPos(Level level, BlockPos ownPos){
        if (!level.isClientSide()) {
            for (Direction d : Direction.values()) {
                BlockPos check = ownPos.relative(d);

                if (level.getBlockState(check).getBlock() instanceof WeaponSmithBaseBlock block) {
                    if(block.getStructure(level,check).contains(ownPos)){
                        return check;


                    }
                }
                for(Direction d2 : Direction.values()){
                    BlockPos check2 = check.relative(d2);
                    if (level.getBlockState(check2).getBlock() instanceof WeaponSmithBaseBlock block) {
                        if(block.getStructure(level,check2).contains(ownPos)){
                            return check2;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return false;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockPos parentPos = getParentPos(level,pos);
        if(!level.isClientSide() && parentPos != null){
            if (level.getBlockState(parentPos).getBlock() instanceof WeaponSmithBaseBlock block){
                block.openMenu(level, parentPos, player);
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockPos parentPos = getParentPos(level,pos);
        if(!level.isClientSide() && parentPos != null){
            if (level.getBlockState(parentPos).getBlock() instanceof WeaponSmithBaseBlock block){
                block.openMenu(level, parentPos, player);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

}
