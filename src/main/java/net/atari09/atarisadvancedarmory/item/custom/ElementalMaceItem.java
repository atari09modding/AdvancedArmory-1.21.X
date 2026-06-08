package net.atari09.atarisadvancedarmory.item.custom;

import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.item.util.ElementalProperties;
import net.atari09.atarisadvancedarmory.item.util.ElementalVariant;
import net.atari09.atarisadvancedarmory.item.util.ElementalWeapon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ElementalMaceItem extends ModMaceItem implements ElementalWeapon {
    private final ElementalVariant element;
    public ElementalMaceItem(Properties properties) {
        super(properties);
        this.element = ((ElementalProperties) properties).element;


    }

    @Override
    public int getElementalLevel(ItemStack stack) {
        if(stack.has(ModDataComponents.ELEMENTAL_LEVEL.get())){
            return stack.get(ModDataComponents.ELEMENTAL_LEVEL.get());

        }
        return 0;
    }

    @Override
    public ElementalVariant getElement() {
        return this.element;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if(!stack.has(ModDataComponents.ELEMENTAL_LEVEL.get())){
            stack.set(ModDataComponents.ELEMENTAL_LEVEL.get(),1);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.element.abilitylv1.accept(stack,target,attacker);
        if(stack.has(ModDataComponents.ELEMENTAL_LEVEL)){
            if(stack.get(ModDataComponents.ELEMENTAL_LEVEL)>=3){
                this.element.abilitylv3.accept(stack,target,attacker);
            }
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if(stack.has(ModDataComponents.ELEMENTAL_LEVEL.get())){
            int elementalLevel = stack.get(ModDataComponents.ELEMENTAL_LEVEL.get());
            if(elementalLevel >=2&&!level.isClientSide()){
                this.element.abilitylv2.accept(level,player,usedHand);
            }
        }

        return super.use(level, player, usedHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.has(ModDataComponents.ELEMENTAL_LEVEL.get())){
            tooltipComponents.add(Component.literal("Level:" + stack.get(ModDataComponents.ELEMENTAL_LEVEL.get())));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
