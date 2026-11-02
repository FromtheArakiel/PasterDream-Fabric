package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;

public final class PasterDreamGameRules {
	public static final GameRules.Key<GameRules.BooleanValue> SAN_SYSTEM =
		GameRuleRegistry.register(
			"pasterdreamSanSystem",
			GameRules.Category.PLAYER,
			GameRuleFactory.createBooleanRule(true, PasterDreamGameRules::onSanSystemChanged)
		);

	public static final GameRules.Key<GameRules.IntegerValue> START_SAN_ON_RESPAWN =
		GameRuleRegistry.register(
			"pasterdreamStartSanOnRespawn",
			GameRules.Category.PLAYER,
			GameRuleFactory.createIntRule(90, 0, 100)
		);
	public static final GameRules.Key<GameRules.IntegerValue> RANDOM_COORD_X =
		GameRuleRegistry.register(
			"randomCoordX",
			GameRules.Category.SPAWNING,
			GameRuleFactory.createIntRule(0)
		);
	public static final GameRules.Key<GameRules.IntegerValue> RANDOM_COORD_Z =
		GameRuleRegistry.register(
			"randomCoordZ",
			GameRules.Category.SPAWNING,
			GameRuleFactory.createIntRule(0)
		);
	public static final GameRules.Key<GameRules.BooleanValue> DEBUG_MODE =
		GameRuleRegistry.register(
			"pasterdreamDebugmode",
			GameRules.Category.MISC,
			GameRuleFactory.createBooleanRule(false)
		);
	public static final GameRules.Key<GameRules.IntegerValue> WIND_DIRECTION =
		GameRuleRegistry.register(
			"pasterdreamWindDirection",
			GameRules.Category.MISC,
			GameRuleFactory.createIntRule(0, 0, 7)
		);
	public static final GameRules.Key<GameRules.IntegerValue> SAN_VARIABILITY_PER_TICK =
		GameRuleRegistry.register(
			"pasterdreamSanVariabilityPerTick",
			GameRules.Category.PLAYER,
			GameRuleFactory.createIntRule(5, 0)
		);

	private PasterDreamGameRules() {
	}

	public static void initialize() {
		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
			if (newPlayer.level().getGameRules().getBoolean(SAN_SYSTEM)) {
				PasterDreamPlayerState.setSan(
					newPlayer,
					newPlayer.level().getGameRules().getInt(START_SAN_ON_RESPAWN)
				);
			}
		});
	}

	public static boolean sanSystemEnabled(net.minecraft.world.entity.player.Player player) {
		return player.level().getGameRules().getBoolean(SAN_SYSTEM);
	}

	private static void onSanSystemChanged(MinecraftServer server, GameRules.BooleanValue value) {
		for (var player : server.getPlayerList().getPlayers()) {
			PasterDreamPlayerState.setSanEnabled(player, value.get());
		}
	}
}
