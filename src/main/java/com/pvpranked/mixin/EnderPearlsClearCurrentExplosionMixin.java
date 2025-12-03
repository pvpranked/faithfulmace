package com.pvpranked.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.pvpranked.MaceUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlsClearCurrentExplosionMixin {

    @Inject(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onLanding()V", ordinal = 0, shift = At.Shift.AFTER))
	private void clearCurrentExplosion(HitResult hitResult, CallbackInfo ci, @Local Entity entity) {
        ServerPlayerEntity serverPlayerEntity;
        try {
            serverPlayerEntity = ((ServerPlayerEntity) entity);
        } catch (ClassCastException e) {
            throw new RuntimeException(MaceUtil.format("Entity {} was not a ServerPlayerEntity in EnderPearlEntity.onCollision mixin, even though we were in an if block that guarantees that it is?", entity));
        }
        serverPlayerEntity.clearCurrentExplosion();
	}
}