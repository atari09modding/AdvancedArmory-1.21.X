package net.atari09.atarisadvancedarmory.item;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.ModBlocks;
import net.atari09.atarisadvancedarmory.block.custom.WeaponSmithBaseBlock;
import net.atari09.atarisadvancedarmory.item.custom.ElementalMaceItem;
import net.atari09.atarisadvancedarmory.item.custom.SpecialSmithingTemplateItem;
import net.atari09.atarisadvancedarmory.item.custom.WeaponSmithBlockItem;
import net.atari09.atarisadvancedarmory.item.util.ElementalProperties;
import net.atari09.atarisadvancedarmory.item.util.ElementalVariant;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static  final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AtarisAdvancedArmory.MOD_ID);

    public static final DeferredItem<Item> WEAPONSMITHBLOCKITEM = ITEMS.register("weaponsmithblockitem",
            ()->new WeaponSmithBlockItem(ModBlocks.WEAPONSMITHBASEBLOCK.get(), new Item.Properties()));

    public static final DeferredItem<Item> SPECIAL_SMITHING_TEMPLATE = ITEMS.register("specialsmithingtemplate",
            ()->new SpecialSmithingTemplateItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> INFERNAL_MACE = ITEMS.register("infernal_mace",
            ()-> new ElementalMaceItem(new ElementalProperties().setLevel(1).setElement(ElementalVariant.INFERNAL).stacksTo(1).durability(700).attributes(ElementalMaceItem.createAttributes())));

    public static final DeferredItem<Item> KRYONIC_MACE = ITEMS.register("infernal_mace",
            ()-> new ElementalMaceItem(new ElementalProperties().setLevel(1).setElement(ElementalVariant.KRYONIC).stacksTo(1).durability(700).attributes(ElementalMaceItem.createAttributes())));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
