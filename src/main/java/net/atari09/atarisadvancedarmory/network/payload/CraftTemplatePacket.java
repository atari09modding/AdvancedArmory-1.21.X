package net.atari09.atarisadvancedarmory.network.payload;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.atari09.atarisadvancedarmory.screen.custom.SpecialSmithingTemplateMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.inventory.AbstractContainerMenu;

public record CraftTemplatePacket(SpecialSmithingTemplateType toCraft) implements CustomPacketPayload {
    public static final Type<CraftTemplatePacket> TYPE = new Type<>(AtarisAdvancedArmory.res("craft_template"));

    public static final StreamCodec<RegistryFriendlyByteBuf,CraftTemplatePacket> STREAM_CODEC =
            StreamCodec.composite(
                    SpecialSmithingTemplateType.STREAM_CODEC,
                    CraftTemplatePacket::toCraft,
                    CraftTemplatePacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
