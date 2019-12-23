package de.lellson.progressivecore.misc;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class ProSounds {

	public static final SoundEvent LIGHT_SABER = createSound("light_saber");
	public static final SoundEvent SCREAM = createSound("scream");
	public static final SoundEvent LEVEL_UP = createSound("level_up");
	
	public static void init()  {}
	
	private static SoundEvent createSound(String name) {
		return new SoundEvent(new ResourceLocation(Constants.MOD_ID, name));
	}
}
