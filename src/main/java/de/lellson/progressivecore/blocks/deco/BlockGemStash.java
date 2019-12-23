package de.lellson.progressivecore.blocks.deco;

import java.util.Random;

import javax.annotation.Nullable;

import de.lellson.progressivecore.blocks.BlockMetaPro;
import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.blocks.BlockPro.Tool;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.misc.ProOreDictionary;
import de.lellson.progressivecore.misc.config.ProConfig;
import de.lellson.progressivecore.misc.helper.MiscHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
import net.minecraftforge.oredict.OreDictionary;

public class BlockGemStash extends BlockMetaPro {
	
	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.3, 0, 0.3, 0.7, 0.3, 0.7);
	
	public float extraHardness;
	public int harvestLevel;
	public int xp;
	
	public BlockGemStash() {
		super("stashgem", Material.ROCK, ProItems.GEMS);
		
		initCfgOptions();
		
		hardness(1f + extraHardness);
		harvestLevel(Tool.PICKAXE, harvestLevel);
		sound(SoundType.STONE);
	}
	
	private void initCfgOptions() {
		
		String name = "stash";
		String category = "oreGemStash";
		
		extraHardness = ProConfig.cfg.getFloat(name + "ExtraBlockHardness", category, 0, -1F, Short.MAX_VALUE, 
				"Determines the additional hardness gem " + name + " blocks (+1) have.");
		harvestLevel = ProConfig.cfg.getInt(name + "RequiredHarvestLevel", category, 2, 0, Short.MAX_VALUE, 
				"Determines the required harvest level to mine gem " + name + " blocks.");
		xp = ProConfig.cfg.getInt(name + "DropXp", category, 7, 0, Short.MAX_VALUE,
				"Determines the amount of XP dropped when a gem " + name + " block is mined.");
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return new ItemStack(ProItems.GEM, 1, getMetaFromState(state)).getItem();
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		return xp;
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
        return super.canPlaceBlockAt(world, pos) && canStashStay(world, pos);
    }
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, world, pos, blockIn, fromPos);
		
		if (!canStashStay(world, pos))
        {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
	}

	public static boolean canStashStay(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos.down());
		return state.getMaterial() == Material.ROCK;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
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
