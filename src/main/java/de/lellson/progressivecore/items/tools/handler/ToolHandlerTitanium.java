package de.lellson.progressivecore.items.tools.handler;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import de.lellson.progressivecore.potion.ProPotions;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ToolHandlerTitanium extends ToolHandler {
	
	@Override
	public boolean canUse(ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean canStartUsing(ItemStack stack, EnumHand hand, World world, EntityPlayer player) {
		return !player.isPotionActive(ProPotions.TITAN_STRENGTH);
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entity, int count) {
		
		if (count >= 20)
		{
			ClientHelper.spawnParticle(entity, EnumParticleTypes.SMOKE_NORMAL, 0.125f, 5f);
			entity.stopActiveHand();
		}
	}
	
	@Override
	public void onUsingStopped(ItemStack stack, World world, EntityLivingBase entity, int count) {
		
		if (count >= 20)
		{
			entity.addPotionEffect(new PotionEffect(ProPotions.TITAN_STRENGTH, 200, 0, false, false));
			stack.damageItem(10, entity);
			ClientHelper.playSound(entity, SoundEvents.ENTITY_ARROW_HIT_PLAYER, 0.5F, 0.5F);
		}
	}
	
	@Override
	public void addAttributes(Multimap<String, AttributeModifier> multimap, UUID uuid, EntityEquipmentSlot slot, ItemStack stack) {
		multimap.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(uuid, Constants.prefix("titanium_reach"), 1, 0));
	}
	
	@Override
	public void onTooltip(List<String> lines, World world, ItemStack stack, EntityPlayerSP player) {
		lines.add("Hold rightclick to gain superpowers");
	}
}
