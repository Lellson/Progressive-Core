package de.lellson.progressivecore.entity.render;

import de.lellson.progressivecore.misc.Constants;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderVex;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.util.ResourceLocation;

public class RenderSpirit extends RenderVex {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/models/entity/spirit.png");
	
	public RenderSpirit(RenderManager renderer) {
		super(renderer);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityVex entity) {
		return TEXTURE;
	}
}
