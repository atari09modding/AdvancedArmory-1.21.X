package net.atari09.atarisadvancedarmory.item.util;

import com.mojang.serialization.Codec;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.effect.ModEffects;
import net.atari09.atarisadvancedarmory.network.payload.ScreenShakePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;

public enum ElementalVariant {
    INFERNAL(0, ElementalVariant::infernal1,ElementalVariant::infernal2,ElementalVariant::infernal3,80),
    KRYONIC(1, ElementalVariant::kryonic1,ElementalVariant::kryonic2,ElementalVariant::kryonic3,300),
    NOXIOUS(2, ElementalVariant::noxious1,ElementalVariant::noxious2,ElementalVariant::noxious3,200),
    ABYSSAL(3, ElementalVariant::abyssal1,ElementalVariant::abyssal2,ElementalVariant::abyssal3,200),
    AERIAL(4, ElementalVariant::aerial1,ElementalVariant::aerial2,ElementalVariant::aerial3,80),
    TERRESTRIAL(5, ElementalVariant::terrestrial1,ElementalVariant::terrestrial2,ElementalVariant::terrestrial3,200),
    AQUATIC(6, ElementalVariant::infernal1,ElementalVariant::infernal2,ElementalVariant::infernal3,200),
    CHRONAL(7, ElementalVariant::infernal1,ElementalVariant::infernal2,ElementalVariant::infernal3,200);

    public int id;
    public static final Codec<ElementalVariant> CODEC =
            Codec.INT.xmap(ElementalVariant::fromId, ElementalVariant::getId);
    public static final StreamCodec<RegistryFriendlyByteBuf, ElementalVariant> STREAM_CODEC =
            StreamCodec.of((buf, variant) -> buf.writeVarInt(variant.getId()),
                    buf -> fromId(buf.readVarInt()));
    public final AbilityLv1Executor abilitylv1;
    public final AbilityLv2Executor abilitylv2;
    public final AbilityLv3Executor abilitylv3;
    public int abilityCooldown;


    ElementalVariant(int id, AbilityLv1Executor abilitylv1, AbilityLv2Executor abilityLv2, AbilityLv3Executor abilityLv3, int abilityCooldown){
        this.id = id;
        this.abilitylv1 = abilitylv1;
        this.abilitylv2 = abilityLv2;
        this.abilitylv3 = abilityLv3;
        this.abilityCooldown = abilityCooldown;

    }

    public static void terrestrial1(ItemStack stack, LivingEntity target, LivingEntity attacker){
        if(target instanceof ServerPlayer player){
            PacketDistributor.sendToPlayer(player,new ScreenShakePacket(50,5));
        }
        if(attacker instanceof ServerPlayer player){
            PacketDistributor.sendToPlayer(player,new ScreenShakePacket(50,5));
        }
    }

    public static void terrestrial2(Level level, Player player, InteractionHand usedHand){

    }

    public static void terrestrial3(ItemStack stack, LivingEntity target, LivingEntity attacker){
        float damage = (float) ((1-(attacker.position().y/attacker.level().getMaxBuildHeight()))*20);
        target.hurt(target.damageSources().generic(),damage);
    }

    public static void aerial1(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.knockback(3,attacker.getX()-target.getX(),attacker.getZ()-target.getZ());
    }

    public static void aerial2(Level level, Player player, InteractionHand usedHand){
        Vec3 pos = player.position();
        WindCharge charge = new WindCharge(player,level,pos.x,pos.y+1.5,pos.z);
        charge.setDeltaMovement(player.getLookAngle());
        level.addFreshEntity(charge);
    }

    public static void aerial3(ItemStack stack, LivingEntity target, LivingEntity attacker){
        //have not had a good idea yet
    }

    public static void abyssal1(ItemStack stack, LivingEntity target, LivingEntity attacker){
        if (stack.has(ModDataComponents.ELEMENTAL_LEVEL.get())) {
            target.addEffect(new MobEffectInstance(ModEffects.VOID_EFFECT,40));//change this later to VOID effect

        }
    }

    public static void abyssal2(Level level, Player player, InteractionHand usedHand){
        int x = player.getBlockX();
        int y = player.getBlockY();
        int z = player.getBlockZ();
        List<Entity> entities =  level.getEntities(player,new AABB(x-5,y-2,z-5,x+5,y+2,z+5));
        for(Entity entity : entities){
            if(entity instanceof LivingEntity living){
                living.addEffect(new MobEffectInstance(MobEffects.DARKNESS,120,2));
                living.addEffect(new MobEffectInstance(ModEffects.VOID_EFFECT,120,2));//void
            }
        }
    }

    public static void abyssal3(ItemStack stack, LivingEntity target, LivingEntity attacker){
        if(target.hasEffect(ModEffects.VOID_EFFECT)){
            stack.set(ModDataComponents.DO_CRIT,true);

        }

    }

