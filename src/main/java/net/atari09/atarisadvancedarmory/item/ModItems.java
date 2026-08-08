package net.atari09.atarisadvancedarmory.item;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.ModBlocks;
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


    // ELEMENTAL MACE

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


    //ELEMENTAL SWORD

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


    //ELEMENTAL AXE

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


    //BROADSWORD
    public static final DeferredItem<Item> WOODEN_BROADSWORD = ITEMS.register("wooden_broadsword",
            ()->new ModSwordItem(Tiers.WOOD,new Item.Properties().stacksTo(1).attributes(SwordItem.createAttributes(Tiers.WOOD, 4, -2.4F))));

    public static final DeferredItem<Item> STONE_BROADSWORD = ITEMS.register("stone_broadsword",
            ()->new ModSwordItem(Tiers.STONE,new Item.Properties().stacksTo(1).attributes(SwordItem.createAttributes(Tiers.STONE, 4, -2.4F))));

    public static final DeferredItem<Item> IRON_BROADSWORD = ITEMS.register("iron_broadsword",
            ()->new ModSwordItem(Tiers.IRON,new Item.Properties().stacksTo(1).attributes(SwordItem.createAttributes(Tiers.IRON, 4, -2.4F))));

    public static final DeferredItem<Item> GOLDEN_BROADSWORD = ITEMS.register("golden_broadsword",
            ()->new ModSwordItem(Tiers.GOLD,new Item.Properties().stacksTo(1).attributes(SwordItem.createAttributes(Tiers.GOLD, 4, -2.4F))));

    public static final DeferredItem<Item> DIAMOND_BROADSWORD = ITEMS.register("diamond_broadsword",
            ()->new ModSwordItem(Tiers.DIAMOND,new Item.Properties().stacksTo(1).attributes(SwordItem.createAttributes(Tiers.DIAMOND, 4, -2.4F))));

    public static final DeferredItem<Item> NETHERITE_BROADSWORD = ITEMS.register("netherite_broadsword",
            ()->new ModSwordItem(Tiers.NETHERITE,new Item.Properties().stacksTo(1).fireResistant().attributes(SwordItem.createAttributes(Tiers.NETHERITE, 4, -2.4F))));


    //RAPIER
    public static final DeferredItem<Item> WOODEN_RAPIER = ITEMS.register("wooden_rapier",
            ()->new ModSwordItem(Tiers.WOOD,new Item.Properties().stacksTo(1).attributes(SwordItem.createAttributes(Tiers.WOOD, 3, -1.8F))));

    public static final DeferredItem<Item> STONE_RAPIER = ITEMS.register("stone_rapier",
            ()->new ModSwordItem(Tiers.STONE,new Item.Properties().stacksTo(1).attributes(SwordItem.createAttributes(Tiers.STONE, 3, -1.8F))));

    public static final DeferredItem<Item> IRON_RAPIER = ITEMS.register("iron_rapier",
            ()->new ModSwordItem(Tiers.IRON,new Item.Properties().stacksTo(1).attributes(SwordItem.createAttributes(Tiers.IRON, 3, -1.8F))));

    public static final DeferredItem<Item> GOLDEN_RAPIER = ITEMS.register("golden_rapier",
            ()->new ModSwordItem(Tiers.GOLD,new Item.Properties().stacksTo(1).attributes(SwordItem.createAttributes(Tiers.GOLD, 3, -1.8F))));

    public static final DeferredItem<Item> DIAMOND_RAPIER = ITEMS.register("diamond_rapier",
            ()->new ModSwordItem(Tiers.DIAMOND,new Item.Properties().stacksTo(1).attributes(SwordItem.createAttributes(Tiers.DIAMOND, 3, -1.8F))));

    public static final DeferredItem<Item> NETHERITE_RAPIER = ITEMS.register("netherite_rapier",
        ()->new ModSwordItem(Tiers.NETHERITE,new Item.Properties().stacksTo(1).fireResistant().attributes(SwordItem.createAttributes(Tiers.NETHERITE, 3, -1.8F))));


    //SCYTHE
    public static final DeferredItem<Item> WOODEN_SCYTHE = ITEMS.register("wooden_scythe",
            ()->new ScytheItem(Tiers.WOOD,new Item.Properties().stacksTo(1).attributes(ScytheItem.createAttributes(Tiers.WOOD, 4,-3.2F))));

    public static final DeferredItem<Item>STONE_SCYTHE = ITEMS.register("stone_scythe",
            ()->new ScytheItem(Tiers.STONE,new Item.Properties().stacksTo(1).attributes(ScytheItem.createAttributes(Tiers.STONE, 4,-3.2F))));

    public static final DeferredItem<Item> GOLDEN_SCYTHE = ITEMS.register("golden_scythe",
            ()->new ScytheItem(Tiers.GOLD,new Item.Properties().stacksTo(1).attributes(ScytheItem.createAttributes(Tiers.GOLD, 4,-3.2F))));

    public static final DeferredItem<Item> IRON_SCYTHE = ITEMS.register("iron_scythe",
            ()->new ScytheItem(Tiers.IRON,new Item.Properties().stacksTo(1).attributes(ScytheItem.createAttributes(Tiers.IRON, 4,-3.2F))));

    public static final DeferredItem<Item> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe",
            ()->new ScytheItem(Tiers.DIAMOND,new Item.Properties().stacksTo(1).attributes(ScytheItem.createAttributes(Tiers.DIAMOND, 4,-3.2F))));

    public static final DeferredItem<Item> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
            ()->new ScytheItem(Tiers.NETHERITE,new Item.Properties().stacksTo(1).fireResistant().attributes(ScytheItem.createAttributes(Tiers.NETHERITE, 4,-3.2F))));


    //BATTLEAXE

    public static final DeferredItem<Item> WOODEN_BATTLEAXE = ITEMS.register("wooden_battleaxe",
            ()->new ModAxeItem(Tiers.WOOD,new Item.Properties().stacksTo(1).attributes(ModAxeItem.createAttributes(Tiers.WOOD,7.0F,-3.2F))));

    public static final DeferredItem<Item> STONE_BATTLEAXE = ITEMS.register("stone_battleaxe",
            ()->new ModAxeItem(Tiers.STONE,new Item.Properties().stacksTo(1).attributes(ModAxeItem.createAttributes(Tiers.STONE,7.0F,-3.2F))));

    public static final DeferredItem<Item> GOLDEN_BATTLEAXE = ITEMS.register("golden_battleaxe",
            ()->new ModAxeItem(Tiers.GOLD,new Item.Properties().stacksTo(1).attributes(ModAxeItem.createAttributes(Tiers.GOLD,7.0F,-3.2F))));

    public static final DeferredItem<Item> IRON_BATTLEAXE = ITEMS.register("iron_battleaxe",
            ()->new ModAxeItem(Tiers.IRON,new Item.Properties().stacksTo(1).attributes(ModAxeItem.createAttributes(Tiers.IRON,7.0F,-3.2F))));

    public static final DeferredItem<Item> DIAMOND_BATTLEAXE = ITEMS.register("diamond_battleaxe",
            ()->new ModAxeItem(Tiers.DIAMOND,new Item.Properties().stacksTo(1).attributes(ModAxeItem.createAttributes(Tiers.DIAMOND,7.0F,-3.2F))));

    public static final DeferredItem<Item> NETHERITE_BATTLEAXE = ITEMS.register("netherite_battleaxe",
            ()->new ModAxeItem(Tiers.NETHERITE,new Item.Properties().stacksTo(1).fireResistant().attributes(ModAxeItem.createAttributes(Tiers.NETHERITE,7.0F,-3.2F))));




    //OTHER

    public static final DeferredItem<Item> SCABBARD = ITEMS.register("scabbard",
            ()->new ScabbardItem(new Item.Properties().stacksTo(1)));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
