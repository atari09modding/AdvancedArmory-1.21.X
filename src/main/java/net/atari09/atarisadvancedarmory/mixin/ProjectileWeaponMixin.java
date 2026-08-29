package net.atari09.atarisadvancedarmory.mixin;


import net.atari09.atarisadvancedarmory.component.ContainerItemContent;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@Mixin(ProjectileWeaponItem.class)
public class ProjectileWeaponMixin {


    @Redirect(method = "useAmmo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;removeItem(Lnet/minecraft/world/item/ItemStack;)V"))
    private static void actuallyremoveItem(Inventory inv, ItemStack ammo){
        Player player = inv.player;
        ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(player).get();
        ItemStack quiver = curiosInventory.getStacksHandler("back").get().getStacks().getStackInSlot(0);
        boolean flag = true;
        if(quiver.is(ModItems.QUIVER)){
            ContainerItemContent content = quiver.get(ModDataComponents.CONTENT).getCopy();
            if(content!=null){
                for(int i = 0; i<content.contents().size();i++){


                    if(content.contents().get(i).is(ammo.getItem())){

                        content.contents().set(i,ItemStack.EMPTY);
                        flag = false;
                        break;
                    }
                }

                quiver.set(ModDataComponents.CONTENT,content);
            }
        }
        if(flag){
            inv.removeItem(ammo);
        }
    }


    @Redirect(method = "useAmmo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;split(I)Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack splitfromQuiver(ItemStack ammo, int amount, ItemStack weapon, ItemStack ammo_, LivingEntity shooter, boolean intangable){
        if(shooter instanceof Player player){
            ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(player).get();
            ItemStack quiver = curiosInventory.getStacksHandler("back").get().getStacks().getStackInSlot(0);

            ItemStack stack = ItemStack.EMPTY;
            if(quiver.is(ModItems.QUIVER)){
                ContainerItemContent content = quiver.get(ModDataComponents.CONTENT).getCopy();
                if(content!=null){
                    for(int i = 0; i<content.contents().size();i++){
                        if(content.contents().get(i).is(ammo.getItem())){
                            if(content.contents().get(i).is(Items.TIPPED_ARROW)){
                                Iterable<MobEffectInstance> effects_stack = content.contents().get(i).get(DataComponents.POTION_CONTENTS).getAllEffects();
                                Iterable<MobEffectInstance> effects_ammo = ammo.get(DataComponents.POTION_CONTENTS).getAllEffects();
                                if(effects_stack!=null&&effects_ammo!=null){
                                    if(effects_stack.equals(effects_ammo)){
                                        stack = content.contents().get(i).split(amount);
                                        break;
                                    }
                                }

                            } else {
                                stack = content.contents().get(i).split(amount);
                                break;
                            }

                        }
                    }

                    quiver.set(ModDataComponents.CONTENT,content);
                    if(stack.isEmpty()) return stack;
                }
            }
        }
        return ammo.split(amount);
    }
}
