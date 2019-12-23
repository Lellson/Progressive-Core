package de.lellson.progressivecore.items.tools.handler;

import de.lellson.progressivecore.entity.projectile.AdamantiteProjectile;
import de.lellson.progressivecore.entity.projectile.OrichalcumProjectile;
import de.lellson.progressivecore.items.tools.ITool;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ToolHandlerAdamantite extends ToolHandlerFantasy {
	
	@Override
	public void shoot(World world, EntityLivingBase entity, ItemStack stack) {
		
		summonProjectile(world, entity, stack);
		stack.damageItem(1, entity);
		ClientHelper.playSound(entity, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 0.5F, 0.5F);
	}
	
	@Override
	public void summonProjectile(World world, EntityLivingBase entity, ItemStack stack) {
		
		AdamantiteProjectile projectile = new AdamantiteProjectile(world, entity, stack);
		projectile.shoot(entity, entity.rotationPitch, entity.rotationYaw, 0);
		if (!world.isRemote)
			world.spawnEntity(projectile);
	}

	@Override
	public String getProjectileDescription() {
		return "Left-Click to fire a powerful projectile";
	}
}
