package de.lellson.progressivecore.integration.baubles.powers;

import de.lellson.progressivecore.misc.helper.MiscHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class Power {
	
	private AbstractPower power;
	private int level;
	
	public Power(AbstractPower power, int level) {
		this.power = power;
		this.level = level;
	}
	
	public AbstractPower getPower() {
		return power;
	}
	
	public int getLevel() {
		return level;
	}
	
	public Power increaseLevel() {
		level++;
		return this;
	}
	
	public NBTTagCompound toNBT() {
		return power.toNBT(this);
	}
	
	public static Power fromNBT(NBTTagCompound nbt) {
		return AbstractPower.fromNBT(nbt);
	}

	public String getFormattedString() {
		if (power == null)
			return "";
		return power.getColor() + power.getLocalizedName() + " " + MiscHelper.getRomanNumber(level);
	}

	public int onXpDrop(ItemStack stack, EntityPlayer player, int xp) {
		return power.onXpDrop(stack, level, player, xp);
	}

	public void onTaken(ItemStack stack, EntityPlayer target, Entity attacker, LivingDamageEvent event) {
		power.onTaken(stack, level, target, attacker, event);
	}

	public void onAttack(ItemStack stack, EntityPlayer attacker, EntityLivingBase target, LivingDamageEvent event) {
		power.onAttack(stack, level, attacker, target, event);
	}
}
