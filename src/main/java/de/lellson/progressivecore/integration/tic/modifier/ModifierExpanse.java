package de.lellson.progressivecore.integration.tic.modifier;

import java.util.UUID;

import com.google.common.collect.Multimap;

import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.helper.AttributeHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.ModLuck.LuckAspect;

public class ModifierExpanse extends ModifierTrait  {

	public ModifierExpanse() {
		super("prog.expanse", 0xDDDDBB, 2, 1);
		addItem(ProItems.REALITY_CRYSTAL);
	}
	
	@Override
	public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
		
		if (slot != EntityEquipmentSlot.MAINHAND)
			return;
		
		UUID uuid = new UUID((stack.getItem().getUnlocalizedName() + slot.toString()).hashCode(), 0);
		attributeMap.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(uuid, Constants.prefix("ticpro_expanse"), getData(stack).level, 0));
	}

	public static double getLevel(ItemStack stack) {
		
		double extra = 0;
		for (IModifier mod : TinkerUtil.getModifiers(stack)) 
		{
			if (mod instanceof ModifierExpanse)
			{
				extra += ((ModifierExpanse)mod).getData(stack).level;
			}
		}
		return extra;
	}
}
