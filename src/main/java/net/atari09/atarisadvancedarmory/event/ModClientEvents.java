package net.atari09.atarisadvancedarmory.event;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.client.ScreenShake;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.item.component.ContainerItemContent;
import net.atari09.atarisadvancedarmory.network.payload.ScabbardSwapPacket;
import net.atari09.atarisadvancedarmory.util.KeyBinding;
import net.atari09.atarisadvancedarmory.util.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

@EventBusSubscriber(modid = AtarisAdvancedArmory.MOD_ID,value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void irreasonableScreenShake(ViewportEvent.ComputeCameraAngles event){
        float pitch = event.getPitch();
        float yaw = event.getYaw();
        float roll = event.getRoll();
        if (ScreenShake.ticks <= 0) return;
        ScreenShake.tick();

        float s = ScreenShake.strength;

        event.setPitch((float) (pitch+Math.sin(ScreenShake.ticks*0.75)*s));
        event.setRoll((float) (roll+Math.sin(ScreenShake.ticks*0.95)*s));
        //event.setYaw((float) (yaw+Math.sin(ScreenShake.ticks)*s));
    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event){
        event.register(KeyBinding.PULL_WEAPON_OUT_KEY);

    }


    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event){
        Minecraft mc = Minecraft.getInstance();


        if(mc.options.keyUp.isDown() && mc.player != null){
            Player player = mc.player;
            if(player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.AERIAL_MACE.get()) || player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.AERIAL_MACE.get())){
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.AERIAL_MACE.get())?player.getItemInHand(InteractionHand.MAIN_HAND):player.getItemInHand(InteractionHand.OFF_HAND);
                if(stack.has(ModDataComponents.ELEMENTAL_LEVEL)){
                    if(stack.get(ModDataComponents.ELEMENTAL_LEVEL) >= 3 && player.isFallFlying() && !(player.getDeltaMovement().length() > 3d)){
                        Vec3 movement = player.getLookAngle();
                        player.setDeltaMovement(movement.scale(2));
                    }
                }
            }
        }

        if (KeyBinding.PULL_WEAPON_OUT_KEY.consumeClick()){
            Player player = mc.player;

            PacketDistributor.sendToServer(new ScabbardSwapPacket());

            ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(player).get();
            ICurioStacksHandler back = curiosInventory.getStacksHandler("back").get();
            ICurioStacksHandler belt = curiosInventory.getStacksHandler("belt").get();

            if(back.getStacks().getStackInSlot(0).has(ModDataComponents.CONTENT)){
                if(((player.getMainHandItem().isEmpty() &&
                        !back.getStacks().getStackInSlot(0).get(ModDataComponents.CONTENT).contents().get(0).isEmpty())
                        || (player.getMainHandItem().is(ModTags.Items.FITS_IN_SCABBARD) &&
                        back.getStacks().getStackInSlot(0).get(ModDataComponents.CONTENT).contents().get(0).isEmpty()))
                        && back.getStacks().getStackInSlot(0).is(ModItems.SCABBARD)){
                    PlayerAnimationController controller =
                            (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(
                                    (AbstractClientPlayer) player,
                                    AtarisAdvancedArmory.res("pull_out_weapon"));
                    controller.triggerAnimation(AtarisAdvancedArmory.res("pull_out_weapon_back"));
                }
            }
            if(belt.getStacks().getStackInSlot(0).has(ModDataComponents.CONTENT)){
                if(((player.getMainHandItem().isEmpty() &&
                        !belt.getStacks().getStackInSlot(0).get(ModDataComponents.CONTENT).contents().get(0).isEmpty())
                        || (player.getMainHandItem().is(ModTags.Items.FITS_IN_SCABBARD) &&
                        belt.getStacks().getStackInSlot(0).get(ModDataComponents.CONTENT).contents().get(0).isEmpty()))
                        && belt.getStacks().getStackInSlot(0).is(ModItems.SCABBARD)){


                    PlayerAnimationController controller =
                            (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(
                                    (AbstractClientPlayer) player,
                                    AtarisAdvancedArmory.res("pull_out_weapon"));
                    controller.triggerAnimation(AtarisAdvancedArmory.res("pull_out_weapon_belt"));
                }
            }




        }
    }
}
