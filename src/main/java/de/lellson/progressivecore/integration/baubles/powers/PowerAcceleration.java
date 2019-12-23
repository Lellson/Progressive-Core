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

public class PowerAcceleration extends AbstractPower {

	private final int speedMultiplier;
	
	public PowerAcceleration() {
		super("acceleration", 3, TextFormatting.BLUE);
		speedMultiplier = ProConfig.cfg.getInt("accelerationMultiplier", CATEGORY, 5, 1, Short.MAX_VALUE, "Amount of extra speed in percent for one level of Acceleration");
	}
	
	@Override
	public void modifyAttributeModifiers(ItemStack stack, EntityLivingBase player, int level, UUID uuid, Multimap<String, AttributeModifier> map) {
		map.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuid, Constants.prefix("acceleration"), level*(speedMultiplier/100f), 1));
	}
	
	@Override
	public String getDescription(Power power) {
		return "+" + (power.getLevel()*speedMultiplier) + "% Movement Speed";
	}
}
