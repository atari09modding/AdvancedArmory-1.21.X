package net.atari09.atarisadvancedarmory.network.handler;

import net.atari09.atarisadvancedarmory.component.PlayerInputs;
import net.atari09.atarisadvancedarmory.network.payload.PlayerInputPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PlayerInputPacketHandler {
    public static void  handle(final PlayerInputPacket packet, final IPayloadContext context){
        context.enqueueWork(()->{
            switch(PlayerInputPacket.PlayerInput.fromId(packet.id())){
                case PlayerInputPacket.PlayerInput.W ->{
                    if(context.player() instanceof PlayerInputs p){
                        p.setRopeSwinging(true);
                    }

                }
            }
        });
    }
}
