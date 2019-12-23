package de.lellson.progressivecore.blocks.misc;

import java.util.Random;

import de.lellson.progressivecore.blocks.BlockPro;
import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.blocks.BlockPro.Tool;
import de.lellson.progressivecore.inv.tile.TileEntityGemPolisher;
import de.lellson.progressivecore.inv.tile.TileEntityRealityEye;
import de.lellson.progressivecore.misc.ITab;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRealityEye extends BlockPro implements ITileEntityProvider, ITab {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.875D, 0.875D);
	private final boolean activated;
	
	public BlockRealityEye(boolean activated) {
		super("reality_eye" + (activated ? "_on" : ""), Material.GLASS);
		this.activated = activated;
		
		if (activated)
			setBlockUnbreakable();
		
		hardness(10f);
		lightLevel(15);
		setLightOpacity(0);
		harvestLevel(Tool.PICKAXE, 2);
		this.hasTileEntity = true;
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
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    	return Item.getItemFromBlock(ProBlocks.REALITY_EYE);
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
    	world.updateComparatorOutputLevel(pos, this);
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

        if (tile instanceof TileEntityRealityEye)
        {
        	return ((TileEntityRealityEye)tile).isActive() ? 15 : 0;
        }
        
        return 0;
    }
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		TileEntity tile = world.getTileEntity(pos);
		
		if (tile instanceof TileEntityRealityEye)
		{
			ClientHelper.playSound(player, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT);
			boolean active = ((TileEntityRealityEye)tile).switchActive();
	        
	        BlockRealityEye block = active ? ProBlocks.REALITY_EYE_ON : ProBlocks.REALITY_EYE;
	        world.setBlockState(pos, block.getDefaultState(), 3);
	    	world.setBlockState(pos, block.getDefaultState(), 3);

        	tile.validate();
            world.setTileEntity(pos, tile);
		}
		
		return true;
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		
		if (!activated)
			return;
		
		double x = (double)pos.getX() + 0.5D;
        double y = (double)pos.getY() + 0.5D;
        double z = (double)pos.getZ() + 0.5D;
        
        ClientHelper.spawnParticle(world, x, y, z, EnumParticleTypes.SMOKE_NORMAL, 3);
        ClientHelper.spawnParticle(world, x, y, z, EnumParticleTypes.CRIT_MAGIC, 0.3f, 3);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityRealityEye();
	}
	
	@Override
	public boolean shouldShow() {
		return !activated;
	}
}
