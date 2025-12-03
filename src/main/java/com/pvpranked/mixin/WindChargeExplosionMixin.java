package com.pvpranked.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.pvpranked.entity.ModEntities;
import com.pvpranked.entity.WindChargeNoDamageEntitiesCancelsFallDamageExplosionBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Explosion.class)
public abstract class WindChargeExplosionMixin {

	@Shadow @Final private ExplosionBehavior behavior;

	@Shadow @Final private @Nullable Entity entity;

	@WrapOperation(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
	private boolean useWindChargeExplosionDamageBehavior(Entity instance, DamageSource source, float amount, Operation<Boolean> original) {
        /*
        The below if statement mimics ExplosionImpl in 1.21.4 calling entity.onExplodedBy which is only extended by ServerPlayerEntity.
        The original code is below as a reference

        @Override
        public void onExplodedBy(@Nullable Entity entity) {
            super.onExplodedBy(entity);
            this.currentExplosionImpactPos = this.getPos();
            this.explodedBy = entity;
            this.setIgnoreFallDamageFromCurrentExplosion(entity != null && entity.getType() == EntityType.WIND_CHARGE);
        }
        in ServerPlayerEntity.java, from version 1.21.4.
         */
        if(instance instanceof ServerPlayerEntity player) {
            player.setCurrentExplosionImpactPos(player.getPos());
            player.setExplodedBy(entity);
            player.setIgnoreFallDamageFromCurrentExplosion(entity != null && entity.getType() == ModEntities.WIND_CHARGE);
        }

		if(this.behavior instanceof WindChargeNoDamageEntitiesCancelsFallDamageExplosionBehavior) {
            /*
            this part mimics the
            boolean bl = this.behavior.shouldDamage(this, entity);
            if(bl) {
                ...
            }
            wrapper around entity.damage() that doesn't damage entities if the explosion behavior is wind charge behavior
            that code was from fabric 1.21.4 minecraft sources
             */
			return false;
		}
		return original.call(instance, source, amount);
	}
}