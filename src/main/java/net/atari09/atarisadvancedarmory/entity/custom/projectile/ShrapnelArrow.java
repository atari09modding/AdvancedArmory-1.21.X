package net.atari09.atarisadvancedarmory.entity.custom.projectile;

import net.atari09.atarisadvancedarmory.entity.ModEntities;
import net.atari09.atarisadvancedarmory.entity.custom.BlockProjectileEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ShrapnelArrow extends CustomArrowEntity {
    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(true,true, Optional.of(2f),Optional.empty());

    public ShrapnelArrow(EntityType<ShrapnelArrow> entityType, Level level) {
        super(entityType, level);
    }

    public ShrapnelArrow(Player player, Level level, double x, double y, double z) {
        this(ModEntities.SHRAPNEL_ARROW.get(),player.level());
        this.setPos(new Vec3(x,y,z));
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
        if(result.getType().equals(HitResult.Type.BLOCK)){
            Direction dir = ((BlockHitResult) result).getDirection();
            this.explodeFromFace(this.level(),this,dir);

        } else {
            this.explode(this.level(),this);
        }

        super.onHit(result);
        this.kill();
    }


    private Vec3 randomHemisphereDirection(Vec3 normal, double speed) {
        double u = random.nextDouble();
        double v = random.nextDouble();
        double theta = 2 * Math.PI * u;
        double phi = Math.acos(1 - v); // v in [0,1] -> phi in [0, pi/2] => obere Halbkugel um (0,0,1)

        double x = Math.sin(phi) * Math.cos(theta);
        double y = Math.sin(phi) * Math.sin(theta);
        double z = Math.cos(phi);

        // lokale Halbkugel (0,0,1) auf die tatsächliche Normal-Richtung rotieren
        Vec3 up = Math.abs(normal.y) < 0.99 ? new Vec3(0, 1, 0) : new Vec3(1, 0, 0);
        Vec3 tangent = up.cross(normal).normalize();
        Vec3 bitangent = normal.cross(tangent);

        Vec3 dir = tangent.scale(x).add(bitangent.scale(y)).add(normal.scale(z));
        return dir.scale(speed);
    }

    public void explodeFromFace(Level level, ShrapnelArrow entity, Direction dir) {
        level.explode(entity, this.damageSources().explosion(this.getOwner(), this),
                EXPLOSION_DAMAGE_CALCULATOR, entity.position(), 1f, false, Level.ExplosionInteraction.NONE);
        Vec3 normal = new Vec3(dir.getStepX(), dir.getStepY(), dir.getStepZ()); // get normal to face
        Vec3 spawnPos = entity.position().add(normal.scale(0.3)); // move a bit away from block

        Player player = entity.getOwner() instanceof Player p ? p : null;
        int splinters = 12;
        for (int i = 0; i < splinters; i++) {
            ShrapnelSplinterProjectile splinter = player != null
                    ? new ShrapnelSplinterProjectile(player, level, spawnPos.x, spawnPos.y, spawnPos.z)
                    : new ShrapnelSplinterProjectile(level, spawnPos.x, spawnPos.y, spawnPos.z);
            splinter.setDeltaMovement(randomHemisphereDirection(normal, 1.2));
            level.addFreshEntity(splinter);
        }
    }



    public void explode(Level level, ShrapnelArrow entity){
        level.explode(entity,this.damageSources().explosion(this.getOwner(),this), EXPLOSION_DAMAGE_CALCULATOR,entity.position(),1f,false, Level.ExplosionInteraction.NONE);

        if(entity.getOwner() instanceof Player player) {
            int splinters = 12;
            for(int i = 0; i < splinters; i++){
                double speed = 0.6;
                double theta = random.nextDouble() * 2 * Math.PI;
                double phi = Math.acos(2 * random.nextDouble() - 1);
                double dx = Math.sin(phi) * Math.cos(theta) * speed;
                double dy = Math.cos(phi) * speed;
                double dz = Math.sin(phi) * Math.sin(theta) * speed;

                ShrapnelSplinterProjectile p = new ShrapnelSplinterProjectile(player, entity.level(), entity.getX(), entity.getY(), entity.getZ());
                p.setDeltaMovement(dx, dy, dz);
                level.addFreshEntity(p);
            }
        } else {
            int splinters = 12;
            for(int i = 0; i < splinters; i++){
                double speed = 0.6;
                double theta = random.nextDouble() * 2 * Math.PI;
                double phi = Math.acos(2 * random.nextDouble() - 1);
                double dx = Math.sin(phi) * Math.cos(theta) * speed;
                double dy = Math.cos(phi) * speed;
                double dz = Math.sin(phi) * Math.sin(theta) * speed;

                ShrapnelSplinterProjectile p = new ShrapnelSplinterProjectile(entity.level(), entity.getX(), entity.getY(), entity.getZ());
                p.setDeltaMovement(dx, dy, dz);
                level.addFreshEntity(p);
            }
        }




    }

    @Override
    public void setWeapon(@Nullable ItemStack stack) {
        this.
    }
}
