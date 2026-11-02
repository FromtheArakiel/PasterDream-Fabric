package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.content.PasterDreamEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.phys.Vec3;

public final class PasterDreamTeleportation {
	private PasterDreamTeleportation() {
	}

	public static void activate(ServerPlayer player) {
		if (player.isSpectator() || hasEffect(player, PasterDreamEffects.RESTRAINMOVE_BLOCK_BUFF)) {
			return;
		}
		boolean creative = player.getAbilities().instabuild;
		if (!creative && (player.getFoodData().getFoodLevel() <= 6
			|| hasEffect(player, PasterDreamEffects.TELEPORTATION_BUFF))) {
			return;
		}

		Vec3 look = player.getLookAngle();
		double range = player.getAttributeValue(
			PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_RANGE)
		);
		double direction = player.isShiftKeyDown() ? -1.0D : 1.0D;
		double horizontalRange = range * direction;
		double vertical = player.isInWater()
			? look.y
			: look.y * (0.1D * direction) + 0.4D;
		player.setDeltaMovement(new Vec3(look.x * horizontalRange, vertical, look.z * horizontalRange));
		player.hurtMarked = true;

		if (!creative) {
			double cost = player.getAttributeValue(
				PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COST)
			);
			player.causeFoodExhaustion((float) (3.0D * cost));
			double cooldown = player.getAttributeValue(
				PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN)
			);
			addEffect(player, PasterDreamEffects.TELEPORTATION_BUFF, (int) (50.0D * cooldown), 0);
		}

		int evasionTicks = hasEffect(player, PasterDreamEffects.EVASION_CLOAK_BUFF) ? 12 : 2;
		addEffect(player, PasterDreamEffects.EVASION_BUFF, evasionTicks, 0);
	}

	private static boolean hasEffect(ServerPlayer player, net.minecraft.world.effect.MobEffect effect) {
		return player.hasEffect(PasterDreamContent.effectHolder(effect));
	}

	private static void addEffect(ServerPlayer player, net.minecraft.world.effect.MobEffect effect, int ticks, int amplifier) {
		player.addEffect(new MobEffectInstance(
			PasterDreamContent.effectHolder(effect),
			ticks,
			amplifier,
			false,
			false
		));
	}
}
