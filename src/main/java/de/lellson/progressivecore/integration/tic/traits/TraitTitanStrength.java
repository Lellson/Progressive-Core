package de.lellson.progressivecore.integration.tic.traits;

import com.google.common.collect.Multimap;

import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import de.lellson.progressivecore.potion.ProPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitTitanStrength extends AbstractTrait {

	public static final String KEY_DELAY = Constants.prefix("tictsdelay");
	
	public TraitTitanStrength() {
		super("prog.titanstrength", 0xBBBBCC);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		
		if (!(entity instanceof EntityLivingBase) || !tool.hasTagCompound() || !isSelected || tool.getItemDamage() >= tool.getMaxDamage()-10)
			return;
		
		EntityLivingBase living = (EntityLivingBase) entity;
		
		if (!entity.isSneaking() && tool.getTagCompound().getInteger(KEY_DELAY) != 0)
		{
			tool.getTagCompound().setInteger(KEY_DELAY, 0);
			return;
		}
		
		if (living.isPotionActive(ProPotions.TITAN_STRENGTH))
			return;
		
		if (tool.getTagCompound().getInteger(KEY_DELAY) >= 20)
		{
			living.addPotionEffect(new PotionEffect(ProPotions.TITAN_STRENGTH, 200, 0, false, false));
			tool.damageItem(10, living);
			ClientHelper.playSound(entity, SoundEvents.ENTITY_ARROW_HIT_PLAYER, 0.5F, 0.5F);
			tool.getTagCompound().setInteger(KEY_DELAY, 0);
		}
		else if (entity.isSneaking())
		{
			tool.getTagCompound().setInteger(KEY_DELAY, tool.getTagCompound().getInteger(KEY_DELAY)+1);
			
			living.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 60, tool.getTagCompound().getInteger(KEY_DELAY)/5, false, false));
			living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, tool.getTagCompound().getInteger(KEY_DELAY)/5, false, false));
		}
	}
}
