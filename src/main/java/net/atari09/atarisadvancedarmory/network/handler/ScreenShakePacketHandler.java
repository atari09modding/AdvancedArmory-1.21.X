package net.atari09.atarisadvancedarmory.network.handler;

import net.atari09.atarisadvancedarmory.client.ScreenShake;
import net.atari09.atarisadvancedarmory.network.payload.ScreenShakePacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ScreenShakePacketHandler {
    public static void handle(final ScreenShakePacket packet, final IPayloadContext context){
        context.enqueueWork(()->{
            ScreenShake.start(packet.ticks(),packet.strength());
        });
    }
}
