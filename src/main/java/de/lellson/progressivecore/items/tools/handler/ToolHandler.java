package de.lellson.progressivecore.items.tools.handler;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.world.BlockEvent;

public class ToolHandler {

	public void onUpdate(ItemStack stack, World world, EntityLivingBase entity) {
	}

	public void onRightClick(ItemStack stack, EnumHand hand, World world, EntityPlayer player) {
	}
	
	public void onLeftClick(ItemStack stack, EntityPlayer player, World world) {
	}
	
	public void onBlockHarvested(ItemStack stack, BlockEvent.HarvestDropsEvent event) {
	}
	
	public void onBlockBroken(ItemStack stack, BlockEvent.BreakEvent event) {
	}

	public void onEntityAttacked(ItemStack stack, EntityLivingBase attacker, EntityLivingBase target, LivingDamageEvent event) {
	}
	
	public void onEntityAttack(ItemStack stack, EntityLivingBase attacker, EntityLivingBase target, LivingHurtEvent event) {
	}

	public void onTooltip(List<String> lines, World world, ItemStack stack, EntityPlayerSP player) {
	}

	public void onEntityKnockback(ItemStack stack, EntityLivingBase attacker, EntityLivingBase target, LivingKnockBackEvent event) {
	}
	
	public void addAttributes(Multimap<String, AttributeModifier> multimap, UUID uuid, EntityEquipmentSlot slot, ItemStack stack) {
	}

	public boolean canUse(ItemStack stack) {
		return false;
	}

	public void onUsingTick(ItemStack stack, EntityLivingBase entity, int count) {
	}

	public void onUsingStopped(ItemStack stack, World world, EntityLivingBase entity, int count) {
	}

	public boolean canStartUsing(ItemStack stack, EnumHand hand, World world, EntityPlayer player) {
		return canUse(stack);
	}
}
