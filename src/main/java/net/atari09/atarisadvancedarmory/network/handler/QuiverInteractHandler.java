package net.atari09.atarisadvancedarmory.network.handler;

import net.atari09.atarisadvancedarmory.component.ContainerItemContent;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.item.custom.QuiverItem;
import net.atari09.atarisadvancedarmory.network.payload.QuiverInteractPacket;
import net.atari09.atarisadvancedarmory.network.payload.ScabbardSwapPacket;
import net.atari09.atarisadvancedarmory.util.ModTags;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class QuiverInteractHandler {
    public static void handle(final QuiverInteractPacket packet, final IPayloadContext context) {

        CompletableFuture.delayedExecutor(420, TimeUnit.MILLISECONDS).execute(()->{
            context.enqueueWork(()->{
                Player player = context.player();

                ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(player).get();
                ICurioStacksHandler back = curiosInventory.getStacksHandler("back").get();

                if(back.getStacks().getStackInSlot(0).has(ModDataComponents.CONTENT) && back.getStacks().getStackInSlot(0).is(ModItems.QUIVER)){
                    ItemStack backStack = back.getStacks().getStackInSlot(0);
                    ContainerItemContent content = backStack.get(ModDataComponents.CONTENT).getCopy();
                    ItemStack hand = player.getMainHandItem();
                    System.out.println("1");

                    if(hand.isEmpty()){
                        player.setItemInHand(InteractionHand.MAIN_HAND, QuiverItem.tryRemove(content,player));
                        System.out.println("2a");
                    } else if (hand.is(ModTags.Items.FITS_IN_QUIVER)){
                        player.setItemInHand(InteractionHand.MAIN_HAND,QuiverItem.tryInsert(content,hand,player));
                        System.out.println("2b");
                    }
                }


            });

        });


    }
}
