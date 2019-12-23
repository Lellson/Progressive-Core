package de.lellson.progressivecore;

import de.lellson.progressivecore.blocks.misc.BlockProMoltenMetal;
import de.lellson.progressivecore.client.ProBlockRenderer;
import de.lellson.progressivecore.entity.ProEntities;
import de.lellson.progressivecore.inv.tile.TileEntityGemPolisher;
import de.lellson.progressivecore.inv.tile.renderer.RendererGemPolisher;
import de.lellson.progressivecore.misc.ProModelHandler;
import de.lellson.progressivecore.misc.ProTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ProModelHandler.renderFluids();
		MinecraftForge.EVENT_BUS.register(ProModelHandler.class);
		MinecraftForge.EVENT_BUS.register(ProBlockRenderer.class);
		ProEntities.renderInit();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGemPolisher.class, new RendererGemPolisher());
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		MinecraftForge.EVENT_BUS.register(ProTab.class);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@Override
	public EntityPlayer getPlayer(MessageContext ctx) {
		return ctx.side.isClient() ? Minecraft.getMinecraft().player : ctx.getServerHandler().player;
	}
	
	@Override
	public void renderFluid(BlockProMoltenMetal fluid) {
		ModelLoader.setCustomStateMapper(fluid, new StateMap.Builder().ignore(BlockProMoltenMetal.LEVEL).build());
	}
}
