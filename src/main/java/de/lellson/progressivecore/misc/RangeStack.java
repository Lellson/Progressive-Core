package de.lellson.progressivecore.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class RangeStack {
	
	private static final Random RND = new Random();
	
	private final List<Item> items;
	private final int min;
	private final int max;
	private final List<Integer> meta;
	private final List<ItemStack> defaultStack = new ArrayList<ItemStack>();
	
	public RangeStack(List<Item> items, int min, int max, List<Integer> meta) {
		
		this.items = items;
		this.min = min;
		this.max = max;
		this.meta = meta;
		for (int i = 0; i < items.size(); i++)
			defaultStack.add(new ItemStack(items.get(i), max, meta.get(i)));
	}

	public ItemStack getItemStack() {
		return getItemStack(0);
	}
	
	public int getMin() {
		return min;
	}
	
	public int getMax() {
		return max;
	}
	
	public ItemStack getItemStack(int amount)
	{
		return new ItemStack(items.get(0), amount > 0 ? amount : min + (max-min+1 <= 0 ? 0 : RND.nextInt(max-min+1)), meta.get(0));
	}
	
	public List<ItemStack> getItemStackNoRnd() {
		return defaultStack;
	}
	
	public List<ItemStack> getStacksWithCount() {
		
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (int i = Math.max(1, getMin()); i <= Math.max(1, getMax()); i++)
		{
			list.add(getItemStack(i));
		}
		
		return list;
	}

	public List<ItemStack> getStacks(boolean splitWildcard) {
		
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		
		for (int i = 0; i < items.size(); i++)
		{
			if (splitWildcard && meta.get(i) == OreDictionary.WILDCARD_VALUE)
			{
				NonNullList<ItemStack> subItems = NonNullList.<ItemStack>create();
				items.get(i).getSubItems(null, subItems);
				
				for (ItemStack stack : subItems)
					stacks.add(stack);
			}
			else
				stacks.add(new ItemStack(items.get(i), 1, meta.get(i)));
		}
		
		return stacks;
	}
	
	public List<ItemStack> getStacks() {
		return getStacks(false);
	}
	
	public static RangeStack getRangeStack(String s) {
		
		String[] split = s.trim().replace(" ", "").split(",");
		
		List<Item> items = new ArrayList<Item>();
		List<Integer> metas = new ArrayList<Integer>();
		Item item = Item.REGISTRY.getObject(new ResourceLocation(split[0]));
		
		if (item == null)
		{
			if (!OreDictionary.doesOreNameExist(split[0]))
				return null;
			
			for (ItemStack stack : OreDictionary.getOres(split[0]))
			{
				items.add(stack.getItem());
				metas.add(stack.getItemDamage());
			}
		}
		else
			items.add(item);
		
		int min = 1, max = 1;
		if (split.length > 1)
		{
			try 
			{
				String[] minmax = split[1].trim().replace(" ", "").split("-");
				
				if (minmax.length == 1)
				{
					min = max = Integer.parseInt(minmax[0]);
				}
				else
				{
					min = Integer.parseInt(minmax[0]);
					max = Integer.parseInt(minmax[1]);
				}
				
				if (split.length > 2)
					metas.add(Integer.parseInt(split[2]));
				else
					metas.add(0);
			}
			catch(NumberFormatException e)
			{
				return null;
			}
		}
		else
			metas.add(0);
		
		return new RangeStack(items, min, max, metas);
	}
}
