package net.atari09.atarisadvancedarmory.entity.custom;

import net.atari09.atarisadvancedarmory.entity.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.windcharge.AbstractWindCharge;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BlockProjectileEntity extends WindCharge {
    private BlockState state;

    public BlockProjectileEntity(Player player, Level level, double x, double y, double z) {
        super(player, level, x, y, z);
    }


    @Override
    protected void explode(Vec3 pos) {
        this.level().explode(this,pos.x,pos.x,pos.z,5f, Level.ExplosionInteraction.BLOCK);
    }


}
