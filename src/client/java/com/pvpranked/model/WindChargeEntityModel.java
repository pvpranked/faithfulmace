package com.pvpranked.model;

import com.pvpranked.entity.ModEntities;
import com.pvpranked.entity.WindChargeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.pvpranked.FaithfulMace.MODID;

@Environment(EnvType.CLIENT)
public class WindChargeEntityModel extends EntityModel<WindChargeEntity> {
    private static final int field_48704 = 16;
    private final ModelPart bone;
    private final ModelPart windCharge;
    private final ModelPart wind;

    public static final EntityModelLayer WIND_CHARGE_MODEL_LAYER = new EntityModelLayer(new Identifier(MODID, "wind_charge"), "main");


    public static void initModel() {
        EntityRendererRegistry.INSTANCE.register(ModEntities.WIND_CHARGE, WindChargeEntityRenderer::new);
        // In 1.17, use EntityRendererRegistry.register (seen below) instead of EntityRendererRegistry.INSTANCE.register (seen above)
//        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityTesting.CUBE, (context) -> {
//            return new CubeEntityRenderer(context);
//        });

        EntityModelLayerRegistry.registerModelLayer(WIND_CHARGE_MODEL_LAYER, WindChargeEntityModel::getTexturedModelData);
    }



    public WindChargeEntityModel(ModelPart modelPart) {
        super(RenderLayer::getEntityTranslucent);
        this.bone = modelPart.getChild(EntityModelPartNames.BONE);
        this.wind = this.bone.getChild("wind");
        this.windCharge = this.bone.getChild("wind_charge");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild(EntityModelPartNames.BONE, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData2.addChild(
                "wind",
                ModelPartBuilder.create()
                        .uv(15, 20)
                        .cuboid(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0F))
                        .uv(0, 9)
                        .cuboid(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
        );
        modelPartData2.addChild(
                "wind_charge", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F)
        );
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(WindChargeEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
//        super.setAngles(state);
        this.windCharge.yaw = -animationProgress * 16.0F * (float) (Math.PI / 180.0);
        this.wind.yaw = animationProgress * 16.0F * (float) (Math.PI / 180.0);
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.windCharge.render(matrices, vertices, light, overlay);
        this.wind.render(matrices, vertices, light, overlay);
    }
}