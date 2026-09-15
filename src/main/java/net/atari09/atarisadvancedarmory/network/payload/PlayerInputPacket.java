package net.atari09.atarisadvancedarmory.network.payload;

import com.mojang.serialization.Codec;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record PlayerInputPacket(int id) implements CustomPacketPayload {



    public static final Type<PlayerInputPacket> TYPE = new Type<>(AtarisAdvancedArmory.res("player_input"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerInputPacket> STREAM_CODEC =
            StreamCodec.of(
                    (buf,p)->buf.writeInt(p.id()),
                    buf->new PlayerInputPacket(buf.readInt()));


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public enum PlayerInput {
        W(1),
        ROPE_UP(2),
        ROPE_DOWN(3);

        private final int id;
        PlayerInput(int id){
            this.id = id;
        }

        public int id(){
            return id;
        }

        public static PlayerInput fromId(int check){
            for(PlayerInput v : values()){
                if(v.id == check){
                    return v;
                }
            }
            throw new IllegalArgumentException("Unknown AAA PlayerInput id: " + check);
        }
    }
}
