package de.lellson.progressivecore.items.armor;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import de.lellson.progressivecore.items.IItemPro;
import de.lellson.progressivecore.items.armor.handler.ArmorHandler;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.ITab;
import de.lellson.progressivecore.misc.ProModelHandler;
import de.lellson.progressivecore.misc.ProRegistry;
import de.lellson.progressivecore.sets.ArmorSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorPro extends ItemArmor implements IItemPro, ITab {
	
	private ArmorSet set;
	private String name;
	private String matName;
	
	public ItemArmorPro(ArmorMaterial material, ArmorSet set, EntityEquipmentSlot slot, String name) {
		super(material, 0, slot);
		setUnlocalizedName(name);
		setCreativeTab(null);
		
		this.set = set;
		this.name = name;
		this.matName = name.replace("boots_", "").replace("leggings_", "").replace("chestplate_", "").replace("helmet_", "");
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public Item setUnlocalizedName(String name) {
		super.setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Constants.prefix(name)));
		ProRegistry.register(this);
		ProModelHandler.register(this);
		
		return this;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		
		if (!set.hasHandler())
			return;
		
		set.getHandler().onUpdate(world, player, armorType, set.getSlots(player), set.hasFullSet(player));
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		
		if (!set.hasHandler())
			return multimap;
		
		if (slot == armorType)
			set.getHandler().addAttributes(multimap, new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0), slot, stack);

		return multimap;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return Constants.prefix("textures/models/armor/armor_" + matName + (slot == EntityEquipmentSlot.LEGS ? "2" : "") + ".png");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
		
		if (getArmorMaterial().getDurability(EntityEquipmentSlot.HEAD) < 0)
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Unbreakable");
		
		if (!set.hasHandler())
			return;
		
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		set.getHandler().onTooltip(tooltip, world, stack, player, set.hasFullSet(player));
	}
	
	@SubscribeEvent
	public static void onDamage(LivingDamageEvent event) {
		
		EntityLivingBase living = event.getEntityLiving();
		
		for (ArmorSet set : ArmorSet.ARMOR_SETS)
		{
			if (!set.hasHandler())
				continue;
			
			List<EntityEquipmentSlot> slots = set.getSlots(living);
			if (slots.size() > 0)
				set.getHandler().onDamageTaken(living.getEntityWorld(), living, slots, event, set.hasFullSet(living));
			
			Entity source = event.getSource().getTrueSource();
			if (source != null && (slots = set.getSlots(source)).size() > 0)
				set.getHandler().onDamageDealt(source.getEntityWorld(), source, event.getSource().getImmediateSource(), living, slots, event, set.hasFullSet(source));
		}
	}
	
	@SubscribeEvent
	public static void onJump(LivingJumpEvent event) {
		
		EntityLivingBase living = event.getEntityLiving();
		
		for (ArmorSet set : ArmorSet.ARMOR_SETS)
		{
			if (!set.hasHandler())
				continue;
			
			List<EntityEquipmentSlot> slots = set.getSlots(living);
			if (slots.size() > 0)
				set.getHandler().onJump(living.getEntityWorld(), living, slots, event, set.hasFullSet(living));
		}
	}
	
	@SubscribeEvent
	public static void logOut(PlayerLoggedOutEvent event) {
		event.player.getEntityData().setInteger(ArmorHandler.COOLDOWN, 60);
	}

	@Override
	public Tab getTab() {
		return Tab.COMBAT;
	}
}
