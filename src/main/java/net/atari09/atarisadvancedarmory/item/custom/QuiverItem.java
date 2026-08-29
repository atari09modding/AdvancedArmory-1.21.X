package net.atari09.atarisadvancedarmory.item.custom;

import net.atari09.atarisadvancedarmory.component.ContainerItemContent;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.screen.ItemListTooltipComponent;
import net.atari09.atarisadvancedarmory.util.ModTags;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class QuiverItem extends ContainerItem{
    public QuiverItem(Properties properties) {
        super(5, properties);
    }


    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stack.getCount() != 1) return false;
        ContainerItemContent content = new ContainerItemContent(stack.get(ModDataComponents.CONTENT).getContent());
        int size = content.getContent().size();

        if(!content.getContent().isEmpty()){
            if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
                ContainerItemContent content2 = content.getCopy();
                if (content2 == null) {
                    return false;
                } else {
                    if (other.isEmpty()) {
                        access.set(tryRemove(content2,player));
                    } else {

                        if(!content2.getContent().isEmpty()){
                            access.set(tryInsert(content2,other,player));
                        }
                    }
                    stack.set(ModDataComponents.CONTENT, content2);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (stack.getCount() != 1 || action != ClickAction.SECONDARY) {

            return false;
        } else {
            ContainerItemContent content = new ContainerItemContent(stack.get(ModDataComponents.CONTENT).getContent());

            if(!content.getContent().isEmpty()){
                ContainerItemContent content2 = content.getCopy();
                ItemStack stack1 = slot.getItem();
                if (stack1.isEmpty()) {
                    slot.set(tryRemove(content2,player));
                } else if (stack1.is(ModTags.Items.FITS_IN_QUIVER)) {
                    slot.set(tryInsert(content2,stack1,player));
                }
                stack.set(ModDataComponents.CONTENT, content2);
                return true;
            }
            return true;
        }
    }

    public static ItemStack tryInsert(ContainerItemContent content, ItemStack insert,Player player){
        for(int i = 0; i< content.contents().size();i++){
            if (!content.getContent().get(i).isEmpty()) continue;
            player.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
            content.contents().set(i,insert);
            return ItemStack.EMPTY;
        }
        return insert;
    }

    public static ItemStack tryRemove(ContainerItemContent content,Player player,boolean doRemove){
        ContainerItemContent content2 = content.getCopy();
        for(int i = content.getContent().size()-1; i >=0;i--){
            if (content.getContent().get(i).isEmpty()) continue;
            player.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
            if(doRemove) content.contents().set(i, ItemStack.EMPTY);
            return content2.getContent().get(i);
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack tryRemove(ContainerItemContent content,Player player){
        return tryRemove(content,player,true);
    }





    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        if(stack.has(ModDataComponents.CONTENT)){
            if(!stack.get(ModDataComponents.CONTENT).getContent().isEmpty()){
                return Optional.of(new ItemListTooltipComponent(stack.get(ModDataComponents.CONTENT).getContent(),true));
            }
        }

        return super.getTooltipImage(stack);
    }




}
