package com.pvpranked.packet;

import com.pvpranked.ExplosionUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

import static com.pvpranked.ExplosionUtil.*;

public record WindChargeExplosionS2CPacket(Vec3d center, Optional<Vec3d> playerKnockback, ParticleEffect explosionParticle, RegistryEntry<SoundEvent> explosionSound) implements Packet<ClientPlayPacketListener> {
    public static WindChargeExplosionS2CPacket read(PacketByteBuf buf) {
        Vec3d vec3d = readVec3d(buf);

        Optional<Vec3d> optional = buf.readOptional(ExplosionUtil::readVec3d);

        ParticleType<?> particleType = buf.readRegistryValue(Registries.PARTICLE_TYPE);
        ParticleEffect particleEffect = readParticleParameters(buf, particleType);

        RegistryEntry<SoundEvent> registryEntry = buf.readRegistryEntry(Registries.SOUND_EVENT.getIndexedEntries(), SoundEvent::fromBuf);

        return new WindChargeExplosionS2CPacket(vec3d, optional, particleEffect, registryEntry);
    }

    @Override
    public void write(PacketByteBuf buf) {
        writeVec3d(buf, center);

//        MOGGER.info("Writing knockback to packet of {}", playerKnockback);
        buf.writeOptional(playerKnockback, ExplosionUtil::writeVec3d);

        explosionParticle.write(buf);

        buf.writeRegistryValue(Registries.PARTICLE_TYPE, explosionParticle.getType());
        buf.writeRegistryEntry(Registries.SOUND_EVENT.getIndexedEntries(), explosionSound, (packetByteBuf, soundEvent) -> soundEvent.writeBuf(packetByteBuf));
    }

    @Override
    public void apply(ClientPlayPacketListener listener) {
        WindChargeExplosionReader.reader.onExplosion(this, listener);
    }
}
