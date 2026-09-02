package net.atari09.atarisadvancedarmory.event;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.atari09.atarisadvancedarmory.block.entity.renderer.BottleModel;
import net.atari09.atarisadvancedarmory.block.entity.renderer.SimpleCubeModel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = AtarisAdvancedArmory.MOD_ID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(BottleModel.LAYER_LOCATION, BottleModel::createBodyLayer);
        event.registerLayerDefinition(SimpleCubeModel.LAYER_LOCATION, SimpleCubeModel::createBodyLayer);


    }
}
