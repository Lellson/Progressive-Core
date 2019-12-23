package de.lellson.progressivecore.items.tools;

import com.google.common.collect.Multimap;

import de.lellson.progressivecore.misc.helper.AttributeHelper;
import de.lellson.progressivecore.sets.Sets;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemTitanCrusher extends ItemSwordPro {

	public ItemTitanCrusher() {
		super(Sets.TITANIUM_TOOL, null, "titan_crusher");
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
		
		if (slot == EntityEquipmentSlot.MAINHAND)
		{
			AttributeHelper.replaceModifier(map, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, 1.2);
		}
		
		return map;
	}

	@Override
	public Tab getTab() {
		return Tab.TOOLS;
	}
}
