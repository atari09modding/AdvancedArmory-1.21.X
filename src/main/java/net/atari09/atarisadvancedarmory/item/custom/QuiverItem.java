package net.atari09.atarisadvancedarmory.item.custom;

import net.atari09.atarisadvancedarmory.component.ContainerItemContent;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.util.ModTags;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class QuiverItem extends ContainerItem{
    public QuiverItem(int containerSize, Properties properties) {
        super(containerSize, properties);
    }


    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stack.getCount() != 1) return false;
        ContainerItemContent content = new ContainerItemContent(stack.get(ModDataComponents.CONTENT).getContent());
        int size = content.getContent().size();

        if(!content.getContent().isEmpty()){
            if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
                ContainerItemContent content2 = new ContainerItemContent(NonNullList.withSize(1, stack.get(ModDataComponents.CONTENT).getContent().get(0).copy()));
                if (content2 == null) {
                    return false;
                } else {
                    if (other.isEmpty()) {
                        // still needs changes
                        for (int i = 0; i < size;i++){
                            if(!content2.getContent().get(size-i-1).isEmpty()){
                                player.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
                                access.set(content2.getContent().get(i));
                                content2.contents().set(i, ItemStack.EMPTY);
                            }
                        }

                    } else {
                        // still needs changes
                        if(!content2.getContent().isEmpty()){

                            for(int i = 0;i<size;i++){

                                if(!other.is(ModTags.Items.FITS_IN_QUIVER)) return false;
                                player.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
                                content2.contents().set(i,other);
                                access.set(ItemStack.EMPTY);
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        return super.overrideStackedOnOther(stack, slot, action, player);
    }
}
