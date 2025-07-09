#version 150

//this shader is called entity because it is an adaptation of the generic entity shader from 1.21.4

//this is not an error, my intellij plugin is just confused
#moj_import <minecraft:light.glsl>
#moj_import <minecraft:fog.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;

//uniform sampler2D Sampler1;
uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat3 IViewRotMat;
uniform mat4 TextureMat;
uniform int FogShape;

        //not used because NO_CARDINAL_LIGHTING is always true for wind charges
//uniform vec3 Light0_Direction;
//uniform vec3 Light1_Direction;

out float vertexDistance;
out vec4 vertexColor;
out vec4 lightMapColor;
out vec4 overlayColor;
out vec2 texCoord0;
out vec4 normal;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexDistance = fog_distance(ModelViewMat, IViewRotMat * Position, FogShape);
        //flags don't exist in this version, but this shader is only used for wind charges so we hard code the values
//    #ifdef NO_CARDINAL_LIGHTING
        vertexColor = Color;
//    #else
//        vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color);
//    #endif
    lightMapColor = texelFetch(Sampler2, UV2 / 16, 0);
//    overlayColor = texelFetch(Sampler1, UV1, 0);

    texCoord0 = UV0;
        //flags don't exist in this version, but this shader is only used for wind charges so we hard code
//    #ifdef APPLY_TEXTURE_MATRIX
        texCoord0 = (TextureMat * vec4(UV0, 0.0, 1.0)).xy;
//    #endif

    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}
