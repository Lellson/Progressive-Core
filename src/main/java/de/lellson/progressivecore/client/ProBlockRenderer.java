package de.lellson.progressivecore.client;

import java.util.Iterator;

import javax.vecmath.Color3f;

import de.lellson.progressivecore.blocks.ProBlocks;
import de.lellson.progressivecore.blocks.ores.BlockOre;
import de.lellson.progressivecore.items.ProItems;
import de.lellson.progressivecore.misc.config.ProConfig;
import de.lellson.progressivecore.sets.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ProBlockRenderer {
	
	@SubscribeEvent
	public static void highlightOres(DrawBlockHighlightEvent event)
	{
		if (event.getTarget().typeOfHit != Type.BLOCK || ProConfig.eyeRange <= 0)
			return;
		
		EntityPlayer player = event.getPlayer();
		World world = player.world;
		
		BlockPos orbPos = event.getTarget().getBlockPos();
		IBlockState orbState = world.getBlockState(orbPos);
		if (orbState.getBlock() != ProBlocks.REALITY_EYE_ON)
			return;

		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(5.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        
        double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)event.getPartialTicks();
        double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)event.getPartialTicks();
        double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)event.getPartialTicks();
        int xi = (int)Math.round(x), yi = (int)Math.round(y), zi = (int)Math.round(z);
        
        int r = ProConfig.eyeRange;
        Iterator<BlockPos> it = BlockPos.getAllInBox(xi - r, yi - r, zi - r, xi + r, yi + r, zi + r).iterator();
        Color3f color = getColor(player);
        double shrink = getShrink(player);
        while(it.hasNext())
        {
        	BlockPos pos = it.next();
            IBlockState state = world.getBlockState(pos);

            if (isOre(world.getBlockState(pos).getBlock()))
            {
                RenderGlobal.drawSelectionBoundingBox(state.getSelectedBoundingBox(world, pos).shrink(0.4 + shrink).offset(-x, -y, -z), color.x, color.y, color.z, 1F);
            }
        }

        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
	}

	private static boolean isOre(Block block) {
		return block == Sets.MITHRIL_SET_ORE.getBlockOre()
			|| block == Sets.ORICHALCUM_SET_ORE.getBlockOre()
			|| block == Sets.ADAMANTITE_SET_ORE.getBlockOre();
	}

	private static double getShrink(EntityPlayer player) {
		
		int t = player.ticksExisted % 40;
		return t < 20 ? t * 0.0045 : (40-t) * 0.0045;
	}
	
	private static Color3f getColor(EntityPlayer player) {
		return new Color3f(getCVal(player.ticksExisted % 90f) / 45f, getCVal((player.ticksExisted+30) % 90f) / 45f, getCVal((player.ticksExisted+60) % 90f) / 45f);
	}
	
	private static float getCVal(float i) {
		return i < 45 ? i : (90-i);
	}
}
