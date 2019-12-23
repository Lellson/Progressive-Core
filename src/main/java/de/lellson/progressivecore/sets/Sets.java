package de.lellson.progressivecore.sets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.items.armor.handler.ArmorHandlerAdamantite;
import de.lellson.progressivecore.items.armor.handler.ArmorHandlerHellsteel;
import de.lellson.progressivecore.items.armor.handler.ArmorHandlerMithril;
import de.lellson.progressivecore.items.armor.handler.ArmorHandlerOrichalcum;
import de.lellson.progressivecore.items.armor.handler.ArmorHandlerSteel;
import de.lellson.progressivecore.items.armor.handler.ArmorHandlerTitanium;
import de.lellson.progressivecore.items.tools.handler.ToolHandlerAdamantite;
import de.lellson.progressivecore.items.tools.handler.ToolHandlerHellsteel;
import de.lellson.progressivecore.items.tools.handler.ToolHandlerMithril;
import de.lellson.progressivecore.items.tools.handler.ToolHandlerOrichalcum;
import de.lellson.progressivecore.items.tools.handler.ToolHandlerSteel;
import de.lellson.progressivecore.items.tools.handler.ToolHandlerTitanium;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.config.ProConfig;
import de.lellson.progressivecore.misc.helper.MiscHelper;
import de.lellson.progressivecore.sets.Sets.Tier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import scala.reflect.internal.StdAttachments.Attachable;

public class Sets {
	
	private static final List<Set> SETS = new ArrayList<Set>();
	
	public static final String WOOD = "wood";
	public static final ArmorMaterial WOOD_ARMOR = createArmorMaterial(WOOD, Tier.WOOD, 3, 11);
	public static final Set WOOD_SET = new Set(WOOD, Tier.WOOD, null, WOOD_ARMOR).setMaterial(Item.getItemFromBlock(Blocks.LOG));
	
	public static final String FLINT = "flint";
	public static final ToolMaterial FLINT_TOOL = createToolMaterial(FLINT, Tier.STONE, 165, 6);
	public static final Set FLINT_SET = new Set(FLINT, Tier.STONE, FLINT_TOOL, null).setMaterial(Items.FLINT);
	
	public static final String COPPER = "copper";
	public static final ToolMaterial COPPER_TOOL = createToolMaterial(COPPER, Tier.COPPER, 180, 16);
	public static final ArmorMaterial COPPER_ARMOR = createArmorMaterial(COPPER, Tier.COPPER, 11, 12);
	public static final Set COPPER_SET = new Set(COPPER, Tier.COPPER, COPPER_TOOL, COPPER_ARMOR).setOre(new SetOreDictionary(COPPER, true, true));
	
	public static final String TIN = "tin";
	public static final Set TIN_SET = new Set(TIN, Tier.COPPER, null, null).setOre(new SetOreDictionary("tin", true, true));
	
	public static final String BRONZE = "bronze";
	public static final ToolMaterial BRONZE_TOOL = createToolMaterial(BRONZE, Tier.IRON, 210, 19);
	public static final ArmorMaterial BRONZE_ARMOR = createArmorMaterial(BRONZE, Tier.IRON, 13, 14);
	public static final Set BRONZE_SET = new Set(BRONZE, Tier.IRON, BRONZE_TOOL, BRONZE_ARMOR, true).setOre(new SetOreDictionary(BRONZE, true, true));
	
	public static final String TUNGSTEN = "tungsten";
	public static final Set TUNGSTEN_SET = new Set(TUNGSTEN, Tier.IRON, null, null).setOre(new SetOreDictionary("tungsten", true, true));
	
	public static final String STEEL = "steel";
	public static final ToolMaterial STEEL_TOOL = createToolMaterial(STEEL, Tier.STEEL, 370, 12);
	public static final ArmorMaterial STEEL_ARMOR = createArmorMaterial(STEEL, Tier.STEEL, 20, 8);
	public static final Set STEEL_SET = new Set(STEEL, Tier.STEEL, STEEL_TOOL, STEEL_ARMOR, true).setHandlers(new ArmorHandlerSteel(), new ToolHandlerSteel()).setOre(new SetOreDictionary(STEEL, true, true));
	
