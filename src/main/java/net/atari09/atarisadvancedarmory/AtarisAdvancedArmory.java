package net.atari09.atarisadvancedarmory;

import net.atari09.atarisadvancedarmory.block.ModBlocks;
import net.atari09.atarisadvancedarmory.block.client.WeaponSmithBaseBlockRenderer;
import net.atari09.atarisadvancedarmory.block.entity.ModBlockEntities;
import net.atari09.atarisadvancedarmory.component.ModDataComponents;
import net.atari09.atarisadvancedarmory.effect.ModEffects;
import net.atari09.atarisadvancedarmory.item.ModCreativeModeTabs;
import net.atari09.atarisadvancedarmory.item.ModItems;
import net.atari09.atarisadvancedarmory.network.handler.CraftTemplatePacketHandler;
import net.atari09.atarisadvancedarmory.network.handler.StartSmithingPacketHandler;
import net.atari09.atarisadvancedarmory.network.payload.CraftTemplatePacket;
import net.atari09.atarisadvancedarmory.network.payload.StartSmithingPacket;
import net.atari09.atarisadvancedarmory.recipe.ModRecipes;
import net.atari09.atarisadvancedarmory.screen.ModMenuTypes;
import net.atari09.atarisadvancedarmory.screen.custom.SpecialSmithingTemplateScreen;
import net.atari09.atarisadvancedarmory.screen.custom.WeaponSmithScreen;
import net.atari09.atarisadvancedarmory.util.ModItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AtarisAdvancedArmory.MOD_ID)
public class AtarisAdvancedArmory {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "atarisadvancedarmory";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public AtarisAdvancedArmory(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);


        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffects.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModDataComponents.register(modEventBus);
    }

    public static ResourceLocation res(String loc) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, loc);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = AtarisAdvancedArmory.MOD_ID)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            ModItemProperties.addCustomProperties();

        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ModBlockEntities.WEAPONSMITHBLOCK_BE.get(), WeaponSmithBaseBlockRenderer::new);

        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event){
                event.register(ModMenuTypes.WEAPONSMITH_MENU.get(), WeaponSmithScreen::new);
                event.register(ModMenuTypes.SPECIALSMITHINGTEMPLATE_MENU.get(), SpecialSmithingTemplateScreen::new);
        }

        @SubscribeEvent
        public static void registerPayloadHandlers(final RegisterPayloadHandlersEvent event) {
            final PayloadRegistrar registrar = event.registrar("1");
            registrar.playToServer(StartSmithingPacket.TYPE, StartSmithingPacket.STREAM_CODEC, StartSmithingPacketHandler::handle);
            registrar.playToServer(CraftTemplatePacket.TYPE,CraftTemplatePacket.STREAM_CODEC,CraftTemplatePacketHandler::handle);
        }

    }
}
