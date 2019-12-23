package de.lellson.progressivecore.misc.network.message;

import de.lellson.progressivecore.inv.tile.TileEntityGemPolisher;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RequestUpdatePolisherMessage implements IMessage {

	private BlockPos pos;
	private int dimension;
	
	public RequestUpdatePolisherMessage(BlockPos pos, int dimension) {
		this.pos = pos;
		this.dimension = dimension;
	}
	
	public RequestUpdatePolisherMessage(TileEntityGemPolisher te) {
		this(te.getPos(), te.getWorld().provider.getDimension());
	}
	
	public RequestUpdatePolisherMessage() {
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(dimension);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		dimension = buf.readInt();
	}
	
	public static class RequestUpdatePolisherMessageHandler implements IMessageHandler<RequestUpdatePolisherMessage, UpdatePolisherMessage> {
	
		@Override
		public UpdatePolisherMessage onMessage(RequestUpdatePolisherMessage message, MessageContext ctx) {
			
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
			TileEntityGemPolisher te = (TileEntityGemPolisher)world.getTileEntity(message.pos);
			
			if (te != null) {
				return new UpdatePolisherMessage(te);
			} else {
				return null;
			}
		}
	
	}

}
