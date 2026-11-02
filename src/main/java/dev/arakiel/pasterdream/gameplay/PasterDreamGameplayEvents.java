package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.gemini.event.GeminiEvents;
import dev.arakiel.gemini.event.entity.living.LivingHealEvent;
import dev.arakiel.gemini.event.entity.living.LivingHurtEvent;
import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.content.PasterDreamEffects;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuOpener;
import dev.arakiel.pasterdream.inventory.PasterDreamInventoryStorage;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class PasterDreamGameplayEvents {
	private PasterDreamGameplayEvents() {
	}

	public static void initialize() {
		PasterDreamConsumables.register();
		PasterDreamEquipmentEvents.register();
		PasterDreamBlockEvents.register();
		PasterDreamMenuOpener.register();
		PasterDreamInventoryStorage.register();
		PasterDreamBlockBehavior.register();
		PasterDreamItemBehavior.register();
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayer player : server.getPlayerList().getPlayers()) {
				if (player.getTags().contains("pasterdream_guard_block")
					&& !player.hasEffect(PasterDreamContent.effectHolder(
						PasterDreamEffects.GUARD_BLOCK_BUFF
					))) {
					player.removeTag("pasterdream_guard_block");
					player.setGameMode(net.minecraft.world.level.GameType.SURVIVAL);
				}
				if (player.getTags().contains("pasterdream_machine_wing")
					&& !player.hasEffect(PasterDreamContent.effectHolder(
						PasterDreamEffects.MACHINE_WING_EFFECT
					))) {
					player.removeTag("pasterdream_machine_wing");
					if (!player.isCreative() && !player.isSpectator()) {
						player.getAbilities().mayfly = false;
						player.getAbilities().flying = false;
						player.onUpdateAbilities();
					}
				}
			}
		});

		GeminiEvents.listen(LivingHurtEvent.class, event -> {
			LivingEntity entity = event.getLivingEntity();
			if (entity.level().isClientSide()) {
				return;
			}
			var evasion = PasterDreamContent.effectHolder(PasterDreamEffects.EVASION_BUFF);
			if (entity instanceof Player && entity.hasEffect(evasion)) {
				MobEffectInstance instance = entity.getEffect(evasion);
				entity.removeEffect(evasion);
				if (instance != null && instance.getAmplifier() > 0) {
					entity.addEffect(new MobEffectInstance(
						evasion,
						instance.getDuration(),
						instance.getAmplifier() - 1,
						false,
						false
					));
				}
				event.setCanceled(true);
				return;
			}
			var vulnerability = PasterDreamContent.effectHolder(PasterDreamEffects.VULNERABILITY_BUFF);
			MobEffectInstance instance = entity.getEffect(vulnerability);
			if (instance != null) {
				event.setAmount(event.getAmount() * (1.0F + 0.1F * instance.getAmplifier()));
			}
		});

		GeminiEvents.listen(LivingHealEvent.class, event -> {
			LivingEntity entity = event.getLivingEntity();
			if (!(entity instanceof Player player) || player.level().isClientSide()) {
				return;
			}
			double multiplier = player.getAttributeValue(
				PasterDreamAttributes.holder(PasterDreamAttributes.TOTAL_HEALING)
			);
			event.setAmount((float) (event.getAmount() * Math.max(0.0D, multiplier)));
		});
	}
}
