package de.lellson.progressivecore.entity.render;

import org.lwjgl.opengl.GL11;

import de.lellson.progressivecore.misc.Constants;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBolt extends Render {
	
	private ResourceLocation texture;
	private ModelBase model;
	private float size;
	
	public RenderBolt(RenderManager renderManager, ModelBase model, String texture, float size) {
		super(renderManager);
		
		this.model = model;
		this.texture = new ResourceLocation(Constants.prefix("textures/models/entity/" + texture));
		this.size = size;
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick) {
		
		GL11.glPushMatrix();
		bindTexture(texture);
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(entity.ticksExisted % 90 * 4, 1, 1, 1);
		model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F * size);
		GL11.glRotatef(30F, (float)x, (float)y, (float)z);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
