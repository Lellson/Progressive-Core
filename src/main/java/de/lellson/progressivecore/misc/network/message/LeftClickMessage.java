package de.lellson.progressivecore.misc.network.message;

import de.lellson.progressivecore.ProgressiveCore;
import de.lellson.progressivecore.items.tools.ITool;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import scala.sys.process.processInternal;

public class LeftClickMessage implements IMessage {

	public LeftClickMessage() {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
	
	public static class LeftClickMessageHandler implements IMessageHandler<LeftClickMessage, LeftClickMessage> 
	{
		@Override
		public LeftClickMessage onMessage(LeftClickMessage message, MessageContext ctx) {
			EntityPlayer player = ProgressiveCore.proxy.getPlayer(ctx);
			player.getServer().addScheduledTask(() -> ITool.handleLeftClick(player.world, player, player.getHeldItemMainhand()));
			return null;
		}
	}
}
