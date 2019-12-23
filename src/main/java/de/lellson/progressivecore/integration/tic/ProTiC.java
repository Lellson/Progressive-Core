package de.lellson.progressivecore.integration.tic;

import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.blocks.misc.BlockProMoltenMetal;
import de.lellson.progressivecore.integration.tic.modifier.ModifierExpanse;
import de.lellson.progressivecore.integration.tic.traits.TraitDense3;
import de.lellson.progressivecore.integration.tic.traits.TraitIgniting;
import de.lellson.progressivecore.integration.tic.traits.TraitMassive;
import de.lellson.progressivecore.integration.tic.traits.TraitTitanStrength;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.helper.MiscHelper;
import de.lellson.progressivecore.sets.Set;
import de.lellson.progressivecore.sets.Sets;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.typesafe.config.Optional;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.MaterialEvent;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import slimeknights.tconstruct.library.smeltery.PreferenceCastingRecipe;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.TinkerTraits;
import slimeknights.tconstruct.tools.melee.item.Rapier;

public class ProTiC {
	
	public static final BlockProMoltenMetal[] MOLTEN_GEM = new BlockProMoltenMetal[]{ ProBlocks.FLUID_RUBY, ProBlocks.FLUID_SAPPHIRE, ProBlocks.FLUID_AMETHYST, 
			ProBlocks.FLUID_TOPAZ, ProBlocks.FLUID_OPAL, ProBlocks.FLUID_MALACHITE };
	
	public static final AbstractTrait FIERY = new TraitIgniting();
	public static final AbstractTrait TITAN_STRENGTH = new TraitTitanStrength();
	public static final AbstractTrait MASSIVE = new TraitMassive();
	public static final AbstractTrait DENSE3 = new TraitDense3();
	
	public static Material tungsten;
	public static Material luminium;
	public static Material hellsteel;
	public static Material titanium;
	public static Material mithril;
	public static Material orichalcum;
	public static Material adamantite;
	public static Material[] gem;
	
	public static ModifierExpanse expanseModifier;
	
	@Optional
	public static void preInit() {
		
		MinecraftForge.EVENT_BUS.register(ProTiC.class);
		
		tungsten = createMaterial(Sets.TUNGSTEN_SET, ProBlocks.FLUID_TUNGSTEN);
		luminium = createMaterial(Sets.LUMINIUM_SET, ProBlocks.FLUID_LUMINIUM);
		hellsteel = createMaterial(Sets.HELLSTEEL_SET, ProBlocks.FLUID_HELLSTEEL,
				new HandleMaterialStats(0.88f, 90), 
				new ExtraMaterialStats(200)
		);
		titanium = createMaterial(Sets.TITANIUM_SET, ProBlocks.FLUID_TITANIUM,
				new HandleMaterialStats(1.3f, 300), 
				new ExtraMaterialStats(800)
		);
		mithril = createMaterial(Sets.MITHRIL_SET, ProBlocks.FLUID_MITHRIL,
				new HandleMaterialStats(1.5f, 450), 
				new ExtraMaterialStats(1200)
		);
		orichalcum = createMaterial(Sets.ORICHALCUM_SET, ProBlocks.FLUID_ORICHALCUM,
				new HandleMaterialStats(1.5f, 450), 
				new ExtraMaterialStats(1200)
		);
		adamantite = createMaterial(Sets.ADAMANTITE_SET, ProBlocks.FLUID_ADAMANTITE,
				new HandleMaterialStats(1.5f, 450), 
				new ExtraMaterialStats(1200)
		);
		gem = new Material[ProItems.GEMS.length];
		for (int i = 0; i < gem.length; i++)
			gem[i] = createMaterialGem(i);
		
		expanseModifier = new ModifierExpanse();
	}

