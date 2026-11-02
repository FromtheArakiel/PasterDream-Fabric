package dev.arakiel.pasterdream.config;

import dev.arakiel.gemini.config.Config;
import dev.arakiel.gemini.config.ConfigEntry;
import dev.arakiel.gemini.config.Range;
import dev.arakiel.pasterdream.PasterDream;

@Config(namespace = PasterDream.MOD_ID, value = "common", nested = true)
public final class PasterDreamCommonConfig {
	@ConfigEntry("san.overworld_night_lowers")
	public boolean overworldNightLowersSan = true;

	@ConfigEntry("worldgen.generate_dyedream_cracks")
	public boolean generateDyedreamCracks = true;

	@ConfigEntry("san.low_value_debuff")
	public boolean lowSanDebuff = true;

	@ConfigEntry("san.overworld_night_rate")
	public double overworldNightSanRate = -0.96D;

	@ConfigEntry("san.lamp_shadow_rate")
	public double lampShadowSanRate = -2.4D;

	@ConfigEntry("san.aaroncos_arena_rate")
	public double aaroncosArenaSanRate = -9.6D;

	@ConfigEntry("san.dyedream_rate")
	public double dyedreamSanRate = 4.8D;

	@ConfigEntry("san.wind_journey_rate")
	public double windJourneySanRate = 1.2D;

	@ConfigEntry("san.cheer_up_threshold")
	@Range(min = 0.0D, max = 100.0D)
	public double cheerUpThreshold = 99.0D;

	@ConfigEntry("loot.meltdream_chest_legend_multiplier")
	@Range(min = 0.0D, max = 100.0D)
	public double meltdreamChestLegendMultiplier = 1.0D;

	@ConfigEntry("loot.meltdream_chest_rare_multiplier")
	@Range(min = 0.0D, max = 100.0D)
	public double meltdreamChestRareMultiplier = 1.0D;

	@ConfigEntry("san.sleep_recovery")
	@Range(min = 0.0D, max = 100.0D)
	public double sleepSanRecovery = 10.0D;

	@ConfigEntry("san.low_value_screen_jitter")
	public boolean lowSanScreenJitter = true;

	@ConfigEntry("worldgen.origin_dyedream_crack")
	public boolean generateOriginDyedreamCrack = false;

	@ConfigEntry("announce_on_join")
	public boolean announceOnJoin = true;

	@ConfigEntry("lamp_shadow.give_pale_bone_needle")
	public boolean givePaleBoneNeedleInLampShadow = false;

	@ConfigEntry("worldgen.disable_dyedream_crack_return")
	public boolean disableDyedreamCrackReturn = false;

	@ConfigEntry("worldgen.generate_dyedream_origin_island")
	public boolean generateDyedreamOriginIsland = true;

	@ConfigEntry("shadow_npc.return_after_third_dialogue")
	public boolean returnAfterThirdShadowDialogue = true;

	@ConfigEntry("player.update_interval")
	@Range(min = 1.0D, max = 200.0D)
	public int playerUpdateInterval = 5;

	@ConfigEntry("feature.disable_wings")
	public boolean disableWings = false;

	@ConfigEntry("feature.disable_terra_sword")
	public boolean disableTerraSword = false;

	@ConfigEntry("feature.disable_fire_necklace")
	public boolean disableFireNecklace = false;

	@ConfigEntry("feature.disable_time_hourglass")
	public boolean disableTimeHourglass = false;
}
