package de.lellson.progressivecore;

import de.lellson.progressivecore.blocks.misc.BlockProMoltenMetal;
import de.lellson.progressivecore.inv.tile.TileEntityGemPolisher;
import de.lellson.progressivecore.inv.tile.TileEntityRealityEye;
import de.lellson.progressivecore.inv.tile.TileEntitySmelter;
import de.lellson.progressivecore.misc.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		
		GameRegistry.registerTileEntity(TileEntitySmelter.TileEntitySmelterBasic.class, new ResourceLocation(Constants.MOD_ID, "tileSmelterBasic"));
		GameRegistry.registerTileEntity(TileEntitySmelter.TileEntitySmelterAdvanced.class, new ResourceLocation(Constants.MOD_ID, "tileSmelterAdvanced"));    
		GameRegistry.registerTileEntity(TileEntitySmelter.TileEntitySmelterInfernal.class, new ResourceLocation(Constants.MOD_ID, "tileSmelterInfernal"));    
		GameRegistry.registerTileEntity(TileEntitySmelter.TileEntitySmelterTitan.class, new ResourceLocation(Constants.MOD_ID, "tileSmelterTitan"));    
		GameRegistry.registerTileEntity(TileEntityRealityEye.class, new ResourceLocation(Constants.MOD_ID, "tileRealityEye"));   
		GameRegistry.registerTileEntity(TileEntityGemPolisher.class, new ResourceLocation(Constants.MOD_ID, "tileGemPolisher"));   
	}

	public void init(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

	}
	
	public EntityPlayer getPlayer(MessageContext ctx) {
		return ctx.getServerHandler().player;
	}
	
	public void renderFluid(BlockProMoltenMetal fluid) {
	}
}