	public static final String LUMINIUM = "luminium";
	public static final Set LUMINIUM_SET = new Set(LUMINIUM, Tier.STEEL, null, null, false, true, 1, 1).setPrefix("").setForceBlock().fuel(6400).setOre(new SetOreDictionary(LUMINIUM, true, true));
	
	public static final String NETHERITE = "netherite";
	public static final Set NETHERITE_SET = new Set(NETHERITE, Tier.HELL, null, null, false, true, 1, 3).setOre(new SetOreDictionary(NETHERITE, true, true));
	
	public static final String HELLSTEEL = "hellsteel";
	public static final ToolMaterial HELLSTEEL_TOOL = createToolMaterial(HELLSTEEL, Tier.HELL, 600, 21);
	public static final ArmorMaterial HELLSTEEL_ARMOR = createArmorMaterial(HELLSTEEL, Tier.HELL, 26, 15);
	public static final Set HELLSTEEL_SET = new Set(HELLSTEEL, Tier.HELL, HELLSTEEL_TOOL, HELLSTEEL_ARMOR, true).setHandlers(new ArmorHandlerHellsteel(), new ToolHandlerHellsteel()).setOre(new SetOreDictionary(HELLSTEEL, true, true));
	
	public static final String OBSIDIAN = "obsidian";
	public static final ToolMaterial OBSIDIAN_TOOL = createToolMaterial(OBSIDIAN, Tier.STEEL, -1, 3);
	public static final ArmorMaterial OBSIDIAN_ARMOR = createArmorMaterial(OBSIDIAN, Tier.STEEL, -1, 2);
	public static final Set OBSIDIAN_SET = new Set(OBSIDIAN, Tier.STEEL, OBSIDIAN_TOOL, OBSIDIAN_ARMOR, true);
	
	public static final String TITANIUM = "titanium";
	public static final Set TITANIUM_SET_ORE = new Set(TITANIUM, Tier.TITAN, null, null, false, true, 1, 3).setNetherOre().setOre(new SetOreDictionary(TITANIUM));
	
	public static final ToolMaterial TITANIUM_TOOL = createToolMaterial(TITANIUM, Tier.TITAN, 1200, 18);
	public static final ArmorMaterial TITANIUM_ARMOR = createArmorMaterial(TITANIUM, Tier.TITAN, 26, 13);
	public static final Set TITANIUM_SET = new Set(TITANIUM, Tier.TITAN, TITANIUM_TOOL, TITANIUM_ARMOR, true).setHandlers(new ArmorHandlerTitanium(), new ToolHandlerTitanium()).setOre(new SetOreDictionary(TITANIUM));
	
	public static final String MITHRIL = "mithril";
	public static final Set MITHRIL_SET_ORE = new Set(MITHRIL, Tier.MYSTIC, null, null, false, true, 1, 3).setOre(new SetOreDictionary(MITHRIL));
	
	public static final ToolMaterial MITHRIL_TOOL = createToolMaterial(MITHRIL, Tier.MYSTIC, 2250, 20);
	public static final ArmorMaterial MITHRIL_ARMOR = createArmorMaterial(MITHRIL, Tier.MYSTIC, 50, 15);
	public static final Set MITHRIL_SET = new Set(MITHRIL, Tier.MYSTIC, MITHRIL_TOOL, MITHRIL_ARMOR, true).setHandlers(new ArmorHandlerMithril(), new ToolHandlerMithril()).setOre(new SetOreDictionary(MITHRIL));
	
	public static final String ORICHALCUM = "orichalcum";
	public static final Set ORICHALCUM_SET_ORE = new Set(ORICHALCUM, Tier.MYSTIC, null, null, false, true, 1, 3).setOre(new SetOreDictionary(ORICHALCUM));
	
	public static final ToolMaterial ORICHALCUM_TOOL = createToolMaterial(ORICHALCUM, Tier.MYSTIC, 2250, 20);
	public static final ArmorMaterial ORICHALCUM_ARMOR = createArmorMaterial(ORICHALCUM, Tier.MYSTIC, 50, 15);
	public static final Set ORICHALCUM_SET = new Set(ORICHALCUM, Tier.MYSTIC, ORICHALCUM_TOOL, ORICHALCUM_ARMOR, true).setHandlers(new ArmorHandlerOrichalcum(), new ToolHandlerOrichalcum()).setOre(new SetOreDictionary(ORICHALCUM));
	
