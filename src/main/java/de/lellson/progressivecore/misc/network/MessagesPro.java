package de.lellson.progressivecore.misc.network;

import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.network.message.LeftClickMessage;
import de.lellson.progressivecore.misc.network.message.ParticleMessage;
import de.lellson.progressivecore.misc.network.message.RequestUpdatePolisherMessage;
import de.lellson.progressivecore.misc.network.message.SoundMessage;
import de.lellson.progressivecore.misc.network.message.UpdatePolisherMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MessagesPro {
	
	public static final SimpleNetworkWrapper WRAPPER = new SimpleNetworkWrapper(Constants.prefix("net"));
	
	public static final MessageHandler<ParticleMessage, ParticleMessage> MSG_PARTICLE = new MessageHandler<ParticleMessage, ParticleMessage>(
			ParticleMessage.ParticleMessageHandler.class, 
			ParticleMessage.class, 
			Side.CLIENT, 
			0
	);
	
	public static final MessageHandler<SoundMessage, SoundMessage> MSG_SOUND = new MessageHandler<SoundMessage, SoundMessage>(
			SoundMessage.SoundMessageHandler.class, 
			SoundMessage.class, 
			Side.CLIENT, 
			1
	);
	
	public static final MessageHandler<LeftClickMessage, LeftClickMessage> MSG_CLICK = new MessageHandler<LeftClickMessage, LeftClickMessage>(
			LeftClickMessage.LeftClickMessageHandler.class, 
			LeftClickMessage.class, 
			Side.SERVER, 
			2
	);
	
	public static final MessageHandler<UpdatePolisherMessage, IMessage> MSG_UPDATE_POLISHER = new MessageHandler<UpdatePolisherMessage, IMessage>(
			UpdatePolisherMessage.UpdatePolisherMessageHandler.class, 
			UpdatePolisherMessage.class, 
			Side.CLIENT, 
			3
	);
	
	public static final MessageHandler<RequestUpdatePolisherMessage, UpdatePolisherMessage> MSG_REQUEST_POLISHER = new MessageHandler<RequestUpdatePolisherMessage, UpdatePolisherMessage>(
			RequestUpdatePolisherMessage.RequestUpdatePolisherMessageHandler.class, 
			RequestUpdatePolisherMessage.class, 
			Side.SERVER, 
			4
	);
	
	
	public static void init() {}
}
