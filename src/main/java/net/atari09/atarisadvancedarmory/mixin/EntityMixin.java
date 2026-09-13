package net.atari09.atarisadvancedarmory.mixin;

import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class EntityMixin {

    @Redirect(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;dropLeash(ZZ)V"))
    private void notifyDropLeash(Leashable l, boolean broadcastPacket, boolean dropItem, Player player, InteractionHand hand){
        l.dropLeash(broadcastPacket, dropItem);
        if(((Entity)(Object) this) instanceof ServerPlayer serverPlayer) serverPlayer.connection.send(new ClientboundSetEntityLinkPacket(((Entity)(Object) this), null));
    }
}
