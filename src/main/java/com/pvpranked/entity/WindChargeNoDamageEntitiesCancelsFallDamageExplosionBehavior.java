package com.pvpranked.entity;

import net.minecraft.world.explosion.ExplosionBehavior;

public class WindChargeNoDamageEntitiesCancelsFallDamageExplosionBehavior extends ExplosionBehavior {

    public final float knockbackMultiplier;

    public WindChargeNoDamageEntitiesCancelsFallDamageExplosionBehavior() {
        this(1F);
    }
    public WindChargeNoDamageEntitiesCancelsFallDamageExplosionBehavior(float knockbackMultiplier) {
        this.knockbackMultiplier = knockbackMultiplier;
    }
}
