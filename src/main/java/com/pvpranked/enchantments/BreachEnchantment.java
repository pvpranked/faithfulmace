package com.pvpranked.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;

import static com.pvpranked.FaithfulMace.MOGGER;
import static com.pvpranked.FaithfulMace.superfluousLogging;

public class BreachEnchantment extends MaceEnchantment {

    protected BreachEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public void modifyArmorEffectiveness(ServerWorld world, int level, ItemStack stack, Entity user, DamageSource damageSource, MutableFloat armorEffectiveness) {
//1.21.4 code was:        this.modifyValue(EnchantmentEffectComponentTypes.ARMOR_EFFECTIVENESS, world, level, stack, user, damageSource, armorEffectiveness);

        if(superfluousLogging()) MOGGER.info("modifying {}", -0.15 * level);
        armorEffectiveness.add(-0.15 * level);
    }

    @Override
    public int getMinPower(int level) {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return other instanceof DensityEnchantment ? false : super.canAccept(other);
    }
}
