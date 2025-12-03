package com.pvpranked.mixin.client;

import com.pvpranked.FaithfulMace;
import com.pvpranked.item.ModItems;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelGenerator.class)
public abstract class MaceModelRegisterMixin {

	@Shadow public abstract void register(Item item, Model model);

	@Inject(method = "register()V", at = @At(value = "HEAD"))
	private void registerMaceModel(CallbackInfo ci) {
		this.register(ModItems.MACE, FaithfulMace.HANDHELD_MACE);
	}
}