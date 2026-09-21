package net.atari09.atarisadvancedarmory.block.custom;

import net.atari09.atarisadvancedarmory.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class PortalBlock extends Block {

    private final ResourceKey<Level> key;

    public PortalBlock(Properties properties, ResourceKey<Level> key) {
        super(properties);
        this.key = key;
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.FAIL;
        }
        ServerLevel targetLevel = serverPlayer.server.getLevel(key);
        if (targetLevel ==null){
            return InteractionResult.FAIL;
        }
        if (player.canChangeDimensions(level,targetLevel)) {

            BlockPos destinationPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

            int tries = 0;
            while ((targetLevel.getBlockState(destinationPos).getBlock() != Blocks.AIR) &&
                    !targetLevel.getBlockState(destinationPos).canBeReplaced(Fluids.WATER) &&
                    (targetLevel.getBlockState(destinationPos.above()).getBlock()  != Blocks.AIR) &&
                    !targetLevel.getBlockState(destinationPos.above()).canBeReplaced(Fluids.WATER) && (tries < 25)) {
                destinationPos = destinationPos.above(2);
                tries++;
            }

            Vec3 targetPos = new Vec3(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());
            boolean doSetBlock = true;
            for (BlockPos checkPos : BlockPos.betweenClosed(destinationPos.below(10).west(10), destinationPos.above(10).east(10))) {
                if (targetLevel.getBlockState(checkPos).getBlock() instanceof PortalBlock) {
                    doSetBlock = false;
                    break;
                }
            }
            if (doSetBlock) {
                targetLevel.setBlock(destinationPos, this.defaultBlockState(), 3);
            }


            // Perform teleport
            serverPlayer.changeDimension(new DimensionTransition(targetLevel, targetPos, Vec3.ZERO, serverPlayer.getYRot(),
                    serverPlayer.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND));

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return ItemInteractionResult.FAIL;
        }
        ServerLevel targetLevel = serverPlayer.server.getLevel(key);
        if (targetLevel ==null){
            return ItemInteractionResult.FAIL;
        }
        if (player.canChangeDimensions(level,targetLevel)) {

            BlockPos destinationPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

            int tries = 0;
            while ((targetLevel.getBlockState(destinationPos).getBlock() != Blocks.AIR) &&
                    !targetLevel.getBlockState(destinationPos).canBeReplaced(Fluids.WATER) &&
                    (targetLevel.getBlockState(destinationPos.above()).getBlock()  != Blocks.AIR) &&
                    !targetLevel.getBlockState(destinationPos.above()).canBeReplaced(Fluids.WATER) && (tries < 25)) {
                destinationPos = destinationPos.above(2);
                tries++;
            }

            Vec3 targetPos = new Vec3(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());
            boolean doSetBlock = true;
            for (BlockPos checkPos : BlockPos.betweenClosed(destinationPos.below(10).west(10), destinationPos.above(10).east(10))) {
                if (targetLevel.getBlockState(checkPos).getBlock() instanceof PortalBlock) {
                    doSetBlock = false;
                    break;
                }
            }
            if (doSetBlock) {
                targetLevel.setBlock(destinationPos, this.defaultBlockState(), 3);
            }


            // Perform teleport
            serverPlayer.changeDimension(new DimensionTransition(targetLevel, targetPos, Vec3.ZERO, serverPlayer.getYRot(),
                    serverPlayer.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND));

            return ItemInteractionResult.SUCCESS;
        } else {
            return ItemInteractionResult.CONSUME;
        }
    }


}
