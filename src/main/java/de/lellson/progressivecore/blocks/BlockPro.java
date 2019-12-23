package de.lellson.progressivecore.blocks;

import de.lellson.progressivecore.ProgressiveCore;
import de.lellson.progressivecore.items.ItemBlockMetaPro;
import de.lellson.progressivecore.items.ItemPro;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.ITab;
import de.lellson.progressivecore.misc.ProModelHandler;
import de.lellson.progressivecore.misc.ProOreDictionary;
import de.lellson.progressivecore.misc.ProRegistry;
import de.lellson.progressivecore.misc.helper.MiscHelper;
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
import net.minecraftforge.oredict.OreDictionary;

public class BlockPro extends Block implements IBlockPro, ITab {
	
	private ItemBlock item = null;
	private String name;
	
	public BlockPro(String name, Material material) {
		super(material);

		this.name = name;
		
		setUnlocalizedName(name);
		
		applyDefaultStats();
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
	
	public BlockPro applyDefaultStats() {
		return hardness(1);
	}
	
	public BlockPro hardness(float hardness) {
		setHardness(hardness);
		return this;
	}
	
	public BlockPro sound(SoundType soundType) {
		setSoundType(soundType);
		return this;
	}
	
	public BlockPro harvestLevel(Tool tool, int level) {
		setHarvestLevel(tool.toString(), level);
		return this;
	}
	
	public BlockPro lightLevel(float lightlevel) {
		setLightLevel(lightlevel);
		return this;
	}
	
	public BlockPro oreDict(String name) {
		ProOreDictionary.registerOre(name, this, 0);
		return this;
	}

	@Override
	public ItemBlock toItemBlock() {
		return item != null ? item : (item = (ItemBlock) new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	public static enum Tool {
		PICKAXE("pickaxe"), AXE("axe"), SHOVEL("shovel");
		
		private String name;
		
		private Tool(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}

	@Override
	public Tab getTab() {
		return Tab.BLOCKS;
	}
}
