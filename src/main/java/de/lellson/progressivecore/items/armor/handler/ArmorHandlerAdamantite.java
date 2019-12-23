package de.lellson.progressivecore.items.armor.handler;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class ArmorHandlerAdamantite extends ArmorHandlerFantasy {
	
	@Override
	public double getExtraKnockback() {
		return 0.25;
	}
	
	@Override
	public double getExtraHealth() {
		return 4;
	}
	
	@Override
	public double getExtraMovementSpeed() {
		return 0;
	}
	
	@Override
	public void onUpdate(World world, EntityPlayer player, EntityEquipmentSlot slot, List<EntityEquipmentSlot> slots, boolean fullset) {
		if (slot == EntityEquipmentSlot.HEAD && fullset && player.ticksExisted % 20 == 1)
			player.heal(1);
	}
	
	@Override
	public void onDamageTaken(World world, Entity entity, List<EntityEquipmentSlot> slots, LivingDamageEvent event, boolean fullset) {
		if (fullset && event.getAmount() <= 1)
			event.setCanceled(true);
	}
	
	@Override
	public String[] getFullSetTooltip() {
		return new String[]{"Greatly increases life regeneration", "Immunity to low damage"};
	}
}
