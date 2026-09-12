package net.atari09.atarisadvancedarmory.entity.custom.projectile;

import net.atari09.atarisadvancedarmory.item.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class RopeArrow extends AbstractArrow {
    public RopeArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.ROPE_ARROW.get());
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Entity owner = this.getOwner();
        if(owner instanceof Leashable l){
            l.setLeashedTo(this,true);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity owner = this.getOwner();
        Entity target = result.getEntity();
        if(target instanceof Leashable l){
            l.setLeashedTo(owner,true);
        }
    }
}
