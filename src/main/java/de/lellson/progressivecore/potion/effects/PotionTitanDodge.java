package de.lellson.progressivecore.potion.effects;

import de.lellson.progressivecore.misc.helper.ClientHelper;
import de.lellson.progressivecore.potion.PotionPro;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class PotionTitanDodge extends PotionPro {

	public PotionTitanDodge() {
		super("titan_dodge", false, 0x333355);
	}
	
	@Override
	public void onDamageTaken(LivingDamageEvent event, EntityLivingBase entity, int amplifier, int duration) {
		event.setCanceled(true);
	}
	
	@Override
	public void onKnockback(LivingKnockBackEvent event, EntityLivingBase entity, int amplifier, int duration) {
		event.setCanceled(true);
		
		entity.removePotionEffect(this);
		
		ClientHelper.startPacket();
		ClientHelper.playSound(entity, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 0.7f, 0.6F);
		ClientHelper.spawnParticle(entity, EnumParticleTypes.SMOKE_NORMAL, 0.3F, 15);
		ClientHelper.spawnParticle(entity, EnumParticleTypes.SMOKE_LARGE, 0.4F, 15);
		ClientHelper.stopPacket();
	}
}
