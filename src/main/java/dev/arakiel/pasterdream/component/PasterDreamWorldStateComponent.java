package dev.arakiel.pasterdream.component;

import dev.arakiel.gemini.component.Component;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public final class PasterDreamWorldStateComponent implements Component {
	private double cloudmistPercent;
	private boolean shadowIntrude;
	private boolean shadowIntrudeEnd;
	private int shadowIntrudeNumber;
	private double tailwindForce;
	private double deadwindForce;
	private boolean lifeCrystalConsumed;

	public PasterDreamWorldStateComponent(Player owner) {
	}

	public double cloudmistPercent() {
		return cloudmistPercent;
	}

	public void setCloudmistPercent(double value) {
		cloudmistPercent = Math.max(0.0D, value);
	}

	public boolean shadowIntrude() {
		return shadowIntrude;
	}

	public void setShadowIntrude(boolean value) {
		shadowIntrude = value;
	}

	public boolean shadowIntrudeEnd() {
		return shadowIntrudeEnd;
	}

	public void setShadowIntrudeEnd(boolean value) {
		shadowIntrudeEnd = value;
	}

	public int shadowIntrudeNumber() {
		return shadowIntrudeNumber;
	}

	public void incrementShadowIntrudeNumber() {
		shadowIntrudeNumber++;
	}

	public void resetShadowIntrusion() {
		shadowIntrude = false;
		shadowIntrudeEnd = false;
		shadowIntrudeNumber = 0;
	}

	public double tailwindForce() {
		return tailwindForce;
	}

	public void setTailwindForce(double value) {
		tailwindForce = value;
	}

	public double deadwindForce() {
		return deadwindForce;
	}

	public void setDeadwindForce(double value) {
		deadwindForce = value;
	}

	public boolean lifeCrystalConsumed() {
		return lifeCrystalConsumed;
	}

	public void setLifeCrystalConsumed(boolean value) {
		lifeCrystalConsumed = value;
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registries) {
		cloudmistPercent = tag.getDouble("cloudmist_percent");
		shadowIntrude = tag.getBoolean("shadow_intrude");
		shadowIntrudeEnd = tag.getBoolean("shadow_intrude_end");
		shadowIntrudeNumber = tag.getInt("shadow_intrude_number");
		tailwindForce = tag.getDouble("tailwind_force");
		deadwindForce = tag.getDouble("deadwind_force");
		lifeCrystalConsumed = tag.getBoolean("life_crystal_consumed");
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registries) {
		tag.putDouble("cloudmist_percent", cloudmistPercent);
		tag.putBoolean("shadow_intrude", shadowIntrude);
		tag.putBoolean("shadow_intrude_end", shadowIntrudeEnd);
		tag.putInt("shadow_intrude_number", shadowIntrudeNumber);
		tag.putDouble("tailwind_force", tailwindForce);
		tag.putDouble("deadwind_force", deadwindForce);
		tag.putBoolean("life_crystal_consumed", lifeCrystalConsumed);
	}
}
