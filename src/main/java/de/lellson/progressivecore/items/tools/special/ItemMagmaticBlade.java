package de.lellson.progressivecore.items.tools.special;

import de.lellson.progressivecore.items.tools.ItemSwordPro;
import de.lellson.progressivecore.items.tools.handler.ToolHandler;
import de.lellson.progressivecore.items.tools.handler.ToolHandlerMagmaticBlade;
import de.lellson.progressivecore.sets.Sets;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public class ItemMagmaticBlade extends ItemSwordPro {

	public ItemMagmaticBlade() {
		super(Sets.HELLSTEEL_TOOL, new ToolHandlerMagmaticBlade(), "magmatic_blade");
	}

	@Override
	protected EnumAction getUseAction(ItemStack stack) {
		return EnumAction.BLOCK;
	}
}
