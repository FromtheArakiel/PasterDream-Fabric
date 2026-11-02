package dev.arakiel.pasterdream.config;

import dev.arakiel.gemini.config.ConfigHandle;
import dev.arakiel.gemini.config.GeminiConfig;

public final class PasterDreamConfig {
	private static ConfigHandle<PasterDreamCommonConfig> common;
	private static ConfigHandle<PasterDreamClientConfig> client;

	private PasterDreamConfig() {
	}

	public static void initialize() {
		GeminiConfig.init();
		common = GeminiConfig.register(PasterDreamCommonConfig.class);
		client = GeminiConfig.register(PasterDreamClientConfig.class);
	}

	public static PasterDreamCommonConfig common() {
		return common.value();
	}

	public static PasterDreamClientConfig client() {
		return client.value();
	}
}
