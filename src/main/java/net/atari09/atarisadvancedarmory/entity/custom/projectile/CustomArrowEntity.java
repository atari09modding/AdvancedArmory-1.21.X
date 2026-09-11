package net.atari09.atarisadvancedarmory.entity.custom.projectile;

import net.atari09.atarisadvancedarmory.component.ModifiableArrow;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;

public abstract class CustomArrowEntity extends AbstractArrow implements ModifiableArrow {

    protected CustomArrowEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }
}
