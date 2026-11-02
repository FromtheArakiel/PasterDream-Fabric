package dev.arakiel.pasterdream.config;

import dev.arakiel.gemini.config.Config;
import dev.arakiel.gemini.config.ConfigEntry;
import dev.arakiel.pasterdream.PasterDream;

@Config(namespace = PasterDream.MOD_ID, value = "client", nested = true)
public final class PasterDreamClientConfig {
	@ConfigEntry("hud.show_only_while_sneaking")
	public boolean showHudOnlyWhileSneaking = false;

	@ConfigEntry("loading.show_tips")
	public boolean showLoadingTips = true;

	@ConfigEntry("hud.paster_health")
	public boolean pasterHealthHud = true;

	@ConfigEntry("hud.meltdream_energy.x")
	public double meltdreamEnergyX = 1.0D;

	@ConfigEntry("hud.meltdream_energy.y")
	public double meltdreamEnergyY = -19.0D;

	@ConfigEntry("hud.san.x")
	public double sanX = -36.0D;

	@ConfigEntry("hud.san.y")
	public double sanY = -34.0D;
}
