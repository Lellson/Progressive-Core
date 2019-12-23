package de.lellson.progressivecore.misc;

import java.util.Random;

import net.minecraft.util.ResourceLocation;

public class Constants {
	
	public static final String MOD_ID = "progressivecore";
	public static final String MOD_NAME = "Progressive Core";
	public static final String MOD_VERSION = "0.1";
	public static final String PREFIX = MOD_ID + ":";
	
	public static final String PROXY_CLIENT = "de.lellson.progressivecore.ClientProxy";
	public static final String PROXY_COMMON = "de.lellson.progressivecore.CommonProxy";
	
	public static final String INVENTORY = "inventory";
	
	public static final Random RND = new Random();
	
	public static final String[] DEFAULT_SMELTER = {
			"400 ; 0.1 ; minecraft:coal ; oreCoal",
			"400 ; 0.7 ; ingotIron ; oreIron",
			"400 ; 1.0 ; ingotGold ; oreGold",
			"400 ; 0.2 ; minecraft:dye,2,4 ; oreLapis",
			"400 ; 0.7 ; dustRedstone,2 ; oreRedstone",
			"400 ; 1.0 ; gemDiamond ; oreDiamond",
			"400 ; 1.0 ; gemEmerald ; oreEmerald",
			"400 ; 0.2 ; minecraft:quartz ; oreQuartz",
			"400 ; 0.5 ; ingotCopper ; oreCopper",
			"400 ; 0.5 ; ingotTin ; oreTin",
			"400 ; 0.7 ; ingotTungsten ; oreTungsten",
			"400 ; 1.0 ; gemRuby ; oreRuby",
			"400 ; 1.0 ; gemSapphire ; oreSapphire",
			"400 ; 1.0 ; gemAmethyst ; oreAmethyst",
			"400 ; 1.0 ; gemTopaz ; oreTopaz",
			"400 ; 1.0 ; gemOpal ; oreOpal",
			"400 ; 1.0 ; gemMalachite ; oreMalachite",
			"400 ; 0.7 ; progressivecore:luminium ; oreLuminium",
			"400 ; 1.0 ; progressivecore:chunk_netherite ; oreNetherite",
			"400 ; 1.5 ; progressivecore:chunk_titanium ; oreTitaniumPro",
			"400 ; 2 ; progressivecore:chunk_mithril ; oreMithrilPro",
			"400 ; 2 ; progressivecore:chunk_orichalcum ; oreOrichalcumPro",
			"400 ; 2 ; progressivecore:chunk_adamantite ; oreAdamantitePro",
			"600 ; 0.7 ; ingotBronze,4 ; ingotCopper ; ingotCopper ; ingotCopper ; ingotTin",
			"600 ; 0.7 ; ingotBronze,4 ; oreCopper ; oreCopper ; oreCopper ; oreTin"
	};
	
