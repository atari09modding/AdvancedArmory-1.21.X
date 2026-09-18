package net.atari09.atarisadvancedarmory.entity.custom.projectile;

import net.atari09.atarisadvancedarmory.entity.ModEntities;
import net.atari09.atarisadvancedarmory.mixin.EvokerFangsAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.level.Level;

public class IceSpikesEntity extends EvokerFangs {


    public IceSpikesEntity(EntityType<? extends EvokerFangs> entityType, Level level) {
        super(entityType, level);
    }

    public IceSpikesEntity(Level level, double x, double y, double z, float yRot, int warmupDelay, LivingEntity owner) {
        this(ModEntities.ICESPIKE.get(), level);
        ((EvokerFangsAccessor) this).setWarmupDelayTicks(warmupDelay);
        this.setOwner(owner);
        this.setYRot(yRot * (180.0F / (float)Math.PI));
        this.setPos(x, y, z);
    }



}
