package dev.arakiel.pasterdream.network;

import dev.arakiel.gemini.network.GeminiNetwork;
import dev.arakiel.gemini.network.NetworkChannel;
import dev.arakiel.gemini.network.RecordCodecs;
import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.gameplay.PasterDreamTeleportation;
import dev.arakiel.pasterdream.gameplay.PasterDreamCloakActivation;

public final class PasterDreamNetworking {
	public static final NetworkChannel MAIN = GeminiNetwork.channel(PasterDream.id("main")).optional();

	private PasterDreamNetworking() {
	}

	public static void initialize() {
		MAIN.registerServerbound(
			"teleport",
			PasterDreamActions.TeleportRequest.class,
			RecordCodecs.create(PasterDreamActions.TeleportRequest.class),
			(payload, player) -> PasterDreamTeleportation.activate(player)
		);
		MAIN.registerServerbound(
			"cloak",
			PasterDreamActions.CloakRequest.class,
			RecordCodecs.create(PasterDreamActions.CloakRequest.class),
			(payload, player) -> PasterDreamCloakActivation.activate(player)
		);
	}
}
