package net.atari09.atarisadvancedarmory.mixin;


import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {



    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/common/CommonHooks;fireCriticalHit(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;ZF)Lnet/neoforged/neoforge/event/entity/player/CriticalHitEvent;"))
    private static CriticalHitEvent overrideCrit(Player player, Entity target, boolean vanillaCritical, float damageModifier){
        ItemStack stack = player.getMainHandItem();

        if((stack.has(ModDataComponents.DO_CRIT))){
            if(stack.get(ModDataComponents.DO_CRIT.get())){
                stack.set(ModDataComponents.DO_CRIT.get(), false);
                return CommonHooks.fireCriticalHit(player, target, true, 1.5F);
            } else {
                return CommonHooks.fireCriticalHit(player, target, vanillaCritical, vanillaCritical ? 1.5F : 1.0F);
            }
        } else{
            return CommonHooks.fireCriticalHit(player, target, vanillaCritical, vanillaCritical ? 1.5F : 1.0F);
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private  void specialFlyAnim(CallbackInfo ci){


        Player player = (Player)(Object)this;
        if(player.level().isClientSide()) {
            PlayerAnimationController controller = (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer((AbstractClientPlayer) player, AtarisAdvancedArmory.res("fly"));
            if(player.isFallFlying() && (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.AERIAL_MACE.get()) || player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.AERIAL_MACE.get()))){
                controller.triggerAnimation(AtarisAdvancedArmory.res("fly"));
            } else{
                controller.triggerAnimation(AtarisAdvancedArmory.res("notfly"));

            }
        }

    }



}

