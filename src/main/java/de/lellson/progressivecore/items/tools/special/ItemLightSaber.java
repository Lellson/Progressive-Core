package de.lellson.progressivecore.items.tools.special;

import de.lellson.progressivecore.items.tools.ItemSwordPro;
import de.lellson.progressivecore.items.tools.handler.ToolHandler;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.ProSounds;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemLightSaber extends ItemSwordPro {
	
	private static final ToolMaterial LIGHT_SABER_MATERIAL = EnumHelper.addToolMaterial(Constants.prefix("lightsaber"), 4, -1, 12, 10, 20);
	
	public ItemLightSaber(String color) {
		super(LIGHT_SABER_MATERIAL, new ToolHandlerLightSaber(), "light_saber_" + color);
	}

	public static class ToolHandlerLightSaber extends ToolHandler {
		
		@Override
		public void onLeftClick(ItemStack stack, EntityPlayer player, World world) {
			lightSaberEffect(player, null, 0);
		}
		
		@Override
		public void onEntityAttack(ItemStack stack, EntityLivingBase attacker, EntityLivingBase target, LivingHurtEvent event) {
			event.getSource().setDamageBypassesArmor();
			lightSaberEffect(attacker, target, event.getAmount());
		}
		
		private void lightSaberEffect(Entity entity, Entity target, float damage) {
			
			ClientHelper.startPacket();
			if (target != null && target instanceof EntityVillager && ((EntityVillager)target).getHealth() <= damage)
				ClientHelper.playSound(entity, ProSounds.SCREAM, 0.6f, 1f);
			else
				ClientHelper.playSound(entity, ProSounds.LIGHT_SABER, 0.7f, 1.25f - entity.world.rand.nextFloat()*0.5f);
			ClientHelper.stopPacket();
		}
	}
}
