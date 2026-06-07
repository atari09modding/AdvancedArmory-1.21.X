package net.atari09.atarisadvancedarmory.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.EnchantCommand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Collection;

@Mixin(EnchantCommand.class)
public class EnchantCommandMixin {
    private static final DynamicCommandExceptionType ERROR_NO_ITEM = new DynamicCommandExceptionType((p_304207_) -> Component.translatableEscape("commands.enchant.failed.itemless", new Object[]{p_304207_}));
    private static final DynamicCommandExceptionType ERROR_NOT_LIVING_ENTITY = new DynamicCommandExceptionType((p_304205_) -> Component.translatableEscape("commands.enchant.failed.entity", new Object[]{p_304205_}));
    private static final SimpleCommandExceptionType ERROR_NOTHING_HAPPENED = new SimpleCommandExceptionType(Component.translatable("commands.enchant.failed"));
    private static final DynamicCommandExceptionType ERROR_INCOMPATIBLE = new DynamicCommandExceptionType((p_304206_) -> Component.translatableEscape("commands.enchant.failed.incompatible", new Object[]{p_304206_}));


    /**
     * @author me
     * @reason because
     */
    @Overwrite
    private static int enchant(CommandSourceStack source, Collection<? extends Entity> targets, Holder<Enchantment> p_enchantment, int level) throws CommandSyntaxException {
        Enchantment enchantment = (Enchantment)p_enchantment.value();

        int i = 0;

        for(Entity entity : targets) {
            if (entity instanceof LivingEntity livingentity) {
                ItemStack itemstack = livingentity.getMainHandItem();
                if (!itemstack.isEmpty()) {
                    if (itemstack.supportsEnchantment(p_enchantment) && EnchantmentHelper.isEnchantmentCompatible(EnchantmentHelper.getEnchantmentsForCrafting(itemstack).keySet(), p_enchantment)) {
                        itemstack.enchant(p_enchantment, level);
                        ++i;
                    } else if (targets.size() == 1) {
                        throw ERROR_INCOMPATIBLE.create(itemstack.getItem().getName(itemstack).getString());
                    }
                } else if (targets.size() == 1) {
                    throw ERROR_NO_ITEM.create(livingentity.getName().getString());
                }
            } else if (targets.size() == 1) {
                throw ERROR_NOT_LIVING_ENTITY.create(entity.getName().getString());
            }
        }

        if (i == 0) {
            throw ERROR_NOTHING_HAPPENED.create();
        } else {
            if (targets.size() == 1) {
                source.sendSuccess(() -> Component.translatable("commands.enchant.success.single", new Object[]{Enchantment.getFullname(p_enchantment, level), ((Entity)targets.iterator().next()).getDisplayName()}), true);
            } else {
                source.sendSuccess(() -> Component.translatable("commands.enchant.success.multiple", new Object[]{Enchantment.getFullname(p_enchantment, level), targets.size()}), true);
            }

            return i;
        }
    }
    
}


