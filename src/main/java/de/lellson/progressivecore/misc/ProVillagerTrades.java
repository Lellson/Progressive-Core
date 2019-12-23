package de.lellson.progressivecore.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.EmeraldForItems;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.passive.EntityVillager.ItemAndEmeraldToItem;
import net.minecraft.entity.passive.EntityVillager.ListEnchantedBookForEmeralds;
import net.minecraft.entity.passive.EntityVillager.ListEnchantedItemForEmeralds;
import net.minecraft.entity.passive.EntityVillager.ListItemForEmeralds;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ProVillagerTrades {

	private static final String MAP_FOR_EMERALDS_CLASS = "net.minecraft.entity.passive.EntityVillager$TreasureMapForEmeralds";
	
	public static void addTrades() {
		
		if (!ProConfig.villagerGemTrades)
			return;
		
		for (VillagerProfession prof : ForgeRegistries.VILLAGER_PROFESSIONS.getValuesCollection())
		{
			List<VillagerCareer> careers = ReflectionHelper.getPrivateValue(VillagerProfession.class, prof, 3);
			for (VillagerCareer career : careers)
			{
				List<List<ITradeList>> tradelists = ReflectionHelper.getPrivateValue(VillagerCareer.class, career, 3);
				for (int i = 0; i < tradelists.size(); i++)
				{
					List<ITradeList> trades = tradelists.get(i);
					List<ITradeList> customTrades = new ArrayList<ITradeList>();
					
					for (ITradeList trade : trades)
					{
						for (int j = 0; j < ProItems.GEMS.length; j++)
						{
							ITradeList newTrade = editEmerald(trade, j);
							if (newTrade != null)
								customTrades.add(newTrade);
							else
								FMLLog.bigWarning(trade.getClass().getName() + " not handled!");
						}
					}
					
					career.addTrade(i+1, customTrades.toArray(new ITradeList[] {}));
				}
			}
		}
	}
	
	private static ITradeList editEmerald(ITradeList trade, int gemMeta) {
		
		if (trade instanceof EmeraldForItems)
		{
			EmeraldForItems old = (EmeraldForItems) trade;
			return new GemForItems(old.buyingItem, old.price, gemMeta);
		}
		else if (trade instanceof ItemAndEmeraldToItem)
		{
			ItemAndEmeraldToItem old = (ItemAndEmeraldToItem) trade;
			return new ItemAndGemToItem(old.buyingItemStack, old.buyingPriceInfo, old.sellingItemstack, old.sellingPriceInfo, gemMeta);
		}
		else if (trade instanceof ListEnchantedBookForEmeralds)
		{
			ListEnchantedBookForEmeralds old = (ListEnchantedBookForEmeralds) trade;
			return new ListEnchantedBookForGems(gemMeta);
		}
		else if (trade instanceof ListEnchantedItemForEmeralds)
		{
			ListEnchantedItemForEmeralds old = (ListEnchantedItemForEmeralds) trade;
			return new ListEnchantedItemForGems(old.enchantedItemStack, old.priceInfo, gemMeta);
		}
		else if (trade instanceof ListItemForEmeralds)
		{
			ListItemForEmeralds old = (ListItemForEmeralds) trade;
			return new ListItemForGems(old.itemToBuy, old.priceInfo, gemMeta);
		}
		else if (trade.getClass().getName().equals(MAP_FOR_EMERALDS_CLASS))
		{
			Class<?> clazz = null;
			try 
			{
				clazz = Class.forName(MAP_FOR_EMERALDS_CLASS);
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
			PriceInfo value = ReflectionHelper.getPrivateValue((Class<ITradeList>)clazz, trade, 0);
			String destination = ReflectionHelper.getPrivateValue((Class<ITradeList>)clazz, trade, 1);
			MapDecoration.Type destinationType = ReflectionHelper.getPrivateValue((Class<ITradeList>)clazz, trade, 2);
			
			return new TreasureMapForGems(value, destination, destinationType, gemMeta);
		}
		
		return null;
	}
	
	public static class GemForItems implements EntityVillager.ITradeList
    {
        public Item buyingItem;
        public EntityVillager.PriceInfo price;
        private final int gemMeta;

        public GemForItems(Item itemIn, EntityVillager.PriceInfo priceIn, int gemMeta)
        {
            this.buyingItem = itemIn;
            this.price = priceIn;
            this.gemMeta = gemMeta;
        }

        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
        {
            int i = 1;

            if (this.price != null)
            {
                i = this.price.getPrice(random);
            }

            recipeList.add(new MerchantRecipe(new ItemStack(this.buyingItem, i, 0), new ItemStack(ProItems.GEM, 1, gemMeta)));
        }
    }

	public static class ItemAndGemToItem implements EntityVillager.ITradeList
    {
        public ItemStack buyingItemStack;
        public EntityVillager.PriceInfo buyingPriceInfo;
        public ItemStack sellingItemstack;
        public EntityVillager.PriceInfo sellingPriceInfo;
        private final int gemMeta;

        public ItemAndGemToItem(ItemStack buyingItemStack, EntityVillager.PriceInfo buyingPriceInfo, ItemStack sellingItemstack, EntityVillager.PriceInfo sellingPriceInfo, int gemMeta)
        {
            this.buyingItemStack = buyingItemStack;
            this.buyingPriceInfo = buyingPriceInfo;
            this.sellingItemstack = sellingItemstack;
            this.sellingPriceInfo = sellingPriceInfo;
            this.gemMeta = gemMeta;
        }

        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
        {
            int i = this.buyingPriceInfo.getPrice(random);
            int j = this.sellingPriceInfo.getPrice(random);
            recipeList.add(new MerchantRecipe(new ItemStack(this.buyingItemStack.getItem(), i, this.buyingItemStack.getMetadata()), new ItemStack(ProItems.GEM, 1, gemMeta), new ItemStack(this.sellingItemstack.getItem(), j, this.sellingItemstack.getMetadata())));
        }
    }

	public static class ListEnchantedBookForGems implements EntityVillager.ITradeList
    {
		private final int gemMeta;
		
		public ListEnchantedBookForGems(int gemMeta) {
			this.gemMeta = gemMeta;
		}
		
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
        {
            Enchantment enchantment = (Enchantment)Enchantment.REGISTRY.getRandomObject(random);
            int i = MathHelper.getInt(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
            ItemStack itemstack = ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(enchantment, i));
            int j = 2 + random.nextInt(5 + i * 10) + 3 * i;

            if (enchantment.isTreasureEnchantment())
            {
                j *= 2;
            }

            if (j > 64)
            {
                j = 64;
            }

            recipeList.add(new MerchantRecipe(new ItemStack(Items.BOOK), new ItemStack(ProItems.GEM, j, gemMeta), itemstack));
        }
    }

	public static class ListEnchantedItemForGems implements EntityVillager.ITradeList
    {
        public ItemStack enchantedItemStack;
        public EntityVillager.PriceInfo priceInfo;
        private final int gemMeta;

        public ListEnchantedItemForGems(ItemStack enchantedItemStack, EntityVillager.PriceInfo priceInfo, int gemMeta)
        {
            this.enchantedItemStack = enchantedItemStack;
            this.priceInfo = priceInfo;
            this.gemMeta = gemMeta;
        }

        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
        {
            int i = 1;

            if (this.priceInfo != null)
            {
                i = this.priceInfo.getPrice(random);
            }

            ItemStack itemstack = new ItemStack(ProItems.GEM, i, gemMeta);
            ItemStack itemstack1 = EnchantmentHelper.addRandomEnchantment(random, new ItemStack(this.enchantedItemStack.getItem(), 1, this.enchantedItemStack.getMetadata()), 5 + random.nextInt(15), false);
            recipeList.add(new MerchantRecipe(itemstack, itemstack1));
        }
    }

	public static class ListItemForGems implements EntityVillager.ITradeList
    {
        public ItemStack itemToBuy;
        public EntityVillager.PriceInfo priceInfo;
        private final int gemMeta;

        public ListItemForGems(ItemStack stack, EntityVillager.PriceInfo priceInfo, int gemMeta)
        {
            this.itemToBuy = stack;
            this.priceInfo = priceInfo;
            this.gemMeta = gemMeta;
        }

        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
        {
            int i = 1;

            if (this.priceInfo != null)
            {
                i = this.priceInfo.getPrice(random);
            }

            ItemStack itemstack;
            ItemStack itemstack1;

            if (i < 0)
            {
                itemstack = new ItemStack(ProItems.GEM, 1, gemMeta);
                itemstack1 = new ItemStack(this.itemToBuy.getItem(), -i, this.itemToBuy.getMetadata());
            }
            else
            {
                itemstack = new ItemStack(ProItems.GEM, i, gemMeta);
                itemstack1 = new ItemStack(this.itemToBuy.getItem(), 1, this.itemToBuy.getMetadata());
            }

            recipeList.add(new MerchantRecipe(itemstack, itemstack1));
        }
    }

	public static class TreasureMapForGems implements EntityVillager.ITradeList
    {
        public EntityVillager.PriceInfo value;
        public String destination;
        public MapDecoration.Type destinationType;
        private final int gemMeta;

        public TreasureMapForGems(EntityVillager.PriceInfo value, String destination, MapDecoration.Type destinationType, int gemMeta)
        {
            this.value = value;
            this.destination = destination;
            this.destinationType = destinationType;
            this.gemMeta = gemMeta;
        }

        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
        {
            int i = this.value.getPrice(random);
            World world = merchant.getWorld();
            BlockPos blockpos = world.findNearestStructure(this.destination, merchant.getPos(), true);

            if (blockpos != null)
            {
                ItemStack itemstack = ItemMap.setupNewMap(world, (double)blockpos.getX(), (double)blockpos.getZ(), (byte)2, true, true);
                ItemMap.renderBiomePreviewMap(world, itemstack);
                MapData.addTargetDecoration(itemstack, blockpos, "+", this.destinationType);
                itemstack.setTranslatableName("filled_map." + this.destination.toLowerCase(Locale.ROOT));
                recipeList.add(new MerchantRecipe(new ItemStack(ProItems.GEM, i, gemMeta), new ItemStack(Items.COMPASS), itemstack));
            }
        }
    }
}
