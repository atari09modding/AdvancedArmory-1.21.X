package net.atari09.atarisadvancedarmory.item.custom.arrow;

import net.atari09.atarisadvancedarmory.entity.custom.projectile.CustomArrowEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.atari09.atarisadvancedarmory.component.ModifiableArrow;

import java.util.function.Supplier;

public class CustomArrow<T extends CustomArrowEntity> extends ArrowItem {

    private final Supplier<EntityType<T>> arrow;
    public CustomArrow(Properties properties, Supplier<EntityType<T>> arrow) {
        super(properties);
        this.arrow = arrow;
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        CustomArrowEntity a = arrow.get().create(level);
        a.setOwner(shooter);
        a.setWeapon(weapon);  NOT_WORKING_YET!!!;
        return a;
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        AbstractArrow a = arrow.get().create(level);
        a.setPos(pos.x(), pos.y(), pos.z());
        return a;
    }
}
