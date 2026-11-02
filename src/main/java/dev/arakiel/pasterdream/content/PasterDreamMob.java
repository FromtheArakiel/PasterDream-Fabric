package dev.arakiel.pasterdream.content;

import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import dev.arakiel.pasterdream.gameplay.PasterDreamAttributes;
import dev.arakiel.pasterdream.gameplay.PasterDreamItemData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import java.util.UUID;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class PasterDreamMob extends PathfinderMob
	implements GeoAnimatable, RangedAttackMob
{
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
	private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
	private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);
	private String entityId;
	private UUID owner;
	private int skillLock;
	private boolean bloodLock;
	private Player dialogueTarget;
	private int dialogueAge;
	private int dialogueStage;

	public PasterDreamMob(EntityType<? extends PasterDreamMob> type, Level level) {
		super(type, level);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		if (isPassive()) {
			goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
		}
		if (getType().getCategory() == net.minecraft.world.entity.MobCategory.MONSTER) {
			goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
			if (rangedProjectile() != null) {
				goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0D, 40, 15.0F));
			}
			if (!entityId().equals("friendly_ghost")) {
				targetSelector.addGoal(
					2,
					new NearestAttackableTargetGoal<>(
						this,
						Player.class,
						10,
						true,
						false,
						player -> !isTame()
					)
				);
			}
		}
		goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(9, new RandomLookAroundGoal(this));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 20.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.25D)
			.add(Attributes.FOLLOW_RANGE, 24.0D);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(
			this,
			"movement",
			5,
			state -> state.setAndContinue(state.isMoving() ? WALK : IDLE)
		));
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		String projectile = rangedProjectile();
		if (projectile == null) {
			return;
		}
		@SuppressWarnings("unchecked")
		net.minecraft.world.entity.EntityType<PasterDreamProjectile> type =
			(net.minecraft.world.entity.EntityType<PasterDreamProjectile>)
				PasterDreamEntities.get(projectile);
		PasterDreamProjectile.shootAt(
			type,
			this,
			target,
			1.5F,
			getAttributeValue(Attributes.ATTACK_DAMAGE)
		);
	}

	@Override
	public void tick() {
		super.tick();
		entityId();
		if (isFloating() && !isNoGravity()) {
			setNoGravity(true);
		}
		if (level().isClientSide()) {
			return;
		}
		if (skillLock > 0) {
			skillLock--;
		}
		if (entityId.equals("firefly") && tickCount % 8 == 0 && level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
			serverLevel.sendParticles(
				(net.minecraft.core.particles.ParticleOptions) PasterDreamParticles.FIREFLY_PARTICLE,
				getX(),
				getY() + 0.4D,
				getZ(),
				1,
				0.1D,
				0.1D,
				0.1D,
				0.0D
			);
		}
		if (level() instanceof ServerLevel serverLevel) {
			tickSpecialEntity(serverLevel);
			if (dialogueTarget != null) {
				tickDialogue(serverLevel);
			}
		}
	}

	private void useAreaSkill() {
		switch (entityId()) {
			case "shadow_golem" -> {
				for (Entity entity : level().getEntities(
					this,
					getBoundingBox().inflate(4.0D),
					candidate -> candidate instanceof LivingEntity
						&& !(candidate instanceof PasterDreamMob)
				)) {
					entity.hurt(damageSources().mobAttack(this), 8.0F);
					entity.setDeltaMovement(0.0D, 0.8D, 0.0D);
				}
			}
			case "terrorbeak", "crazy_terrorbeak", "weakeness_terrorbeak" -> {
				if (getTarget() instanceof LivingEntity target) {
					target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
						net.minecraft.world.effect.MobEffects.DARKNESS,
						80,
						0
					));
				}
			}
			case "wind_knight" -> {
				var look = getLookAngle();
				setDeltaMovement(look.x * 2.0D, 0.5D, look.z * 2.0D);
				hurtMarked = true;
			}
			default -> {
			}
		}
	}

	private void tickSpecialEntity(ServerLevel level) {
		switch (entityId()) {
			case "aaroncos_lefthand_0", "aaroncos_righthand_0" -> tickAaroncosHand(level);
			case "terrorbeak", "crazy_terrorbeak" -> {
				if (tickCount % 200 == 0 && skillLock == 0) {
					terrorRoar(level);
				}
			}
			case "black_beetle_mother" -> {
				if (tickCount % 200 == 0 && skillLock == 0 && getHealth() > 1.0F) {
					beetleBrood(level);
				}
			}
			case "shadow_hand" -> {
				if (tickCount % 20 == 0 && getTarget() != null) {
					Vec3 look = getLookAngle();
					setDeltaMovement(look.x * 1.2D, look.y - 0.5D, look.z * 1.2D);
					hurtMarked = true;
				}
			}
			case "fury_spell_entity" -> {
				if (tickCount % 10 == 0) {
					for (Player player : level.getEntitiesOfClass(
						Player.class,
						getBoundingBox().inflate(4.0D),
						player -> true
					)) {
						player.addEffect(new MobEffectInstance(
							PasterDreamContent.effectHolder(PasterDreamEffects.FURY_SPELL_BUFF),
							60,
							0
						));
					}
				}
				if (tickCount >= 90) {
					discard();
				}
			}
			case "healing_spell_entity" -> {
				if (tickCount >= 400) {
					discard();
					return;
				}
				for (LivingEntity living : level.getEntitiesOfClass(
					LivingEntity.class,
					getBoundingBox().inflate(2.5D),
					candidate -> candidate instanceof PathfinderMob
						|| candidate instanceof Player
				)) {
					living.setHealth(Math.min(
						living.getMaxHealth(),
						living.getHealth() + living.getMaxHealth() / 400.0F
					));
				}
				if (tickCount % 4 == 0) {
					level.sendParticles(
						(net.minecraft.core.particles.ParticleOptions) PasterDreamParticles.HEALING_SPELL_PARTICLE,
						getX(),
						getY() - 0.5D,
						getZ(),
						3,
						1.7D,
						0.5D,
						1.7D,
						0.05D
					);
				}
			}
			case "fox_fire" -> tickFoxFire(level);
			case "shaking_crystal" -> {
				if (tickCount == 1) {
					for (Entity entity : level.getEntities(
						this,
						getBoundingBox().inflate(4.5D),
						candidate -> !candidate.getType().is(specialEntityTag())
							&& candidate instanceof LivingEntity
					)) {
						((LivingEntity) entity).addEffect(new MobEffectInstance(
							PasterDreamContent.effectHolder(PasterDreamEffects.CONFUSION_BUFF),
							20,
							1
						));
					}
				}
				if (tickCount >= 50) {
					discard();
				}
			}
			case "terrasword_wave" -> tickTerraswordWave(level);
			case "shadow_magicball" -> {
				for (Entity entity : level.getEntities(
					this,
					getBoundingBox().inflate(1.5D),
					candidate -> !candidate.getType().is(specialEntityTag())
						&& !candidate.getType().is(shadowMobTag())
				)) {
					entity.hurt(damageSources().magic(), 4.0F);
					shakingBurst(level);
					discard();
					return;
				}
				if (tickCount % 30 == 0 && getTarget() != null && skillLock == 0) {
					shakingBurst(level);
					discard();
				}
			}
			case "small_stone_spirit" -> {
				if (tickCount % 40 == 0
					&& level.getBlockState(blockPosition()).isAir()
					&& !level.getBlockState(blockPosition().below()).isAir()
					&& level.getRandom().nextDouble() > 0.5D) {
					level.setBlock(
						blockPosition(),
						PasterDreamBlocks.SMALL_STONE_SPIRIT_BLOCK.defaultBlockState(),
						3
					);
					discard();
				}
			}
			default -> {
				if (tickCount % 100 == 0 && getTarget() != null && getTarget().distanceToSqr(this) <= 16.0D) {
					useAreaSkill();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void tickAaroncosHand(ServerLevel level) {
		if (tickCount < 100) {
			if (tickCount == 1) {
				setHealth(1.0F);
				addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 4, false, false));
				addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4, false, false));
				level.playSound(null, blockPosition(), PasterDreamSounds.AARONCOS_SPAWN, SoundSource.MASTER, 1.0F, 1.0F);
			}
			if (tickCount % 30 == 0 || tickCount == 70 || tickCount == 47) {
				level.sendParticles(
					(net.minecraft.core.particles.ParticleOptions) PasterDreamParticles.SHADOW_STONE_PARTICLE,
					getX(),
					getY(),
					getZ(),
					64,
					1.0D,
					1.0D,
					1.0D,
					0.2D
				);
			}
			if (tickCount == 70 || tickCount == 47) {
				level.explode(this, getX(), getY() + 1.0D, getZ(), 3.0F, Level.ExplosionInteraction.MOB);
			}
			return;
		}
		if (!bloodLock && getHealth() <= 100.0F) {
			bloodLock = true;
			addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 3, false, false));
			removeEffect(PasterDreamContent.effectHolder(PasterDreamEffects.SHADOW_SILENCE_BUFF));
			for (int index = 0; index < 4; index++) {
				summon(level, "shadow_hand", blockPosition().offset(
					level.getRandom().nextInt(7) - 3,
					level.getRandom().nextInt(7) - 3,
					level.getRandom().nextInt(7) - 3
				));
			}
			for (Player player : level.getEntitiesOfClass(
				Player.class,
				AABB.ofSize(position(), 40.0D, 40.0D, 40.0D),
				candidate -> true
			)) {
				player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 0));
				player.addEffect(new MobEffectInstance(
					PasterDreamContent.effectHolder(PasterDreamEffects.CONFUSION_BUFF),
					60,
					1
				));
				player.addEffect(new MobEffectInstance(
					PasterDreamContent.effectHolder(PasterDreamEffects.RESTRAINMOVE_BLOCK_BUFF),
					60,
					0
				));
			}
			level.playSound(null, blockPosition(), PasterDreamSounds.AARONCOS_SPAWN, SoundSource.MASTER, 1.0F, 1.0F);
		}
		if (tickCount % 120 == 0 && skillLock == 0 && getTarget() != null) {
			skillLock = 80;
			if (entityId().equals("aaroncos_lefthand_0")) {
				Vec3 look = getLookAngle();
				setDeltaMovement(look.x * 2.5D, 0.2D, look.z * 2.5D);
				hurtMarked = true;
				for (LivingEntity target : level.getEntitiesOfClass(
					LivingEntity.class,
					getBoundingBox().inflate(4.0D),
					candidate -> !(candidate instanceof PasterDreamMob)
				)) {
					target.hurt(damageSources().mobAttack(this), 12.0F);
					target.addEffect(new MobEffectInstance(
						PasterDreamContent.effectHolder(PasterDreamEffects.CONFUSION_BUFF),
						40,
						1
					));
				}
			} else {
				PasterDreamProjectile.shootAt(
					(net.minecraft.world.entity.EntityType<PasterDreamProjectile>)
						PasterDreamEntities.get("projectile_lightning_projectile"),
					this,
					getTarget(),
					1.5F,
					7.0D
				);
			}
		}
	}

	private void terrorRoar(ServerLevel level) {
		skillLock = 200;
		level.playSound(null, blockPosition(), PasterDreamSounds.TERRORBEAK_ROAR, SoundSource.MASTER, 0.65F, 1.0F);
		Vec3 center = position().add(getLookAngle().scale(2.0D));
		double radius = entityId().equals("crazy_terrorbeak") ? 5.0D : 4.5D;
		for (LivingEntity target : level.getEntitiesOfClass(
			LivingEntity.class,
			AABB.ofSize(center, radius * 2.0D, radius * 2.0D, radius * 2.0D),
			candidate -> !(candidate instanceof PasterDreamMob)
		)) {
			target.addEffect(new MobEffectInstance(
				PasterDreamContent.effectHolder(PasterDreamEffects.CONFUSION_BUFF),
				30,
				1
			));
			target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 30, 0));
			target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 1));
			if (target instanceof ServerPlayer player) {
				PasterDreamPlayerState.addSan(
					player,
					entityId().equals("crazy_terrorbeak") ? -3.0D : -2.0D
				);
			}
		}
	}

	private void beetleBrood(ServerLevel level) {
		skillLock = 200;
		addEffect(new MobEffectInstance(MobEffects.LEVITATION, 80, 0, false, false));
		addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 80, 1, false, false));
		addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 80, 4, false, false));
		addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1, false, false));
		level.playSound(null, blockPosition(), PasterDreamSounds.BEETLE_SKILL, SoundSource.MASTER, 2.0F, 1.0F);
		for (int index = 0; index < 4; index++) {
			summon(level, "black_beetle", blockPosition());
		}
		for (PasterDreamMob beetle : level.getEntitiesOfClass(
			PasterDreamMob.class,
			getBoundingBox().inflate(12.0D),
			mob -> BuiltInRegistries.ENTITY_TYPE.getKey(mob.getType()).getPath().equals("black_beetle")
		)) {
			beetle.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 240, 1));
			beetle.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 240, 1));
			beetle.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 240, 0));
			if (getTarget() != null) {
				beetle.setTarget(getTarget());
			}
		}
		heal(1.0F);
	}

	private void tickFoxFire(ServerLevel level) {
		if (tickCount >= 400) {
			discard();
			return;
		}
		level.sendParticles(
			(net.minecraft.core.particles.ParticleOptions) PasterDreamParticles.FOX_FIRE_0_PARTICLE,
			getX(),
			getY(),
			getZ(),
			5,
			5.0D,
			0.15D,
			5.0D,
			1.0D
		);
		for (LivingEntity living : level.getEntitiesOfClass(
			LivingEntity.class,
			getBoundingBox().inflate(3.0D),
			candidate -> true
		)) {
			if (living instanceof Player) {
				living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
			} else if (!living.getType().is(specialEntityTag())) {
				living.addEffect(new MobEffectInstance(
					PasterDreamContent.effectHolder(PasterDreamEffects.VULNERABILITY_BUFF),
					20,
					1
				));
				living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 0));
			}
		}
	}

	private void tickTerraswordWave(ServerLevel level) {
		level.sendParticles(
			(net.minecraft.core.particles.ParticleOptions) PasterDreamParticles.TERRASWORD_WAVE_PARTICLE,
			getX(),
			getY(),
			getZ(),
			3,
			0.2D,
			0.5D,
			0.2D,
			0.1D
		);
		for (LivingEntity living : level.getEntitiesOfClass(
			LivingEntity.class,
			getBoundingBox().inflate(1.25D),
			candidate -> !(candidate instanceof Player)
				&& !candidate.getType().is(specialEntityTag())
		)) {
			living.hurt(damageSources().mobAttack(this), 5.0F);
			discard();
			return;
		}
		if (!hasEffect(PasterDreamContent.effectHolder(PasterDreamEffects.CLOUDMIST_BUFF))
			&& tickCount >= 20) {
			discard();
		}
	}

	@Override
	public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount) {
		if (entityId().equals("basalt_snail")) {
			addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 170, 3, false, false));
			addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 170, 4, false, false));
			if (source.is(net.minecraft.tags.DamageTypeTags.IS_FIRE)
				|| source.is(net.minecraft.world.damagesource.DamageTypes.DROWN)) {
				return false;
			}
		}
		if (entityId().equals("highvoltage_thundercloud")
			&& source.getEntity() instanceof LivingEntity attacker
			&& level() instanceof ServerLevel serverLevel) {
			for (int index = 0; index < 6; index++) {
				summonProjectileDown(
					serverLevel,
					"projectile_lightning_projectile",
					attacker.getX() + serverLevel.getRandom().nextDouble() * 1.2D - 0.6D,
					attacker.getY() + 5.0D,
					attacker.getZ() + serverLevel.getRandom().nextDouble() * 1.2D - 0.6D,
					10.0D
				);
			}
			serverLevel.playSound(
				null,
				attacker.blockPosition(),
				PasterDreamSounds.THUNDERCLOUD_ATTACK,
				SoundSource.MASTER,
				0.6F,
				1.0F
			);
		}
		return super.hurt(source, amount);
	}

	@Override
	public void die(net.minecraft.world.damagesource.DamageSource source) {
		if (level() instanceof ServerLevel serverLevel) {
			if (entityId().equals("weakeness_terrorbeak")
				&& source.getEntity() instanceof ServerPlayer player
				&& PasterDreamPlayerState.san(player).value() <= 20.0D) {
				PasterDreamPlayerState.addSan(player, 7.0D);
			}
			if (entityId().equals("crazy_terrorbeak")
				&& source.getEntity() instanceof ServerPlayer player
				&& PasterDreamPlayerState.san(player).value() <= 20.0D) {
				PasterDreamPlayerState.addSan(player, 30.0D);
			}
			if (entityId().equals("highvoltage_thundercloud")) {
				scheduleLightning(serverLevel, blockPosition().below(4));
			}
			if (entityId().equals("meltdream_crystal_entity")) {
				serverLevel.sendParticles(
					(net.minecraft.core.particles.ParticleOptions) PasterDreamParticles.MELTDREAM_CRYSTAL_PARTICLE,
					getX(),
					getY() + 1.0D,
					getZ(),
					128,
					2.0D,
					2.0D,
					2.0D,
					0.5D
				);
				serverLevel.playSound(
					null,
					blockPosition(),
					SoundEvents.AMETHYST_BLOCK_BREAK,
					SoundSource.NEUTRAL,
					0.8F,
					1.0F
				);
				ItemEntity drop = new ItemEntity(
					serverLevel,
					getX(),
					getY() + 0.5D,
					getZ(),
					new ItemStack(PasterDreamItems.MELTDREAM_CRYSTAL)
				);
				drop.setPickUpDelay(10);
				serverLevel.addFreshEntity(drop);
			}
		}
		super.die(source);
	}

	private static void summonProjectileDown(
		ServerLevel level,
		String id,
		double x,
		double y,
		double z,
		double damage
	) {
		@SuppressWarnings("unchecked")
		net.minecraft.world.entity.EntityType<PasterDreamProjectile> type =
			(net.minecraft.world.entity.EntityType<PasterDreamProjectile>)
				PasterDreamEntities.get(id);
		PasterDreamProjectile projectile = new PasterDreamProjectile(type, level);
		projectile.setPos(x, y, z);
		projectile.shoot(0.0D, -1.0D, 0.0D, 1.0F, 0.0F);
		projectile.setBaseDamage(damage);
		projectile.setSilent(true);
		level.addFreshEntity(projectile);
	}

	private static void scheduleLightning(ServerLevel level, net.minecraft.core.BlockPos pos) {
		for (int index = 0; index < 4; index++) {
			var lightning = EntityType.LIGHTNING_BOLT.create(level);
			if (lightning != null) {
				lightning.moveTo(
					pos.getX() + level.getRandom().nextDouble() * 4.0D - 2.0D,
					pos.getY(),
					pos.getZ() + level.getRandom().nextDouble() * 4.0D - 2.0D
				);
				level.addFreshEntity(lightning);
			}
		}
	}

	private void shakingBurst(ServerLevel level) {
		level.sendParticles(
			net.minecraft.core.particles.ParticleTypes.EXPLOSION,
			getX(),
			getY(),
			getZ(),
			7,
			0.7D,
			0.5D,
			0.7D,
			0.01D
		);
		level.sendParticles(
			(net.minecraft.core.particles.ParticleOptions) PasterDreamParticles.SHADOW_STONE_PARTICLE,
			getX(),
			getY(),
			getZ(),
			64,
			1.0D,
			0.2D,
			1.0D,
			0.02D
		);
	}

	private static net.minecraft.tags.TagKey<EntityType<?>> specialEntityTag() {
		return net.minecraft.tags.TagKey.create(
			net.minecraft.core.registries.Registries.ENTITY_TYPE,
			dev.arakiel.pasterdream.PasterDream.id("special_entity_tag")
		);
	}

	private static net.minecraft.tags.TagKey<EntityType<?>> shadowMobTag() {
		return net.minecraft.tags.TagKey.create(
			net.minecraft.core.registries.Registries.ENTITY_TYPE,
			dev.arakiel.pasterdream.PasterDream.id("shadow_mob")
		);
	}

	private static void summon(ServerLevel level, String id, net.minecraft.core.BlockPos position) {
		Entity entity = PasterDreamEntities.get(id).create(level);
		if (entity != null) {
			entity.moveTo(
				position.getX() + 0.5D,
				position.getY(),
				position.getZ() + 0.5D,
				level.getRandom().nextFloat() * 360.0F,
				0.0F
			);
			level.addFreshEntity(entity);
		}
	}

	private boolean isPassive() {
		return switch (entityId()) {
			case "pink_chicken", "pink_slime", "basalt_snail", "spore_entity",
				"jellyfish", "meltdream_crystal_entity" -> true;
			default -> false;
		};
	}

	private boolean isFloating() {
		return switch (entityId()) {
			case "jellyfish", "firefly", "bone_wing", "ash_bone_wing",
				"thundercloud", "highvoltage_thundercloud", "shadow_squeal_ghost",
				"shadow_squeal_ghost_0", "friendly_ghost", "fury_spell_entity",
				"fox_fire", "terrasword_wave" -> true;
			default -> false;
		};
	}

	private String rangedProjectile() {
		return switch (entityId()) {
			case "bone_wing", "ash_bone_wing" -> "projectile_bone_wing_fire_ball_projectile";
			case "shadow_squeal_ghost", "shadow_squeal_ghost_0", "friendly_ghost" ->
				"projectile_squeal_wave_projectile";
			case "thundercloud", "highvoltage_thundercloud" ->
				"projectile_lightning_projectile";
			default -> null;
		};
	}

	private String entityId() {
		if (entityId == null) {
			entityId = BuiltInRegistries.ENTITY_TYPE.getKey(getType()).getPath();
		}
		return entityId;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (entityId().equals("golden_fox")) {
			return goldenFoxWish(player, hand, stack);
		}
		if (entityId().equals("shadow_npc_0")) {
			if (!level().isClientSide()) {
				startDialogue(player);
			}
			return InteractionResult.SUCCESS;
		}
		if (!entityId().equals("black_beetle")
			|| !stack.is(PasterDreamBlocks.FLOWER_15.asItem())) {
			return super.mobInteract(player, hand);
		}
		if (level().isClientSide()) {
			return InteractionResult.SUCCESS;
		}
		if (isTame()) {
			if (isOwner(player) && getHealth() < getMaxHealth()) {
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				heal(4.0F);
				return InteractionResult.SUCCESS;
			}
			return super.mobInteract(player, hand);
		}
		if (!player.getAbilities().instabuild) {
			stack.shrink(1);
		}
		if (random.nextInt(3) == 0) {
			owner = player.getUUID();
			setPersistenceRequired();
			level().broadcastEntityEvent(this, (byte) 7);
		} else {
			level().broadcastEntityEvent(this, (byte) 6);
		}
		return InteractionResult.SUCCESS;
	}

	private InteractionResult goldenFoxWish(
		Player player,
		InteractionHand hand,
		ItemStack stack
	) {
		ItemStack result = ItemStack.EMPTY;
		boolean block = false;
		if (stack.is(Items.GOLDEN_APPLE)) {
			result = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE);
		} else if (stack.is(Items.BUCKET)) {
			result = new ItemStack(PasterDreamItems.MELTDREAM_LIQUID_BUCKET);
		} else if (stack.is(PasterDreamItems.DREAM_COIN_BUNDLE)) {
			block = true;
		} else if (stack.is(PasterDreamItems.MELTDREAM_CRYSTAL)
			|| stack.is(PasterDreamBlocks.MEMENTO_11.asItem())) {
			result = new ItemStack(PasterDreamItems.CRADLE_IN_ONES_ARMS);
		}
		if (result.isEmpty() && !block) {
			return super.mobInteract(player, hand);
		}
		if (level().isClientSide()) {
			return InteractionResult.SUCCESS;
		}
		if (!player.getAbilities().instabuild) {
			stack.shrink(1);
		}
		if (block) {
			level().setBlock(blockPosition(), PasterDreamBlocks.MELTDREAM_CHEST.defaultBlockState(), 3);
		} else {
			ItemEntity drop = new ItemEntity(
				level(),
				getX(),
				getY() + 0.5D,
				getZ(),
				result
			);
			drop.setPickUpDelay(10);
			level().addFreshEntity(drop);
		}
		if (level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
				net.minecraft.core.particles.ParticleTypes.CLOUD,
				getX(),
				getY(),
				getZ(),
				12,
				0.6D,
				0.6D,
				0.6D,
				0.1D
			);
			serverLevel.playSound(
				null,
				blockPosition(),
				SoundEvents.FOX_AMBIENT,
				SoundSource.MASTER,
				1.0F,
				1.0F
			);
		}
		player.displayClientMessage(
			Component.literal("金色狐狸在完成了你许下的愿望之后消失了..."),
			false
		);
		discard();
		return InteractionResult.SUCCESS;
	}

	private void startDialogue(Player player) {
		if (dialogueTarget == player && dialogueAge < 560) {
			return;
		}
		dialogueTarget = player;
		dialogueAge = 0;
		dialogueStage = dialogueStage(player);
	}

	private int dialogueStage(Player player) {
		if (!(player instanceof ServerPlayer serverPlayer)) {
			return 0;
		}
		for (int stage = 0; stage <= 5; stage++) {
			if (!dev.arakiel.pasterdream.gameplay.PasterDreamAdvancements.completed(
				serverPlayer,
				"achievement_shadow_npc_" + stage
			)) {
				return stage;
			}
		}
		return 5;
	}

	private void tickDialogue(ServerLevel level) {
		if (!(dialogueTarget instanceof ServerPlayer player)
			|| !player.isAlive()
			|| player.level() != level
			|| player.distanceToSqr(this) > 64.0D) {
			dialogueTarget = null;
			return;
		}
		String[][] lines = {
			{
				"？？？：...",
				"？？？：你好...已经..很久没人来到这里了",
				"？？？：请不要感到害怕，我不像外面的暗影生物一样",
				"？？？：你很好奇我和我为什么在这里吗",
				"？？？：如果要谈起来关于我的事...经历了太多，我也不想回忆了",
				"？？？：很抱歉，这也是我们第一次见面...希望你能理解",
				"？？？：...",
				"？？？：我的名字是...时间过去太久，我也失去了曾经的“自己”",
				"？？？：就叫我“无名”吧",
				"无名：这些给你，作为初次的见面礼"
			},
			{
				"无名：我们见过一面，对吧",
				"无名：又一次来到这里...你是为了什么",
				"无名：我为什么在这里吗？因为我无法逃离，也不再想尝试逃离这里了",
				"无名：正如你所见，这一层，并非被阴影全部覆盖",
				"无名：因为这是我仅存的回忆，我曾看到过的世界",
				"无名：想帮我出去吗？",
				"无名：钥匙就在这里，出口也就在这一层的下方，你明白的"
			},
			{
				"无名：你为何如此执着...",
				"无名：请不要再尝试继续向下了，这会危及你的生命",
				"无名：影子已经注意到了你",
				"无名：回去吧，趁你还记得回家的路"
			},
			{
				"无名：你已经...见到它们出现在了你的世界吗",
				"无名：蛰伏于黑暗，在没有光亮的地方，它们伺机而动...",
				"无名：它们是实际存在的物质？还只是仅存于人们思想的梦...",
				"无名：我能感受到...你可以运用那份力量，来自你的精神",
				"无名：在我们下次见面..."
			},
			{
				"无名：看来你准备好了...",
				"无名：在这层下面，其实是一位已故之人的坟墓",
				"无名：去轻抚暮影长床，做出你内心的第一选择，不计结果"
			},
			{
				"无名：已经没有什么可以告诉你的了",
				"无名：愿你的梦不会在这里终结"
			}
		};
		String[] sequence = lines[Math.min(dialogueStage, lines.length - 1)];
		int interval = 40;
		int index = dialogueAge / interval;
		if (index < sequence.length && dialogueAge % interval == 0) {
			player.displayClientMessage(Component.literal(sequence[index]), false);
			if (dialogueStage == 0 && index == sequence.length - 1) {
				ItemEntity gift = new ItemEntity(
					level,
					getX(),
					getY(),
					getZ(),
					new ItemStack(net.minecraft.world.level.block.Blocks.GOLD_BLOCK)
				);
				gift.setPickUpDelay(20);
				level.addFreshEntity(gift);
			}
		}
		int finish = sequence.length * interval + 40;
		if (dialogueAge >= finish) {
			if (dialogueStage < 5) {
				dev.arakiel.pasterdream.gameplay.PasterDreamAdvancements.award(
					player,
					"achievement_shadow_npc_" + dialogueStage
				);
				player.displayClientMessage(Component.literal("§a新的主线进度已解锁"), false);
			}
			dialogueTarget = null;
			return;
		}
		dialogueAge++;
	}

	private boolean isTame() {
		return owner != null;
	}

	private boolean isOwner(Player player) {
		return player.getUUID().equals(owner);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		if (owner != null) {
			tag.putString("pasterdream_owner", owner.toString());
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("pasterdream_owner")) {
			try {
				owner = UUID.fromString(tag.getString("pasterdream_owner"));
			} catch (IllegalArgumentException ignored) {
				owner = null;
			}
		}
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return animationCache;
	}

	@Override
	public double getTick(Object object) {
		return tickCount;
	}
}
