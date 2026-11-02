package dev.arakiel.pasterdream.component;

import dev.arakiel.gemini.component.Component;
import dev.arakiel.gemini.component.ComponentLifecycle;
import dev.arakiel.gemini.component.ComponentSync;
import dev.arakiel.gemini.component.RespawnableComponent;
import dev.arakiel.pasterdream.gameplay.PasterDreamSanSystem;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public final class SanComponent implements
	RespawnableComponent<SanComponent>,
	ComponentSync.AutoSyncedComponent,
	ComponentLifecycle.ServerTicking
{
	private static final String VALUE = "value";
	private static final String ENABLED = "enabled";

	private final Player owner;
	private double value = 100.0D;
	private boolean enabled = true;

	public SanComponent(Player owner) {
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

	public boolean enabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Player owner() {
		return owner;
	}

	@Override
	public void serverTick() {
		if (owner instanceof ServerPlayer player) {
			PasterDreamSanSystem.tick(player);
		}
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
		if (tag.contains(ENABLED)) {
			enabled = tag.getBoolean(ENABLED);
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registries) {
		if (value != 100.0D) {
			tag.putDouble(VALUE, value);
		}
		if (!enabled) {
			tag.putBoolean(ENABLED, false);
		}
	}

	@Override
	public void copyFrom(SanComponent other, HolderLookup.Provider registries) {
		value = other.value;
		enabled = other.enabled;
	}

	private static double clamp(double value) {
		return Math.max(0.0D, Math.min(value, 100.0D));
	}
}
