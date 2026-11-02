package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.pasterdream.PasterDream;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class PasterDreamEffectFactory {
	private PasterDreamEffectFactory() {
	}

	public static MobEffect configure(MobEffect effect, String id) {
		switch (id) {
			case "cheerup_buff" -> {
				add(
					effect,
					"teleportation_cooldown",
					PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN),
					-0.1D
				);
				add(effect, "movement_speed", Attributes.MOVEMENT_SPEED, 0.05D);
				add(effect, "attack_speed", Attributes.ATTACK_SPEED, 0.05D);
				add(
					effect,
					"skill_cooldown",
					PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN),
					-0.1D
				);
			}
			case "cook_buff" -> add(
				effect,
				"san_variability",
				PasterDreamAttributes.holder(PasterDreamAttributes.SAN_VARIABILITY),
				1.2D
			);
			case "counterattack_buff" -> {
				add(effect, "attack_damage", Attributes.ATTACK_DAMAGE, 3.0D);
				add(
					effect,
					"skill_multiplier",
					PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_MULTIPLIER),
					0.5D
				);
			}
			case "dreamharp_of_wanderer_buff" -> {
				add(
					effect,
					"san_variability",
					PasterDreamAttributes.holder(PasterDreamAttributes.SAN_VARIABILITY),
					2.4D
				);
				add(effect, "max_health", Attributes.MAX_HEALTH, 4.0D);
				add(
					effect,
					"movement_speed",
					Attributes.MOVEMENT_SPEED,
					0.01D,
					AttributeModifier.Operation.ADD_MULTIPLIED_BASE
				);
			}
			case "insand_buff" -> {
				add(
					effect,
					"teleportation_cooldown",
					PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN),
					2.0D
				);
				add(
					effect,
					"movement_speed",
					Attributes.MOVEMENT_SPEED,
					-0.3D,
					AttributeModifier.Operation.ADD_MULTIPLIED_BASE
				);
				add(
					effect,
					"attack_speed",
					Attributes.ATTACK_SPEED,
					-0.1D,
					AttributeModifier.Operation.ADD_MULTIPLIED_BASE
				);
				add(effect, "attack_damage", Attributes.ATTACK_DAMAGE, -2.0D);
				add(
					effect,
					"skill_cooldown",
					PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN),
					1.0D
				);
				add(effect, "entity_reach", Attributes.ENTITY_INTERACTION_RANGE, -0.2D);
				add(effect, "block_reach", Attributes.BLOCK_INTERACTION_RANGE, -0.2D);
			}
			case "oppression_buff" -> add(
				effect,
				"san_variability",
				PasterDreamAttributes.holder(PasterDreamAttributes.SAN_VARIABILITY),
				-9.6D
			);
			case "rest_buff" -> add(
				effect,
				"san_variability",
				PasterDreamAttributes.holder(PasterDreamAttributes.SAN_VARIABILITY),
				0.6D
			);
			case "rest_buff_in_dark" -> add(
				effect,
				"san_variability",
				PasterDreamAttributes.holder(PasterDreamAttributes.SAN_VARIABILITY),
				1.2D
			);
			default -> {
			}
		}
		return effect;
	}

	private static void add(
		MobEffect effect,
		String modifier,
		net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
		double amount
	) {
		add(effect, modifier, attribute, amount, AttributeModifier.Operation.ADD_VALUE);
	}

	private static void add(
		MobEffect effect,
		String modifier,
		net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
		double amount,
		AttributeModifier.Operation operation
	) {
		if (!(effect instanceof PasterDreamEffectIdentified identified)) {
			throw new IllegalArgumentException("Effect does not provide a stable identifier");
		}
		effect.addAttributeModifier(
			attribute,
			PasterDream.id("effect/" + identified.effectId() + "/" + modifier),
			amount,
			operation
		);
	}
}
