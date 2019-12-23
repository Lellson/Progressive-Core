package de.lellson.progressivecore.inv.recipe;

import java.util.ArrayList;
import java.util.List;

import de.lellson.progressivecore.misc.RangeStack;
import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;

public class PolisherEntry {
	
	public static final List<PolisherEntry> ENTRIES = new ArrayList<PolisherEntry>();
	
	private final RangeStack input;
	private final RangeStack output;
	
	public PolisherEntry(RangeStack input, RangeStack output) {
		this.input = input;
		this.output = output;
	}
	
	public RangeStack getInput() {
		return input;
	}
	
	public RangeStack getOutput() {
		return output;
	}
	
	public static void initRecipes() {
		
		String[] entries = ProConfig.getPolisherEntries();
		for (String line : entries) 
		{
			String error = null;
			
			if (line.isEmpty())
				continue;
			
			String[] split = line.replace(" ", "").split(";");
			if (split.length >= 2) 
			{
				RangeStack output = RangeStack.getRangeStack(split[0]);
				
				if (output == null)
					error = "Output \"" + split[0] + "\" ist not valid Item/Block!";
				
				RangeStack input = RangeStack.getRangeStack(split[1]);
				
				if (input == null)
					error = "input \"" + split[1] + "\" ist not valid Item/Block!";
				
				if (error == null)
					ENTRIES.add(new PolisherEntry(input, output));
			}
			else
				error = "Invalid amount of arguments (" + split.length + ")";
			
			if (error != null)
				FMLLog.bigWarning("ProgressiveCore: Error on adding polisher recipe: " + error);
		}
	}
	
	public static ItemStack getOutput(ItemStack input) {
		
		for (PolisherEntry entry : ENTRIES)
		{
			boolean isInput = false;
			
			for (ItemStack stack : entry.getInput().getItemStackNoRnd())
			{
				if (stack.isItemEqual(input))
				{
					isInput = true;
					break;
				}
			}
			
			if (isInput)
				return entry.getOutput().getItemStack();
		}
		
		return null;
	}
}
