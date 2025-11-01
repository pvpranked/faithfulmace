package com.pvpranked.mixin;

import com.pvpranked.EnchantmentSmashDamageInterface;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Enchantment.class)
public abstract class EnchantmentSmashDamageInterfaceMixin implements EnchantmentSmashDamageInterface {
    //this mixin is required to actually make the interface go into the class. The fabric.mod.json thing is only so your compiler doesn't yell at you, and apparently does some extra stuff specifically in the dev environment
}