    public static void noxious1(ItemStack stack, LivingEntity target, LivingEntity attacker){
        if (stack.has(ModDataComponents.ELEMENTAL_LEVEL.get())) {
            if (! (stack.get(ModDataComponents.ELEMENTAL_LEVEL.get()) >= 3)){
                target.addEffect(new MobEffectInstance(MobEffects.POISON,40));

            }
        }
    }

    public static void noxious2(Level level, Player player, InteractionHand usedHand){
        int x = player.getBlockX();
        int y = player.getBlockY();
        int z = player.getBlockZ();
        List<Entity> entities =  level.getEntities(player,new AABB(x-5,y-2,z-5,x+5,y+2,z+5));
        for(Entity entity : entities){
            if(entity instanceof LivingEntity living){
                living.addEffect(new MobEffectInstance(MobEffects.POISON,120,2));
            }
        }
    }

    public static void noxious3(ItemStack stack, LivingEntity target, LivingEntity attacker){
        int i = ThreadLocalRandom.current().nextInt(0,5);
        if(i == 0){
            target.addEffect(new MobEffectInstance(MobEffects.WITHER,50));
        } else{
            target.addEffect(new MobEffectInstance(MobEffects.POISON,50));
        }

    }

    public static void kryonic1(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.addEffect(new MobEffectInstance(ModEffects.FREEZE_EFFECT,40));
    }

    public static void kryonic2(Level level, Player player, InteractionHand usedHand){
        if(!level.isClientSide()){
            Vec3 pos = player.position();
            double y = pos.y;

            for (int radius = 3; radius<7; radius++){
                for (double angle = 0 ;angle<Math.PI; angle+=Math.PI/8){
                    double angle_use = angle + Math.toRadians(player.yHeadRot);
                    double x = player.position().x + Math.cos(angle_use)*radius;
                    double z = player.position().z + Math.sin(angle_use)*radius;
                    Vec3 pos1 = new Vec3(x,y,z);
                    boolean blocked = level.getBlockState(BlockPos.containing(pos1.x,pos1.y,pos1.z)).canBeReplaced();
                    boolean floating = level.getBlockState(BlockPos.containing(pos1.x,pos1.y,pos1.z).below()).canBeReplaced();
                    int i = 0;
                    BlockPos possiblepos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING,BlockPos.containing(pos1.x,pos1.y,pos1.z));

                    while(!(pos.y >= possiblepos.getY())){
                        if(blocked){
                            pos1 = pos1.add(0,1,0);

                        } else if (floating) {
                            pos1 = pos1.add(0,-1,0);

                        }
                        blocked = level.getBlockState(BlockPos.containing(pos1.x,pos1.y,pos1.z)).canBeReplaced();
                        floating = level.getBlockState(BlockPos.containing(pos1.x,pos1.y,pos1.z).below()).canBeReplaced();
                        if(!(blocked||floating) ||i>20)break;
                        i++;
                    }
                    Entity ice = new EvokerFangs(level,x,pos.y,z,((float) angle_use),(radius-3)*5,player);
                    level.addFreshEntity(ice);
                }
            }

        }
    }

    public static void kryonic3(ItemStack stack, LivingEntity target, LivingEntity attacker){
        if(target.hasEffect(ModEffects.FREEZE_EFFECT)){
            target.hurt(target.damageSources().generic(),2f);
        }
    }

    public static void infernal1(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.igniteForTicks(40);
        Level level = target.level();
        if(!level.isClientSide()){
            ((ServerLevel) level).sendParticles(ParticleTypes.FLAME,target.getX(),target.getY(),target.getZ(),200,0,0,0,2);
        }

    }

    public static void infernal2(Level level, Player player, InteractionHand usedHand){
        SmallFireball charge = new SmallFireball(level,player,player.getLookAngle());
        charge.moveTo(player.position().add(0,1.5,0));
        level.addFreshEntity(charge);
    }

    public static void infernal3(ItemStack stack, LivingEntity target, LivingEntity attacker){

       if(target.getEffect(MobEffects.FIRE_RESISTANCE).getDuration()>0){
           target.removeEffect(MobEffects.FIRE_RESISTANCE);
       } else if (target.isOnFire()){
           target.hurt(target.damageSources().generic(),1f);
       }

    }

    public int getId() {
        return id;
    }

    public static ElementalVariant fromId(int id) {
        for (ElementalVariant variant : values()) {
            if (variant.id == id) {
                return variant;
            }
        }
        throw new IllegalArgumentException("Unknown variant id: " + id);
    }

    @FunctionalInterface
    public interface AbilityLv1Executor{
        void accept(ItemStack stack, LivingEntity target, LivingEntity attacker);
    }

    @FunctionalInterface
    public interface AbilityLv2Executor{
        void accept(Level level, Player player, InteractionHand usedHand);
    }

    @FunctionalInterface
    public interface AbilityLv3Executor{
        void accept(ItemStack stack, LivingEntity target, LivingEntity attacker);
    }
}
