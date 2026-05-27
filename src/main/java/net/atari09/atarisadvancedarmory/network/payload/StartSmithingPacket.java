package net.atari09.atarisadvancedarmory.network.payload;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record StartSmithingPacket(BlockPos pos) implements CustomPacketPayload {

    public static final Type<StartSmithingPacket> TYPE =
            new Type<>(AtarisAdvancedArmory.res("start_smithing"));

    public static final StreamCodec<FriendlyByteBuf, StartSmithingPacket> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,
                    StartSmithingPacket::pos,
                    StartSmithingPacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
