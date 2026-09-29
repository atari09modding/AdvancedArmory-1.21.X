package net.atari09.atarisadvancedarmory.datagen;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModChestLootTableProvider implements LootTableSubProvider {

    public static final ResourceKey<LootTable> ICY_CAVES_BARREL_REGULAR = ResourceKey.create(
            Registries.LOOT_TABLE, AtarisAdvancedArmory.res("barrels/icy_caves_village_regular"));

    public static final ResourceKey<LootTable> ICY_CAVES_BARREL_RARE = ResourceKey.create(
            Registries.LOOT_TABLE, AtarisAdvancedArmory.res("barrels/icy_caves_village_rare"));

    public static final ResourceKey<LootTable> ICY_CAVES_BARREL_FISH = ResourceKey.create(
            Registries.LOOT_TABLE, AtarisAdvancedArmory.res("barrels/icy_caves_village_fish"));

    private final HolderLookup.Provider lookupProvider;

    public ModChestLootTableProvider(HolderLookup.Provider lookupProvider){
        this.lookupProvider = lookupProvider;
    }


    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(ICY_CAVES_BARREL_REGULAR, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2, 5))
                        .add(LootItem.lootTableItem(Items.COAL)
                                .setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
                        .add(LootItem.lootTableItem(Items.SNOWBALL)
                                .setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 8))))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT)
                                .setWeight(3)))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0, 1))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))));

        output.accept(ICY_CAVES_BARREL_RARE, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(5, 20))
                        .add(LootItem.lootTableItem(Items.DIAMOND)
                                .setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                .setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 8))))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT)
                                .setWeight(5)))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0, 1))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(5))
                        .add(EmptyLootItem.emptyItem().setWeight(95))
                ));

        output.accept(ICY_CAVES_BARREL_FISH, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(5, 20))
                        .add(LootItem.lootTableItem(Items.SALMON)
                                .setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
                        .add(LootItem.lootTableItem(Items.COD)
                                .setWeight(9)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 8))))
                        .add(LootItem.lootTableItem(Items.DRIED_KELP)
                                .setWeight(1)))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0, 1))
                        .add(LootItem.lootTableItem(Items.FISHING_ROD).setWeight(10))
                        .add(LootItem.lootTableItem(Items.COD_BUCKET).setWeight(1))
                        .add(EmptyLootItem.emptyItem().setWeight(89))
                ));
    }
}
