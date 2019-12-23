package de.lellson.progressivecore.items.armor.handler;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;

public class ArmorHandlerMithril extends ArmorHandlerFantasy {
	
	@Override
	public double getExtraAttackSpeed() {
		return 0.1;
	}
	
	@Override
	public double getExtraMovementSpeed() {
		return 0.1;
	}
	
	@Override
	public void onUpdate(World world, EntityPlayer player, EntityEquipmentSlot slot, List<EntityEquipmentSlot> slots, boolean fullset) {
		
		if (fullset && slot == EntityEquipmentSlot.HEAD)
			player.fallDistance = 0;
	}
	
	@Override
	public void onJump(World world, EntityLivingBase entity, List<EntityEquipmentSlot> slots, LivingJumpEvent event, boolean fullset) {
		
		if (!fullset)
			return;
		
		entity.motionX *= 2.5;
		entity.motionY *= 1.5;
		entity.motionZ *= 2.5;
	}
	
	@Override
	public String[] getFullSetTooltip() {
		return new String[]{"Greatly increases agility", "Immunity to fall damage"};
	}
}
