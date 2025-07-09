package com.pvpranked;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public interface PlayerEntityMaceInterface {
    default Vec3d currentExplosionImpactPos() {
        new Exception("Default method for PlayerEntityMaceInterface should never run").printStackTrace();
        return null;
    }
    default void setCurrentExplosionImpactPos(Vec3d newVal) {
        new Exception("Default method for PlayerEntityMaceInterface should never run").printStackTrace();
    }

    default Entity explodedBy() {
        new Exception("Default method for PlayerEntityMaceInterface should never run").printStackTrace();
        return null;
    }
    default void setExplodedBy(Entity newVal) {
        new Exception("Default method for PlayerEntityMaceInterface should never run").printStackTrace();
    }

    default boolean ignoreFallDamageFromCurrentExplosion() {
        new Exception("Default method for PlayerEntityMaceInterface should never run").printStackTrace();
        return false;
    }
    default void setIgnoreFallDamageFromCurrentExplosion(boolean newVal) {
        new Exception("Default method for PlayerEntityMaceInterface should never run").printStackTrace();
    }

    default int currentExplosionResetGraceTime() {
        new Exception("Default method for PlayerEntityMaceInterface should never run").printStackTrace();
        return 0;
    }
    default void setCurrentExplosionResetGraceTime(int newVal) {
        new Exception("Default method for PlayerEntityMaceInterface should never run").printStackTrace();
    }

    default void setSpawnExtraParticlesOnFall(boolean newVal) {
        new Exception("Default method for PlayerEntityMaceInterface should never run").printStackTrace();
    }

    default boolean spawnExtraParticlesOnFall() {
        new Exception("Default method for PlayerEntityMaceInterface should never run").printStackTrace();
        return false;
    }
}
