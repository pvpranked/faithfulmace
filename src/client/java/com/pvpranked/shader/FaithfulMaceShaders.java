package com.pvpranked.shader;

import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import static net.minecraft.client.render.RenderPhase.*;

/*
    Note:

    "defines": {
        "values": {
            "ALPHA_CUTOUT": "0.1"
        },
        "flags": [
            "APPLY_TEXTURE_MATRIX",
            "NO_CARDINAL_LIGHTING",
            "NO_OVERLAY"
        ]
    }

    is present in the 1.21.4 version of rendertype_breeze_wind, but flags were not available in 1.20.1, so we just hardcode their values into the versions of entity.fsh and entity.vsh that are used for the wind charge here
 */
public class FaithfulMaceShaders {
    private static final Identifier RENDER_TYPE_BREEZE_WIND_IDENTIFIER = new Identifier("faithfulmace:rendertype_breeze_wind");

    private static ShaderProgram RENDERTYPE_BREEZE_WIND = null;

    public static void registerShaders() {
        CoreShaderRegistrationCallback.EVENT.register((context) -> {
            context.register(
                    RENDER_TYPE_BREEZE_WIND_IDENTIFIER,
                    VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                    program -> RENDERTYPE_BREEZE_WIND = program
            );
        });
    }

    public static ShaderProgram getBreezeWind() {
        return RENDERTYPE_BREEZE_WIND;
    }

    public static final RenderPhase.ShaderProgram BREEZE_WIND_PROGRAM = new RenderPhase.ShaderProgram(FaithfulMaceShaders::getBreezeWind);

    public static RenderLayer getBreezeWindRenderLayer(Identifier texture, float x, float y) {

        return RenderLayer.of(
                "breeze_wind",
                VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                VertexFormat.DrawMode.QUADS,
                1536,
                false,
                true,
                RenderLayer.MultiPhaseParameters.builder()
                        .program(BREEZE_WIND_PROGRAM)
                        .texture(new RenderPhase.Texture(texture, false, false))
                        .texturing(new RenderPhase.OffsetTexturing(x, y))
                        .transparency(TRANSLUCENT_TRANSPARENCY)
                        .cull(DISABLE_CULLING)
                        .lightmap(ENABLE_LIGHTMAP)
                        .overlay(DISABLE_OVERLAY_COLOR)
                        .build(false)
        );
    }
}