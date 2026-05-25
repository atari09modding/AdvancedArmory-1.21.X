package net.atari09.atarisadvancedarmory.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;

import java.util.Properties;

public class WeaponSmithBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public WeaponSmithBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WEAPONSMITHBLOCK_BE.get(),pos,state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",20, this::predicate));
    }

    private PlayState predicate(AnimationState<WeaponSmithBlockEntity> state) {
        return state.setAndContinue( isWorking() ? RawAnimation.begin().thenLoop("animation_smithing") : RawAnimation.begin().thenLoop("animation_idle"));
    }

    public void tick(Level level, BlockPos pos, BlockState blockState){

    }



    public boolean isWorking(){
        return false;
    }



    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public void drops() {
    }
}
