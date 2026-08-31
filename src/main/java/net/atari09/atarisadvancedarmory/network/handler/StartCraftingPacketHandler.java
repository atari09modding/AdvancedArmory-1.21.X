package net.atari09.atarisadvancedarmory.network.handler;

import net.atari09.atarisadvancedarmory.component.CraftingBlockEntity;
import net.atari09.atarisadvancedarmory.network.payload.StartCraftPacket;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class StartCraftingPacketHandler {

    public static void handle(final StartCraftPacket packet, final IPayloadContext context){
        context.enqueueWork(()->{
            Player player = context.player();

            if(player.level().getBlockEntity(packet.pos()) instanceof CraftingBlockEntity be && !be.isWorking() && be.hasRecipe() ){
                be.startCrafting();
            }
        });
    }
}
