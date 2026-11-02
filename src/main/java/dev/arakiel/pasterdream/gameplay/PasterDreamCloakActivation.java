package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.curios.api.CuriosApi;
import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.content.PasterDreamEffects;
import dev.arakiel.pasterdream.content.PasterDreamItems;
import dev.arakiel.pasterdream.content.PasterDreamParticles;
import dev.arakiel.pasterdream.content.PasterDreamSounds;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;

public final class PasterDreamCloakActivation {
	private PasterDreamCloakActivation() {
	}

	public static void activate(ServerPlayer player) {
		if (hasCurio(player, PasterDreamItems.TURNBACK_CLOAK)
			&& !player.getCooldowns().isOnCooldown(PasterDreamItems.TURNBACK_CLOAK)) {
			activateCloak(
				player,
				PasterDreamItems.TURNBACK_CLOAK,
				PasterDreamEffects.TURNBACK_CLOAK_BUFF,
				2400,
				(SimpleParticleType) PasterDreamParticles.GOLDEN_PARTICLE
			);
			return;
		}
		if (hasCurio(player, PasterDreamItems.EVASION_CLOAK)
			&& !player.getCooldowns().isOnCooldown(PasterDreamItems.EVASION_CLOAK)) {
			activateCloak(
				player,
				PasterDreamItems.EVASION_CLOAK,
				PasterDreamEffects.EVASION_CLOAK_BUFF,
				1800,
				(SimpleParticleType) PasterDreamParticles.SILVER_PARTICLE
			);
		}
	}

	private static void activateCloak(
		ServerPlayer player,
		Item item,
		MobEffect effect,
		int duration,
		SimpleParticleType particle
	) {
		if (!PasterDreamPlayerState.consumeMeltDreamEnergy(player, 10.0D)) {
			return;
		}
		player.getCooldowns().addCooldown(item, 7800);
		player.addEffect(new MobEffectInstance(
			PasterDreamContent.effectHolder(effect),
			duration,
			0,
			false,
			false
		));
		if (effect == PasterDreamEffects.TURNBACK_CLOAK_BUFF) {
			player.addEffect(new MobEffectInstance(
				PasterDreamContent.effectHolder(PasterDreamEffects.EVASION_BUFF),
				duration,
				5,
				false,
				false
			));
		}
		player.serverLevel().playSound(
			null,
			player.blockPosition(),
			PasterDreamSounds.CLOAK,
			SoundSource.NEUTRAL,
			1.0F,
			1.0F
		);
		player.serverLevel().playSound(
			null,
			player.blockPosition(),
			PasterDreamSounds.EVASION,
			SoundSource.NEUTRAL,
			1.0F,
			1.0F
		);
		player.serverLevel().sendParticles(
			particle,
			player.getX(),
			player.getY() + 1.0D,
			player.getZ(),
			32,
			0.4D,
			0.5D,
			0.4D,
			0.01D
		);
	}

	private static boolean hasCurio(ServerPlayer player, Item item) {
		return CuriosApi.getCuriosInventory(player)
			.flatMap(inventory -> inventory.findFirstCurio(item))
			.isPresent();
	}
}
