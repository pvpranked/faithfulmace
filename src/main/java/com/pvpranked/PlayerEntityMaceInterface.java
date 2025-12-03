package com.pvpranked;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public interface PlayerEntityMaceInterface {
    default boolean hasLandedInFluid() {
        throw getUnimplementedInterfaceError();
    }

    default void clearCurrentExplosion() {
        throw getUnimplementedInterfaceError();
    }

    default void tryClearCurrentExplosion() {
        throw getUnimplementedInterfaceError();
    }

    default Vec3d currentExplosionImpactPos() {
        throw getUnimplementedInterfaceError();
    }

    default void setCurrentExplosionImpactPos(Vec3d newVal) {
        throw getUnimplementedInterfaceError();
    }

    /** set but never read as this is only for achievements */
    default Entity explodedBy() {
        throw getUnimplementedInterfaceError();
    }
    /** set but never read as this is only for achievements */
    default void setExplodedBy(Entity newVal) {
        throw getUnimplementedInterfaceError();
    }

    default boolean ignoreFallDamageFromCurrentExplosion() {
        throw getUnimplementedInterfaceError();
    }
    default void setIgnoreFallDamageFromCurrentExplosion(boolean newVal) {
        throw getUnimplementedInterfaceError();
    }

    default int currentExplosionResetGraceTime() {
        throw getUnimplementedInterfaceError();
    }
    default void setCurrentExplosionResetGraceTime(int newVal) {
        throw getUnimplementedInterfaceError();
    }

    default void setSpawnExtraParticlesOnFall(boolean newVal) {
        throw getUnimplementedInterfaceError();
    }

    default boolean spawnExtraParticlesOnFall() {
        throw getUnimplementedInterfaceError();
    }

    static RuntimeException getUnimplementedInterfaceError() {
        return new UnsupportedOperationException("Default method for PlayerEntityMaceInterface from the mod FaithfulMace should never run");
    }
}
