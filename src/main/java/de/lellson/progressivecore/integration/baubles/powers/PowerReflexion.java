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

public class PowerReflexion extends AbstractPower {

	private final int speedMultiplier;
	
	public PowerReflexion() {
		super("reflexion", 3, TextFormatting.YELLOW);
		speedMultiplier = ProConfig.cfg.getInt("reflexionMultiplier", CATEGORY, 5, 1, Short.MAX_VALUE, "Amount of extra attack speed in percent for one level of Reflexion");
	}
	
	@Override
	public void modifyAttributeModifiers(ItemStack stack, EntityLivingBase player, int level, UUID uuid, Multimap<String, AttributeModifier> map) {
		map.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(uuid, Constants.prefix("reflexion"), level*(speedMultiplier/100f), 1));
	}
	
	@Override
	public String getDescription(Power power) {
		return "+" + (power.getLevel()*speedMultiplier) + "% Attack Speed";
	}
}
