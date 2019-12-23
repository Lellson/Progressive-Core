package de.lellson.progressivecore.inv.container;

import de.lellson.progressivecore.inv.tile.TileEntitySmelter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerSmelter extends Container {

	private final IInventory inv;
    private int cookTime;
    private int totalCookTime;
    private int smelterBurnTime;
    private int currentItemBurnTime;

    public ContainerSmelter(InventoryPlayer playerInventory, IInventory inv) {
        this.inv = inv;
        
        for (int i = 0; i < 6; i++)
        	this.addSlotToContainer(new Slot(inv, i, 51 + i%3 * 18, 26 + i/3 * 18));
        
        this.addSlotToContainer(new SlotFurnaceFuel(inv, 6, 25, 35));
        this.addSlotToContainer(new SlotSmelterOutput(playerInventory.player, inv, 7, 133, 35));

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.inv);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.cookTime != this.inv.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.inv.getField(2));
            }

            if (this.smelterBurnTime != this.inv.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.inv.getField(0));
            }

            if (this.currentItemBurnTime != this.inv.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.inv.getField(1));
            }

            if (this.totalCookTime != this.inv.getField(3))
            {
                icontainerlistener.sendWindowProperty(this, 3, this.inv.getField(3));
            }
        }

        this.cookTime = this.inv.getField(2);
        this.smelterBurnTime = this.inv.getField(0);
        this.currentItemBurnTime = this.inv.getField(1);
        this.totalCookTime = this.inv.getField(3);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        this.inv.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.inv.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
    	
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 7)
            {
                if (!this.mergeItemStack(itemstack1, 8, 44, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= 8)
            {
                if (TileEntityFurnace.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 6, 7, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (!inputFull())
                {
                	if (!this.mergeItemStack(itemstack1, 0, 6, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index < 35)
                {
                    if (!this.mergeItemStack(itemstack1, 35, 44, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 35 && index < 44 && !this.mergeItemStack(itemstack1, 8, 35, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 8, 44, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

	private boolean inputFull() {

		for (int i = 0; i < 6; i++)
			if (!this.inventorySlots.get(i).getHasStack())
				return false;
		
		return true;
	}
}
