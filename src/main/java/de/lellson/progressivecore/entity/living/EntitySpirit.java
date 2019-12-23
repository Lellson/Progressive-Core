package de.lellson.progressivecore.entity.living;

import de.lellson.progressivecore.inv.tile.TileEntityRealityEye;
import de.lellson.progressivecore.misc.Constants;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntitySpirit extends EntityVex implements IEntityAdditionalSpawnData {

	private static final String KEY_TILE_X = Constants.prefix("tileX"), KEY_TILE_Y = Constants.prefix("tileY"), KEY_TILE_Z = Constants.prefix("tileZ");
	private BlockPos tilePos;
	
	public EntitySpirit(World world) {
		this(world, BlockPos.ORIGIN);
	}
	
	public EntitySpirit(World world, BlockPos tilePos) {
		super(world);
		this.tilePos = tilePos;
		setLimitedLife(1200);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		tilePos = new BlockPos(compound.getInteger(KEY_TILE_X), compound.getInteger(KEY_TILE_Y), compound.getInteger(KEY_TILE_Z));
	}
	
	@Override
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2.5D);
    }
	
	@Override
	protected void collideWithEntity(Entity entity) {
		super.collideWithEntity(entity);
		
		if (entity instanceof EntityLivingBase)
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
	}
	
	@Override
	protected int getExperiencePoints(EntityPlayer player) {
		return 0;
	}
	
	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		
		TileEntity tile = world.getTileEntity(tilePos);
		if (tile instanceof TileEntityRealityEye)
		{
			((TileEntityRealityEye)tile).onSpiritDeath(this);
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger(KEY_TILE_X, tilePos.getX());
		compound.setInteger(KEY_TILE_Y, tilePos.getY());
		compound.setInteger(KEY_TILE_Z, tilePos.getZ());
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(tilePos.getX());
		buffer.writeInt(tilePos.getY());
		buffer.writeInt(tilePos.getZ());
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		this.tilePos = new BlockPos(buffer.readInt(), buffer.readInt(), buffer.readInt());
	}
}
