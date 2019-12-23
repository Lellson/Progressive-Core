package de.lellson.progressivecore.misc.helper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class BlockHelper {

	public static boolean isMineable(World world, EntityPlayer player, ItemStack stack, BlockPos pos, IBlockState refState, BlockPos refPos) {
		
		if (stack == null && world.isAirBlock(pos))
			return false;
		
		IBlockState state = world.getBlockState(pos);
		
		float refStrength = ForgeHooks.blockStrength(refState, player, world, refPos);
	    float strength = ForgeHooks.blockStrength(state, player, world, pos);

	    if(!ForgeHooks.canHarvestBlock(state.getBlock(), player, world, pos) || (refStrength / strength > 10f))
	    	return false;
		
		for(String type : stack.getItem().getToolClasses(stack)) 
			if(state.getBlock().isToolEffective(type, state)) 
				return true;
		
		return false;
	}
	
	public static boolean destroyBlock(EntityPlayer player, BlockPos pos, boolean dropBlock)
    {
		World world = player.world;
        IBlockState iblockstate = world.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (block.isAir(iblockstate, world, pos))
        {
            return false;
        }
        else
        {
        	int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand());
        	
        	world.playEvent(2001, pos, Block.getStateId(iblockstate));
        	
            if (dropBlock)
            {
            	block.dropXpOnBlockBreak(world, pos, block.getExpDrop(iblockstate, world, pos, fortune));
                block.dropBlockAsItem(world, pos, iblockstate, fortune);
            }

            return world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

	public static RayTraceResult rayTrace(World world, EntityPlayer player, boolean useLiquids)
    {
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        double d0 = player.posX;
        double d1 = player.posY + (double)player.getEyeHeight();
        double d2 = player.posZ;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d3 = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
        return world.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
    }
}
