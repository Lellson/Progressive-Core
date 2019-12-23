package de.lellson.progressivecore.items.armor.handler;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import de.lellson.progressivecore.misc.Constants;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public abstract class ArmorHandlerFantasy extends ArmorHandler {

	
	@Override
	public void onTooltip(List<String> lines, World world, ItemStack stack, EntityPlayerSP player, boolean fullset) {
		
		if (fullset)
		{
			String[] full = getFullSetTooltip();
			for (int i = 0; i < full.length; i++)
				lines.add(TextFormatting.GOLD + (i == 0 ? "Set Bonus: " : "") + full[i]);
		}
	}
	
	@Override
	public void addAttributes(Multimap<String, AttributeModifier> multimap, UUID uuid, EntityEquipmentSlot slot, ItemStack stack) {
		if (getExtraKnockback() > 0) 
			multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(uuid, Constants.prefix("fantasy_knockback"), getExtraKnockback(), 0));
		if (getExtraMovementSpeed() > 0) 
			multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuid, Constants.prefix("fantasy_movespeed"), getExtraMovementSpeed(), 1));
		if (getExtraDamage() > 0) 
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(uuid, Constants.prefix("fantasy_damage"), getExtraDamage(), 1));
		if (getExtraAttackSpeed() > 0) 
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(uuid, Constants.prefix("fantasy_attackspeed"), getExtraAttackSpeed(), 1));
		if (getExtraHealth() > 0) 
			multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuid, Constants.prefix("fantasy_health"), getExtraHealth(), 0));
	}
	
	public double getExtraKnockback() {
		return 0.1;
	}
	
	public double getExtraMovementSpeed() {
		return 0.05;
	}
	
	public double getExtraDamage() {
		return 0.05;
	}
	
	public double getExtraAttackSpeed() {
		return 0.05;
	}
	
	public double getExtraHealth() {
		return 2;
	}
	
	public abstract String[] getFullSetTooltip();
}
