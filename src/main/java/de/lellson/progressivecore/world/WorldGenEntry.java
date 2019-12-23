package de.lellson.progressivecore.world;

import java.util.Random;

import com.google.common.base.Predicate;

import de.lellson.progressivecore.blocks.ores.BlockOreGem;
import de.lellson.progressivecore.misc.config.ProConfig;
import de.lellson.progressivecore.misc.helper.MiscHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.TempCategory;
import net.minecraft.world.gen.feature.WorldGenMinable;
import scala.collection.generic.GenericClassTagTraversableTemplate;

public class WorldGenEntry {
	
	protected static final Random RND = new Random();
	protected static final int CHUNK_SIZE = 16;
	
	protected final Block block;
	protected final GenType type;
	protected final int minY;
	protected final int maxY;
	protected final int minVein;
	protected final int maxVein;
	protected final float spawnChance;
	protected final int[] allowedDimensions;
	
	public WorldGenEntry(String name, Block block, GenType type, int minY, int maxY, int minVein, int maxVein, float spawnChance) {
		
		String category = "ore" + MiscHelper.upperFirst(name);
		ProConfig.cfg.addCustomCategoryComment(category, MiscHelper.upperFirst(name) + " ore and block settings");
		
		this.block = block;
		this.type = type;
		this.minY = ProConfig.cfg.getInt(name + "MinY", category, minY, 0, 255, "Determines the minimum height " + name + " ore is allowed spawn.");
		this.maxY = ProConfig.cfg.getInt(name + "MaxY", category, maxY, 0, 255, "Determines the maximum height " + name + " ore is allowed spawn.");
		this.minVein = minVein == 0 ? 1 : ProConfig.cfg.getInt(name + "MinVein", category, minVein, 1, Short.MAX_VALUE, "Determines the minimum vein size " + name + " ores have.");
		this.maxVein = maxVein == 0 ? 1 : ProConfig.cfg.getInt(name + "MaxVein", category, maxVein, 1, Short.MAX_VALUE, "Determines the maximum vein size " + name + " ores have.");
		this.spawnChance = ProConfig.cfg.getFloat(name + "SpawnChance", category, spawnChance, 0, Short.MAX_VALUE, "Determines the chance that a vein of " + name + " ores spawns.\n0 prevents " + name + " ores from spawning.");
		this.allowedDimensions = MiscHelper.toIntArray(ProConfig.cfg.getStringList(name + "AllowedDimensions", category, new String[] {String.valueOf(type.getDimensionId())}, 
				"Determines the dimensions (ids) in which " + name + " ores are allowed to spawn. Each dimension id needs its own line."), Short.MAX_VALUE);
	}
	
	public void generate(World world, int chunkX, int chunkZ) {
		
		if (!MiscHelper.isInArray(world.provider.getDimension(), this.allowedDimensions))
			return;
		
		int posX, posY, posZ, vein;
		
		int chance = getChance();
		for (int i = 0; i < chance; i++)
		{
			posX = chunkX * CHUNK_SIZE + RND.nextInt(CHUNK_SIZE);
			posY = minY + RND.nextInt(maxY - minY + 1);
			posZ = chunkZ * CHUNK_SIZE + RND.nextInt(CHUNK_SIZE);
			vein = minVein + RND.nextInt(maxVein - minVein + 1);
			
			new WorldGenMinable(getState(world, chunkX, chunkZ), vein, new Predicate<IBlockState>() {
				@Override
				public boolean apply(IBlockState input) {
					return input.getBlock() == type.getReplaceBlock();
				}
			}).generate(world, RND, new BlockPos(posX, posY, posZ));
		}
	}
	
	protected int getChance() {
		return (int)Math.floor(spawnChance) + (RND.nextFloat() < (spawnChance - Math.floor(spawnChance)) ? 1 : 0);
	}

	protected IBlockState getState(World world, int chunkX, int chunkZ) {
		return block.getDefaultState();
	}
	
	protected Biome getBiome(World world, int chunkX, int chunkZ) {
		return world.getBiome(new BlockPos(chunkX * CHUNK_SIZE, 64, chunkZ * CHUNK_SIZE));
	}

	public static enum GenType {
		
		OVERWORLD(0, Blocks.STONE), 
		NETHER(-1, Blocks.NETHERRACK), 
		END(1, Blocks.END_STONE);
		
		private final int dimensionId;
		private final Block replaceBlock;
		
		private GenType(int dimensionId, Block replaceBlock) {
			this.dimensionId = dimensionId;
			this.replaceBlock = replaceBlock;
		}
		
		public int getDimensionId() {
			return dimensionId;
		}
		
		public Block getReplaceBlock() {
			return replaceBlock;
		}
	}
}
