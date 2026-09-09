package net.atari09.atarisadvancedarmory.entity.custom.projectile;

import net.atari09.atarisadvancedarmory.entity.ModEntities;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ShrapnelSplinterProjectile extends AbstractArrow {

    private float damage;
    public ShrapnelSplinterProjectile(EntityType<ShrapnelSplinterProjectile> entityType, Level level) {
        super(entityType, level);
        this.damage = 2;
    }

    public ShrapnelSplinterProjectile(Player player, Level level, double x, double y, double z) {
        this(ModEntities.SHRAPNEL_SPLINTER.get(),level);
        this.setPos(new Vec3(x,y,z));
        this.setOwner(player);
    }

    public ShrapnelSplinterProjectile(Level level, double x, double y, double z) {
        this(ModEntities.SHRAPNEL_SPLINTER.get(),level);
        this.setPos(new Vec3(x,y,z));
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if(this.getOwner() instanceof Entity owner){
            result.getEntity().hurt(this.damageSources().arrow(this,owner),damage);
        } else {
            result.getEntity().hurt(this.damageSources().arrow(this,null),damage);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        this.kill();
    }

    @Override
    public void tick() {
        super.tick();

        if(this.onGround()){
            this.kill();
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.IRON_NUGGET);
    }



}
