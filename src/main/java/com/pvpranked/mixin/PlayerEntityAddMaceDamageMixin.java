package com.pvpranked.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.pvpranked.item.MaceItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityAddMaceDamageMixin {

	@Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getKnockback(Lnet/minecraft/entity/LivingEntity;)I"))
	private void addMaceDamage(Entity target, CallbackInfo ci, @Local(ordinal = 0) LocalFloatRef f) {
		ItemStack itemStack = ((PlayerEntity) (Object) this).getMainHandStack();
		PlayerEntity thiscast = (PlayerEntity)((Object) this);
		if(target instanceof LivingEntity && itemStack.getItem() instanceof MaceItem) {
			MaceItem item = (MaceItem) itemStack.getItem();

			DamageSource damageSource = (DamageSource) Optional.ofNullable(item.getDamageSource(thiscast)).orElse(thiscast.getDamageSources().playerAttack(thiscast));

			float extra = ((MaceItem) itemStack.getItem()).getBonusAttackDamage(target, f.get(), damageSource);

			f.set(f.get() + extra);
		}
	}
}