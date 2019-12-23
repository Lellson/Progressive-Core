package de.lellson.progressivecore;

import static de.lellson.progressivecore.misc.Constants.*;

import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.entity.ProEntities;
import de.lellson.progressivecore.integration.ProIntegration;
import de.lellson.progressivecore.integration.tic.ProTiC;
import de.lellson.progressivecore.inv.GuiHandler;
import de.lellson.progressivecore.inv.recipe.PolisherEntry;
import de.lellson.progressivecore.inv.recipe.SmelterEntry;
import de.lellson.progressivecore.items.ItemPro;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.misc.EventSubscriber;
import de.lellson.progressivecore.misc.ProOreDictionary;
import de.lellson.progressivecore.misc.ProRecipes;
import de.lellson.progressivecore.misc.ProSounds;
import de.lellson.progressivecore.misc.ProTab;
import de.lellson.progressivecore.misc.ProVillagerTrades;
import de.lellson.progressivecore.misc.config.ProConfig;
import de.lellson.progressivecore.misc.network.MessagesPro;
import de.lellson.progressivecore.potion.ProPotions;
import de.lellson.progressivecore.sets.Sets;
import de.lellson.progressivecore.world.ProWorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION)
public class ProgressiveCore {
	
	@Instance(MOD_ID)
	public static ProgressiveCore instance;
	
	@SidedProxy(serverSide = PROXY_COMMON, clientSide = PROXY_CLIENT)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ProConfig.init(event);
		MessagesPro.init();
		ProSounds.init();
		ProEntities.init();
		ProItems.init();
		ProBlocks.init();
		Sets.init();
		ProWorldGenerator.init();
		ProRecipes.init();
		ProPotions.init();
		EventSubscriber.init();
		GuiHandler.init();
		ProTab.init();
		ProOreDictionary.init();
		ProIntegration.preInit();
		ProConfig.save();
		
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		ProVillagerTrades.addTrades();
		ProIntegration.init();
		
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		SmelterEntry.initRecipes();
		PolisherEntry.initRecipes();
		ProIntegration.postInit();
		
		proxy.postInit(event);
	}
}
