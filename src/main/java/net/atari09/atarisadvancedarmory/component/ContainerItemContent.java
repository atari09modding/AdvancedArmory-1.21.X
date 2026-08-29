package net.atari09.atarisadvancedarmory.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public record ContainerItemContent(NonNullList<ItemStack> contents) implements ContainerAccessor {

    public static final Codec<ContainerItemContent> CODEC =
            NonNullList.codecOf(ItemStack.OPTIONAL_CODEC)
                    .xmap(ContainerItemContent::new, ContainerItemContent::getContent);

    public static final StreamCodec<RegistryFriendlyByteBuf, ContainerItemContent> STREAM_CODEC = ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list())
            .map(NonNullList::copyOf, Function.identity()).map(ContainerItemContent::new,ContainerItemContent::contents);

    @Override
    public NonNullList<ItemStack> getContent() {
        return contents;
    }


    public ContainerItemContent getCopy(){
        NonNullList<ItemStack> copyContent = NonNullList.withSize(getContent().size(), ItemStack.EMPTY);

        for(int i = 0; i<getContent().size();i++){
            if(!getContent().get(i).isEmpty()){
                copyContent.set(i,getContent().get(i).copy());
            }
        }
        return new ContainerItemContent(copyContent);
    }
}
