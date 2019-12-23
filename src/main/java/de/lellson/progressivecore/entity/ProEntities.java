package de.lellson.progressivecore.entity;

import de.lellson.progressivecore.ProgressiveCore;
import de.lellson.progressivecore.entity.living.EntitySpirit;
import de.lellson.progressivecore.entity.models.ModelBolt;
import de.lellson.progressivecore.entity.projectile.AdamantiteProjectile;
import de.lellson.progressivecore.entity.projectile.MithrilProjectile;
import de.lellson.progressivecore.entity.projectile.OrichalcumProjectile;
import de.lellson.progressivecore.entity.render.RenderBolt;
import de.lellson.progressivecore.entity.render.RenderSpirit;
import de.lellson.progressivecore.misc.Constants;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ProEntities {

	private static int entityId = 0;
	
	public static void init() {
		registerEntity(MithrilProjectile.class, "projectile_mithril", 64, 10, true);
		registerEntity(OrichalcumProjectile.class, "projectile_orichalcum", 64, 10, true);
		registerEntity(AdamantiteProjectile.class, "projectile_adamantite", 64, 10, true);
		registerEntity(EntitySpirit.class, "spirit", 80, 1, true);
	}
	
	public static void renderInit() {
		registerRendererBolt(MithrilProjectile.class, "projectile_mithril", new ModelBolt(), 1);
		registerRendererBolt(OrichalcumProjectile.class, "projectile_orichalcum", new ModelBolt(), 1);
		registerRendererBolt(AdamantiteProjectile.class, "projectile_adamantite", new ModelBolt(), 2);
		
		RenderingRegistry.registerEntityRenderingHandler(EntitySpirit.class, new IRenderFactory() {
			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderSpirit(manager);
			}
		});
	}
	
	private static void registerEntity(Class<? extends Entity> entityClass, String name, int track, int update, boolean sendUpdate) {
		EntityRegistry.registerModEntity(Constants.registry(name), entityClass, name, ++entityId, ProgressiveCore.instance, track, update, sendUpdate);
	}
	
	private static void registerRendererBolt(Class<? extends Entity> entityClass, String textureName, ModelBase model, float size) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, new IRenderFactory() {
			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderBolt(manager, model, textureName + ".png", size);
			}
		});
	}
}
