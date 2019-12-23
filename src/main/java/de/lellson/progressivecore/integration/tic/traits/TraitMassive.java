package de.lellson.progressivecore.integration.tic.traits;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import de.lellson.progressivecore.integration.tic.ProTiC;
import de.lellson.progressivecore.integration.tic.modifier.ModifierExpanse;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.helper.AttributeHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitMassive extends AbstractTrait {

	public TraitMassive() {
		super("prog.massive", 0xDDDDBB);
	}
	
	@Override
	public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
		
		if (slot != EntityEquipmentSlot.MAINHAND)
			return;
		
		UUID uuid = new UUID((stack.getItem().getUnlocalizedName() + slot.toString()).hashCode(), 0);
		attributeMap.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(uuid, Constants.prefix("ticpro_massive"), 1 + ModifierExpanse.getLevel(stack), 0));
	}
}
