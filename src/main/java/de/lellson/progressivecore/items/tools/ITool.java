package de.lellson.progressivecore.items.tools;

import de.lellson.progressivecore.blocks.BlockPro.Tool;
import de.lellson.progressivecore.items.tools.handler.ToolHandler;
import de.lellson.progressivecore.misc.network.MessagesPro;
import de.lellson.progressivecore.misc.network.message.LeftClickMessage;
import de.lellson.progressivecore.sets.ToolSet;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public interface ITool {
	
	public ToolHandler getHandler();
	
	public default boolean hasHandler() {
		return getHandler() != null;
	}
	
	public boolean isWeapon();
	
	@SubscribeEvent
	public static void leftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
		
		ItemStack stack = event.getItemStack();
		if (stack.getItem() instanceof ITool)
		{
			handleLeftClick(event.getWorld(), event.getEntityPlayer(), stack);
			MessagesPro.WRAPPER.sendToServer(new LeftClickMessage());
		}
	}
	
	@SubscribeEvent
	public static void leftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
		
		ItemStack stack = event.getItemStack();
		if (stack.getItem() instanceof ITool)
		{
			handleLeftClick(event.getWorld(), event.getEntityPlayer(), stack);
			MessagesPro.WRAPPER.sendToServer(new LeftClickMessage());
		}
	}
	
	public static void handleLeftClick(World world, EntityPlayer player, ItemStack stack) {
		if (stack.getItem() instanceof ITool)
		{
			ITool tool = (ITool)stack.getItem();
			if (tool.hasHandler())
				tool.getHandler().onLeftClick(stack, player, world);
		}
	}
	
	@SubscribeEvent
	public static void blockBreak(BlockEvent.HarvestDropsEvent event) {
		
		if (event.getHarvester() != null)
		{
			ItemStack stack = event.getHarvester().getHeldItemMainhand();
			if (stack.getItem() instanceof ITool)
			{
				ITool tool = (ITool)stack.getItem();
				if (tool.hasHandler())
					tool.getHandler().onBlockHarvested(stack, event);
			}
		}
	}
	
	@SubscribeEvent
	public static void blockBreak(BlockEvent.BreakEvent event) {
		
		if (event.getPlayer() != null)
		{
			ItemStack stack = event.getPlayer().getHeldItemMainhand();
			if (stack.getItem() instanceof ITool)
			{
				ITool tool = (ITool)stack.getItem();
				if (tool.hasHandler())
					tool.getHandler().onBlockBroken(stack, event);
			}
		}
	}
	
	@SubscribeEvent
	public static void damageEntity(LivingDamageEvent event) {
		
		if (event.getSource().getTrueSource() instanceof EntityLivingBase)
		{
			EntityLivingBase player = (EntityLivingBase) event.getSource().getTrueSource();
			ItemStack stack = player.getHeldItemMainhand();
			
			if (stack.getItem() instanceof ITool)
			{
				ITool tool = (ITool)stack.getItem();
				if (tool.hasHandler())
					tool.getHandler().onEntityAttacked(stack, player, event.getEntityLiving(), event);
			}
		}
	}
	
	@SubscribeEvent
	public static void damageEntity(LivingHurtEvent event) {
		
		if (event.getSource().getTrueSource() instanceof EntityLivingBase)
		{
			EntityLivingBase player = (EntityLivingBase) event.getSource().getTrueSource();
			ItemStack stack = player.getHeldItemMainhand();
			
			if (stack.getItem() instanceof ITool)
			{
				ITool tool = (ITool)stack.getItem();
				if (tool.hasHandler())
					tool.getHandler().onEntityAttack(stack, player, event.getEntityLiving(), event);
			}
		}
	}
	
	@SubscribeEvent
	public static void onKnockback(LivingKnockBackEvent event) {
		
		if (event.getAttacker() instanceof EntityLivingBase)
		{
			EntityLivingBase player = (EntityLivingBase) event.getAttacker();
			ItemStack stack = player.getHeldItemMainhand();
			
			if (stack.getItem() instanceof ITool)
			{
				ITool tool = (ITool)stack.getItem();
				if (tool.hasHandler())
					tool.getHandler().onEntityKnockback(stack, player, event.getEntityLiving(), event);
			}
		}
	}
}
