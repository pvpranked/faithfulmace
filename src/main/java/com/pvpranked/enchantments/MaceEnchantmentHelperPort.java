package com.pvpranked.enchantments;

import com.pvpranked.EnchantmentSmashDamageInterface;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.mutable.MutableFloat;

import static com.pvpranked.FaithfulMace.MOGGER;
import static com.pvpranked.FaithfulMace.superfluousLogging;
import static net.minecraft.enchantment.EnchantmentHelper.getIdFromNbt;
import static net.minecraft.enchantment.EnchantmentHelper.getLevelFromNbt;

public class MaceEnchantmentHelperPort {

    public static void forEachEnchantAfterPostHitMaceForWindBurst(ItemStack stack, LivingEntity userAndAttacker, float fallDist) {

        if (userAndAttacker instanceof PlayerEntity) {
            if (!stack.isEmpty()) {
                NbtList nbtList = stack.getEnchantments();

                for(int i = 0; i < nbtList.size(); ++i) {
                    NbtCompound nbtCompound = nbtList.getCompound(i);
                    Registries.ENCHANTMENT.getOrEmpty(getIdFromNbt(nbtCompound)).ifPresent((enchantment) -> {

                        if(enchantment instanceof WindBurstEnchantment) {
                            ((WindBurstEnchantment) enchantment).onTargetDamagedCustomLocation(userAndAttacker, getLevelFromNbt(nbtCompound), fallDist);
                        }

                    });
                }

            }
        }
    }

    /** mimics 1.21.4's DamageUtil.getDamageLeft() */
    public static float damageUtilDotGetDamageLeft(LivingEntity armorWearer, float damageAmount, DamageSource damageSource, float armor, float armorToughness) {
        float f = 2.0F + armorToughness / 4.0F;
        float g = MathHelper.clamp(armor - damageAmount / f, armor * 0.2F, 20.0F);
        float h = g / 25.0F;
        Entity attacker = damageSource.getAttacker();
        ItemStack itemStack = attacker instanceof LivingEntity ? ((LivingEntity) attacker).getMainHandStack() : null;
        float i;
        if (itemStack != null && armorWearer.getWorld() instanceof ServerWorld serverWorld) {
            i = MathHelper.clamp(MaceEnchantmentHelperPort.getArmorEffectiveness(serverWorld, itemStack, armorWearer, damageSource, h), 0.0F, 1.0F);
        } else {
            i = h;
        }

        float j = 1.0F - i;
        return damageAmount * j;
    }

    public static float getArmorEffectiveness(ServerWorld world, ItemStack stack, Entity user, DamageSource damageSource, float baseArmorEffectiveness) {
        try {
            MutableFloat mutableFloat = new MutableFloat(baseArmorEffectiveness);
            forEachEnchantment(stack, (enchantment, level) -> {
                if (enchantment instanceof EnchantmentSmashDamageInterface)
                    enchantment.modifyArmorEffectiveness(world, level, stack, user, damageSource, mutableFloat);
            });
            if(superfluousLogging()) MOGGER.info(mutableFloat.floatValue() + " effectiveness diff");
            return mutableFloat.floatValue();
        } catch (Exception e) {
            MOGGER.error("Exception caught while calculating armor effectiveness with stack {} user {} baseValue {} enchantments {}: ", stack, user, baseArmorEffectiveness, stack.getEnchantments(), e);
            return baseArmorEffectiveness;
        }
    }

    public static float getSmashDamagePerFallenBlock(ServerWorld world, ItemStack stack, Entity target, DamageSource damageSource, float baseSmashDamagePerFallenBlock) {
        try {
            MutableFloat mutableFloat = new MutableFloat(baseSmashDamagePerFallenBlock);
            forEachEnchantment((enchantment, level) -> {
                if (enchantment instanceof EnchantmentSmashDamageInterface)
                    ((Enchantment) enchantment).modifySmashDamagePerFallenBlock(world, level, stack, target, damageSource, mutableFloat);
            }, stack);
            return mutableFloat.floatValue();
        } catch (Exception e) {
            MOGGER.error("Exception caught while calculating extra base damage with stack {} target {} baseValue {} enchantments {}: ", stack, target, baseSmashDamagePerFallenBlock, stack.getEnchantments(), e);
            return baseSmashDamagePerFallenBlock;
        }
    }

    private static void forEachEnchantment(ItemStack stack, MaceEnchantmentHelperPort.Consumer consumer) {
        forEachEnchantment(consumer, stack);
    }
    private static void forEachEnchantment(MaceEnchantmentHelperPort.Consumer consumer, ItemStack stack) {
        if (!stack.isEmpty()) {
            NbtList nbtList = stack.getEnchantments();

            for(int i = 0; i < nbtList.size(); ++i) {
                NbtCompound nbtCompound = nbtList.getCompound(i);
                Registries.ENCHANTMENT.getOrEmpty(getIdFromNbt(nbtCompound)).ifPresent((enchantment) -> {
                    consumer.accept(enchantment, getLevelFromNbt(nbtCompound));
                });
            }

        }
    }

    @FunctionalInterface
    private interface Consumer {
        void accept(Enchantment enchantment, int level);
    }
}