	public static final String[] DEFAULT_SMELTER_ADVANCED = {
			"400 ; 0.1 ; minecraft:coal,1-2 ; oreCoal",
			"400 ; 0.7 ; ingotIron,1-2 ; oreIron",
			"400 ; 1.0 ; ingotGold,1-2 ; oreGold",
			"400 ; 0.2 ; minecraft:dye,3-6,4 ; oreLapis",
			"400 ; 0.7 ; dustRedstone,3-4 ; oreRedstone",
			"400 ; 1.0 ; gemDiamond,1-2 ; oreDiamond",
			"400 ; 1.0 ; gemEmerald,1-2 ; oreEmerald",
			"400 ; 0.2 ; minecraft:quartz,1-2 ; oreQuartz",
			"400 ; 0.5 ; ingotCopper,1-2 ; oreCopper",
			"400 ; 0.5 ; ingotTin,1-2 ; oreTin",
			"400 ; 0.7 ; ingotTungsten,1-2 ; oreTungsten",
			"400 ; 1.0 ; gemRuby,1-2 ; oreRuby",
			"400 ; 1.0 ; gemSapphire,1-2 ; oreSapphire",
			"400 ; 1.0 ; gemAmethyst,1-2 ; oreAmethyst",
			"400 ; 1.0 ; gemTopaz,1-2 ; oreTopaz",
			"400 ; 1.0 ; gemOpal,1-2 ; oreOpal",
			"400 ; 1.0 ; gemMalachite,1-2 ; oreMalachite",
			"400 ; 0.7 ; progressivecore:luminium,1-2 ; oreLuminium",
			"400 ; 1.0 ; progressivecore:chunk_netherite,1-2 ; oreNetherite",
			"400 ; 1.5 ; progressivecore:chunk_titanium,1-2 ; oreTitaniumPro",
			"400 ; 2 ; progressivecore:chunk_mithril,1-2 ; oreMithrilPro",
			"400 ; 2 ; progressivecore:chunk_orichalcum,1-2 ; oreOrichalcumPro",
			"400 ; 2 ; progressivecore:chunk_adamantite,1-2 ; oreAdamantitePro",
			"600 ; 0.7 ; ingotBronze,4-8 ; oreCopper ; oreCopper ; oreCopper ; oreTin",
			"1000 ; 1.2 ; ingotSteel,2 ; ingotTungsten ; ingotIron ; minecraft:coal",
			"1000 ; 1.2 ; ingotSteel,2-4 ; oreTungsten ; oreIron ; minecraft:coal",
			"1000 ; 1.2 ; ingotSteel,2 ; ingotTungsten ; ingotIron ; minecraft:coal,1,1",
			"1000 ; 1.2 ; ingotSteel,2-4 ; oreTungsten ; oreIron ; minecraft:coal,1,1",
			"1000 ; 1.2 ; ingotSteel,2 ; ingotTungsten ; ingotIron ; progressivecore:luminium",
			"1000 ; 1.2 ; ingotSteel,2-4 ; oreTungsten ; oreIron ; progressivecore:luminium",
			"1500 ; 1.5 ; ingotHellsteel ; ingotSteel ; progressivecore:chunk_netherite ; progressivecore:chunk_netherite ; minecraft:blaze_powder"
	};
	
	public static final String[] DEFAULT_SMELTER_INFERNAL = {
			"400 ; 0.1 ; minecraft:coal,2 ; oreCoal",
			"400 ; 0.7 ; ingotIron,2 ; oreIron",
			"400 ; 1.0 ; ingotGold,2 ; oreGold",
			"400 ; 0.2 ; minecraft:dye,4-8,4 ; oreLapis",
			"400 ; 0.7 ; dustRedstone,4-5 ; oreRedstone",
			"400 ; 1.0 ; gemDiamond,2 ; oreDiamond",
			"400 ; 1.0 ; gemEmerald,2 ; oreEmerald",
			"400 ; 0.2 ; minecraft:quartz,2 ; oreQuartz",
			"400 ; 0.5 ; ingotCopper,2 ; oreCopper",
			"400 ; 0.5 ; ingotTin,2 ; oreTin",
			"400 ; 0.7 ; ingotTungsten,2 ; oreTungsten",
			"400 ; 1.0 ; gemRuby,2 ; oreRuby",
			"400 ; 1.0 ; gemSapphire,2 ; oreSapphire",
			"400 ; 1.0 ; gemAmethyst,2 ; oreAmethyst",
			"400 ; 1.0 ; gemTopaz,2 ; oreTopaz",
			"400 ; 1.0 ; gemOpal,2 ; oreOpal",
			"400 ; 1.0 ; gemMalachite,2 ; oreMalachite",
			"400 ; 0.7 ; progressivecore:luminium,2 ; oreLuminium",
			"400 ; 1.0 ; progressivecore:chunk_netherite,2 ; oreNetherite",
			"400 ; 1.5 ; progressivecore:chunk_titanium,2 ; oreTitaniumPro",
			"400 ; 2 ; progressivecore:chunk_mithril,2 ; oreMithrilPro",
			"400 ; 2 ; progressivecore:chunk_orichalcum,2 ; oreOrichalcumPro",
			"400 ; 2 ; progressivecore:chunk_adamantite,2 ; oreAdamantitePro",
			"600 ; 0.7 ; ingotBronze,8 ; oreCopper ; oreCopper ; oreCopper ; oreTin",
			"1000 ; 1.2 ; ingotSteel,4 ; oreTungsten ; oreIron ; minecraft:coal",
			"1000 ; 1.2 ; ingotSteel,4 ; oreTungsten ; oreIron ; minecraft:coal,1,1",
			"1000 ; 1.2 ; ingotSteel,4 ; oreTungsten ; oreIron ; progressivecore:luminium",
			"1500 ; 1.5 ; ingotHellsteel,1-2 ; ingotSteel ; progressivecore:chunk_netherite ; progressivecore:chunk_netherite ; minecraft:blaze_powder",
			"2000 ; 2 ; progressivecore:ingot_obsidian ; ingotHellsteel ; gemDiamond ; minecraft:obsidian ; minecraft:obsidian ; minecraft:obsidian",
			"2000 ; 2 ; progressivecore:ingot_obsidian ; ingotHellsteel ; minecraft:ender_pearl ; minecraft:obsidian ; minecraft:obsidian ; minecraft:obsidian",
			"2000 ; 2 ; progressivecore:ingot_titanium ; gemDiamond ; progressivecore:chunk_titanium ; progressivecore:chunk_titanium ; progressivecore:chunk_titanium",
			"2000 ; 2 ; progressivecore:ingot_titanium ; minecraft:ender_pearl ; progressivecore:chunk_titanium ; progressivecore:chunk_titanium ; progressivecore:chunk_titanium"
	};
	
