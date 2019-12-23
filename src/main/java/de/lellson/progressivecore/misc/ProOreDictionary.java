package de.lellson.progressivecore.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.lellson.progressivecore.blocks.deco.BlockGem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ProOreDictionary {
	
	private static final List<OreEntry> ENTRIES = new ArrayList<OreEntry>();
	
	public static void init() {
		
		for (OreEntry entry : ENTRIES)
		{
			ItemStack stack = entry.getStack();
			if (stack == null)
				continue;
			
			OreDictionary.registerOre(entry.getName(), stack);
		}
	}
	
	public static void registerOre(String name, Item item, int meta) {
		ENTRIES.add(new OreEntry(name, item, meta));
	}

	public static void registerOre(String name, Block block, int meta) {
		ENTRIES.add(new OreEntry(name, block, meta));
	}
	
	public static class OreEntry {
		
		private final String name;
		private final Object obj;
		private final int meta;
		
		public OreEntry(String name, Object obj, int meta) {
			this.name = name;
			this.obj = obj;
			this.meta = meta;
		}
		
		public String getName() {
			return name;
		}
		
		private ItemStack getStack() {
			return obj instanceof Item ? new ItemStack((Item)obj, 1, meta) : obj instanceof Block ? new ItemStack((Block)obj, 1, meta) : null;
		}
	}
}
