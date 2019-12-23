package de.lellson.progressivecore.integration.baubles.powers;

import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class PowerSmoothness extends AbstractPower {

	private int smoothnessMultiplier;
	
	public PowerSmoothness() {
		super("smoothness", 3, TextFormatting.GRAY);
		smoothnessMultiplier = ProConfig.cfg.getInt("smoothnessMultiplier", CATEGORY, 15, 1, Short.MAX_VALUE, "Amount of extra fall damage resistance in percent for one level of Smoothness");
	}
	
	@Override
	public void onTaken(ItemStack stack, int level, EntityPlayer target, Entity attacker, LivingDamageEvent event) {
		
		if (event.getSource() != null && event.getSource() == DamageSource.FALL)
			event.setAmount(event.getAmount() * ((100 - smoothnessMultiplier*level)/100.0f));
	}
	
	@Override
	public String getDescription(Power power) {
		return "+" + (power.getLevel()*smoothnessMultiplier) + "% Fall Damage resistance";
	}
}
