package de.lellson.progressivecore.items.tools.handler;

import java.util.List;
import java.util.Random;

import de.lellson.progressivecore.misc.helper.MiscHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ToolHandlerMagmaticBlade extends ToolHandlerHellsteel {

	@Override
	public void onTooltip(List<String> lines, World world, ItemStack stack, EntityPlayerSP player) {
		lines.add("Sets targets on fire");
		lines.add("Hold rightclick to burn them all");
	}
	
	@Override
	public boolean canUse(ItemStack stack) {
		return true;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		
		Vec3d vec = player.getLookVec();
		Random rnd = player.world.rand;
		double lookX = vec.x / 10;
		double lookY = vec.y / 10;
		double lookZ = vec.z / 10;
		for (int i = 0; i < 100; i++)
		{
			double x = player.posX + lookX*i + rnd.nextDouble()*1.5-0.75;
			double y = player.posY + lookY*i + rnd.nextDouble()*1.5;
			double z = player.posZ + lookZ*i + rnd.nextDouble()*1.5-0.75;
			if (player.world.getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.WATER)
				break;
			player.world.spawnParticle(EnumParticleTypes.FLAME, x, y, z, lookX*5, lookY*5, lookZ*5);
			player.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, lookX*7, lookY*7, lookZ*7);
			for (EntityLivingBase entity : player.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x-1, y-1, z-1, x+1, y+1, z+1)))
			{
				if (entity != player && player.canEntityBeSeen(entity)) 
					entity.setFire(4);
			}
		}
		
		if (count % 20 == 0)
		{
			stack.damageItem(1, player);
			player.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 0.8F, 1.0F);
		}
		MiscHelper.knockback(player, player, -0.02, 0, -0.02);
	}
	
	@Override
	public void onUsingStopped(ItemStack stack, World world, EntityLivingBase entity, int count) {
		entity.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.8F, 1.0F);
		stack.damageItem(1, entity);
	}
}
