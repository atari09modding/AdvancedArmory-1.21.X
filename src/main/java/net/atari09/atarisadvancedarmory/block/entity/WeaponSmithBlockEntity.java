package net.atari09.atarisadvancedarmory.block.entity;

import net.atari09.atarisadvancedarmory.recipe.ModRecipes;
import net.atari09.atarisadvancedarmory.recipe.WeaponSmithRecipe;
import net.atari09.atarisadvancedarmory.recipe.WeaponSmithRecipeInput;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;

import java.util.Optional;
import java.util.Properties;

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

    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int TEMPLATE_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;


    protected final ContainerData data;
    private int progress = 0;
    private int maxprogress = 72;

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public WeaponSmithBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WEAPONSMITHBLOCK_BE.get(),pos,state);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i){
                    case 0 -> WeaponSmithBlockEntity.this.progress;
                    case 1 -> WeaponSmithBlockEntity.this.maxprogress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0 : WeaponSmithBlockEntity.this.progress = value;
                    case 1 : WeaponSmithBlockEntity.this.maxprogress = value;
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

    public boolean isWorking(){
        return progress > 0;
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
        return null;
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
        if(hasRecipe()){
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

    }

    private Optional<RecipeHolder<WeaponSmithRecipe>> getCurrentRecipe() {
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.WEAPONSMITH_TYPE.get(),
                new WeaponSmithRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT_1), itemHandler.getStackInSlot(INPUT_SLOT_2), itemHandler.getStackInSlot(TEMPLATE_SLOT)), level);
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
    }

    private boolean canInsertItemIntoOutputSlot() {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty();
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<WeaponSmithRecipe>> recipe = getCurrentRecipe();

        if(recipe.isEmpty()){
            return false;
        }
        ItemStack output = recipe.get().value().output();
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
}