	@Optional
	public static void init() {
		
		createMaterialNoTool(tungsten, Sets.TUNGSTEN_SET);
		createMaterialNoTool(luminium, Sets.LUMINIUM_SET);
		createMaterialCastTool(hellsteel, Sets.HELLSTEEL_SET,
				new Trait(TinkerTraits.flammable),
				new Trait(FIERY, MaterialTypes.HEAD),
				new Trait(TinkerTraits.autosmelt, MaterialTypes.HEAD)
		);
		createMaterialCastTool(titanium, Sets.TITANIUM_SET,
				new Trait(TITAN_STRENGTH, MaterialTypes.HEAD),
				new Trait(MASSIVE, MaterialTypes.HEAD)
		);
		createMaterialCastTool(mithril, Sets.MITHRIL_SET, 
				new Trait(TinkerTraits.established),
				new Trait(TinkerTraits.lightweight),
				new Trait(MASSIVE, MaterialTypes.HEAD)
		);
		createMaterialCastTool(orichalcum, Sets.ORICHALCUM_SET, 
				new Trait(TinkerTraits.established),
				new Trait(TinkerTraits.insatiable),
				new Trait(MASSIVE, MaterialTypes.HEAD)
		);
		createMaterialCastTool(adamantite, Sets.ADAMANTITE_SET, 
				new Trait(TinkerTraits.established),
				new Trait(DENSE3),
				new Trait(MASSIVE, MaterialTypes.HEAD)
		);
	}

	@Optional
	public static void postInit() {
		
		for (int i = 0; i < ProItems.GEMS.length; i++)
		{
			String suffix = MiscHelper.upperFirst(ProItems.GEMS[i]);
			Fluid fluid = MOLTEN_GEM[i].getFluid();
			
			for(FluidStack fs : TinkerSmeltery.castCreationFluids)
				TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castGem, RecipeMatch.of("gem" + suffix), fs, true, true));
			
			TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of("gem" + suffix, Material.VALUE_Gem), fluid));
			TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of("dust" + suffix, Material.VALUE_Gem), fluid));
		    TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of("ore" + suffix, (int) (Material.VALUE_Gem * Config.oreToIngotRatio)), fluid));
		    TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of("block" + suffix, Material.VALUE_Gem * 9), fluid));
		    TinkerRegistry.registerTableCasting(new ItemStack(ProItems.GEM, 1, i), TinkerSmeltery.castGem, fluid, Material.VALUE_Gem);
		    TinkerRegistry.registerBasinCasting(new ItemStack(ProBlocks.BLOCK_GEM, 1, i), ItemStack.EMPTY, fluid, Material.VALUE_Gem * 9);
		}
	}
	
	@SubscribeEvent
	public static void onHeadStatAdded(MaterialEvent.StatRegisterEvent<HeadMaterialStats> event) {
		
		if (event.stats instanceof HeadMaterialStats)
		{
			for (String material : Sets.getTicSets())
			{
				if (event.material.getIdentifier().equals(material))
				{
					Set set = Sets.getForName(material);
					
					if (set == null)
					{
						FMLLog.bigWarning(Constants.MOD_NAME + ": Tinkers Construct material adjustment failed. No tool set with name \"" + material + "\" found!");
						break;
					}
					
					ToolMaterial tool = set.getToolMaterial();
					if (tool != null)
					{
						event.overrideResult(new HeadMaterialStats(tool.getMaxUses(), tool.getEfficiency(), tool.getAttackDamage()+1, tool.getHarvestLevel()));
					}
				}
			}
		}
	}
	
	
	private static Material createMaterial(String name, BlockProMoltenMetal fluidBlock, String oreRequirement, String oreSuffix, ToolMaterial tool, boolean toolforge, IMaterialStats... stats) {
		
		Material material = new Material("prog." + name, fluidBlock.getColor());
		
		if (tool != null)
			TinkerRegistry.addMaterialStats(material, new HeadMaterialStats(tool.getMaxUses(), tool.getEfficiency(), tool.getAttackDamage()+2, tool.getHarvestLevel()), stats);
		
		if (oreSuffix != null)
			oreSuffix = MiscHelper.upperFirst(oreSuffix);
		
		MaterialIntegration integration = TinkerRegistry.integrate(new MaterialIntegration(oreSuffix != null ? oreRequirement + oreSuffix : null, material, fluidBlock.getFluid(), oreSuffix));
		
		if (oreSuffix != null)
			integration.setRepresentativeItem(oreRequirement + oreSuffix);
		
		if (toolforge)
			integration.toolforge();
			
		integration.preInit();
		
		return material;
	}
	
	private static Material createMaterial(Set set, BlockProMoltenMetal fluidBlock, IMaterialStats... stats) {
		return createMaterial(set.getName(), fluidBlock, "ingot", set.getOre().getName(), set.getToolMaterial(), set.hasDeco(), stats);
	}
	
	private static Material createMaterialGem(int gemIndex) {
		return createMaterial(ProItems.GEMS[gemIndex], MOLTEN_GEM[gemIndex], null, null, null, true);
	}
	
	private static void createMaterialCastTool(Material material, Set set, Trait... traits) {
		createMaterialNoTool(material, set);
		for (Trait trait : traits)
			material.addTrait(trait.trait, trait.types);
		material.setCraftable(false).setCastable(true);
	}
	
	private static void createMaterialNoTool(Material material, Set set) {
		material.addCommonItems(MiscHelper.upperFirst(set.getOre().getName()));
		material.addItem(set.getMaterial());
		material.addItem(set.getBlockOre(), 2);
		material.setRepresentativeItem(set.getMaterial());
	}

	private static class Trait {
		
		public final ITrait trait;
		public final String types;
		
		public Trait(ITrait trait, String types) {
			this.trait = trait;
			this.types = types;
		}
		
		public Trait(ITrait trait) {
			this.trait = trait;
			this.types = null;
		}
	}
	
	/*
	private static final ResourceLocation FLUID_STILL = new ResourceLocation("tconstruct:blocks/fluids/molten_metal");
	private static final ResourceLocation FLUID_FLOWING = new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow");
	
	public static void init() {
		
		if (!Loader.isModLoaded("tconstruct"))
			return;
		
		addTiCMaterial("hellsteel", new Color(255, 150, 0), true);
		addTiCMaterial("tungsten", new Color(160, 200, 190), true);
		addTiCMaterial("luminium", new Color(180, 160, 130), true);
		
		addTiCAlloy("steel", new AlloyInput("iron", 0.5f), new AlloyInput("tungsten", 0.5f));
	}
	
	private static void addTiCMaterial(String name, Color fluidColor, boolean toolforge) {
		
		Fluid fluid = new Fluid(name, FLUID_STILL, FLUID_FLOWING, fluidColor);
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("fluid", fluid.getName());
		String ore = MiscHelper.upperFirst(name);
		tag.setString("ore", ore);
		tag.setString("ingot", ore);
		tag.setString("block", ore);
		tag.setInteger("temperature", 3000);
		tag.setBoolean("toolforge", toolforge);

		FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tag);
	}
	
	private static void addTiCAlloy(String outputFluid, AlloyInput... input)
	{
		NBTTagList tagList = new NBTTagList();
		
		NBTTagCompound fluid = new NBTTagCompound();
		fluid.setString("FluidName", outputFluid);
		fluid.setInteger("Amount", 144);
		tagList.appendTag(fluid);
		
		for (AlloyInput material : input)
		{
			fluid = new NBTTagCompound();
			fluid.setString("FluidName", material.getFluid());
			fluid.setInteger("Amount", (int)(144 * material.getRatio())); // 3/4 ingot
			tagList.appendTag(fluid);
		}

		NBTTagCompound message = new NBTTagCompound();
		message.setTag("alloy", tagList);
		FMLInterModComms.sendMessage("tconstruct", "alloy", message);
	}
	
	public static class AlloyInput {
		
		private final String fluid;
		private final float ratio;
		
		public AlloyInput(String fluid, float ratio) {
			this.fluid = fluid;
			this.ratio = ratio;
		}
		
		public String getFluid() {
			return fluid;
		}
		
		public float getRatio() {
			return ratio;
		}
	}
	*/
}
