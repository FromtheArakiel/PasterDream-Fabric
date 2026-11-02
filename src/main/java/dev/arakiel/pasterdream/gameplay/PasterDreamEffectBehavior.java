package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.component.PasterDreamWorldStateComponent;
import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import dev.arakiel.pasterdream.config.PasterDreamConfig;
import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.content.PasterDreamEffects;
import dev.arakiel.pasterdream.content.PasterDreamEntities;
import dev.arakiel.pasterdream.content.PasterDreamParticles;
import dev.arakiel.pasterdream.content.PasterDreamSounds;
import java.util.Comparator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class PasterDreamEffectBehavior extends MobEffect
	implements PasterDreamEffectIdentified
{
	private static final ResourceKey<Level> WIND_JOURNEY =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("wind_journey_world"));

	private final String effectId;

	public PasterDreamEffectBehavior(String effectId, MobEffectCategory category, int color) {
		super(category, color);
		this.effectId = effectId;
	}

	@Override
	public String effectId() {
		return effectId;
	}

	@Override
	public void onEffectAdded(LivingEntity entity, int amplifier) {
		super.onEffectAdded(entity, amplifier);
		switch (effectId) {
			case "boboji_buff" -> {
				entity.level().playSound(
					null,
					entity.blockPosition(),
					SoundEvents.PARROT_FLY,
					SoundSource.NEUTRAL,
					1.0F,
					1.0F
				);
				entity.level().playSound(
					null,
					entity.blockPosition(),
					SoundEvents.PARROT_AMBIENT,
					SoundSource.NEUTRAL,
					1.0F,
					1.0F
				);
			}
			case "deadwind_buff" -> {
				entity.removeEffect(PasterDreamContent.effectHolder(PasterDreamEffects.TAILWIND_BUFF));
				modify(entity, Attributes.MOVEMENT_SPEED, "deadwind_speed", amplifier == 0 ? -0.02D : -0.03D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_RANGE), "deadwind_range", amplifier == 0 ? -0.3D : -0.5D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), "deadwind_cooldown", amplifier == 0 ? 1.5D : 2.0D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
			}
			case "tailwind_buff" -> {
				entity.removeEffect(PasterDreamContent.effectHolder(PasterDreamEffects.DEADWIND_BUFF));
				modify(entity, Attributes.MOVEMENT_SPEED, "tailwind_speed", amplifier == 0 ? 0.03D : 0.04D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_RANGE), "tailwind_range", amplifier == 0 ? 1.0D : 1.5D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), "tailwind_cooldown", amplifier == 0 ? 0.7D : 0.6D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
			}
			case "flareup_buff" -> {
				modify(entity, Attributes.ATTACK_DAMAGE, "flareup_damage", 3.0D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, Attributes.ATTACK_SPEED, "flareup_attack_speed", 0.2D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_MULTIPLIER), "flareup_skill_power", 0.5D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), "flareup_skill_cooldown", -0.1D, AttributeModifier.Operation.ADD_VALUE);
			}
			case "fury_spell_buff" -> {
				modify(entity, Attributes.ATTACK_DAMAGE, "fury_damage", 4.0D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, Attributes.MOVEMENT_SPEED, "fury_speed", 0.05D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, Attributes.ATTACK_SPEED, "fury_attack_speed", 3.0D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), "fury_skill_cooldown", -0.3D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), "fury_teleport_cooldown", -0.3D, AttributeModifier.Operation.ADD_VALUE);
			}
			case "grail_buff" -> modify(entity, Attributes.MAX_HEALTH, "grail_health", 20.0D, AttributeModifier.Operation.ADD_VALUE);
			case "ice_spell_buff" -> {
				modify(entity, Attributes.MOVEMENT_SPEED, "ice_speed", -1.0D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, Attributes.ATTACK_DAMAGE, "ice_damage", -100.0D, AttributeModifier.Operation.ADD_VALUE);
			}
			case "lethargy_buff" -> {
				modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), "lethargy_teleport", 0.5D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, Attributes.MOVEMENT_SPEED, "lethargy_speed", -0.01D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, Attributes.ATTACK_SPEED, "lethargy_attack_speed", -0.1D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), "lethargy_skill_cooldown", 0.2D, AttributeModifier.Operation.ADD_VALUE);
			}
			case "trance_buff" -> {
				modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), "trance_teleport", 1.0D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, Attributes.MOVEMENT_SPEED, "trance_speed", -0.02D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, Attributes.ATTACK_SPEED, "trance_attack_speed", -0.2D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, Attributes.ATTACK_DAMAGE, "trance_damage", -1.0D, AttributeModifier.Operation.ADD_VALUE);
				modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), "trance_skill_cooldown", 0.5D, AttributeModifier.Operation.ADD_VALUE);
			}
			case "rapid_reaction" -> modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), "rapid_reaction", -0.2D, AttributeModifier.Operation.ADD_VALUE);
			case "memento_buff" -> modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.LUCK), "memento_luck", 10.0D, AttributeModifier.Operation.ADD_VALUE);
			case "snow_vow_buff" -> modify(entity, PasterDreamAttributes.holder(PasterDreamAttributes.LUCK), "snow_vow_luck", 3.0D, AttributeModifier.Operation.ADD_VALUE);
			case "machine_wing_effect" -> {
				if (entity instanceof Player player) {
					player.addTag("pasterdream_machine_wing");
					player.getAbilities().mayfly = true;
					player.getAbilities().flying = true;
					player.onUpdateAbilities();
				}
			}
			case "guard_block_buff" -> {
				if (entity instanceof ServerPlayer player) {
					player.addTag("pasterdream_guard_block");
					player.setGameMode(GameType.ADVENTURE);
				}
			}
			default -> {
			}
		}
	}

	@Override
	public void createModifiers(
		int amplifier,
		java.util.function.BiConsumer<
			net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute>,
			AttributeModifier
		> consumer
	) {
		super.createModifiers(amplifier, consumer);
		switch (effectId) {
			case "deadwind_buff" -> {
				registerModifier(consumer, "deadwind_speed", Attributes.MOVEMENT_SPEED, amplifier == 0 ? -0.02D : -0.03D);
				registerModifier(consumer, "deadwind_range", PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_RANGE), amplifier == 0 ? -0.3D : -0.5D);
				registerModifier(consumer, "deadwind_cooldown", PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), amplifier == 0 ? 1.5D : 2.0D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
			}
			case "tailwind_buff" -> {
				registerModifier(consumer, "tailwind_speed", Attributes.MOVEMENT_SPEED, amplifier == 0 ? 0.03D : 0.04D);
				registerModifier(consumer, "tailwind_range", PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_RANGE), amplifier == 0 ? 1.0D : 1.5D);
				registerModifier(consumer, "tailwind_cooldown", PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), amplifier == 0 ? 0.7D : 0.6D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
			}
			case "flareup_buff" -> {
				registerModifier(consumer, "flareup_damage", Attributes.ATTACK_DAMAGE, 3.0D);
				registerModifier(consumer, "flareup_attack_speed", Attributes.ATTACK_SPEED, 0.2D);
				registerModifier(consumer, "flareup_skill_power", PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_MULTIPLIER), 0.5D);
				registerModifier(consumer, "flareup_skill_cooldown", PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), -0.1D);
			}
			case "fury_spell_buff" -> {
				registerModifier(consumer, "fury_damage", Attributes.ATTACK_DAMAGE, 4.0D);
				registerModifier(consumer, "fury_speed", Attributes.MOVEMENT_SPEED, 0.05D);
				registerModifier(consumer, "fury_attack_speed", Attributes.ATTACK_SPEED, 3.0D);
				registerModifier(consumer, "fury_skill_cooldown", PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), -0.3D);
				registerModifier(consumer, "fury_teleport_cooldown", PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), -0.3D);
			}
			case "grail_buff" -> registerModifier(consumer, "grail_health", Attributes.MAX_HEALTH, 20.0D);
			case "ice_spell_buff" -> {
				registerModifier(consumer, "ice_speed", Attributes.MOVEMENT_SPEED, -1.0D);
				registerModifier(consumer, "ice_damage", Attributes.ATTACK_DAMAGE, -100.0D);
			}
			case "lethargy_buff" -> {
				registerModifier(consumer, "lethargy_teleport", PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), 0.5D);
				registerModifier(consumer, "lethargy_speed", Attributes.MOVEMENT_SPEED, -0.01D);
				registerModifier(consumer, "lethargy_attack_speed", Attributes.ATTACK_SPEED, -0.1D);
				registerModifier(consumer, "lethargy_skill_cooldown", PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), 0.2D);
			}
			case "trance_buff" -> {
				registerModifier(consumer, "trance_teleport", PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), 1.0D);
				registerModifier(consumer, "trance_speed", Attributes.MOVEMENT_SPEED, -0.02D);
				registerModifier(consumer, "trance_attack_speed", Attributes.ATTACK_SPEED, -0.2D);
				registerModifier(consumer, "trance_damage", Attributes.ATTACK_DAMAGE, -1.0D);
				registerModifier(consumer, "trance_skill_cooldown", PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), 0.5D);
			}
			case "rapid_reaction" -> registerModifier(consumer, "rapid_reaction", PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), -0.2D);
			case "memento_buff" -> registerModifier(consumer, "memento_luck", PasterDreamAttributes.holder(PasterDreamAttributes.LUCK), 10.0D);
			case "snow_vow_buff" -> registerModifier(consumer, "snow_vow_luck", PasterDreamAttributes.holder(PasterDreamAttributes.LUCK), 3.0D);
			default -> {
			}
		}
	}

	@Override
	public void onMobRemoved(
		LivingEntity entity,
		int amplifier,
		Entity.RemovalReason reason
	) {
		switch (effectId) {
			case "deadwind_buff" -> {
				remove(entity, Attributes.MOVEMENT_SPEED, "deadwind_speed");
				remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_RANGE), "deadwind_range");
				remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), "deadwind_cooldown");
			}
			case "tailwind_buff" -> {
				remove(entity, Attributes.MOVEMENT_SPEED, "tailwind_speed");
				remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_RANGE), "tailwind_range");
				remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), "tailwind_cooldown");
			}
			case "flareup_buff" -> {
				remove(entity, Attributes.ATTACK_DAMAGE, "flareup_damage");
				remove(entity, Attributes.ATTACK_SPEED, "flareup_attack_speed");
				remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_MULTIPLIER), "flareup_skill_power");
				remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), "flareup_skill_cooldown");
			}
			case "fury_spell_buff" -> {
				remove(entity, Attributes.ATTACK_DAMAGE, "fury_damage");
				remove(entity, Attributes.MOVEMENT_SPEED, "fury_speed");
				remove(entity, Attributes.ATTACK_SPEED, "fury_attack_speed");
				remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), "fury_skill_cooldown");
				remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), "fury_teleport_cooldown");
			}
			case "grail_buff" -> remove(entity, Attributes.MAX_HEALTH, "grail_health");
			case "ice_spell_buff" -> {
				remove(entity, Attributes.MOVEMENT_SPEED, "ice_speed");
				remove(entity, Attributes.ATTACK_DAMAGE, "ice_damage");
			}
			case "lethargy_buff" -> {
				remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), "lethargy_teleport");
				remove(entity, Attributes.MOVEMENT_SPEED, "lethargy_speed");
				remove(entity, Attributes.ATTACK_SPEED, "lethargy_attack_speed");
				remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), "lethargy_skill_cooldown");
			}
			case "trance_buff" -> {
				remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), "trance_teleport");
				remove(entity, Attributes.MOVEMENT_SPEED, "trance_speed");
				remove(entity, Attributes.ATTACK_SPEED, "trance_attack_speed");
				remove(entity, Attributes.ATTACK_DAMAGE, "trance_damage");
				remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), "trance_skill_cooldown");
			}
			case "rapid_reaction" -> remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), "rapid_reaction");
			case "memento_buff" -> remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.LUCK), "memento_luck");
			case "snow_vow_buff" -> remove(entity, PasterDreamAttributes.holder(PasterDreamAttributes.LUCK), "snow_vow_luck");
			case "machine_wing_effect" -> {
				if (entity instanceof Player player) {
					player.removeTag("pasterdream_machine_wing");
					player.getAbilities().mayfly = false;
					player.getAbilities().flying = false;
					player.onUpdateAbilities();
				}
			}
			case "guard_block_buff" -> {
				if (entity instanceof ServerPlayer player && !player.isCreative()) {
					player.removeTag("pasterdream_guard_block");
					player.setGameMode(GameType.SURVIVAL);
				}
			}
			default -> {
			}
		}
		super.onMobRemoved(entity, amplifier, reason);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.level().isClientSide()) {
			return true;
		}
		switch (effectId) {
			case "boboji_buff" -> boboji(entity);
			case "cloudmist_buff" -> cloudmist(entity);
			case "fondillusion_buff" -> fondillusion(entity);
			case "confusion_buff" -> confusion(entity, amplifier);
			case "insand_buff" -> insand(entity, amplifier);
			case "evasion_cloak_buff" -> particles(entity, (ParticleOptions) PasterDreamParticles.SILVER_PARTICLE);
			case "turnback_cloak_buff" -> particles(entity, (ParticleOptions) PasterDreamParticles.GOLDEN_PARTICLE);
			case "expup_buff" -> {
				if (entity instanceof Player player
					&& player.getRandom().nextInt(1000) < 10) {
					player.giveExperiencePoints(1);
				}
			}
			case "goldenrod_tea_buff" -> {
				entity.removeEffect(MobEffects.HUNGER);
				entity.removeEffect(MobEffects.CONFUSION);
			}
			case "grail_buff" -> {
				entity.removeEffect(MobEffects.CONFUSION);
				entity.removeEffect(MobEffects.BLINDNESS);
				entity.removeEffect(MobEffects.HUNGER);
			}
			case "guard_block_buff" -> {
				entity.removeEffect(MobEffects.JUMP);
				if (entity instanceof Player player) {
					player.getAbilities().flying = false;
					player.onUpdateAbilities();
				}
			}
			case "restrainmove_block_buff" -> {
				entity.removeEffect(MobEffects.JUMP);
				if (entity instanceof Player player) {
					player.getAbilities().flying = false;
					player.onUpdateAbilities();
				}
			}
			case "dyedream_armor_buff", "sculk_armor_buff" -> {
				if (!entity.hasEffect(MobEffects.HEALTH_BOOST)) {
					entity.addEffect(new MobEffectInstance(
						MobEffects.HEALTH_BOOST,
						1_200_000,
						0,
						false,
						false
					));
				}
			}
			case "snow_vow_buff" -> {
				entity.clearFire();
				entity.setTicksFrozen(0);
			}
			case "shadow_spyon_buff" -> shadowIntrusion(entity);
			case "evasion_buff" -> {
			}
			default -> {
			}
		}
		return true;
	}

	private static void boboji(LivingEntity entity) {
		particles(entity, (ParticleOptions) PasterDreamParticles.SPORE_PARTICLE);
		particles(entity, (ParticleOptions) PasterDreamParticles.DUST_0_PARTICLE);
		Vec3 center = entity.position();
		boolean nearbyPlayer = !entity.level().getEntitiesOfClass(
			Player.class,
			AABB.ofSize(center, 15.0D, 15.0D, 15.0D),
			player -> true
		).isEmpty();
		if (nearbyPlayer) {
				for (Player nearby : entity.level().getEntitiesOfClass(
						Player.class,
						AABB.ofSize(center, 7.5D, 7.5D, 7.5D),
						candidate -> true
				).stream().sorted(Comparator.comparingDouble(candidate ->
						candidate.distanceToSqr(center)
				)).toList()) {
						addEvasion(nearby, 10);
				}
		} else {
			addEvasion(entity, 5);
		}
	}

	private static void cloudmist(LivingEntity entity) {
		Level level = entity.level();
		if (!(entity instanceof Player player)) {
			return;
		}
		PasterDreamWorldStateComponent state = PasterDreamPlayerState.worldState(player);
		if (level.dimension().equals(WIND_JOURNEY)) {
			if (entity.getY() > 0.0D && entity.getY() <= 50.0D) {
				state.setCloudmistPercent((50.0D - entity.getY()) * 2.0D);
				if (entity.getY() <= 5.0D && player instanceof ServerPlayer serverPlayer) {
					ServerLevel overworld = serverPlayer.server.overworld();
					BlockPos spawn = overworld.getSharedSpawnPos();
					serverPlayer.teleportTo(
						overworld,
						spawn.getX(),
						304.0D,
						spawn.getZ(),
						serverPlayer.getYRot(),
						serverPlayer.getXRot()
					);
				}
			} else {
				state.setCloudmistPercent(0.0D);
			}
		} else if (!level.dimension().equals(Level.OVERWORLD)) {
			state.setCloudmistPercent(0.0D);
		}
	}

	private static void fondillusion(LivingEntity entity) {
		if (!(entity instanceof Player player)) {
			return;
		}
		PasterDreamWorldStateComponent state = PasterDreamPlayerState.worldState(player);
		if (entity.level().dimension().equals(Level.OVERWORLD)) {
			if (entity.getY() > 260.0D && entity.getY() <= 310.0D) {
				state.setCloudmistPercent((entity.getY() - 260.0D) * 2.0D);
				if (entity.getY() >= 306.0D
					&& player instanceof ServerPlayer serverPlayer
					&& hasCompleted(serverPlayer, "achievement_b_0")
					&& hasCompleted(serverPlayer, "achievement_hide_16")) {
					ServerLevel destination = serverPlayer.server.getLevel(WIND_JOURNEY);
					if (destination != null) {
						serverPlayer.teleportTo(
							destination,
							serverPlayer.getX(),
							serverPlayer.getY(),
							serverPlayer.getZ(),
							serverPlayer.getYRot(),
							serverPlayer.getXRot()
						);
					}
				}
			} else {
				state.setCloudmistPercent(0.0D);
			}
		} else if (!entity.level().dimension().equals(WIND_JOURNEY)) {
			state.setCloudmistPercent(0.0D);
		}
	}

	private static void confusion(LivingEntity entity, int amplifier) {
		double horizontal = amplifier == 0 ? 1.0D : 2.0D;
		if (entity instanceof Player) {
			entity.setYRot(entity.getYRot() + (float) random(entity, -horizontal, horizontal));
			entity.setXRot(entity.getXRot() + (float) random(entity, -horizontal, horizontal));
		} else {
			entity.setYRot((float) random(entity, -180.0D, 180.0D));
			entity.setXRot((float) random(entity, -90.0D, 90.0D));
			entity.setDeltaMovement(0.0D, -1.0D, 0.0D);
		}
		entity.setYBodyRot(entity.getYRot());
		entity.setYHeadRot(entity.getYRot());
		entity.yRotO = entity.getYRot();
		entity.xRotO = entity.getXRot();
		entity.yBodyRotO = entity.getYRot();
		entity.yHeadRotO = entity.getYRot();
	}

	private static void insand(LivingEntity entity, int amplifier) {
		if (!(entity instanceof Player player)) {
			return;
		}
		if (PasterDreamConfig.common().lowSanScreenJitter) {
			double range = amplifier == 0 ? 0.5D : amplifier == 1 ? 1.0D : 3.0D;
			confusion(player, 0);
			player.setYRot(player.getYRot() + (float) random(player, -range, range));
			player.setXRot(player.getXRot() + (float) random(player, -range, range));
		}
		double chance = amplifier == 0 ? 0.005D : amplifier == 1 ? 0.01D : 0.025D;
		if (player.getRandom().nextDouble() >= chance || !(player.level() instanceof ServerLevel level)) {
			return;
		}
		BlockPos position = player.blockPosition();
		if (amplifier == 2 && !player.hasEffect(PasterDreamContent.effectHolder(
			PasterDreamEffects.SHADOW_SPYON_BUFF
		)) && player.getHealth() > 1.0F
			&& player.getRandom().nextDouble() < 0.5D) {
			player.hurt(level.damageSources().fellOutOfWorld(), 1.0F);
		}
		double terrorbeakChance = amplifier == 0 ? 0.3D : amplifier == 1 ? 0.25D : 0.05D;
		if (level.isEmptyBlock(position.above(2)) && player.getRandom().nextDouble() < terrorbeakChance) {
			summon(level, "terrorbeak", position);
		}
		if (amplifier == 2 && level.isEmptyBlock(position.above(2))
			&& player.getRandom().nextDouble() < 0.03D) {
			summon(level, "crazy_terrorbeak", position);
		}
		if (level.isEmptyBlock(position) && player.getRandom().nextDouble() < 0.5D) {
			summon(level, "shadow_hand", position);
		}
	}

	private static void shadowIntrusion(LivingEntity entity) {
		if (!(entity instanceof Player player) || !(player.level() instanceof ServerLevel level)) {
			return;
		}
		PasterDreamWorldStateComponent state = PasterDreamPlayerState.worldState(player);
		if (!level.dimension().equals(Level.OVERWORLD)) {
			return;
		}
		if (level.isDay()) {
			if (state.shadowIntrude()) {
				endShadowIntrusion(player, state);
			}
			return;
		}
		if (!state.shadowIntrude()) {
			if (level.getMaxLocalRawBrightness(player.blockPosition()) <= 7
				&& player.getRandom().nextDouble() < 0.0005D) {
				state.setShadowIntrude(true);
				state.setShadowIntrudeEnd(false);
				player.displayClientMessage(
					Component.literal("§5你感到一丝凉意，眼前被笼罩了一层黑雾"),
					false
				);
				player.displayClientMessage(
					Component.literal("§5附近的的影子开始蠕动"),
					false
				);
				player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100, 0, false, false));
				level.playSound(
					null,
					player.blockPosition(),
					PasterDreamSounds.SHADOW_DOOR,
					SoundSource.NEUTRAL,
					1.0F,
					1.0F
				);
			}
			return;
		}
		if (state.shadowIntrudeEnd() || player.getRandom().nextDouble() >= 0.02D) {
			return;
		}
		int x = player.getRandom().nextIntBetweenInclusive(-9, 9);
		int z = player.getRandom().nextIntBetweenInclusive(-9, 9);
		BlockPos target = player.blockPosition().offset(x, 0, z);
		if (level.isEmptyBlock(target.above(2))
			&& level.isEmptyBlock(target.above())
			&& !level.isEmptyBlock(target.below())) {
			summon(level, "weakeness_terrorbeak", target);
			summon(level, "shadow_hand", target);
			state.incrementShadowIntrudeNumber();
			if (state.shadowIntrudeNumber() > 4 && player.getRandom().nextBoolean()) {
				state.setShadowIntrudeEnd(true);
			}
			player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100, 0));
		}
		if (state.shadowIntrudeEnd()
			&& level.getEntitiesOfClass(
				LivingEntity.class,
				AABB.ofSize(player.position(), 32.0D, 32.0D, 32.0D),
				candidate -> candidate.getType() == PasterDreamEntities.WEAKENESS_TERRORBEAK
			).isEmpty()) {
			endShadowIntrusion(player, state);
		}
	}

	private static void endShadowIntrusion(Player player, PasterDreamWorldStateComponent state) {
		state.resetShadowIntrusion();
		player.displayClientMessage(Component.literal("§5影子归于平息..."), false);
		if (player instanceof ServerPlayer serverPlayer
			&& !hasCompleted(serverPlayer, "achievement_shadow_npc_3")) {
			award(serverPlayer, "achievement_shadow_npc_3");
			player.displayClientMessage(
				Component.literal("§7你对这些暗影生物会来到这里感到疑惑"),
				false
			);
			player.displayClientMessage(Component.literal("§7或许我们应该再去找一次无名..."), false);
			player.removeEffect(PasterDreamContent.effectHolder(PasterDreamEffects.SHADOW_SPYON_BUFF));
		}
	}

	private static void modify(
		LivingEntity entity,
		net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
		String name,
		double amount,
		AttributeModifier.Operation operation
	) {
		AttributeInstance instance = entity.getAttribute(attribute);
		if (instance != null) {
			instance.addOrUpdateTransientModifier(new AttributeModifier(
				PasterDream.id("effect/" + name),
				amount,
				operation
			));
		}
	}

	private static void registerModifier(
		java.util.function.BiConsumer<
			net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute>,
			AttributeModifier
		> consumer,
		String name,
		net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
		double amount
	) {
		registerModifier(
			consumer,
			name,
			attribute,
			amount,
			AttributeModifier.Operation.ADD_VALUE
		);
	}

	private static void registerModifier(
		java.util.function.BiConsumer<
			net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute>,
			AttributeModifier
		> consumer,
		String name,
		net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
		double amount,
		AttributeModifier.Operation operation
	) {
		consumer.accept(
			attribute,
			new AttributeModifier(
				PasterDream.id("effect/" + name),
				amount,
				operation
			)
		);
	}

	private static void remove(
		LivingEntity entity,
		net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
		String name
	) {
		AttributeInstance instance = entity.getAttribute(attribute);
		if (instance != null) {
			instance.removeModifier(PasterDream.id("effect/" + name));
		}
	}

	private static void particles(LivingEntity entity, ParticleOptions particle) {
		if (entity.level() instanceof ServerLevel level) {
			level.sendParticles(
				particle,
				entity.getX(),
				entity.getY() + 1.0D,
				entity.getZ(),
				4,
				0.4D,
				0.5D,
				0.4D,
				0.01D
			);
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.DUST_0_PARTICLE,
				entity.getX(),
				entity.getY() + 1.0D,
				entity.getZ(),
				4,
				0.4D,
				0.5D,
				0.4D,
				0.01D
			);
		}
	}

	private static void addEvasion(LivingEntity entity, int duration) {
		entity.addEffect(new MobEffectInstance(
			PasterDreamContent.effectHolder(PasterDreamEffects.EVASION_BUFF),
			duration,
			0,
			false,
			false
		));
	}

	private static double random(Entity entity, double min, double max) {
		return Mth.nextDouble(entity.getRandom(), min, max);
	}

	private static void summon(ServerLevel level, String entityId, BlockPos position) {
		Entity entity = PasterDreamEntities.get(entityId).create(level);
		if (entity == null) {
			return;
		}
		entity.moveTo(
			position.getX() + 0.5D,
			position.getY(),
			position.getZ() + 0.5D,
			level.getRandom().nextFloat() * 360.0F,
			0.0F
		);
		level.addFreshEntity(entity);
	}

	private static boolean hasCompleted(ServerPlayer player, String advancementId) {
		return PasterDreamAdvancements.completed(player, advancementId);
	}

	private static void award(ServerPlayer player, String advancementId) {
		PasterDreamAdvancements.award(player, advancementId);
	}
}
