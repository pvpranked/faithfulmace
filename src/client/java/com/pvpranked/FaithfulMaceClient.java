package com.pvpranked;

import com.pvpranked.entity.ModEntities;
import com.pvpranked.model.WindChargeEntityModel;
import com.pvpranked.model.WindChargeEntityRenderer;
import com.pvpranked.packet.WindChargeExplosionReader;
import com.pvpranked.packet.WindChargeExplosionS2CPacket;
import com.pvpranked.particle.GustParticle;
import com.pvpranked.shader.FaithfulMaceShaders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.network.OffThreadException;

import static com.pvpranked.FaithfulMace.GUST;
import static com.pvpranked.FaithfulMace.MOGGER;

public class FaithfulMaceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FaithfulMaceShaders.registerShaders();

		WindChargeExplosionReader.reader = new ClientWindChargeExplosionReader();
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		EntityRendererRegistry.register(ModEntities.WIND_CHARGE, WindChargeEntityRenderer::new);
		WindChargeEntityModel.initModel();

		// For this example, we will use the end rod particle behaviour.
		ParticleFactoryRegistry.getInstance().register(GUST, GustParticle.Factory::new);

		ClientPlayNetworking.registerGlobalReceiver(FaithfulMace.EXPLOSION_WIND_CHARGE_S2C_PACKET_ID, (client, handler, buf, responseSender) -> {
			try {
				WindChargeExplosionS2CPacket.read(buf).apply(handler);
			} catch (OffThreadException e) {
				//thrown as an internal mechanism by NetworkThreadUtils.forceMainThread
			} catch (Exception e) {
				MOGGER.error("Exception caught reading custom explosion packet!", e);
			}
		});
	}
}