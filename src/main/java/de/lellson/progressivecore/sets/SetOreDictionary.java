package de.lellson.progressivecore.sets;

import de.lellson.progressivecore.items.ItemPro;
import de.lellson.progressivecore.misc.helper.MiscHelper;

public class SetOreDictionary {
	
	private final String name;
	private final boolean material, ore;
	
	public SetOreDictionary(String name, boolean material, boolean ore) {
		this.name = MiscHelper.upperFirst(name);
		this.material = material;
		this.ore = ore;
	}
	
	public SetOreDictionary(String name) {
		this.name = MiscHelper.upperFirst(name + "Pro");
		this.material = this.ore = true;
	}
	
	public void applyOreDict(Set set) {
		
		if (material && set.getMaterial() instanceof ItemPro)
			((ItemPro)set.getMaterial()).oreDict((set.hasChunks() ? "chunk" : "ingot") + name);
		
		if (ore)
		{
			if (set.hasOre())
				set.getBlockOre().oreDict("ore" + name);
			
			if (set.hasNetherOre())
				set.getBlockOreNether().oreDict("ore" + name);
			
			if (!set.hasChunks())
				set.getBlockDeco().oreDict("block" + name);
		}
	}
	
	public String getName() {
		return name;
	}
}