	public static final String[] DEFAULT_SMELTER_TITAN = {
			"400 ; 0.1 ; minecraft:coal,2-3 ; oreCoal",
			"400 ; 0.7 ; ingotIron,2-3 ; oreIron",
			"400 ; 1.0 ; ingotGold,2-3 ; oreGold",
			"400 ; 0.2 ; minecraft:dye,6-10,4 ; oreLapis",
			"400 ; 0.7 ; dustRedstone,5-6 ; oreRedstone",
			"400 ; 1.0 ; gemDiamond,2-3 ; oreDiamond",
			"400 ; 1.0 ; gemEmerald,2-3 ; oreEmerald",
			"400 ; 0.2 ; minecraft:quartz,2-3 ; oreQuartz",
			"400 ; 0.5 ; ingotCopper,2-3 ; oreCopper",
			"400 ; 0.5 ; ingotTin,2-3 ; oreTin",
			"400 ; 0.7 ; ingotTungsten,2-3 ; oreTungsten",
			"400 ; 1.0 ; gemRuby,2-3 ; oreRuby",
			"400 ; 1.0 ; gemSapphire,2-3 ; oreSapphire",
			"400 ; 1.0 ; gemAmethyst,2-3 ; oreAmethyst",
			"400 ; 1.0 ; gemTopaz,2-3 ; oreTopaz",
			"400 ; 1.0 ; gemOpal,2-3 ; oreOpal",
			"400 ; 1.0 ; gemMalachite,2-3 ; oreMalachite",
			"400 ; 0.7 ; progressivecore:luminium,2-3 ; oreLuminium",
			"400 ; 1.0 ; progressivecore:chunk_netherite,2-3 ; oreNetherite",
			"400 ; 1.5 ; progressivecore:chunk_titanium,2-3 ; oreTitaniumPro",
			"400 ; 2 ; progressivecore:chunk_mithril,2-3 ; oreMithrilPro",
			"400 ; 2 ; progressivecore:chunk_orichalcum,2-3 ; oreOrichalcumPro",
			"400 ; 2 ; progressivecore:chunk_adamantite,2-3 ; oreAdamantitePro",
			"600 ; 0.7 ; ingotBronze,8-12 ; oreCopper ; oreCopper ; oreCopper ; oreTin",
			"1000 ; 1.2 ; ingotSteel,4-6 ; oreTungsten ; oreIron ; minecraft:coal",
			"1000 ; 1.2 ; ingotSteel,4-6 ; oreTungsten ; oreIron ; minecraft:coal,1,1",
			"1000 ; 1.2 ; ingotSteel,4-6 ; oreTungsten ; oreIron ; progressivecore:luminium",
			"1500 ; 1.5 ; ingotHellsteel,2 ; ingotSteel ; progressivecore:chunk_netherite ; progressivecore:chunk_netherite ; minecraft:blaze_powder",
			"2000 ; 2 ; progressivecore:ingot_obsidian,1-2 ; ingotHellsteel ; gemDiamond ; minecraft:obsidian ; minecraft:obsidian ; minecraft:obsidian",
			"2000 ; 2 ; progressivecore:ingot_obsidian,1-2 ; ingotHellsteel ; minecraft:ender_pearl ; minecraft:obsidian ; minecraft:obsidian ; minecraft:obsidian",
			"2000 ; 2 ; progressivecore:ingot_titanium,1-2 ; gemDiamond ; progressivecore:chunk_titanium ; progressivecore:chunk_titanium ; progressivecore:chunk_titanium",
			"2000 ; 2 ; progressivecore:ingot_titanium,1-2 ; minecraft:ender_pearl ; progressivecore:chunk_titanium ; progressivecore:chunk_titanium ; progressivecore:chunk_titanium",
			"2500 ; 2.5 ; progressivecore:reality_crystal ; dustGem ; dustGem ; dustGem ; dustGem ; dustGem ; dustGem",
			"3000 ; 3 ; progressivecore:ingot_mithril ; progressivecore:reality_crystal ; progressivecore:chunk_mithril ; progressivecore:chunk_mithril ; progressivecore:chunk_mithril ; progressivecore:chunk_mithril",
			"3000 ; 3 ; progressivecore:ingot_orichalcum ; progressivecore:reality_crystal ; progressivecore:chunk_orichalcum ; progressivecore:chunk_orichalcum ; progressivecore:chunk_orichalcum ; progressivecore:chunk_orichalcum",
			"3000 ; 3 ; progressivecore:ingot_adamantite ; progressivecore:reality_crystal ; progressivecore:chunk_adamantite ; progressivecore:chunk_adamantite ; progressivecore:chunk_adamantite ; progressivecore:chunk_adamantite"
	};
	
