#version 150

//this shader is called entity because it is an adaptation of the generic entity shader from 1.21.4

//this is not an error, my intellij plugin is just confused
#moj_import <minecraft:fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);
    if (color.a < 0.1) {
        discard;
    }
    color *= vertexColor * ColorModulator;
        //flags don't exist in this version, but this shader is only used for wind charges so we hard code the values
//    #ifndef NO_OVERLAY
//        color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
//    #endif
//    #ifndef EMISSIVE
        color *= lightMapColor;
//    #endif
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
