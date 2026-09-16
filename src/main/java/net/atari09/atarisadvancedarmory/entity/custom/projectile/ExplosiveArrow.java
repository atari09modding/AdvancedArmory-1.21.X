package net.atari09.atarisadvancedarmory.entity.custom.projectile;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;

public class ExplosiveArrow extends AbstractArrow {
    @Override
    protected ItemStack getDefaultPickupItem() {
        return null;
    }
}
