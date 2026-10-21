package net.atari09.atarisadvancedarmory.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ModRenderTypes extends RenderType {
    public ModRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
    public static final ResourceLocation NOISE_PATTERN_FROSTY = AtarisAdvancedArmory.res("textures/effect/noise_pattern_frosty.png");


    public static final ShaderStateShard ICY_CAVES_PORTAL_SHADER_STATE =
            new ShaderStateShard(()->ModShaders.icyCavesPortal);

    public static final RenderType ICY_CAVES_PORTAL = create("icy_caves_portal",
            DefaultVertexFormat.POSITION_TEX,
            VertexFormat.Mode.QUADS,
            256,
            false,false,
            RenderType.CompositeState.builder()
                    .setShaderState(ICY_CAVES_PORTAL_SHADER_STATE)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)//makes transparent places on the shader still have clouds/water
                    .setTextureState(new RenderStateShard.TextureStateShard(NOISE_PATTERN_FROSTY,false,false))
                    .createCompositeState(false)
    );

    /*
public static final ResourceLocation NOISE_PATTERN = AtariMod.res("textures/effect/noise_pattern.png");


    public static final ShaderStateShard CANYONS_SHADER_STATE =
            new ShaderStateShard(()->ModShaders.canyons);

    public static final RenderType CANYONS = create("canyons",
            DefaultVertexFormat.POSITION_TEX,
            VertexFormat.Mode.QUADS,
            256,
            false,false,
            RenderType.CompositeState.builder()
                    .setShaderState(CANYONS_SHADER_STATE)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(NOISE_PATTERN,false,false))
                    .createCompositeState(false)
    );*/
}
