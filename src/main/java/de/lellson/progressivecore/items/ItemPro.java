package de.lellson.progressivecore.items;

import akka.routing.BroadcastRoutingLogic;
import de.lellson.progressivecore.ProgressiveCore;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.ITab;
import de.lellson.progressivecore.misc.ProModelHandler;
import de.lellson.progressivecore.misc.ProOreDictionary;
import de.lellson.progressivecore.misc.ProRegistry;
import de.lellson.progressivecore.misc.helper.MiscHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class ItemPro extends Item implements IItemPro, ITab {
	
	private String name;
	private String[] variants;
	private int burnTime = -1;
	
	public ItemPro(String name, String... variants) {
		setUnlocalizedName(name);
		
		if (variants.length > 0)
			setHasSubtypes(true);
		
		this.name = name;
		this.variants = variants;
	}
	
	public String[] getVariants() {
		return variants;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public Item setUnlocalizedName(String name) {
		super.setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Constants.prefix(name)));
		ProRegistry.register(this);
		ProModelHandler.register(this);
		
		return this;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int dmg = stack.getItemDamage();
		return "item." + (dmg >= variants.length ? name : name + "_" + variants[dmg]);
	}
	
	public ItemPro stacksize(int size) {
		setMaxStackSize(size);
		return this;
	}
	
	public ItemPro oreDict(String name, boolean eachVariant) {
		
		if (getHasSubtypes() && eachVariant)
		{
			for (int i = 0; i < variants.length; i++)
				ProOreDictionary.registerOre(name + MiscHelper.upperFirst(variants[i]), this, i);
		}
		else
			ProOreDictionary.registerOre(name, this, getHasSubtypes() ? OreDictionary.WILDCARD_VALUE : 0);
		
		return this;
	}
	
	public ItemPro oreDict(String name) {
		return oreDict(name, true);
	}
	
	public ItemPro fuel(int burnTime) {
		this.burnTime = burnTime;
		return this;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if(isInCreativeTab(tab) || tab == null)
			for(int i = 0; i < variants.length; i++)
				subItems.add(new ItemStack(this, 1, i));
	}
	
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return burnTime;
	}

	@Override
	public Tab getTab() {
		return Tab.MATERIALS;
	}
}
