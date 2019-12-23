package de.lellson.progressivecore.integration.baubles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.realmsclient.gui.ChatFormatting;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.BaubleItem;
import baubles.common.Baubles;
import de.lellson.progressivecore.blocks.ores.BlockOreGem;
import de.lellson.progressivecore.integration.baubles.ProBaubles.AccessoryVariant;
import de.lellson.progressivecore.integration.baubles.powers.AbstractPower;
import de.lellson.progressivecore.integration.baubles.powers.Power;
import de.lellson.progressivecore.integration.baubles.powers.Powers;
import de.lellson.progressivecore.items.ItemPro;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.ProSounds;
import de.lellson.progressivecore.misc.config.ProConfig;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import de.lellson.progressivecore.misc.helper.MiscHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeHills;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.tools.modifiers.ModAntiMonsterType;

public class ItemProBauble extends ItemPro implements IBauble {

	public static final Random RND = new Random();
	
	public static final String CATEGORY = "accessories";
	public static final int LEVEL_DIVIDER = 465;
	
	public static final String KEY_XP = Constants.prefix("acc_xp");
	public static final String KEY_POWERS = Constants.prefix("acc_powers");
	public static final String KEY_SLOT = Constants.prefix("acc_slot");
	
	protected BaubleType type;
	protected final int maxLevel;
	
	public ItemProBauble(String name, BaubleType type, int maxLevel) {
		super(name, AccessoryVariant.variants());
		this.type = type;
		this.maxLevel = ProConfig.cfg.getInt("powers" + MiscHelper.upperFirst(name.replace("_", "")), CATEGORY, maxLevel, 1, Short.MAX_VALUE, "Max amount of powers for the " + getItemStackDisplayName(new ItemStack(this)));
		setMaxStackSize(1);
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {
		return type;
	}
	
	public int getMaxLevel(ItemStack stack) {
		return maxLevel;
	}
	
	@Override
	public Tab getTab() {
		return Tab.ACCESSORIES;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		
		AccessoryVariant variant = getVariant(stack);
		if (variant != null)
			tooltip.add("Gem: " + variant.getFormat() + variant.getGem());
		
		if (stack.hasTagCompound() && isAccessory(stack))
		{
			tooltip.add("Level: " + TextFormatting.WHITE +  getLevel(stack));
			
			if (!isMaxLevel(stack))
				tooltip.add("XP: " + TextFormatting.DARK_GRAY + (stack.getTagCompound().getInteger(KEY_XP) % LEVEL_DIVIDER) + "/" + LEVEL_DIVIDER);
			
			List<Power> powers = getPowers(stack);
			for (Power power : powers)
			{
				tooltip.add(power.getFormattedString());
				if (GuiScreen.isShiftKeyDown() && power != null && power.getPower().getDescription(power).length() > 0)
					tooltip.add(TextFormatting.WHITE + power.getPower().getDescription(power));
			}
			
			if (!powers.isEmpty())
				tooltip.add(TextFormatting.DARK_GRAY + "SHIFT for more information");
		}
	}
	
	public static AccessoryVariant getVariant(ItemStack stack) {
		
		if (!isAccessory(stack, false))
			return null;
		
		return AccessoryVariant.values()[stack.getItemDamage()];
	}
	
	public static boolean isAccessory(ItemStack stack, boolean noBasic) {
		return stack.getItem() instanceof ItemProBauble && stack.getItemDamage() < AccessoryVariant.values().length && (stack.getItemDamage() > 0 || !noBasic);
	}
	
	public static boolean isAccessory(ItemStack stack) {
		return isAccessory(stack, true);
	}

	public static void initNBT(ItemStack stack) {
		
		if (stack.hasTagCompound() || !isAccessory(stack))
			return;
		
		NBTTagCompound nbt = new NBTTagCompound();
		
		nbt.setInteger(KEY_XP, 0);
		nbt.setTag(KEY_POWERS, new NBTTagList());
		
		stack.setTagCompound(nbt);
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		initNBT(stack);
	}
	
	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase player) {
		setSlotData(stack, player);
		player.getAttributeMap().applyAttributeModifiers(getAttributeModifiers(player, stack));
	}
	
	private void setSlotData(ItemStack stack, EntityLivingBase player) {
		initNBT(stack);
		stack.getTagCompound().setInteger(KEY_SLOT, ProBaubles.getBaubleSlot(player, stack));
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		player.getAttributeMap().removeAttributeModifiers(getAttributeModifiers(player, stack));
	}
	
	private Multimap<String, AttributeModifier> getAttributeModifiers(EntityLivingBase player, ItemStack stack) {
		
		Multimap<String, AttributeModifier> map = HashMultimap.<String, AttributeModifier>create();
		
		for (Power power : getPowers(stack))
		{
			power.getPower().modifyAttributeModifiers(stack, player, power.getLevel(), new UUID((getUnlocalizedName() + stack.getTagCompound().getInteger(KEY_SLOT)).hashCode(), 0), map);
		}
		
		return map;
	}
	
