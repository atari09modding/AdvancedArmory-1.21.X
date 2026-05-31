package net.atari09.atarisadvancedarmory.item.custom;

import net.atari09.atarisadvancedarmory.block.entity.WeaponSmithBlockEntity;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.atari09.atarisadvancedarmory.screen.custom.SpecialSmithingTemplateMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpecialSmithingTemplateItem extends Item{
    protected final ContainerData data;
    public static final int COST_SLOT = 0;


    public SpecialSmithingTemplateItem(Properties properties) {
        super(properties);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return 0;
            }

            @Override
            public void set(int i, int value) {

            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    public SpecialSmithingTemplateType getType(ItemStack stack){
        return stack.get(ModDataComponents.SPECIALSMITHINGTEMPLATETYPES.get());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(getType(player.getMainHandItem()) == SpecialSmithingTemplateType.NONE){
            player.openMenu(new SimpleMenuProvider(SpecialSmithingTemplateMenu::new, Component.translatable("item.atarisadvancedarmory.specialsmithingtemplate")));
            //player.getMainHandItem().set(ModDataComponents.SPECIALSMITHINGTEMPLATETYPES.get(), SpecialSmithingTemplateType.TEMPORARY_DEBUG);
        }
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!stack.has(ModDataComponents.SPECIALSMITHINGTEMPLATETYPES.get())) {
            stack.set(ModDataComponents.SPECIALSMITHINGTEMPLATETYPES.get(), SpecialSmithingTemplateType.NONE);
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.get(ModDataComponents.SPECIALSMITHINGTEMPLATETYPES) != null){
            tooltipComponents.add(Component.literal("Type: ").append(Component.translatable("atarisadvancedarmory.specialsmithingtemplatetype."+getType(stack).name).getString()));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }



}
