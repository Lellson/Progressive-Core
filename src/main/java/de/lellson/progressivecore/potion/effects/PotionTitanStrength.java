package de.lellson.progressivecore.potion.effects;

import java.util.Iterator;

import de.lellson.progressivecore.misc.helper.BlockHelper;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import de.lellson.progressivecore.potion.PotionPro;
import de.lellson.progressivecore.sets.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentKnockback;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class PotionTitanStrength extends PotionPro {

	public PotionTitanStrength() {
		super("titan_strength", false, 0x444488);
	}
	
	@Override
	public void onDamageDealt(LivingDamageEvent event, EntityLivingBase attacker, EntityLivingBase target, int amplifier, int duration) {
		
		event.setAmount(event.getAmount() * (3f + amplifier));
		target.knockBack(attacker, 3F + amplifier, attacker.posX - target.posX, attacker.posZ - target.posZ);
		
		ClientHelper.startPacket();
		ClientHelper.playSound(target, SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 0.7F, 1.0F);
		ClientHelper.setParticlePars(Block.getIdFromBlock(getBlockBelow(target.world, target.getPosition())));
		ClientHelper.spawnParticle(target, EnumParticleTypes.BLOCK_CRACK, 0.15f, 30);
		ClientHelper.stopPacket();
		
		attacker.removePotionEffect(this);
	}

	private Block getBlockBelow(World world, BlockPos pos) {
		
		Block block = world.getBlockState(pos.down()).getBlock();
		return block == Blocks.AIR ? pos.getY() == 0 ? Blocks.DIRT : getBlockBelow(world, pos.down()) : block;
	}
	
	@Override
	public void onBlockBroken(BreakEvent event, EntityPlayer player, int amplifier, int duration) {
		
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
	    
	    int amount = 2+amplifier;
	    switch(mop.sideHit)
	    {
	    	case DOWN:
	    		it = BlockPos.getAllInBox(pos.east().north(), pos.up(amount).west().south());
	    		break;
			case EAST:
				it = BlockPos.getAllInBox(pos.up().north(), pos.west(amount).down().south());
				break;
			case NORTH:
				it = BlockPos.getAllInBox(pos.up().west(), pos.south(amount).down().east());
				break;
			case SOUTH:
				it = BlockPos.getAllInBox(pos.up().west(), pos.north(amount).down().east());
				break;
			case UP:
				it = BlockPos.getAllInBox(pos.east().north(), pos.down(amount).west().south());
				break;
			case WEST:
				it = BlockPos.getAllInBox(pos.up().north(), pos.east(amount).down().south());
				break;
	    }
	    
	    boolean destroyed = false;
	    if (it != null)
	    {
	    	Iterator<BlockPos> iter = it.iterator();
	    	while(iter.hasNext())
	    	{
	    		BlockPos cur = iter.next();
	    		
	    		if (BlockHelper.isMineable(world, player, player.getHeldItemMainhand(), cur, state, pos))
	    		{
	    			BlockHelper.destroyBlock(player, cur, !player.isCreative());
	    			player.getHeldItemMainhand().damageItem(1, player);
	    			destroyed = true;
	    		}	
	    	}
	    }
		
	    if (destroyed)
	    	player.removePotionEffect(this);
	}
}
