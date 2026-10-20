
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
    vec2 scroll = vec2(0.4, 0.5);
    vec2 NoiseScale1 = vec2(0.7,0.7)*0.2;

    vec3 dX = dFdx(worldpos);
    vec3 dY = dFdy(worldpos);

    // Das Kreuzprodukt gibt uns die perfekt senkrechte Ausrichtung der Fläche!
    vec3 calcNormal = normalize(cross(dX, dY));
    vec3 absNormal = abs(calcNormal);

    vec2 coords;

    if (absNormal.y > 0.5) {
        // Oben/Unten
        coords = worldpos.xz;
    } else if (absNormal.x > 0.5) {
        // Osten/Westen
        coords = worldpos.yz;
    } else {
        // Norden/Süden
        coords = worldpos.xy;
    }


    float alpha_pattern = texture(NOISE_PATTERN, coords *  NoiseScale1 + GameTime * 500. * scroll).a;


    vec2 center = vec2(0.5,0.5);

    float a = 1.0;
    float b = 2.0;

    float x = coords.x - center.x;
    float y = coords.y - center.y;

    float proximity = (pow( x, 2.0 ) / ( a * a ) + pow( y, 2.0 ) / ( b * b ))-1.0;
    //proximity = smoothstep(0.,0.1,proximity);

    float alpha_shape = 1.0-proximity;

    float alpha = alpha_shape + alpha_pattern;

    fragColor = vec4(0.027, 0.839, 0.878,alpha);
}


