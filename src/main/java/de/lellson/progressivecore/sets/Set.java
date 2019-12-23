package de.lellson.progressivecore.sets;

import de.lellson.progressivecore.blocks.BlockPro;
import de.lellson.progressivecore.blocks.IBlockPro;
import de.lellson.progressivecore.blocks.BlockPro.Tool;
import de.lellson.progressivecore.blocks.ores.BlockOre;
import de.lellson.progressivecore.items.IItemPro;
import de.lellson.progressivecore.items.ItemPro;
import de.lellson.progressivecore.items.armor.handler.ArmorHandler;
import de.lellson.progressivecore.items.tools.handler.ToolHandler;
import de.lellson.progressivecore.misc.ProRecipes;
import de.lellson.progressivecore.misc.config.ProConfig;
import de.lellson.progressivecore.misc.helper.MiscHelper;
import de.lellson.progressivecore.sets.Sets.Tier;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Set {
	
	private String name;
	private Tier tier;
	private ToolMaterial toolMaterial;
	private ArmorMaterial armorMaterial;
	private boolean alloy;
	private boolean chunks;
	private int minDrop;
	private int maxDrop;
	
	private Item material;
	private BlockPro blockOre;
	private BlockPro blockOreNether;
	private BlockPro blockDeco;
	private ToolSet toolSet;
	private ArmorSet armorSet;
	private SetOreDictionary setDict;
	private boolean hasExistingMat;
	private boolean hasNetherOre;
	private String prefixIngot = "ingot_";
	private String prefixChunk = "chunk_";
	private int fuel = -1;
	private boolean forceBlock;
	
	private ArmorHandler armorHandler;
	private ToolHandler toolHandler;
	
	public Set(String name, Tier tier, ToolMaterial toolMaterial, ArmorMaterial armorMaterial, boolean alloy, boolean chunks, int minDrop, int maxDrop) {
		this.name = name;
		this.tier = tier;
		this.toolMaterial = toolMaterial;
		this.armorMaterial = armorMaterial;
		this.alloy = alloy;
		this.chunks = chunks;
		this.minDrop = minDrop;
		this.maxDrop = maxDrop;
	}
	
	public Set(String name, Tier tier, ToolMaterial toolMaterial, ArmorMaterial armorMaterial, boolean alloy) {
		this(name, tier, toolMaterial, armorMaterial, alloy, false, 1, 1);
	}	
	
	public Set(String name, Tier tier, ToolMaterial toolMaterial, ArmorMaterial armorMaterial) {
		this(name, tier, toolMaterial, armorMaterial, false);
	}

	public void createSet() {
		
		String category = "ore" + MiscHelper.upperFirst(name);
		
		if (!hasExistingMat)
			material = new ItemPro((hasChunks() ? prefixChunk : prefixIngot) + name).fuel(fuel);
		
		float extraBlockHardness = 0;
		int requiredHarvestLevel = 0;
		if ((hasOre() && !hasExistingMat) || ((!hasChunks() && !hasExistingMat) || forceBlock))
		{
			extraBlockHardness = ProConfig.cfg.getFloat(name + "ExtraBlockHardness", category, tier.getExtraBlockHardness(), -3F, Short.MAX_VALUE, 
								"Determines the additional hardness " + name + " ores (+3) and " + name + " blocks (+5) have.");
			requiredHarvestLevel = ProConfig.cfg.getInt(name + "RequiredHarvestLevel", category, tier.getRequiredHarvestLevel(), 0, Short.MAX_VALUE, 
								"Determines the required harvest level to mine " + name + " ores and blocks.");
		}
		
		if (hasOre() && !hasExistingMat)
		{
			float smeltXp = ProConfig.cfg.getFloat(name + "SmeltXp", category, tier.getSmeltXp(), 0, Short.MAX_VALUE, 
					"Determines the amount of XP dropped when a " + name + " ore is smelted.");
			int xp = ProConfig.cfg.getInt(name + "DropXp", category, tier.getXp(), 0, Short.MAX_VALUE,
					"Determines the amount of XP dropped when a " + name + " ore is mined. Only works if the ore drops an item.");
			
			blockOre = new BlockOre(name, hasChunks() ? material : null, minDrop, maxDrop, extraBlockHardness, requiredHarvestLevel, xp);
			ProRecipes.addSmelting(blockOre, new ItemStack(material), smeltXp);
			if (hasNetherOre)
			{
				float extraBlockHardnessNether = ProConfig.cfg.getFloat(name + "ExtraBlockHardness", category + "Nether", tier.getExtraBlockHardness(), -3F, Short.MAX_VALUE, 
						"Determines the additional hardness " + name + " nether ores (+3) have.");
				int requiredHarvestLevelNether = ProConfig.cfg.getInt(name + "RequiredHarvestLevel", category + "Nether", tier.getRequiredHarvestLevel(), 0, Short.MAX_VALUE, 
						"Determines the required harvest level to mine " + name + " nether ores and blocks.");
				
				float smeltXpNether = ProConfig.cfg.getFloat(name + "SmeltXp", category + "Nether", tier.getSmeltXp(), 0, Short.MAX_VALUE, 
						"Determines the amount of XP dropped when a " + name + " nether ore is smelted.");
				int xpNether = ProConfig.cfg.getInt(name + "DropXp", category + "Nether", tier.getXp(), 0, Short.MAX_VALUE,
						"Determines the amount of XP dropped when a " + name + "nether ore is mined. Only works if the ore drops an item.");

				blockOreNether = new BlockOre("nether_" + name, hasChunks() ? material : null, minDrop, maxDrop, extraBlockHardnessNether, requiredHarvestLevelNether, xpNether); 
				ProRecipes.addSmelting(blockOreNether, new ItemStack(material), smeltXpNether);
			}
		}
		
		if (hasDeco())
		{
			blockDeco = new BlockPro("block_" + name, Material.IRON).hardness(5f + extraBlockHardness).harvestLevel(Tool.PICKAXE, requiredHarvestLevel).sound(SoundType.METAL);
		}
			
		
		if (hasTools())
		{
			toolMaterial.setRepairItem(new ItemStack(material));
			toolSet = new ToolSet(toolMaterial, toolHandler, name, tier);
		}
		
		if (hasArmor())
		{
			armorMaterial.setRepairItem(new ItemStack(material));
			armorSet = new ArmorSet(armorMaterial, armorHandler, name);
		}
		
		if (setDict != null)
			setDict.applyOreDict(this);
	}

	public Set setMaterial(Item material) {
		this.material = material;
		this.hasExistingMat = true;
		return this;
	}
	
	public Set setOre(SetOreDictionary setDict) {
		this.setDict = setDict;
		return this;
	}
	
	public Set setNetherOre() {
		this.hasNetherOre = true;
		return this;
	}
	
	public Set setForceBlock() {
		this.forceBlock = true;
		return this;
	}
	
	public Set setHandlers(ArmorHandler armorHandler, ToolHandler toolHandler) {
		this.armorHandler = armorHandler;
		this.toolHandler = toolHandler;
		return this;
	}
	
	public Set setPrefix(String prefix) {
		this.prefixIngot = prefix;
		this.prefixChunk = prefix;
		return this;
	}
	
	public Set fuel(int fuel) {
		this.fuel = fuel;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public Tier getTier() {
		return tier;
	}
	
	public ToolMaterial getToolMaterial() {
		return toolMaterial;
	}
	
	public ArmorMaterial getArmorMaterial() {
		return armorMaterial;
	}
	
	public SetOreDictionary getOre() {
		return setDict;
	}
	
	public boolean hasNetherOre() {
		return hasNetherOre;
	}
	
	public boolean hasChunks() {
		return chunks;
	}
	
	public boolean hasOre() {
		return !alloy;
	}
	
	public boolean hasOreDict() {
		return setDict != null;
	}
	
	public boolean hasDeco() {
		return (!hasChunks() && !hasExistingMat) || forceBlock;
	}

	public boolean hasTools() {
		return toolMaterial != null;
	}
	
	public boolean hasArmor() {
		return armorMaterial != null;
	}
	
	public Item getMaterial() {
		return material;
	}
	
	public BlockPro getBlockOre() {
		return blockOre;
	}
	
	public BlockPro getBlockOreNether() {
		return blockOreNether;
	}
	
	public BlockPro getBlockDeco() {
		return blockDeco;
	}
	
	public ToolSet getToolSet() {
		return toolSet;
	}
	
	public ArmorSet getArmorSet() {
		return armorSet;
	}
}
