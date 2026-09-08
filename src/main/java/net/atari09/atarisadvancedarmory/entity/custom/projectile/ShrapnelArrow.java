package net.atari09.atarisadvancedarmory.entity.custom.projectile;

import net.atari09.atarisadvancedarmory.entity.ModEntities;
import net.atari09.atarisadvancedarmory.entity.custom.BlockProjectileEntity;
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
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class ShrapnelArrow extends AbstractArrow {
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
        this.explode(this.level(),this);
        super.onHit(result);
        this.kill();
    }

    public void explode(Level level, ShrapnelArrow entity){
        level.explode(entity,this.damageSources().explosion(this.getOwner(),this), EXPLOSION_DAMAGE_CALCULATOR,entity.position(),1f,false, Level.ExplosionInteraction.NONE);
        //add shrapnel projectiles later here

        if(entity.getOwner() instanceof Player player) {
            int splinters = 12;
            for(int i = 0; i < splinters; i++){
                ShrapnelSplinterProjectile p = new ShrapnelSplinterProjectile(player,entity.level(),entity.getX(),entity.getY(),entity.getZ());
                p.setDeltaMovement(Math.sin(random.nextInt()*2*Math.PI),Math.sin(random.nextInt()*2*Math.PI),Math.sin(random.nextInt()*2*Math.PI));
                level.addFreshEntity(p);
            }
        } else {
            int splinters = 12;
            for(int i = 0; i < splinters; i++){
                ShrapnelSplinterProjectile p = new ShrapnelSplinterProjectile(entity.level(),entity.getX(),entity.getY(),entity.getZ());
                p.setDeltaMovement(Math.sin(random.nextInt()*2*Math.PI),Math.sin(random.nextInt()*2*Math.PI),Math.sin(random.nextInt()*2*Math.PI));
                level.addFreshEntity(p);
            }
        }




    }
}
