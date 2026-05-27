package net.atari09.atarisadvancedarmory.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class WeaponSmithPieceBlock extends Block {


    public WeaponSmithPieceBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }


    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if (!level.isClientSide()) {
            // find nearby master and break it
            for (Direction d : Direction.values()) {
                BlockPos check = pos.relative(d);

                if (level.getBlockState(check).getBlock() instanceof WeaponSmithBaseBlock block) {
                    destroyParentwithCheck(level,check,pos,block);
                }
                for(Direction d2 : Direction.values()){
                    BlockPos check2 = check.relative(d2);
                    if (level.getBlockState(check).getBlock() instanceof WeaponSmithBaseBlock block) {
                        destroyParentwithCheck(level,check2,pos,block);
                    }
                }
            }
        }
    }

    public void destroyParentwithCheck(Level level,BlockPos parentPos,BlockPos ownPos, WeaponSmithBaseBlock block){
        if(block.getStructure(level,parentPos).contains(ownPos)){
            block.destroyChildren(level,parentPos);
            level.destroyBlock(parentPos, true);

        }

    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return false;
    }
}
