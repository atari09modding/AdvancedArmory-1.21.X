package net.atari09.atarisadvancedarmory.network.handler;

import net.atari09.atarisadvancedarmory.component.PlayerInputs;
import net.atari09.atarisadvancedarmory.network.payload.PlayerInputPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PlayerInputPacketHandler {
    public static void  handle(final PlayerInputPacket packet, final IPayloadContext context){
        context.enqueueWork(()->{
            if(!(context.player() instanceof PlayerInputs p)) return;
            switch(PlayerInputPacket.PlayerInput.fromId(packet.id())){
                case PlayerInputPacket.PlayerInput.W ->{
                    p.setRopeSwinging(true);
                }
                case ROPE_DOWN -> {p.setClimbingRope(-1);}
                case ROPE_UP -> {p.setClimbingRope(1);}
            }
        });
    }
}