	public static final String ADAMANTITE = "adamantite";
	public static final Set ADAMANTITE_SET_ORE = new Set(ADAMANTITE, Tier.MYSTIC, null, null, false, true, 1, 3).setOre(new SetOreDictionary(ADAMANTITE));
	
	public static final ToolMaterial ADAMANTITE_TOOL = createToolMaterial(ADAMANTITE, Tier.MYSTIC, 2250, 20);
	public static final ArmorMaterial ADAMANTITE_ARMOR = createArmorMaterial(ADAMANTITE, Tier.MYSTIC, 50, 15);
	public static final Set ADAMANTITE_SET = new Set(ADAMANTITE, Tier.MYSTIC, ADAMANTITE_TOOL, ADAMANTITE_ARMOR, true).setHandlers(new ArmorHandlerAdamantite(), new ToolHandlerAdamantite()).setOre(new SetOreDictionary(ADAMANTITE));
	
	public static void init() {
		
		for (Set set : getSets())
		{
			set.createSet();
		}
	}
	
	public static List<Set> getSets() {
		
		if (SETS.isEmpty())
		{
			for (Field field : Sets.class.getFields())
			{
				field.setAccessible(true);
				try 
				{
					Object obj = field.get(null);
					if (obj instanceof Set)
					{
						SETS.add((Set) obj);
					}
				} 
				catch (IllegalArgumentException | IllegalAccessException e) 
				{
					FMLLog.bigWarning(e.getMessage());
				}
			}
		}
		
		return SETS;
	}
	
	private static final int MAX = Short.MAX_VALUE;
	
	private static ToolMaterial createToolMaterial(String name, Tier tier, int defaultMaxUses, int defaultEnchantability) {
		
		String category = "tool" + MiscHelper.upperFirst(name);
		ProConfig.cfg.addCustomCategoryComment(category, MiscHelper.upperFirst(name) + " tool and weapon settings");
		
		int maxUses = ProConfig.cfg.getInt(name + "Durability", category, defaultMaxUses, -1, MAX, "Determines how many uses " + name + " tools and weapons have. -1 means infinite but disables enchanting.");
		int harvestLevel = ProConfig.cfg.getInt(name + "HarvestLevel", category, tier.getHarvestLevel(), 0, MAX, "Determines which blocks " + name + " tools can mine.\ne.g a value bigger or equal to 3 means the tool can mine obsidian");
		float efficiency = ProConfig.cfg.getFloat(name + "Efficiency", category, tier.getEfficiency(), 0.1F, MAX, "Determines how fast " + name + " tools mine blocks.");
		float attackDamage = ProConfig.cfg.getFloat(name + "AttackDamage", category, tier.getAttackDamage(), -MAX, MAX, "Determines the attack damage of " + name + " tools and weapons (+4 attack on swords).\nAxes use the \"" + name + "AxeAttackDamage\" option.");
		int enchantability = ProConfig.cfg.getInt(name + "Enchantability", category, defaultEnchantability, 1, MAX, "Determines how enchantable " + name + " tools are. A higher value means better enchanting results.");
		
		return EnumHelper.addToolMaterial(Constants.prefix(category), harvestLevel, maxUses, efficiency, attackDamage, enchantability);
	}
	
	private static ArmorMaterial createArmorMaterial(String name, Tier tier, int defaultMaxUses, int defaultEnchantability) {
		
		String category = "armor" + MiscHelper.upperFirst(name);
		ProConfig.cfg.addCustomCategoryComment(category, MiscHelper.upperFirst(name) + " armor settings");
		
		int maxUses = ProConfig.cfg.getInt(name + "Durability", category, defaultMaxUses, -1, MAX, "Determines how many uses " + name + " armor have. They value is multiplied depending on the armor type. -1 means infinite but disables enchanting.\nMultipliers: boots = 13, leggings = 15, chestplate = 16, helmet = 11");
		String[] armorReduction = ProConfig.cfg.getStringList(name, category, tier.getArmorReduction(), "Determines the armor reduction values of " + name + " armor. Each line is for another armor type.\nOrder: boots, leggings, chestplate, helmet");
		float toughness = ProConfig.cfg.getFloat(name + "Toughness", category, tier.getToughness(), 0, MAX, "Determines the toughness of " + name + " armor.");
		int enchantability = ProConfig.cfg.getInt(name + "Enchantability", category, defaultEnchantability, 1, MAX, "Determines how enchantable " + name + " armor is. A higher value means better enchanting results.");
		
		return EnumHelper.addArmorMaterial(Constants.prefix(name + "Armor"), name + "_armor", maxUses, MiscHelper.toIntArray(armorReduction, 0), enchantability, tier.getArmorSound(), toughness);
	}
	
