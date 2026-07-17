package net.atari09.atarisadvancedarmory.item;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.ModBlocks;
import net.atari09.atarisadvancedarmory.block.custom.WeaponSmithBaseBlock;
import net.atari09.atarisadvancedarmory.item.custom.*;
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
            ()-> new ElementalMaceItem(new ElementalProperties().setElement(ElementalVariant.INFERNAL).fireResistant().stacksTo(1).durability(700).attributes(ElementalMaceItem.createAttributes())));

    public static final DeferredItem<Item> KRYONIC_MACE = ITEMS.register("kryonic_mace",
            ()-> new ElementalMaceItem(new ElementalProperties().setElement(ElementalVariant.KRYONIC).fireResistant().stacksTo(1).durability(700).attributes(ElementalMaceItem.createAttributes())));

    public static final DeferredItem<Item> NOXIOUS_MACE = ITEMS.register("noxious_mace",
            ()-> new ElementalMaceItem(new ElementalProperties().setElement(ElementalVariant.NOXIOUS).fireResistant().stacksTo(1).durability(700).attributes(ElementalMaceItem.createAttributes())));

    public static final DeferredItem<Item> ABYSSAL_MACE = ITEMS.register("abyssal_mace",
            ()-> new ElementalMaceItem(new ElementalProperties().setElement(ElementalVariant.ABYSSAL).fireResistant().stacksTo(1).durability(700).attributes(ElementalMaceItem.createAttributes())));

    public static final DeferredItem<Item> AERIAL_MACE = ITEMS.register("aerial_mace",
            ()-> new ElementalMaceItem(new ElementalProperties().setElement(ElementalVariant.AERIAL).fireResistant().stacksTo(1).durability(700).attributes(ElementalMaceItem.createAttributes())));

    public static final DeferredItem<Item> TERRESTRIAL_MACE = ITEMS.register("terrestrial_mace",
            ()-> new ElementalMaceItem(new ElementalProperties().setElement(ElementalVariant.TERRESTRIAL).fireResistant().stacksTo(1).durability(700).attributes(ElementalMaceItem.createAttributes())));

    public static final DeferredItem<Item> INFERNAL_SWORD = ITEMS.register("infernal_sword",
            ()-> new ElementalSwordItem(Tiers.NETHERITE, new ElementalProperties().setElement(ElementalVariant.INFERNAL).fireResistant().stacksTo(1).durability(700).attributes(ElementalSwordItem.createAttributes(Tiers.NETHERITE,3.5f,-2.4f))));

    public static final DeferredItem<Item> KRYONIC_SWORD = ITEMS.register("kryonic_sword",
            ()-> new ElementalSwordItem(Tiers.NETHERITE, new ElementalProperties().setElement(ElementalVariant.KRYONIC).fireResistant().stacksTo(1).durability(700).attributes(ElementalSwordItem.createAttributes(Tiers.NETHERITE,3.5f,-2.4f))));

    public static final DeferredItem<Item> NOXIOUS_SWORD = ITEMS.register("noxious_sword",
            ()-> new ElementalSwordItem(Tiers.NETHERITE, new ElementalProperties().setElement(ElementalVariant.NOXIOUS).fireResistant().stacksTo(1).durability(700).attributes(ElementalSwordItem.createAttributes(Tiers.NETHERITE,3.5f,-2.4f))));

    public static final DeferredItem<Item> ABYSSAL_SWORD = ITEMS.register("abyssal_sword",
            ()-> new ElementalSwordItem(Tiers.NETHERITE, new ElementalProperties().setElement(ElementalVariant.ABYSSAL).fireResistant().stacksTo(1).durability(700).attributes(ElementalSwordItem.createAttributes(Tiers.NETHERITE,3.5f,-2.4f))));

    public static final DeferredItem<Item> AERIAL_SWORD= ITEMS.register("aerial_sword",
            ()-> new ElementalSwordItem(Tiers.NETHERITE, new ElementalProperties().setElement(ElementalVariant.AERIAL).fireResistant().stacksTo(1).durability(700).attributes(ElementalSwordItem.createAttributes(Tiers.NETHERITE,3.5f,-2.4f))));

    public static final DeferredItem<Item> TERRESTRIAL_SWORD = ITEMS.register("terrestrial_sword",
            ()-> new ElementalSwordItem(Tiers.NETHERITE, new ElementalProperties().setElement(ElementalVariant.TERRESTRIAL).fireResistant().stacksTo(1).durability(700).attributes(ElementalSwordItem.createAttributes(Tiers.NETHERITE,3.5f,-2.4f))));

    public static final DeferredItem<Item> INFERNAL_AXE = ITEMS.register("infernal_axe",
            ()-> new ElementalSwordItem(Tiers.NETHERITE, new ElementalProperties().setElement(ElementalVariant.INFERNAL).fireResistant().stacksTo(1).durability(700).attributes(ElementalAxeItem.createAttributes(Tiers.NETHERITE,5f,-3f))));

    public static final DeferredItem<Item> KRYONIC_AXE = ITEMS.register("kryonic_axe",
            ()-> new ElementalSwordItem(Tiers.NETHERITE, new ElementalProperties().setElement(ElementalVariant.KRYONIC).fireResistant().stacksTo(1).durability(700).attributes(ElementalAxeItem.createAttributes(Tiers.NETHERITE,5f,-3f))));

    public static final DeferredItem<Item> NOXIOUS_AXE = ITEMS.register("noxious_axe",
            ()-> new ElementalSwordItem(Tiers.NETHERITE, new ElementalProperties().setElement(ElementalVariant.NOXIOUS).fireResistant().stacksTo(1).durability(700).attributes(ElementalAxeItem.createAttributes(Tiers.NETHERITE,5f,-3f))));

    public static final DeferredItem<Item> ABYSSAL_AXE = ITEMS.register("abyssal_axe",
            ()-> new ElementalSwordItem(Tiers.NETHERITE, new ElementalProperties().setElement(ElementalVariant.ABYSSAL).fireResistant().stacksTo(1).durability(700).attributes(ElementalAxeItem.createAttributes(Tiers.NETHERITE,5f,-3f))));

    public static final DeferredItem<Item> AERIAL_AXE = ITEMS.register("aerial_axe",
            ()-> new ElementalSwordItem(Tiers.NETHERITE, new ElementalProperties().setElement(ElementalVariant.AERIAL).fireResistant().stacksTo(1).durability(700).attributes(ElementalAxeItem.createAttributes(Tiers.NETHERITE,5f,-3f))));

    public static final DeferredItem<Item> TERRESTRIAL_AXE = ITEMS.register("terrestrial_axe",
            ()-> new ElementalSwordItem(Tiers.NETHERITE, new ElementalProperties().setElement(ElementalVariant.TERRESTRIAL).fireResistant().stacksTo(1).durability(700).attributes(ElementalAxeItem.createAttributes(Tiers.NETHERITE,5f,-3f))));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
