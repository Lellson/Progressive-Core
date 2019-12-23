package de.lellson.progressivecore.integration.baubles.powers;

import java.util.UUID;

import com.google.common.collect.Multimap;

import baubles.api.BaublesApi;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class PowerVitality extends AbstractPower {

	private final int lifeMultiplier;
	
	public PowerVitality() {
		super("vitality", 3, TextFormatting.RED);
		lifeMultiplier = ProConfig.cfg.getInt("vitalityMultiplier", CATEGORY, 2, 1, Short.MAX_VALUE, "Amount of half hearts for one level of Vitality");
	}
	
	@Override
	public void modifyAttributeModifiers(ItemStack stack, EntityLivingBase player, int level, UUID uuid, Multimap<String, AttributeModifier> map) {
		map.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuid, Constants.prefix("vitality"), level*lifeMultiplier, 0));
	}
	
	@Override
	public String getDescription(Power power) {
		return "+" + (power.getLevel()*lifeMultiplier) + " Max Health";
	}
}
