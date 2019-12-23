package de.lellson.progressivecore.items.armor.handler;

import java.util.List;

import de.lellson.progressivecore.misc.helper.ClientHelper;
import de.lellson.progressivecore.sets.ArmorSet;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class ArmorHandlerHellsteel extends ArmorHandler {

	@Override
	public void onUpdate(World world, EntityPlayer player, EntityEquipmentSlot slot, List<EntityEquipmentSlot> slots, boolean fullset) {
		
		if (fullset && slot == EntityEquipmentSlot.HEAD && (player.motionX > 0 || player.motionY > 0 || player.motionZ > 0)) 
		{
			ClientHelper.spawnParticle(player, EnumParticleTypes.SMOKE_NORMAL, 0.1f, 0.3f);
			ClientHelper.spawnParticle(player, EnumParticleTypes.FLAME, 0.08f, 0.3f);
		}
	}

	@Override
	public void onDamageTaken(World world, Entity entity, List<EntityEquipmentSlot> slots, LivingDamageEvent event, boolean fullset) {
		
		if (event.getSource().isFireDamage())
		{
			if (fullset) 
			{
				event.setCanceled(true);
			}
			else
				event.setAmount(event.getAmount() * (1 - slots.size() * 0.25f));
		}
		
		if (fullset)
		{
			Entity attacker = event.getSource().getTrueSource();
			
			if (attacker != null)
			{
				if (!attacker.isBurning())
					ClientHelper.playSound(attacker, SoundEvents.ITEM_FLINTANDSTEEL_USE, 0.5f, 1.2f);
				
				attacker.setFire(6);
			}
		}
	}

	@Override
	public void onTooltip(List<String> lines, World world, ItemStack stack, EntityPlayerSP player, boolean fullset) {
		
		lines.add("Reduces fire damage taken by 25%");
		
		if (fullset)
			lines.add(TextFormatting.GOLD + "Set Bonus: Ignites attackers");
	}
}