	public static String[] getTicSets() {
		return ProConfig.ticEditSets;
	}
	
	public static Set getForName(String name) {

		for (Set set : getSets())
			if (set.getName().equals(name))
				return set;
		
		return null;
	}
	
	public static enum Tier {
		WOOD(0, 2.0F, 0.0F, new String[]{"1", "1", "2", "1"}, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 6.0F, -3.2F, 0, 0, 0.1f, 0),
		STONE(1, 4.0F, 1.0F, new String[]{"1", "2", "3", "1"}, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 8.0F, -3.2F, 0, 0, 0.25f, 0), 
		COPPER(1, 5.0F, 1.5F, new String[]{"1", "4", "5", "2"}, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F, 8.0F, -3.1F, 1, 0, 0.5f, 2),
		IRON(2, 6.0F, 2.0F, new String[]{"2", "5", "6", "2"}, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 8.0F, -3.1F, 1, 0, 0.7f, 2),
		STEEL(4, 7.0F, 2.5F, new String[]{"2", "5", "6", "2"}, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F, 8.0F, -3.1F, 2, 0, 1.2f, 5),
		HELL(5, 8.0F, 3.0F, new String[]{"3", "6", "8", "3"}, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 8.0F, -3.0F, 4, 1, 1.5f, 7),
		TITAN(6, 10.0F, 5.0F, new String[]{"4", "7", "9", "4"}, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0F, 10.0F, -3.0F, 5, 3, 2f, 10),
		MYSTIC(6, 12.0F, 8.0F, new String[]{"5", "8", "10", "5"}, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 5.0F, 14.0F, -3.0F, 6, 7, 3f, 20);
		
		private int harvestLevel;
		private float efficiency;
		private float attackDamage;
		private String[] armorReduction;
		private SoundEvent armorSound;
		private float toughness;
		private float axeAttackDamage;
		private float axeSpeed;
		private int requiredHarvestLevel;
		private float extraBlockHardness;
		private float smeltXp;
		private int xp;
		
		private Tier(int havestLevel, float efficiency, float attackDamage, String[] armorReduction, SoundEvent armorSound, float toughness, float axeAttackDamage, float axeSpeed, int requiredHarvestLevel, float extraBlockHardness, float smeltXp, int xp) {
			this.harvestLevel = havestLevel;
			this.efficiency = efficiency;
			this.attackDamage = attackDamage;
			this.armorReduction = armorReduction;
			this.armorSound = armorSound;
			this.toughness = toughness;
			this.axeAttackDamage = axeAttackDamage;
			this.axeSpeed = axeSpeed;
			this.requiredHarvestLevel = requiredHarvestLevel;
			this.extraBlockHardness = extraBlockHardness;
			this.smeltXp = smeltXp;
			this.xp = xp;
		}
		
		public int getHarvestLevel() {
			return harvestLevel;
		}
		
		public float getEfficiency() {
			return efficiency;
		}
		
		public float getAttackDamage() {
			return attackDamage;
		}
		
		public String[] getArmorReduction() {
			return armorReduction;
		}
		
		public SoundEvent getArmorSound() {
			return armorSound;
		}
		
		public float getToughness() {
			return toughness;
		}
		
		public float getAxeAttackDamage() {
			return axeAttackDamage;
		}
		
		public float getAxeSpeed() {
			return axeSpeed;
		}
		
		public int getRequiredHarvestLevel() {
			return requiredHarvestLevel;
		}
		
		public float getExtraBlockHardness() {
			return extraBlockHardness;
		}
		
		public float getSmeltXp() {
			return smeltXp;
		}
		
		public int getXp() {
			return xp;
		}
	}
}
