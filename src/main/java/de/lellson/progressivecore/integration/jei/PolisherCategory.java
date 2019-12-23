package de.lellson.progressivecore.integration.jei;

import java.util.List;

import de.lellson.progressivecore.inv.tile.TileEntitySmelter;
import de.lellson.progressivecore.misc.Constants;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class PolisherCategory implements IRecipeCategory<PolisherWrapper>{
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(Constants.prefix("textures/jei/polisher_jei.png"));
	public static final String ID = Constants.prefix("polisher");
	
	private final IGuiHelper helper;
	private final IDrawable background;
    private final IDrawable icon;
	
	public PolisherCategory(IGuiHelper helper) {
		this.helper = helper;
		this.background = helper.createDrawable(TEXTURE, 0, 0, 167, 130);
		this.icon = helper.createDrawable(TEXTURE, 167, 0, 16, 16);
	}
 
	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public String getTitle() {
		return "Gem Polisher";
	}

	@Override
	public String getModName() {
		return Constants.MOD_NAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, PolisherWrapper recipeWrapper, IIngredients ingredients) {

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
			
		recipeLayout.getItemStacks().init(0, true, 31, 23);
		recipeLayout.getItemStacks().set(0, inputs.get(0));
		
		recipeLayout.getItemStacks().init(1, true, 76, 23);
		recipeLayout.getItemStacks().set(1, inputs.get(1));
		
		recipeLayout.getItemStacks().init(2, false, 119, 23);
		recipeLayout.getItemStacks().set(2, ingredients.getOutputs(ItemStack.class).get(0));
	}
}
