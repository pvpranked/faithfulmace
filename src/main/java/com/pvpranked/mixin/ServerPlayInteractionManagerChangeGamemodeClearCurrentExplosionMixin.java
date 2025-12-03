package com.pvpranked.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayInteractionManagerChangeGamemodeClearCurrentExplosionMixin {

    @Shadow
    @Final
    protected ServerPlayerEntity player;

    @Inject(method = "changeGameMode", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;updateSleepingPlayers()V", shift = At.Shift.AFTER))
	private void clearCurrentExplosion(GameMode gameMode, CallbackInfoReturnable<Boolean> cir) {
        if(gameMode == GameMode.CREATIVE) {
            this.player.clearCurrentExplosion();
        }
	}
}