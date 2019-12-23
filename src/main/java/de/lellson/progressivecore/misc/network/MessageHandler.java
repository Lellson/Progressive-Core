package de.lellson.progressivecore.misc.network;

import de.lellson.progressivecore.misc.Constants;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandler<REQ extends IMessage, REPLY extends IMessage> {
	
	public MessageHandler(Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass, Side side, int discriminator) {
		MessagesPro.WRAPPER.registerMessage(handlerClass, messageClass, discriminator, side);
	}
}
