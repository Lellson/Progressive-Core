package de.lellson.progressivecore.misc.config;

import de.lellson.progressivecore.inv.recipe.SmelterEntry;
import de.lellson.progressivecore.misc.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ProConfig {
	
	public static Configuration cfg;
	
	private static final String CATEGORY_SMELTER = "smelter";
	private static final String CATEGORY_POLISHER = "polisher";
	private static final String CATEGORY_MISC = "misc";
	
	private static String[] smelterEntriesNormal;
	private static String[] smelterEntriesAdvanced;
	private static String[] smelterEntriesInfernal;
	private static String[] smelterEntriesTitan;
	
	private static String[] polisherEntries;
	
	public static int eyeRange;
	
	public static float adamantiteDamage, mithrilDamage, orichalcumDamage;
	public static float adamantiteSpeed, mithrilSpeed, orichalcumSpeed;
	
	public static float smelterFuelMultiplier;
	
	public static String[] ticEditSets;
	
	public static boolean villagerGemTrades;
	
	public static void init(FMLPreInitializationEvent event) {
		
		cfg = new Configuration(event.getSuggestedConfigurationFile());
		cfg.load();
		
		cfg.addCustomCategoryComment(CATEGORY_SMELTER, "SMELTER RECIPES divided in all 4 smelter levels. Keep in mind that lower level recipes also work for alls levels above, but they can be overriden by higher level recipes!\n" +
				 										"########################################\n" +
														"To add a recipe simply add a new line with the following format:\n" +
														"smelt_duration ; xp ; output_item ; input_items...\n" + 
														"smelt_duration: Time in ticks needed to smelt the item (20 ticks = 1 second).\n" + 
														"xp: Amount of xp dropped per item.\n" +
														"output_item: The result of the recipe.\n" +
														"input_items: The ingredients of the recipe (up to 6).\n" +
														"########################################\n" +
														"Input and Output Items have the following format:\n" + 
														"item_name,amount,meta\n" +
														"item_name: The name of the item. Usually with the modid and a colon in front (minecraft for vanilla). You can also use Ore Dictionary names.\n" +
														"amount: the stacksize of the item (OPTIONAL). This can also be a min-max random value.\n" + 
														"meta: the metadata value of the item (OPTIONAL).");
		
		cfg.addCustomCategoryComment(CATEGORY_POLISHER, "GEM POLISHER RECIPES\n" +
														"########################################\n" +
														"To add a recipe simply add a new line with the following format:\n" +
														"output_item ; input_item\n" +
														"output_item: The result of the recipe.\n" +
														"input_item: The ingredient of the recipe.\n" +
														"########################################\n" +
														"Input and Output Items have the following format:\n" + 
														"item_name,amount,meta\n" +
														"item_name: The name of the item. Usually with the modid and a colon in front (minecraft for vanilla). You can also use Ore Dictionary names.\n" +
														"amount: the stacksize of the item (OPTIONAL). This can also be a min-max random value.\n" + 
														"meta: the metadata value of the item (OPTIONAL).");
		
		smelterEntriesNormal = cfg.getStringList("entriesNormal", CATEGORY_SMELTER, Constants.DEFAULT_SMELTER, "Smelter recipes for level 1 (normal) and above");
		smelterEntriesAdvanced = cfg.getStringList("entriesAdvanced", CATEGORY_SMELTER, Constants.DEFAULT_SMELTER_ADVANCED, "Smelter recipes for level 2 (advanced) and above");
		smelterEntriesInfernal = cfg.getStringList("entriesInfernal", CATEGORY_SMELTER, Constants.DEFAULT_SMELTER_INFERNAL, "Smelter recipes for level 3 (infernal) and above");
		smelterEntriesTitan = cfg.getStringList("entriesTitan", CATEGORY_SMELTER, Constants.DEFAULT_SMELTER_TITAN, "Smelter recipes for level 4 (titan)");
		
		smelterFuelMultiplier = cfg.getFloat("smelterFuelMultiplier", CATEGORY_SMELTER, 0.5F, 0.01F, Short.MAX_VALUE, "Fuel multiplier for smelters. e.g. 0.5 means 1 piece of coal can smelt half as much items a regular furnace can (4).");
		
		polisherEntries = cfg.getStringList("polisherEntries", CATEGORY_POLISHER, Constants.DEFAULT_POLISHER, "Polisher recipes");
		
		ticEditSets = cfg.getStringList("ticEditSets", CATEGORY_MISC, Constants.DEFAULT_TIC_SETS, "Already existing Tinkers Construct materials which get adjusted to this mods tool stats");
		
		eyeRange = cfg.getInt("eyeRange", CATEGORY_MISC, 24, 1, 255, "Block range in each direction in which the eye of reality shows ores. Highly affects performance if this is set too high!");
		
		adamantiteDamage = cfg.getFloat("adamantiteProjectileDamage", "toolAdamantite", 1.0f, 0, Short.MAX_VALUE, "Damage multiplier for adamantite projectiles (Multiplies the swords attack damage).");
		adamantiteSpeed = cfg.getFloat("adamantiteProjectileSpeed", "toolAdamantite", 0.5f, 0, Short.MAX_VALUE, "Speed multiplier for adamantite projectiles.");
		mithrilDamage = cfg.getFloat("mithrilProjectileDamage", "toolMithril", 0.75f, 0, Short.MAX_VALUE, "Damage multiplier for mithril projectiles (Multiplies the swords attack damage).");
		mithrilSpeed = cfg.getFloat("mithrilProjectileSpeed", "toolMithril", 0.75f, 0, Short.MAX_VALUE, "Speed multiplier for mithril projectiles.");
		orichalcumDamage = cfg.getFloat("orichalcumProjectileDamage", "toolOrichalcum", 0.5f, 0, Short.MAX_VALUE, "Damage multiplier for orichalcum projectiles (Multiplies the swords attack damage).");
		orichalcumSpeed = cfg.getFloat("orichalcumProjectileSpeed", "toolOrichalcum", 1.0f, 0, Short.MAX_VALUE, "Speed multiplier for orichalcum projectiles.");
		
		villagerGemTrades = cfg.getBoolean("villagerGemTrades", CATEGORY_MISC, true, "If false, villagers only trade with emeralds and not any other gems (Like vanilla).");
	}
	
	public static void save() {
		
		if (cfg != null && cfg.hasChanged())
			cfg.save();
	}
	
	public static String[][] getSmelterEntries() {
		return new String[][] {smelterEntriesNormal, smelterEntriesAdvanced, smelterEntriesInfernal, smelterEntriesTitan};
	}

	public static String[] getPolisherEntries() {
		return polisherEntries;
	}
}
