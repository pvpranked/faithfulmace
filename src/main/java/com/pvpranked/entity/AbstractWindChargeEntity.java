package com.pvpranked.entity;


import com.pvpranked.FaithfulMace;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;

import static com.pvpranked.FaithfulMace.ALL_CONDITIONS_THAT_CAN_MAKE_WIND_CHARGE_DESPAWN_ON_BLOCK_IMPACT;

public abstract class AbstractWindChargeEntity extends ExplosiveProjectileEntity implements FlyingItemEntity {

    public AbstractWindChargeEntity(EntityType<? extends AbstractWindChargeEntity> entityType, World world) {
        super(entityType, world);
        this.powerX = 0.0;
        this.powerY = 0.0;
        this.powerZ = 0.0;
    }

    public AbstractWindChargeEntity(EntityType<? extends AbstractWindChargeEntity> type, World world, Entity owner, double x, double y, double z) {
        super(type, x, y, z, 0, 0, 0, world);
        this.setOwner(owner);
        this.powerX = 0.0;
        this.powerY = 0.0;
        this.powerZ = 0.0;
    }

    AbstractWindChargeEntity(EntityType<? extends AbstractWindChargeEntity> entityType, double d, double e, double f, Vec3d vec3d, World world) {
        super(entityType, d, e, f, vec3d.x, vec3d.y, vec3d.z, world);
        this.powerX = 0.0;
        this.powerY = 0.0;
        this.powerZ = 0.0;
    }

    @Override
    public Box calculateBoundingBox() {
        Vec3d pos = this.getPos();
        float f = this.getType().getDimensions().width / 2.0F;
        float g = this.getType().getDimensions().height;
        float h = 0.15F;
        return new Box(pos.x - (double)f, pos.y - 0.15F, pos.z - (double)f, pos.x + (double)f, pos.y - 0.15F + (double)g, pos.z + (double)f);
    }

    @Override
    public boolean collidesWith(Entity other) {
        return other instanceof AbstractWindChargeEntity ? false : super.collidesWith(other);
    }

    @Override
    protected boolean canHit(Entity entity) {
        if (entity instanceof AbstractWindChargeEntity) {
            return false;
        } else {
            return entity.getType() == EntityType.END_CRYSTAL ? false : super.canHit(entity);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            LivingEntity livingEntity2 = this.getOwner() instanceof LivingEntity livingEntity ? livingEntity : null;
            Entity entity = entityHitResult.getEntity();
            if (livingEntity2 != null) {
                livingEntity2.onAttacking(entity);
            }

            DamageSource damageSource = this.getDamageSources().create(FaithfulMace.MACE_SMASH, this, livingEntity2);

            if (entity.damage(damageSource, 1.0F) && entity instanceof LivingEntity livingEntity3) {
                EnchantmentHelper.onTargetDamaged(livingEntity2, livingEntity3);
            }

            this.createExplosion(this.getPos());
        }
    }

    @Override
    public void addVelocity(double deltaX, double deltaY, double deltaZ) {
    }

    protected abstract void createExplosion(Vec3d pos);

    /** details on this in the javadoc for WIND_CHARGE_DESPAWNS_ON_CONTACT_WITH */
    protected boolean despawnOnContactWith(AbstractWindChargeEntity windChargeEntity, BlockState blockState) {
        for (BiPredicate<AbstractWindChargeEntity, BlockState> shouldDespawnChecker : ALL_CONDITIONS_THAT_CAN_MAKE_WIND_CHARGE_DESPAWN_ON_BLOCK_IMPACT) {
            if (shouldDespawnChecker.test(windChargeEntity, blockState)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            BlockState blockState = this.getWorld().getBlockState(blockHitResult.getBlockPos());
            if(!despawnOnContactWith(this, blockState)) {
                Vec3i vec3i = blockHitResult.getSide().getVector();
                Vec3d vec3d = Vec3d.of(vec3i).multiply(0.25, 0.25, 0.25);
                Vec3d vec3d2 = blockHitResult.getPos().add(vec3d);
                this.createExplosion(vec3d2);
                this.discard();
            } //else .discard() is handled by AbstractWindChargeEntity#onCollision, so it will just disappear
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.discard();
        }
    }

    @Override
    protected boolean isBurning() {
        return false;
    }

    @Override
    public ItemStack getStack() {
        return ItemStack.EMPTY;
    }

    @Override
    protected float getDrag() {
        return 1.0F;
    }

    @Override
    public boolean isTouchingWater() {
        return false;
    }

    @Nullable
    @Override
    protected ParticleEffect getParticleType() {
        return null;
    }

    @Override
    public void tick() {
        int topYInclusive = this.getWorld().getBottomY() + this.getWorld().getHeight() - 1;

        if (!this.getWorld().isClient && this.getBlockY() > topYInclusive + 30) {
            this.createExplosion(this.getPos());
            this.discard();
        } else {
            super.tick();
        }
    }
}