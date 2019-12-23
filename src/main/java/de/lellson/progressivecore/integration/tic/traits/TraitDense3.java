package de.lellson.progressivecore.integration.tic.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.traits.TraitDense;

public class TraitDense3 extends AbstractTrait {

	public TraitDense3() {
		super("prog.dense3", 0xDDDDDD);
	}

	@Override
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
		float durability = ToolHelper.getCurrentDurability(tool);
		float maxDurability = ToolHelper.getMaxDurability(tool);

		float chance = 0.75f * (1f - durability / maxDurability);

		if (chance > random.nextFloat()) {
			newDamage -= Math.max(damage / 2, 1);
		}

		return super.onToolDamage(tool, damage, newDamage, entity);
	}
}
