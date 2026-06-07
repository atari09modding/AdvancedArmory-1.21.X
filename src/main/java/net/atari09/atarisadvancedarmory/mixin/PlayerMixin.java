package net.atari09.atarisadvancedarmory.mixin;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public class PlayerMixin {

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/common/CommonHooks;fireCriticalHit(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;ZF)Lnet/neoforged/neoforge/event/entity/player/CriticalHitEvent;"))
    private static CriticalHitEvent overrideCrit(Player player, Entity target, boolean vanillaCritical, float damageModifier){

        if(!player.getMainHandItem().is(Items.NETHERITE_SWORD)){
            return CommonHooks.fireCriticalHit(player, target, vanillaCritical, vanillaCritical ? 1.5F : 1.0F);
        } else{
            return CommonHooks.fireCriticalHit(player, target, true, 1.5F);//here put some other condition later
        }
    }
}

