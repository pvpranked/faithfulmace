package com.pvpranked.enchantments;

import com.pvpranked.ExplosionUtil;
import com.pvpranked.FaithfulMace;
import com.pvpranked.entity.WindChargeNoDamageEntitiesCancelsFallDamageExplosionBehavior;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WindBurstEnchantment extends MaceEnchantment {

    protected WindBurstEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    public void onTargetDamagedCustomLocation(LivingEntity user, int level, float fallDist) {
        if(!(user.getWorld() instanceof ServerWorld)) return;
        if(fallDist < 1.5d) return;

        if(user instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) user;
            if((livingEntity.isFallFlying() || livingEntity instanceof PlayerEntity playerEntity && playerEntity.getAbilities().flying)) {
                return;
            }
        }

//        if (user instanceof LivingEntity livingEntity
//                && (livingEntity.isFallFlying() || livingEntity instanceof PlayerEntity playerEntity && playerEntity.getAbilities().flying)) {
////            flying = true; commented out: no flying allowed
//            return;
//        }

        boolean attributeToUser = false;
        DamageSource damageSource = null;


        float knockbackMultiplier = switch (level) {
            case 1: yield 1.2F;
            case 2: yield 1.75F;
            case 3: yield 2.2F;
            default: yield (1.5F) + ((level - 1) * 0.35F);
        };

        Vec3d pos = user.getPos();
        Vec3d offset = Vec3d.ZERO;
        float radius = 3.5F;
        boolean createFire = false;
        World.ExplosionSourceType blockInteraction = World.ExplosionSourceType.NONE;
        ParticleEffect smallParticle = FaithfulMace.GUST_EMITTER_SMALL;
        ParticleEffect largeParticle = FaithfulMace.GUST_EMITTER_LARGE;
        RegistryEntry<SoundEvent> sound = Registries.SOUND_EVENT.getEntry(FaithfulMace.ENTITY_WIND_CHARGE_WIND_BURST_SOUND_EVENT);


        Vec3d vec3d = pos.add(offset);
        ExplosionUtil.createExplosion(
                ((ServerWorld) user.getWorld()),

                attributeToUser ? user : null,
                damageSource,
                new WindChargeNoDamageEntitiesCancelsFallDamageExplosionBehavior(knockbackMultiplier),
                vec3d.getX(),
                vec3d.getY(),
                vec3d.getZ(),
                radius,
                createFire,
                blockInteraction,
                smallParticle,
                largeParticle,
                sound
        );
    }

    @Override
    public int getMinPower(int level) {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
