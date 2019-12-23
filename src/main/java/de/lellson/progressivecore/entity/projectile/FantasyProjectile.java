package de.lellson.progressivecore.entity.projectile;

import de.lellson.progressivecore.misc.Constants;
import de.lellson.progressivecore.misc.helper.ClientHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public abstract class FantasyProjectile extends EntityThrowable {

	public static final String TAG_WEAPON = Constants.prefix("projWeapon");
	
	protected ItemStack weapon = ItemStack.EMPTY;
	
	public FantasyProjectile(World world) {
		super(world);
		setNoGravity(true);
	}
	
	public FantasyProjectile(World world, double x, double y, double z, ItemStack weapon) {
        super(world, x, y, z);
        this.weapon = weapon;
        setNoGravity(true);
    }

    public FantasyProjectile(World world, EntityLivingBase thrower, ItemStack weapon) {
        super(world, thrower);
        this.weapon = weapon;
        setNoGravity(true);
    }

	@Override
	protected void onImpact(RayTraceResult result) {
		
		if (result.typeOfHit == Type.BLOCK)
		{
			IBlockState state = world.getBlockState(result.getBlockPos());
			if (state.getCollisionBoundingBox(world, result.getBlockPos()) != Block.NULL_AABB)
				setDead();
		}
		else if (result.typeOfHit == Type.ENTITY && !world.isRemote)
		{
			if (result.entityHit == getThrower() || result.entityHit == null || result.entityHit instanceof FantasyProjectile)
				return;
			
			IAttributeInstance weaponDamage = new AttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
			for (AttributeModifier modifier : weapon.getAttributeModifiers(EntityEquipmentSlot.MAINHAND).get(SharedMonsterAttributes.ATTACK_DAMAGE.getName()))
				weaponDamage.applyModifier(modifier);
			
			float damage = (float) (weaponDamage.getAttributeValue()-1) * getDamageMultiplier();
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), damage);
			
			setDead();
		}
	}
	
	public void shoot(EntityLivingBase entity, float rotationPitch, float rotationYaw, float inaccuracy) {
		this.shoot(entity, rotationPitch, rotationYaw, 0, getSpeedMultiplier(), inaccuracy);
		
		this.posX += (double) (-MathHelper.sin(entity.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(entity.rotationPitch / 180.0F * (float) Math.PI) * 0.65);
		this.posZ += (double) (MathHelper.cos(entity.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(entity.rotationPitch / 180.0F * (float) Math.PI) * 0.65);
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		this.motionX *= 0.995;
		this.motionY *= 0.995;
		this.motionZ *= 0.995;
		
		if (this.ticksExisted >= 80)
			setDead();
	}
	
	@Override
	public void setDead() {
		super.setDead();
		
		ClientHelper.spawnParticle(this, EnumParticleTypes.CRIT_MAGIC, 0.5f, getParticleAmount());
		ClientHelper.spawnParticle(this, EnumParticleTypes.SMOKE_NORMAL, 0.35f, getParticleAmount());
		ClientHelper.playSound(this, SoundEvents.BLOCK_GLASS_HIT, 0.5F, 0.5F);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        
        NBTTagCompound comp = new NBTTagCompound();
		comp = weapon.writeToNBT(comp);
        compound.setTag(TAG_WEAPON, comp);
    }

	@Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        
        weapon = new ItemStack((NBTTagCompound)compound.getTag(TAG_WEAPON));
    }
	
	public ItemStack getWeapon() {
		return weapon;
	}
	
	public boolean hasWeapon() {
		return weapon != null && !weapon.isEmpty();
	}
	
	public abstract float getDamageMultiplier();
	
	public abstract float getSpeedMultiplier();
	
	public abstract float getParticleAmount();
}
