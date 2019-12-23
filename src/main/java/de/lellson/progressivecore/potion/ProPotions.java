package de.lellson.progressivecore.potion;

import de.lellson.progressivecore.potion.effects.PotionTitanDodge;
import de.lellson.progressivecore.potion.effects.PotionTitanStrength;
import net.minecraft.potion.Potion;

public class ProPotions {

	public static final PotionPro TITAN_DODGE = new PotionTitanDodge();
	public static final PotionPro TITAN_STRENGTH = new PotionTitanStrength();
	
	public static void init() {}
}
