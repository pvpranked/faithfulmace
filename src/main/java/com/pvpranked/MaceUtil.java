package com.pvpranked;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.apache.logging.log4j.message.ParameterizedMessage;

public class MaceUtil {
    public static void itemStackDamage(ItemStack itemStack, int amount, LivingEntity entity, EquipmentSlot slot) {
        World var5 = entity.getWorld();
        if (var5 instanceof ServerWorld serverWorld) {
            ServerPlayerEntity var10003;
            if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
                var10003 = serverPlayerEntity;
            } else {
                var10003 = null;
                return;
            }

            itemStack.damage(amount, var10003, (item) -> {
                entity.sendEquipmentBreakStatus(slot);
            });
        }

    }

    public static String format(String format, Object... args) {
        return new ParameterizedMessage(format, args).getFormattedMessage();
    }
}
