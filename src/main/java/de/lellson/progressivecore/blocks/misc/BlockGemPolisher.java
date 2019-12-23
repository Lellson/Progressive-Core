package de.lellson.progressivecore.blocks.misc;

import java.util.Random;

import de.lellson.progressivecore.blocks.BlockPro;
import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.blocks.BlockPro.Tool;
import de.lellson.progressivecore.inv.tile.TileEntityGemPolisher;
import de.lellson.progressivecore.misc.ITab;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGemPolisher extends BlockPro implements ITileEntityProvider, ITab {
	
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1D, 0.75D, 1D);
	
	public BlockGemPolisher() {
		super("gem_polisher", Material.IRON);
		
		hardness(10f);
		setLightOpacity(0);
		harvestLevel(Tool.PICKAXE, 2);
		this.hasTileEntity = true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGemPolisher();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
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
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
    
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
    	TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntityGemPolisher)
        {
        	((TileEntityGemPolisher)tile).dropInput(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, EnumFacing.UP);
            world.updateComparatorOutputLevel(pos, this);
        }
    	
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }
    
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos)
    {
    	TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntityGemPolisher)
        {
        	return ((TileEntityGemPolisher)tile).getInventory().getStackInSlot(0).isEmpty() ? 0 : 15;
        }
        
        return 0;
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	
    	TileEntity tile = world.getTileEntity(pos);
    	
    	if (tile instanceof TileEntityGemPolisher)
    	{
    		return ((TileEntityGemPolisher)tile).handleRightclick(world, pos, state, player, hand, facing, new Vec3d(pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ));
    	}
    	
    	return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }
	
	@Override
	public Tab getTab() {
		return Tab.BLOCKS;
	}
}
