package com.pvpranked;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class MaceClientUtil {

    public static void spawnSmashAttackParticles(ClientWorld world, BlockPos pos, int count) {
        Vec3d vec3d = pos.toCenterPos().add(0.0, 0.5, 0.0);

        BlockStateParticleEffect blockStateParticleEffect = new BlockStateParticleEffect(FaithfulMace.DUST_PILLAR, world.getBlockState(pos));

        for (int i = 0; (float)i < (float)count / 3.0F; i++) {
            double d = vec3d.x + world.getRandom().nextGaussian() / 2.0;
            double e = vec3d.y;
            double f = vec3d.z + world.getRandom().nextGaussian() / 2.0;
            double g = world.getRandom().nextGaussian() * 0.2F;
            double h = world.getRandom().nextGaussian() * 0.2F;
            double j = world.getRandom().nextGaussian() * 0.2F;
            world.addParticle(blockStateParticleEffect, d, e, f, g, h, j);
        }

        for (int i = 0; (float)i < (float)count / 1.5F; i++) {
            double d = vec3d.x + 3.5 * Math.cos((double)i) + world.getRandom().nextGaussian() / 2.0;
            double e = vec3d.y;
            double f = vec3d.z + 3.5 * Math.sin((double)i) + world.getRandom().nextGaussian() / 2.0;
            double g = world.getRandom().nextGaussian() * 0.05F;
            double h = world.getRandom().nextGaussian() * 0.05F;
            double j = world.getRandom().nextGaussian() * 0.05F;
            world.addParticle(blockStateParticleEffect, d, e, f, g, h, j);
        }
    }
}
