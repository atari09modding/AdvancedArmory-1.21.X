package net.atari09.atarisadvancedarmory.entity.custom.projectile;

import com.google.common.base.MoreObjects;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ShulkerArrow extends AbstractArrow {
    public ShulkerArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.SHULKER_ARROW.get());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.END_ROD, this.getX() - this.getDeltaMovement().x, this.getY() - this.getDeltaMovement().y + 0.15, this.getZ() - this.getDeltaMovement().z, 0.0, 0.0, 0.0);
        } else {
            pointAtTarget();
        }
    }

    private void pointAtTarget(){
        Entity target = chooseTarget();
        if(target!=null){
            Vec3 tm = target.position().subtract(this.position());
            //this.lookAt(EntityAnchorArgument.Anchor.EYES,tm);
            this.setDeltaMovement(tm.normalize());

        }
    }

    private Entity chooseTarget(){
        Entity owner = this.getOwner();
        Level level = this.level();
        Vec3 v = new Vec3(10,10,10);

        List<Entity> targets = level.getEntities(owner,new AABB(this.position().subtract(v),this.position().add(v)), entity -> entity instanceof LivingEntity);
        Entity closest = null;
        for(Entity target : targets){
            if(closest!=null){
                if(this.distanceTo(target)<this.distanceTo(closest)){
                    closest = target;
                    continue;
                }
            }
            closest = target;
        }
        return closest;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        Entity entity1 = this.getOwner();
        LivingEntity livingentity = entity1 instanceof LivingEntity ? (LivingEntity)entity1 : null;
        DamageSource damagesource = this.damageSources().mobProjectile(this, livingentity);
        boolean flag = entity.hurt(damagesource, 4.0F);
        if (flag) {
            if (this.level() instanceof ServerLevel serverlevel) {
                EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
            }

            if (entity instanceof LivingEntity livingentity1) {
                livingentity1.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200), MoreObjects.firstNonNull(entity1, this));
            }
        }
    }
}
