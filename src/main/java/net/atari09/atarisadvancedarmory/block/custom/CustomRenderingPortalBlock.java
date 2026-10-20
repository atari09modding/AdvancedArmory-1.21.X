package net.atari09.atarisadvancedarmory.block.custom;

import com.mojang.serialization.MapCodec;
import net.atari09.atarisadvancedarmory.block.entity.CustomRenderingPortalBlockEntity;
import net.atari09.atarisadvancedarmory.block.entity.ModBlockEntities;
import net.atari09.atarisadvancedarmory.block.entity.renderer.CustomPortalRenderConsumers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CustomRenderingPortalBlock extends BaseEntityBlock {

    public static final MapCodec<CustomRenderingPortalBlock> CODEC = simpleCodec(CustomRenderingPortalBlock::new);
    public static final DirectionProperty FACING;
    public final CustomPortalRenderConsumers.CustomPortalRenderConsumer portalRenderConsumer;

    private CustomRenderingPortalBlock(Properties properties) {
        this(properties,CustomPortalRenderConsumers.DEBUG);
    }


    public CustomRenderingPortalBlock(Properties properties, CustomPortalRenderConsumers.CustomPortalRenderConsumer render) {
        super(properties);
        this.portalRenderConsumer = render;
    }


    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomRenderingPortalBlockEntity(pos,state);
    }


    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();

        return this.defaultBlockState().setValue(FACING, direction.getOpposite());
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
    }
}
