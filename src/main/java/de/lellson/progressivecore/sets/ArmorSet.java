package de.lellson.progressivecore.sets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.lellson.progressivecore.items.armor.ItemArmorPro;
import de.lellson.progressivecore.items.armor.handler.ArmorHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.NonNullList;
import net.minecraft.item.ItemStack;

public class ArmorSet {
	
	public static final List<ArmorSet> ARMOR_SETS = new ArrayList<ArmorSet>();
	
	private ItemArmorPro helmet;
	private ItemArmorPro chestplate;
	private ItemArmorPro leggings;
	private ItemArmorPro boots;
	
	private ArmorHandler handler;
	
	public ArmorSet(ArmorMaterial material, ArmorHandler handler, String name) {
		
		this.handler = handler;
		
		this.helmet = new ItemArmorPro(material, this, EntityEquipmentSlot.HEAD, "helmet_" + name);
		this.chestplate = new ItemArmorPro(material, this, EntityEquipmentSlot.CHEST, "chestplate_" + name);
		this.leggings = new ItemArmorPro(material, this, EntityEquipmentSlot.LEGS, "leggings_" + name);
		this.boots = new ItemArmorPro(material, this, EntityEquipmentSlot.FEET, "boots_" + name);
		
		ARMOR_SETS.add(this);
	}
	
	public ItemArmorPro getHelmet() {
		return helmet;
	}
	
	public ItemArmorPro getChestplate() {
		return chestplate;
	}
	
	public ItemArmorPro getLeggings() {
		return leggings;
	}
	
	public ItemArmorPro getBoots() {
		return boots;
	}

	public boolean hasFullSet(Entity entity) {
		return getSlots(entity).size() >= 4;
	}
	
	public ArmorHandler getHandler() {
		return handler;
	}

	public boolean hasHandler() {
		return handler != null;
	}

	public List<EntityEquipmentSlot> getSlots(Entity entity) {
		
		List<EntityEquipmentSlot> slots = new ArrayList<EntityEquipmentSlot>();
		
		if (entity == null)
			return slots;
		
		Iterator<ItemStack> playerArmor = entity.getArmorInventoryList().iterator();
		Item[] armor = new Item[] {boots, leggings, chestplate, helmet};
		
		int i = 0;
		while(playerArmor.hasNext())
		{
			ItemStack piece = playerArmor.next();
			if (piece.isItemEqualIgnoreDurability(new ItemStack(armor[i]))) 
				slots.add(EntityEquipmentSlot.values()[i+2]);
			i++;
		}
		
		return slots;
	}
}
