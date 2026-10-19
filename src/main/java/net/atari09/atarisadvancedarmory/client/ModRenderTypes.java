package net.atari09.atarisadvancedarmory.client;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;

public class ModRenderTypes extends RenderType {
    public ModRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }




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
