package net.atari09.atarisadvancedarmory.item.util;

import com.mojang.serialization.Codec;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum SpecialSmithingTemplateType {
    NONE(0,"none",ItemStack.EMPTY),
    TEMPORARY_DEBUG(1,"debug",new ItemStack(Items.TNT,23)),
    TEMPORARY_DEBUG2(2,"debug2",new ItemStack(Items.DIAMOND,5)),
    TEMPORARY_DEBUG3(3,"debug3",ItemStack.EMPTY),
    TEMPORARY_DEBUG4(4,"debug3",ItemStack.EMPTY),
    TEMPORARY_DEBUG5(5,"debug4",ItemStack.EMPTY),
    TEMPORARY_DEBUG6(6,"debug5",ItemStack.EMPTY),
    TEMPORARY_DEBUG7(7,"debug6",ItemStack.EMPTY),
    TEMPORARY_DEBUG8(8,"debug7",ItemStack.EMPTY),
    TEMPORARY_DEBUG9(9,"debug8",ItemStack.EMPTY),
    TEMPORARY_DEBUG10(10,"debug9",ItemStack.EMPTY),
    TEMPORARY_DEBUG11(11,"debug10",ItemStack.EMPTY),
    TEMPORARY_DEBUG12(12,"debug11",ItemStack.EMPTY),
    TEMPORARY_DEBUG13(13,"debug12",ItemStack.EMPTY),
    TEMPORARY_DEBUG14(14,"debug13",ItemStack.EMPTY),
    TEMPORARY_DEBUG15(15,"debug14",ItemStack.EMPTY),
    TEMPORARY_DEBUG16(16,"debug15",ItemStack.EMPTY),
    TEMPORARY_DEBUG17(17,"debug16",ItemStack.EMPTY),
    TEMPORARY_DEBUG18(18,"debug17",ItemStack.EMPTY),
    TEMPORARY_DEBUG19(19,"debug18",ItemStack.EMPTY),
    TEMPORARY_DEBUG20(20,"debug19",ItemStack.EMPTY),
    TEMPORARY_DEBUG21(21,"debug20",ItemStack.EMPTY),
    TEMPORARY_DEBUG22(22,"debug21",ItemStack.EMPTY),
    TEMPORARY_DEBUG23(23,"debug22",ItemStack.EMPTY),
    TEMPORARY_DEBUG24(24,"debug23",ItemStack.EMPTY),
    TEMPORARY_DEBUG25(25,"debug34",ItemStack.EMPTY),
    TEMPORARY_DEBUG26(26,"debug25",ItemStack.EMPTY),
    TEMPORARY_DEBUG27(27,"debug26",ItemStack.EMPTY),
    TEMPORARY_DEBUG28(28,"debug27",ItemStack.EMPTY),
    TEMPORARY_DEBUG29(29,"debug28",ItemStack.EMPTY),
    TEMPORARY_DEBUG30(30,"debug29",ItemStack.EMPTY),
    TEMPORARY_DEBUG31(31,"debug30",ItemStack.EMPTY),
    TEMPORARY_DEBUG32(32,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG33(33,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG34(34,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG35(35,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG36(36,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG37(37,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG38(38,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG39(39,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG40(40,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG41(41,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG42(42,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG43(43,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG44(44,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG45(45,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG46(46,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG47(47,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG48(48,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG49(49,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG50(50,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG51(51,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG52(52,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG53(53,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG54(54,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG55(55,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG56(56,"debug",ItemStack.EMPTY),
    TEMPORARY_DEBUG57(57,"debug",ItemStack.EMPTY);

    public String name;
    public int id;
    public ItemStack cost;
    public static final Codec<SpecialSmithingTemplateType> CODEC =
            Codec.INT.xmap(SpecialSmithingTemplateType::fromId, SpecialSmithingTemplateType::getId);

    public static final StreamCodec<RegistryFriendlyByteBuf, SpecialSmithingTemplateType> STREAM_CODEC =
            StreamCodec.of((buf, type) -> buf.writeVarInt(type.getId()),
            buf -> fromId(buf.readVarInt()));

    SpecialSmithingTemplateType(int id, String name, ItemStack cost){
        this.name = name;
        this.id = id;
        this.cost = cost;
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


    public ResourceLocation getTexture() {
        return AtarisAdvancedArmory.res("textures/item/specialsmithingtemplate_variant/"+name+".png");
    }
}
