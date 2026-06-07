package net.atari09.atarisadvancedarmory.item.util;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public enum ElementalVariant {
    INFERNAL(0),
    KRYONIC(1),
    NOXIOUS(2),
    ABYSSAL(3),
    AERIAL(4),
    TERRESTRIAL(5),
    AQUATIC(6),
    CHRONAL(7);

    public int id;
    public static final Codec<ElementalVariant> CODEC =
            Codec.INT.xmap(ElementalVariant::fromId, ElementalVariant::getId);
    public static final StreamCodec<RegistryFriendlyByteBuf, ElementalVariant> STREAM_CODEC =
            StreamCodec.of((buf, variant) -> buf.writeVarInt(variant.getId()),
                    buf -> fromId(buf.readVarInt()));

    ElementalVariant(int id){
        this.id = id;

    }




    public int getId() {
        return id;
    }

    public static ElementalVariant fromId(int id) {
        for (ElementalVariant variant : values()) {
            if (variant.id == id) {
                return variant;
            }
        }
        throw new IllegalArgumentException("Unknown variant id: " + id);
    }
}
