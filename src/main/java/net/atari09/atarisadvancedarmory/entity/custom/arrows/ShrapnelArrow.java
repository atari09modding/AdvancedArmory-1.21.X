package net.atari09.atarisadvancedarmory.entity.custom.arrows;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.phys.HitResult;

import java.util.Optional;

public class ShrapnelArrow extends AbstractArrow {
    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(true,true, Optional.of(2f),Optional.empty());

    public ShrapnelArrow(EntityType<ShrapnelArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    public void tick() {
        super.tick();

        if(this.onGround()) {
            this.kill();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        this.explode(this.level(),this);
        super.onHit(result);
        this.kill();
    }

    public void explode(Level level, ShrapnelArrow entity){
        level.explode(entity,this.damageSources().explosion(this.getOwner(),this), EXPLOSION_DAMAGE_CALCULATOR,entity.position(),1f,false, Level.ExplosionInteraction.NONE);
        //add shrapnel projectiles later here
    }
}
