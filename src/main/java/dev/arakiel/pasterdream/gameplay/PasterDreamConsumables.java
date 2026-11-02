package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.gemini.event.GeminiEvents;
import dev.arakiel.gemini.event.item.ItemUseFinishEvent;
import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.content.PasterDreamEffects;
import dev.arakiel.pasterdream.content.PasterDreamItems;
import dev.arakiel.pasterdream.content.PasterDreamSounds;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class PasterDreamConsumables {
	private PasterDreamConsumables() {
	}

	public static void register() {
		GeminiEvents.listen(ItemUseFinishEvent.class, event -> {
			if (!(event.getUser() instanceof ServerPlayer player)) {
				return;
			}
			ItemStack stack = event.getStack();
			applyEffects(player, stack);
		});
	}

	private static void applyEffects(ServerPlayer player, ItemStack stack) {
		if (stack.is(PasterDreamItems.AMBER_CANDY)) {
			addEffect(player, MobEffects.REGENERATION, 60, 0);
		} else if (stack.is(PasterDreamItems.BLUE_DEW)) {
			addEffect(player, MobEffects.WATER_BREATHING, 1200, 0);
		} else if (stack.is(PasterDreamItems.DYEDREAM_FRUIT)) {
			addEffect(player, MobEffects.REGENERATION, 100, 0);
		} else if (stack.is(PasterDreamItems.POPPING_CANDY)) {
			addEffect(player, MobEffects.JUMP, 400, 3);
			player.serverLevel().sendParticles(
				ParticleTypes.ELECTRIC_SPARK,
				player.getX(),
				player.getY(),
				player.getZ(),
				64,
				3.0D,
				3.0D,
				3.0D,
				1.0D
			);
		} else if (stack.is(PasterDreamItems.RED_DEW)) {
			addEffect(player, MobEffects.HEAL, 1, 0);
		} else if (stack.is(PasterDreamItems.JELLYFISH_JELLO)) {
			addEffect(player, PasterDreamContent.effectHolder(PasterDreamEffects.WINDPROOF_BUFF), 12000, 0);
		} else if (stack.is(PasterDreamItems.DYEDREAM_PERFUME)) {
			addEffect(
				player,
				PasterDreamContent.effectHolder(PasterDreamEffects.DYEDREAM_PERFUME_BUFF),
				1200,
				0
			);
		} else if (stack.is(PasterDreamItems.DREAM_JUICE)) {
			applyDreamJuice(player);
		}
	}

	private static void applyDreamJuice(ServerPlayer player) {
		AdvancementHolder advancement = player.server
			.getAdvancements()
			.get(PasterDream.id("achievement_b_0"));
		if (advancement == null || !player.getAdvancements().getOrStartProgress(advancement).isDone()) {
			player.displayClientMessage(
				Component.translatable("item.pasterdream.dream_juice.requires_advancement"),
				false
			);
			return;
		}
		addEffect(player, PasterDreamContent.effectHolder(PasterDreamEffects.DREAMWISH_BUFF), 1800, 0);
		player.serverLevel().playSound(
			null,
			player.blockPosition(),
			PasterDreamSounds.DREAM1,
			SoundSource.PLAYERS,
			0.1F,
			1.0F
		);
	}

	private static void addEffect(LivingEntity entity, Holder<MobEffect> effect, int duration, int amplifier) {
		entity.addEffect(new MobEffectInstance(effect, duration, amplifier));
	}
}
