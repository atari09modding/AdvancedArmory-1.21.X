package net.atari09.atarisadvancedarmory.block.entity;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.atari09.atarisadvancedarmory.component.CraftingBlockEntity;
import net.atari09.atarisadvancedarmory.recipe.*;
import net.atari09.atarisadvancedarmory.screen.custom.ArchersTableMenu;
import net.atari09.atarisadvancedarmory.screen.custom.WeaponSmithMenu;
import net.atari09.atarisadvancedarmory.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ArchersTableBlockEntity extends BlockEntity implements MenuProvider, CraftingBlockEntity {
    public final ItemStackHandler itemHandler = new ItemStackHandler(7){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            for(int i : POTION_SLOTS) {
                if (slot == i) {
                    return stack.is(ModTags.Items.ARROW_INFLICTABLE);
                }
            }
            if(slot == INPUT_SLOT){
                return stack.is(Items.ARROW);
            }
            return super.isItemValid(slot, stack);
        }
    };


    protected final ContainerData data;
    public static final int INPUT_SLOT = 0;

    public static final int POTION_SLOT_1 = 1;
    public static final int POTION_SLOT_2 = 2;
    public static final int POTION_SLOT_3 = 3;

    public static final int INGREDIENT_SLOT_1 = 4;
    public static final int INGREDIENT_SLOT_2 = 5;
    public static final int OUTPUT_SLOT = 6;

    public static final List<Integer> POTION_SLOTS = List.of(POTION_SLOT_1, POTION_SLOT_2, POTION_SLOT_3);

    private boolean shouldCraft = false;
    private int progress = 0;
    private int maxprogress = 20;
    private boolean isTimedRecipe = false;

    public ArchersTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ARCHERSTABLE_BE.get(), pos, blockState);

        data = new ContainerData(){

            @Override
            public int get(int i) {
                return switch (i) {
                    case 1 -> hasRecipe() ? 1 : 0;
                    case 2 -> itemHandler.getStackInSlot(INPUT_SLOT).isEmpty()?0:1;
                    case 3 -> progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {

            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ArchersTableMenu(i,inventory,this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory",itemHandler.serializeNBT(registries));
        tag.putInt("archerstable.progress",progress);
        tag.putInt("archerstable.maxprogress",maxprogress);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("archerstable.progress");
        maxprogress = tag.getInt("archerstable.maxprogress");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    public void tick(Level level1, BlockPos pos, BlockState state) {



        if(hasRecipe() && shouldCraft){
            if(isTimedRecipe){
                increaseCraftingProgress();
                setChanged(level,pos,state);

                if(hasCraftingFinished()){
                    craft();
                    resetProgress();

                }
            } else{
                setChanged(level,pos,state);
                craft();
                resetProgress();
            }



        } else {
            resetProgress();
        }
    }

    private boolean hasCraftingFinished() {
        return progress >= maxprogress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private void resetProgress() {
        progress = 0;
        maxprogress = 20;
        shouldCraft = false;
    }

    private void craft(){
        Optional<RecipeHolder<?>> recipe =  getCurrentRecipe();
        ItemStack output = null;
        if(!recipe.isEmpty()){
            if(recipe.get().value() instanceof ArchersTableRecipe r){
                output = r.output();
            }
            int count = itemHandler.getStackInSlot(INPUT_SLOT).getCount();

            itemHandler.extractItem(INPUT_SLOT,count,false);
            itemHandler.extractItem(INGREDIENT_SLOT_1,1,false);
            itemHandler.extractItem(INGREDIENT_SLOT_2,1,false);
            assert output != null;
            output.setCount(count);
            itemHandler.setStackInSlot(OUTPUT_SLOT, output);

        } else {
            List<MobEffectInstance> effects = getPotionEffects();
            int count = itemHandler.getStackInSlot(INPUT_SLOT).getCount();

            PotionContents potion_content = PotionContents.EMPTY;
            for (MobEffectInstance i : effects){
                potion_content = potion_content.withEffectAdded(i);
            }

            output = new ItemStack(Items.TIPPED_ARROW,count);
            output.set(DataComponents.POTION_CONTENTS,potion_content);

            itemHandler.extractItem(INPUT_SLOT,count,false);
            itemHandler.extractItem(POTION_SLOT_1,1,false);
            itemHandler.extractItem(POTION_SLOT_2,1,false);
            itemHandler.extractItem(POTION_SLOT_3,1,false);


            itemHandler.setStackInSlot(OUTPUT_SLOT, output);
        }

    }

    private List<MobEffectInstance> getPotionEffects(){
        List<MobEffectInstance> l = new ArrayList<>();
        for(int i : POTION_SLOTS){
            ItemStack stack = itemHandler.getStackInSlot(i);
            if(stack.has(DataComponents.POTION_CONTENTS)){
                stack.get(DataComponents.POTION_CONTENTS).forEachEffect(l::add);
            }
            if(stack.is(Items.OMINOUS_BOTTLE)){
                Integer integer = (Integer)stack.getOrDefault(DataComponents.OMINOUS_BOTTLE_AMPLIFIER, 0);
                l.add(new MobEffectInstance(MobEffects.BAD_OMEN, 120000, integer, false, false, true));
            }
        }

        return l;
    }


    public boolean hasRecipe(){
        Optional<RecipeHolder<?>> recipe = getCurrentRecipe();

        if(recipe.isEmpty()){
            for(int i : POTION_SLOTS){
                if(itemHandler.getStackInSlot(i).is(ModTags.Items.ARROW_INFLICTABLE) && itemHandler.getStackInSlot(INPUT_SLOT).is(Items.ARROW)){
                    isTimedRecipe = true;
                    return true;
                }
            }
            return false;
        }
        isTimedRecipe = false;
        return canInsertItemIntoOutputSlot();
    }

    private boolean canInsertItemIntoOutputSlot() {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty();
    }

    @Override
    public boolean isWorking() {
        return progress>0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<RecipeHolder<?>> getCurrentRecipe() {
        return (Optional<RecipeHolder<?>>)(Optional<?>) this.level.getRecipeManager().getRecipeFor(ModRecipes.ARCHERSTABLE_TYPE.get(),
                new ArchersTableRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT), itemHandler.getStackInSlot(INGREDIENT_SLOT_1), itemHandler.getStackInSlot(INGREDIENT_SLOT_2)), level);
    }


    @Override
    public void startCrafting(){
        shouldCraft = true;
    }




}
