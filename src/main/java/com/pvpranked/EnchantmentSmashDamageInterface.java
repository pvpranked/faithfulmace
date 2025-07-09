package com.pvpranked;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;

public interface EnchantmentSmashDamageInterface {

    //DO NOT DELETE THIS IT IS USED IN fabric.mod.json IT IS VERY IMPORTANT

    default void modifySmashDamagePerFallenBlock(
            ServerWorld world, int level, ItemStack stack, Entity user, DamageSource damageSource, MutableFloat smashDamagePerFallenBlock) {

        //do nothing because no data driven anything yet:)
//        this.modifyValue(EnchantmentEffectComponentTypes.SMASH_DAMAGE_PER_FALLEN_BLOCK, world, level, stack, user, damageSource, smashDamagePerFallenBlock);
    }

    default void modifyArmorEffectiveness(ServerWorld world, int level, ItemStack stack, Entity user, DamageSource damageSource, MutableFloat mutableFloat) {
//        System.out.println("hi for: " + this.getClass().getName());
        //nothing for normal enchants
    }
}
