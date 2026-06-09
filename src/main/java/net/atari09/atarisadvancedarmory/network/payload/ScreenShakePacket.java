package net.atari09.atarisadvancedarmory.network.payload;

import com.mojang.serialization.Codec;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ScreenShakePacket(int ticks,float strength) implements CustomPacketPayload {
    public static final Type<ScreenShakePacket> TYPE = new Type<>(AtarisAdvancedArmory.res("shake_screen"));
    private static final StreamCodec<RegistryFriendlyByteBuf, Integer> STREAM_CODEC_TICKS = StreamCodec.of((buf,i)->buf.writeInt(i), (buf)->buf.readInt());
    private static final StreamCodec<RegistryFriendlyByteBuf, Float> STREAM_CODEC_STRENGTH = StreamCodec.of((buf,i)->buf.writeFloat(i), (buf)->buf.readFloat());

    public static final StreamCodec<RegistryFriendlyByteBuf, ScreenShakePacket> STREAM_CODEC =
            StreamCodec.composite(
                    STREAM_CODEC_TICKS,ScreenShakePacket::ticks,
                    STREAM_CODEC_STRENGTH,ScreenShakePacket::strength,
                    ScreenShakePacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
