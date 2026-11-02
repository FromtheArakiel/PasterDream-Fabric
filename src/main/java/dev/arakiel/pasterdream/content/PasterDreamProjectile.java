package dev.arakiel.pasterdream.content;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;

public final class PasterDreamProjectile extends AbstractArrow {
	public PasterDreamProjectile(EntityType<? extends PasterDreamProjectile> type, Level level) {
		super(type, level);
	}

	public static PasterDreamProjectile shoot(
		EntityType<PasterDreamProjectile> type,
		LivingEntity owner,
		float velocity,
		double damage
	) {
		PasterDreamProjectile projectile = new PasterDreamProjectile(type, owner.level());
		projectile.setOwner(owner);
		projectile.shootFromRotation(owner, owner.getXRot(), owner.getYRot(), 0.0F, velocity, 0.0F);
		projectile.setBaseDamage(damage);
		projectile.setSilent(true);
		owner.level().addFreshEntity(projectile);
		return projectile;
	}

	public static PasterDreamProjectile shootAt(
		EntityType<PasterDreamProjectile> type,
		LivingEntity owner,
		Entity target,
		float velocity,
		double damage
	) {
		PasterDreamProjectile projectile = new PasterDreamProjectile(type, owner.level());
		projectile.setOwner(owner);
		projectile.setPos(owner.getX(), owner.getEyeY() - 0.1D, owner.getZ());
		double dx = target.getX() - owner.getX();
		double dy = target.getY(0.5D) - projectile.getY();
		double dz = target.getZ() - owner.getZ();
		projectile.shoot(dx, dy, dz, velocity, 1.0F);
		projectile.setBaseDamage(damage);
		projectile.setSilent(true);
		owner.level().addFreshEntity(projectile);
		return projectile;
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		if (!(level() instanceof ServerLevel serverLevel)) {
			return;
		}
		String id = BuiltInRegistries.ENTITY_TYPE.getKey(getType()).getPath();
		Entity target = result.getEntity();
		if (target instanceof LivingEntity living) {
			if (id.equals("projectile_moltengold_wand_projectile")
				|| id.equals("projectile_true_moltengold_wand_projectile")
				|| id.equals("projectile_truest_moltengold_wand_projectile")) {
				serverLevel.playSound(
					null,
					result.getEntity().blockPosition(),
					net.minecraft.sounds.SoundEvents.DRAGON_FIREBALL_EXPLODE,
					net.minecraft.sounds.SoundSource.NEUTRAL,
					0.6F,
					1.0F
				);
			} else if (id.contains("poison")) {
				living.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 1));
				serverLevel.sendParticles(
					(net.minecraft.core.particles.ParticleOptions) PasterDreamParticles.POISON_GAS_PARTICLE,
					living.getX(),
					living.getY() + 1.0D,
					living.getZ(),
					48,
					2.0D,
					1.0D,
					2.0D,
					0.01D
				);
			} else if (id.contains("ice")) {
				living.setTicksFrozen(Math.max(living.getTicksFrozen(), 120));
				living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
			} else if (id.contains("fury") || id.contains("fire_ball")) {
				living.setRemainingFireTicks(Math.max(living.getRemainingFireTicks(), 100));
			} else if (id.contains("healing")) {
				living.heal(4.0F);
			} else if (id.contains("lightning")) {
				if (serverLevel.canSeeSky(result.getEntity().blockPosition())) {
					var lightning = EntityType.LIGHTNING_BOLT.create(serverLevel);
					if (lightning != null) {
						Entity owner = getOwner();
						if (owner != null) {
							lightning.setCause(
								owner instanceof net.minecraft.server.level.ServerPlayer player
									? player
									: null
							);
						}
						lightning.moveTo(result.getEntity().position());
						serverLevel.addFreshEntity(lightning);
					}
				}
			} else if (id.contains("strawberry")) {
				Entity owner = getOwner();
				if (owner instanceof LivingEntity livingOwner) {
					livingOwner.heal(6.0F);
				}
			} else if (id.contains("pinkegg")) {
				if (serverLevel.getRandom().nextInt(5) == 0) {
					summon(serverLevel, "pink_chicken", result.getEntity().blockPosition());
				}
			} else if (id.contains("white_sword")) {
				net.minecraft.tags.TagKey<net.minecraft.world.entity.EntityType<?>> shadowTag =
					net.minecraft.tags.TagKey.create(
						net.minecraft.core.registries.Registries.ENTITY_TYPE,
						dev.arakiel.pasterdream.PasterDream.id("shadow_mob")
					);
				String entityType = BuiltInRegistries.ENTITY_TYPE.getKey(living.getType()).getPath();
				boolean aaroncos = entityType.equals("aaroncos_lefthand_0")
					|| entityType.equals("aaroncos_righthand_0");
				if ((aaroncos && serverLevel.getRandom().nextDouble() <= 0.06D)
					|| (!aaroncos && living.getType().is(shadowTag)
						&& serverLevel.getRandom().nextDouble() <= 0.12D)) {
					living.addEffect(new MobEffectInstance(
						PasterDreamContent.effectHolder(PasterDreamEffects.SHADOW_SILENCE_BUFF),
						200,
						0
					));
				}
				living.addEffect(new MobEffectInstance(
					PasterDreamContent.effectHolder(PasterDreamEffects.BIND_BUFF),
					40,
					0
				));
			} else if (id.contains("squeal_wave")) {
				living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));
				Entity owner = getOwner();
				if (owner instanceof net.minecraft.world.entity.player.Player player) {
					double power = player.getAttributeValue(
						dev.arakiel.pasterdream.gameplay.PasterDreamAttributes.holder(
							dev.arakiel.pasterdream.gameplay.PasterDreamAttributes.MAGIC_POWER
						)
					);
					var specialTag = net.minecraft.tags.TagKey.create(
						net.minecraft.core.registries.Registries.ENTITY_TYPE,
						dev.arakiel.pasterdream.PasterDream.id("special_entity_tag")
					);
					for (LivingEntity nearby : serverLevel.getEntitiesOfClass(
						LivingEntity.class,
						result.getEntity().getBoundingBox().inflate(1.5D),
						candidate -> !candidate.getType().is(specialTag)
					)) {
						nearby.hurt(serverLevel.damageSources().magic(), (float) (2.0D + power + 1.5D));
					}
					boolean ghostFace = dev.arakiel.curios.api.CuriosApi
						.getCuriosInventory(player)
						.map(inventory -> inventory.findFirstCurio(
							PasterDreamItems.GHOST_FACE_HEAD
						).isPresent())
						.orElse(false);
					if (ghostFace && serverLevel.getRandom().nextDouble() <= 0.1D) {
						summon(serverLevel, "friendly_ghost", result.getEntity().blockPosition());
					}
				}
			}
		}
		discard();
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		if (!(level() instanceof ServerLevel serverLevel)) {
			return;
		}
		String id = BuiltInRegistries.ENTITY_TYPE.getKey(getType()).getPath();
		net.minecraft.core.BlockPos position = result.getBlockPos();
		if (id.contains("pinkegg")) {
			if (serverLevel.getRandom().nextInt(5) == 0) {
				summon(serverLevel, "pink_chicken", position);
			}
		} else if (id.contains("fury_spell")) {
			summon(serverLevel, "fury_spell_entity", position.above());
			furyWave(serverLevel, position);
		} else if (id.contains("healing_spell")) {
			summon(serverLevel, "healing_spell_entity", position.above());
			serverLevel.sendParticles(
				(net.minecraft.core.particles.ParticleOptions) PasterDreamParticles.HEALING_SPELL_PARTICLE,
				position.getX(),
				position.getY() + 1.0D,
				position.getZ(),
				64,
				2.0D,
				1.0D,
				2.0D,
				0.05D
			);
		} else if (id.contains("ice_spell")) {
			iceWave(serverLevel, position);
		} else if (id.contains("lightning_spell")) {
			lightningWave(serverLevel, position);
		} else if (id.contains("bone_wing_fire_ball")) {
			serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.FLAME, getX(), getY(), getZ(), 5, 0.12D, 0.12D, 0.12D, 0.01D);
			serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SMOKE, getX(), getY(), getZ(), 8, 0.15D, 0.15D, 0.15D, 0.02D);
			if (serverLevel.getBlockState(position.above()).isAir()) {
				serverLevel.setBlock(position.above(), Blocks.FIRE.defaultBlockState(), 3);
			}
		} else if (id.contains("moltengold_wand") || id.contains("true_moltengold_wand")
			|| id.contains("truest_moltengold_wand")) {
			if (serverLevel.getBlockState(position.above()).isAir()
				&& !serverLevel.getBlockState(position).isAir()) {
				serverLevel.setBlock(position.above(), Blocks.FIRE.defaultBlockState(), 3);
			}
			if (!id.contains("projectile_moltengold_wand")) {
				if (serverLevel.getBlockState(position.above(2)).isAir()) {
					serverLevel.setBlock(
						position.above(2),
						PasterDreamBlocks.STARCALL_BLOCK.defaultBlockState(),
						3
					);
				}
				double chance = id.contains("truest") ? 0.5D : 0.2D;
				if (serverLevel.canSeeSkyFromBelowWater(position.above(2))
					&& serverLevel.getRandom().nextDouble() <= chance
					&& serverLevel.getBlockState(position.above(11)).isAir()) {
					serverLevel.setBlock(
						position.above(11),
						PasterDreamBlocks.STARCALL_CRACK.defaultBlockState(),
						3
					);
				}
			}
		} else if (id.contains("shadow_vortex_book")) {
			Entity owner = getOwner();
			if (owner instanceof net.minecraft.world.entity.player.Player player
				&& player.isShiftKeyDown()
				&& serverLevel.getBlockState(position).isAir()) {
				serverLevel.setBlock(
					position,
					PasterDreamBlocks.SHADOW_VORTEX.defaultBlockState(),
					3
				);
			}
			serverLevel.sendParticles(
				(net.minecraft.core.particles.ParticleOptions) PasterDreamParticles.SHADOW_STONE_PARTICLE,
				getX(),
				getY(),
				getZ(),
				5,
				0.1D,
				0.1D,
				0.1D,
				0.01D
			);
		} else if (id.contains("white_sword")) {
			serverLevel.sendParticles(
				(net.minecraft.core.particles.ParticleOptions) PasterDreamParticles.DUST_0_PARTICLE,
				getX(),
				getY(),
				getZ(),
				2,
				0.1D,
				0.0D,
				0.1D,
				0.1D
			);
		}
		discard();
	}

	private static void furyWave(
		ServerLevel level,
		net.minecraft.core.BlockPos position
	) {
		level.sendParticles(
			net.minecraft.core.particles.ParticleTypes.ENCHANT,
			position.getX(),
			position.getY() + 3.0D,
			position.getZ(),
			100,
			2.5D,
			1.0D,
			2.5D,
			0.02D
		);
		level.sendParticles(
			net.minecraft.core.particles.ParticleTypes.DRAGON_BREATH,
			position.getX(),
			position.getY() + 0.8D,
			position.getZ(),
			100,
			2.5D,
			0.3D,
			2.5D,
			0.01D
		);
		for (net.minecraft.world.entity.player.Player player : level.getEntitiesOfClass(
			net.minecraft.world.entity.player.Player.class,
			net.minecraft.world.phys.AABB.ofSize(
				net.minecraft.world.phys.Vec3.atCenterOf(position),
				8.0D,
				8.0D,
				8.0D
			),
			player -> true
		)) {
			player.addEffect(new MobEffectInstance(
				PasterDreamContent.effectHolder(PasterDreamEffects.FURY_SPELL_BUFF),
				60,
				0
			));
		}
	}

	private static void iceWave(
		ServerLevel level,
		net.minecraft.core.BlockPos position
	) {
		level.sendParticles(
			net.minecraft.core.particles.ParticleTypes.SNOWFLAKE,
			position.getX() + 0.5D,
			position.getY() + 1.5D,
			position.getZ() + 0.5D,
			48,
			2.5D,
			1.5D,
			2.5D,
			0.1D
		);
		for (LivingEntity entity : level.getEntitiesOfClass(
			LivingEntity.class,
			net.minecraft.world.phys.AABB.ofSize(
				net.minecraft.world.phys.Vec3.atCenterOf(position),
				7.0D,
				7.0D,
				7.0D
			),
			entity -> true
		)) {
			entity.setTicksFrozen(140);
			entity.addEffect(new MobEffectInstance(
				PasterDreamContent.effectHolder(PasterDreamEffects.ICE_SPELL_BUFF),
				40,
				0
			));
		}
	}

	private static void lightningWave(
		ServerLevel level,
		net.minecraft.core.BlockPos position
	) {
		level.playSound(
			null,
			position,
			PasterDreamSounds.LIGHTNING_SPELL,
			net.minecraft.sounds.SoundSource.NEUTRAL,
			1.0F,
			1.0F
		);
		level.sendParticles(
			net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK,
			position.getX(),
			position.getY() + 1.0D,
			position.getZ(),
			128,
			2.0D,
			1.0D,
			2.0D,
			0.5D
		);
		for (int strike = 0; strike < 4; strike++) {
			var lightning = EntityType.LIGHTNING_BOLT.create(level);
			if (lightning != null) {
				lightning.moveTo(
					position.getX() + level.getRandom().nextDouble() * 4.0D - 2.0D,
					position.getY(),
					position.getZ() + level.getRandom().nextDouble() * 4.0D - 2.0D
				);
				level.addFreshEntity(lightning);
			}
		}
	}

	private static void summon(ServerLevel level, String id, net.minecraft.core.BlockPos position) {
		Entity entity = PasterDreamEntities.get(id).create(level);
		if (entity != null) {
			entity.moveTo(position.getX() + 0.5D, position.getY(), position.getZ() + 0.5D, level.getRandom().nextFloat() * 360.0F, 0.0F);
			level.addFreshEntity(entity);
		}
	}
}
