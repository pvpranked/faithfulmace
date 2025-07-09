package com.pvpranked.mixin.client;

import com.pvpranked.FaithfulMace;
import com.pvpranked.particle.DustPillarParticle;
import com.pvpranked.particle.GustEmitterParticle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public abstract class CustomParticleTypeFactoryMixin {
	@Shadow protected abstract <T extends ParticleEffect> void registerFactory(ParticleType<T> type, ParticleFactory<T> factory);

	@Inject(at = @At("HEAD"), method = "registerDefaultFactories")
	private void init(CallbackInfo info) {
		this.registerFactory(FaithfulMace.DUST_PILLAR, new DustPillarParticle.DustPillarFactory());
		this.registerFactory(FaithfulMace.GUST_EMITTER_SMALL, new GustEmitterParticle.Factory(1.0, 3, 2));
		this.registerFactory(FaithfulMace.GUST_EMITTER_LARGE, new GustEmitterParticle.Factory(3.0, 7, 0));
	}
}