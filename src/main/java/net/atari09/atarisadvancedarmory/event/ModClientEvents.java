package net.atari09.atarisadvancedarmory.event;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.client.ScreenShake;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.item.util.ElementalWeapon;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.settings.KeyMappingLookup;
import net.neoforged.neoforge.event.level.NoteBlockEvent;
import org.lwjgl.glfw.GLFW;

import javax.swing.text.JTextComponent;

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
    }
}
