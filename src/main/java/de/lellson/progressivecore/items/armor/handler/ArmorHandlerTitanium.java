package de.lellson.progressivecore.items.armor.handler;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import de.lellson.progressivecore.potion.ProPotions;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class ArmorHandlerTitanium extends ArmorHandler {
	
	@Override
	public void onUpdate(World world, EntityPlayer player, EntityEquipmentSlot slot, List<EntityEquipmentSlot> slots, boolean fullset) {
		
		if (fullset && slot == EntityEquipmentSlot.HEAD && (player.motionX > 0 || player.motionY > 0 || player.motionZ > 0)) 
		{
			ClientHelper.spawnParticle(player, EnumParticleTypes.SMOKE_NORMAL, 0.125f, 0.5f);
		}
	}
	
	@Override
	public void addAttributes(Multimap<String, AttributeModifier> multimap, UUID uuid, EntityEquipmentSlot slot, ItemStack stack) {
		multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(uuid, Constants.prefix("titanium_knockback"), 0.05, 0));
		multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuid, Constants.prefix("titanium_speed"), 0.05, 1));
		multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(uuid, Constants.prefix("titanium_damage"), 0.05, 1));
	}
	
	@Override
	public void onDamageDealt(World world, Entity entity, Entity immediateSource, EntityLivingBase target, List<EntityEquipmentSlot> slots, LivingDamageEvent event, boolean fullset) {

		if (entity instanceof EntityLivingBase && fullset && !isCooldown(entity))
		{
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(ProPotions.TITAN_DODGE, 200, 0, false, false));
			setCooldown(entity, 60);
		}
	}
	
	@Override
	public void onTooltip(List<String> lines, World world, ItemStack stack, EntityPlayerSP player, boolean fullset) {

		if (fullset)
		{
			lines.add(TextFormatting.GOLD + "Set Bonus: Briefly become invulnerable");
			lines.add(TextFormatting.GOLD + "after striking an enemy");
		}
	}
}
