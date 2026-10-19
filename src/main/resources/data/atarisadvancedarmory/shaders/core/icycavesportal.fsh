
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

    fragColor = vec4(0.0,0.0,0.0,0.0);
}