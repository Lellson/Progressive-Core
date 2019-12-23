package de.lellson.progressivecore.inv.tile;

import java.util.Random;

import de.lellson.progressivecore.entity.living.EntitySpirit;
import de.lellson.progressivecore.misc.Constants;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityRealityEye extends TileEntity implements ITickable {

	private static final String KEY_SPIRIT_AMOUNT = Constants.prefix("spiritAmount");
	private static final String KEY_TICK = Constants.prefix("tick");
	private static final String KEY_ACTIVE = Constants.prefix("active");
	
	private static final int SUMMON_RATE = 60;
	private static final int MAX_SPIRIT = 10;
	
	private int spiritAmount = 0;
	private int tick = 0;
	private boolean active = false;
	
	@Override
	public void update() {
		
		if (active && tick <= 0)
		{
			summonSpirit(world.rand);
			spiritAmount++;
			tick = SUMMON_RATE;
		}
		
		if (!active && tick > 0)
			tick = 0;
		
		if (spiritAmount < MAX_SPIRIT && active)
			tick--;
	}
	
	public void summonSpirit(Random rnd) {
		
		EntitySpirit spirit = new EntitySpirit(world, pos);
		spirit.setPosition(pos.getX() + rnd.nextInt(12) - 6, Math.max(1, pos.getY() + rnd.nextInt(12) - 6), pos.getZ() + rnd.nextInt(12) - 6);
		if (!world.isRemote)
			world.spawnEntity(spirit);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger(KEY_SPIRIT_AMOUNT, spiritAmount);
		compound.setInteger(KEY_TICK, tick);
		compound.setBoolean(KEY_ACTIVE, active);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.spiritAmount = compound.getInteger(KEY_SPIRIT_AMOUNT);
		this.tick = compound.getInteger(KEY_TICK);
		this.active = compound.getBoolean(KEY_ACTIVE);
	}

	public void onSpiritDeath(EntitySpirit spirit) {
		spiritAmount--;
	}

	public boolean switchActive() {
		return (this.active = !this.active);
	}
	
	public boolean isActive() {
		return active;
	}
}
