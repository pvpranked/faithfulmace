package com.pvpranked.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.pvpranked.FaithfulMace.MOGGER;
import static com.pvpranked.FaithfulMace.superfluousLogging;

@Mixin(PlayerEntity.class)
public abstract class ExplosionLogging2Mixin {


	@Inject(method = "attack", at = @At(value = "HEAD"))
	private void logggyyyyyyyyy(Entity target, CallbackInfo ci) {
		PlayerEntity thiscast = ((PlayerEntity)(Object) this);
		if(superfluousLogging()) MOGGER.info("Starting player attack with vel {}", thiscast.getVelocity());
	}

	@Inject(method = "attack", at = @At(value = "TAIL"))
	private void logggyyyyyyyyy2(Entity target, CallbackInfo ci) {
		PlayerEntity thiscast = ((PlayerEntity)(Object) this);
		if(superfluousLogging()) MOGGER.info("Ending player attack with vel {}", thiscast.getVelocity());
	}
}