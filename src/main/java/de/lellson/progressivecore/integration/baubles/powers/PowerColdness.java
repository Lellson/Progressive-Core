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

public class PowerColdness extends AbstractPower {

	private int coldnessChance;
	
	public PowerColdness() {
		super("coldness", 2, TextFormatting.AQUA);
		coldnessChance = ProConfig.cfg.getInt("coldnessChance", CATEGORY, 3, 1, Short.MAX_VALUE, "Chance (1 in X-level+1) to slowdown and weaken attackers per Coldness power");
	}
	
	@Override
	public void onTaken(ItemStack stack, int level, EntityPlayer target, Entity attacker, LivingDamageEvent event) {
		
		if (attacker instanceof EntityLivingBase && target != null && target.world.rand.nextInt(coldnessChance-level+1) == 0)
		{
			((EntityLivingBase)attacker).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80, level));
			((EntityLivingBase)attacker).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 80, 0));
		}
	}
	
	@Override
	public String getDescription(Power power) {
		return Math.round(100.0/(coldnessChance-power.getLevel()+1)) + "% Chance to slowdown and weaken attackers";
	}
}
