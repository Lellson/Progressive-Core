package de.lellson.progressivecore.misc.network.message;

import de.lellson.progressivecore.ProgressiveCore;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SoundMessage implements IMessage {
	
	public double posX, posY, posZ;
	public SoundCategory category;
	public SoundEvent event;
	public float volume;
	public float pitch;
	
	public SoundMessage() {
	}
	
	public SoundMessage(double posX, double posY, double posZ, SoundCategory category, SoundEvent event, float volume, float pitch) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.category = category;
		this.event = event;
		this.volume = volume;
		this.pitch = pitch;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
		buf.writeInt(category.ordinal());
		buf.writeInt(SoundEvent.REGISTRY.getIDForObject(event));
		buf.writeFloat(volume);
		buf.writeFloat(pitch);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		posX = buf.readDouble();
		posY = buf.readDouble();
		posZ = buf.readDouble();
		category = SoundCategory.values()[buf.readInt()];
		event = SoundEvent.REGISTRY.getObjectById(buf.readInt());
		volume = buf.readFloat();
		pitch = buf.readFloat();
	}

	public static class SoundMessageHandler implements IMessageHandler<SoundMessage, SoundMessage> {

		@Override
		public SoundMessage onMessage(SoundMessage message, MessageContext ctx) {
			ClientHelper.playSound(ProgressiveCore.proxy.getPlayer(ctx).world, message.posX, message.posY, message.posZ, message.category, message.event, message.volume, message.pitch);
			return null;
		}
	}
}
