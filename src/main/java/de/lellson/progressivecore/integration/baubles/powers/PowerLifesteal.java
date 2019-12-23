package de.lellson.progressivecore.integration.baubles.powers;

import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class PowerLifesteal extends AbstractPower {

	private final int lifestealChance;
	
	public PowerLifesteal() {
		super("lifesteal", 1, TextFormatting.DARK_RED);
		lifestealChance = ProConfig.cfg.getInt("lifestealChance", CATEGORY, 3, 1, Short.MAX_VALUE, "Chance (1 in X-level+1) to heal to heal half a heart per Lifesteal power");
	}
	
	@Override
	public void onAttack(ItemStack stack, int level, EntityPlayer attacker, EntityLivingBase target, LivingDamageEvent event) {
		if (event.getAmount() > 5 && attacker.world.rand.nextInt(lifestealChance-level+1) == 0 && attacker != null && target != null)
			attacker.heal(1);
	}
	
	@Override
	public String getDescription(Power power) {
		return Math.round(100.0/(lifestealChance-power.getLevel()+1)) + "% Chance to heal half a heart on attack";
	}
}
