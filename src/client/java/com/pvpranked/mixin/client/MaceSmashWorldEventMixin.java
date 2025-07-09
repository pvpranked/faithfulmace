package com.pvpranked.mixin.client;

import com.pvpranked.MaceClientUtil;
import com.pvpranked.PlayerEntityMaceInterface;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.pvpranked.FaithfulMace.MACE_SMASH_WORLD_EVENT_ID;
import static com.pvpranked.FaithfulMace.MOGGER;

@Mixin(WorldRenderer.class)
public abstract class MaceSmashWorldEventMixin implements PlayerEntityMaceInterface {

	@Shadow private @Nullable ClientWorld world;

	@Inject(method = "processWorldEvent", at = @At(value = "HEAD"), cancellable = true)
	private void checkForMaceWorldEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {

		if(eventId == MACE_SMASH_WORLD_EVENT_ID) {
			if(this.world == null) {
				MOGGER.error("World was null when receiving mace smash world event? passing error...");
			}
			MaceClientUtil.spawnSmashAttackParticles(this.world, pos, data);

			ci.cancel();
		}
	}
}