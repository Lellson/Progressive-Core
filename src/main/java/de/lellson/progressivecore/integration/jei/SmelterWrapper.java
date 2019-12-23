package de.lellson.progressivecore.integration.jei;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.lellson.progressivecore.inv.recipe.SmelterEntry;
import de.lellson.progressivecore.misc.RangeStack;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import mezz.jei.config.LocalizedConfiguration;
import mezz.jei.ingredients.IngredientRegistry;
import mezz.jei.plugins.vanilla.VanillaPlugin;
import mezz.jei.plugins.vanilla.furnace.FurnaceFuelCategory;
import mezz.jei.plugins.vanilla.furnace.FurnaceRecipeCategory;
import mezz.jei.runtime.JeiHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class SmelterWrapper implements IRecipeWrapper {

	private final SmelterEntry recipe;
	
	public SmelterWrapper(SmelterEntry recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		
		ingredients.setOutputLists(ItemStack.class, Arrays.asList(recipe.getOutput().getStacksWithCount()));
		
		List<List<ItemStack>> input = new ArrayList<>();
        for(RangeStack range : recipe.getInput())
        {
        	input.add(range.getStacks(true));
        }
        for (int i = input.size(); i < 6; i++)
        	input.add(Arrays.asList(ItemStack.EMPTY));
        
        input.add(ProJei.fuels);
        
        ingredients.setInputLists(ItemStack.class, input);
	}
	
	@Override
	public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

		mc.fontRenderer.drawString(recipe.getXp() + " XP", 128, 41, Color.darkGray.getRGB());
	}
}
