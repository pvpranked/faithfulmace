package com.pvpranked.mixin;

import com.pvpranked.PlayerEntityMaceInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityInterfaceMixin implements PlayerEntityMaceInterface {

	@Unique
	@Nullable
	public Vec3d currentExplosionImpactPos;
    /** set but never read as this is only for achievements */
	@Unique
	@Nullable
	public Entity explodedBy;
	@Unique
	private boolean ignoreFallDamageFromCurrentExplosion;
	@Unique
	private int currentExplosionResetGraceTime;
	@Unique
	private boolean spawnExtraParticlesOnFall;

    @Override
    public boolean hasLandedInFluid() {
        PlayerEntity thiscast = ((PlayerEntity)(Object) this);
        boolean isInFluid = thiscast.isInsideWaterOrBubbleColumn() || thiscast.isInLava();

        return thiscast.getVelocity().getY() < 1.0E-5F && isInFluid;
    }

    @Override
    public void clearCurrentExplosion() {
        this.currentExplosionResetGraceTime = 0;
        this.explodedBy = null;
        this.currentExplosionImpactPos = null;
        this.ignoreFallDamageFromCurrentExplosion = false;
    }

    @Override
    public void tryClearCurrentExplosion() {
        if (this.currentExplosionResetGraceTime == 0) {
            this.clearCurrentExplosion();
        }
    }

	@Override
	public void setCurrentExplosionImpactPos(Vec3d newVal) {
		currentExplosionImpactPos = newVal;
	}

	@Override
	public Vec3d currentExplosionImpactPos() {
		return currentExplosionImpactPos;
	}

	@Override
	public void setExplodedBy(Entity newVal) {
		explodedBy = newVal;
	}

	@Override
	public Entity explodedBy() {
		return explodedBy;
	}

	@Override
	public void setIgnoreFallDamageFromCurrentExplosion(boolean newVal) {
		ignoreFallDamageFromCurrentExplosion = newVal;
	}

	@Override
	public boolean ignoreFallDamageFromCurrentExplosion() {
		return ignoreFallDamageFromCurrentExplosion;
	}

	@Override
	public void setCurrentExplosionResetGraceTime(int newVal) {
		currentExplosionResetGraceTime = newVal;
	}

	@Override
	public int currentExplosionResetGraceTime() {
		return currentExplosionResetGraceTime;
	}

	@Override
	public void setSpawnExtraParticlesOnFall(boolean newVal) {
		spawnExtraParticlesOnFall = newVal;
	}

	@Override
	public boolean spawnExtraParticlesOnFall() {
		return spawnExtraParticlesOnFall;
	}
}