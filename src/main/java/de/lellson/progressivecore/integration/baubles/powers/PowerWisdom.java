package de.lellson.progressivecore.integration.baubles.powers;

import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class PowerWisdom extends AbstractPower {

	private final int xpMultiplier;
	
	public PowerWisdom() {
		super("wisdom", 2, TextFormatting.GREEN);
		xpMultiplier = ProConfig.cfg.getInt("wisdomMultiplier", CATEGORY, 20, 1, Short.MAX_VALUE, "Amount of extra xp entities drop per Wisdom level (in percent)");
	}

	@Override
	public int onXpDrop(ItemStack stack, int level, EntityPlayer player, int xp) {
		return Math.round(xp * (1 + level * (xpMultiplier/100.0f)));
	}
	
	@Override
	public String getDescription(Power power) {
		return "+" + (power.getLevel()*xpMultiplier) + "% XP Dropped";
	}
}
