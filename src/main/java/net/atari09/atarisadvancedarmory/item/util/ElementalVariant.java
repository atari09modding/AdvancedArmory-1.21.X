package net.atari09.atarisadvancedarmory.item.util;

import com.mojang.serialization.Codec;
import net.atari09.atarisadvancedarmory.effect.ModEffects;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public enum ElementalVariant {
    INFERNAL(0, ElementalVariant::infernal1,ElementalVariant::infernal2,ElementalVariant::infernal3),
    KRYONIC(1, ElementalVariant::kryonic1,ElementalVariant::infernal2,ElementalVariant::infernal3),
    NOXIOUS(2, ElementalVariant::infernal1,ElementalVariant::infernal2,ElementalVariant::infernal3),
    ABYSSAL(3, ElementalVariant::infernal1,ElementalVariant::infernal2,ElementalVariant::infernal3),
    AERIAL(4, ElementalVariant::infernal1,ElementalVariant::infernal2,ElementalVariant::infernal3),
    TERRESTRIAL(5, ElementalVariant::infernal1,ElementalVariant::infernal2,ElementalVariant::infernal3),
    AQUATIC(6, ElementalVariant::infernal1,ElementalVariant::infernal2,ElementalVariant::infernal3),
    CHRONAL(7, ElementalVariant::infernal1,ElementalVariant::infernal2,ElementalVariant::infernal3);

    public int id;
    public static final Codec<ElementalVariant> CODEC =
            Codec.INT.xmap(ElementalVariant::fromId, ElementalVariant::getId);
    public static final StreamCodec<RegistryFriendlyByteBuf, ElementalVariant> STREAM_CODEC =
            StreamCodec.of((buf, variant) -> buf.writeVarInt(variant.getId()),
                    buf -> fromId(buf.readVarInt()));
    public final AbilityLv1Executor abilitylv1;
    public final AbilityLv2Executor abilitylv2;
    public final AbilityLv3Executor abilitylv3;


    ElementalVariant(int id, AbilityLv1Executor abilitylv1, AbilityLv2Executor abilityLv2, AbilityLv3Executor abilityLv3){
        this.id = id;
        this.abilitylv1 = abilitylv1;
        this.abilitylv2 = abilityLv2;
        this.abilitylv3 = abilityLv3;


    }





    public static void kryonic1(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.addEffect(new MobEffectInstance(ModEffects.FREEZE_EFFECT,40));
    }

    public static void kryonic2(Level level, Player player, InteractionHand usedHand){

    }

    public static void kryonic3(ItemStack stack, LivingEntity target, LivingEntity attacker){

    }

    public static void infernal1(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.igniteForTicks(40);
    }

    public static void infernal2(Level level, Player player, InteractionHand usedHand){
        SmallFireball charge = new SmallFireball(level,player,player.getLookAngle());
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
