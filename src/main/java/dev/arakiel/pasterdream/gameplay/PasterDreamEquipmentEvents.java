package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.gemini.event.GeminiEvents;
import dev.arakiel.gemini.event.entity.living.LivingEquipmentChangeEvent;
import dev.arakiel.gemini.event.server.ServerTickEvent;
import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import dev.arakiel.pasterdream.config.PasterDreamConfig;
import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.content.PasterDreamEffects;
import dev.arakiel.pasterdream.content.PasterDreamItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class PasterDreamEquipmentEvents {
	private static final ResourceKey<Level> AARONCOS_ARENA =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("aaroncos_arena_world"));

	private PasterDreamEquipmentEvents() {
	}

	public static void register() {
		GeminiEvents.listen(LivingEquipmentChangeEvent.class, event -> {
			if (!(event.getLivingEntity() instanceof ServerPlayer player)) {
				return;
			}
			if (event.getSlot() == EquipmentSlot.LEGS) {
				boolean previous = event.getPrevious().is(PasterDreamItems.QYM_ARMOR_LEGGINGS);
				boolean current = event.getCurrent().is(PasterDreamItems.QYM_ARMOR_LEGGINGS);
				if (previous != current) {
					PasterDreamPlayerState.setMeltDreamFreeUses(player, current ? 1 : 0);
				}
			}
			if (event.getSlot() == EquipmentSlot.CHEST && !player.isCreative()) {
				boolean previous = grantsFlight(event.getPrevious());
				boolean current = grantsFlight(event.getCurrent());
				if (previous != current) {
					player.getAbilities().mayfly = current && !PasterDreamConfig.common().disableWings;
					if (!player.getAbilities().mayfly) {
						player.getAbilities().flying = false;
					}
					player.onUpdateAbilities();
				}
			}
		});

		GeminiEvents.listen(ServerTickEvent.class, event -> {
			if (event.getPhase() != ServerTickEvent.Phase.POST) {
				return;
			}
			for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
				tickWings(player);
			}
		});
	}

	private static void tickWings(ServerPlayer player) {
		ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
		boolean disabled = PasterDreamConfig.common().disableWings;
		if (disabled) {
			if (!player.isCreative() && player.getAbilities().mayfly) {
				player.getAbilities().mayfly = false;
				player.getAbilities().flying = false;
				player.onUpdateAbilities();
			}
			return;
		}

		if (chest.is(PasterDreamItems.FORSAKENS_WING_CHESTPLATE)) {
			player.fallDistance = 0.0F;
			player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
			player.removeEffect(MobEffects.WITHER);
			enableFlight(player);
			return;
		}
		if (chest.is(PasterDreamItems.ANGEL_WING_CHESTPLATE)) {
			player.fallDistance = 0.0F;
			enableFlight(player);
			return;
		}
		if (chest.is(PasterDreamItems.MACHINE_WING_CHESTPLATE) && player.tickCount % 20 == 0) {
			double rate = player.level().dimension().equals(AARONCOS_ARENA) ? 1.0D : 0.02D;
			if (PasterDreamPlayerState.consumeMeltDreamEnergy(player, rate)) {
				player.addEffect(new MobEffectInstance(
					PasterDreamContent.effectHolder(PasterDreamEffects.MACHINE_WING_EFFECT),
					22,
					0,
					false,
					false
				));
				enableFlight(player);
			} else if (!player.isCreative()) {
				player.getAbilities().mayfly = false;
				player.getAbilities().flying = false;
				player.onUpdateAbilities();
			}
		}
	}

	private static boolean grantsFlight(ItemStack stack) {
		return stack.is(PasterDreamItems.QYM_ARMOR_CHESTPLATE)
			|| stack.is(PasterDreamItems.MACHINE_WING_CHESTPLATE)
			|| stack.is(PasterDreamItems.FORSAKENS_WING_CHESTPLATE)
			|| stack.is(PasterDreamItems.ANGEL_WING_CHESTPLATE);
	}

	private static void enableFlight(ServerPlayer player) {
		if (!player.getAbilities().mayfly) {
			player.getAbilities().mayfly = true;
			player.onUpdateAbilities();
		}
	}
}
