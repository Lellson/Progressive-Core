package de.lellson.progressivecore.items;

import de.lellson.progressivecore.ProgressiveCore;
import de.lellson.progressivecore.blocks.BlockMetaPro;
import de.lellson.progressivecore.blocks.BlockPro;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.ProModelHandler;
import de.lellson.progressivecore.misc.ProRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ItemBlockMetaPro extends ItemBlock {
	
	private BlockMetaPro block;
	
	public ItemBlockMetaPro(BlockMetaPro block, ResourceLocation res) {
		super(block);
		this.block = block;
		
		if (block.getVariants().length > 0)
			setHasSubtypes(true);
		
		setRegistryName(res);
	}
	
	public String[] getVariants() {
		return block.getVariants();
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int dmg = stack.getItemDamage();
		return "tile." + block.getName() + (dmg >= block.getVariants().length ? "" : "_" + block.getVariants()[dmg]);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if(isInCreativeTab(tab) || tab == null)
			for(int i = 0; i < block.getVariants().length; i++)
				subItems.add(new ItemStack(this, 1, i));
	}
}
