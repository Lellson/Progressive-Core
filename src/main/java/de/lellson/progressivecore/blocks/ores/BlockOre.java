package de.lellson.progressivecore.blocks.ores;

import java.util.Random;

import de.lellson.progressivecore.blocks.BlockPro;
import de.lellson.progressivecore.items.IItemPro;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.sets.Sets.Tier;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOre extends BlockPro {

	private final Item drop;
	private final int minDrop;
	private final int maxDrop;
	private final int xp;
	
	public BlockOre(String name, Item drop, int minDrop, int maxDrop, float extraBlockHardness, int requiredHarvestLevel, int xp) {
		super("ore_" + name, Material.ROCK);
		
		this.drop = drop;
		this.minDrop = minDrop;
		this.maxDrop = maxDrop;
		this.xp = xp;
		
		hardness(3f + extraBlockHardness);
		harvestLevel(Tool.PICKAXE, requiredHarvestLevel);
		sound(SoundType.STONE);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return drop == null ? super.getItemDropped(state, rand, fortune) : new ItemStack(drop, 1, getMetaFromState(state)).getItem();
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		return drop == null ? super.getExpDrop(state, world, pos, fortune) : xp;
	}
	
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return drop != null;
	}
	
	@Override
    public int quantityDroppedWithBonus(int fortune, Random rand) {
		return drop != null && fortune > 0 ? this.quantityDropped(rand) * Math.max(1, rand.nextInt(fortune+1)) : this.quantityDropped(rand);
    }
	
	@Override
	public int quantityDropped(Random rand) {
		return minDrop + rand.nextInt(maxDrop-minDrop+1);
	}
}
