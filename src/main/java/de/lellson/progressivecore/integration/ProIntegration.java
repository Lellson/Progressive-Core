package de.lellson.progressivecore.integration;

import de.lellson.progressivecore.integration.baubles.ProBaubles;
import de.lellson.progressivecore.integration.jei.ProJei;
import de.lellson.progressivecore.integration.tic.ProTiC;
import de.lellson.progressivecore.items.ProItems;
import net.minecraft.item.Item;
import net.minecraft.util.MinecraftError;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class ProIntegration {

	public static enum Mod {
		
		JEI("jei"),
		BAUBLES("baubles"),
		TCONSTRUCT("tconstruct");
		
		private String modId;
		
		private Mod(String modId) {
			this.modId = modId;
		}
		
		public String getModId() {
			return modId;
		}
		
		public boolean isLoaded() {
			
			for (ModContainer mod : Loader.instance().getActiveModList())
				if (mod.getModId().equals(getModId()))
					return true;
			
			return false; 
		}
		
		public void preInit() {
			
			if (!isLoaded())
				return;
			
			switch(this)
			{
				case TCONSTRUCT: ProTiC.preInit(); break;
				case BAUBLES: ProBaubles.preInit(); break;
				case JEI: ProJei.preInit(); break;
			}
		}

		public void init() {
			
			if (!isLoaded())
				return;
			
			switch(this)
			{
				case TCONSTRUCT: ProTiC.init(); break;
				case BAUBLES: ProBaubles.init(); break;
				case JEI: ProJei.init(); break;
			}
		}
		
		public void postInit() {
			
			if (!isLoaded())
				return;
			
			switch(this)
			{
				case TCONSTRUCT: ProTiC.postInit(); break;
				case BAUBLES: ProBaubles.postInit(); break;
				case JEI: ProJei.postInit(); break;
			}
		}
	}
	
	public static void preInit() {
		
		for (Mod mod : Mod.values())
		{
			if (mod.isLoaded());
			{
				mod.preInit();
			}
		}
	}
	
	public static void init() {
		
		for (Mod mod : Mod.values())
		{
			if (mod.isLoaded());
			{
				mod.init();
			}
		}
	}
	
	public static void postInit() {
		
		for (Mod mod : Mod.values())
		{
			if (mod.isLoaded());
			{
				mod.postInit();
			}
		}
	}

	public static Item getAccessoryIconItem() {
		return Mod.BAUBLES.isLoaded() ? ProBaubles.GOLD_RING : ProItems.CUT_GEM;
	}
}
