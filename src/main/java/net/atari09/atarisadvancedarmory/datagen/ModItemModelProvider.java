package net.atari09.atarisadvancedarmory.datagen;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.item.util.SpecialSmithingTemplateType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.SeparateTransformsModel;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }


    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AtarisAdvancedArmory.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        specialSmithingTemplate(ModItems.SPECIAL_SMITHING_TEMPLATE);

        withExistingParent(ModItems.KRYONIC_MACE.getId().getPath(),mcLoc("item/mace"));
        withExistingParent(ModItems.NOXIOUS_MACE.getId().getPath(),mcLoc("item/mace"));
        withExistingParent(ModItems.ABYSSAL_MACE.getId().getPath(),mcLoc("item/mace"));
        withExistingParent(ModItems.AERIAL_MACE.getId().getPath(),mcLoc("item/mace"));
        withExistingParent(ModItems.TERRESTRIAL_MACE.getId().getPath(),mcLoc("item/mace"));

        withExistingParent(ModItems.ABYSSAL_SWORD.getId().getPath(),mcLoc("item/netherite_sword"));
        withExistingParent(ModItems.AERIAL_SWORD.getId().getPath(),mcLoc("item/netherite_sword"));
        withExistingParent(ModItems.INFERNAL_SWORD.getId().getPath(),mcLoc("item/netherite_sword"));
        withExistingParent(ModItems.KRYONIC_SWORD.getId().getPath(),mcLoc("item/netherite_sword"));
        withExistingParent(ModItems.NOXIOUS_SWORD.getId().getPath(),mcLoc("item/netherite_sword"));
        withExistingParent(ModItems.TERRESTRIAL_SWORD.getId().getPath(),mcLoc("item/netherite_sword"));

        splittedTextureChangingItemModel(ModItems.INFERNAL_MACE,3);


    }

    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(AtarisAdvancedArmory.MOD_ID,"block/" + item.getId().getPath()));
    }

    private void specialSmithingTemplate(DeferredItem<Item> item){
        String path = item.getId().getPath();
        ResourceLocation texture = AtarisAdvancedArmory.res(item.getId().withPrefix("item/").getPath());
        ItemModelBuilder base = getBuilder(path).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0",texture);

        int i = 0;
        for(SpecialSmithingTemplateType type :SpecialSmithingTemplateType.values()){
            if(type.check(SpecialSmithingTemplateType.NONE)){
                i++;
                continue;
            }
            String overrideModelName = path + "_" + type.name.toLowerCase();

            // Generate the override model
            getBuilder(overrideModelName)
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", texture)
                    .texture("layer1", type.getTexture());

            // Add override to base model
            base.override()
                    .predicate(AtarisAdvancedArmory.res("template_type"), (float) i)
                    .model(new ModelFile.UncheckedModelFile(
                            AtarisAdvancedArmory.MOD_ID + ":item/" + overrideModelName))
                    .end();
            i++;
        }
    }

    private void splittedTextureChangingItemModel(DeferredItem<Item> item, int countTextures){
        String path = item.getId().getPath();
        ResourceLocation texture = AtarisAdvancedArmory.res(item.getId().withPrefix("item/").getPath());
        ResourceLocation loc = AtarisAdvancedArmory.res("item/"+path);
        ResourceLocation parentLoc = loc.withSuffix("_parent");

        ItemModelBuilder base = getBuilder(path)
                .parent(new ModelFile.UncheckedModelFile("item/generated"));




        for(int i = 0; i<countTextures; i++) {
            String end =(i != 0 ? "_" + (i + 1) : "");
            String overrideModelName = "item/"+ path + end;

            //generate the 2d model the override model can point to
            getBuilder(loc.withSuffix(end+"_2d").getPath())
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", texture.withSuffix(end+"_2d"));

            //generate the same for 3d
            getBuilder(loc.withSuffix(end+"_3d").getPath())
                    .parent(new ModelFile.ExistingModelFile(parentLoc,existingFileHelper))
                    .texture("0", texture.withSuffix(end+"_3d"));

            // Generate the override model
            ItemModelBuilder builder = getBuilder(overrideModelName).parent(new ModelFile.UncheckedModelFile("item/handheld"))
                    .customLoader(SeparateTransformsModelBuilder::begin)
                    .base(withExistingParent(end+"3d",loc.withSuffix(end+"_3d")))
                    .perspective(ItemDisplayContext.GUI,withExistingParent(end+"2d",loc.withSuffix(end+"_2d")))
                    .end();


            // Add override to base model
            base.override()
                    .predicate(AtarisAdvancedArmory.res(path), (float) i)
                    .model(new ModelFile.UncheckedModelFile(AtarisAdvancedArmory.res(overrideModelName)))
                    .end();
        }






    }

    private void item2dModelWithChangingTexture(DeferredItem<Item> item, int countTextures){
        String path = item.getId().getPath();
        ResourceLocation texture = AtarisAdvancedArmory.res(item.getId().withPrefix("item/").getPath());

        ItemModelBuilder base = getBuilder(path+"_2d")
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0",texture.withSuffix("_2d"));


        for(int i = 1; i<countTextures; i++) {
            String end = "_" + (i + 1);// (i != 0 ? "_" + (i + 1) : "");
            String overrideModelName = path + end+"_2d";

            // Generate the override model
            getBuilder(overrideModelName)
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", texture.withSuffix(end+"_2d"));

            // Add override to base model
            base.override()
                    .predicate(AtarisAdvancedArmory.res(path), (float) i)
                    .model(new ModelFile.UncheckedModelFile(
                            AtarisAdvancedArmory.MOD_ID + ":item/" + overrideModelName))
                    .end();
        }
    }

    private void customModelWithChangingTexture(DeferredItem<Item> item,int countTextures){
        String path = item.getId().getPath();
        ResourceLocation texture = AtarisAdvancedArmory.res(item.getId().withPrefix("item/").getPath());
        ResourceLocation parentLoc = AtarisAdvancedArmory.res("item/"+path+"_parent");

        //take the blockbench model from here
        ItemModelBuilder base = getBuilder(path+"_3d").parent(new ModelFile.ExistingModelFile(parentLoc,existingFileHelper));


        for(int i = 1; i<countTextures; i++){
            String end = "_" + (i + 1);//(i!=0 ?"_" + (i+1):"");
            String overrideModelName = path +end+"_3d";


            // Generate the override model
            getBuilder(overrideModelName)
                    .parent(new ModelFile.ExistingModelFile(parentLoc,existingFileHelper))
                    .texture("0", texture.withSuffix(end+"_3d"));

            // Add override to base model
            base.override()
                    .predicate(AtarisAdvancedArmory.res(path), (float) i)
                    .model(new ModelFile.UncheckedModelFile(
                            AtarisAdvancedArmory.MOD_ID + ":item/" + overrideModelName))
                    .end();

        }
    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(DeferredItem<ArmorItem> itemDeferredItem) {
        final String MOD_ID = AtarisAdvancedArmory.MOD_ID; // Change this to your mod id

        if(itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemDeferredItem.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace()  + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                        "item/" + itemDeferredItem.getId().getPath()));
            });
        }
    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(AtarisAdvancedArmory.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(AtarisAdvancedArmory.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
        System.out.println(mcLoc("block/fence_inventory"));
    }


    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(AtarisAdvancedArmory.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
        System.out.println(mcLoc("block/wall_inventory"));
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(AtarisAdvancedArmory.MOD_ID,"item/" + item.getId().getPath()));
    }
}
