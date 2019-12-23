package de.lellson.progressivecore.misc.network.message;

import de.lellson.progressivecore.inv.tile.TileEntityGemPolisher;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdatePolisherMessage implements IMessage {

	private BlockPos pos;
	private ItemStack stack;
	
	public UpdatePolisherMessage() {
	}
	
	public UpdatePolisherMessage(BlockPos pos, ItemStack stack) {
		this.pos = pos;
		this.stack = stack;
	}
	
	public UpdatePolisherMessage(TileEntityGemPolisher te) {
		this(te.getPos(), te.getInventory().getStackInSlot(0));
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		stack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		ByteBufUtils.writeItemStack(buf, stack);
	}
	
	public static class UpdatePolisherMessageHandler implements IMessageHandler<UpdatePolisherMessage, IMessage> {

		@Override
		public IMessage onMessage(UpdatePolisherMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> 
			{
				TileEntityGemPolisher te = (TileEntityGemPolisher)Minecraft.getMinecraft().world.getTileEntity(message.pos);
				if (te != null)
					te.getInventory().setStackInSlot(0, message.stack);
			});
			
			return null;
		}
		
	}
}
