package de.lellson.progressivecore.items.tools.handler;

import de.lellson.progressivecore.entity.projectile.FantasyProjectile;
import de.lellson.progressivecore.entity.projectile.MithrilProjectile;
import de.lellson.progressivecore.entity.projectile.OrichalcumProjectile;
import de.lellson.progressivecore.items.tools.ITool;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ToolHandlerOrichalcum extends ToolHandlerFantasy {

	@Override
	public void shoot(World world, EntityLivingBase entity, ItemStack stack) {
		
		for (int i = 0; i < 5; i++)
			summonProjectile(world, entity, stack);
		stack.damageItem(1, entity);
		ClientHelper.playSound(entity, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 0.5F, 0.5F);
	}
	
	@Override
	public void summonProjectile(World world, EntityLivingBase entity, ItemStack stack) {
		
		OrichalcumProjectile projectile = new OrichalcumProjectile(world, entity, stack);
		projectile.shoot(entity, entity.rotationPitch, entity.rotationYaw, 6);
		if (!world.isRemote)
			world.spawnEntity(projectile);
	}
	
	@Override
	public String getProjectileDescription() {
		return "Left-Click to fire a burst of 5 inaccurate projectiles";
	}
}
