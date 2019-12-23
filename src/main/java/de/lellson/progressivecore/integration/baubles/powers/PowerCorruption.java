package de.lellson.progressivecore.integration.baubles.powers;

import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class PowerCorruption extends AbstractPower {

	private int corruptionChance;
	
	public PowerCorruption() {
		super("corruption", 2, TextFormatting.DARK_GRAY);
		corruptionChance = ProConfig.cfg.getInt("corruptionChance", CATEGORY, 3, 1, Short.MAX_VALUE, "Chance (1 in X-level+1) to wither attackers per Corruption power");
	}
	
	@Override
	public void onTaken(ItemStack stack, int level, EntityPlayer target, Entity attacker, LivingDamageEvent event) {
		
		if (attacker instanceof EntityLivingBase && target != null && target.world.rand.nextInt(corruptionChance-level+1) == 0)
			((EntityLivingBase)attacker).addPotionEffect(new PotionEffect(MobEffects.WITHER, 80, level));
	}
	
	@Override
	public String getDescription(Power power) {
		return Math.round(100.0/(corruptionChance-power.getLevel()+1)) + "% Chance to wither attackers";
	}
}
