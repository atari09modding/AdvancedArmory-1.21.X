package net.atari09.atarisadvancedarmory.entity.custom;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;


public class BlockProjectileEntity extends WindCharge {
    private BlockState state;
    private static ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(true,true,Optional.of(2f),Optional.empty());

    public BlockProjectileEntity(Player player, Level level, double x, double y, double z) {
        super(player, level, x, y, z);
    }

    public BlockProjectileEntity(EntityType<BlockProjectileEntity> t, Level level) {
        super(t,level);
        this.state = Blocks.DIRT.defaultBlockState();
    }


    @Override
    protected void explode(Vec3 pos) {
        this.level().explode(this,this.damageSources().generic(),EXPLOSION_DAMAGE_CALCULATOR,pos,5f,false, Level.ExplosionInteraction.BLOCK);
    }

    public BlockState getState(){
        return state;
    }

}
