package de.lellson.progressivecore.misc;

import java.util.ArrayList;
import java.util.List;

import de.lellson.progressivecore.blocks.BlockPro;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.sets.Set;
import de.lellson.progressivecore.sets.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProRecipes {

	private static final List<FurnaceEntry> ENTRIES = new ArrayList<FurnaceEntry>();
	
	public static void init() {
		
		for (int i = 0; i < ProItems.GEMS.length; i++)
			addSmelting(new ItemStack(ProItems.DUST, 1, i), new ItemStack(ProItems.GEM, 1, i), 0.15f);
		addSmelting(ProItems.EMERALD_DUST, new ItemStack(Items.EMERALD), 0.15f);
		
		for (FurnaceEntry entry : ENTRIES)
			entry.addRecipe();
	}

	public static void addSmelting(Object input, ItemStack output, float smeltXp) {
		ENTRIES.add(new FurnaceEntry(input, output, smeltXp));
	}
	
	public static class FurnaceEntry {
		
		private final Object input;
		private final ItemStack output;
		private final float smeltXp;
		
		public FurnaceEntry(Object input, ItemStack output, float smeltXp) {
			this.input = input;
			this.output = output;
			this.smeltXp = smeltXp;
		}
		
		public void addRecipe() {
			if (input instanceof Block)
				GameRegistry.addSmelting((Block)input, output, smeltXp);
			else if (input instanceof Item)
				GameRegistry.addSmelting((Item)input, output, smeltXp);
			else
				GameRegistry.addSmelting((ItemStack)input, output, smeltXp);
		}
	}
}
