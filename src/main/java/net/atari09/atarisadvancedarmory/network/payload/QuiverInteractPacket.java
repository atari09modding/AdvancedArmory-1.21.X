package net.atari09.atarisadvancedarmory.network.payload;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record QuiverInteractPacket() implements CustomPacketPayload {

    public static final Type<QuiverInteractPacket> TYPE = new Type<>(AtarisAdvancedArmory.res("quiver_interact"));

    public static final StreamCodec<RegistryFriendlyByteBuf, QuiverInteractPacket> STREAM_CODEC =
            StreamCodec.unit(new QuiverInteractPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
