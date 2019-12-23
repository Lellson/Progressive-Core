package de.lellson.progressivecore.inv.tile;

import java.util.ArrayList;
import java.util.List;

import de.lellson.progressivecore.blocks.misc.BlockSmelter;
import de.lellson.progressivecore.inv.container.ContainerSmelter;
import de.lellson.progressivecore.inv.recipe.SmelterEntry;
import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntitySmelter extends TileEntityLockable implements ITickable, ISidedInventory, IInteractionObject {

	private static final int[] SLOTS_TOP = new int[] {0, 1, 2, 3, 4, 5};
    private static final int[] SLOTS_BOTTOM = new int[] {7};
    private static final int[] SLOTS_SIDES = new int[] {6};
    private NonNullList<ItemStack> smelterItemStacks = NonNullList.<ItemStack>withSize(8, ItemStack.EMPTY);
    private int smelterBurnTime;
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;
    private String smelterCustomName;
    
    public abstract int getLevel();

    @Override
    public int getSizeInventory() {
        return this.smelterItemStacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.smelterItemStacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.smelterItemStacks.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.smelterItemStacks, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.smelterItemStacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.smelterItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.smelterItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag)
        {
        	if (SmelterEntry.hasOutput(this, getStacksToSmelt()))
        		this.totalCookTime = this.getCookTime();
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    public ITextComponent getDisplayName() {
    	return new TextComponentString(getName());
    }
    
    @Override
    public String getName() {
        return this.hasCustomName() ? this.smelterCustomName : getSmelterName(getLevel());
    }

    public static String getSmelterName(int level) {
		switch(level)
		{
			case 0: return "Basic Smelter";
			case 1: return "Advanced Smelter";
			case 2: return "Infernal Smelter";
			case 3: return "Titan Smelter";
			default: return "Smelter";
		}
	}

	@Override
    public boolean hasCustomName() {
        return this.smelterCustomName != null && !this.smelterCustomName.isEmpty();
    }

    public void setCustomInventoryName(String name) {
        this.smelterCustomName = name;
    }

    public static void registerFixesSmelter(DataFixer fixer) {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntitySmelter.class, new String[] {"Items"}));
    }
    
    public static int getItemBurnTime(ItemStack fuel) {
		return (int)Math.ceil(TileEntityFurnace.getItemBurnTime(fuel)*ProConfig.smelterFuelMultiplier);
	}

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.smelterItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.smelterItemStacks);
        this.smelterBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.smelterItemStacks.get(1));

        if (compound.hasKey("CustomName", 8))
        {
            this.smelterCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short)this.smelterBurnTime);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        ItemStackHelper.saveAllItems(compound, this.smelterItemStacks);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.smelterCustomName);
        }

        return compound;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public boolean isBurning() {
        return this.smelterBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }
    
    @Override
    public void update() {
        
    	boolean wasBurning = this.isBurning();
    	boolean mark = false;
    	
    	if (this.isBurning())
    	{
    		this.smelterBurnTime--;
    	}
    	
    	if (this.world.isRemote)
    		return;
    	
    	ItemStack fuel = smelterItemStacks.get(6);
    	ItemStack output = smelterItemStacks.get(7);
    	
    	if (!this.isBurning() && this.canSmelt() && TileEntityFurnace.isItemFuel(fuel))
    	{
    		this.smelterBurnTime = this.currentItemBurnTime = getItemBurnTime(fuel);
    		mark = true;
    		
    		Item fuelItem = fuel.getItem();
    		fuel.shrink(1);
    		
    		if (fuel.isEmpty())
    		{
                this.smelterItemStacks.set(1, fuelItem.getContainerItem(fuel));
    		}
    	}
    	
    	if (this.isBurning() && this.canSmelt())
    	{
    		++this.cookTime;

            if (this.cookTime >= (this.totalCookTime = this.getCookTime()))
            {
                this.cookTime = 0;
                this.totalCookTime = this.getCookTime();
                this.smeltItem();
                mark = true;
            }
    	}
    	else
    		this.cookTime = 0;
    	
    	if (!this.isBurning() && this.cookTime > 0)
        {
            this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
        }
    	
    	if (this.isBurning() != wasBurning)
    	{
    		BlockSmelter.setState(getLevel(), this.isBurning(), this.world, this.pos);
    		mark = true;
    	}
    	
    	if (mark)
    		this.markDirty();
    }
    
	private List<ItemStack> getStacksToSmelt() {
		
    	List<ItemStack> stacks = new ArrayList<ItemStack>();
    	
    	for (int i = 0; i < 6; i++)
    		if (!smelterItemStacks.get(i).isEmpty())
    			stacks.add(smelterItemStacks.get(i));
    	
		return stacks;
	}

	public int getCookTime() {
        return SmelterEntry.getCookTime(this, getStacksToSmelt());
    }

    private boolean canSmelt() {
    	
    	ItemStack currentOutput = smelterItemStacks.get(7);
    	
    	if (!SmelterEntry.hasOutput(this, getStacksToSmelt()))
    		return false;

    	ItemStack output = SmelterEntry.getOutput(this, getStacksToSmelt(), false);
    	
    	if (!output.isItemEqual(currentOutput) && !currentOutput.isEmpty())
    		return false; 
    	
    	int size = currentOutput.getCount() + output.getCount();
        return size < this.getInventoryStackLimit() && size < currentOutput.getMaxStackSize() && size < output.getMaxStackSize();
    }

    public void smeltItem() {
    	
    	if (!this.canSmelt())
    		return;
    	
    	ItemStack currentOutput = smelterItemStacks.get(7);
    	ItemStack output = SmelterEntry.getOutput(this, getStacksToSmelt(), true);
    	
    	if (currentOutput.isEmpty())
    	{
    		smelterItemStacks.set(7, output.copy());
    	}
    	else
    	{
    		currentOutput.grow(output.getCount());
    	}
    	
    	for (int i = 0; i < 6; i++)
    		smelterItemStacks.get(i).shrink(1);
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 2)
        {
            return false;
        }
        else if (index != 1)
        {
            return true;
        }
        else
        {
            ItemStack itemstack = this.smelterItemStacks.get(1);
            return TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.DOWN)
        {
            return SLOTS_BOTTOM;
        }
        else
        {
            return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
        }
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (direction == EnumFacing.DOWN && index == 1)
        {
            Item item = stack.getItem();

            if (item != Items.WATER_BUCKET && item != Items.BUCKET)
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getGuiID() {
        return Constants.prefix("smelter");
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerSmelter(playerInventory, this);
    }

    @Override
    public int getField(int id) {
        switch (id)
        {
            case 0:
                return this.smelterBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id)
        {
            case 0:
                this.smelterBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 4;
    }

    @Override
    public void clear() {
        this.smelterItemStacks.clear();
    }

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);

    @SuppressWarnings("unchecked")
    @Override
    @javax.annotation.Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing)
    {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }
    
    public static class TileEntitySmelterBasic extends TileEntitySmelter {
		@Override
		public int getLevel() {
			return 0;
		}
    }
    
    public static class TileEntitySmelterAdvanced extends TileEntitySmelter {
		@Override
		public int getLevel() {
			return 1;
		}
    }
    
    public static class TileEntitySmelterInfernal extends TileEntitySmelter {
		@Override
		public int getLevel() {
			return 2;
		}
    }
    
    public static class TileEntitySmelterTitan extends TileEntitySmelter {
		@Override
		public int getLevel() {
			return 3;
		}
    }
}
