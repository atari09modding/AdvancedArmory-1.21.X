package net.atari09.atarisadvancedarmory.entity.custom;


import net.atari09.atarisadvancedarmory.entity.ModEntities;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;


public class BlockProjectileEntity extends WindCharge {
    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(true,true,Optional.of(2f),Optional.empty());
    private Vec3 ipPos = null;
    private static final EntityDataAccessor<BlockState> STATE = SynchedEntityData.defineId(BlockProjectileEntity.class, EntityDataSerializers.BLOCK_STATE);
    private final double size;

    public BlockProjectileEntity(Player player, Level level, double x, double y, double z, BlockState state) {
        this(ModEntities.BLOCK_PROJECTILE_ENTITY.get(),player.level(),state,1);
        this.setPos(new Vec3(x,y,z));
    }

    public BlockProjectileEntity(Level level, double x, double y, double z, BlockState state, double size) {
        this(ModEntities.BLOCK_PROJECTILE_ENTITY.get(),level,state,size);
        this.setPos(new Vec3(x,y,z));
    }

    public BlockProjectileEntity(EntityType<BlockProjectileEntity> t, Level level, BlockState state, double size) {
        super(t,level);
        this.entityData.set(STATE,state);
        this.size = size;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE,Blocks.DIRT.defaultBlockState());
    }

    @Override
    protected void explode(Vec3 pos) {
        this.level().explode(this,this.damageSources().generic(),EXPLOSION_DAMAGE_CALCULATOR,pos,2f * (float)size,false, Level.ExplosionInteraction.BLOCK);
    }

    public BlockState getState(){
        return this.entityData.get(STATE);
    }

    public void setState(BlockState newState){
        this.entityData.set(STATE,newState);
    }

    @Override
    public boolean deflect(ProjectileDeflection deflection, @Nullable Entity entity, @Nullable Entity owner, boolean deflectedByPlayer) {
        ipPos = null;
        if(this.getState() == Blocks.GRAVEL.defaultBlockState() || this.getState() == Blocks.SAND.defaultBlockState()){
            double size = this.getSize()/2d;
            if(size > 0.2){
                for (int i = 0; i<4;i++){
                    BlockProjectileEntity entity1 = new BlockProjectileEntity(this.level(),this.getX(),this.getY(),this.getZ(),this.getState(),size);
                    this.level().addFreshEntity(entity1);

                    ProjectileDeflection deflection1 = (a,b,c)->{
                        deflection.deflect(a,b,c);
                        a.addDeltaMovement(new Vec3(c.nextInt(-200,200)*0.01d,c.nextInt(-200,200)*0.01d, c.nextInt(-200,200)*0.01d));
                    };
                    entity1.deflectWithoutSplit(deflection1,entity,owner,deflectedByPlayer);
                }
            }
            this.kill();
        }
        return super.deflect(deflection, entity, owner, deflectedByPlayer);
    }

    public void deflectWithoutSplit(ProjectileDeflection deflection, @Nullable Entity entity, @Nullable Entity owner, boolean deflectedByPlayer){
        super.deflect(deflection, entity, owner, deflectedByPlayer);
    }

    public double getSize() {
        return size;
    }

    @Override
    public void tick() {
        super.tick();
        if(ipPos!=null){
            setDeltaMovement(ipPos.subtract(this.position()).scale(0.1));
        }
    }

    public void interpolateTowardsPos(Vec3 pos){
        ipPos = pos;
    }
}
