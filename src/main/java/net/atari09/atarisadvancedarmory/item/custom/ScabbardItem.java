package net.atari09.atarisadvancedarmory.item.custom;

import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.component.ContainerItemContent;
import net.atari09.atarisadvancedarmory.screen.ItemTooltipComponent;
import net.atari09.atarisadvancedarmory.util.ModTags;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Optional;

public class ScabbardItem extends ContainerItem{
    public ScabbardItem(Properties properties) {
        super(1, properties);
    }


    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {

        if (stack.getCount() != 1) return false;


        ContainerItemContent content = new ContainerItemContent(stack.get(ModDataComponents.CONTENT).getContent());
        //System.out.println("a");

        if(!content.getContent().isEmpty()){
            if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
                ContainerItemContent content2 = new ContainerItemContent(NonNullList.withSize(1,stack.get(ModDataComponents.CONTENT).getContent().get(0).copy()));
                //System.out.println("b");


                if (content2 == null) {

                    return false;
                } else {
                    //BundleContents.Mutable bundlecontents$mutable = new BundleContents.Mutable(bundlecontents);
                    if (other.isEmpty()) {
                        if(!content2.getContent().isEmpty()){
                            player.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
                            access.set(content2.getContent().get(0));
                            content2.contents().set(0, ItemStack.EMPTY);
                        }
                    } else {

                        if(!content2.getContent().isEmpty()){

                            if(content2.getContent().get(0).isEmpty()){

                                if(!other.is(ModTags.Items.FITS_IN_SCABBARD)) return false;
                                player.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
                                content2.contents().set(0,other);
                                access.set(ItemStack.EMPTY);



                            }
                        }

                    }

                    stack.set(ModDataComponents.CONTENT, content2);
                    return true;
                }
            } else {
                return false;
            }

        }
        return true;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {

        if (stack.getCount() != 1 || action != ClickAction.SECONDARY) {

            return false;
        } else {
            ContainerItemContent content = new ContainerItemContent(stack.get(ModDataComponents.CONTENT).getContent());

            if(!content.getContent().isEmpty()){
                ContainerItemContent content2 = new ContainerItemContent(NonNullList.withSize(1,stack.get(ModDataComponents.CONTENT).getContent().get(0)));
                ItemStack stack1 = slot.getItem();
                if (stack1.isEmpty()) {
                    player.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
                    content2.contents().set(0, ItemStack.EMPTY);
                    slot.set(content.contents().get(0));

                } else if (stack1.is(ModTags.Items.FITS_IN_SCABBARD)) {
                    if (content2.getContent().get(0).isEmpty()) {
                        player.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
                        content2.contents().set(0,stack1);
                        slot.set(ItemStack.EMPTY);
                    }
                }

                stack.set(ModDataComponents.CONTENT, content2);
                return true;
            }
            return true;
        }

    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {

        if (stack.has(ModDataComponents.CONTENT)){
            if(!stack.get(ModDataComponents.CONTENT).getContent().isEmpty()){
                ItemStack content = stack.get(ModDataComponents.CONTENT).getContent().get(0);
               return Optional.of(new ItemTooltipComponent(content,true));
            }
        }
        return Optional.empty();

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {


        if(stack.has(ModDataComponents.CONTENT)){
            if(!stack.get(ModDataComponents.CONTENT).getContent().isEmpty()){
                ItemStack content = stack.get(ModDataComponents.CONTENT).getContent().get(0);
                tooltipComponents.add(Component.literal("Contains: "+content.getItem().getName(content).getString()));
            }


        }


        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
