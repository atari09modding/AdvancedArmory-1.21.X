package net.atari09.atarisadvancedarmory.item;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab>CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AtarisAdvancedArmory.MOD_ID);

public static final Supplier<CreativeModeTab> SMITHING_TAB = CREATIVE_MODE_TAB.register("smithing_tab",
        ()-> CreativeModeTab.builder()
                .icon(()->new ItemStack(ModItems.WEAPONSMITHBLOCKITEM.get()))
                .title(Component.translatable("creativetab.atarisadvancedarmory.smithing_tab"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(ModItems.WEAPONSMITHBLOCKITEM);
                })
                .build());
    public static final Supplier<CreativeModeTab> ARMORY_TAB = CREATIVE_MODE_TAB.register("armory_tab",
            ()-> CreativeModeTab.builder()
                    .icon(()->new ItemStack(ModItems.INFERNAL_MACE.get())) //change this later
                    .title(Component.translatable("creativetab.atarisadvancedarmory.armory_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.SPECIAL_SMITHING_TEMPLATE);


                        //BROADSWORDS
                        output.accept(ModItems.WOODEN_BROADSWORD);
                        output.accept(ModItems.STONE_BROADSWORD);
                        output.accept(ModItems.GOLDEN_BROADSWORD);
                        output.accept(ModItems.IRON_BROADSWORD);
                        output.accept(ModItems.DIAMOND_BROADSWORD);
                        output.accept(ModItems.NETHERITE_BROADSWORD);


                        //RAPIERS
                        output.accept(ModItems.WOODEN_RAPIER);
                        output.accept(ModItems.STONE_BROADSWORD);
                        output.accept(ModItems.GOLDEN_RAPIER);
                        output.accept(ModItems.IRON_RAPIER);
                        output.accept(ModItems.DIAMOND_RAPIER);
                        output.accept(ModItems.NETHERITE_RAPIER);

                        //BATTLEAXES
                        output.accept(ModItems.WOODEN_BATTLEAXE);
                        output.accept(ModItems.STONE_BATTLEAXE);
                        output.accept(ModItems.GOLDEN_BATTLEAXE);
                        output.accept(ModItems.IRON_BATTLEAXE);
                        output.accept(ModItems.DIAMOND_BATTLEAXE);
                        output.accept(ModItems.NETHERITE_BATTLEAXE);

                        //SCYTHES
                        output.accept(ModItems.WOODEN_SCYTHE);
                        output.accept(ModItems.STONE_SCYTHE);
                        output.accept(ModItems.GOLDEN_SCYTHE);
                        output.accept(ModItems.IRON_SCYTHE);
                        output.accept(ModItems.DIAMOND_SCYTHE);
                        output.accept(ModItems.NETHERITE_SCYTHE);


                        //ELEMENTAL MACES
                        output.accept(ModItems.INFERNAL_MACE);
                        output.accept(ModItems.KRYONIC_MACE);
                        output.accept(ModItems.NOXIOUS_MACE);
                        output.accept(ModItems.ABYSSAL_MACE);
                        output.accept(ModItems.AERIAL_MACE);
                        output.accept(ModItems.TERRESTRIAL_MACE);

                        //ELEMENTAL SWORDS
                        output.accept(ModItems.ABYSSAL_SWORD);
                        output.accept(ModItems.AERIAL_SWORD);
                        output.accept(ModItems.INFERNAL_SWORD);
                        output.accept(ModItems.KRYONIC_SWORD);
                        output.accept(ModItems.NOXIOUS_SWORD);
                        output.accept(ModItems.TERRESTRIAL_SWORD);

                        //ELEMENTAL AXES
                        output.accept(ModItems.ABYSSAL_AXE);
                        output.accept(ModItems.AERIAL_AXE);
                        output.accept(ModItems.INFERNAL_AXE);
                        output.accept(ModItems.KRYONIC_AXE);
                        output.accept(ModItems.NOXIOUS_AXE);
                        output.accept(ModItems.TERRESTRIAL_AXE);



                    })
                    .build());

    public static final Supplier<CreativeModeTab> OTHER = CREATIVE_MODE_TAB.register("other",
            ()-> CreativeModeTab.builder()
                    .icon(()->new ItemStack(ModItems.SCABBARD.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.SCABBARD);
                        output.accept(ModItems.QUIVER);
                    }).build());




    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
