package net.atari09.atarisadvancedarmory.util;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.item.custom.ElementalMaceItem;
import net.atari09.atarisadvancedarmory.item.util.ElementalWeapon;
import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemProperties {
    public static void addCustomProperties(){
        ItemProperties.register(ModItems.SPECIAL_SMITHING_TEMPLATE.get(), AtarisAdvancedArmory.res("template_type"),(stack,level,entity,seed)-> {
            SpecialSmithingTemplateType type = stack.get(ModDataComponents.SPECIALSMITHINGTEMPLATETYPES);
            return type == null?0: type.getId();
        });

        ModItems.ITEMS.getEntries().forEach((item)->{
            if(item.get() instanceof ElementalWeapon ){
                ItemProperties.register(item.get(), AtarisAdvancedArmory.res("infernal_mace"),((stack, level, entity, i) -> {
                    if(stack.has(ModDataComponents.ELEMENTAL_LEVEL)){

                        return stack.get(ModDataComponents.ELEMENTAL_LEVEL)-1;
                    } else{
                        return 0;
                    }
                }));
            }

        });




    }
}
