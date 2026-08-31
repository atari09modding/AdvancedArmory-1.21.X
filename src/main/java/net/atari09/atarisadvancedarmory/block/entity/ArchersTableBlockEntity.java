package net.atari09.atarisadvancedarmory.block.entity;

import net.atari09.atarisadvancedarmory.screen.custom.ArchersTableMenu;
import net.atari09.atarisadvancedarmory.screen.custom.WeaponSmithMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ArchersTableBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(7){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
            }
        }
    };


    protected final ContainerData data;
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    public static final int POTION_SLOT_1 = 2;
    public static final int POTION_SLOT_2 = 3;
    public static final int POTION_SLOT_3 = 4;
    public static final int INGREDIENT_SLOT_1 = 5;
    public static final int INGREDIENT_SLOT_2 = 6;



    public ArchersTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ARCHERSTABLE_BE.get(), pos, blockState);

        data = new ContainerData(){

            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {

            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Archer's Table");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ArchersTableMenu(i,inventory,this,this.data);
    }



}
