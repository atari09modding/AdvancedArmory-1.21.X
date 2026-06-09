package net.atari09.atarisadvancedarmory.event;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.client.ScreenShake;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

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
}
