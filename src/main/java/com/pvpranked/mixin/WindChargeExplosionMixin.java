package com.pvpranked.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.pvpranked.PlayerEntityMaceInterface;
import com.pvpranked.entity.WindChargeNoDamageEntitiesExplosionBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Explosion.class)
public abstract class WindChargeExplosionMixin implements PlayerEntityMaceInterface {

	@Shadow @Final private ExplosionBehavior behavior;

	@Shadow @Final private @Nullable Entity entity;

	@WrapOperation(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
	private boolean useWindChargeExplosionDamageBehavior(Entity instance, DamageSource source, float amount, Operation<Boolean> original) {

		if(this.behavior instanceof WindChargeNoDamageEntitiesExplosionBehavior) {
			return false;
		}
		return original.call(instance, source, amount);
	}
}