package de.lellson.progressivecore.items.tools.handler;

import java.util.List;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class ToolHandlerSteel extends ToolHandler {
	
	@Override
	public void onEntityKnockback(ItemStack stack, EntityLivingBase attacker, EntityLivingBase target, LivingKnockBackEvent event) {
		event.setStrength(event.getStrength() + 0.2f);
	}
	
	@Override
	public void onTooltip(List<String> lines, World world, ItemStack stack, EntityPlayerSP player) {
		lines.add("Increased knockback");
	}
}
