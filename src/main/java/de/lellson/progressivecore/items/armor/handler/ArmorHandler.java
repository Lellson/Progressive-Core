package de.lellson.progressivecore.items.armor.handler;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.sets.ArmorSet;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class ArmorHandler {
	
	public static final String COOLDOWN = Constants.prefix("cooldown");
	
	public void onUpdate(World world, EntityPlayer player, EntityEquipmentSlot slot, List<EntityEquipmentSlot> slots, boolean fullset) {
	}

	public void onDamageTaken(World world, Entity entity, List<EntityEquipmentSlot> slots, LivingDamageEvent event, boolean fullset) {
	}
	
	public void onDamageDealt(World world, Entity entity, Entity immediateSource, EntityLivingBase target, List<EntityEquipmentSlot> slots, LivingDamageEvent event, boolean fullset) {
	}
	
	public void onJump(World world, EntityLivingBase entity, List<EntityEquipmentSlot> slots, LivingJumpEvent event, boolean fullset) {
	}

	public void onTooltip(List<String> lines, World world, ItemStack stack, EntityPlayerSP player, boolean fullset) {
	}

	public void addAttributes(Multimap<String, AttributeModifier> multimap, UUID uuid, EntityEquipmentSlot slot, ItemStack stack) {
	}
	
	public void setCooldown(Entity entity, int ticks) {
		entity.getEntityData().setInteger(COOLDOWN, entity.ticksExisted + ticks);
	}
	
	public void resetCooldown(Entity entity) {
		setCooldown(entity, 0);
	}
	
	public boolean isCooldown(Entity entity) {
		return entity.getEntityData().getInteger(COOLDOWN) > entity.ticksExisted;
	}
}
