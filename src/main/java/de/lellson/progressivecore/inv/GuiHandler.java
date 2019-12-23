package de.lellson.progressivecore.inv;

import de.lellson.progressivecore.ProgressiveCore;
import de.lellson.progressivecore.inv.container.ContainerSmelter;
import de.lellson.progressivecore.inv.gui.GuiSmelter;
import de.lellson.progressivecore.inv.tile.TileEntitySmelter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {

	public static void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(ProgressiveCore.instance, new GuiHandler());
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if (ID == Gui.SMELTER.getId())
		{
			TileEntitySmelter tile = (TileEntitySmelter) world.getTileEntity(new BlockPos(x, y, z));
			return new ContainerSmelter(player.inventory, tile);
		}
			
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if (ID == Gui.SMELTER.getId())
		{
			TileEntitySmelter tile = (TileEntitySmelter) world.getTileEntity(new BlockPos(x, y, z));
			return new GuiSmelter(player.inventory, tile);
		}
		
		return null;
	}
	
	public static enum Gui {
		SMELTER(0);
		
		private int id;
		
		private Gui(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}
}
