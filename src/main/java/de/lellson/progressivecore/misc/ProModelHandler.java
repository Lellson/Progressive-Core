package de.lellson.progressivecore.misc;

import java.util.ArrayList;
import java.util.List;

import de.lellson.progressivecore.ProgressiveCore;
import de.lellson.progressivecore.blocks.IBlockPro;
import de.lellson.progressivecore.blocks.misc.BlockProMoltenMetal;
import de.lellson.progressivecore.items.IItemPro;
import de.lellson.progressivecore.items.ItemBlockMetaPro;
import de.lellson.progressivecore.items.ItemPro;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ProModelHandler {
	
	private static List<IItemPro> items = new ArrayList<IItemPro>();
	private static List<IBlockPro> blocks = new ArrayList<IBlockPro>();
	private static List<BlockProMoltenMetal> fluidBlocks = new ArrayList<BlockProMoltenMetal>();
	
	public static void register(IBlockPro block) {
		blocks.add(block);
	}
	
	public static void register(IItemPro item) {
		items.add(item);
	}
	
	public static void register(BlockProMoltenMetal fluidBlock) {
		fluidBlocks.add(fluidBlock);
	}
	
	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event) {

		for (IItemPro item : items)
			registerRender((Item)item);
		
		for (IBlockPro block : blocks)
			registerRender(block.toItemBlock());
	}
	
	public static void renderFluids() {
		
		for (BlockProMoltenMetal fluidBlock : fluidBlocks)
			ProgressiveCore.proxy.renderFluid(fluidBlock);
	}

	private static void registerRender(Item item) {
		
		if (item.getHasSubtypes())
		{
			NonNullList<ItemStack> subItems = NonNullList.create();
			item.getSubItems(null, subItems);
			for (ItemStack stack : subItems)
			{
				ModelLoader.setCustomModelResourceLocation(item, stack.getItemDamage(), new ModelResourceLocation(item.getRegistryName() + "_" + getDamageName(item, stack), Constants.INVENTORY));
			}
		}
		else
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), Constants.INVENTORY));
	}

	private static String getDamageName(Item item, ItemStack stack) {
		return (item instanceof ItemBlockMetaPro ? ((ItemBlockMetaPro)item).getVariants() : ((ItemPro)item).getVariants())[stack.getItemDamage()];
	}
}
