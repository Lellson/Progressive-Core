package de.lellson.progressivecore.integration.baubles.powers;

import java.util.UUID;

import com.google.common.collect.Multimap;

import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.config.ProConfig;
import de.lellson.progressivecore.misc.helper.MiscHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class AbstractPower {
	
	public static final String CATEGORY = "accessories_powers";
	
	public static final String POWER_NAME = Constants.prefix("pow_name");
	public static final String POWER_LEVEL = Constants.prefix("pow_level");
	
	private final String name;
	private final int maxLevel;
	private final TextFormatting color;
	
	public AbstractPower(String name, int maxLevel, TextFormatting color) {
		this.name = name;
		this.maxLevel = ProConfig.cfg.getInt(name.replace("_", "") + "Level", CATEGORY, maxLevel, 1, Short.MAX_VALUE, "Max level for the " + getLocalizedName() + " accessory power");
		this.color = color;
		
		if (ProConfig.cfg.getBoolean(name.replace("_", "") + "Enabled", CATEGORY, true, "Set to false to disable the " + getLocalizedName() + " accessory power"))
			Powers.POWERS.add(this);
	}
	
	public String getName() {
		return name;
	}
	
	public String getLocalizedName() {
		return I18n.translateToLocal("power." + name);
	}
	
	public TextFormatting getColor() {
		return color;
	}

	public boolean isMaxLevel(Power power) {
		return power.getLevel() >= maxLevel;
	}
	
	public String getDescription(Power power) {
		return "";
	}

	public void modifyAttributeModifiers(ItemStack stack, EntityLivingBase player, int level, UUID uuid, Multimap<String, AttributeModifier> map) {
	}

	public NBTTagCompound toNBT(Power power) {
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString(POWER_NAME, name);
		nbt.setInteger(POWER_LEVEL, power.getLevel());
		return nbt;
	}

	public static Power fromNBT(NBTTagCompound nbt) {
		return new Power(Powers.getByName(nbt.getString(POWER_NAME)), nbt.getInteger(POWER_LEVEL));
	}

	public int onXpDrop(ItemStack stack, int level, EntityPlayer player, int xp) {
		return xp;
	}

	public void onTaken(ItemStack stack, int level, EntityPlayer target, Entity attacker, LivingDamageEvent event) {
	}

	public void onAttack(ItemStack stack, int level, EntityPlayer attacker, EntityLivingBase target, LivingDamageEvent event) {
	}
}
