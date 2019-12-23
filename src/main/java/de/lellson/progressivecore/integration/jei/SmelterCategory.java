package de.lellson.progressivecore.integration.jei;

import java.util.List;

import de.lellson.progressivecore.inv.tile.TileEntitySmelter;
import de.lellson.progressivecore.misc.Constants;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SmelterCategory implements IRecipeCategory<SmelterWrapper>{

	public static final ResourceLocation TEXTURE = new ResourceLocation(Constants.prefix("textures/jei/smelter_jei.png"));
	private static final String ID = Constants.prefix("smelter");
	
	private final IGuiHelper helper;
	private final int level;
	private final IDrawable background;
    private final IDrawable icon;
	
	public SmelterCategory(IGuiHelper helper, int level) {
		this.helper = helper;
		this.level = level;
		this.background = helper.createDrawable(TEXTURE, 0, 0, 167, 130);
		this.icon = helper.createDrawable(TEXTURE, 167 + level*16, 0, 16, 16);
	}
 
	@Override
	public String getUid() {
		return ID + "." + level;
	}
	
	public static String getUid(int level) {
		return ID + "." + level;
	}

	@Override
	public String getTitle() {
		return TileEntitySmelter.getSmelterName(level);
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
	public void setRecipe(IRecipeLayout recipeLayout, SmelterWrapper recipeWrapper, IIngredients ingredients) {
		
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		
		for (int i = 0; i < 6; i++)
		{
			recipeLayout.getItemStacks().init(i, true, 46 + i%3 * 18, 6 + i/3 * 18);
			recipeLayout.getItemStacks().set(i, inputs.get(i));
		}
			
		recipeLayout.getItemStacks().init(6, true, 20, 15);
		recipeLayout.getItemStacks().set(6, inputs.get(6));
		
		recipeLayout.getItemStacks().init(7, false, 128, 15);
		recipeLayout.getItemStacks().set(7, ingredients.getOutputs(ItemStack.class).get(0));
	}
}
