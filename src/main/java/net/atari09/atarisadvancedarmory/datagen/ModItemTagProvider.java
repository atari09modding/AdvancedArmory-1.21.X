package net.atari09.atarisadvancedarmory.datagen;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.item.custom.ModAxeItem;
import net.atari09.atarisadvancedarmory.item.custom.ModMaceItem;
import net.atari09.atarisadvancedarmory.item.custom.ModSwordItem;
import net.atari09.atarisadvancedarmory.item.custom.ScytheItem;
import net.atari09.atarisadvancedarmory.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AtarisAdvancedArmory.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        ModItems.ITEMS.getEntries().stream().forEach((deferredItem)->{
            Item item = deferredItem.get();
            if(item instanceof ModMaceItem){
                tag(ItemTags.MACE_ENCHANTABLE).add(item);
                tag(ModTags.Items.FITS_IN_SCABBARD).add(item);
            }
            if(item instanceof ModAxeItem){
                tag(ItemTags.AXES).add(item);
                tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(item);

            }
            if(item instanceof ModSwordItem){
                tag(ItemTags.SWORDS).add(item);
                tag(ItemTags.SWORD_ENCHANTABLE).add(item);
                tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(item);

            }
            if(item instanceof ScytheItem){
                tag(ItemTags.SWORD_ENCHANTABLE).add(item);
                tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(item);
                tag(ItemTags.HOES).add(item);
                tag(ModTags.Items.SCYTHES).add(item);
            }
        });

        tag(ModTags.Items.FITS_IN_SCABBARD).addTags(
                ItemTags.SWORDS,
                ItemTags.AXES,
                ModTags.Items.SCYTHES)
                .add(Items.BOW).add(Items.CROSSBOW).add(Items.MACE);

        tag(ModTags.Items.FITS_IN_QUIVER).addTag(ItemTags.ARROWS);

        tag(CuriosTags.BACK).add(ModItems.SCABBARD.get());
        tag(CuriosTags.BELT).add(ModItems.SCABBARD.get());


    }

}
