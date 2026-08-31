package net.atari09.atarisadvancedarmory.network.payload;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record StartCraftPacket(BlockPos pos) implements CustomPacketPayload {

    public static final Type<StartCraftPacket> TYPE =
            new Type<>(AtarisAdvancedArmory.res("start_smithing"));

    public static final StreamCodec<FriendlyByteBuf, StartCraftPacket> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,
                    StartCraftPacket::pos,
                    StartCraftPacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
