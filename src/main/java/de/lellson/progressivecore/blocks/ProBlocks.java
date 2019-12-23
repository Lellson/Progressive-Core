package de.lellson.progressivecore.blocks;

import de.lellson.progressivecore.blocks.deco.BlockEmeraldStash;
import de.lellson.progressivecore.blocks.deco.BlockGem;
import de.lellson.progressivecore.blocks.deco.BlockGemStash;
import de.lellson.progressivecore.blocks.misc.BlockGemPolisher;
import de.lellson.progressivecore.blocks.misc.BlockProMoltenMetal;
import de.lellson.progressivecore.blocks.misc.BlockRealityEye;
import de.lellson.progressivecore.blocks.misc.BlockSmelter;
import de.lellson.progressivecore.blocks.misc.ProFluid;
import de.lellson.progressivecore.blocks.ores.BlockOreGem;
import de.lellson.progressivecore.integration.ProIntegration;
import de.lellson.progressivecore.integration.ProIntegration.Mod;
import de.lellson.progressivecore.items.ItemPro;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;

public class ProBlocks {

	public static final BlockOreGem ORE_GEM = new BlockOreGem();
	public static final BlockGem BLOCK_GEM = new BlockGem();
	public static final BlockGemStash STASH_GEM = new BlockGemStash();
	public static final BlockEmeraldStash STASH_EMERALD = new BlockEmeraldStash();
	
	public static final BlockSmelter SMELTER1 = new BlockSmelter(0, false);
	public static final BlockSmelter SMELTER1_ON = new BlockSmelter(0, true);
	public static final BlockSmelter SMELTER2 = new BlockSmelter(1, false);
	public static final BlockSmelter SMELTER2_ON = new BlockSmelter(1, true);
	public static final BlockSmelter SMELTER3 = new BlockSmelter(2, false);
	public static final BlockSmelter SMELTER3_ON = new BlockSmelter(2, true);
	public static final BlockSmelter SMELTER4 = new BlockSmelter(3, false);
	public static final BlockSmelter SMELTER4_ON = new BlockSmelter(3, true);
	
	public static final BlockRealityEye REALITY_EYE = new BlockRealityEye(false);
	public static final BlockRealityEye REALITY_EYE_ON = new BlockRealityEye(true);
	
	public static final BlockGemPolisher GEM_POLISHER = new BlockGemPolisher();
	
	public static final BlockProMoltenMetal FLUID_HELLSTEEL = new BlockProMoltenMetal(new ProFluid("hellsteel", 300), 0xFF9900);
	public static final BlockProMoltenMetal FLUID_TUNGSTEN = new BlockProMoltenMetal(new ProFluid("tungsten", 500), 0x88BBAA);
	public static final BlockProMoltenMetal FLUID_LUMINIUM = new BlockProMoltenMetal(new ProFluid("luminium", 800), 0x99885F);
	public static final BlockProMoltenMetal FLUID_TITANIUM = new BlockProMoltenMetal(new ProFluid("titanium", 1300), 0xAAAADD);
	public static final BlockProMoltenMetal FLUID_MITHRIL = new BlockProMoltenMetal(new ProFluid("mithril", 1300), 0x66CCFF);
	public static final BlockProMoltenMetal FLUID_ORICHALCUM = new BlockProMoltenMetal(new ProFluid("orichalcum", 1300), 0x66FF77);
	public static final BlockProMoltenMetal FLUID_ADAMANTITE = new BlockProMoltenMetal(new ProFluid("adamantite", 1300), 0xFF6677);
	
	public static final BlockProMoltenMetal FLUID_RUBY = new BlockProMoltenMetal(new ProFluid("ruby", 999), 0xFF8888);
	public static final BlockProMoltenMetal FLUID_SAPPHIRE = new BlockProMoltenMetal(new ProFluid("sapphire", 999), 0x8888FF);
	public static final BlockProMoltenMetal FLUID_AMETHYST = new BlockProMoltenMetal(new ProFluid("amethyst", 999), 0xFF88FF);
	public static final BlockProMoltenMetal FLUID_TOPAZ = new BlockProMoltenMetal(new ProFluid("topaz", 999), 0xFFFF88);
	public static final BlockProMoltenMetal FLUID_OPAL = new BlockProMoltenMetal(new ProFluid("opal", 999), 0xFFBB88);
	public static final BlockProMoltenMetal FLUID_MALACHITE = new BlockProMoltenMetal(new ProFluid("malachite", 999), 0x88FFBB);
	
	public static void init() {
		if (Mod.TCONSTRUCT.isLoaded())
			FluidRegistry.enableUniversalBucket();
	}
}
