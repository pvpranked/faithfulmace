package com.pvpranked.item;


import com.pvpranked.FaithfulMace;
import com.pvpranked.entity.WindChargeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WindChargeItem extends Item // implements ProjectileItem
{
    public static float POWER = 1.5F;

    public WindChargeItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(this, 10);
        if (!world.isClient) {
            WindChargeEntity windChargeEntity = WindChargeEntity.create(user, world, user.getPos().getX(), user.getEyePos().getY(), user.getPos().getZ());
            windChargeEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, POWER, 1.0F);
            world.spawnEntity(windChargeEntity);
        }

        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                (SoundEvent) FaithfulMace.ENTITY_WIND_CHARGE_THROW_SOUND_EVENT,
                SoundCategory.NEUTRAL,
                0.5F,
                0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
