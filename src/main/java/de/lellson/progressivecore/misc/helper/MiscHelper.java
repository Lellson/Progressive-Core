package de.lellson.progressivecore.misc.helper;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.FMLLog;

public class MiscHelper {
	
	public static String upperFirst(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static int[] toIntArray(String[] stringArray, int defaultFiller) {
		
		int[] array = new int[stringArray.length];
		
		for (int i = 0; i < stringArray.length; i++)
		{
			try 
			{
				array[i] = Integer.parseInt(stringArray[i]);
			}
			catch(NumberFormatException e)
			{
				FMLLog.bigWarning("ProgressiveCore: " + stringArray[i] + " is not a valid number!");
				array[i] = defaultFiller;
			}
		}
		
		return array;
	}

	public static boolean isInArray(int toSearch,int[] values) {

		for (int value : values)
			if (value == toSearch)
				return true;
		
		return false;
	}

	public static String getRomanNumber(int number) {
		switch(number)
		{
			case 0: return "";
			case 1: return "I";
			case 2: return "II";
			case 3: return "III";
			case 4: return "IV";
			case 5: return "V";
			case 6: return "VI";
			case 7: return "VII";
			case 8: return "VIII";
			case 9: return "IX";
			case 10: return "X";
			default: return "X+";
		}
	}
	
	public static void knockback(Entity target, Entity entity, double multiplierX, double multiplierY, double multiplierZ) {
		
		target.motionX += (double) (-MathHelper.sin(entity.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(entity.rotationPitch / 180.0F * (float) Math.PI) * multiplierX);
		target.motionY += 0.1D * multiplierY;
		target.motionZ += (double) (MathHelper.cos(entity.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(entity.rotationPitch / 180.0F * (float) Math.PI) * multiplierZ);
	}
}
