package net.atari09.atarisadvancedarmory.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CustomRenderingPortalBlockEntity extends BlockEntity {
    public CustomRenderingPortalBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CUSTOMRENDERINGPORTALBLOCK_BE.get(), pos, blockState);
    }
}
