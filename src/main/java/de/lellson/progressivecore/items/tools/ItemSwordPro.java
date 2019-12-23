package de.lellson.progressivecore.items.tools;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import de.lellson.progressivecore.blocks.BlockPro.Tool;
import de.lellson.progressivecore.items.IItemPro;
import de.lellson.progressivecore.items.tools.handler.ToolHandler;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.ITab;
import de.lellson.progressivecore.misc.ProModelHandler;
import de.lellson.progressivecore.misc.ProRegistry;
import de.lellson.progressivecore.sets.ToolSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSwordPro extends ItemSword implements IItemPro, ITab, ITool {
	
	private String name;
	private ToolHandler handler;
	private ToolMaterial tool;
	
	public ItemSwordPro(ToolMaterial material, ToolHandler handler, String name) {
		super(material);
		setUnlocalizedName(name);
		setCreativeTab(null);
		
		this.name = name;
		this.handler = handler;
		this.tool = material;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean canUse(ItemStack stack) {
		return hasHandler() && getHandler().canUse(stack);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return canUse(stack) ? 72000 : super.getMaxItemUseDuration(stack);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return canUse(stack) ? getUseAction(stack) : super.getItemUseAction(stack);
	}
	
	protected EnumAction getUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		if (!hasHandler())
        	return super.onItemRightClick(world, player, hand);
        
        ItemStack stack = player.getHeldItem(hand);
        getHandler().onRightClick(stack, hand, world, player);

        if (canUse(stack) && getHandler().canStartUsing(player.getHeldItem(hand), hand, world, player))
        {
        	player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
        else
            return super.onItemRightClick(world, player, hand);
    }
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entity, int timeLeft) {
		if (canUse(stack))
			getHandler().onUsingTick(stack, entity, getMaxItemUseDuration(stack)-timeLeft);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft) {
		if (canUse(stack))
			getHandler().onUsingStopped(stack, world, entity, getMaxItemUseDuration(stack)-timeLeft);
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
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		
		if (!hasHandler())
			return multimap;
		
		if (slot == EntityEquipmentSlot.MAINHAND)
			getHandler().addAttributes(multimap, new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0), slot, stack);

		return multimap;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		
		if (isSelected && hasHandler() && entity instanceof EntityLivingBase)
		{
			getHandler().onUpdate(stack, world, (EntityLivingBase)entity);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
		
		if (tool.getMaxUses() == -1)
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Unbreakable");
		
		if (!hasHandler())
			return;
		
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		getHandler().onTooltip(tooltip, world, stack, player);
	}

	@Override
	public boolean isWeapon() {
		return true;
	}

	@Override
	public ToolHandler getHandler() {
		return handler;
	}

	@Override
	public Tab getTab() {
		return Tab.COMBAT;
	}
}
