package net.atari09.atarisadvancedarmory.entity.custom.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SmokeArrow extends AbstractArrow {
    public SmokeArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    private int smoketime = 20;
    private boolean hit = false;


    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        Vec3 pos = result.getLocation();
        if (!this.level().isClientSide()) {
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, pos.x, pos.y, pos.z, 100, 0d, 0d, 0d, 0.05d);
        }
        hit = true;
    }

    @Override
    public void tick() {
        super.tick();
        if(smoketime <=0) this.kill();
        if(!level().isClientSide){
            if(hit) this.smoketime --;
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, this.position().x, this.position().y, this.position().z, 50, 0d, 0d, 0d, 0.05d);
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, this.position().x, this.position().y, this.position().z, 100, 0d, 0d, 0d, random.nextDouble()*0.05d);
        }
    }
}
