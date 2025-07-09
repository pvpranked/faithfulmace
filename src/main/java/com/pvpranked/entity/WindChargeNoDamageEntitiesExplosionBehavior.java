package com.pvpranked.entity;

import net.minecraft.world.explosion.ExplosionBehavior;

public class WindChargeNoDamageEntitiesExplosionBehavior extends ExplosionBehavior {

    public final float knockbackMultiplier;

    public WindChargeNoDamageEntitiesExplosionBehavior() {
        this(1F);
    }
    public WindChargeNoDamageEntitiesExplosionBehavior(float knockbackMultiplier) {
        this.knockbackMultiplier = knockbackMultiplier;
    }
}
