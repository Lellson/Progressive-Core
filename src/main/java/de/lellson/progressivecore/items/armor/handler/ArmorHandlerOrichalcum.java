package de.lellson.progressivecore.items.armor.handler;

import java.util.List;

import de.lellson.progressivecore.misc.Constants;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class ArmorHandlerOrichalcum extends ArmorHandlerFantasy {
	
	public static final String TAG_LAST_HIT = Constants.prefix("lastHit");
	public static final String TAG_LAST_HIT_AMOUNT = Constants.prefix("lastHitAmount");
	
	@Override
	public void onDamageDealt(World world, Entity entity, Entity immediateSource, EntityLivingBase target, List<EntityEquipmentSlot> slots, LivingDamageEvent event, boolean fullset) {
		if (fullset)
		{
			NBTTagCompound data = entity.getEntityData();
			if (data.getString(TAG_LAST_HIT).equals(target.getUniqueID().toString()))
			{
				float extraDamage = data.getFloat(TAG_LAST_HIT_AMOUNT);
				event.setAmount(event.getAmount() * (1f + extraDamage));
				data.setFloat(TAG_LAST_HIT_AMOUNT, Math.min(extraDamage + 0.25f, 2.5f));
			}
			else
			{
				data.setString(TAG_LAST_HIT, target.getUniqueID().toString());
				data.setFloat(TAG_LAST_HIT_AMOUNT, 0.25f);
			}
			
			if (entity instanceof EntityLivingBase)
				((EntityLivingBase)entity).heal((float)Math.floor(event.getAmount()/10));
		}
	}

	@Override
	public double getExtraDamage() {
		return 0.15;
	}
	
	@Override
	public String[] getFullSetTooltip() {
		return new String[]{"Grants lifesteal", "Continuously attacking the", "same target increases damage"};
	}
}
