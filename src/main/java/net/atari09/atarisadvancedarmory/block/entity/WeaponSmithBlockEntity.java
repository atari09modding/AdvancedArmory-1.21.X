package net.atari09.atarisadvancedarmory.block.entity;

import ca.weblite.objc.Client;
import net.atari09.atarisadvancedarmory.block.custom.WeaponSmithBaseBlock;
import net.atari09.atarisadvancedarmory.item.custom.SpecialSmithingTemplateItem;
import net.atari09.atarisadvancedarmory.recipe.*;
import net.atari09.atarisadvancedarmory.screen.custom.WeaponSmithMenu;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;

import java.util.Optional;
import java.util.Random;
import java.util.random.RandomGenerator;

public class WeaponSmithBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(4){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
            }
        }
    };

    public static final int INPUT_SLOT_1 = 0;
    public static final int INPUT_SLOT_2 = 1;
    public static final int TEMPLATE_SLOT = 2;
    public static final int OUTPUT_SLOT = 3;
    private float outputRotation;
    private float outputHeight;


    protected final ContainerData data;
    private int progress = 0;
    private int maxprogress = 72;
    private boolean shouldCraft = false;
    private int hasRecipe = 0;

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public WeaponSmithBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WEAPONSMITHBLOCK_BE.get(),pos,state);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i){
                    case 0 -> WeaponSmithBlockEntity.this.progress;
                    case 1 -> WeaponSmithBlockEntity.this.maxprogress;
                    case 2 -> WeaponSmithBlockEntity.this.hasRecipe;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0 : WeaponSmithBlockEntity.this.progress = value;
                    case 1 : WeaponSmithBlockEntity.this.maxprogress = value;
                    case 2 : WeaponSmithBlockEntity.this.hasRecipe = value;
                }

            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",20, this::predicate));
    }

    private PlayState predicate(AnimationState<WeaponSmithBlockEntity> state) {
        return state.setAndContinue( isWorking() ? RawAnimation.begin().thenLoop("animation_smithing") : RawAnimation.begin().thenLoop("animation_idle"));
    }

    public float getRenderingRotationForOutput() {
        outputRotation += 0.5f;
        if(outputRotation >= 360) {
            outputRotation = 0;
        }
        return outputRotation;
    }

    public float getRenderingExtraHeightForOutput() {
        outputHeight += 0.05f;
        if(outputHeight >= 18) {
            outputHeight = 0;
        }

        return (float) (0.2 * Math.sin(Math.toRadians(outputHeight*10)));
    }




    public boolean isWorking(){
        return this.getBlockState().getValue(WeaponSmithBaseBlock.WORKING);
    }



    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i<itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.atarisadvancedarmory.weaponsmithbaseblock");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new WeaponSmithMenu(i,inventory,this,this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory",itemHandler.serializeNBT(registries));
        tag.putInt("weaponsmith.progress",progress);
        tag.putInt("weaponsmith.maxprogress",maxprogress);

        super.saveAdditional(tag,registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("weaponsmith.progress");
        maxprogress = tag.getInt("weaponsmith.maxprogress");
    }

    public void tick(Level level, BlockPos pos, BlockState blockState){
        hasRecipe = hasRecipe()?1:0;
        level.setBlock(pos,blockState.setValue(WeaponSmithBaseBlock.WORKING, progress>0),3);







        if(hasRecipe() && shouldCraft){
            increaseCraftingProgress();
            setChanged(level,pos,blockState);

            if(hasCraftingFinished()){
                craft();
                resetProgress();

            }

        } else {
            resetProgress();
        }
    }

    private void craft() {
        Optional<RecipeHolder<?>> recipe = getCurrentRecipe();
        ItemStack output = null;
        if(recipe.get().value() instanceof WeaponSmithRecipe v){
            output = v.output();
        }
        if(recipe.get().value() instanceof WeaponSmithTemplateTypeRecipe v){
            output = v.output();
        }


        itemHandler.extractItem(INPUT_SLOT_1,1,false);
        itemHandler.extractItem(INPUT_SLOT_2,1,false);
        itemHandler.extractItem(TEMPLATE_SLOT,1,false);
        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                output.getCount()));

    }

    @SuppressWarnings("unchecked")
    private Optional<RecipeHolder<?>> getCurrentRecipe() {
        if(!(itemHandler.getStackInSlot(TEMPLATE_SLOT).getItem() instanceof SpecialSmithingTemplateItem templateItem)){
            return (Optional<RecipeHolder<?>>)(Optional<?>) this.level.getRecipeManager().getRecipeFor(ModRecipes.WEAPONSMITH_TYPE.get(),
                    new WeaponSmithRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT_1), itemHandler.getStackInSlot(INPUT_SLOT_2), itemHandler.getStackInSlot(TEMPLATE_SLOT)), level);
        }
        return (Optional<RecipeHolder<?>>)(Optional<?>) this.level.getRecipeManager().getRecipeFor(ModRecipes.WEAPONSMITH_TT_TYPE.get(),
                new WeaponSmithRecipeTemplateTypeInput(itemHandler.getStackInSlot(INPUT_SLOT_1), itemHandler.getStackInSlot(INPUT_SLOT_2), templateItem.getType(itemHandler.getStackInSlot(TEMPLATE_SLOT))), level);
    }

    private boolean hasCraftingFinished() {
        return progress >= maxprogress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private void resetProgress() {
        progress = 0;
        maxprogress = 72;
        shouldCraft = false;
    }

    private boolean canInsertItemIntoOutputSlot() {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty();
    }

    public boolean hasRecipe() {
        Optional<RecipeHolder<?>> recipe = getCurrentRecipe();

        if(recipe.isEmpty()){
            return false;
        }
        return canInsertItemIntoOutputSlot();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void startCrafting() {
        shouldCraft = true;
    }

    public interface WeaponSmithingRecipe<T extends RecipeInput> extends Recipe<T>{
    }
}
