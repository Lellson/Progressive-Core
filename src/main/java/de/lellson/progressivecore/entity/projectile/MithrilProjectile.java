package de.lellson.progressivecore.entity.projectile;

import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MithrilProjectile extends FantasyProjectile {

	public MithrilProjectile(World world) {
		super(world);
	}
	
	public MithrilProjectile(World world, double x, double y, double z, ItemStack weapon) {
        super(world, x, y, z, weapon);
    }

    public MithrilProjectile(World world, EntityLivingBase thrower, ItemStack weapon) {
        super(world, thrower, weapon);
    }

	@Override
	public float getDamageMultiplier() {
		return ProConfig.mithrilDamage;
	}

	@Override
	public float getSpeedMultiplier() {
		return ProConfig.mithrilSpeed;
	}
	
	@Override
	public float getParticleAmount() {
		return 6;
	}
}
