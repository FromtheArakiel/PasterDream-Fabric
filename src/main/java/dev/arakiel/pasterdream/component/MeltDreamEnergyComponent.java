package dev.arakiel.pasterdream.component;

import dev.arakiel.gemini.component.ComponentSync;
import dev.arakiel.gemini.component.RespawnableComponent;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public final class MeltDreamEnergyComponent implements
	RespawnableComponent<MeltDreamEnergyComponent>,
	ComponentSync.AutoSyncedComponent
{
	private static final String VALUE = "value";
	private static final String FREE_USES = "free_uses";

	private final Player owner;
	private double value;
	private int freeUses;

	public MeltDreamEnergyComponent(Player owner) {
		this.owner = owner;
	}

	public double value() {
		return value;
	}

	public void setValue(double value) {
		this.value = clamp(value);
	}

	public void addValue(double amount) {
		setValue(value + amount);
	}

	public boolean consume(double amount, boolean creative) {
		if (creative || freeUses > 0) {
			return true;
		}
		if (value < amount) {
			return false;
		}
		addValue(-amount);
		return true;
	}

	public int freeUses() {
		return freeUses;
	}

	public void setFreeUses(int freeUses) {
		this.freeUses = Math.max(0, freeUses);
	}

	public Player owner() {
		return owner;
	}

	@Override
	public boolean shouldCopyForRespawn(boolean lossless, boolean keepInventory, boolean sameCharacter) {
		return lossless || keepInventory;
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registries) {
		if (tag.contains(VALUE)) {
			value = clamp(tag.getDouble(VALUE));
		}
		if (tag.contains(FREE_USES)) {
			freeUses = Math.max(0, tag.getInt(FREE_USES));
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registries) {
		if (value != 0.0D) {
			tag.putDouble(VALUE, value);
		}
		if (freeUses != 0) {
			tag.putInt(FREE_USES, freeUses);
		}
	}

	@Override
	public void copyFrom(MeltDreamEnergyComponent other, HolderLookup.Provider registries) {
		value = other.value;
		freeUses = other.freeUses;
	}

	private static double clamp(double value) {
		return Math.max(0.0D, Math.min(value, 100.0D));
	}
}
