package de.lellson.progressivecore.sets;

import de.lellson.progressivecore.items.IItemPro;
import de.lellson.progressivecore.items.armor.handler.ArmorHandlerTitanium;
import de.lellson.progressivecore.items.tools.ItemAxePro;
import de.lellson.progressivecore.items.tools.ItemHoePro;
import de.lellson.progressivecore.items.tools.ItemPickaxePro;
import de.lellson.progressivecore.items.tools.ItemShovelPro;
import de.lellson.progressivecore.items.tools.ItemSwordPro;
import de.lellson.progressivecore.items.tools.handler.ToolHandler;
import de.lellson.progressivecore.items.tools.handler.ToolHandlerTitanium;
import de.lellson.progressivecore.sets.Sets.Tier;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;

public class ToolSet {
	
	private ItemSwordPro sword;
	private ItemPickaxePro pickaxe;
	private ItemAxePro axe;
	private ItemShovelPro shovel;
	private ItemHoePro hoe;
	
	private ToolHandler handler;
	
	public ToolSet(ToolMaterial material, ToolHandler handler, String name, Tier tier) {
		
		this.handler = handler;
		
		this.sword = new ItemSwordPro(material, handler, "sword_" + name);
		this.pickaxe = new ItemPickaxePro(material, handler, "pickaxe_" + name);
		this.axe = new ItemAxePro(material, handler, tier, "axe_" + name);
		this.shovel = new ItemShovelPro(material, handler, "shovel_" + name);
		this.hoe = new ItemHoePro(material, handler, "hoe_" + name);
	}
	
	public ItemSwordPro getSword() {
		return sword;
	}
	
	public ItemPickaxePro getPickaxe() {
		return pickaxe;
	}
	
	public ItemAxePro getAxe() {
		return axe;
	}
	
	public ItemShovelPro getShovel() {
		return shovel;
	}
	
	public ItemHoePro getHoe() {
		return hoe;
	}
	
	public ToolHandler getHandler() {
		return handler;
	}
	
	public boolean hasHandler() {
		return handler != null;
	}
}
