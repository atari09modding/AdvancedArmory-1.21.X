package net.atari09.atarisadvancedarmory.entity.custom.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.phys.HitResult;

import java.util.Optional;

public class ExplosiveArrow extends AbstractArrow {
    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(true,true, Optional.of(2f),Optional.empty());

    public ExplosiveArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        this.level().explode(this,this.damageSources().explosion(this.getOwner(),this),EXPLOSION_DAMAGE_CALCULATOR,this.position(),5f,false, Level.ExplosionInteraction.TNT);
        this.discard();
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
