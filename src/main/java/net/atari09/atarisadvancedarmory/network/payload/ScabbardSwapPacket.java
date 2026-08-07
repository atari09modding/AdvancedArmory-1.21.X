package net.atari09.atarisadvancedarmory.network.payload;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ScabbardSwapPacket() implements CustomPacketPayload {
    public static final Type<ScabbardSwapPacket> TYPE = new Type<>(AtarisAdvancedArmory.res("scabbardswap"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ScabbardSwapPacket> STREAM_CODEC =
            StreamCodec.unit(new ScabbardSwapPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
