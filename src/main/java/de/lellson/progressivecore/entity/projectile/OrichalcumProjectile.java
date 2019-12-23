package de.lellson.progressivecore.entity.projectile;

import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class OrichalcumProjectile extends FantasyProjectile {
	
	public OrichalcumProjectile(World world) {
		super(world);
	}
	
	public OrichalcumProjectile(World world, double x, double y, double z, ItemStack weapon) {
        super(world, x, y, z, weapon);
    }

    public OrichalcumProjectile(World world, EntityLivingBase thrower, ItemStack weapon) {
        super(world, thrower, weapon);
    }

	@Override
	public float getDamageMultiplier() {
		return ProConfig.orichalcumDamage;
	}

	@Override
	public float getSpeedMultiplier() {
		return ProConfig.orichalcumSpeed;
	}
	
	@Override
	public float getParticleAmount() {
		return 6;
	}
}
