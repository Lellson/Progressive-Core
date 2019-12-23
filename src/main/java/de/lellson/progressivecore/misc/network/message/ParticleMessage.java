package de.lellson.progressivecore.misc.network.message;

import de.lellson.progressivecore.ProgressiveCore;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ParticleMessage implements IMessage {
	
	public double posX, posY, posZ;
	public EnumParticleTypes type;
	public float spread;
	public float amount;
	
	public ParticleMessage() {
	}
	
	public ParticleMessage(double posX, double posY, double posZ, EnumParticleTypes type, float spread, float amount) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.type = type;
		this.spread = spread;
		this.amount = amount;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
		buf.writeInt(type.ordinal());
		buf.writeFloat(spread);
		buf.writeFloat(amount);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		posX = buf.readDouble();
		posY = buf.readDouble();
		posZ = buf.readDouble();
		type = EnumParticleTypes.values()[buf.readInt()];
		spread = buf.readFloat();
		amount = buf.readFloat();
	}
	

	public static class ParticleMessageHandler implements IMessageHandler<ParticleMessage, ParticleMessage> {

		@Override
		public ParticleMessage onMessage(ParticleMessage message, MessageContext ctx) {
			ClientHelper.spawnParticle(ProgressiveCore.proxy.getPlayer(ctx).world, message.posX, message.posY, message.posZ, message.type, message.spread, message.amount);
			return null;
		}
	}
}
