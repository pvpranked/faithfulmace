package com.pvpranked.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;

public class DensityEnchantment extends MaceEnchantment {

    protected DensityEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public void modifySmashDamagePerFallenBlock(ServerWorld world, int level, ItemStack stack, Entity user, DamageSource damageSource, MutableFloat smashDamagePerFallenBlock) {
        smashDamagePerFallenBlock.add(0.5 * level);
    }

    @Override
    public int getMinPower(int level) {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return other instanceof BreachEnchantment ? false : super.canAccept(other);
    }
}
