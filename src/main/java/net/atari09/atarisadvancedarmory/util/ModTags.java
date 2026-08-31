package net.atari09.atarisadvancedarmory.util;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> TERRESTRIAL_THROWABLE = createTag("terrestrial_throwable");



        private static TagKey<Block> createTag(String name){
            return BlockTags.create(AtarisAdvancedArmory.res(name));
        }
    }

    public static class Items{
        public static final TagKey<Item> FITS_IN_SCABBARD = createTag("fits_in_scabbard");
        public static final TagKey<Item> FITS_IN_QUIVER = createTag("fits_in_quiver");
        public static final TagKey<Item> SCYTHES = createTag("scythes");
        public static final TagKey<Item> ARROW_INFLICTABLE= createTag("arrow_inflictable");




        private static TagKey<Item> createTag(String name){
            return ItemTags.create(AtarisAdvancedArmory.res(name));
        }

    }
}
