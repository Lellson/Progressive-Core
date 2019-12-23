package de.lellson.progressivecore.misc;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import de.lellson.progressivecore.blocks.BlockPro;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ProRegistry {

	private static final Multimap<Class<?>, IForgeRegistryEntry<?>> ENTRIES = MultimapBuilder.hashKeys().arrayListValues().build();
	
	public static void register(Item entry) {
		ForgeRegistries.ITEMS.register(entry);
		ENTRIES.put(entry.getClass(), entry);
	}
	
	public static void register(Block entry) {
		ForgeRegistries.BLOCKS.register(entry);
		ENTRIES.put(entry.getClass(), entry);
	}
	
	public static void register(Potion entry) {
		ForgeRegistries.POTIONS.register(entry);
		ENTRIES.put(entry.getClass(), entry);
	}
	
	public static Multimap<Class<?>, IForgeRegistryEntry<?>> getEntries() {
		return ENTRIES;
	}
	
	@SubscribeEvent
	public static void onRegistry(RegistryEvent.Register event) {
		
		/*
		Class<?> type = event.getRegistry().getRegistrySuperType();
		
		if(entries.containsKey(type)) 
		{
			Collection<IForgeRegistryEntry<?>> curEntries = entries.get(type);
			
			for(IForgeRegistryEntry<?> entry : curEntries)
			{
				event.getRegistry().register(entry);
			}
		}
		*/
	}
}
