package de.lellson.progressivecore.integration.baubles.powers;

import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class PowerFireproofness extends AbstractPower {

	private int fireproofnessMultiplier;
	
	public PowerFireproofness() {
		super("fireproofness", 3, TextFormatting.GOLD);
		fireproofnessMultiplier = ProConfig.cfg.getInt("fireproofnessMultiplier", CATEGORY, 15, 1, Short.MAX_VALUE, "Amount of extra fire/lava resistance in percent for one level of Fireproofness");
	}
	
	@Override
	public void onTaken(ItemStack stack, int level, EntityPlayer target, Entity attacker, LivingDamageEvent event) {
		
		if (event.getSource() != null && event.getSource().isFireDamage())
			event.setAmount(event.getAmount() * ((100 - fireproofnessMultiplier*level)/100.0f));
	}
	
	@Override
	public String getDescription(Power power) {
		return "+" + (power.getLevel()*fireproofnessMultiplier) + "% Fire Damage resistance";
	}
}
