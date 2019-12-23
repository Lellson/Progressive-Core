package de.lellson.progressivecore.misc.helper;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;

public class AttributeHelper {
	
	public static double getValues(Multimap<String, AttributeModifier> attributeMap, String key) {
		
		double value = 0;
		for (AttributeModifier mod : attributeMap.get(key)) 
			value += mod.getAmount();
		
		return value;
	}
	
	public static void replaceModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double multiplier) {
		
		Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());
		Optional<AttributeModifier> op = modifiers.stream().filter(attr -> attr.getID().equals(id)).findFirst();

		if (op.isPresent()) 
		{
			AttributeModifier modifier = op.get();
			modifiers.remove(modifier);
			modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), modifier.getAmount() * multiplier, modifier.getOperation()));
		}
	}
}
