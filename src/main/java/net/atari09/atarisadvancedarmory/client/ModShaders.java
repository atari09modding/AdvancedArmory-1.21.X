package net.atari09.atarisadvancedarmory.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.client.renderer.ShaderInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

@EventBusSubscriber(modid = AtarisAdvancedArmory.MOD_ID,value = Dist.CLIENT)
public class ModShaders {
    public static ShaderInstance icyCavesPortal;


    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), AtarisAdvancedArmory.res("icycavesportal"), DefaultVertexFormat.POSITION_TEX), shader -> icyCavesPortal = shader);
    }
}
