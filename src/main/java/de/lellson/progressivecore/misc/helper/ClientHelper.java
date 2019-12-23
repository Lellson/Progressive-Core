package de.lellson.progressivecore.misc.helper;

import java.util.Random;

import de.lellson.progressivecore.misc.network.MessagesPro;
import de.lellson.progressivecore.misc.network.message.ParticleMessage;
import de.lellson.progressivecore.misc.network.message.SoundMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ClientHelper {
	
	public static boolean packet = false;
	public static int[] particlePars = new int[0];
	
	public static void spawnParticle(World world, double posX, double posY, double posZ, EnumParticleTypes type, float spread, float amount) {
		
		if (packet && !world.isRemote)
		{
			EntityPlayer player = world.getClosestPlayer(posX, posY, posZ, -1, true);
			if (player != null)
			{
				MessagesPro.WRAPPER.sendTo(new ParticleMessage(posX, posY, posZ, type, spread, amount), (EntityPlayerMP)player);
			}
		}
		
		Random rnd = world.rand; 
		
		if (amount < 1f && Math.random() > amount)
			return;
			
		for (int i = 0; i < Math.ceil(amount); i++) 
		{
			double moveX = (rnd.nextDouble() - 0.5D) * 2.0D * spread;
			double moveY = (rnd.nextDouble() - 0.5D) * spread;
			double moveZ = (rnd.nextDouble() - 0.5D) * 2.0D * spread;
			world.spawnParticle(type, posX, posY, posZ, moveX, moveY, moveZ, particlePars);
		}
		
		if (world.isRemote)
			resetParticlePars();
	}
	
	public static void spawnParticle(World world, double posX, double posY, double posZ, EnumParticleTypes type, float amount) {
		spawnParticle(world, posX, posY, posZ, type, 0.1f, amount);
	}
	
	public static void spawnParticle(World world, double posX, double posY, double posZ, EnumParticleTypes type) {
		spawnParticle(world, posX, posY, posZ, type, 1f);
	}
	
	public static void spawnParticle(Entity entity, EnumParticleTypes type, float spread, float amount) {
		
		Random rnd = entity.world.rand; 
		spawnParticle(entity.world, entity.posX + (rnd.nextDouble() - 0.5D) * (double)entity.width, entity.posY + rnd.nextDouble() * (double)entity.height - 0.25D, entity.posZ + (rnd.nextDouble() - 0.5D) * (double)entity.width, type, spread, amount);
	}
	
	public static void spawnParticle(Entity entity, EnumParticleTypes type, float amount) {
		spawnParticle(entity, type, 0.1f, amount);
	}
	
	public static void spawnParticle(Entity entity, EnumParticleTypes type) {
		spawnParticle(entity, type, 1f);
	}
	
	public static void playSound(World world, double posX, double posY, double posZ, SoundCategory category, SoundEvent event, float volume, float pitch) {
		
		if (packet && !world.isRemote)
		{
			EntityPlayer player = world.getClosestPlayer(posX, posY, posZ, -1, true);
			if (player != null)
			{
				MessagesPro.WRAPPER.sendTo(new SoundMessage(posX, posY, posZ, category, event, volume, pitch), (EntityPlayerMP)player);
			}
		}
		
		world.playSound(null, posX, posY, posZ, event, category, volume, pitch);
	}
	
	public static void playSound(Entity entity, SoundEvent event, float volume, float pitch) {
    	if (!entity.isSilent())
        {
    		playSound(entity.world, entity.posX, entity.posY, entity.posZ, entity.getSoundCategory(), event, volume, pitch);
        }
	}
	
	public static void playSound(Entity entity, SoundEvent event) {
		playSound(entity, event, 1.0F, 1.0F);
	}
	
	public static void setParticlePars(int... pars) {
		particlePars = pars;
	}
	
	public static void resetParticlePars() {
		particlePars = new int[0];
	}

	public static void startPacket() {
		packet = true;
	}
	
	public static void stopPacket() {
		packet = false;
	}
}
