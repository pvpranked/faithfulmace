package com.pvpranked;

import com.pvpranked.packet.WindChargeExplosionReader;
import com.pvpranked.packet.WindChargeExplosionS2CPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;

public class ClientWindChargeExplosionReader extends WindChargeExplosionReader {

    @Override
    public void onExplosion(WindChargeExplosionS2CPacket packet, ClientPlayPacketListener listener) {
        MinecraftClient client = MinecraftClient.getInstance();
        NetworkThreadUtils.forceMainThread(packet, listener, client);

        Vec3d vec3d = packet.center();
        client
                .world
                .playSound(
                        vec3d.getX(),
                        vec3d.getY(),
                        vec3d.getZ(),
                        (SoundEvent)packet.explosionSound().value(),
                        SoundCategory.BLOCKS,
                        4.0F,
                        (1.0F + (client.world.random.nextFloat() - client.world.random.nextFloat()) * 0.2F) * 0.7F,
                        false
                );
        client.world.addParticle(packet.explosionParticle(), vec3d.getX(), vec3d.getY(), vec3d.getZ(), 1.0, 0.0, 0.0);
        packet.playerKnockback().ifPresent(client.player::addVelocity);
    }
}
