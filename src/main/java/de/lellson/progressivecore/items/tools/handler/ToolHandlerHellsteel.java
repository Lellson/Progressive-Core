package de.lellson.progressivecore.items.tools.handler;

import java.util.List;
import java.util.ListIterator;

import de.lellson.progressivecore.items.tools.ItemSwordPro;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class ToolHandlerHellsteel extends ToolHandler {

	@Override
	public void onBlockHarvested(ItemStack stack, HarvestDropsEvent event) {

		ListIterator<ItemStack> iter = event.getDrops().listIterator();
		boolean smelt = false;
		
		while(iter.hasNext())
		{
			ItemStack drop = iter.next();
			ItemStack smelted = FurnaceRecipes.instance().getSmeltingResult(drop);
			
			if (!smelted.isEmpty()) 
			{
				smelt = true;
				smelted = smelted.copy();
				smelted.setCount(drop.getCount());
				
				int fortune = event.getFortuneLevel();
				if (fortune > 0 && !(smelted.getItem() instanceof ItemBlock)) {
					smelted.setCount(smelted.getCount() * event.getWorld().rand.nextInt(fortune + 1) + 1);
				}
				
				iter.set(smelted);

				float xp = FurnaceRecipes.instance().getSmeltingExperience(smelted);
				
				if (xp < 1 && Math.random() < xp) {
					xp += 1f;
				}
				
				if (xp >= 1f) {
					event.getState().getBlock().dropXpOnBlockBreak(event.getWorld(), event.getPos(), (int) xp);
				}
			}
		}
		
		if (!smelt)
			return;
		
		ClientHelper.startPacket();
		ClientHelper.playSound(event.getHarvester(), SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, 0.7F, 1.0F);
		ClientHelper.spawnParticle(event.getWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.7, event.getPos().getZ() + 0.5, EnumParticleTypes.FLAME, 0.2f, 5);
		ClientHelper.spawnParticle(event.getWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.7, event.getPos().getZ() + 0.5, EnumParticleTypes.SMOKE_NORMAL, 0.225f, 5);
		ClientHelper.stopPacket();
	}

	@Override
	public void onEntityAttacked(ItemStack stack, EntityLivingBase attacker, EntityLivingBase target, LivingDamageEvent event) {
		target.setFire(8 + EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) * 4);
	}

	@Override
	public void onTooltip(List<String> lines, World world, ItemStack stack, EntityPlayerSP player) {
		lines.add("Sets targets on fire");
		lines.add("Automatically smelts mined blocks");
	}
}
