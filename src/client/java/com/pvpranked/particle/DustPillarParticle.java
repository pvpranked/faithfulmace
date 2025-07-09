package com.pvpranked.particle;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.BlockStateParticleEffect;
import org.jetbrains.annotations.Nullable;

public class DustPillarParticle {
    public static class DustPillarFactory implements ParticleFactory<BlockStateParticleEffect> {
        public DustPillarFactory() {
        }

        @Nullable
        public Particle createParticle(BlockStateParticleEffect blockStateParticleEffect, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            Particle particle = create(blockStateParticleEffect, clientWorld, d, e, f, g, h, i);
            if (particle != null) {
                particle.setVelocity(clientWorld.random.nextGaussian() / 30.0, h + clientWorld.random.nextGaussian() / 2.0, clientWorld.random.nextGaussian() / 30.0);
                particle.setMaxAge(clientWorld.random.nextInt(20) + 20);
            }

            return particle;
        }
    }

    //imported from somewhere else
    @Nullable
    static BlockDustParticle create(
            BlockStateParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ
    ) {
        BlockState blockState = parameters.getBlockState();
        return !blockState.isAir() && !blockState.isOf(Blocks.MOVING_PISTON) && blockState.hasBlockBreakParticles()
                ? new BlockDustParticle(world, x, y, z, velocityX, velocityY, velocityZ, blockState)
                : null;
    }
}
