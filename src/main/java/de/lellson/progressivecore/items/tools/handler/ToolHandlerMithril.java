package de.lellson.progressivecore.items.tools.handler;

import de.lellson.progressivecore.entity.projectile.FantasyProjectile;
import de.lellson.progressivecore.entity.projectile.MithrilProjectile;
import de.lellson.progressivecore.items.tools.ITool;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import de.lellson.progressivecore.potion.ProPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class ToolHandlerMithril extends ToolHandlerFantasy {
	
	private static final String TAG_FIRE = Constants.prefix("fire");
	
	@Override
	public void shoot(World world, EntityLivingBase entity, ItemStack stack) {
		
		if (!stack.hasTagCompound())
			return;
		
		stack.getTagCompound().setInteger(TAG_FIRE, 9);
		stack.damageItem(1, entity);
	}
	
	@Override
	public void summonProjectile(World world, EntityLivingBase entity, ItemStack stack) {
		
		MithrilProjectile projectile = new MithrilProjectile(world, entity, stack);
		projectile.shoot(entity, entity.rotationPitch, entity.rotationYaw, 0);
		if (!world.isRemote)
			world.spawnEntity(projectile);
		ClientHelper.playSound(entity, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 0.4F, 0.75F);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, EntityLivingBase entity) {
		
		super.onUpdate(stack, world, entity);
		
		int fire = stack.getTagCompound().getInteger(TAG_FIRE);
		if (fire > 0)
		{
			if (fire % 3 == 0)
				summonProjectile(world, entity, stack);
			stack.getTagCompound().setInteger(TAG_FIRE, fire-1);
		}
	}
	
	@Override
	public String getProjectileDescription() {
		return "Left-Click to fire a burst of 3 tight projectiles";
	}
}
