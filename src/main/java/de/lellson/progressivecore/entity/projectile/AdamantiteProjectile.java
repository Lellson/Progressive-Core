package de.lellson.progressivecore.entity.projectile;

import de.lellson.progressivecore.misc.config.ProConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class AdamantiteProjectile extends FantasyProjectile {

	public AdamantiteProjectile(World world) {
		super(world);
	}
	
	public AdamantiteProjectile(World world, double x, double y, double z, ItemStack weapon) {
        super(world, x, y, z, weapon);
    }

    public AdamantiteProjectile(World world, EntityLivingBase thrower, ItemStack weapon) {
        super(world, thrower, weapon);
    }

	@Override
	public float getDamageMultiplier() {
		return ProConfig.adamantiteDamage;
	}

	@Override
	public float getSpeedMultiplier() {
		return ProConfig.adamantiteSpeed;
	}

	@Override
	public float getParticleAmount() {
		return 12;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox() {
		AxisAlignedBB aabb = super.getCollisionBoundingBox();
		return aabb == null ? null : aabb.expand(0.25, 0.25, 0.25).expand(-0.25, -0.25, -0.25);
	}
	
	@Override
	public AxisAlignedBB getEntityBoundingBox() {
		AxisAlignedBB aabb = super.getEntityBoundingBox();
		return aabb == null ? null : aabb.expand(0.25, 0.25, 0.25).expand(-0.25, -0.25, -0.25);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		AxisAlignedBB aabb = super.getRenderBoundingBox();
		return aabb == null ? null : aabb.expand(0.25, 0.25, 0.25).expand(-0.25, -0.25, -0.25);
	}
}
