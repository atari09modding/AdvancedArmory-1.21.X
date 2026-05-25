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




    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
