package com.pvpranked.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.pvpranked.FaithfulMace.MODID;

public class MaceEnchants {
    public static Enchantment DENSITY = new DensityEnchantment();
    public static Enchantment BREACH = new BreachEnchantment();
    public static Enchantment WIND_BURST = new WindBurstEnchantment();

    public static void initialize() {
        Registry.register(Registries.ENCHANTMENT, new Identifier(MODID, "density"), DENSITY);
        Registry.register(Registries.ENCHANTMENT, new Identifier(MODID, "breach"), BREACH);
        Registry.register(Registries.ENCHANTMENT, new Identifier(MODID, "wind_burst"), WIND_BURST);
    }
}
