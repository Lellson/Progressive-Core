package de.lellson.progressivecore.items;

import de.lellson.progressivecore.items.tools.ItemTitanCrusher;
import de.lellson.progressivecore.items.tools.special.ItemLightSaber;
import de.lellson.progressivecore.items.tools.special.ItemMagmaticBlade;
import de.lellson.progressivecore.misc.Constants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProItems {
	
	public static final String[] GEMS = new String[] { "ruby", "sapphire", "amethyst", "topaz", "opal", "malachite" };
	public static final ItemPro GEM = new ItemPro("gem", GEMS).oreDict("gem");
	public static final ItemPro DUST = new ItemPro("dust_gem", GEMS).oreDict("dust").oreDict("dustGem", false);
	public static final ItemPro CUT_GEM = new ItemPro("cut_gem", GEMS).oreDict("cut");
	
	public static final ItemPro EMERALD_DUST = new ItemPro("dust_emerald").oreDict("dustEmerald").oreDict("dustGem", false);
	public static final ItemPro EMERALD_CUT = new ItemPro("cut_emerald").oreDict("cutEmerald");
	public static final ItemPro REALITY_CRYSTAL = new ItemPro("reality_crystal");
	public static final ItemPro NETHER_STAR_PIECE = new ItemPro("nether_star_piece");
	
	public static final ItemTitanCrusher TITAN_CRUSHER = new ItemTitanCrusher();
	
	public static final ItemPro LIGHT_SABER_HILT = new ItemPro("light_saber_hilt");
	public static final ItemLightSaber RED_LIGHT_SABER = new ItemLightSaber("red");
	public static final ItemLightSaber BLUE_LIGHT_SABER = new ItemLightSaber("blue");
	public static final ItemLightSaber PURPLE_LIGHT_SABER = new ItemLightSaber("purple");
	public static final ItemLightSaber YELLOW_LIGHT_SABER = new ItemLightSaber("yellow");
	public static final ItemLightSaber ORANGE_LIGHT_SABER = new ItemLightSaber("orange");
	public static final ItemLightSaber TURQUOISE_LIGHT_SABER = new ItemLightSaber("turquoise");
	public static final ItemLightSaber GREEN_LIGHT_SABER = new ItemLightSaber("green");
	
	public static final ItemMagmaticBlade MAGMATIC_BLADE = new ItemMagmaticBlade();
	
	public static void init() {}
}
