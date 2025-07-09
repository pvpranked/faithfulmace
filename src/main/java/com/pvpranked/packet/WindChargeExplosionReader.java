package com.pvpranked.packet;

import net.minecraft.network.listener.ClientPlayPacketListener;

public abstract class WindChargeExplosionReader {
    public static WindChargeExplosionReader reader;

    public abstract void onExplosion(WindChargeExplosionS2CPacket packet, ClientPlayPacketListener listener);
}