	public static final String[] DEFAULT_TIC_SETS = {
			"flint",
			"copper",
			"bronze",
			"steel"
	};
	public static final String[] DEFAULT_POLISHER = {
			"progressivecore:dust_gem,1,0 ; gemRuby",
			"progressivecore:dust_gem,1,1 ; gemSapphire",
			"progressivecore:dust_gem,1,2 ; gemAmethyst",
			"progressivecore:dust_gem,1,3 ; gemTopaz",
			"progressivecore:dust_gem,1,4 ; gemOpal",
			"progressivecore:dust_gem,1,5 ; gemMalachite",
			"progressivecore:dust_emerald ; gemEmerald",
			"progressivecore:cut_gem,1,0 ; blockRuby",
			"progressivecore:cut_gem,1,1 ; blockSapphire",
			"progressivecore:cut_gem,1,2 ; blockAmethyst",
			"progressivecore:cut_gem,1,3 ; blockTopaz",
			"progressivecore:cut_gem,1,4 ; blockOpal",
			"progressivecore:cut_gem,1,5 ; blockMalachite",
			"progressivecore:cut_emerald ; blockEmerald",
			"progressivecore:nether_star_piece,4 ; minecraft:nether_star",
	};
	
	public static String prefix(String name) {
		return name.startsWith(PREFIX) ? name : PREFIX + name;
	}
	
	public static ResourceLocation registry(String name) {
		return new ResourceLocation(prefix(name));
	}
	
	public static int random(int min, int max) {
		return min + RND.nextInt(max-min);
	}
	
	public static int random(int max) {
		return random(0, max);
	}
}
