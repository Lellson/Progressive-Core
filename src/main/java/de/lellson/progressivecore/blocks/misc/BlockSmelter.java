package de.lellson.progressivecore.blocks.misc;

import java.util.Random;

import de.lellson.progressivecore.blocks.BlockPro.Tool;
import de.lellson.progressivecore.ProgressiveCore;
import de.lellson.progressivecore.blocks.IBlockPro;
import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.inv.GuiHandler.Gui;
import de.lellson.progressivecore.inv.tile.TileEntitySmelter;
import de.lellson.progressivecore.items.ItemBlockMetaPro;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.ITab;
import de.lellson.progressivecore.misc.ProModelHandler;
import de.lellson.progressivecore.misc.ProRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSmelter extends BlockContainer implements IBlockPro, ITab {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static boolean keepInventory;
    private static final String NAME = "smelter";
    
    private final int level;
    private final boolean isBurning;
    private ItemBlock item = null;

    public BlockSmelter(int level, boolean isBurning) {
        super(level == 0 ? Material.ROCK : Material.IRON);
        this.level = level;
        this.isBurning = isBurning;
        setUnlocalizedName(NAME + (level+1) + (isBurning ? "_on" : ""));
        setHardness(4);
        setHarvestLevel(Tool.PICKAXE.toString(), level+1);
        setSoundType(level == 0 ? SoundType.STONE : SoundType.METAL);
        if (isBurning)
        	setLightLevel(13);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }
    
    public int getLevel() {
		return level;
	}
    
    @Override
	public Block setUnlocalizedName(String name) {
		super.setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Constants.prefix(name)));
		ProRegistry.register(this);
		ProRegistry.register(toItemBlock());
		ProModelHandler.register(this);
		return this;
	}
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(getSmelter(level, false));
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        
    	if (worldIn.isRemote)
        	return;
    	
        IBlockState iblockstate = worldIn.getBlockState(pos.north());
        IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
        IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
        IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

        if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
        {
            enumfacing = EnumFacing.SOUTH;
        }
        else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
        {
            enumfacing = EnumFacing.NORTH;
        }
        else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
        {
            enumfacing = EnumFacing.EAST;
        }
        else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
        {
            enumfacing = EnumFacing.WEST;
        }

        worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    	
        if (!this.isBurning)
        	return;
        
        EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.5D + rand.nextDouble() * 6.0D / 16.0D;
        double d2 = (double)pos.getZ() + 0.5D;
        double d3 = 0.52D;
        double d4 = rand.nextDouble() * 0.6D - 0.3D;

        if (rand.nextDouble() < 0.1D)
        {
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }

        switch (enumfacing)
        {
            case WEST:
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                break;
            case EAST:
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                break;
            case NORTH:
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                break;
            case SOUTH:
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
            return true;
        
        TileEntity tileentity = world.getTileEntity(pos);

        if (tileentity instanceof TileEntitySmelter)
            playerIn.openGui(ProgressiveCore.instance, Gui.SMELTER.getId(), world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    public static void setState(int level, boolean active, World world, BlockPos pos) {
    	
        IBlockState iblockstate = world.getBlockState(pos);
        TileEntity tileentity = world.getTileEntity(pos);
        keepInventory = true;
        
        BlockSmelter smelter = getSmelter(level, active);
        
        world.setBlockState(pos, smelter.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
    	world.setBlockState(pos, smelter.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);

        keepInventory = false;

        if (tileentity != null)
        {
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        switch(level)
        {
        	case 1: return new TileEntitySmelter.TileEntitySmelterAdvanced();
        	case 2: return new TileEntitySmelter.TileEntitySmelterInfernal();
        	case 3: return new TileEntitySmelter.TileEntitySmelterTitan();
        	default: return new TileEntitySmelter.TileEntitySmelterBasic();
        }
    }
    
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntitySmelter)
            {
                ((TileEntitySmelter)tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!keepInventory)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntitySmelter)
            {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntitySmelter)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }
    
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(getSmelter(level, false));
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
            enumfacing = EnumFacing.NORTH;

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

	@Override
	public ItemBlock toItemBlock() {
		return item != null ? item : (item = (ItemBlock) new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	public static BlockSmelter getSmelter(int level, boolean active) {
		
		switch(level)
		{
			case 1: return active ? ProBlocks.SMELTER2_ON : ProBlocks.SMELTER2;
			case 2: return active ? ProBlocks.SMELTER3_ON : ProBlocks.SMELTER3;
			case 3: return active ? ProBlocks.SMELTER4_ON : ProBlocks.SMELTER4;
			default: return active ? ProBlocks.SMELTER1_ON : ProBlocks.SMELTER1;
		}
	}

	@Override
	public Tab getTab() {
		return Tab.BLOCKS;
	}
	
	@Override
	public boolean shouldShow() {
		return !isBurning;
	}
}
