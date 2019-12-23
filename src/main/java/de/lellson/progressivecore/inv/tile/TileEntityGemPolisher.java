package de.lellson.progressivecore.inv.tile;

import javax.annotation.Nullable;

import de.lellson.progressivecore.inv.recipe.PolisherEntry;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import de.lellson.progressivecore.misc.network.MessagesPro;
import de.lellson.progressivecore.misc.network.message.RequestUpdatePolisherMessage;
import de.lellson.progressivecore.misc.network.message.UpdatePolisherMessage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityGemPolisher extends TileEntity {

	public static final String KEY_INPUT = Constants.prefix("polisherInput");
	
	private ItemStackHandler inventory = new ItemStackHandler(1) {
		
		protected void onContentsChanged(int slot) {
			if (!world.isRemote)
				MessagesPro.WRAPPER.sendToAllAround(new UpdatePolisherMessage(TileEntityGemPolisher.this), 
						new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
		};
		
		public int getSlotLimit(int slot) {
			return 1;
		};
	};
	
	@Override
	public void onLoad() {
		
		if (world.isRemote)
		{
			MessagesPro.WRAPPER.sendToServer(new RequestUpdatePolisherMessage(this));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag(KEY_INPUT, inventory.serializeNBT());
	
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		inventory.deserializeNBT(compound.getCompoundTag(KEY_INPUT));
	}

	public boolean handleRightclick(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, Vec3d hit) {
		
		ItemStack stack = player.getHeldItem(hand);
		IItemHandler handler = getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
		ItemStack input = handler.getStackInSlot(0);
		
		if (!input.isEmpty() && player.isSneaking())
		{
			dropInput(world, hit.x, hit.y, hit.z, facing);
			return true;
		}
		
		if (input.isEmpty() && !stack.isEmpty() && PolisherEntry.getOutput(stack) != null)
		{
			ItemStack newInput = stack.copy();
			newInput.setCount(1);
			handler.insertItem(0, newInput, false);
			
			if (!player.isCreative())
				stack.setCount(stack.getCount()-1);
			
			markDirty();
			return true;
		}
		else if (!input.isEmpty() && stack.isItemEqualIgnoreDurability(new ItemStack(ProItems.TITAN_CRUSHER)) && !player.getCooldownTracker().hasCooldown(stack.getItem()))
		{
			ItemStack output = PolisherEntry.getOutput(input);
			
			if (output != null)
			{
				EntityItem item = new EntityItem(world, hit.x, hit.y, hit.z, output);
				if (!world.isRemote)
					world.spawnEntity(item);
				handler.extractItem(0, 64, false);
				
				if (!player.isCreative())
					stack.damageItem(10, player);
				
				ClientHelper.playSound(player, SoundEvents.BLOCK_GLASS_BREAK, 0.5f, 1.0f - world.rand.nextFloat()*0.5f);
				player.getCooldownTracker().setCooldown(stack.getItem(), 10);
				
				markDirty();
			}
			
			return true;
		}
		
		return false;
	}

	public void dropInput(World world, double x, double y, double z, EnumFacing facing) {
		
		ItemStack input = inventory.getStackInSlot(0);
		
		if (input.isEmpty())
			return;
		
		EntityItem item = new EntityItem(world, x, y, z, input);
		if (!world.isRemote)
			world.spawnEntity(item);
		
		getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing).extractItem(0, 64, false);
		markDirty();
	}
	
	public ItemStackHandler getInventory() {
		return inventory;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(capability, facing);
	}
}
