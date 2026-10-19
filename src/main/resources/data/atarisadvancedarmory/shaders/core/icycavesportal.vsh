#version 150

#moj_import <projection.glsl>

in vec3 Position;
in vec2 UV0;
in vec3 Normal;
uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 pos;
out vec2 texCoord0;
out vec3 worldpos;
out vec3 vNormal;

void main() {
gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

pos = projection_from_position(gl_Position);
texCoord0 = UV0;
worldpos = Position;
vNormal = Normal;
}
