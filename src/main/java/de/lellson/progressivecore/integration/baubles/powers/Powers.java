package de.lellson.progressivecore.integration.baubles.powers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.accessibility.AccessibleBundle;

public class Powers {
	
	public static final List<AbstractPower> POWERS = new ArrayList<AbstractPower>();
	
	public static final AbstractPower VITALITY = new PowerVitality();
	public static final AbstractPower ACCELERATION = new PowerAcceleration();
	public static final AbstractPower POTENCY = new PowerPotency();
	public static final AbstractPower REFLEXION = new PowerReflexion();
	public static final AbstractPower WISDOM = new PowerWisdom();
	public static final AbstractPower LIFESTEAL = new PowerLifesteal();
	public static final AbstractPower SMOOTHNESS = new PowerSmoothness();
	public static final AbstractPower FIREPROOFNESS = new PowerFireproofness();
	public static final AbstractPower CORRUPTION = new PowerCorruption();
	public static final AbstractPower COLDNESS = new PowerColdness();
	
	public static AbstractPower getByName(String name) {
		
		for (AbstractPower power : POWERS)
			if (power.getName().equals(name))
				return power;
		
		return null;
	}

	public static AbstractPower getRandom(Random rnd) {
		return POWERS.get(rnd.nextInt(POWERS.size()));
	}
	
	public static void init() {}
}
