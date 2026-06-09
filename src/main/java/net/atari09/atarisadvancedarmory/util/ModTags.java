package net.atari09.atarisadvancedarmory.util;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> TERRESTRIAL_THROWABLE = createTag("terresttrial_throwable");

        private static TagKey<Block> createTag(String name){
            return BlockTags.create(AtarisAdvancedArmory.res(name));
        }
    }
}
