package de.lellson.progressivecore.integration.baubles;

import java.util.ArrayList;
import java.util.List;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import de.lellson.progressivecore.integration.baubles.powers.Powers;
import de.lellson.progressivecore.items.ItemPro;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.ProTab;
import de.lellson.progressivecore.misc.ITab.Tab;
import de.lellson.progressivecore.potion.PotionPro;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ProBaubles {

	public static enum AccessoryVariant {
		BASIC("basic", "/", TextFormatting.GRAY),
		RUBY("ruby", "Ruby", TextFormatting.RED),
		SAPPHIRE("sapphire", "Sapphire", TextFormatting.BLUE),
		AMETHYST("amethyst", "Amethyst", TextFormatting.LIGHT_PURPLE),
		TOPAZ("topaz", "Topaz", TextFormatting.YELLOW),
		OPAL("opal", "Opal", TextFormatting.GOLD),
		MALACHITE("malachite", "Malachite", TextFormatting.DARK_AQUA),
		EMERALD("emerald", "Emerald", TextFormatting.GREEN);
		
		private final String variant;
		private final String gem;
		private final TextFormatting format;
		
		private AccessoryVariant(String variant, String gem, TextFormatting format) {
			this.variant = variant;
			this.gem = gem;
			this.format = format;
			
		}
		
		public String getVariant() {
			return variant;
		}
		
		public String getGem() {
			return gem;
		}
		
		public TextFormatting getFormat() {
			return format;
		}
		
		public static String[] variants() {
			
			String[] variants = new String[AccessoryVariant.values().length];
			for (int i = 0; i < AccessoryVariant.values().length; i++)
				variants[i] = AccessoryVariant.values()[i].getVariant();
			return variants;
		}
	}

	public static final ItemPro GOLD_RING = new ItemProBauble("ring_gold", BaubleType.RING, 1).oreDict("ringGold", false);
	public static final ItemPro OBSIDIAN_RING = new ItemProBauble("ring_obsidian", BaubleType.RING, 2).oreDict("ringObsidian", false);
	public static final ItemPro ASTRAL_RING = new ItemProBauble("ring_astral", BaubleType.RING, 3).oreDict("ringAstral", false);
	
	
	public static void preInit() {
		MinecraftForge.EVENT_BUS.register(ProBaubles.class);
		Powers.init();
	}
	
	public static void init() {

	}
	
	public static void postInit() {

	}
	
	@SubscribeEvent
	public static void onXP(PlayerPickupXpEvent event) {
		
		EntityPlayer player = event.getEntityPlayer();
		IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
		
		int xp = event.getOrb().getXpValue();
		
		for (int slot : getProBaubles(handler)) 
		{
			ItemStack bauble = handler.getStackInSlot(slot);
			
			xp = ItemProBauble.increaseXp(bauble, player, xp);
			if (xp <= 0)
				break;
		}
		
		if (xp != event.getOrb().getXpValue())
			event.getOrb().xpValue = xp;
	}
	
	@SubscribeEvent
	public static void onXPDrop(LivingExperienceDropEvent event) {
		
		EntityPlayer player = event.getAttackingPlayer();
		
		if (player == null || event.getEntityLiving() == event.getAttackingPlayer() || player.isDead)
			return;
		
		IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
		
		int xp = event.getDroppedExperience();
		
		for (int slot : getProBaubles(handler)) 
		{
			ItemStack bauble = handler.getStackInSlot(slot);
			
			if (ItemProBauble.isAccessory(bauble))
			{
				xp = ((ItemProBauble)bauble.getItem()).onXPDrop(bauble, player, xp);
			}
		}
		
		if (xp != event.getDroppedExperience())
			event.setDroppedExperience(xp);
	}
	
	@SubscribeEvent
	public static void onAttack(LivingDamageEvent event) {
		
		EntityLivingBase entity = event.getEntityLiving();
		Entity source = event.getSource().getTrueSource();
		
		if (entity instanceof EntityPlayer)
		{
			IBaublesItemHandler handler = BaublesApi.getBaublesHandler((EntityPlayer)entity);
			
			for (int slot : getProBaubles(handler)) 
			{
				ItemStack bauble = handler.getStackInSlot(slot);
				
				if (ItemProBauble.isAccessory(bauble))
				{
					((ItemProBauble)bauble.getItem()).onTaken(bauble, (EntityPlayer)entity, source, event);
				}
			}
		}
		
		if (source instanceof EntityPlayer)
		{
			IBaublesItemHandler handler = BaublesApi.getBaublesHandler((EntityPlayer)source);
			
			for (int slot : getProBaubles(handler)) 
			{
				ItemStack bauble = handler.getStackInSlot(slot);
				
				if (ItemProBauble.isAccessory(bauble))
				{
					((ItemProBauble)bauble.getItem()).onAttack(bauble, (EntityPlayer)source, entity, event);
				}
			}
		}
	}

	private static List<Integer> getProBaubles(IBaublesItemHandler handler) {
		
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < handler.getSlots(); i++)
		{
			if (!handler.getStackInSlot(i).isEmpty() && handler.getStackInSlot(i).getItem() instanceof ItemProBauble)
				list.add(i);
		}
		
		return list;
	}

	public static int getBaubleSlot(EntityLivingBase player, ItemStack stack) {

		if (!(player instanceof EntityPlayer))
			return -1;
		
		IBaublesItemHandler handler = BaublesApi.getBaublesHandler((EntityPlayer) player);
		for (int i = 0; i < handler.getSlots(); i++)
			if (handler.getStackInSlot(i).equals(stack))
				return i;
		
		return -1;
	}
}
