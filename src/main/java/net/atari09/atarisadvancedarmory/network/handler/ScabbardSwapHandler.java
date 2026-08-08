package net.atari09.atarisadvancedarmory.network.handler;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.item.component.ContainerItemContent;
import net.atari09.atarisadvancedarmory.network.payload.ScabbardSwapPacket;
import net.atari09.atarisadvancedarmory.util.ModTags;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.server.TickTask;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ScabbardSwapHandler {
    public static void handle(final ScabbardSwapPacket packet, final IPayloadContext context) {

        CompletableFuture.delayedExecutor(420, TimeUnit.MILLISECONDS).execute(()->{
            context.enqueueWork(()->{
                Player player = context.player();

                ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(player).get();
                ICurioStacksHandler back = curiosInventory.getStacksHandler("back").get();
                ICurioStacksHandler belt = curiosInventory.getStacksHandler("belt").get();

                if(back.getStacks().getStackInSlot(0).has(ModDataComponents.CONTENT)){
                    if(((player.getMainHandItem().isEmpty() &&
                            !back.getStacks().getStackInSlot(0).get(ModDataComponents.CONTENT).contents().get(0).isEmpty())
                            || (player.getMainHandItem().is(ModTags.Items.FITS_IN_SCABBARD) &&
                            back.getStacks().getStackInSlot(0).get(ModDataComponents.CONTENT).contents().get(0).isEmpty()))
                            && back.getStacks().getStackInSlot(0).is(ModItems.SCABBARD)){

                        if(player.getMainHandItem().isEmpty()){
                            ItemStack stack = back.getStacks().getStackInSlot(0).get(ModDataComponents.CONTENT).getContent().get(0);
                            player.setItemInHand(InteractionHand.MAIN_HAND,stack.copy());
                            back.getStacks().getStackInSlot(0).set(ModDataComponents.CONTENT,
                                    new ContainerItemContent(NonNullList.withSize(1,ItemStack.EMPTY)));

                        } else {
                            ItemStack stack = player.getMainHandItem();
                            player.setItemInHand(InteractionHand.MAIN_HAND,ItemStack.EMPTY);

                            NonNullList<ItemStack> list = NonNullList.withSize(1,stack.copy());
                            list.set(0,stack.copy());
                            ItemStack scabbardStack = back.getStacks().getStackInSlot(0);
                            scabbardStack.set(ModDataComponents.CONTENT, new ContainerItemContent(list));
                            back.getStacks().setStackInSlot(0, scabbardStack);



                        }


                    }
                }
                if(belt.getStacks().getStackInSlot(0).has(ModDataComponents.CONTENT)){
                    if(((player.getMainHandItem().isEmpty() &&
                            !belt.getStacks().getStackInSlot(0).get(ModDataComponents.CONTENT).contents().get(0).isEmpty())
                            || (player.getMainHandItem().is(ModTags.Items.FITS_IN_SCABBARD) &&
                            belt.getStacks().getStackInSlot(0).get(ModDataComponents.CONTENT).contents().get(0).isEmpty()))
                            && belt.getStacks().getStackInSlot(0).is(ModItems.SCABBARD)){


                        if(player.getMainHandItem().isEmpty()){
                            ItemStack stack = belt.getStacks().getStackInSlot(0).get(ModDataComponents.CONTENT).getContent().get(0);
                            player.setItemInHand(InteractionHand.MAIN_HAND,stack.copy());
                            belt.getStacks().getStackInSlot(0).set(ModDataComponents.CONTENT,
                                    new ContainerItemContent(NonNullList.withSize(1,ItemStack.EMPTY)));

                        } else{
                            ItemStack stack = player.getMainHandItem();
                            player.setItemInHand(InteractionHand.MAIN_HAND,ItemStack.EMPTY);
                            NonNullList<ItemStack> list = NonNullList.withSize(1,stack.copy());
                            list.set(0,stack.copy());

                            ItemStack scabbardStack = belt.getStacks().getStackInSlot(0);
                            scabbardStack.set(ModDataComponents.CONTENT, new ContainerItemContent(list));
                            belt.getStacks().setStackInSlot(0, scabbardStack);



                        }


                    }
                }


            });

        });


    }
}
