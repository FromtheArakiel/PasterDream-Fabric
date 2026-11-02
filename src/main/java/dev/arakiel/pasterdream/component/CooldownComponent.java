package dev.arakiel.pasterdream.component;

import dev.arakiel.gemini.component.Component;
import dev.arakiel.gemini.component.ComponentLifecycle;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public final class CooldownComponent implements Component, ComponentLifecycle.ServerTicking {
	private final Player owner;
	private final Map<String, Integer> ticks = new LinkedHashMap<>();

	public CooldownComponent(Player owner) {
		this.owner = owner;
	}

	public void start(String id, int duration) {
		if (duration > 0) {
			ticks.put(id, duration);
		}
	}

	public int remaining(String id) {
		return ticks.getOrDefault(id, 0);
	}

	public boolean ready(String id) {
		return remaining(id) <= 0;
	}

	public void clear(String id) {
		ticks.remove(id);
	}

	@Override
	public void serverTick() {
		if (owner.level().isClientSide() || ticks.isEmpty()) {
			return;
		}
		Iterator<Map.Entry<String, Integer>> iterator = ticks.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Integer> entry = iterator.next();
			int next = entry.getValue() - 1;
			if (next <= 0) {
				iterator.remove();
			} else {
				entry.setValue(next);
			}
		}
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registries) {
		ticks.clear();
		for (String id : tag.getAllKeys()) {
			int remaining = tag.getInt(id);
			if (remaining > 0) {
				ticks.put(id, remaining);
			}
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registries) {
		ticks.forEach(tag::putInt);
	}
}
