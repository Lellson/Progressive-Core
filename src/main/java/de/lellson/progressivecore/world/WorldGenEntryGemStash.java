package de.lellson.progressivecore.world;

import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.blocks.deco.BlockGemStash;
import de.lellson.progressivecore.blocks.ores.BlockOreGem;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.ForgeModContainer;

public class WorldGenEntryGemStash extends WorldGenEntry {

	public WorldGenEntryGemStash(String name, Block block, GenType type, int minY, int maxY, int spawnChance) {
		super(name, block, type, minY, maxY, 0, 0, spawnChance);
	}
	
	@Override
	public void generate(World world, int chunkX, int chunkZ) {
		
		BlockPos pos = new BlockPos(chunkX * CHUNK_SIZE, 0, chunkZ * CHUNK_SIZE);
		int chance = getChance();
		int count = chance/2 + RND.nextInt(chance);
        for (int i = 0; i < count; i++)
        {
            int offset = ForgeModContainer.fixVanillaCascading ? 8 : 0;
            BlockPos blockpos = pos.add(RND.nextInt(16) + offset, RND.nextInt(maxY-minY) + minY, RND.nextInt(16) + offset);

            IBlockState state = world.getBlockState(blockpos);
            if (world.isAirBlock(blockpos) && BlockGemStash.canStashStay(world, blockpos))
            {
            	world.setBlockState(blockpos, getState(world, chunkX, chunkZ), 16 | 2);
            }
        }
	}
	
	@Override
	protected IBlockState getState(World world, int chunkX, int chunkZ) {
		
		if (getBiome(world, chunkX, chunkZ) instanceof BiomeHills)
			return ProBlocks.STASH_EMERALD.getDefaultState();
		
		return ((BlockGemStash)block).getStateFromMeta(BlockOreGem.getMetaForBiome(getBiome(world, chunkX, chunkZ)));
	}
}
