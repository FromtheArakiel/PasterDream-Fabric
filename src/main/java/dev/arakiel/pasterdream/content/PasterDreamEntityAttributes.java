package dev.arakiel.pasterdream.content;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class PasterDreamEntityAttributes {
	private PasterDreamEntityAttributes() {
	}

	public static AttributeSupplier.Builder create(String id) {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		switch (id) {
			case "aaroncos_lefthand_0" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.2500D);
				builder.add(Attributes.MAX_HEALTH, 500.0000D);
				builder.add(Attributes.ARMOR, 10.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 20.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 32.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0000D);
				builder.add(Attributes.FLYING_SPEED, 0.2500D);
			}
			case "aaroncos_righthand_0" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.4000D);
				builder.add(Attributes.MAX_HEALTH, 500.0000D);
				builder.add(Attributes.ARMOR, 4.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 18.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 32.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0000D);
				builder.add(Attributes.FLYING_SPEED, 0.4000D);
			}
			case "ash_bone_wing" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.4000D);
				builder.add(Attributes.MAX_HEALTH, 32.0000D);
				builder.add(Attributes.ARMOR, 3.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 3.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 20.0000D);
				builder.add(Attributes.FLYING_SPEED, 0.4000D);
			}
			case "basalt_snail" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.1000D);
				builder.add(Attributes.MAX_HEALTH, 10.0000D);
				builder.add(Attributes.ARMOR, 10.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 0.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
			}
			case "black_beetle" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.2000D);
				builder.add(Attributes.MAX_HEALTH, 12.0000D);
				builder.add(Attributes.ARMOR, 5.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 4.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
			}
			case "black_beetle_mother" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.2500D);
				builder.add(Attributes.MAX_HEALTH, 100.0000D);
				builder.add(Attributes.ARMOR, 10.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 10.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0000D);
			}
			case "bone_wing" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.4000D);
				builder.add(Attributes.MAX_HEALTH, 20.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 3.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 20.0000D);
				builder.add(Attributes.FLYING_SPEED, 0.4000D);
			}
			case "crazy_terrorbeak" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.3100D);
				builder.add(Attributes.MAX_HEALTH, 60.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 20.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 24.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0000D);
			}
			case "firefly" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.3000D);
				builder.add(Attributes.MAX_HEALTH, 2.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 0.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
				builder.add(Attributes.FLYING_SPEED, 0.3000D);
			}
			case "fox_fire" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.0000D);
				builder.add(Attributes.MAX_HEALTH, 2.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 3.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
			}
			case "friendly_ghost" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.8000D);
				builder.add(Attributes.MAX_HEALTH, 10.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 4.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 20.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5000D);
				builder.add(Attributes.FLYING_SPEED, 0.8000D);
			}
			case "golden_fox" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.0000D);
				builder.add(Attributes.MAX_HEALTH, 8.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 3.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
			}
			case "healing_spell_entity" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.0000D);
				builder.add(Attributes.MAX_HEALTH, 10.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 3.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
			}
			case "highvoltage_thundercloud" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.1500D);
				builder.add(Attributes.MAX_HEALTH, 50.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 3.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8000D);
				builder.add(Attributes.FLYING_SPEED, 0.1500D);
			}
			case "jellyfish" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.2000D);
				builder.add(Attributes.MAX_HEALTH, 10.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 3.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
				builder.add(Attributes.FLYING_SPEED, 0.2000D);
			}
			case "meltdream_crystal_entity" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.0000D);
				builder.add(Attributes.MAX_HEALTH, 2.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 0.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 0.0000D);
				builder.add(Attributes.FLYING_SPEED, 0.0000D);
			}
			case "pink_chicken" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.3000D);
				builder.add(Attributes.MAX_HEALTH, 5.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 0.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
				builder.add(Attributes.FLYING_SPEED, 0.3000D);
			}
			case "pink_slime" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.1500D);
				builder.add(Attributes.MAX_HEALTH, 6.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 0.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
			}
			case "shadow_ghost" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.8000D);
				builder.add(Attributes.MAX_HEALTH, 10.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 4.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 20.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5000D);
				builder.add(Attributes.FLYING_SPEED, 0.8000D);
			}
			case "shadow_golem" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.2500D);
				builder.add(Attributes.MAX_HEALTH, 150.0000D);
				builder.add(Attributes.ARMOR, 8.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 20.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 20.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.7000D);
				builder.add(Attributes.ATTACK_KNOCKBACK, 0.5000D);
			}
			case "shadow_hand" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.4000D);
				builder.add(Attributes.MAX_HEALTH, 12.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 8.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 4.0000D);
				builder.add(Attributes.FLYING_SPEED, 0.4000D);
			}
			case "shadow_magicball" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.5000D);
				builder.add(Attributes.MAX_HEALTH, 1.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 3.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 32.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 10.0000D);
				builder.add(Attributes.ATTACK_KNOCKBACK, 10.0000D);
				builder.add(Attributes.FLYING_SPEED, 0.5000D);
			}
			case "shadow_npc_0" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.0000D);
				builder.add(Attributes.MAX_HEALTH, 20.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 3.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
			}
			case "shadow_squeal_ghost" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.7000D);
				builder.add(Attributes.MAX_HEALTH, 10.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 5.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 20.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5000D);
				builder.add(Attributes.FLYING_SPEED, 0.7000D);
			}
			case "shadow_squeal_ghost_0" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.8000D);
				builder.add(Attributes.MAX_HEALTH, 20.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 6.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 20.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.4000D);
				builder.add(Attributes.FLYING_SPEED, 0.8000D);
			}
			case "shadow_tune_totem" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.0000D);
				builder.add(Attributes.MAX_HEALTH, 40.0000D);
				builder.add(Attributes.ARMOR, 5.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 0.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 10.0000D);
			}
			case "shaking_crystal" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.0000D);
				builder.add(Attributes.MAX_HEALTH, 50.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 3.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 100.0000D);
			}
			case "small_stone_spirit" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.2500D);
				builder.add(Attributes.MAX_HEALTH, 10.0000D);
				builder.add(Attributes.ARMOR, 4.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 4.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0000D);
			}
			case "spore_entity" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.3000D);
				builder.add(Attributes.MAX_HEALTH, 1.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 0.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
				builder.add(Attributes.FLYING_SPEED, 0.3000D);
			}
			case "terrasword_wave" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.5000D);
				builder.add(Attributes.MAX_HEALTH, 1.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 3.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 64.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 10.0000D);
				builder.add(Attributes.ATTACK_KNOCKBACK, 10.0000D);
				builder.add(Attributes.FLYING_SPEED, 0.5000D);
			}
			case "terrorbeak" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.3000D);
				builder.add(Attributes.MAX_HEALTH, 40.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 16.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 24.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0000D);
			}
			case "thundercloud" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.1500D);
				builder.add(Attributes.MAX_HEALTH, 30.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 3.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8000D);
				builder.add(Attributes.FLYING_SPEED, 0.1500D);
			}
			case "weakeness_terrorbeak" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.3000D);
				builder.add(Attributes.MAX_HEALTH, 30.0000D);
				builder.add(Attributes.ARMOR, 0.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 12.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 24.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0000D);
			}
			case "wind_knight" -> {
				builder.add(Attributes.MOVEMENT_SPEED, 0.2500D);
				builder.add(Attributes.MAX_HEALTH, 150.0000D);
				builder.add(Attributes.ARMOR, 10.0000D);
				builder.add(Attributes.ATTACK_DAMAGE, 20.0000D);
				builder.add(Attributes.FOLLOW_RANGE, 16.0000D);
				builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.4000D);
			}
			default -> {
			}
		}
		return builder;
	}
}
