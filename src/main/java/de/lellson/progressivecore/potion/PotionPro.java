package de.lellson.progressivecore.potion;

import java.util.ArrayList;
import java.util.List;

import de.lellson.progressivecore.items.tools.ITool;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.ProRegistry;
import de.lellson.progressivecore.sets.ArmorSet;
import de.lellson.progressivecore.sets.ToolSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionPro extends Potion {
	
	public static final List<PotionPro> POTIONS = new ArrayList<PotionPro>();
	
	private int tickrate = 0;
	private boolean isticking = false;

	private ResourceLocation icon;
	
	protected PotionPro(String name, boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		setRegistryName(Constants.MOD_ID, name);
		setPotionName("potion." + name);
		ProRegistry.register(this);
		POTIONS.add(this);
	}
	
	public PotionPro setTickRate(int tickrate) {
		this.tickrate = tickrate;
		this.isticking = tickrate > 0;
		return this;
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return isticking && duration % (tickrate >> amplifier) == 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha)
	{
		super.renderHUDEffect(x, y, effect, mc, alpha);

		mc.renderEngine.bindTexture(getIcon());

		GlStateManager.enableBlend();
		Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
	{
		super.renderInventoryEffect(x, y, effect, mc);

		mc.renderEngine.bindTexture(getIcon());

		GlStateManager.enableBlend();
		Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
	}

	private ResourceLocation getIcon() {
		return icon == null ? (icon = new ResourceLocation(Constants.MOD_ID + ":textures/potion_icons/" + getName().replace("potion.", "") + ".png")) : icon;
	}
	
	@SubscribeEvent
	public static void onDamageTakenEvent(LivingDamageEvent event) {
		
		if (!(event.getEntityLiving() instanceof EntityLivingBase))
			return;
		
		EntityLivingBase player = (EntityLivingBase) event.getEntityLiving();
		
		for (PotionPro potion : POTIONS)
		{
			PotionEffect effect = player.getActivePotionEffect(potion);
			if (effect != null)
				potion.onDamageTaken(event, player, effect.getAmplifier(), effect.getDuration());
			
			Entity source = event.getSource().getTrueSource();
			if (source != null && source instanceof EntityLivingBase)
			{
				effect = ((EntityLivingBase)source).getActivePotionEffect(potion);
				if (effect != null)
					potion.onDamageDealt(event, (EntityLivingBase)source, player, effect.getAmplifier(), effect.getDuration());
			}
		}
	}

	@SubscribeEvent
	public static void onKnockbackTakenEvent(LivingKnockBackEvent event) {
		
		if (!(event.getEntityLiving() instanceof EntityLivingBase))
			return;
		
		EntityLivingBase player = (EntityLivingBase) event.getEntityLiving();
		
		for (PotionPro potion : POTIONS)
		{
			PotionEffect effect = player.getActivePotionEffect(potion);
			if (effect != null)
				potion.onKnockback(event, player, effect.getAmplifier(), effect.getDuration());
		}
	}
	
	@SubscribeEvent
	public static void blockBreak(BlockEvent.BreakEvent event) {
		
		EntityPlayer player = event.getPlayer();
		if (player != null)
		{
			for (PotionPro potion : POTIONS)
			{
				PotionEffect effect = player.getActivePotionEffect(potion);
				if (effect != null)
					potion.onBlockBroken(event, player, effect.getAmplifier(), effect.getDuration());
			}
		}
	}

	public void onKnockback(LivingKnockBackEvent event, EntityLivingBase entity, int amplifier, int duration) {
	}

	public void onDamageTaken(LivingDamageEvent event, EntityLivingBase entity, int amplifier, int duration) {
	}
	
	public void onDamageDealt(LivingDamageEvent event, EntityLivingBase attacker, EntityLivingBase target, int amplifier, int duration) {
	}
	
	public void onBlockBroken(BreakEvent event, EntityPlayer player, int amplifier, int duration) {
	}
}
