package com.pvpranked;

import com.pvpranked.enchantments.MaceEnchants;
import com.pvpranked.entity.AbstractWindChargeEntity;
import com.pvpranked.entity.ModEntities;
import com.pvpranked.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class FaithfulMace implements ModInitializer {

    /**
     * This utility list is here for use by the PVP Ranked mod, because in PVP Ranked matches wind charges disappear on contact with barriers.
     * <p>
     * if you wish to add/remove blocks to this list or add additional conditions, feel free to add stuff to this list in your mods .onInitialize() function. Example summarized from the PVP Ranked source code:
     *
     *
     *

     <pre>{@code
     * public class PVPRanked implements ModInitializer {
     *     @Override
     *     public void onInitialize() {
     *         AbstractWindChargeEntity.WIND_CHARGE_DESPAWNS_ON_CONTACT_WITH.add(Blocks.BARRIER);
     *     }
     * }
     * </pre>
     */

    public static final List<BiPredicate<AbstractWindChargeEntity, BlockState>> ALL_CONDITIONS_THAT_CAN_MAKE_WIND_CHARGE_DESPAWN_ON_BLOCK_IMPACT = new ArrayList<>();

	public static final String MODID = "faithfulmace";

	// This mogger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger MOGGER = LoggerFactory.getLogger(MODID);

	public static final RegistryKey<DamageType> MACE_SMASH = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(MODID, "mace_smash"));
    /** This was in the later version so I backported it. Wind charges don't do damage, though. So this is unused for now lol */
	public static final RegistryKey<DamageType> WIND_CHARGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(MODID, "wind_charge"));

	public static final Identifier ITEM_MACE_SMASH_AIR = Identifier.of(MODID, "item.mace.smash_air");
	public static final Identifier ITEM_MACE_SMASH_GROUND = Identifier.of(MODID, "item.mace.smash_ground");
	public static final Identifier ITEM_MACE_SMASH_GROUND_HEAVY = Identifier.of(MODID, "item.mace.smash_ground_heavy");

	public static final Identifier ENTITY_WIND_CHARGE_THROW = Identifier.of(MODID,"entity.wind_charge.throw");
	public static final Identifier ENTITY_WIND_CHARGE_WIND_BURST = Identifier.of(MODID,"entity.wind_charge.wind_burst");

	public static SoundEvent ITEM_MACE_SMASH_AIR_SOUND_EVENT = SoundEvent.of(ITEM_MACE_SMASH_AIR);
	public static SoundEvent ITEM_MACE_SMASH_GROUND_SOUND_EVENT = SoundEvent.of(ITEM_MACE_SMASH_GROUND);
	public static SoundEvent ITEM_MACE_SMASH_GROUND_HEAVY_SOUND_EVENT = SoundEvent.of(ITEM_MACE_SMASH_GROUND_HEAVY);

	public static final SoundEvent ENTITY_WIND_CHARGE_THROW_SOUND_EVENT = SoundEvent.of(ENTITY_WIND_CHARGE_THROW);
	public static final SoundEvent ENTITY_WIND_CHARGE_WIND_BURST_SOUND_EVENT = SoundEvent.of(ENTITY_WIND_CHARGE_WIND_BURST);

	public static final int MACE_SMASH_WORLD_EVENT_ID = 2013;

	public static final ParticleType<BlockStateParticleEffect> DUST_PILLAR = ParticleTypes.register(
			"dust_pillar", false, BlockStateParticleEffect.PARAMETERS_FACTORY, BlockStateParticleEffect::createCodec
	);

	public static final Model HANDHELD_MACE = Models.item("handheld_mace", TextureKey.LAYER0);

	// instance of our particle
	public static final DefaultParticleType GUST_EMITTER_SMALL = FabricParticleTypes.simple(true);
	public static final DefaultParticleType GUST_EMITTER_LARGE = FabricParticleTypes.simple(true);
	public static final DefaultParticleType GUST = FabricParticleTypes.simple(true);

	public static final Identifier EXPLOSION_WIND_CHARGE_S2C_PACKET_ID = new Identifier(MODID, "wind_charge_explosion_s2c_packet");

	public static boolean superfluousLogging() {
		return false;
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		MOGGER.info("Wassup Fabric world - Faithful mace crew");

		ModItems.initialize();
		ModEntities.initialize();

		Registry.register(Registries.SOUND_EVENT, FaithfulMace.ITEM_MACE_SMASH_AIR, ITEM_MACE_SMASH_AIR_SOUND_EVENT);
		Registry.register(Registries.SOUND_EVENT, FaithfulMace.ITEM_MACE_SMASH_GROUND, ITEM_MACE_SMASH_GROUND_SOUND_EVENT);
		Registry.register(Registries.SOUND_EVENT, FaithfulMace.ITEM_MACE_SMASH_GROUND_HEAVY, ITEM_MACE_SMASH_GROUND_HEAVY_SOUND_EVENT);

		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MODID, "gust_emitter_small"), GUST_EMITTER_SMALL);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MODID, "gust_emitter_large"), GUST_EMITTER_LARGE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MODID, "gust"), GUST);

		MaceEnchants.initialize();
	}

	public static DamageSource getMaceSmashDamageSource(World world) {
		return new DamageSource(
				world.getRegistryManager()
						.get(RegistryKeys.DAMAGE_TYPE)
						.entryOf(MACE_SMASH));
	}
}