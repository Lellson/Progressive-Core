package de.lellson.progressivecore.blocks.deco;

import java.util.Random;

import de.lellson.progressivecore.blocks.BlockMetaPro;
import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.blocks.BlockPro.Tool;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.misc.ProOreDictionary;
import de.lellson.progressivecore.misc.helper.MiscHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockGem extends BlockMetaPro {

	public BlockGem() {
		super("blockgem", Material.IRON, ProItems.GEMS);
		hardness(5f + ProBlocks.ORE_GEM.extraHardness);
		harvestLevel(Tool.PICKAXE, ProBlocks.ORE_GEM.harvestLevel);
		sound(SoundType.METAL);
		for (int i = 0; i < ProItems.GEMS.length; i++)
			ProOreDictionary.registerOre("block" + MiscHelper.upperFirst(ProItems.GEMS[i]), this, i);
	}
}
