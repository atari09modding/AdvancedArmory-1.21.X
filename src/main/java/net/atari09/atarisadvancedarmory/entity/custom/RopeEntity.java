package net.atari09.atarisadvancedarmory.entity.custom;

import net.atari09.atarisadvancedarmory.entity.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.UUID;

public class RopeEntity extends Entity {
    private static final EntityDataAccessor<Integer> DATA_ENTITY1_ID =
            SynchedEntityData.defineId(RopeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ENTITY2_ID =
            SynchedEntityData.defineId(RopeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> LENGTH =
            SynchedEntityData.defineId(RopeEntity.class, EntityDataSerializers.FLOAT);

    // Für NBT-Persistenz (Entity-IDs überleben keinen Reload)
    @Nullable
    private UUID stationaryEntityUuid;
    @Nullable private UUID nonStationaryEntityUuid;
    private float length;
    private Vec3 ropeDeltaMovementForce = new Vec3(0,0,0);


    public RopeEntity(Level level, Entity stationaryEntity, Entity nonStationaryEntity, float length) {
        this(ModEntities.ROPE.get(), level);
        this.setStationaryEntity(stationaryEntity);
        this.setNonStationaryEntity(nonStationaryEntity);
        this.setLength(length);
    }

    public RopeEntity(EntityType<RopeEntity> ropeEntityEntityType, Level level) {
        super(ropeEntityEntityType,level);
    }

    public void setStationaryEntity(Entity entity) {
        this.stationaryEntityUuid = entity.getUUID();
        this.entityData.set(DATA_ENTITY1_ID, entity.getId());
    }

    public void setNonStationaryEntity(Entity entity) {
        this.nonStationaryEntityUuid = entity.getUUID();
        this.entityData.set(DATA_ENTITY2_ID, entity.getId());
    }

    public void setLength(float len){
        this.length = len;
        this.entityData.set(LENGTH,len);
    }

    @Nullable
    public Entity getStationaryEntity() {
        return this.level().getEntity(this.entityData.get(DATA_ENTITY1_ID));
    }

    @Nullable
    public Entity getNonStationaryEntity() {
        return this.level().getEntity(this.entityData.get(DATA_ENTITY2_ID));
    }

    public float getLength(){
        return this.entityData.get(LENGTH);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ENTITY1_ID, -1);
        builder.define(DATA_ENTITY2_ID, -1);
        builder.define(LENGTH, 1f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.hasUUID("Entity1UUID")) {
            this.stationaryEntityUuid = compound.getUUID("Entity1UUID");
        }
        if (compound.hasUUID("Entity2UUID")) {
            this.nonStationaryEntityUuid = compound.getUUID("Entity2UUID");
        }

        setLength(compound.getFloat("length"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        if (this.stationaryEntityUuid != null) compound.putUUID("Entity1UUID", this.stationaryEntityUuid);
        if (this.nonStationaryEntityUuid != null) compound.putUUID("Entity2UUID", this.nonStationaryEntityUuid);
        compound.putFloat("length", this.length);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.entityData.get(DATA_ENTITY1_ID) == -1 && this.stationaryEntityUuid != null
                && this.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            Entity found = serverLevel.getEntity(this.stationaryEntityUuid);
            if (found != null) this.entityData.set(DATA_ENTITY1_ID, found.getId());
        }
        if (this.entityData.get(DATA_ENTITY2_ID) == -1 && this.nonStationaryEntityUuid != null
                && this.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            Entity found = serverLevel.getEntity(this.nonStationaryEntityUuid);
            if (found != null) this.entityData.set(DATA_ENTITY2_ID, found.getId());
        }

        if (this.getStationaryEntity() == null || this.getNonStationaryEntity() == null) {
            this.discard();
            return;
        }

        float distance = this.getStationaryEntity().distanceTo(this.getNonStationaryEntity());
        if(distance >= length) pull(distance);

        if(this.getNonStationaryEntity() instanceof Player player){
            if(player.isShiftKeyDown()){
                this.discard();
            }
        }

    }



    public void pull(float distance){
        Entity eStationary = this.getStationaryEntity();
        Entity eNonStationary = this.getNonStationaryEntity();
        assert eNonStationary != null;
        double gravity = -eNonStationary.getGravity()*0.98;

        if(length <0.1) return;
        assert eStationary != null;

        Vec3 velocity = eNonStationary.getDeltaMovement();
        eNonStationary.setDeltaMovement(clampMovementIntoCircle(eStationary.position(),velocity,eNonStationary.position(),length));


        eNonStationary.hasImpulse = true;

        if (eNonStationary instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
        }

    }

    private Vec3 clampMovementIntoCircle(Vec3 center, Vec3 curr, Vec3 playerPos, float radius){
        if(center.subtract(playerPos.add(curr)).length() < radius) return curr;
        Vec3 direction = playerPos.add(curr).subtract(center).normalize();
        return direction.scale(radius);
    }
}
