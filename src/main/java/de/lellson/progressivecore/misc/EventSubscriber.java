package de.lellson.progressivecore.misc;

import de.lellson.progressivecore.items.armor.ItemArmorPro;
import de.lellson.progressivecore.items.tools.ITool;
import de.lellson.progressivecore.potion.PotionPro;
import net.minecraftforge.common.MinecraftForge;

public class EventSubscriber {

	public static void init() {
		MinecraftForge.EVENT_BUS.register(PotionPro.class);
		MinecraftForge.EVENT_BUS.register(ItemArmorPro.class);
		MinecraftForge.EVENT_BUS.register(ITool.class);
	}
}
