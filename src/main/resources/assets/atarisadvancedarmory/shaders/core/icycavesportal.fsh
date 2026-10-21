
#version 150

//#moj_import <projection.glsl>


uniform sampler2D NOISE_PATTERN;

uniform float GameTime;

in vec4 pos;
in vec2 texCoord0;
in vec3 worldpos;
in vec3 vNormal;

out vec4 fragColor;

void main(){
    vec2 scroll1 = vec2(0.4, 0.5);
    vec2 scroll2 = vec2(-0.4, 0.5);
    vec2 scroll3 = vec2(0.0, 0.5);
    vec2 NoiseScale1 = vec2(0.7,0.7)*0.2;
    vec2 NoiseScale2 = vec2(1.5,1.5)*0.2;


    float alpha_pattern_still = texture(NOISE_PATTERN, texCoord0 * NoiseScale1).a;
    float alpha_pattern_move = texture(NOISE_PATTERN, texCoord0 * NoiseScale1 + GameTime * 500. * scroll1).a;
    float alpha_pattern_move2 = texture(NOISE_PATTERN, texCoord0 * NoiseScale1 + GameTime * 500. * scroll1).a;


    vec2 center = vec2(0,0);

    float a = 0.5;
    float b = 1.0;

    float x = texCoord0.x - center.x;
    float y = texCoord0.y - center.y;

    float proximity = (pow( x, 2.0 ) / ( a * a ) + pow( y, 2.0 ) / ( b * b ))-1.0;
    //proximity = smoothstep(0.,0.1,proximity);

    float add_blue = 0.0;
    add_blue = 0.5* clamp(0.9-proximity,0.0,1.0) * texture(NOISE_PATTERN, texCoord0 *  NoiseScale2 + GameTime * 100. * scroll3).a;

    float alpha_shape = 1.0-proximity;
    float alpha = clamp((alpha_shape+ alpha_pattern_still*0.5) * (alpha_pattern_move+alpha_pattern_move2),0.8-proximity,1.0);



    vec3 color = vec3(0.027+add_blue, 0.839-add_blue, 0.878+add_blue);

    fragColor = vec4(color,alpha);
}


