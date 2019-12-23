package de.lellson.progressivecore.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.sets.Set;
import de.lellson.progressivecore.sets.Sets;
import de.lellson.progressivecore.world.WorldGenEntry.GenType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProWorldGenerator implements IWorldGenerator {

	public static final List<WorldGenEntry> ENTRIES = new ArrayList<WorldGenEntry>();
	
	//SIMPLE RETRO GEN: de.lellson.progressivecore.world.ProWorldGenerator
	
	public static void init() {
		
		ENTRIES.add(new WorldGenEntry(Sets.COPPER, Sets.COPPER_SET.getBlockOre(), GenType.OVERWORLD, 45, 96, 4, 9, 14));
		ENTRIES.add(new WorldGenEntry(Sets.TIN, Sets.TIN_SET.getBlockOre(), GenType.OVERWORLD, 30, 63, 4, 9, 8));
		ENTRIES.add(new WorldGenEntry(Sets.TUNGSTEN, Sets.TUNGSTEN_SET.getBlockOre(), GenType.OVERWORLD, 0, 30, 4, 8, 4));
		
		ENTRIES.add(new WorldGenEntry(Sets.NETHERITE, Sets.NETHERITE_SET.getBlockOre(), GenType.NETHER, 7, 117, 2, 6, 4));
		ENTRIES.add(new WorldGenEntry(Sets.LUMINIUM, Sets.LUMINIUM_SET.getBlockOre(), GenType.NETHER, 7, 117, 6, 14, 9));
		
		ENTRIES.add(new WorldGenEntry(Sets.TITANIUM, Sets.TITANIUM_SET_ORE.getBlockOre(), GenType.OVERWORLD, 0, 16, 4, 9, 1));
		ENTRIES.add(new WorldGenEntry(Sets.TITANIUM + "Nether", Sets.TITANIUM_SET_ORE.getBlockOreNether(), GenType.NETHER, 7, 117, 1, 4, 3));
		
		ENTRIES.add(new WorldGenEntry(Sets.MITHRIL, Sets.MITHRIL_SET_ORE.getBlockOre(), GenType.OVERWORLD, 0, 24, 12, 16, 0.05f));
		ENTRIES.add(new WorldGenEntry(Sets.ORICHALCUM, Sets.ORICHALCUM_SET_ORE.getBlockOre(), GenType.OVERWORLD, 0, 24, 12, 16, 0.05f));
		ENTRIES.add(new WorldGenEntry(Sets.ADAMANTITE, Sets.ADAMANTITE_SET_ORE.getBlockOre(), GenType.OVERWORLD, 0, 24, 12, 16, 0.05f));
		
		ENTRIES.add(new WorldGenEntryGem("gem", ProBlocks.ORE_GEM, GenType.OVERWORLD, 4, 32, 6));
		ENTRIES.add(new WorldGenEntryGemStash("gemStash", ProBlocks.STASH_GEM, GenType.OVERWORLD, 4, 32, 15));
		
		GameRegistry.registerWorldGenerator(new ProWorldGenerator(), 1);
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

		for (WorldGenEntry entry : ENTRIES)
		{
			if (entry.spawnChance > 0)
				entry.generate(world, chunkX, chunkZ);
		}
	}
}
