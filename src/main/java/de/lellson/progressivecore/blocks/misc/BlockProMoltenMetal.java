package de.lellson.progressivecore.blocks.misc;

import de.lellson.progressivecore.misc.ProModelHandler;
import de.lellson.progressivecore.misc.ProRegistry;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockProMoltenMetal extends BlockFluidClassic {
	
	private final int color;
	
	public BlockProMoltenMetal(Fluid fluid, int color) {
		super(fluid, Material.LAVA);
		this.color = color;
		setRegistryName("molten_" + fluid.getName());
		setUnlocalizedName(getRegistryName().toString());
		setLightLevel(1.0f);
		ProRegistry.register(this);
		ProModelHandler.register(this);
	}
	
	public int getColor() {
		return color;
	}
}
