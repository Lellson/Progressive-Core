package de.lellson.progressivecore.integration.jei;

import java.util.List;

import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.blocks.misc.BlockSmelter;
import de.lellson.progressivecore.inv.container.ContainerSmelter;
import de.lellson.progressivecore.inv.recipe.PolisherEntry;
import de.lellson.progressivecore.inv.recipe.SmelterEntry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.plugins.vanilla.VanillaPlugin;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class ProJei implements IModPlugin {
	
	public static void preInit() {}
	
	public static void init() {}
	
	public static void postInit() {}
	
	public static List<ItemStack> fuels;
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		
		for (int i = 0; i < 4; i++)
			registry.addRecipeCategories(new SmelterCategory(registry.getJeiHelpers().getGuiHelper(), i));
		
		registry.addRecipeCategories(new PolisherCategory(registry.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registry) {

		fuels = registry.getIngredientRegistry().getFuels();
		
		for (int i = 0; i < 4; i++)
		{
			registry.handleRecipes(SmelterEntry.class, SmelterWrapper::new, SmelterCategory.getUid(i));
			registry.addRecipes(SmelterEntry.getEntries(i), SmelterCategory.getUid(i));
			registry.addRecipeCatalyst(new ItemStack(BlockSmelter.getSmelter(i, false)), SmelterCategory.getUid(i));
			registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerSmelter.class, SmelterCategory.getUid(i), 0, 8, 8, 36);
		}
		
		registry.handleRecipes(PolisherEntry.class, PolisherWrapper::new, PolisherCategory.ID);
		registry.addRecipes(PolisherEntry.ENTRIES, PolisherCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(ProBlocks.GEM_POLISHER), PolisherCategory.ID);
	}
}
