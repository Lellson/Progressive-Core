package de.lellson.progressivecore.blocks;

import de.lellson.progressivecore.ProgressiveCore;
import de.lellson.progressivecore.blocks.BlockPro.Tool;
import de.lellson.progressivecore.items.ItemBlockMetaPro;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.ITab;
import de.lellson.progressivecore.misc.ProModelHandler;
import de.lellson.progressivecore.misc.ProRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockMetaPro extends Block implements IBlockPro, ITab {
	
	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 15);
	
	private ItemBlockMetaPro item = null;
	private String name;
	private String[] variants;
	
	public BlockMetaPro(String name, Material material, String... variants) {
		super(material);

		this.name = name;
		this.variants = variants;
		
		setUnlocalizedName(name);
		
		applyDefaultStats();
	}
	
	public String[] getVariants() {
		return variants;
	}
	
	public String getName() {
		return name;
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
	
	public BlockMetaPro applyDefaultStats() {
		return hardness(1);
	}
	
	public BlockMetaPro hardness(float hardness) {
		setHardness(hardness);
		return this;
	}
	
	public BlockMetaPro sound(SoundType soundType) {
		setSoundType(soundType);
		return this;
	}
	
	public BlockMetaPro harvestLevel(Tool tool, int level) {
		setHarvestLevel(tool.toString(), level);
		return this;
	}
	
	public BlockMetaPro lightLevel(float lightlevel) {
		setLightLevel(lightlevel);
		return this;
	}
	
	@Override
	public ItemBlock toItemBlock() {
		return item != null ? item : (item = new ItemBlockMetaPro(this, this.getRegistryName()));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> subBlocks) {
		for (int i = 0; i < variants.length; i++)
			subBlocks.add(new ItemStack(this, 1, i));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, meta);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE);
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(item, 1, this.getMetaFromState(world.getBlockState(pos)));
	}

	@Override
	public Tab getTab() {
		return Tab.BLOCKS;
	}
}
