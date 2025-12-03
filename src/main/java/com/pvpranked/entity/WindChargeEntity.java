package com.pvpranked.entity;


import com.pvpranked.ExplosionUtil;
import com.pvpranked.FaithfulMace;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;

public class WindChargeEntity extends AbstractWindChargeEntity {

    private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new WindChargeNoDamageEntitiesCancelsFallDamageExplosionBehavior(1.22F);
    private static final float EXPLOSION_POWER = 1.2F;
    private static final float MAX_RENDER_DISTANCE_WHEN_NEWLY_SPAWNED = MathHelper.square(3.5F);
    private int deflectCooldown = 5;

    public WindChargeEntity(EntityType<? extends AbstractWindChargeEntity> entityType, World world) {
        super(entityType, world);
    }

    public static WindChargeEntity create(PlayerEntity player, World world, double x, double y, double z) {
        return new WindChargeEntity(player, world, x, y, z);
    }

    private WindChargeEntity(PlayerEntity player, World world, double x, double y, double z) {
        super(ModEntities.WIND_CHARGE, world, player, x, y, z);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.deflectCooldown > 0) {
            this.deflectCooldown--;
        }
    }

    //two functions from 1.21.4 that had their functionality implemented elsewhere, each of the two below states where with a comment in the method body
//    @Override
//    public boolean deflect(ProjectileDeflection deflection, @Nullable Entity deflector, @Nullable Entity owner, boolean fromAttack) {
//        this behavior was moved to:
//        ExplosiveProjectileEntity.damage;
//
//        return this.deflectCooldown > 0 ? false : super.deflect(deflection, deflector, owner, fromAttack);
//    }
//
//    implementing that below with damage()
//    public boolean deflect(ProjectileDeflection deflection, @Nullable Entity deflector, @Nullable Entity owner, boolean fromAttack) {
//        this behavior was moved to:
//        ExplosiveProjectileEntity.damage;
//
//        return this.deflectCooldown > 0 ? false : super.deflect(deflection, deflector, owner, fromAttack);
//    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.scheduleVelocityUpdate();
            Entity entity = source.getAttacker();
            if (entity != null) {
                if (!this.getWorld().isClient) {
                    if(this.deflectCooldown > 0) {
                        //nothing
                    } else {
                        Vec3d vec3d = entity.getRotationVector();
                        this.setVelocity(vec3d);
                        this.powerX = vec3d.x * 0.1;
                        this.powerY = vec3d.y * 0.1;
                        this.powerZ = vec3d.z * 0.1;
                        this.setOwner(entity);
                    }
                }

                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    protected void createExplosion(Vec3d pos) {
        if(this.getWorld() instanceof ServerWorld) {
            ExplosionUtil.createExplosion(
                    ((ServerWorld) this.getWorld()),

                    this,
                    null,
                    EXPLOSION_BEHAVIOR,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    1.2F,
                    false,
                    World.ExplosionSourceType.NONE,
                    FaithfulMace.GUST_EMITTER_SMALL,
                    FaithfulMace.GUST_EMITTER_LARGE,
                    Registries.SOUND_EVENT.getEntry(FaithfulMace.ENTITY_WIND_CHARGE_WIND_BURST_SOUND_EVENT)
            );
        }
    }

    @Override
    public boolean shouldRender(double distance) {
        return this.age < 2 && distance < (double)MAX_RENDER_DISTANCE_WHEN_NEWLY_SPAWNED ? false : super.shouldRender(distance);
    }

    @Override
    protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.0F;
    }
}
