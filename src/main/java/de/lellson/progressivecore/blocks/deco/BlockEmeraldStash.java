package de.lellson.progressivecore.blocks.deco;

import java.util.Random;

import javax.annotation.Nullable;

import de.lellson.progressivecore.blocks.BlockPro;
import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.blocks.BlockPro.Tool;
import de.lellson.progressivecore.items.ProItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEmeraldStash extends BlockPro {

	public BlockEmeraldStash() {
		super("stashemerald", Material.ROCK);
		
		hardness(1f + ProBlocks.STASH_GEM.extraHardness);
		harvestLevel(Tool.PICKAXE, ProBlocks.STASH_GEM.harvestLevel);
		sound(SoundType.STONE);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.EMERALD;
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		return ProBlocks.STASH_GEM.xp;
	}
	
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return true;
	}
	
	@Override
    public int quantityDroppedWithBonus(int fortune, Random rand) {
		return fortune > 0 ? this.quantityDropped(rand) + rand.nextInt(fortune+1) : this.quantityDropped(rand);
    }
	
	@Override
	public int quantityDropped(Random rand) {
		return 1 + rand.nextInt(2);
	}

	@Override
	public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && BlockGemStash.canStashStay(world, pos);
    }
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, world, pos, blockIn, fromPos);
		
		if (!BlockGemStash.canStashStay(world, pos))
        {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BlockGemStash.AABB;
    }

	@Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

	@Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

	@Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
