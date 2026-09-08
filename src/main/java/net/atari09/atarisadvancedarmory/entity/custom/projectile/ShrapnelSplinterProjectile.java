package net.atari09.atarisadvancedarmory.entity.custom.projectile;

import net.atari09.atarisadvancedarmory.entity.ModEntities;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ShrapnelSplinterProjectile extends Projectile {

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

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

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
        if(this.getOwner() instanceof Player owner){
            result.getEntity().hurt(this.damageSources().playerAttack(owner),damage);
        }
    }


    // still crashes cuz no renderer
}
