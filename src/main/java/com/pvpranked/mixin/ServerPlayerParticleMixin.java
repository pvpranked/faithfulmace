package com.pvpranked.mixin;

import com.pvpranked.PlayerEntityMaceInterface;
import net.minecraft.block.BlockState;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerParticleMixin implements PlayerEntityMaceInterface {

	@Inject(method = "fall", at = @At(value = "HEAD"))
	private void addParticleFromMace(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition, CallbackInfo ci) {
		ServerPlayerEntity thiscast = (ServerPlayerEntity) (Object) this;

		if (this.spawnExtraParticlesOnFall() && onGround && thiscast.fallDistance > 0.0F) {
			Vec3d vec3d = landedPosition.toCenterPos().add(0.0, 0.5, 0.0);
			int i = (int) MathHelper.clamp(50.0F * thiscast.fallDistance, 0.0F, 200.0F);
			thiscast.getServerWorld().spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), vec3d.x, vec3d.y, vec3d.z, i, 0.3F, 0.3F, 0.3F, 0.15F);
			this.setSpawnExtraParticlesOnFall(false);
		}
	}
}