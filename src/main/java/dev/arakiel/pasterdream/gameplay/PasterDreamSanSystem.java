package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import dev.arakiel.pasterdream.config.PasterDreamCommonConfig;
import dev.arakiel.pasterdream.config.PasterDreamConfig;
import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.content.PasterDreamEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;

public final class PasterDreamSanSystem {
	private static final ResourceKey<Level> LAMP_SHADOW =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("lamp_shadow_world"));
	private static final ResourceKey<Level> AARONCOS_ARENA =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("aaroncos_arena_world"));
	private static final ResourceKey<Level> DYEDREAM =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("dyedream_world"));
	private static final ResourceKey<Level> WIND_JOURNEY =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("wind_journey_world"));
	private static final ResourceLocation ENVIRONMENT_MODIFIER =
		PasterDream.id("environment_san_variability");

	private PasterDreamSanSystem() {
	}

	public static void tick(ServerPlayer player) {
		PasterDreamCommonConfig config = PasterDreamConfig.common();
		int interval = Math.max(1, config.playerUpdateInterval);
		if (player.tickCount % interval != 0) {
			return;
		}
		var san = PasterDreamPlayerState.san(player);
		if (!san.enabled() || !PasterDreamGameRules.sanSystemEnabled(player) || player.isSpectator()) {
			return;
		}

		AttributeInstance variability = player.getAttribute(
			PasterDreamAttributes.holder(PasterDreamAttributes.SAN_VARIABILITY)
		);
		if (variability == null) {
			return;
		}

		variability.addOrUpdateTransientModifier(new AttributeModifier(
			ENVIRONMENT_MODIFIER,
			environmentRate(player, config),
			AttributeModifier.Operation.ADD_VALUE
		));

		if (config.lowSanDebuff) {
			applyLowSanEffects(player, san.value(), config.cheerUpThreshold);
		}
		if (player.level().dimension().equals(WIND_JOURNEY)) {
			addEffect(player, PasterDreamEffects.CLOUDMIST_BUFF, 200, 0);
		}

		double change = interval * variability.getValue() / 1200.0D;
		if (change != 0.0D) {
			PasterDreamPlayerState.addSan(player, change);
		}
	}

	private static double environmentRate(ServerPlayer player, PasterDreamCommonConfig config) {
		double rate = 0.0D;
		var dimension = player.level().dimension();
		if (dimension.equals(Level.OVERWORLD)
			&& !player.level().isDay()
			&& config.overworldNightLowersSan) {
			rate += config.overworldNightSanRate;
		}
		if (dimension.equals(LAMP_SHADOW)) {
			rate += config.lampShadowSanRate;
		}
		if (dimension.equals(AARONCOS_ARENA)) {
			rate += config.aaroncosArenaSanRate;
		}
		if (dimension.equals(DYEDREAM)) {
			rate += config.dyedreamSanRate;
		}
		if (dimension.equals(WIND_JOURNEY)) {
			rate += config.windJourneySanRate;
		}
		return rate;
	}

	private static void applyLowSanEffects(ServerPlayer player, double san, double cheerUpThreshold) {
		if (san <= 1.0D) {
			addEffect(player, PasterDreamEffects.INSAND_BUFF, 20, 2);
		} else if (san <= 10.0D) {
			addEffect(player, PasterDreamEffects.INSAND_BUFF, 20, 1);
		} else if (san <= 20.0D) {
			addEffect(player, PasterDreamEffects.INSAND_BUFF, 20, 0);
		} else if (san <= 40.0D) {
			addEffect(player, PasterDreamEffects.TRANCE_BUFF, 20, 0);
		} else if (san <= 60.0D) {
			addEffect(player, PasterDreamEffects.LETHARGY_BUFF, 20, 0);
		}
		if (san >= cheerUpThreshold) {
			addEffect(player, PasterDreamEffects.CHEERUP_BUFF, 20, 0);
		}
	}

	private static void addEffect(ServerPlayer player, MobEffect effect, int duration, int amplifier) {
		Holder<MobEffect> holder = PasterDreamContent.effectHolder(effect);
		player.addEffect(new MobEffectInstance(holder, duration, amplifier, false, false));
	}
}