	public int onXPDrop(ItemStack stack, EntityPlayer player, int xp) {

		for (Power power : getPowers(stack)) 
		{
			xp = power.onXpDrop(stack, player, xp);
		}
		
		return xp;
	}

	public static int increaseXp(ItemStack stack, EntityLivingBase player, int xp) {

		if (!isAccessory(stack) || (stack.hasTagCompound() && isMaxLevel(stack)))
			return xp;
		
		initNBT(stack);
		
		int oldXp = stack.getTagCompound().getInteger(KEY_XP);
		int newXp = oldXp + xp * getMultiplier(player, stack.getItemDamage());
		stack.getTagCompound().setInteger(KEY_XP, newXp);
		
		if (getLevel(oldXp) < getLevel(newXp))
			addPower(stack, player);
		
		return Math.max(0, newXp - getMaxXp(stack));
	}

	private static boolean isMaxLevel(ItemStack stack) {
		return stack.getTagCompound().getInteger(KEY_XP) >= getMaxXp(stack);
	}
	
	private static int getMaxXp(ItemStack stack) {
		return getMaxXpLevel(stack) * LEVEL_DIVIDER;
	}
	
	private static int getMaxXpLevel(ItemStack stack) {
		return isAccessory(stack) ? ((ItemProBauble)stack.getItem()).getMaxLevel(stack) : 0;
	}

	private static int getMultiplier(EntityLivingBase player, int damage) {
		
		World world = player.getEntityWorld();
		if (world.provider.getDimension() != 0)
			return 1;
		
		Biome biome = world.getBiome(player.getPosition());
		
		if (biome instanceof BiomeHills)
			return damage == 7 ? 2 : 1;
		else
			return damage == BlockOreGem.getMetaForBiome(biome)+1 ? 2 : 1;
	}

	public static void addPower(ItemStack stack, EntityLivingBase player) {
		
		if (!isAccessory(stack))
			return;
		
		initNBT(stack);
		
		AbstractPower newPower = Powers.getRandom(RND);
		List<Power> powers = getPowers(stack);
		Power p = null;
		
		boolean found = false;
		for (Power power : powers)
		{
			if (newPower.getName().equals(power.getPower().getName()))
			{
				if (power.getPower().isMaxLevel(power))
				{
					addPower(stack, player);
					return;
				}
				else
					powers.set(powers.indexOf(power), p = power.increaseLevel());
				
				found = true;	
				break;
			}
		}
			
		if (!found)
		{
			powers.add(p = new Power(newPower, 1));
		}
		
		ItemProBauble bauble = (ItemProBauble)stack.getItem();
		
		player.getAttributeMap().removeAttributeModifiers(bauble.getAttributeModifiers(player, stack));
		
		setPowers(stack, powers);
		
		player.getAttributeMap().applyAttributeModifiers(bauble.getAttributeModifiers(player, stack));
		
		if (player instanceof EntityPlayer)
		{
			player.sendMessage(stack.getTextComponent().appendSibling(new TextComponentString(TextFormatting.DARK_AQUA + " leveled up (" + p.getFormattedString() + TextFormatting.DARK_AQUA + ")")));
			ClientHelper.playSound(player, ProSounds.LEVEL_UP, 0.7f, 1.0f);
		}
	}
	public static List<Power> getPowers(ItemStack stack) {
		
		List<Power> powers = new ArrayList<Power>();
		
		if (!isAccessory(stack))
			return powers;
		
		initNBT(stack);
		NBTTagList taglist = stack.getTagCompound().getTagList(KEY_POWERS, 10);
		
		for (NBTBase nbt : taglist)
			powers.add(Power.fromNBT((NBTTagCompound) nbt));
		
		return powers;
	}
	
	public static void setPowers(ItemStack stack, List<Power> powers) {
		
		if (!isAccessory(stack))
			return;
		
		NBTTagList taglist = new NBTTagList();
		
		for (Power power : powers) 
			taglist.appendTag(power.toNBT());
		
		initNBT(stack);
		stack.getTagCompound().setTag(KEY_POWERS, taglist);
	}


	public static int getLevel(ItemStack stack) {
		return isAccessory(stack) && stack.hasTagCompound() ? getLevel(stack.getTagCompound().getInteger(KEY_XP)) : 0;
	}
	
	public static int getLevel(int xp) {
		return xp / LEVEL_DIVIDER;
	}

	public void onTaken(ItemStack stack, EntityPlayer target, Entity attacker, LivingDamageEvent event) {
		
		for (Power power : getPowers(stack)) 
			power.onTaken(stack, target, attacker, event);
	}

	public void onAttack(ItemStack stack, EntityPlayer attacker, EntityLivingBase target, LivingDamageEvent event) {
		
		for (Power power : getPowers(stack)) 
			power.onAttack(stack, attacker, target, event);
	}
}
