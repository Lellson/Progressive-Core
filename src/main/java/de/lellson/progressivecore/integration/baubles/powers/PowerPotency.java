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

public class PowerPotency extends AbstractPower {

	private final int damageMultiplier;
	
	public PowerPotency() {
		super("potency", 3, TextFormatting.LIGHT_PURPLE);
		damageMultiplier = ProConfig.cfg.getInt("potencyMultiplier", CATEGORY, 5, 1, Short.MAX_VALUE, "Amount of extra attack damage in percent for one level of Potency");
	}
	
	@Override
	public void modifyAttributeModifiers(ItemStack stack, EntityLivingBase player, int level, UUID uuid, Multimap<String, AttributeModifier> map) {
		map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(uuid, Constants.prefix("potency"), level*(damageMultiplier/100f), 1));
	}
	
	@Override
	public String getDescription(Power power) {
		return "+" + (power.getLevel()*damageMultiplier) + "% Attack Damage";
	}
}
