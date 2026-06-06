package net.atari09.atarisadvancedarmory.util;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.minecraft.client.renderer.item.ItemProperties;

public class ModItemProperties {
    public static void addCustomProperties(){
        ItemProperties.register(ModItems.SPECIAL_SMITHING_TEMPLATE.get(), AtarisAdvancedArmory.res("template_type"),(stack,level,entity,seed)-> {
            SpecialSmithingTemplateType type = stack.get(ModDataComponents.SPECIALSMITHINGTEMPLATETYPES);
            return type == null?0: type.getId();
        });
    }
}
