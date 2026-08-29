package net.atari09.atarisadvancedarmory.event;


import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.component.ContainerItemContent;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.item.custom.QuiverItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingGetProjectileEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;


@EventBusSubscriber(modid = AtarisAdvancedArmory.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onLoadProjectileCheckQuiver(LivingGetProjectileEvent event){
        if(event.getEntity() instanceof Player player){
            ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(player).get();
            ItemStack quiver = curiosInventory.getStacksHandler("back").get().getStacks().getStackInSlot(0);
            if(quiver.is(ModItems.QUIVER)){
               ContainerItemContent content =  quiver.get(ModDataComponents.CONTENT);
               if(content!=null){
                    ItemStack possibleAmmo = QuiverItem.tryRemove(content,player,false);
                    event.setProjectileItemStack(possibleAmmo.isEmpty()?event.getProjectileItemStack():possibleAmmo);
               }
            }
        }
    }


}
