package dev.arakiel.pasterdream.component;

import dev.arakiel.gemini.component.GeminiComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public final class PasterDreamPlayerState {
	private PasterDreamPlayerState() {
	}

	public static SanComponent san(Player player) {
		return GeminiComponents.get(PasterDreamComponentRegistration.san(), player);
	}

	public static MeltDreamEnergyComponent meltDreamEnergy(Player player) {
		return GeminiComponents.get(PasterDreamComponentRegistration.meltDreamEnergy(), player);
	}

	public static CooldownComponent cooldowns(Player player) {
		return GeminiComponents.get(PasterDreamComponentRegistration.cooldowns(), player);
	}

	public static PasterDreamWorldStateComponent worldState(Player player) {
		return GeminiComponents.get(PasterDreamComponentRegistration.worldState(), player);
	}

	public static void setSan(ServerPlayer player, double value) {
		san(player).setValue(value);
		GeminiComponents.sync(PasterDreamComponentRegistration.san(), player);
	}

	public static void setSanEnabled(ServerPlayer player, boolean enabled) {
		san(player).setEnabled(enabled);
		GeminiComponents.sync(PasterDreamComponentRegistration.san(), player);
	}

	public static void addSan(ServerPlayer player, double amount) {
		san(player).addValue(amount);
		GeminiComponents.sync(PasterDreamComponentRegistration.san(), player);
	}

	public static void setMeltDreamEnergy(ServerPlayer player, double value) {
		meltDreamEnergy(player).setValue(value);
		GeminiComponents.sync(PasterDreamComponentRegistration.meltDreamEnergy(), player);
	}

	public static void addMeltDreamEnergy(ServerPlayer player, double amount) {
		meltDreamEnergy(player).addValue(amount);
		GeminiComponents.sync(PasterDreamComponentRegistration.meltDreamEnergy(), player);
	}

	public static void setMeltDreamFreeUses(ServerPlayer player, int freeUses) {
		meltDreamEnergy(player).setFreeUses(freeUses);
		GeminiComponents.sync(PasterDreamComponentRegistration.meltDreamEnergy(), player);
	}

	public static boolean consumeMeltDreamEnergy(ServerPlayer player, double amount) {
		boolean consumed = meltDreamEnergy(player).consume(amount, player.isCreative());
		if (consumed) {
			GeminiComponents.sync(PasterDreamComponentRegistration.meltDreamEnergy(), player);
		}
		return consumed;
	}

	public static void sync(ServerPlayer player) {
		GeminiComponents.sync(PasterDreamComponentRegistration.san(), player);
		GeminiComponents.sync(PasterDreamComponentRegistration.meltDreamEnergy(), player);
	}
}
