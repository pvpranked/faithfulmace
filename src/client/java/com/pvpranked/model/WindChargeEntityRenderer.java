package com.pvpranked.model;


import com.pvpranked.entity.AbstractWindChargeEntity;
import com.pvpranked.entity.WindChargeEntity;
import com.pvpranked.shader.FaithfulMaceShaders;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.pvpranked.FaithfulMace.MODID;

@Environment(EnvType.CLIENT)
public class WindChargeEntityRenderer extends EntityRenderer<AbstractWindChargeEntity> {
    private static final Identifier TEXTURE = new Identifier(MODID, "textures/entity/projectiles/wind_charge.png");
    private final WindChargeEntityModel model;

    public WindChargeEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new WindChargeEntityModel(context.getPart(WindChargeEntityModel.WIND_CHARGE_MODEL_LAYER));
    }

    @Override
    public Identifier getTexture(AbstractWindChargeEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(AbstractWindChargeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(FaithfulMaceShaders.getBreezeWindRenderLayer(TEXTURE, this.getXOffset(entity.age) % 1.0F, 0.0F));
        float doesntMatterNotUsed = 0; //this parameter isn't used so we'll just set it to 0
        this.model.setAngles((WindChargeEntity) entity, doesntMatterNotUsed, doesntMatterNotUsed, entity.age + tickDelta, doesntMatterNotUsed, doesntMatterNotUsed);

        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    protected float getXOffset(float tickDelta) {
        return tickDelta * 0.03F;
    }
}
