package de.lellson.progressivecore.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.integration.ProIntegration;
import de.lellson.progressivecore.integration.ProIntegration.Mod;
import de.lellson.progressivecore.integration.baubles.ProBaubles.AccessoryVariant;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.misc.ITab.Tab;
import de.lellson.progressivecore.sets.Sets;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class ProTab extends CreativeTabs {

	public static final ProTab TAB_MATERIALS = new ProTab(Tab.MATERIALS)
	{
		@Override
		protected Item getItem() {
			return ProItems.GEM;
		}
		
		@Override
		protected int getMeta() {
			
			int meta = Constants.random(ProItems.GEM.getVariants().length);
			
			while (icon != null && icon.getItemDamage() == meta)
				meta = Constants.random(ProItems.GEM.getVariants().length);
			
			return meta;
		}
	};
	public static final ProTab TAB_BLOCKS = new ProTab(Tab.BLOCKS)
	{
		@Override
		protected Item getItem() {
			return ProBlocks.BLOCK_GEM.toItemBlock();
		}
		
		@Override
		protected int getMeta() {
			
			int meta = Constants.random(ProBlocks.BLOCK_GEM.getVariants().length);
			
			while (icon != null && icon.getItemDamage() == meta)
				meta = Constants.random(ProBlocks.BLOCK_GEM.getVariants().length);
			
			return meta;
		}
	};
	public static final ProTab TAB_COMBAT = new ProTab(Tab.COMBAT)
	{
		private int lastIndex = -1;
		
		@Override
		protected Item getItem() {
			
			int index = Constants.random(Sets.getSets().size());
			
			while (lastIndex == index || !Sets.getSets().get(index).hasArmor() || Sets.getSets().get(index).getArmorSet().getChestplate() == null)
				index = Constants.random(Sets.getSets().size());
			
			lastIndex = index;
			return Sets.getSets().get(index).getArmorSet().getChestplate();
		}
	};
	public static final ProTab TAB_TOOLS = new ProTab(Tab.TOOLS)
	{
		private int lastIndex = -1;
		
		@Override
		protected Item getItem() {
			
			int index = Constants.random(Sets.getSets().size());
			
			while (lastIndex == index || !Sets.getSets().get(index).hasTools() || Sets.getSets().get(index).getToolSet().getPickaxe() == null)
				index = Constants.random(Sets.getSets().size());
			
			lastIndex = index;
			return Sets.getSets().get(index).getToolSet().getPickaxe();
		}
	};
	public static ProTab tab_accessories = null;
	
	public static void init() {
		
		if (Mod.BAUBLES.isLoaded())
			tab_accessories = new ProTab(Tab.ACCESSORIES)
			{
				@Override
				protected Item getItem() {
					return ProIntegration.getAccessoryIconItem();
				}
				
				@Override
				protected int getMeta() {
					
					int meta = Constants.random(AccessoryVariant.variants().length);
					
					while (icon != null && icon.getItemDamage() == meta)
						meta = Constants.random(AccessoryVariant.variants().length);
					
					return meta;
				}
			};
	}
	
	private final Tab tab;
	private int tick = 0;
	protected ItemStack icon = getTabIconItem();
	
	public ProTab(Tab tab) {
		super("tabProgressiveCore");
		this.tab = tab;
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(getItem(), 1, getMeta());
	}
	
	protected abstract Item getItem();

	protected int getMeta() {
		return 0;
	}

	@Override
	public ItemStack getIconItemStack() {
		return icon;
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> items) {
		
		List<IForgeRegistryEntry<?>> entries = new ArrayList<IForgeRegistryEntry<?>>(ProRegistry.getEntries().values());
		entries.sort(new RegistryCompare());
		
		for (IForgeRegistryEntry<?> entry : entries)
			if (entry instanceof ITab && ((ITab)entry).getTab() == tab && ((ITab)entry).shouldShow())
				items.addAll(getSubStacks(entry));
	}
	
	private static Collection<? extends ItemStack> getSubStacks(IForgeRegistryEntry<?> entry) {
		
		NonNullList<ItemStack> stacks = NonNullList.create();
		
		if (entry instanceof Item)
		{
			((Item)entry).getSubItems(null, stacks);
			
			if (stacks.isEmpty())
				stacks.add(new ItemStack((Item)entry));
		}
		else if (entry instanceof Block)
		{
			((Block)entry).getSubBlocks(null, stacks);
			
			if (stacks.isEmpty())
				stacks.add(new ItemStack((Block)entry));
		}
		
		return stacks;
	}

	private void onTick() {

		tick++;
		if (tick > 20) 
		{
			icon = getTabIconItem();
			tick = 0;
		}
	}
	
	@SubscribeEvent
	public static void tickEvent(TickEvent.ClientTickEvent event) {
		
		if (event.phase == Phase.START)
			return;

		TAB_BLOCKS.onTick();
		TAB_MATERIALS.onTick();
		TAB_COMBAT.onTick();
		TAB_TOOLS.onTick();
		if (tab_accessories != null)
			tab_accessories.onTick();
	}
	
	public class RegistryCompare implements Comparator<IForgeRegistryEntry<?>> {
		@Override
		public int compare(IForgeRegistryEntry<?> o1, IForgeRegistryEntry<?> o2) {
			return o1.getRegistryName().compareTo(o2.getRegistryName());
		}
	}
}
