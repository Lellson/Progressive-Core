package de.lellson.progressivecore.integration.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.lellson.progressivecore.inv.recipe.PolisherEntry;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.misc.RangeStack;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class PolisherWrapper implements IRecipeWrapper {

	private final PolisherEntry recipe;
	
	public PolisherWrapper(PolisherEntry recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		
		ingredients.setOutputLists(ItemStack.class, Arrays.asList(recipe.getOutput().getStacksWithCount()));
		ingredients.setInputLists(ItemStack.class, Arrays.asList(recipe.getInput().getStacksWithCount(), Arrays.asList(new ItemStack(ProItems.TITAN_CRUSHER))));
	}
}
