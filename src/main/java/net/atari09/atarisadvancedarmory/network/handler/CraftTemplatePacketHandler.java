package net.atari09.atarisadvancedarmory.network.handler;

import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.custom.SpecialSmithingTemplateItem;
import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.atari09.atarisadvancedarmory.network.payload.CraftTemplatePacket;
import net.atari09.atarisadvancedarmory.screen.custom.SpecialSmithingTemplateMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class CraftTemplatePacketHandler {
    public static void handle(final CraftTemplatePacket packet, final IPayloadContext context){
        context.enqueueWork(()->{
                Player player = context.player();
                ItemStack cost = packet.toCraft().cost;

                if(player.getItemInHand(player.getUsedItemHand()).getItem() instanceof SpecialSmithingTemplateItem item
                        && item.getType(player.getItemInHand(player.getUsedItemHand())) == SpecialSmithingTemplateType.NONE
                        && player.containerMenu instanceof SpecialSmithingTemplateMenu menu){
                    if (menu.container.getItem(0).is(cost.getItem()) && menu.container.getItem(0).getCount() >= cost.getCount()){
                        menu.container.getItem(0).shrink(cost.getCount());
                        player.getItemInHand(player.getUsedItemHand()).set(ModDataComponents.SPECIALSMITHINGTEMPLATETYPES, packet.toCraft());
                        player.closeContainer();
                    }
                }
            }
        );

    }
}
