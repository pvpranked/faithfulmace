package com.pvpranked.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.pvpranked.FaithfulMace.MODID;

public class ModEntities {
    public static final EntityType<WindChargeEntity> WIND_CHARGE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MODID, "wind_charge"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, WindChargeEntity::new).dimensions(new EntityDimensions(.3125F, 0.3125F, true))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static void initialize() {

    }
}
