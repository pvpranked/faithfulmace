package com.pvpranked.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.pvpranked.PlayerEntityMaceInterface;
import com.pvpranked.enchantments.MaceEnchantmentHelperPort;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class DamageUtilBreachMixin implements PlayerEntityMaceInterface {

	@Shadow public abstract boolean damage(DamageSource source, float amount);

	@WrapOperation(method = "applyArmorToDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getDamageLeft(FFF)F"))
	private float useNewPropertyDamageBypassness(float damage, float armor, float armorToughness, Operation<Float> original, @Local(argsOnly = true) DamageSource damageSource) {
		return MaceEnchantmentHelperPort.damageUtilDotGetDamageLeft(((LivingEntity)(Object) this), damage, damageSource, armor, armorToughness);
	}
}