package net.atari09.atarisadvancedarmory.block.client;

import net.atari09.atarisadvancedarmory.block.entity.WeaponSmithBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class WeaponSmithBaseBlockRenderer extends GeoBlockRenderer<WeaponSmithBlockEntity> {
    public WeaponSmithBaseBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new WeaponSmithBaseBlockModel());
    }

    @Override
    public AABB getRenderBoundingBox(WeaponSmithBlockEntity blockEntity) {
        return super.getRenderBoundingBox(blockEntity).inflate(5d);
    }
}
