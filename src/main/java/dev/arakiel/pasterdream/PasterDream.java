package dev.arakiel.pasterdream;

import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.config.PasterDreamConfig;
import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import dev.arakiel.pasterdream.network.PasterDreamNetworking;
import dev.arakiel.pasterdream.gameplay.PasterDreamGameplayEvents;
import dev.arakiel.pasterdream.gameplay.PasterDreamGameRules;
import dev.arakiel.pasterdream.gameplay.PasterDreamBlueprints;
import dev.arakiel.pasterdream.worldgen.PasterDreamWorldgenModifications;
import dev.arakiel.gemini.event.GeminiEvents;
import dev.arakiel.gemini.event.server.PlayerLoggedInEvent;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasterDream implements ModInitializer {
	public static final String MOD_ID = "pasterdream";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		PasterDreamContent.initialize();
		PasterDreamWorldgenModifications.initialize();
		PasterDreamConfig.initialize();
		PasterDreamNetworking.initialize();
		PasterDreamGameplayEvents.initialize();
		PasterDreamGameRules.initialize();
		PasterDreamBlueprints.initialize();
		GeminiEvents.listen(PlayerLoggedInEvent.class, event -> {
			if (event.getPlayer() instanceof ServerPlayer player) {
				PasterDreamPlayerState.setSanEnabled(player, PasterDreamGameRules.sanSystemEnabled(player));
				PasterDreamPlayerState.sync(player);
			}
		});
		LOGGER.info("Initialized PasterDream content");
	}
}
