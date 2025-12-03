package com.pvpranked.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.pvpranked.PlayerEntityMaceInterface;
import net.minecraft.block.BlockState;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityClearCurrentExplosionMixin implements PlayerEntityMaceInterface {

	@WrapOperation(method = "handleFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z"))
	private boolean insertSpecialExplosionHandlingAddendum(PlayerEntity instance, float fallDistance, float damageMultiplier, DamageSource damageSource, Operation<Boolean> original) {
        PlayerEntity thiscast = (PlayerEntity)((Object) this);

        boolean bl = this.currentExplosionImpactPos() != null && this.ignoreFallDamageFromCurrentExplosion();
        float f;
        if (bl) {
            f = Math.min(fallDistance, (float)(this.currentExplosionImpactPos().y - thiscast.getY()));
            boolean bl2 = f <= 0.0F;
            if (bl2) {
                this.clearCurrentExplosion();
            } else {
                this.tryClearCurrentExplosion();
            }
        } else {
            f = fallDistance;
        }

        if (f > 0.0F && original.call(instance, f, damageMultiplier, damageSource)) {
            this.clearCurrentExplosion();
            return true;
        } else {
            return false;
        }
    }

	@Inject(method = "slowMovement", at = @At(value = "TAIL"))
	private void clearCurrentExplosion(BlockState state, Vec3d multiplier, CallbackInfo ci) {
        this.tryClearCurrentExplosion();
    }
}