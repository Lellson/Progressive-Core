package de.lellson.progressivecore.inv.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import de.lellson.progressivecore.inv.tile.TileEntitySmelter;
import de.lellson.progressivecore.misc.RangeStack;
import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.oredict.OreDictionary;

public class SmelterEntry {
	
	public static final List<SmelterEntry> ENTRIES = new ArrayList<SmelterEntry>();
	
	public static List<SmelterEntry> getEntries(int level) {
		
		List<SmelterEntry> entries = new ArrayList<SmelterEntry>();
		
		for (SmelterEntry entry : ENTRIES)
			if (entry.getRequiredLevel() == level)
				entries.add(entry);
		
		return entries;
	}
	
	public static void initRecipes() 
	{
		String[][] recipes = ProConfig.getSmelterEntries();
		for (int i = recipes.length-1; i >= 0; i--)
		{
			String[] recipeSet = recipes[i];
			for (String recipe : recipeSet)
			{
				if (recipe.isEmpty())
					continue;
				
				SmelterEntry entry = getEntry(recipe, i);
				if (entry != null)
					ENTRIES.add(entry);
			}
		}
	}
	
	private static SmelterEntry getEntry(String recipe, int level) {
		
		String error = "";
		String[] parts = recipe.trim().replace(" ", "").split(";");
		
		if (parts.length >= 4 && parts.length <= 9)
		{
			int intIndex = 0;
			int duration;
			float xp;
			try 
			{
				duration = Integer.parseInt(parts[0]);
				intIndex = 1;
				xp = Float.parseFloat(parts[1]);
				
				RangeStack output = RangeStack.getRangeStack(parts[2]);
				if (output == null)
				{
					error = parts[2] + " is not a valid item or ore dictionary entry";
				}
				else 
				{
					RangeStack[] input = new RangeStack[parts.length-3];
					boolean errored = false;
					
					for (int i = 3; i < parts.length; i++)
					{
						RangeStack stack = RangeStack.getRangeStack(parts[i]);
						if (stack == null)
						{
							error = parts[i] + " is not a valid item or ore dictionary entry";
							errored = true;
							break;
						}
						else
							input[i-3] = stack;
					}
					
					if (!errored)
					{
						return new SmelterEntry(level, duration, xp, output, input);
					}
				}
			}
			catch(NumberFormatException e)
			{
				error = parts[intIndex] + " is not a valid number!";
			}
		}
		else
			error = "Invalid amount of arguments (" + parts.length + ") for recipe: " + recipe;
			
		FMLLog.bigWarning("ProgressiveCore: Error on adding smelter recipe: " + error);
		return null;
	}

	

	public static ItemStack getOutput(TileEntitySmelter smelter, List<ItemStack> items, boolean respectAmount) {
		
		for (SmelterEntry entry : ENTRIES)
		{
			if (entry.isInput(smelter, items))
				return respectAmount ? entry.output.getItemStack() : entry.output.getItemStackNoRnd().get(0);
		}
		
		return ItemStack.EMPTY;
	}
	
	public static int getCookTime(TileEntitySmelter smelter, List<ItemStack> items) {
		
		for (SmelterEntry entry : ENTRIES)
		{
			if (entry.isInput(smelter, items))
				return (int)(entry.cookTime * (1 - smelter.getLevel()*0.25));
		}
		
		return 0;
	}
	
	public static float getXP(ItemStack stack) {
		
		for (SmelterEntry entry : ENTRIES)
		{
			if (entry.output.getItemStackNoRnd().get(0).isItemEqual(stack))
				return entry.xp;
		}
		
		return 0;
	}
	
	public static boolean hasOutput(TileEntitySmelter smelter, List<ItemStack> items) {
		return getOutput(smelter, items, false) != ItemStack.EMPTY;
	}
	
	private final int requiredLevel;
	private final int cookTime;
	private final float xp;
	private final RangeStack output;
	private final List<RangeStack> input = new ArrayList<RangeStack>();
	
	public SmelterEntry(int requiredLevel, int cookTime, float xp, RangeStack output, RangeStack... input) {
		
		this.requiredLevel = requiredLevel;
		this.cookTime = cookTime;
		this.xp = xp;
		this.output = output;
		
		for (RangeStack stack : input)
			this.input.add(stack);
	}
	
	public int getRequiredLevel() {
		return requiredLevel;
	}
	
	public List<RangeStack> getInput() {
		return input;
	}
	
	public int getCookTime() {
		return cookTime;
	}
	
	public float getXp() {
		return xp;
	}
	
	public RangeStack getOutput() {
		return output;
	}
	
	public boolean isInput(TileEntitySmelter smelter, List<ItemStack> list) {
		
		if (input.size() != list.size() || smelter.getLevel() < requiredLevel)
			return false;
		
		List<ItemStack> items = new ArrayList<ItemStack>(list);
		
		boolean[] finished = new boolean[input.size()];
		for (int i = 0; i < input.size(); i++)
			finished[i] = false;
		
		
		for (int i = 0; i < input.size(); i++) 
		{
			RangeStack ingredient = input.get(i);
			
			Iterator<ItemStack> it = items.listIterator();
			while(it.hasNext())
			{
				ItemStack stack = it.next();
				for (ItemStack possibleIngredient : ingredient.getStacks())
				{
					if (stack.isItemEqual(possibleIngredient) || (stack.getItem() == possibleIngredient.getItem() && possibleIngredient.getItemDamage() == OreDictionary.WILDCARD_VALUE))
					{
						finished[i] = true;
						it.remove();
						break;
					}
				}
				if (finished[i])
					break;
			}
		}
		
		for (boolean finish : finished)
			if (!finish)
				return false;
		
		return true; 
	}
	
	class StackComparator implements Comparator<ItemStack> {

		@Override
		public int compare(ItemStack o1, ItemStack o2) {
			
			String reg1 = o1.getItem().getRegistryName().toString();
			String reg2 = o2.getItem().getRegistryName().toString();
			
			return reg1.compareTo(reg2);
		}
	}
}
