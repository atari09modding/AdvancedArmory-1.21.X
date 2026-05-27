package net.atari09.atarisadvancedarmory.network.handler;

import net.atari09.atarisadvancedarmory.block.entity.WeaponSmithBlockEntity;
import net.atari09.atarisadvancedarmory.network.payload.StartSmithingPacket;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class StartSmithingPacketHandler {

    public static void handle(final StartSmithingPacket packet, final IPayloadContext context){
        context.enqueueWork(()->{
            Player player = context.player();

            if(player.level().getBlockEntity(packet.pos()) instanceof WeaponSmithBlockEntity be && !be.isWorking() && be.hasRecipe() ){
                be.startCrafting();
            }
        });
    }
}
