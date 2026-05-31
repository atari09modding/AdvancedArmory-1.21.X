package net.atari09.atarisadvancedarmory.item.util;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public enum SpecialSmithingTemplateType {
    NONE(0,"none"),
    TEMPORARY_DEBUG(1,"debug"),
    TEMPORARY_DEBUG2(2,"debug2"),
    TEMPORARY_DEBUG3(3,"debug3"),
    TEMPORARY_DEBUG4(4,"debug3"),
    TEMPORARY_DEBUG5(5,"debug4"),
    TEMPORARY_DEBUG6(6,"debug5"),
    TEMPORARY_DEBUG7(7,"debug6"),
    TEMPORARY_DEBUG8(8,"debug7"),
    TEMPORARY_DEBUG9(9,"debug8"),
    TEMPORARY_DEBUG10(10,"debug9"),
    TEMPORARY_DEBUG11(11,"debug10"),
    TEMPORARY_DEBUG12(12,"debug11"),
    TEMPORARY_DEBUG13(13,"debug12"),
    TEMPORARY_DEBUG14(14,"debug13"),
    TEMPORARY_DEBUG15(15,"debug14"),
    TEMPORARY_DEBUG16(16,"debug15"),
    TEMPORARY_DEBUG17(17,"debug16"),
    TEMPORARY_DEBUG18(18,"debug17"),
    TEMPORARY_DEBUG19(19,"debug18"),
    TEMPORARY_DEBUG20(20,"debug19"),
    TEMPORARY_DEBUG21(21,"debug20"),
    TEMPORARY_DEBUG22(22,"debug21"),
    TEMPORARY_DEBUG23(23,"debug22"),
    TEMPORARY_DEBUG24(24,"debug23"),
    TEMPORARY_DEBUG25(25,"debug34"),
    TEMPORARY_DEBUG26(26,"debug25"),
    TEMPORARY_DEBUG27(27,"debug26"),
    TEMPORARY_DEBUG28(28,"debug27"),
    TEMPORARY_DEBUG29(29,"debug28"),
    TEMPORARY_DEBUG30(30,"debug29"),
    TEMPORARY_DEBUG31(31,"debug30"),
    TEMPORARY_DEBUG32(32,"debug"),
    TEMPORARY_DEBUG33(33,"debug"),
    TEMPORARY_DEBUG34(34,"debug"),
    TEMPORARY_DEBUG35(35,"debug"),
    TEMPORARY_DEBUG36(36,"debug"),
    TEMPORARY_DEBUG37(37,"debug"),
    TEMPORARY_DEBUG38(38,"debug"),
    TEMPORARY_DEBUG39(39,"debug"),
    TEMPORARY_DEBUG40(40,"debug"),
    TEMPORARY_DEBUG41(41,"debug"),
    TEMPORARY_DEBUG42(42,"debug"),
    TEMPORARY_DEBUG43(43,"debug"),
    TEMPORARY_DEBUG44(44,"debug"),
    TEMPORARY_DEBUG45(45,"debug"),
    TEMPORARY_DEBUG46(46,"debug"),
    TEMPORARY_DEBUG47(47,"debug"),
    TEMPORARY_DEBUG48(48,"debug"),
    TEMPORARY_DEBUG49(49,"debug"),
    TEMPORARY_DEBUG50(50,"debug"),
    TEMPORARY_DEBUG51(51,"debug"),
    TEMPORARY_DEBUG52(52,"debug"),
    TEMPORARY_DEBUG53(53,"debug"),
    TEMPORARY_DEBUG54(54,"debug"),
    TEMPORARY_DEBUG55(55,"debug"),
    TEMPORARY_DEBUG56(56,"debug"),
    TEMPORARY_DEBUG57(57,"debug");

    public String name;
    public int id;
    public static final Codec<SpecialSmithingTemplateType> CODEC =
            Codec.INT.xmap(SpecialSmithingTemplateType::fromId, SpecialSmithingTemplateType::getId);

    public static final StreamCodec<RegistryFriendlyByteBuf, SpecialSmithingTemplateType> STREAM_CODEC =
            StreamCodec.of((buf, type) -> buf.writeVarInt(type.getId()),
            buf -> fromId(buf.readVarInt()));

    SpecialSmithingTemplateType(int id, String name){
        this.name = name;
        this.id = id;
    }

    public boolean check(SpecialSmithingTemplateType type) {
        return(this == type);
    }

    public int getId() {
        return id;
    }

    public static SpecialSmithingTemplateType fromId(int id) {
        for (SpecialSmithingTemplateType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown template type id: " + id);
    }




}
