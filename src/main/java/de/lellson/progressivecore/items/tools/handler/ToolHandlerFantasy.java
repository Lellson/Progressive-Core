package de.lellson.progressivecore.items.tools.handler;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import de.lellson.progressivecore.items.tools.ITool;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.helper.BlockHelper;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public abstract class ToolHandlerFantasy extends ToolHandler {
	
	protected static final String TAG_MODE = Constants.prefix("mode");
	protected static final String[] TOOL_MODE = {"1x1", "3x3"};
	
	@Override
	public void onLeftClick(ItemStack stack, EntityPlayer player, World world) {
		
		if (!isWeapon(stack))
			return;
		
		if (player.getCooledAttackStrength(0) >= 1 && !player.isSneaking())
		{
			shoot(world, player, stack);
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, EntityLivingBase entity) {
		super.onUpdate(stack, world, entity);
		
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		if (!isWeapon(stack) && !stack.getTagCompound().hasKey(TAG_MODE))
			stack.getTagCompound().setBoolean(TAG_MODE, false);
	}
	
	@Override
	public void onRightClick(ItemStack stack, EnumHand hand, World world, EntityPlayer player) {
		if (!isWeapon(stack) && stack.hasTagCompound())
		{
			stack.getTagCompound().setBoolean(TAG_MODE, !stack.getTagCompound().getBoolean(TAG_MODE));
			ClientHelper.playSound(player, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND);
		}
	}
	
	@Override
	public void onBlockBroken(ItemStack stack, BreakEvent event) {

		if (!stack.hasTagCompound() || !stack.getTagCompound().getBoolean(TAG_MODE))
			return;
		
		EntityPlayer player = event.getPlayer();
		double x = player.posX - (event.getPos().getX() + 0.5);
		double y = player.posY - (event.getPos().getY() + 0.5);
		double z = player.posZ - (event.getPos().getZ() + 0.5);
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		IBlockState state = world.getBlockState(pos);
		
		RayTraceResult mop = BlockHelper.rayTrace(world, player, true);
	    if(mop == null || !pos.equals(mop.getBlockPos())) 
	    {
	    	mop = BlockHelper.rayTrace(world, player, false);
	    	if(mop == null || !pos.equals(mop.getBlockPos())) 
	    	{
	    		return;
	    	}
	    }
	    
	    Iterable<BlockPos> it = null;
	    
	    switch(mop.sideHit)
	    {
	    	case DOWN:
	    		it = BlockPos.getAllInBox(pos.east().north(), pos.west().south());
	    		break;
			case EAST:
				it = BlockPos.getAllInBox(pos.up().north(), pos.down().south());
				break;
			case NORTH:
				it = BlockPos.getAllInBox(pos.up().west(), pos.down().east());
				break;
			case SOUTH:
				it = BlockPos.getAllInBox(pos.up().west(), pos.down().east());
				break;
			case UP:
				it = BlockPos.getAllInBox(pos.east().north(), pos.west().south());
				break;
			case WEST:
				it = BlockPos.getAllInBox(pos.up().north(), pos.down().south());
				break;
	    }
	    
	    boolean destroyed = false;
	    if (it != null)
	    {
	    	Iterator<BlockPos> iter = it.iterator();
	    	while(iter.hasNext())
	    	{
	    		BlockPos cur = iter.next();
	    		
	    		if (BlockHelper.isMineable(world, player, stack, cur, state, pos))
	    		{
	    			BlockHelper.destroyBlock(player, cur, !player.isCreative());
	    			stack.damageItem(1, player);
	    			destroyed = true;
	    		}	
	    	}
	    }
	}
	
	protected boolean isWeapon(ItemStack stack) {
		return stack.getItem() instanceof ITool && ((ITool)stack.getItem()).isWeapon();
	}
	
	@Override
	public void onTooltip(List<String> lines, World world, ItemStack stack, EntityPlayerSP player) {
		
		if (isWeapon(stack))
		{
			lines.add(getProjectileDescription());
		}
		else if (stack.hasTagCompound())
		{
			lines.add("Mode: " + TextFormatting.GOLD + TOOL_MODE[stack.getTagCompound().getBoolean(TAG_MODE) ? 1 : 0]);
			lines.add("Right-Click to change mode");
		}
	}
	
	@Override
	public void addAttributes(Multimap<String, AttributeModifier> multimap, UUID uuid, EntityEquipmentSlot slot, ItemStack stack) {
		multimap.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(uuid, Constants.prefix("fantasy_reach"), 1, 0));
	}
	
	public abstract String getProjectileDescription();
	
	public abstract void shoot(World world, EntityLivingBase entity, ItemStack stack);
	
	public abstract void summonProjectile(World world, EntityLivingBase entity, ItemStack stack);
}
