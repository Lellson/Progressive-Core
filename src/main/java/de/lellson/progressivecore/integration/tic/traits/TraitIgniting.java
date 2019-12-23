package de.lellson.progressivecore.integration.tic.traits;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitIgniting extends AbstractTrait {

	public TraitIgniting() {
		super("prog.igniting", TextFormatting.GOLD);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
		target.setFire(8 + EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, tool) * 4);
	}
}
