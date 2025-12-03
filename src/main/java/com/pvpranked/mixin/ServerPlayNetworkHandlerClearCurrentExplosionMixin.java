package com.pvpranked.mixin;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerClearCurrentExplosionMixin {

    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onPlayerMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;increaseTravelMotionStats(DDD)V"))
	private void clearCurrentExplosion(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        /*
        source code from fabric 1.21.4:
        if (
                packet.isOnGround() ||
                        this.player.hasLandedInFluid() ||
                        this.player.isClimbing() ||
                        this.player.isSpectator() ||
                        bl ||
                        bl5
        ) {
            this.player.tryClearCurrentExplosion();
        }
        in 1.21.4, bl is player.isGliding() (= .isFallFlying in this version) and bl5 is player.isUsingRiptide().
        in this version, there are no booleans created to cache the return value of .isGliding or .isUsingRiptide, so we just call the getter methods directly
         */

        if (
                packet.isOnGround() ||
                        this.player.hasLandedInFluid() ||
                        this.player.isClimbing() ||
                        this.player.isSpectator() ||
                        this.player.isFallFlying() ||
                        this.player.isUsingRiptide()
        ) {
            this.player.tryClearCurrentExplosion();
        }
	}
}