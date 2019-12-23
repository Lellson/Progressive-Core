package de.lellson.progressivecore.blocks.misc;

import de.lellson.progressivecore.integration.ProIntegration.Mod;
import de.lellson.progressivecore.misc.Constants;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ProFluid extends Fluid {

	public ProFluid(String fluidName, int temp) {
		super(fluidName, new ResourceLocation(Constants.prefix("fluids/" + fluidName + "_still")), new ResourceLocation(Constants.prefix("fluids/" + fluidName + "_flow")));
		
		setTemperature(temp);
		setLuminosity(10);
		setDensity(2000);
		setViscosity(10000);
		setRarity(EnumRarity.UNCOMMON);
		
		FluidRegistry.registerFluid(this);
		if (Mod.TCONSTRUCT.isLoaded())
			FluidRegistry.addBucketForFluid(this);
	}
}
