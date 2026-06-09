package net.atari09.atarisadvancedarmory.mixin;


import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.util.ElementalWeapon;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

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
}

