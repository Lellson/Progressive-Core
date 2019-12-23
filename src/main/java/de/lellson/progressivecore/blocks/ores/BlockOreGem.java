package de.lellson.progressivecore.blocks.ores;

import java.util.Random;

import de.lellson.progressivecore.blocks.BlockMetaPro;
import de.lellson.progressivecore.blocks.BlockPro.Tool;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.misc.ProOreDictionary;
import de.lellson.progressivecore.misc.ProRecipes;
import de.lellson.progressivecore.misc.config.ProConfig;
import de.lellson.progressivecore.misc.helper.MiscHelper;
import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.oredict.OreDictionary;

public class BlockOreGem extends BlockMetaPro {

	public float extraHardness;
	public int harvestLevel;
	public float smeltXp;
	public int xp;
	
	public BlockOreGem() {
		super("oregem", Material.ROCK, ProItems.GEMS);
		
		String name = "gem";
		String category = "ore" + MiscHelper.upperFirst(name);
		
		extraHardness = ProConfig.cfg.getFloat(name + "ExtraBlockHardness", category, 0, -3F, Short.MAX_VALUE, 
				"Determines the additional hardness " + name + " ores (+3) and " + name + " blocks (+5) have.");
		harvestLevel = ProConfig.cfg.getInt(name + "RequiredHarvestLevel", category, 2, 0, Short.MAX_VALUE, 
				"Determines the required harvest level to mine " + name + " ores and blocks.");
		smeltXp = ProConfig.cfg.getFloat(name + "SmeltXp", category, 1, 0, Short.MAX_VALUE, 
				"Determines the amount of XP dropped when a " + name + " ore is smelted.");
		xp = ProConfig.cfg.getInt(name + "DropXp", category, 5, 0, Short.MAX_VALUE,
				"Determines the amount of XP dropped when a " + name + " ore is mined. Only works if the ore drops an item.");
		
		hardness(3f + extraHardness);
		harvestLevel(Tool.PICKAXE, harvestLevel);
		sound(SoundType.STONE);
		for (int i = 0; i < ProItems.GEMS.length; i++)
		{
			ProOreDictionary.registerOre("ore" + MiscHelper.upperFirst(ProItems.GEMS[i]), this, i);
			ProRecipes.addSmelting(new ItemStack(this, 1, i), new ItemStack(ProItems.GEM, 1, i), 1);
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return new ItemStack(ProItems.GEM, this.quantityDropped(rand), getMetaFromState(state)).getItem();
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
		return fortune > 0 ? this.quantityDropped(rand) * Math.max(1, rand.nextInt(fortune+1)) : this.quantityDropped(rand);
    }

	public static int getMetaForBiome(Biome biome) {
		
		switch(biome.getTempCategory())
		{
			case MEDIUM:
				return biome.isHighHumidity() ? 5 : biome.getDefaultTemperature() <= 0.3f ? 3 : 0;
			case OCEAN:
				return 1;
			case COLD:
				return biome.isSnowyBiome() ? 2 : 3;
			case WARM:
				return 4;
		}
		
		return 0;
	}
}
