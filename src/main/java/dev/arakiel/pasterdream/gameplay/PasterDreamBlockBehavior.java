package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import dev.arakiel.pasterdream.component.PasterDreamWorldStateComponent;
import dev.arakiel.pasterdream.config.PasterDreamConfig;
import dev.arakiel.pasterdream.content.PasterDreamBlocks;
import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.content.PasterDreamEffects;
import dev.arakiel.pasterdream.content.PasterDreamEntities;
import dev.arakiel.pasterdream.content.PasterDreamItems;
import dev.arakiel.pasterdream.content.PasterDreamParticles;
import dev.arakiel.pasterdream.content.PasterDreamRecipeTypes;
import dev.arakiel.pasterdream.content.PasterDreamBlockEntity;
import dev.arakiel.pasterdream.content.PasterDreamSounds;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuOpener;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuSpecs;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import dev.arakiel.pasterdream.gameplay.recipe.ShadowBlastingRecipe;

public final class PasterDreamBlockBehavior {
	private static final ResourceKey<Level> LAMP_SHADOW_WORLD =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("lamp_shadow_world"));
	private static final ResourceKey<Level> DYEDREAM_WORLD =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("dyedream_world"));
	private static final List<ScheduledBlockAction> SCHEDULED = new ArrayList<>();

	private PasterDreamBlockBehavior() {
	}

	public static void register() {
		UseBlockCallback.EVENT.register(PasterDreamBlockBehavior::use);
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			if (!SCHEDULED.isEmpty()) {
				long time = server.overworld().getGameTime();
				SCHEDULED.removeIf(action -> {
					if (time >= action.executeAt()) {
						action.action().accept(action.level());
						return true;
					}
					return false;
				});
			}
			for (ServerPlayer player : server.getPlayerList().getPlayers()) {
				tickPlayerInside(player);
			}
		});
	}

	public static void tick(PasterDreamBlockEntity machine) {
		if (!(machine.getLevel() instanceof ServerLevel level)) {
			return;
		}
		var key = BuiltInRegistries.BLOCK.getKey(machine.getBlockState().getBlock());
		if (!PasterDream.MOD_ID.equals(key.getNamespace())) {
			return;
		}
		BlockPos pos = machine.getBlockPos();
		String id = key.getPath();
		switch (id) {
			case "shadow_blast_furnace", "shadow_blast_furnace_core" -> tickShadowBlasting(level, machine);
			case "dream_accumulator" -> tickDreamAccumulator(level, machine);
				case "dream_cauldron" -> tickDreamCauldron(level, machine);
			case "research_table" -> tickResearchTable(level, machine);
			case "meltdream_chest", "meltdream_chest_open" -> {
				if (level.getGameTime() % 20L == 0L) {
					level.sendParticles(
						(ParticleOptions) PasterDreamParticles.MELTDREAM_CRYSTAL_PARTICLE,
						pos.getX() + 0.5D,
						pos.getY() + 0.8D,
						pos.getZ() + 0.5D,
						4,
						0.5D,
						0.4D,
						0.5D,
						0.02D
					);
				}
			}
			case "shadow_brazier" -> {
				if (level.getGameTime() % 10L == 0L) {
					level.sendParticles(
						(ParticleOptions) PasterDreamParticles.SOUL_PARTICLE,
						pos.getX() + 0.5D,
						pos.getY() + 1.0D,
						pos.getZ() + 0.5D,
						2,
						0.2D,
						0.2D,
						0.2D,
						0.01D
					);
				}
			}
			case "shadow_dungeon_portal" -> {
				if (machine.stateData().getBoolean("cd")) {
					int cooldown = machine.stateData().getInt("cooldown_time") + 1;
					if (cooldown >= 1800) {
						machine.stateData().putBoolean("cd", false);
						machine.stateData().putInt("cooldown_time", 0);
						machine.markStateChanged();
					} else {
						machine.stateData().putInt("cooldown_time", cooldown);
					}
				}
			}
			case "birds_nest" -> {
				if (level.getGameTime() % 80L == 0L
					&& level.getRandom().nextDouble() < 0.1D
					&& level.getEntitiesOfClass(
						net.minecraft.world.entity.animal.Parrot.class,
						net.minecraft.world.phys.AABB.ofSize(
							net.minecraft.world.phys.Vec3.atCenterOf(pos),
							15.0D,
							15.0D,
							15.0D
						),
						bird -> true
					).isEmpty()) {
					Entity parrot = net.minecraft.world.entity.EntityType.PARROT.create(level);
					if (parrot != null) {
						parrot.moveTo(pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.5D, level.getRandom().nextFloat() * 360.0F, 0.0F);
						level.addFreshEntity(parrot);
					}
					level.sendParticles(ParticleTypes.CLOUD, pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D, 6, 0.2D, 0.2D, 0.2D, 0.03D);
				}
			}
			case "firefly_nest" -> {
				if (level.isDay()) {
					machine.stateData().putBoolean("firefly_nest", true);
				} else {
					if (machine.stateData().getBoolean("firefly_nest")) {
						summon(level, "firefly", pos);
						machine.stateData().putBoolean("firefly_nest", false);
					}
					level.sendParticles(
						(ParticleOptions) PasterDreamParticles.FIREFLY_PARTICLE,
						pos.getX() + 0.5D,
						pos.getY() + 0.4D,
						pos.getZ() + 0.5D,
						5,
						0.5D,
						0.5D,
						0.5D,
						0.02D
					);
				}
			}
			case "ecology_glass_jar" -> {
				level.sendParticles(
					(ParticleOptions) PasterDreamParticles.FIREFLY_GLASS_JAR_PARTICLE,
					pos.getX() + 0.5D,
					pos.getY() + 0.5D,
					pos.getZ() + 0.5D,
					1,
					0.2D,
					0.2D,
					0.2D,
					0.0D
				);
			}
			case "frost_edelweiss" -> level.sendParticles(
				ParticleTypes.SNOWFLAKE,
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				16,
				0.5D,
				0.5D,
				0.5D,
				0.05D
			);
			case "claypan_1" -> {
				if (level.canSeeSkyFromBelowWater(pos)) {
					double time = machine.stateData().getDouble("time") + 1.0D;
					if (level.isDay() && !level.isRaining()) {
						time += 1.0D;
					}
					if (time >= 240.0D) {
						level.setBlock(pos, PasterDreamBlocks.CLAYPAN_2.defaultBlockState(), 3);
					} else {
						machine.stateData().putDouble("time", time);
					}
				}
			}
			case "shadow_vortex" -> tickShadowVortex(level, machine);
			case "forced_tower" -> tickForcedTower(level, pos);
			case "lost_sword_block" -> tickLostSword(level, pos);
			case "qym_doll_0", "uuz_doll_0" -> tickRestDoll(level, pos);
			case "twilight_lantern" -> tickTwilightLantern(level, machine);
			case "guard_block", "guard_crystal", "restrainmove_block" ->
				tickProtectionBlock(level, machine, id);
			case "windmoor_crate" -> tickWindmoorCrate(level, machine);
			case "the_endless_book_of_dream_seekers" -> tickEndlessBook(level, pos);
			case "firefly_glass_jar", "light_firefly_glass_jar" ->
				tickFireflyJar(level, pos);
			case "starcall_block" -> tickStarcallBlock(level, machine);
			case "starcall_crack" -> tickStarcallCrack(level, machine);
			case "dyedream_crack" -> tickDyedreamCrack(level, machine);
			case "dream_spawner_0" -> tickDreamSpawner(level, machine);
			case "workshop_blast" -> tickWorkshopBlast(level, machine);
			case "workshop_anvil" -> tickWorkshopAnvil(level, machine);
			case "weapon_workshop" -> tickWeaponWorkshop(level, machine);
			case "aaroncos_hand_spawn_block" -> tickAaroncosSpawn(level, machine);
			case "shadow_fungus" -> {
				if (level.getRandom().nextDouble() < 0.06D
					&& level.getBlockState(pos.above()).isAir()
					&& level.getBlockState(pos.above(3)).isAir()
					&& level.getBlockState(pos.above(5)).isAir()
					&& level.getBlockState(pos.above(7)).isAir()
					&& level.getBlockState(pos.above(9)).isAir()) {
					String[] templates = {
						"shadow_fungustree_0", "shadow_fungustree_1",
						"shadow_fungustree_2", "shadow_fungustree_3",
						"shadow_fungustree_4", "shadow_fungustree_5",
						"shadow_fungustree_6", "shadow_fungustree_7"
					};
					PasterDreamStructureBlockPlacer.placeNamed(
						level,
						pos.offset(-2, 0, -2),
						templates[level.getRandom().nextInt(templates.length)]
					);
				}
			}
			case "dream_train_structure" -> {
				PasterDreamStructureBlockPlacer.placeNamed(level, pos, "dream_train");
				level.removeBlock(pos, false);
				level.getServer().getPlayerList().broadcastSystemMessage(
					net.minecraft.network.chat.Component.literal("一辆逐梦列车呼啸而过..."),
					false
				);
				level.getServer().getPlayerList().broadcastSystemMessage(
					net.minecraft.network.chat.Component.literal(
						"列车向我们暴露了一个位置信息... §a"
							+ (level.getRandom().nextBoolean() ? "X：" + pos.getX() : "Z：" + pos.getZ())
					),
					false
				);
			}
			default -> {
			}
		}
	}

	public static void onPlaced(Level level, BlockPos pos, BlockState state) {
		if (level.isClientSide()) {
			return;
		}
		var key = BuiltInRegistries.BLOCK.getKey(state.getBlock());
		if (!PasterDream.MOD_ID.equals(key.getNamespace())) {
			return;
		}
		String id = key.getPath();
				if (PasterDreamStructureBlockPlacer.supports(id)
					&& level.getBlockEntity(pos) instanceof PasterDreamBlockEntity machine
					&& !machine.stateData().getBoolean("structure_placed")) {
					machine.stateData().putBoolean("structure_placed", true);
				if (level instanceof ServerLevel serverLevel) {
					PasterDreamStructureBlockPlacer.place(serverLevel, pos, id);
				}
					if (level.getBlockState(pos).is(state.getBlock())) {
						level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
					}
				}
				if (level.getBlockEntity(pos) instanceof PasterDreamBlockEntity machine) {
					switch (id) {
						case "guard_block", "guard_crystal" -> {
							if (machine.stateData().getDouble("range") <= 0.0D) {
								machine.stateData().putDouble("range", 16.0D);
							}
							machine.stateData().putBoolean("switch", true);
							machine.markStateChanged();
						}
						case "restrainmove_block" -> {
							if (!machine.stateData().contains("range")) {
								machine.stateData().putDouble("range", 0.0D);
							}
							machine.stateData().putBoolean("switch", true);
							machine.markStateChanged();
						}
						case "the_endless_book_of_dream_seekers" ->
							machine.stateData().putInt("time", 0);
						case "dream_spawner_0" -> {
							machine.stateData().putBoolean("first", false);
							machine.stateData().putDouble("player_range", 16.0D);
							machine.stateData().putDouble("number", 0.0D);
							machine.markStateChanged();
						}
						case "starcall_block", "starcall_crack" -> {
							machine.stateData().putLong("placed_at", level.getGameTime());
							machine.markStateChanged();
						}
						case "dream_accumulator" -> level.playSound(
							null,
							pos,
							PasterDreamSounds.DREAM1,
							SoundSource.NEUTRAL,
							0.8F,
							1.0F
						);
						case "windmoor_crate" -> {
							if (level.getGameRules().getBoolean(PasterDreamGameRules.DEBUG_MODE)) {
								machine.stateData().putBoolean("new_loots", true);
								machine.markStateChanged();
							}
						}
						case "shadow_vortex" -> {
							machine.stateData().putLong(
								"activate_at",
								level.getGameTime() + 20L
							);
							machine.markStateChanged();
						}
						default -> {
						}
					}
				}
		if (id.endsWith("_crop") || id.endsWith("_seed")
			|| id.equals("dyedream_sapling")) {
			level.scheduleTick(pos, state.getBlock(), 20);
		}
		if (id.equals("shadow_dungeon_door_0")) {
			for (BlockPos offset : List.of(
				pos.offset(1, 0, 1),
				pos.offset(1, 0, -1),
				pos.offset(-1, 0, -1),
				pos.offset(-1, 0, 1),
				pos.east(),
				pos.west(),
				pos.north(),
				pos.south()
			)) {
				level.setBlock(
					offset,
					PasterDreamBlocks.SHADOW_DUNGEON_DOOR_1.defaultBlockState(),
					3
				);
			}
		} else if (id.equals("shadow_dungeon_door_2")) {
			for (BlockPos offset : List.of(
				pos.offset(1, 1, 0),
				pos.offset(1, -1, 0),
				pos.offset(-1, -1, 0),
				pos.offset(-1, 1, 0),
				pos.east(),
				pos.west(),
				pos.above(),
				pos.below()
			)) {
				level.setBlock(
					offset,
					PasterDreamBlocks.SHADOW_DUNGEON_DOOR_3.defaultBlockState(),
					3
				);
			}
		}
	}

	public static void neighborChanged(Level level, BlockPos pos, BlockState state) {
		if (level.isClientSide()) {
			return;
		}
		var key = BuiltInRegistries.BLOCK.getKey(state.getBlock());
		if (PasterDream.MOD_ID.equals(key.getNamespace())
			&& (key.getPath().equals("pebble_0")
				|| key.getPath().equals("big_bubble")
				|| key.getPath().equals("windmoor_leaves_2")
				|| key.getPath().equals("fig_vine"))) {
			level.scheduleTick(pos, state.getBlock(), 1);
		}
	}

	public static void scheduledTick(Level level, BlockPos pos, BlockState state) {
		if (!(level instanceof ServerLevel serverLevel)) {
			return;
		}
		var key = BuiltInRegistries.BLOCK.getKey(state.getBlock());
		if (!PasterDream.MOD_ID.equals(key.getNamespace())) {
			return;
		}
		String id = key.getPath();
		if (id.equals("meltdream_liquid")) {
			if (level.getGameTime() % 10L == 0L) {
				serverLevel.sendParticles(
					(ParticleOptions) PasterDreamParticles.MELTDREAM_CRYSTAL_PARTICLE,
					pos.getX() + 0.5D,
					pos.getY() + 0.2D,
					pos.getZ() + 0.5D,
					4,
					0.4D,
					0.4D,
					0.4D,
					0.1D
				);
			}
			return;
		}
		if (id.equals("shadow_candle")) {
			if (level.getGameTime() % 5L == 0L) {
				serverLevel.sendParticles(
					(ParticleOptions) PasterDreamParticles.SOUL_PARTICLE,
					pos.getX() + 0.5D,
					pos.getY() + 0.7D,
					pos.getZ() + 0.5D,
					1,
					0.0D,
					0.1D,
					0.0D,
					0.0D
				);
			}
			return;
		}
		if (id.equals("dyedream_ice")) {
			if (state.getLightEmission() >= 13) {
				level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
			}
			return;
		}
		if ((id.equals("windmoor_leaves_2") || id.equals("fig_vine"))
			&& level.getBlockState(pos.above()).isAir()) {
			level.removeBlock(pos, false);
			return;
		}
		if (id.equals("pebble_0") && !level.getBlockState(pos.below()).canOcclude()) {
			level.removeBlock(pos, false);
			Block.popResource(level, pos, new ItemStack(PasterDreamBlocks.PEBBLE_0));
			return;
		}
		if (id.equals("big_bubble")
			&& !level.getGameRules().getBoolean(PasterDreamGameRules.DEBUG_MODE)) {
			level.removeBlock(pos, false);
			return;
		}
		if (id.equals("dyedream_camellia_seed") && level.dimension().equals(DYEDREAM_WORLD)
			&& level.getRandom().nextDouble() < 0.05D) {
			level.setBlock(pos, PasterDreamBlocks.CROP_0A.defaultBlockState(), 3);
		} else if (id.equals("pale_snow_lotus_seed")
			&& level.getRandom().nextDouble() < 0.05D) {
			level.setBlock(pos, PasterDreamBlocks.CROP_1A.defaultBlockState(), 3);
		} else if (id.equals("luminous_violet_seed")
			&& level.getRandom().nextDouble() < 0.05D) {
			level.setBlock(pos, PasterDreamBlocks.CROP_2A.defaultBlockState(), 3);
		} else if (id.equals("ringing_cloud_flower_seed")
			&& level.getRandom().nextDouble() < 0.05D) {
			level.setBlock(pos, PasterDreamBlocks.CROP_3A.defaultBlockState(), 3);
		} else if (id.equals("cotton_crop_seed")
			&& level.getRandom().nextDouble() < 0.09D) {
			level.setBlock(pos, PasterDreamBlocks.CROP_4A.defaultBlockState(), 3);
		} else if (id.equals("dyedream_sapling")) {
			growDyedreamTree(serverLevel, pos);
		}
	}

	private static void tickPlayerInside(ServerPlayer player) {
		if (!(player.level() instanceof ServerLevel level)) {
			return;
		}
		BlockPos pos = player.blockPosition();
		BlockState state = level.getBlockState(pos);
		String id = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
		switch (id) {
			case "breakwind_curtain" -> {
				if (!player.onGround()) {
					player.setDeltaMovement(
						player.getLookAngle().x * 5.0D,
						player.getLookAngle().y,
						player.getLookAngle().z * 5.0D
					);
					player.hurtMarked = true;
				}
			}
			case "ejection_pressure_plate" -> {
				if (player.getDeltaMovement().y < 0.5D) {
					schedule(level, 2, ignored -> {
						player.setDeltaMovement(0.0D, 0.8D, 0.0D);
						player.fallDistance = 3.0F;
						player.hurtMarked = true;
					});
				}
			}
			case "aaroncos_arena_portals" -> {
				if (PasterDreamAdvancements.completed(player, "achievement_shadow_d_0")
					|| player.isCreative()) {
					player.setGameMode(net.minecraft.world.level.GameType.ADVENTURE);
					ServerLevel arena = player.server.getLevel(
						ResourceKey.create(
							Registries.DIMENSION,
							PasterDream.id("aaroncos_arena_world")
						)
					);
					if (arena != null) {
						player.teleportTo(
							arena,
							0.0D,
							70.0D,
							0.0D,
							player.getYRot(),
							player.getXRot()
						);
						player.addEffect(new MobEffectInstance(
							MobEffects.SLOW_FALLING,
							120,
							0
						));
					}
				}
			}
			default -> {
			}
		}
	}

	public static void entityInside(
		Level level,
		BlockPos pos,
		BlockState state,
		Entity entity
	) {
		if (level.isClientSide() || !(entity instanceof LivingEntity living)) {
			return;
		}
		var key = BuiltInRegistries.BLOCK.getKey(state.getBlock());
		if (!PasterDream.MOD_ID.equals(key.getNamespace())) {
			return;
		}
		switch (key.getPath()) {
			case "guard_block", "guard_crystal" -> living.addEffect(new MobEffectInstance(
				PasterDreamContent.effectHolder(PasterDreamEffects.GUARD_BLOCK_BUFF),
				20,
				0,
				false,
				false
			));
			case "restrainmove_block" -> living.addEffect(new MobEffectInstance(
				PasterDreamContent.effectHolder(PasterDreamEffects.RESTRAINMOVE_BLOCK_BUFF),
				20,
				0,
				false,
				false
			));
			case "starcall_block", "starcall_crack" -> {
				if (level.getGameTime() % 10L == 0L && level instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(
						(ParticleOptions) PasterDreamParticles.STARCALL_PARTICLE,
						pos.getX() + 0.5D,
						pos.getY() + 0.5D,
						pos.getZ() + 0.5D,
						2,
						0.2D,
						0.2D,
						0.2D,
						0.01D
					);
				}
			}
			case "shadow_trap_0" -> {
				if (entity instanceof Player player
					&& level.getBlockEntity(pos) instanceof PasterDreamBlockEntity trap
					&& !trap.stateData().getBoolean("switch")) {
					trap.stateData().putBoolean("switch", true);
					trap.markStateChanged();
					living.hurt(level.damageSources().magic(), 5.0F);
					if (player instanceof ServerPlayer serverPlayer) {
						PasterDreamPlayerState.addSan(serverPlayer, -5.0D);
					}
					living.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 80, 0));
					living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
					level.playSound(
						null,
						pos,
						PasterDreamSounds.SHADOW_TRAP_0,
						SoundSource.NEUTRAL,
						1.0F,
						1.0F
					);
					if (level instanceof ServerLevel serverLevel) {
						schedule(serverLevel, 25, ignored ->
							level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3)
						);
					}
				}
			}
			case "crimson_thorn" -> {
				entity.setRemainingFireTicks(Math.max(entity.getRemainingFireTicks(), 20));
				living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 0));
			}
			case "dyedream_crack" -> {
				if (level.getBlockEntity(pos) instanceof PasterDreamBlockEntity machine) {
					int count = machine.stateData().getInt("number") + 1;
					if (count >= 5) {
						count = 0;
					}
					machine.stateData().putInt("number", count);
					machine.markStateChanged();
					if (level instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(
							(ParticleOptions) PasterDreamParticles.CRACK_0_PARTICLE,
							pos.getX() + 1.0D,
							pos.getY() + 1.0D,
							pos.getZ() + 1.0D,
							12,
							0.5D,
							0.5D,
							0.2D,
							0.1D
						);
					}
					if (entity instanceof ServerPlayer player && !machine.stateData().getBoolean("music")) {
						if (!level.dimension().equals(DYEDREAM_WORLD)
							&& PasterDreamAdvancements.completed(player, "achievement_a_0")) {
							machine.stateData().putBoolean("music", true);
							player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 1, false, false));
							player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 3, false, false));
							player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 19, false, false));
							schedule((ServerLevel) level, 50, ignored -> {
								ServerLevel destination = player.server.getLevel(DYEDREAM_WORLD);
								if (destination != null) {
									double y = level.getBlockState(new BlockPos(0, 100, 0)).isAir()
										? 118.0D
										: 168.0D;
									player.teleportTo(destination, 0.5D, y, 0.5D, player.getYRot(), player.getXRot());
								}
								machine.stateData().putBoolean("music", false);
								machine.markStateChanged();
							});
						} else if (level.dimension().equals(DYEDREAM_WORLD)
							&& !PasterDreamConfig.common().disableDyedreamCrackReturn) {
							machine.stateData().putBoolean("music", true);
							player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 1, false, false));
							player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 3, false, false));
							player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 19, false, false));
							schedule((ServerLevel) level, 50, ignored -> {
								ServerLevel overworld = player.server.overworld();
								BlockPos spawn = player.getRespawnPosition() != null
									? player.getRespawnPosition()
									: overworld.getSharedSpawnPos();
								player.teleportTo(
									overworld,
									spawn.getX() + 0.5D,
									spawn.getY(),
									spawn.getZ() + 0.5D,
									player.getYRot(),
									player.getXRot()
								);
								machine.stateData().putBoolean("music", false);
								machine.markStateChanged();
							});
						}
					}
				}
			}
			default -> {
			}
		}
	}

	private static void tickForcedTower(ServerLevel level, BlockPos pos) {
		if (level.getGameTime() % 5L != 0L
			|| level.getEntitiesOfClass(
				Player.class,
				net.minecraft.world.phys.AABB.ofSize(
					net.minecraft.world.phys.Vec3.atCenterOf(pos),
					7.0D,
					7.0D,
					7.0D
				),
				player -> true
			).isEmpty()) {
			return;
		}
		level.playSound(
			null,
			pos,
			SoundEvents.BEACON_AMBIENT,
			SoundSource.NEUTRAL,
			1.5F,
			1.0F
		);
	}

	private static void tickLostSword(ServerLevel level, BlockPos pos) {
		if (level.getGameTime() % 20L == 0L) {
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.DUST_0_PARTICLE,
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				8,
				0.5D,
				0.6D,
				0.5D,
				0.02D
			);
		}
	}

	private static void tickRestDoll(ServerLevel level, BlockPos pos) {
		if (level.getGameTime() % 20L != 0L) {
			return;
		}
		for (Player player : level.getEntitiesOfClass(
			Player.class,
			net.minecraft.world.phys.AABB.ofSize(
				net.minecraft.world.phys.Vec3.atCenterOf(pos),
				7.5D,
				7.5D,
				7.5D
			),
			player -> true
		)) {
			player.addEffect(new MobEffectInstance(
				PasterDreamContent.effectHolder(PasterDreamEffects.REST_BUFF),
				1200,
				0,
				false,
				false
			));
		}
	}

	private static void tickProtectionBlock(
		ServerLevel level,
		PasterDreamBlockEntity machine,
		String id
	) {
		int interval = id.equals("guard_crystal") ? 10 : 20;
		if (level.getGameTime() % interval != 0L
			|| level.getGameRules().getBoolean(PasterDreamGameRules.DEBUG_MODE)) {
			return;
		}
		double range = machine.stateData().getDouble("range");
		if (range <= 0.0D && !id.equals("restrainmove_block")) {
			range = 16.0D;
			machine.stateData().putDouble("range", range);
			machine.markStateChanged();
		}
		if (range <= 0.0D) {
			return;
		}
		var effect = id.equals("restrainmove_block")
			? PasterDreamEffects.RESTRAINMOVE_BLOCK_BUFF
			: PasterDreamEffects.GUARD_BLOCK_BUFF;
		for (Player player : level.getEntitiesOfClass(
			Player.class,
			net.minecraft.world.phys.AABB.ofSize(
				net.minecraft.world.phys.Vec3.atCenterOf(machine.getBlockPos()),
				range,
				range,
				range
			),
			player -> true
		)) {
			player.addEffect(new MobEffectInstance(
				PasterDreamContent.effectHolder(effect),
				60,
				0,
				false,
				false
			));
		}
	}

	private static void tickTwilightLantern(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		if (!machine.stateData().getBoolean("switch") || level.getGameTime() % 20L != 0L) {
			return;
		}
		int step = machine.stateData().getInt("number") + 1;
		machine.stateData().putInt("number", step);
		BlockPos pos = machine.getBlockPos();
		level.sendParticles(
			(ParticleOptions) PasterDreamParticles.SHADOW_STONE_PARTICLE,
			pos.getX() + 0.5D,
			pos.getY() + 1.0D,
			pos.getZ() + 0.5D,
			8,
			1.0D,
			1.0D,
			1.0D,
			0.05D
		);
		if (step == 30) {
			summon(level, "shadow_golem", pos.offset(0, -1, 13));
			summon(level, "terrorbeak", pos.offset(-13, -1, 0));
		} else if (step == 80) {
			summon(level, "shadow_golem", pos.offset(0, -1, -13));
			summon(level, "terrorbeak", pos.offset(-13, -1, 0));
		}
		machine.markStateChanged();
	}

	private static void tickWindmoorCrate(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		if (!level.getGameRules().getBoolean(PasterDreamGameRules.DEBUG_MODE)) {
			machine.stateData().remove("new_loots");
			return;
		}
		if (!machine.stateData().getBoolean("new_loots")) {
			machine.stateData().putBoolean("new_loots", true);
			machine.markStateChanged();
		}
		if (level.getGameTime() % 20L == 0L) {
			BlockPos pos = machine.getBlockPos();
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.MELTDREAM_CRYSTAL_PARTICLE,
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				4,
				0.17D,
				0.2D,
				0.17D,
				0.01D
			);
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.DUST_0_PARTICLE,
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				4,
				0.17D,
				0.2D,
				0.17D,
				0.01D
			);
		}
	}

	private static void tickEndlessBook(ServerLevel level, BlockPos pos) {
		if (level.getGameTime() % 10L == 0L) {
			level.sendParticles(
				ParticleTypes.ENCHANT,
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				6,
				0.4D,
				0.4D,
				0.4D,
				1.0D
			);
		}
	}

	private static void tickFireflyJar(ServerLevel level, BlockPos pos) {
		if (level.getGameTime() % 20L == 0L) {
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.FIREFLY_GLASS_JAR_PARTICLE,
				pos.getX() + 0.5D,
				pos.getY() + 0.4D,
				pos.getZ() + 0.5D,
				2,
				0.1D,
				0.15D,
				0.1D,
				0.01D
			);
		}
	}

	private static void tickStarcallBlock(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		BlockPos pos = machine.getBlockPos();
		if (level.getGameTime() - machine.stateData().getLong("placed_at") >= 1200L) {
			level.removeBlock(pos, false);
			return;
		}
		if (level.getGameTime() % 40L == 0L) {
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.STARCALL_PARTICLE,
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				7,
				2.0D,
				2.0D,
				2.0D,
				0.01D
			);
		}
	}

	@SuppressWarnings("unchecked")
	private static void tickStarcallCrack(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		BlockPos pos = machine.getBlockPos();
		if (level.getGameTime() - machine.stateData().getLong("placed_at") >= 200L) {
			level.removeBlock(pos, false);
			return;
		}
		if (level.getGameTime() % 10L != 0L) {
			return;
		}
		level.sendParticles(
			ParticleTypes.LAVA,
			pos.getX() + 0.5D,
			pos.getY() - 0.1D,
			pos.getZ() + 0.5D,
			3,
			1.0D,
			0.1D,
			1.0D,
			0.1D
		);
		level.playSound(
			null,
			pos,
			SoundEvents.DRAGON_FIREBALL_EXPLODE,
			SoundSource.NEUTRAL,
			0.6F,
			1.0F
		);
		if (level.getFluidState(pos.below(10)).isEmpty()) {
			var projectile = new dev.arakiel.pasterdream.content.PasterDreamProjectile(
				(net.minecraft.world.entity.EntityType<dev.arakiel.pasterdream.content.PasterDreamProjectile>)
					PasterDreamEntities.get("projectile_moltengold_wand_projectile"),
				level
			);
			projectile.setPos(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
			projectile.shoot(0.0D, -1.0D, 0.0D, 1.0F, 8.0F);
			projectile.setBaseDamage(5.0D);
			projectile.setSilent(true);
			level.addFreshEntity(projectile);
		}
	}

	private static void tickDyedreamCrack(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		if (level.getGameTime() % 10L != 0L) {
			return;
		}
		int step = machine.stateData().getInt("number") + 1;
		if (step >= 5) {
			step = 0;
		}
		machine.stateData().putInt("number", step);
		BlockPos pos = machine.getBlockPos();
		level.sendParticles(
			(ParticleOptions) PasterDreamParticles.CRACK_0_PARTICLE,
			pos.getX() + 1.0D,
			pos.getY() + 1.0D,
			pos.getZ() + 1.0D,
			12,
			0.5D,
			0.5D,
			0.2D,
			0.1D
		);
		if (step == 1) {
			level.playSound(
				null,
				pos,
				SoundEvents.AMETHYST_BLOCK_RESONATE,
				SoundSource.BLOCKS,
				1.0F,
				1.0F
			);
		}
		machine.markStateChanged();
	}

	private static void tickDreamSpawner(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		if (level.getGameTime() % 10L != 0L
			|| level.getGameRules().getBoolean(PasterDreamGameRules.DEBUG_MODE)
			|| machine.getItem(0).isEmpty()) {
			return;
		}
		double range = machine.stateData().getDouble("player_range");
		if (range <= 0.0D) {
			range = 16.0D;
			machine.stateData().putDouble("player_range", range);
		}
		BlockPos pos = machine.getBlockPos();
		if (level.getEntitiesOfClass(
			Player.class,
			net.minecraft.world.phys.AABB.ofSize(
				net.minecraft.world.phys.Vec3.atCenterOf(pos),
				range,
				range,
				range
			),
			player -> true
		).isEmpty()) {
			return;
		}
		ItemStack egg = machine.getItem(0);
		if (!(egg.getItem() instanceof SpawnEggItem spawnEgg)) {
			return;
		}
		double remaining = machine.stateData().getDouble("number");
		if (!machine.stateData().getBoolean("first")) {
			machine.stateData().putBoolean("first", true);
		} else if (remaining > 0.0D && level.getRandom().nextDouble() < 0.1D) {
			machine.stateData().putDouble("number", remaining - 1.0D);
		} else if (remaining <= 0.0D) {
			level.setBlock(pos, PasterDreamBlocks.get("dream_spawner_1").defaultBlockState(), 3);
			return;
		} else {
			machine.markStateChanged();
			return;
		}
		Entity spawned = spawnEgg.getType(egg).create(level);
		if (spawned != null) {
			spawned.moveTo(
				pos.getX() + 0.5D,
				pos.getY(),
				pos.getZ() + 0.5D,
				level.getRandom().nextFloat() * 360.0F,
				0.0F
			);
			level.addFreshEntity(spawned);
		}
		level.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 10, 0.5D, 0.5D, 0.5D, 0.0D);
		level.sendParticles(ParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 10, 0.5D, 0.5D, 0.5D, 0.0D);
		machine.markStateChanged();
	}

	private static void tickWorkshopBlast(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		ItemStack lava = machine.getItem(2);
		if (lava.is(Items.LAVA_BUCKET) && machine.stateData().getInt("lava") + 1000 <= 4000) {
			machine.stateData().putInt("lava", machine.stateData().getInt("lava") + 1000);
			machine.setItem(2, ItemStack.EMPTY);
			ItemStack buckets = machine.getItem(3);
			if (buckets.isEmpty()) {
				machine.setItem(3, new ItemStack(Items.BUCKET));
			} else if (buckets.is(Items.BUCKET)) {
				buckets.grow(1);
				machine.setItem(3, buckets);
			}
			machine.markStateChanged();
		}
		if (!machine.stateData().getBoolean("switch")) {
			ItemStack input = machine.getItem(0);
			if (!isEmbryo(input)
				|| PasterDreamItemData.read(input).getDouble("process") >= 1.0D
				|| !machine.getItem(4).isEmpty()
				|| machine.stateData().getInt("lava") < 1000) {
				return;
			}
			machine.setItem(4, input.copyWithCount(1));
			input.shrink(1);
			machine.setItem(0, input);
			machine.stateData().putInt("lava", machine.stateData().getInt("lava") - 1000);
			machine.stateData().putBoolean("switch", true);
			machine.stateData().putInt("number", 0);
			machine.markStateChanged();
			level.playSound(
				null,
				machine.getBlockPos(),
				PasterDreamSounds.MACHINE2,
				SoundSource.BLOCKS,
				1.2F,
				1.0F
			);
			return;
		}
		if (level.getGameTime() % 10L != 0L) {
			return;
		}
		int number = machine.stateData().getInt("number") + 1;
		machine.stateData().putInt("number", number);
		BlockPos pos = machine.getBlockPos();
		level.sendParticles(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, 7, 0.0D, 3.0D, 0.0D, 0.15D);
		level.sendParticles(ParticleTypes.LAVA, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, 4, 0.05D, 3.0D, 0.05D, 0.15D);
		level.playSound(null, pos, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 2.0F, 1.0F);
		if (number == 5 || number == 10 || number == 15 || number == 20) {
			level.playSound(null, pos, PasterDreamSounds.MACHINE2, SoundSource.BLOCKS, 1.2F, 1.0F);
		}
		if (number >= 23) {
			ItemStack result = machine.getItem(4);
			if (!result.isEmpty()) {
				PasterDreamItemData.update(result, tag -> tag.putDouble("process", 1.0D));
				result.set(
					net.minecraft.core.component.DataComponents.CUSTOM_NAME,
					net.minecraft.network.chat.Component.literal("未完工原胚（待锤炼）")
				);
				machine.setItem(4, result);
			}
			machine.stateData().putBoolean("switch", false);
			machine.stateData().putInt("number", 0);
			level.playSound(
				null,
				pos,
				PasterDreamSounds.MACHINE1,
				SoundSource.NEUTRAL,
				1.2F,
				1.0F
			);
		}
		machine.markStateChanged();
	}

	private static void tickWorkshopAnvil(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		if (!machine.stateData().getBoolean("switch")) {
			ItemStack input = machine.getItem(0);
			if (input.is(net.minecraft.tags.TagKey.create(
					Registries.ITEM,
					PasterDream.id("embryo_items")
				))
				&& PasterDreamItemData.read(input).getDouble("process") == 1.0D
				&& machine.getItem(1).isEmpty()) {
				machine.setItem(1, input.copy());
				input.shrink(1);
				machine.setItem(0, input);
				machine.stateData().putBoolean("switch", true);
				machine.stateData().putInt("number", level.getRandom().nextIntBetweenInclusive(1, 5));
				machine.stateData().putInt("game", 0);
				machine.stateData().putDouble("score", 0.0D);
				machine.markStateChanged();
			}
			return;
		}
		int game = machine.stateData().getInt("game") + 1;
		if (game < 16) {
			machine.stateData().putInt("game", game);
			return;
		}
		ItemStack result = machine.getItem(1);
		if (!result.isEmpty()) {
			double score = Math.max(0.0D, Math.min(20.0D, machine.stateData().getDouble("score")));
			PasterDreamItemData.update(result, tag -> {
				tag.putDouble("process", 2.0D);
				tag.putDouble(
					"paster_attack_damage_number",
					tag.getDouble("paster_attack_damage_number") * score * 0.1D
				);
				tag.putDouble(
					"paster_luck_number",
					tag.getDouble("paster_luck_number") * score * 0.1D
				);
			});
			result.set(net.minecraft.core.component.DataComponents.CUSTOM_NAME,
				net.minecraft.network.chat.Component.literal("未完成原胚（待淬火）"));
			machine.setItem(1, result);
		}
		machine.stateData().putBoolean("switch", false);
		machine.stateData().putInt("game", 0);
		machine.stateData().putInt("number", 0);
		machine.stateData().putDouble("score", 0.0D);
		machine.markStateChanged();
	}

	@SuppressWarnings("unchecked")
	private static void tickWeaponWorkshop(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		ItemStack output = machine.getItem(6);
		if (!output.isEmpty()) {
			return;
		}
		List<ItemStack> stacks = List.of(
			machine.getItem(0),
			machine.getItem(1),
			machine.getItem(2),
			machine.getItem(3),
			machine.getItem(4)
		);
		RecipeType<dev.arakiel.pasterdream.gameplay.recipe.WeaponWorkshopRecipe> type =
			PasterDreamRecipeTypes.WEAPON_WORKSHOP;
		var holder = level.getRecipeManager()
			.getRecipeFor(
				type,
				new dev.arakiel.pasterdream.gameplay.recipe.WorkshopRecipeInput(stacks),
				level
			)
			.orElse(null);
		if (holder == null
			|| !(holder.value() instanceof dev.arakiel.pasterdream.gameplay.recipe.WeaponWorkshopRecipe recipe)) {
			return;
		}
		for (int slot = 0; slot < recipe.inputs().size(); slot++) {
			ItemStack required = recipe.inputs().get(slot);
			if (required.isEmpty() || required.getCount() <= 0) {
				continue;
			}
			ItemStack stack = machine.getItem(slot);
			stack.shrink(required.getCount());
			machine.setItem(slot, stack);
		}
		machine.setItem(6, recipe.result().copy());
		machine.stateData().putString("recipe", holder.id().toString());
		machine.markStateChanged();
		level.playSound(
			null,
			machine.getBlockPos(),
			PasterDreamSounds.MACHINE1,
			SoundSource.BLOCKS,
			1.0F,
			1.0F
		);
	}

	@SuppressWarnings("unchecked")
	private static void tickDreamCauldron(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		ItemStack bucket = machine.getItem(4);
		if (bucket.is(PasterDreamItems.MELTDREAM_LIQUID_BUCKET)
			&& machine.stateData().getInt("fluid") + 1000 <= 4000) {
			machine.stateData().putInt("fluid", machine.stateData().getInt("fluid") + 1000);
			machine.setItem(4, ItemStack.EMPTY);
			ItemStack empties = machine.getItem(5);
			if (empties.isEmpty()) {
				machine.setItem(5, new ItemStack(Items.BUCKET));
			} else if (empties.is(Items.BUCKET)) {
				empties.grow(1);
				machine.setItem(5, empties);
			}
			machine.markStateChanged();
			level.playSound(
				null,
				machine.getBlockPos(),
				SoundEvents.BUCKET_FILL,
				SoundSource.BLOCKS,
				1.0F,
				1.0F
			);
		}
		int craftTicks = machine.stateData().getInt("craft_ticks");
		if (craftTicks > 0) {
			craftTicks--;
			machine.stateData().putInt("craft_ticks", craftTicks);
			if (craftTicks == 0) {
				var location = net.minecraft.resources.ResourceLocation.tryParse(
					machine.stateData().getString("result_item")
				);
				if (location != null) {
					var item = BuiltInRegistries.ITEM.getOptional(location).orElse(Items.AIR);
					if (item != Items.AIR) {
						machine.setItem(6, new ItemStack(item));
					}
				}
				machine.stateData().remove("result_item");
				machine.markStateChanged();
			}
			return;
		}
		ItemStack output = machine.getItem(6);
		if (!output.isEmpty()) {
			return;
		}
		List<ItemStack> stacks = List.of(
			machine.getItem(0),
			machine.getItem(1),
			machine.getItem(2),
			machine.getItem(3)
		);
		RecipeType<dev.arakiel.pasterdream.gameplay.recipe.DreamCauldronRecipe> type =
			PasterDreamRecipeTypes.DREAM_CAULDRON;
		var holder = level.getRecipeManager()
			.getRecipeFor(
				type,
				new dev.arakiel.pasterdream.gameplay.recipe.WorkshopRecipeInput(stacks),
				level
			)
			.orElse(null);
		if (holder == null
			|| !(holder.value() instanceof dev.arakiel.pasterdream.gameplay.recipe.DreamCauldronRecipe recipe)
			|| machine.stateData().getInt("fluid") < recipe.fluidCost()) {
			return;
		}
		for (int slot = 0; slot < recipe.inputs().size(); slot++) {
			ItemStack required = recipe.inputs().get(slot);
			if (required.isEmpty() || required.getCount() <= 0) {
				continue;
			}
			ItemStack stack = machine.getItem(slot);
			stack.shrink(required.getCount());
			machine.setItem(slot, stack);
		}
		machine.stateData().putInt(
			"fluid",
			machine.stateData().getInt("fluid") - recipe.fluidCost()
		);
		machine.stateData().putString(
			"result_item",
			BuiltInRegistries.ITEM.getKey(recipe.result().getItem()).toString()
		);
		machine.stateData().putString("recipe", holder.id().toString());
		machine.stateData().putInt("craft_ticks", 59);
		machine.markStateChanged();
		BlockPos pos = machine.getBlockPos();
		level.sendParticles(
			(ParticleOptions) PasterDreamParticles.MELTDREAM_CRYSTAL_PARTICLE,
			pos.getX() + 0.5D,
			pos.getY() + 1.0D,
			pos.getZ() + 0.5D,
			24,
			0.5D,
			0.5D,
			0.5D,
			0.05D
		);
		level.playSound(
			null,
			pos,
			PasterDreamSounds.MACHINE0,
			SoundSource.BLOCKS,
			1.0F,
			1.0F
		);
	}

	private static void tickResearchTable(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		if (level.getGameTime() % 10L != 0L
			|| !machine.getItem(0).is(PasterDreamItems.PEN_AND_INK)
			|| !machine.getItem(4).is(PasterDreamItems.UNKNOWN_NOTES)
			|| !machine.getItem(5).isEmpty()) {
			return;
		}
		ServerPlayer viewer = null;
		for (ServerPlayer player : level.players()) {
			if (player.containerMenu instanceof dev.arakiel.pasterdream.content.PasterDreamMenu menu
				&& menu.spec().id().equals(PasterDreamMenuSpecs.RESEARCH_TABLE)
				&& menu.pos().equals(machine.getBlockPos())) {
				viewer = player;
				break;
			}
		}
		if (viewer == null) {
			return;
		}
		if (!PasterDreamAdvancements.completed(viewer, "achievement_shadow_start")) {
			viewer.displayClientMessage(
				net.minecraft.network.chat.Component.literal("缺少前置知识 你还无法解读笔记的内容"),
				true
			);
			level.playSound(
				null,
				machine.getBlockPos(),
				SoundEvents.BOOK_PUT,
				SoundSource.BLOCKS,
				1.0F,
				1.0F
			);
			return;
		}
		String output = researchOutput(viewer);
		ItemStack pen = machine.getItem(0);
		pen.hurtAndBreak(1, viewer, net.minecraft.world.entity.EquipmentSlot.MAINHAND);
		machine.setItem(0, pen);
		ItemStack notes = machine.getItem(4);
		notes.shrink(1);
		machine.setItem(4, notes);
		if (output == null) {
			viewer.giveExperiencePoints(50);
			viewer.displayClientMessage(
				net.minecraft.network.chat.Component.literal("经验值+50"),
				false
			);
		} else {
			machine.setItem(
				5,
				new ItemStack(PasterDreamItems.get(output))
			);
			viewer.displayClientMessage(
				net.minecraft.network.chat.Component.literal("已获得新的寻梦者笔记"),
				false
			);
		}
		machine.markStateChanged();
		level.playSound(
			null,
			machine.getBlockPos(),
			SoundEvents.BOOK_PAGE_TURN,
			SoundSource.BLOCKS,
			1.2F,
			1.0F
		);
	}

	private static String researchOutput(ServerPlayer player) {
		if (PasterDreamAdvancements.completed(player, "achievement_hide_14")) {
			return PasterDreamAdvancements.completed(player, "achievement_hide_15")
				? null
				: "dream_note_13";
		}
		if (PasterDreamAdvancements.completed(player, "achievement_hide_13")) {
			return "dream_note_12";
		}
		if (PasterDreamAdvancements.completed(player, "achievement_hide_12")) {
			return "shadow_blast_furnace_blueprint";
		}
		if (PasterDreamAdvancements.completed(player, "achievement_hide_11")) {
			return "dream_note_11";
		}
		return "dream_note_10";
	}

	private static void tickAaroncosSpawn(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		if (!machine.stateData().getBoolean("switch")) {
			return;
		}
		int time = machine.stateData().getInt("time0") + 1;
		if (time >= 150) {
			time = 0;
			level.playSound(
				null,
				machine.getBlockPos(),
				PasterDreamSounds.AARONCOS_MUSIC,
				SoundSource.WEATHER,
				1.0F,
				1.0F
			);
		}
		machine.stateData().putInt("time0", time);
		if (time == 50 || time == 100 || time == 150) {
			summon(level, "terrorbeak", machine.getBlockPos().offset(0, 0, 12));
			summon(level, "terrorbeak", machine.getBlockPos().offset(0, 0, -12));
		}
		BlockPos pos = machine.getBlockPos();
		var area = net.minecraft.world.phys.AABB.ofSize(
			net.minecraft.world.phys.Vec3.atCenterOf(pos),
			99.0D,
			99.0D,
			99.0D
		);
		if (level.getEntitiesOfClass(
				dev.arakiel.pasterdream.content.PasterDreamMob.class,
				area,
				entity -> {
					String id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();
					return id.equals("aaroncos_lefthand_0") || id.equals("aaroncos_righthand_0");
				}
			).isEmpty()) {
			level.setBlock(
				pos.below(),
				PasterDreamBlocks.get("aaroncos_hand_chest").defaultBlockState(),
				3
			);
			level.removeBlock(pos, false);
			for (ServerPlayer player : level.players()) {
				PasterDreamAdvancements.award(player, "achievement_shadow_e_0");
				player.removeEffect(PasterDreamContent.effectHolder(
					PasterDreamEffects.SHADOW_SPYON_BUFF
				));
			}
		}
		machine.markStateChanged();
	}

	@SuppressWarnings("unchecked")
	private static void tickShadowBlasting(ServerLevel level, PasterDreamBlockEntity machine) {
		ItemStack input = machine.getItem(0);
		if (input.isEmpty()) {
			if (machine.stateData().getInt("progress") != 0) {
				machine.stateData().putInt("progress", 0);
				machine.markStateChanged();
			}
			return;
		}
		RecipeType<ShadowBlastingRecipe> type = PasterDreamRecipeTypes.SHADOW_BLASTING;
		var holder = level.getRecipeManager()
			.getRecipeFor(type, new SingleRecipeInput(input), level)
			.orElse(null);
		if (holder == null || !(holder.value() instanceof ShadowBlastingRecipe recipe)) {
			machine.stateData().putInt("progress", 0);
			return;
		}
		ItemStack primary = machine.getItem(2);
		ItemStack secondary = machine.getItem(3);
		if (!canMerge(primary, recipe.result()) || !canMerge(secondary, recipe.byproduct())) {
			return;
		}
		if (recipe.itemFuelCost() > 0) {
			ItemStack fuel = machine.getItem(1);
			if (fuel.isEmpty() || fuel.getCount() < recipe.itemFuelCost()) {
				return;
			}
		}
		int progress = machine.stateData().getInt("progress") + 1;
		if (progress < recipe.blastingTicks()) {
			machine.stateData().putInt("progress", progress);
			if (progress % 20 == 0) {
				machine.markStateChanged();
			}
			return;
		}
		input.shrink(1);
		machine.setItem(0, input);
		if (recipe.itemFuelCost() > 0) {
			ItemStack fuel = machine.getItem(1);
			fuel.shrink(recipe.itemFuelCost());
			machine.setItem(1, fuel);
		}
		machine.setItem(2, merge(primary, recipe.result()));
		if (!recipe.byproduct().isEmpty()
			&& level.getRandom().nextDouble() < recipe.byproductChance()) {
			machine.setItem(3, merge(secondary, recipe.byproduct()));
		}
		machine.stateData().putInt("progress", 0);
		machine.markStateChanged();
	}

	private static boolean canMerge(ItemStack existing, ItemStack addition) {
		return addition.isEmpty()
			|| existing.isEmpty()
			|| (ItemStack.isSameItemSameComponents(existing, addition)
				&& existing.getCount() + addition.getCount() <= existing.getMaxStackSize());
	}

	private static ItemStack merge(ItemStack existing, ItemStack addition) {
		if (addition.isEmpty()) {
			return existing;
		}
		if (existing.isEmpty()) {
			return addition.copy();
		}
		ItemStack result = existing.copy();
		result.grow(addition.getCount());
		return result;
	}

	private static void tickDreamAccumulator(
		ServerLevel level,
		PasterDreamBlockEntity machine
	) {
		if (!level.dimension().equals(DYEDREAM_WORLD)
			|| machine.stateData().getInt("time") >= 600) {
			return;
		}
		int time = machine.stateData().getInt("time") + 1;
		ItemStack sorbent = machine.getItem(1);
		if (sorbent.is(PasterDreamItems.SORBENT) && !sorbent.isEmpty()) {
			if (sorbent.isDamageableItem()) {
				sorbent.setDamageValue(sorbent.getDamageValue() + 1);
				if (sorbent.getDamageValue() >= sorbent.getMaxDamage()) {
					sorbent.shrink(1);
				}
			} else {
				sorbent.shrink(1);
			}
			time++;
			if (sorbent.isEmpty()) {
				machine.setItem(1, ItemStack.EMPTY);
			}
		}
		machine.stateData().putInt("time", time);
		if (level.getGameTime() % 10L == 0L) {
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.DUST_0_PARTICLE,
				machine.getBlockPos().getX() + 0.5D,
				machine.getBlockPos().getY() + 0.5D,
				machine.getBlockPos().getZ() + 0.5D,
				16,
				0.1D,
				0.2D,
				0.1D,
				0.02D
			);
		}
		if (time >= 600) {
			ItemStack output = machine.getItem(0);
			if (output.isEmpty()) {
				machine.setItem(0, new ItemStack(PasterDreamItems.DYEDREAM_DUST_PIECE));
			} else if (output.is(PasterDreamItems.DYEDREAM_DUST_PIECE)
				&& output.getCount() < 64) {
				output.grow(1);
				machine.setItem(0, output);
			}
			machine.stateData().putInt("time", 0);
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.DYEDREAM_0_PARTICLE,
				machine.getBlockPos().getX() + 0.5D,
				machine.getBlockPos().getY() + 0.5D,
				machine.getBlockPos().getZ() + 0.5D,
				16,
				0.1D,
				0.2D,
				0.1D,
				0.02D
			);
		}
	}

	private static void tickShadowVortex(ServerLevel level, PasterDreamBlockEntity machine) {
		if (!machine.stateData().getBoolean("switch")
			&& level.getGameTime() >= machine.stateData().getLong("activate_at")) {
			machine.stateData().putBoolean("switch", true);
			machine.stateData().putInt("time", 0);
			machine.markStateChanged();
		}
		if (!machine.stateData().getBoolean("switch")) {
			return;
		}
		int time = machine.stateData().getInt("time") + 1;
		if (time >= 30) {
			level.removeBlock(machine.getBlockPos(), false);
			return;
		}
		machine.stateData().putInt("time", time);
		BlockPos pos = machine.getBlockPos();
		level.sendParticles(
			(ParticleOptions) PasterDreamParticles.SHADOW_STONE_PARTICLE,
			pos.getX() + 0.5D,
			pos.getY(),
			pos.getZ() + 0.5D,
			64,
			3.0D,
			0.5D,
			3.0D,
			0.1D
		);
		level.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 64, 3.0D, 0.5D, 3.0D, 0.1D);
		boolean friendly = machine.stateData().getBoolean("friendly");
		var specialTag = net.minecraft.tags.TagKey.create(
			Registries.ENTITY_TYPE,
			PasterDream.id("special_entity_tag")
		);
		var shadowTag = net.minecraft.tags.TagKey.create(
			Registries.ENTITY_TYPE,
			PasterDream.id("shadow_mob")
		);
		for (Entity target : level.getEntitiesOfClass(
			Entity.class,
			net.minecraft.world.phys.AABB.ofSize(
				net.minecraft.world.phys.Vec3.atCenterOf(pos),
				9.0D,
				9.0D,
				9.0D
			),
			candidate -> candidate instanceof LivingEntity
				&& !candidate.getType().is(specialTag)
				&& (friendly ? !(candidate instanceof Player) : !candidate.getType().is(shadowTag))
		)) {
			LivingEntity living = (LivingEntity) target;
			if (friendly) {
				living.hurt(level.damageSources().magic(), 4.0F);
				living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 0, false, false));
			} else {
				living.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 20, 0));
				living.addEffect(new MobEffectInstance(
					PasterDreamContent.effectHolder(PasterDreamEffects.CONFUSION_BUFF),
					20,
					1
				));
				living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 0));
				living.hurt(level.damageSources().magic(), 3.0F);
			}
		}
	}

	private static InteractionResult use(
		Player player,
		Level level,
		InteractionHand hand,
		BlockHitResult hit
	) {
		BlockState state = level.getBlockState(hit.getBlockPos());
		var key = BuiltInRegistries.BLOCK.getKey(state.getBlock());
		if (!PasterDream.MOD_ID.equals(key.getNamespace()) || player.isSpectator()) {
			return InteractionResult.PASS;
		}
		String id = key.getPath();
		switch (id) {
			case "clay_pot_0" -> {
				if (level.getFluidState(hit.getBlockPos()).createLegacyBlock()
					.is(PasterDreamBlocks.SHADOW_LIQUID)
					&& level instanceof ServerLevel serverLevel) {
					serverLevel.explode(
						null,
						hit.getLocation().x,
						hit.getLocation().y,
						hit.getLocation().z,
						4.0F,
						Level.ExplosionInteraction.NONE
					);
					return InteractionResult.SUCCESS;
				}
			}
			case "qym_doll_0", "uuz_doll_0" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return useDoll(serverPlayer, hit.getBlockPos());
				}
			}
			case "calle_card_block" -> {
				level.removeBlock(hit.getBlockPos(), false);
				give(player, "calle_card_0", 1);
				level.playSound(null, hit.getBlockPos(), PasterDreamSounds.CARD0, SoundSource.NEUTRAL, 1.0F, 1.0F);
				return InteractionResult.SUCCESS;
			}
			case "shadow_dungeon_key_0", "shadow_dungeon_key_1" -> {
				level.destroyBlock(hit.getBlockPos(), false);
				give(player, "shadow_dungeon_key", 1);
				return InteractionResult.SUCCESS;
			}
			case "guard_crystal" -> {
				if (level instanceof ServerLevel serverLevel) {
					serverLevel.playSound(
						null,
						hit.getBlockPos(),
						SoundEvents.BEACON_ACTIVATE,
						SoundSource.NEUTRAL,
						3.0F,
						1.2F
					);
					schedule(serverLevel, 3, ignored -> serverLevel.sendParticles(
						ParticleTypes.END_ROD,
						hit.getBlockPos().getX() + 0.5D,
						hit.getBlockPos().getY() + 0.5D,
						hit.getBlockPos().getZ() + 0.5D,
						64,
						1.0D,
						1.0D,
						1.0D,
						0.3D
					));
					schedule(serverLevel, 31, ignored -> {
						for (Player nearby : serverLevel.getEntitiesOfClass(
							Player.class,
							net.minecraft.world.phys.AABB.ofSize(
								net.minecraft.world.phys.Vec3.atCenterOf(hit.getBlockPos()),
								16.0D,
								16.0D,
								16.0D
							),
							candidate -> true
						)) {
							nearby.displayClientMessage(
								net.minecraft.network.chat.Component.literal(
									"“守护者”已被破坏，范围内地形将可被改造"
								),
								false
							);
						}
						serverLevel.explode(
							null,
							hit.getLocation().x,
							hit.getLocation().y,
							hit.getLocation().z,
							3.0F,
							Level.ExplosionInteraction.TNT
						);
						serverLevel.destroyBlock(hit.getBlockPos(), false);
					});
					return InteractionResult.SUCCESS;
				}
			}
			case "firefly_glass_jar", "light_firefly_glass_jar" -> {
				if (level instanceof ServerLevel serverLevel) {
					serverLevel.playSound(
						null,
						hit.getBlockPos(),
						SoundEvents.BOTTLE_FILL,
						SoundSource.BLOCKS,
						1.0F,
						1.0F
					);
					schedule(serverLevel, 8, ignored -> {
						BlockState current = level.getBlockState(hit.getBlockPos());
						Block replacement = current.is(PasterDreamBlocks.FIREFLY_GLASS_JAR)
							? PasterDreamBlocks.LIGHT_FIREFLY_GLASS_JAR
							: PasterDreamBlocks.FIREFLY_GLASS_JAR;
						level.setBlock(hit.getBlockPos(), replacement.defaultBlockState(), 3);
					});
					return InteractionResult.SUCCESS;
				}
			}
			case "life_crystal" -> {
				if (player instanceof ServerPlayer serverPlayer
					&& level instanceof ServerLevel serverLevel) {
					return useLifeCrystal(serverPlayer, serverLevel, hit.getBlockPos());
				}
			}
			case "golden_fox_sculpture" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return useGoldenFox(serverPlayer, hit.getBlockPos());
				}
			}
			case "shadow_bed" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return useShadowBed(serverPlayer);
				}
			}
			case "true_shadow_bed" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					if (PasterDreamAdvancements.completed(serverPlayer, "achievement_shadow_npc_5")
						&& !PasterDreamAdvancements.completed(serverPlayer, "achievement_shadow_d_0")
						&& level.dimension().equals(LAMP_SHADOW_WORLD)) {
						PasterDreamMenuOpener.openBlock(
							serverPlayer,
							hit.getBlockPos(),
							PasterDreamMenuSpecs.SHADOW_SELECT_END
						);
						return InteractionResult.SUCCESS;
					}
					return useShadowBed(serverPlayer);
				}
			}
			case "wind_knight_spawnblock_0" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return assembleWindKnight(serverPlayer, hit.getBlockPos(), "windrunner_crystal", 1);
				}
			}
			case "wind_knight_spawnblock_1" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return assembleWindKnight(serverPlayer, hit.getBlockPos(), "wind_iron_ingot", 2);
				}
			}
			case "wind_knight_spawnblock_2" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return assembleWindKnight(serverPlayer, hit.getBlockPos(), "wind_iron_ingot", 3);
				}
			}
			case "wind_knight_spawnblock_3" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return assembleWindKnight(serverPlayer, hit.getBlockPos(), "wind_iron_ingot", 4);
				}
			}
			case "wind_knight_spawnblock_4" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return finishWindKnight(serverPlayer, hit.getBlockPos());
				}
			}
			case "dyedream_camellia_crop", "pale_snow_lotus_crop", "luminous_violet_crop", "ringing_cloud_flower_crop", "cotton_crop" -> {
				if ((player.getMainHandItem().is(PasterDreamItems.PLIERS)
					|| player.getOffhandItem().is(PasterDreamItems.PLIERS))
					&& player instanceof ServerPlayer serverPlayer) {
					harvestCrop(serverPlayer, hit.getBlockPos(), id);
					return InteractionResult.SUCCESS;
				}
			}
			case "dyedream_sapling" -> {
				boolean main = player.getMainHandItem().is(PasterDreamItems.DREAM_FERTILIZER);
				boolean offhand = player.getOffhandItem().is(PasterDreamItems.DREAM_FERTILIZER);
				if (main || offhand) {
					player.swing(main ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, true);
					if (level instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(
							(ParticleOptions) PasterDreamParticles.LEAVES_PARTICLE,
							hit.getBlockPos().getX() + 0.5D,
							hit.getBlockPos().getY() + 0.7D,
							hit.getBlockPos().getZ() + 0.5D,
							8,
							0.4D,
							0.5D,
							0.4D,
							0.2D
						);
						growDyedreamTree(serverLevel, hit.getBlockPos());
					}
					return InteractionResult.sidedSuccess(level.isClientSide());
				}
			}
			case "broken_shadow_dungeon_portal" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return repairShadowPortal(serverPlayer, hit.getBlockPos());
				}
			}
			case "shadow_dungeon_portal" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return useShadowPortal(serverPlayer, hit.getBlockPos());
				}
			}
			case "shadow_brazier" -> {
				if (player.getMainHandItem().is(PasterDreamBlocks.SHADOW_CANDLE.asItem())
					&& level instanceof ServerLevel serverLevel) {
					var blockEntity = serverLevel.getBlockEntity(hit.getBlockPos());
					if (blockEntity instanceof PasterDreamBlockEntity machine) {
						machine.stateData().putBoolean("switch", true);
						machine.markStateChanged();
					}
					player.displayClientMessage(
						net.minecraft.network.chat.Component.literal("火盆被点燃，但带来的并不是光明..."),
						false
					);
					player.displayClientMessage(
						net.minecraft.network.chat.Component.literal("阴影从四周蔓延开来"),
						false
					);
					player.swing(InteractionHand.MAIN_HAND, true);
					level.playSound(null, hit.getBlockPos(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0F, 1.0F);
					schedule(serverLevel, 5, ignored ->
						level.playSound(null, hit.getBlockPos(), PasterDreamSounds.SHADOW0, SoundSource.NEUTRAL, 1.0F, 1.0F)
					);
					schedule(serverLevel, 20, ignored ->
						level.playSound(null, hit.getBlockPos(), PasterDreamSounds.SHADOW_MUSIC_0, SoundSource.NEUTRAL, 1.0F, 1.0F)
					);
					return InteractionResult.SUCCESS;
				}
				player.displayClientMessage(
					net.minecraft.network.chat.Component.literal("需要用阴影蜡烛点燃火盆"),
					true
				);
				return InteractionResult.SUCCESS;
			}
			case "aaroncos_hand_chest" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return useHandChest(serverPlayer, hit.getBlockPos());
				}
			}
			case "dream_spawner_0" -> {
				if (player.getMainHandItem().getItem() instanceof net.minecraft.world.item.SpawnEggItem
					&& level.getBlockEntity(hit.getBlockPos()) instanceof PasterDreamBlockEntity spawner) {
					spawner.setItem(0, player.getMainHandItem().copyWithCount(1));
					return InteractionResult.SUCCESS;
				}
			}
			case "workshop_cauldron" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return useWorkshopCauldron(serverPlayer);
				}
			}
			case "workshop_grind" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return useWorkshopGrind(serverPlayer);
				}
			}
			case "dreamforge_lotus" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return decryptGarden(serverPlayer, hit.getBlockPos());
				}
			}
			case "edelweiss" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return transformSnowFlower(serverPlayer, hit.getBlockPos());
				}
			}
			case "aaroncos_hand_spawn_block" -> {
				if (level instanceof ServerLevel serverLevel) {
					return startArenaEncounter(serverLevel, hit.getBlockPos());
				}
			}
			case "claypan_0" -> {
				if (player.getMainHandItem().is(Items.WATER_BUCKET)
					|| player.getMainHandItem().is(PasterDreamItems.WATER_GLASS_JAR)
					|| player.getMainHandItem().is(Items.POTION)) {
					ItemStack replacement = player.getMainHandItem().is(Items.WATER_BUCKET)
						? new ItemStack(Items.BUCKET)
						: player.getMainHandItem().is(PasterDreamItems.WATER_GLASS_JAR)
							? new ItemStack(PasterDreamItems.GLASS_JAR)
							: new ItemStack(Items.GLASS_BOTTLE);
					consumeItem(player, player.getMainHandItem(), 1);
					player.getInventory().placeItemBackInInventory(replacement, true);
					level.setBlock(
						hit.getBlockPos(),
						PasterDreamBlocks.CLAYPAN_1.defaultBlockState(),
						3
					);
					level.playSound(
						null,
						hit.getBlockPos(),
						SoundEvents.BUCKET_EMPTY,
						SoundSource.NEUTRAL,
						1.0F,
						1.0F
					);
					return InteractionResult.SUCCESS;
				}
			}
			case "claypan_2" -> {
				level.setBlock(
					hit.getBlockPos(),
					PasterDreamBlocks.CLAYPAN_0.defaultBlockState(),
					3
				);
				Block.popResource(level, hit.getBlockPos(), new ItemStack(PasterDreamItems.COARSE_SALT));
				level.playSound(
					null,
					hit.getBlockPos(),
					SoundEvents.SAND_BREAK,
					SoundSource.NEUTRAL,
					0.8F,
					1.0F
				);
				return InteractionResult.SUCCESS;
			}
			case "lost_sword_block" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return pullLostSword(serverPlayer, hit.getBlockPos());
				}
			}
			case "twilight_lantern" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return igniteTwilightLantern(serverPlayer, hit.getBlockPos());
				}
			}
			case "forced_tower" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return useForcedTower(serverPlayer, hit.getBlockPos());
				}
			}
			case "desert_hero_tomb" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return useDesertHeroTomb(serverPlayer, hit.getBlockPos());
				}
			}
			case "weapon_table" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return useWeaponTable(serverPlayer, hit.getBlockPos());
				}
			}
			case "shadow_dungeon_door_0" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return openShadowDungeonDoor(serverPlayer, hit.getBlockPos(), false);
				}
			}
			case "shadow_dungeon_door_2" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					return openShadowDungeonDoor(serverPlayer, hit.getBlockPos(), true);
				}
			}
			default -> {
			}
		}
		return InteractionResult.PASS;
	}

	private static InteractionResult useDoll(ServerPlayer player, BlockPos pos) {
		var blockEntity = player.serverLevel().getBlockEntity(pos);
		if (blockEntity instanceof dev.arakiel.pasterdream.content.PasterDreamBlockEntity machine) {
			if (!machine.stateData().getBoolean("switch")) {
				machine.stateData().putBoolean("switch", true);
				machine.markStateChanged();
				PasterDreamPlayerState.addSan(player, 1.0D);
				schedule(player.serverLevel(), 20, ignored -> {
					machine.stateData().putBoolean("switch", false);
					machine.markStateChanged();
				});
			}
		}
		player.serverLevel().playSound(
			null,
			pos,
			PasterDreamSounds.DOLL,
			SoundSource.BLOCKS,
			1.0F,
			1.0F
		);
		for (Player target : player.serverLevel().getEntitiesOfClass(
			Player.class,
			net.minecraft.world.phys.AABB.ofSize(
				net.minecraft.world.phys.Vec3.atCenterOf(pos),
				15.0D,
				15.0D,
				15.0D
			),
			candidate -> true
		)) {
			target.addEffect(new MobEffectInstance(
				PasterDreamContent.effectHolder(PasterDreamEffects.REST_BUFF),
				1200,
				0,
				false,
				false
			));
		}
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult useLifeCrystal(
		ServerPlayer player,
		ServerLevel level,
		BlockPos pos
	) {
		PasterDreamWorldStateComponent state = PasterDreamPlayerState.worldState(player);
		if (state.lifeCrystalConsumed()) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("你已经吸收过生命水晶了！"),
				false
			);
			return InteractionResult.SUCCESS;
		}
		state.setLifeCrystalConsumed(true);
		level.playSound(null, pos, PasterDreamSounds.LIFE_CRYSTAL, SoundSource.NEUTRAL, 0.6F, 1.0F);
		for (int delay = 2; delay <= 38; delay += 5) {
			int offset = delay;
			schedule(level, offset, ignored ->
				level.sendParticles(
					ParticleTypes.HEART,
					pos.getX() + 0.5D,
					pos.getY() + 0.5D,
					pos.getZ() + 0.5D,
					3,
					0.3D,
					0.3D,
					0.3D,
					0.01D
				)
			);
		}
		schedule(level, 40, ignored -> {
			level.removeBlock(pos, false);
			AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
			if (health != null) {
				health.addOrUpdateTransientModifier(new AttributeModifier(
					PasterDream.id("life_crystal"),
					2.0D,
					AttributeModifier.Operation.ADD_VALUE
				));
			}
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("生命水晶破碎并涌入你的体内 §a最大生命值+2"),
				false
			);
		});
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult useGoldenFox(ServerPlayer player, BlockPos pos) {
		Level level = player.level();
		if (!player.getMainHandItem().is(Items.GLOW_BERRIES)
			|| level.getDayTime() % 24000L > 450L
			|| !level.getBlockState(pos.offset(9, 0, 9)).is(PasterDreamBlocks.FLOWER_12)
			|| !level.getBlockState(pos.offset(-9, 0, -9)).is(PasterDreamBlocks.FLOWER_12)
			|| !level.getBlockState(pos.offset(-9, 0, 9)).is(PasterDreamBlocks.FLOWER_12)
			|| !level.getBlockState(pos.offset(9, 0, -9)).is(PasterDreamBlocks.FLOWER_12)) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("雕像没有反应..."),
				false
			);
			return InteractionResult.SUCCESS;
		}
		player.getMainHandItem().shrink(1);
		level.removeBlock(pos.offset(9, 0, 9), false);
		level.removeBlock(pos.offset(-9, 0, -9), false);
		level.removeBlock(pos.offset(-9, 0, 9), false);
		level.removeBlock(pos.offset(9, 0, -9), false);
		level.removeBlock(pos, false);
		if (level instanceof ServerLevel serverLevel) {
			summon(serverLevel, "golden_fox", pos);
			serverLevel.playSound(null, pos, SoundEvents.FOX_AMBIENT, SoundSource.MASTER, 1.2F, 1.0F);
			serverLevel.sendParticles(
				(ParticleOptions) PasterDreamParticles.HEALING_SPELL_PARTICLE,
				pos.getX() + 0.5D,
				pos.getY() + 0.2D,
				pos.getZ() + 0.5D,
				12,
				0.5D,
				0.4D,
				0.5D,
				0.1D
			);
		}
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult useShadowBed(ServerPlayer player) {
		Level level = player.level();
		if (level.dimension().equals(LAMP_SHADOW_WORLD)
			&& level.isDay()
			&& !level.isThundering()) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("你只能在夜晚或雷暴中入眠"),
				true
			);
			return InteractionResult.SUCCESS;
		}
		if (!PasterDreamAdvancements.completed(player, "achievement_shadow_start")) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("缺少灯影之下的进度"),
				true
			);
			return InteractionResult.SUCCESS;
		}
		PasterDreamPlayerState.addSan(player, -10.0D);
		PasterDreamAdvancements.award(player, "achievement_shadow_a_1");
		player.swing(InteractionHand.MAIN_HAND, true);
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult repairShadowPortal(ServerPlayer player, BlockPos pos) {
		if (pos.getY() <= 20) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("地牢的结构被破坏，传送门没有反应"),
				false
			);
			return InteractionResult.SUCCESS;
		}
		boolean creative = player.isCreative();
		boolean hasKnowledge = PasterDreamAdvancements.completed(player, "achievement_hide_14");
		boolean hasLight = player.getMainHandItem().is(PasterDreamBlocks.SHADOW_LIGHT_0.asItem())
			|| player.getOffhandItem().is(PasterDreamBlocks.SHADOW_LIGHT_0.asItem());
		boolean hasMetal = player.getMainHandItem().is(PasterDreamItems.BLACK_METAL_INGOT)
			|| player.getOffhandItem().is(PasterDreamItems.BLACK_METAL_INGOT);
		if (!creative && !hasKnowledge) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("缺少知识 你还不理解这个核心的工作原理和修复方法"),
				true
			);
			return InteractionResult.SUCCESS;
		}
		if (!creative && !(hasLight && hasMetal)) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("双手持§e黑金属§f和§e影灯§f以修复核心"),
				true
			);
			return InteractionResult.SUCCESS;
		}
		if (!creative) {
			player.getInventory().clearOrCountMatchingItems(
				stack -> stack.is(PasterDreamItems.BLACK_METAL_INGOT),
				1,
				player.inventoryMenu.getCraftSlots()
			);
			player.getInventory().clearOrCountMatchingItems(
				stack -> stack.is(PasterDreamBlocks.SHADOW_LIGHT_0.asItem()),
				1,
				player.inventoryMenu.getCraftSlots()
			);
		}
		if (player.level() instanceof ServerLevel level) {
			level.playSound(null, pos, SoundEvents.SMITHING_TABLE_USE, SoundSource.NEUTRAL, 1.0F, 1.0F);
			level.sendParticles(ParticleTypes.END_ROD, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 24, 1.0D, 1.0D, 1.0D, 0.3D);
			schedule(level, 20, ignored -> {
				level.setBlock(pos, PasterDreamBlocks.SHADOW_DUNGEON_PORTAL.defaultBlockState(), 3);
				player.displayClientMessage(
					net.minecraft.network.chat.Component.literal("核心已修复"),
					true
				);
			});
		}
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult useShadowPortal(ServerPlayer player, BlockPos pos) {
		if (!(player.level() instanceof ServerLevel level)) {
			return InteractionResult.SUCCESS;
		}
		BlockPos structureBase = pos.below(2);
		if (!level.getBlockState(structureBase).is(PasterDreamBlocks.SHADOW_DUNGEON_BLOCK_0)
			|| !level.getBlockState(structureBase.east()).is(PasterDreamBlocks.SHADOW_DUNGEON_BLOCK_1)) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("遗迹结构不完整"),
				false
			);
			return InteractionResult.SUCCESS;
		}
		var blockEntity = level.getBlockEntity(pos);
		if (!(blockEntity instanceof PasterDreamBlockEntity machine)) {
			return InteractionResult.SUCCESS;
		}
		if (machine.stateData().getBoolean("cd")) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal(
					"地牢刷新冷却中 剩余："
						+ (1800 - machine.stateData().getInt("cooldown_time"))
						+ "秒"
				),
				true
			);
			return InteractionResult.SUCCESS;
		}
		machine.stateData().putBoolean("cd", true);
		machine.stateData().putInt("cooldown_time", 0);
		machine.markStateChanged();
		level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.NEUTRAL, 2.0F, 1.0F);
		player.displayClientMessage(net.minecraft.network.chat.Component.literal("传送倒计时：3"), true);
		schedule(level, 20, ignored ->
			player.displayClientMessage(net.minecraft.network.chat.Component.literal("传送倒计时：2"), true)
		);
		schedule(level, 40, ignored ->
			player.displayClientMessage(net.minecraft.network.chat.Component.literal("传送倒计时：1"), true)
		);
		schedule(level, 60, ignored -> {
			if (PasterDreamAdvancements.completed(player, "achievement_shadow_b_0")) {
				PasterDreamAdvancements.award(player, "achievement_shadow_c_0");
			}
			player.teleportTo(
				level,
				pos.getX() - 3.5D,
				pos.getY() - 19.0D,
				pos.getZ() + 0.5D,
				player.getYRot(),
				player.getXRot()
			);
			level.playSound(
				null,
				pos.below(19),
				SoundEvents.END_PORTAL_SPAWN,
				SoundSource.NEUTRAL,
				1.0F,
				1.0F
			);
		});
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult useHandChest(ServerPlayer player, BlockPos pos) {
		if (!(player.level() instanceof ServerLevel level)) {
			return InteractionResult.SUCCESS;
		}
		var blockEntity = level.getBlockEntity(pos);
		if (!(blockEntity instanceof PasterDreamBlockEntity machine)
			|| machine.stateData().getBoolean("switch")) {
			return InteractionResult.SUCCESS;
		}
		machine.stateData().putBoolean("switch", true);
		machine.markStateChanged();
		level.playSound(null, pos, PasterDreamSounds.SHADOW_DOOR, SoundSource.NEUTRAL, 1.0F, 1.0F);
		schedule(level, 40, ignored -> {
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.SHADOW_STONE_PARTICLE,
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				64,
				1.0D,
				1.0D,
				1.0D,
				0.2D
			);
			if (PasterDreamAdvancements.completed(player, "achievement_talent_light")) {
				Block.popResource(level, pos, new ItemStack(PasterDreamItems.WHITE_FLOWER_BODY));
				Block.popResource(level, pos, new ItemStack(PasterDreamItems.WHITE_CRYSTAL));
			}
			if (PasterDreamAdvancements.completed(player, "achievement_talent_shadow")) {
				Block.popResource(level, pos, new ItemStack(PasterDreamItems.DEGENERATE_BODIES));
				Block.popResource(level, pos, new ItemStack(PasterDreamItems.SHADOW_HILT));
			}
			Block.popResource(level, pos, new ItemStack(PasterDreamItems.PURE_HORROR));
		});
		schedule(level, 41, ignored -> level.removeBlock(pos, false));
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult useWorkshopCauldron(ServerPlayer player) {
		ItemStack stack = player.getMainHandItem();
		if (!isEmbryo(stack) || PasterDreamItemData.read(stack).getDouble("process") != 2.0D) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("用于冷却待淬火的原胚"),
				true
			);
			return InteractionResult.SUCCESS;
		}
		PasterDreamItemData.update(stack, tag -> tag.putDouble("process", 3.0D));
		player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
		if (player.level() instanceof ServerLevel level) {
			level.playSound(
				null,
				player.blockPosition(),
				SoundEvents.LAVA_EXTINGUISH,
				SoundSource.NEUTRAL,
				1.0F,
				1.0F
			);
			level.sendParticles(
				ParticleTypes.DRIPPING_WATER,
				player.getX(),
				player.getY() + 1.0D,
				player.getZ(),
				7,
				0.3D,
				0.5D,
				0.3D,
				0.05D
			);
			Block.popResource(level, player.blockPosition().above(), stack);
		}
		player.getInventory().setChanged();
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult useWorkshopGrind(ServerPlayer player) {
		ItemStack stack = player.getMainHandItem();
		if (!isEmbryo(stack) || PasterDreamItemData.read(stack).getDouble("process") != 3.0D) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("用于打磨原胚 过程总计需要消耗10经验等级"),
				true
			);
			return InteractionResult.SUCCESS;
		}
		int progress = PasterDreamItemData.read(stack).getInt("grind_level");
		if (progress < 9) {
			if (player.experienceLevel <= 0) {
				player.displayClientMessage(
					net.minecraft.network.chat.Component.literal("经验值不足"),
					true
				);
				return InteractionResult.SUCCESS;
			}
			player.giveExperienceLevels(-1);
			int next = progress + 1;
			PasterDreamItemData.update(stack, tag -> tag.putInt("grind_level", next));
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("打磨进度 " + next * 10 + "%"),
				true
			);
			if (player.level() instanceof ServerLevel level) {
				level.playSound(
					null,
					player.blockPosition(),
					SoundEvents.GRINDSTONE_USE,
					SoundSource.NEUTRAL,
					1.0F,
					1.0F
				);
			}
		} else {
				String outputId = switch (idOf(stack)) {
					case "dream_wand_embryo" -> "dream_wand";
					case "shadow_erosion_sword_embryo" -> "shadow_erosion_sword";
					case "shadow_erosion_pickaxe_embryo" -> "shadow_erosion_pickaxe";
					case "shadow_erosion_axe_embryo" -> "shadow_erosion_axe";
					case "shadow_erosion_shovel_embryo" -> "shadow_erosion_shovel";
					case "shadow_erosion_hoe_embryo" -> "shadow_erosion_hoe";
					case "terra_sword_embryo" -> "terra_sword";
					case "white_sword_embryo" -> "white_sword";
					case "shadow_sword_embryo" -> "shadow_sword";
					case "star_wish_rod_embryo" -> "star_wish_rod";
					case "iceshadow_hammer_embryo" -> "iceshadow_hammer";
					default -> null;
				};
				if (outputId == null) {
					player.displayClientMessage(
						net.minecraft.network.chat.Component.literal("这个原胚没有可用的成品配方"),
						true
					);
					return InteractionResult.SUCCESS;
				}
				var carried = PasterDreamItemData.read(stack).copy();
				ItemStack result = new ItemStack(PasterDreamItems.get(outputId));
				PasterDreamItemData.update(result, tag -> tag.merge(carried));
				player.setItemInHand(InteractionHand.MAIN_HAND, result);
				player.displayClientMessage(
					net.minecraft.network.chat.Component.literal("打磨进度 100%"),
					true
				);
				player.level().playSound(
					null,
					player.blockPosition(),
					SoundEvents.PLAYER_LEVELUP,
					SoundSource.NEUTRAL,
					1.0F,
					1.0F
				);
				if (player.level() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(
						(ParticleOptions) PasterDreamParticles.DUST_0_PARTICLE,
						player.getX(),
						player.getY() + 1.0D,
						player.getZ(),
						20,
						0.3D,
						0.5D,
						0.3D,
						0.05D
					);
					serverLevel.sendParticles(
						(ParticleOptions) PasterDreamParticles.MELTDREAM_CRYSTAL_PARTICLE,
						player.getX(),
						player.getY() + 1.0D,
						player.getZ(),
						5,
						0.3D,
						0.5D,
						0.3D,
						0.05D
					);
				}
		}
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult decryptGarden(ServerPlayer player, BlockPos pos) {
		Level level = player.level();
		if (!level.dimension().equals(DYEDREAM_WORLD)
			|| !level.getBlockState(pos.below(3)).is(PasterDreamBlocks.DYEDREAM_DESK)
			|| !level.getBlockState(pos.offset(-2, 0, -2)).is(PasterDreamBlocks.FLOWER_13)
			|| !level.getBlockState(pos.offset(2, 0, -2)).is(PasterDreamBlocks.CROP_3A)
			|| !level.getBlockState(pos.offset(2, 0, 2)).is(PasterDreamBlocks.FLOWER_8)
			|| !level.getBlockState(pos.offset(-2, 0, 2)).is(PasterDreamBlocks.CROP_2A)
			|| !level.getBlockState(pos.offset(2, 0, 0)).is(PasterDreamBlocks.GRASS_3)
			|| !level.getBlockState(pos.offset(-2, 0, 0)).is(PasterDreamBlocks.GRASS_3)
			|| !level.getBlockState(pos.offset(0, 0, 2)).is(PasterDreamBlocks.GRASS_3)
			|| !level.getBlockState(pos.offset(0, 0, -2)).is(PasterDreamBlocks.GRASS_3)) {
			return InteractionResult.PASS;
		}
		for (BlockPos offset : List.of(
			pos.offset(2, 0, 2),
			pos.offset(2, 0, -2),
			pos.offset(-2, 0, -2),
			pos.offset(-2, 0, 2),
			pos.offset(2, 0, 0),
			pos.offset(-2, 0, 0),
			pos.offset(0, 0, 2),
			pos.offset(0, 0, -2)
		)) {
			level.removeBlock(offset, false);
		}
		level.setBlock(pos, PasterDreamBlocks.FLOWER_12.defaultBlockState(), 3);
		level.removeBlock(pos.below(3), false);
		if (level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
				(ParticleOptions) PasterDreamParticles.DUST_0_PARTICLE,
				pos.getX() + 0.5D,
				pos.getY() + 1.0D,
				pos.getZ() + 0.5D,
				128,
				2.0D,
				2.0D,
				2.0D,
				0.1D
			);
			serverLevel.playSound(null, pos, PasterDreamSounds.DREAM0, SoundSource.BLOCKS, 1.0F, 1.0F);
		}
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult transformSnowFlower(ServerPlayer player, BlockPos pos) {
		Level level = player.level();
		if (!level.getBlockState(pos.below(3)).is(PasterDreamBlocks.DYEDREAM_DESK)
			|| !level.getBlockState(pos.offset(0, 0, 5)).is(PasterDreamBlocks.CROP_0A)
			|| !level.getBlockState(pos.offset(0, 0, -5)).is(PasterDreamBlocks.DYEDREAM_SAPLING)
			|| !level.getBlockState(pos.offset(5, 0, 0)).is(PasterDreamBlocks.FLOWER_14)
			|| !level.getBlockState(pos.offset(-5, 0, 0)).is(PasterDreamBlocks.FLOWER_9)
			|| player.level().getEntitiesOfClass(
				net.minecraft.world.entity.animal.SnowGolem.class,
				net.minecraft.world.phys.AABB.ofSize(
					net.minecraft.world.phys.Vec3.atCenterOf(pos),
					9.0D,
					9.0D,
					9.0D
				),
				candidate -> true
			).isEmpty()
			|| player.level().getEntitiesOfClass(
				net.minecraft.world.entity.animal.allay.Allay.class,
				net.minecraft.world.phys.AABB.ofSize(
					net.minecraft.world.phys.Vec3.atCenterOf(pos),
					9.0D,
					9.0D,
					9.0D
				),
				candidate -> true
			).isEmpty()) {
			return InteractionResult.PASS;
		}
		level.setBlock(pos, PasterDreamBlocks.FLOWER_17.defaultBlockState(), 3);
		level.removeBlock(pos.below(3), false);
		level.removeBlock(pos.offset(0, 0, 5), false);
		level.removeBlock(pos.offset(0, 0, -5), false);
		level.removeBlock(pos.offset(5, 0, 0), false);
		level.removeBlock(pos.offset(-5, 0, 0), false);
		if (level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 64, 2.0D, 1.0D, 2.0D, 0.1D);
		}
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult startArenaEncounter(ServerLevel level, BlockPos pos) {
		var blockEntity = level.getBlockEntity(pos);
		if (!(blockEntity instanceof PasterDreamBlockEntity machine)
			|| machine.stateData().getBoolean("switch")) {
			return InteractionResult.SUCCESS;
		}
		machine.stateData().putBoolean("switch", true);
		machine.markStateChanged();
		summon(level, "aaroncos_lefthand_0", pos.offset(12, 0, 0));
		summon(level, "aaroncos_righthand_0", pos.offset(-12, 0, 0));
		for (ServerPlayer player : level.players()) {
			player.setGameMode(net.minecraft.world.level.GameType.ADVENTURE);
		}
		level.playSound(
			null,
			pos,
			PasterDreamSounds.AARONCOS_MUSIC,
			SoundSource.WEATHER,
			1.0F,
			1.0F
		);
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult pullLostSword(ServerPlayer player, BlockPos pos) {
		MobEffectInstance strength = player.getEffect(MobEffects.DAMAGE_BOOST);
		if (strength == null || strength.getAmplifier() < 1) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("你的力气不足以拔出这柄剑"),
				true
			);
			return InteractionResult.SUCCESS;
		}
		var blockEntity = player.level().getBlockEntity(pos);
		if (!(blockEntity instanceof PasterDreamBlockEntity machine)) {
			return InteractionResult.SUCCESS;
		}
		int progress = machine.stateData().getInt("pull_count") + 1;
		machine.stateData().putInt("pull_count", progress);
		machine.markStateChanged();
		if (player.level() instanceof ServerLevel level) {
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.DUST_0_PARTICLE,
				pos.getX() + 0.5D,
				pos.getY() + 0.8D,
				pos.getZ() + 0.5D,
				16,
				0.3D,
				0.2D,
				0.3D,
				0.5D
			);
			level.playSound(
				null,
				pos,
				SoundEvents.DEEPSLATE_BREAK,
				SoundSource.NEUTRAL,
				1.0F,
				1.0F
			);
		}
		if (progress >= 5) {
					player.level().removeBlock(pos, false);
			Block.popResource(
				player.level(),
				pos,
				new ItemStack(PasterDreamItems.SWORD_EMBRYO)
			);
			player.level().playSound(
				null,
				pos,
				PasterDreamSounds.SKILL0,
				SoundSource.NEUTRAL,
				1.0F,
				1.0F
			);
		}
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult igniteTwilightLantern(
		ServerPlayer player,
		BlockPos pos
	) {
		if (!PasterDreamAdvancements.completed(player, "achievement_hide_10")) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("你尚未知晓如何激活影灯"),
				true
			);
			return InteractionResult.SUCCESS;
		}
		if (!player.getMainHandItem().is(PasterDreamItems.MELTDREAM_CRYSTAL)) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("需要用融梦水晶碎片点燃影灯"),
				true
			);
			return InteractionResult.SUCCESS;
		}
		var blockEntity = player.level().getBlockEntity(pos);
		if (!(blockEntity instanceof PasterDreamBlockEntity machine)
			|| machine.stateData().getBoolean("switch")) {
			return InteractionResult.SUCCESS;
		}
		machine.stateData().putBoolean("switch", true);
		machine.markStateChanged();
		consumeItem(player, player.getMainHandItem(), 1);
		player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 140, 0, false, false));
		if (player.level() instanceof ServerLevel level) {
			level.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundSource.NEUTRAL, 1.0F, 1.0F);
			level.sendParticles(ParticleTypes.END_ROD, pos.getX() + 0.5D, pos.getY() - 0.5D, pos.getZ() + 0.5D, 32, 1.0D, 1.0D, 1.0D, 0.05D);
			schedule(level, 18, ignored ->
				level.playSound(null, pos, PasterDreamSounds.SHADOW0, SoundSource.NEUTRAL, 1.0F, 1.0F)
			);
			schedule(level, 55, ignored ->
				level.playSound(null, pos, PasterDreamSounds.SHADOW_MUSIC_0, SoundSource.NEUTRAL, 1.0F, 1.0F)
			);
			schedule(level, 2600, ignored -> {
				if (!level.getEntitiesOfClass(
					Player.class,
					net.minecraft.world.phys.AABB.ofSize(
						net.minecraft.world.phys.Vec3.atCenterOf(pos),
						46.0D,
						46.0D,
						46.0D
					),
					candidate -> true
				).isEmpty()) {
					machine.stateData().putBoolean("key", true);
					machine.stateData().putBoolean("switch", false);
					machine.markStateChanged();
					for (ServerPlayer nearby : level.getPlayers(
						candidate -> candidate.distanceToSqr(net.minecraft.world.phys.Vec3.atCenterOf(pos)) <= 27.0D * 27.0D
					)) {
						PasterDreamAdvancements.award(nearby, "achievement_hide_9");
						nearby.displayClientMessage(
							net.minecraft.network.chat.Component.literal(
								"§5已可以与暮影之笼发生共鸣"
							),
							true
						);
					}
				}
			});
		}
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult useForcedTower(ServerPlayer player, BlockPos pos) {
		if (!(player.level() instanceof ServerLevel level)
			|| !(level.getBlockEntity(pos) instanceof PasterDreamBlockEntity machine)) {
			return InteractionResult.PASS;
		}
		ItemStack held = player.getMainHandItem();
		if (held.is(PasterDreamItems.DREAM_WAND)) {
			var data = PasterDreamItemData.read(held);
			if (!data.getBoolean("switch")) {
				PasterDreamItemData.update(held, tag -> {
					tag.putDouble("coord_x", pos.getX());
					tag.putDouble("coord_y", pos.getY());
					tag.putDouble("coord_z", pos.getZ());
					tag.putBoolean("switch", true);
				});
				player.displayClientMessage(
					net.minecraft.network.chat.Component.literal("已存储记录点"),
					true
				);
				return InteractionResult.SUCCESS;
			}
			BlockPos linked = new BlockPos(
				(int) data.getDouble("coord_x"),
				(int) data.getDouble("coord_y"),
				(int) data.getDouble("coord_z")
			);
			if (!level.getBlockState(linked).is(PasterDreamBlocks.FORCED_TOWER)
				|| !(level.getBlockEntity(linked) instanceof PasterDreamBlockEntity other)) {
				player.displayClientMessage(
					net.minecraft.network.chat.Component.literal("对应坐标没有找到强征传送塔，建立链接失败"),
					true
				);
				return InteractionResult.SUCCESS;
			}
			machine.stateData().putBoolean("switch", true);
			machine.stateData().putInt("link_x", pos.getX());
			machine.stateData().putInt("link_y", pos.getY());
			machine.stateData().putInt("link_z", pos.getZ());
			machine.markStateChanged();
			other.stateData().putBoolean("switch", true);
			other.stateData().putInt("link_x", linked.getX());
			other.stateData().putInt("link_y", linked.getY());
			other.stateData().putInt("link_z", linked.getZ());
			other.markStateChanged();
			PasterDreamItemData.update(held, tag -> tag.putBoolean("switch", false));
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("强征传送塔链接建立成功"),
				true
			);
			return InteractionResult.SUCCESS;
		}
		if (held.isEmpty() && machine.stateData().getBoolean("switch")) {
			int x = machine.stateData().getInt("link_x");
			int y = machine.stateData().getInt("link_y");
			int z = machine.stateData().getInt("link_z");
			if (!level.getBlockState(new BlockPos(x, y, z)).is(PasterDreamBlocks.FORCED_TOWER)) {
				player.displayClientMessage(
					net.minecraft.network.chat.Component.literal("强征传送塔已被遗失或阻挡"),
					true
				);
				return InteractionResult.SUCCESS;
			}
			if (!PasterDreamPlayerState.consumeMeltDreamEnergy(player, 0.5D)) {
				player.displayClientMessage(
					net.minecraft.network.chat.Component.literal("融梦能量不足"),
					true
				);
				return InteractionResult.SUCCESS;
			}
			level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.NEUTRAL, 1.5F, 1.0F);
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("正在返回记录点..."),
				true
			);
			schedule(level, 35, ignored -> {
				player.teleportTo(
					level,
					x + 0.5D,
					y + 0.8D,
					z + 0.5D,
					player.getYRot(),
					player.getXRot()
				);
				level.playSound(
					null,
					new BlockPos(x, y, z),
					SoundEvents.END_PORTAL_SPAWN,
					SoundSource.BLOCKS,
					1.0F,
					1.0F
				);
			});
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	private static InteractionResult useDesertHeroTomb(ServerPlayer player, BlockPos pos) {
		if (!(player.level() instanceof ServerLevel level)
			|| !(level.getBlockEntity(pos) instanceof PasterDreamBlockEntity machine)) {
			return InteractionResult.PASS;
		}
		int stage = machine.stateData().getInt("number");
		switch (stage) {
			case 0 -> {
				machine.stateData().putInt("number", 1);
				machine.markStateChanged();
				schedule(level, 20, ignored ->
					player.displayClientMessage(net.minecraft.network.chat.Component.literal("良久，竟有来者"), false)
				);
				schedule(level, 40, ignored ->
					player.displayClientMessage(net.minecraft.network.chat.Component.literal("无苦心，有孚，吾有一事相求"), false)
				);
				schedule(level, 60, ignored ->
					player.displayClientMessage(net.minecraft.network.chat.Component.literal("以剑授汝，可否助我逐外寇也"), false)
				);
				schedule(level, 90, ignored -> {
					player.getInventory().placeItemBackInInventory(
						new ItemStack(PasterDreamItems.DESERT_SWORD),
						true
					);
					player.displayClientMessage(
						net.minecraft.network.chat.Component.literal("[清除附近的劫掠者与尸壳]"),
						false
					);
				});
			}
			case 1 -> {
				var area = net.minecraft.world.phys.AABB.ofSize(
					net.minecraft.world.phys.Vec3.atCenterOf(pos.above(16)),
					48.0D,
					48.0D,
					48.0D
				);
				if (level.getEntitiesOfClass(
					net.minecraft.world.entity.monster.Pillager.class,
					area,
					candidate -> true
				).isEmpty()
					&& level.getEntitiesOfClass(
						net.minecraft.world.entity.monster.Husk.class,
						area,
						candidate -> true
					).isEmpty()) {
					machine.stateData().putInt("number", 2);
					machine.markStateChanged();
					player.displayClientMessage(
						net.minecraft.network.chat.Component.literal("汝又能终吾三请，吾以真剑付汝"),
						false
					);
					player.displayClientMessage(
						net.minecraft.network.chat.Component.literal(
							"1.为我带来10块年糕 2.我想要一匹马在旁边陪着 3.获得英雄的村庄"
						),
						false
					);
				} else {
					player.displayClientMessage(
						net.minecraft.network.chat.Component.literal("尚未清除附近的威胁，他不予回应"),
						true
					);
				}
			}
			case 2 -> {
				if (!machine.stateData().getBoolean("task1")
					&& player.getMainHandItem().is(PasterDreamItems.RICE_CAKE)
					&& player.getMainHandItem().getCount() >= 10) {
					consumeItem(player, player.getMainHandItem(), 10);
					machine.stateData().putBoolean("task1", true);
					machine.markStateChanged();
					player.displayClientMessage(net.minecraft.network.chat.Component.literal("任务1完成"), true);
				}
				if (!machine.stateData().getBoolean("task2")
					&& !level.getEntitiesOfClass(
						net.minecraft.world.entity.animal.horse.Horse.class,
						net.minecraft.world.phys.AABB.ofSize(
							net.minecraft.world.phys.Vec3.atCenterOf(pos),
							9.0D,
							9.0D,
							9.0D
						),
						candidate -> true
					).isEmpty()) {
					machine.stateData().putBoolean("task2", true);
					machine.markStateChanged();
					player.displayClientMessage(net.minecraft.network.chat.Component.literal("任务2完成"), true);
				}
				if (!machine.stateData().getBoolean("task3")
					&& player.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) {
					machine.stateData().putBoolean("task3", true);
					machine.markStateChanged();
					player.displayClientMessage(net.minecraft.network.chat.Component.literal("任务3完成"), true);
				}
				if (machine.stateData().getBoolean("task1")
					&& machine.stateData().getBoolean("task2")
					&& machine.stateData().getBoolean("task3")) {
					machine.stateData().putInt("number", 3);
					machine.markStateChanged();
					player.displayClientMessage(
						net.minecraft.network.chat.Component.literal("吾心愿已成，请携朔漠大剑见我"),
						false
					);
				}
			}
			case 3 -> {
				if (player.getMainHandItem().is(PasterDreamItems.DESERT_SWORD)) {
					player.setItemInHand(
						InteractionHand.MAIN_HAND,
						new ItemStack(PasterDreamItems.TRUE_DESERT_SWORD)
					);
					machine.stateData().putInt("number", 4);
					machine.markStateChanged();
					player.displayClientMessage(
						net.minecraft.network.chat.Component.literal("请君收下吧：此名为『沉荆门』朔漠"),
						false
					);
				} else {
					player.displayClientMessage(
						net.minecraft.network.chat.Component.literal("吾心愿已成，请携朔漠大剑见我"),
						false
					);
				}
			}
			default -> player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("已有人完成他的全部愿望，他便不再回应"),
				true
			);
		}
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult useWeaponTable(ServerPlayer player, BlockPos pos) {
		if (!player.getMainHandItem().is(PasterDreamItems.WEAPON_WORKSHOP_BLUEPRINT)
			&& !player.isCreative()) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("缺少蓝图 请手持蓝图点击核心"),
				true
			);
			return InteractionResult.SUCCESS;
		}
		player.level().setBlock(
			pos,
			PasterDreamBlocks.WEAPON_WORKSHOP.defaultBlockState(),
			3
		);
		PasterDreamMenuOpener.openBlock(
			player,
			pos,
			PasterDreamMenuSpecs.WEAPON_WORKSHOP
		);
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult openShadowDungeonDoor(
		ServerPlayer player,
		BlockPos pos,
		boolean endDoor
	) {
		Level level = player.level();
		level.playSound(
			null,
			pos,
			PasterDreamSounds.SHADOW_DOOR,
			SoundSource.BLOCKS,
			1.0F,
			1.0F
		);
		if (endDoor) {
			if (!PasterDreamAdvancements.completed(player, "achievement_shadow_npc_5")) {
				player.displayClientMessage(
					net.minecraft.network.chat.Component.literal("大门紧闭不开"),
					true
				);
				return InteractionResult.SUCCESS;
			}
			for (BlockPos offset : List.of(
				pos,
				pos.offset(1, 1, 0),
				pos.offset(-1, 1, 0),
				pos.offset(-1, -1, 0),
				pos.offset(1, -1, 0),
				pos.east(),
				pos.west(),
				pos.above(),
				pos.below()
			)) {
				level.destroyBlock(offset, false);
			}
			return InteractionResult.SUCCESS;
		}
		if (!player.getMainHandItem().is(PasterDreamItems.SHADOW_DUNGEON_KEY)) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal(
					"需要在本层寻找暗影地牢钥匙以打开大门"
				),
				true
			);
			return InteractionResult.SUCCESS;
		}
		consumeItem(player, player.getMainHandItem(), 1);
		for (BlockPos offset : List.of(
			pos,
			pos.offset(1, 0, 1),
			pos.offset(-1, 0, 1),
			pos.offset(-1, 0, -1),
			pos.offset(1, 0, -1),
			pos.east(),
			pos.west(),
			pos.south(),
			pos.north()
		)) {
			level.destroyBlock(offset, false);
		}
		return InteractionResult.SUCCESS;
	}

	private static void consumeItem(Player player, ItemStack stack, int amount) {
		if (!player.getAbilities().instabuild) {
			stack.shrink(amount);
		}
		player.getInventory().setChanged();
	}

	private static void give(Player player, String itemId, int count) {
		player.getInventory().placeItemBackInInventory(
			new ItemStack(PasterDreamItems.get(itemId), count),
			true
		);
	}

	private static String idOf(ItemStack stack) {
		if (stack.isEmpty()) {
			return "";
		}
		var key = BuiltInRegistries.ITEM.getKey(stack.getItem());
		return PasterDream.MOD_ID.equals(key.getNamespace()) ? key.getPath() : "";
	}

	private static boolean isEmbryo(ItemStack stack) {
		return stack.is(net.minecraft.tags.TagKey.create(
			Registries.ITEM,
			PasterDream.id("embryo_items")
		));
	}

	private static InteractionResult assembleWindKnight(
		ServerPlayer player,
		BlockPos pos,
		String requiredItem,
		int nextStage
	) {
		if (!player.getMainHandItem().is(PasterDreamItems.get(requiredItem))) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal(
					requiredItem.equals("windrunner_crystal")
						? "需要嵌入 [风行者水晶]"
						: "需要 [凝风铁锭] 组装"
				),
				true
			);
			return InteractionResult.SUCCESS;
		}
		player.getMainHandItem().shrink(1);
		Block next = switch (nextStage) {
			case 1 -> PasterDreamBlocks.WIND_KNIGHT_SPAWNBLOCK_1;
			case 2 -> PasterDreamBlocks.WIND_KNIGHT_SPAWNBLOCK_2;
			case 3 -> PasterDreamBlocks.WIND_KNIGHT_SPAWNBLOCK_3;
			default -> PasterDreamBlocks.WIND_KNIGHT_SPAWNBLOCK_4;
		};
		player.level().setBlock(pos, next.defaultBlockState(), 3);
		if (player.level() instanceof ServerLevel level) {
			level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.sendParticles(
				ParticleTypes.SCRAPE,
				pos.getX() + 0.5D,
				pos.getY() + 1.0D,
				pos.getZ() + 0.5D,
				12,
				0.7D,
				0.7D,
				0.7D,
				0.1D
			);
		}
		return InteractionResult.SUCCESS;
	}

	private static InteractionResult finishWindKnight(ServerPlayer player, BlockPos pos) {
		if (!player.getMainHandItem().is(PasterDreamItems.LIGHTNING_SPELL)) {
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("盛有闪电的魔药才能唤醒傀儡"),
				true
			);
			return InteractionResult.SUCCESS;
		}
		player.getMainHandItem().shrink(1);
		if (player.level() instanceof ServerLevel level) {
			level.sendParticles(ParticleTypes.ELECTRIC_SPARK, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 128, 2.0D, 1.0D, 2.0D, 0.5D);
			schedule(level, 86, ignored -> {
				if (!level.getBlockState(pos).is(PasterDreamBlocks.WIND_KNIGHT_SPAWNBLOCK_4)) {
					return;
				}
				level.setBlock(pos, PasterDreamBlocks.WIND_KNIGHT_SPAWNBLOCK_0.defaultBlockState(), 3);
				summon(level, "wind_knight", pos.above());
				summon(level, "thundercloud", pos.offset(6, 8, 6));
				summon(level, "thundercloud", pos.offset(-6, 8, 6));
				summon(level, "thundercloud", pos.offset(6, 8, -6));
				summon(level, "thundercloud", pos.offset(-6, 8, -6));
				level.playSound(null, pos, PasterDreamSounds.SHADOW_DOOR, SoundSource.MASTER, 1.0F, 1.0F);
			});
		}
		return InteractionResult.SUCCESS;
	}

	private static void harvestCrop(ServerPlayer player, BlockPos pos, String id) {
		Block regrowth = switch (id) {
			case "dyedream_camellia_crop" -> PasterDreamBlocks.CROP_0B;
			case "pale_snow_lotus_crop" -> PasterDreamBlocks.CROP_1B;
			case "luminous_violet_crop" -> PasterDreamBlocks.CROP_2B;
			case "ringing_cloud_flower_crop" -> PasterDreamBlocks.CROP_3B;
			default -> PasterDreamBlocks.CROP_4B;
		};
		ItemStack drop = switch (id) {
			case "dyedream_camellia_crop" -> new ItemStack(PasterDreamItems.DYEDREAM_COROLLA);
			case "pale_snow_lotus_crop" -> new ItemStack(PasterDreamItems.WHITE_COROLLA);
			case "luminous_violet_crop" -> new ItemStack(PasterDreamBlocks.LIGHTBALL);
			case "ringing_cloud_flower_crop" -> new ItemStack(PasterDreamBlocks.CLOUD);
			default -> new ItemStack(PasterDreamItems.COTTON);
		};
		player.level().setBlock(pos, regrowth.defaultBlockState(), 3);
		Block.popResource(player.level(), pos, drop);
		player.level().playSound(
			null,
			pos,
			PasterDreamSounds.PLIERS0,
			SoundSource.PLAYERS,
			0.5F,
			1.0F
		);
		if (player.level() instanceof ServerLevel level) {
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.DUST_0_PARTICLE,
				pos.getX() + 0.5D,
				pos.getY(),
				pos.getZ() + 0.5D,
				10,
				0.5D,
				0.5D,
				0.5D,
				0.1D
			);
		}
	}

	private static void growDyedreamTree(ServerLevel level, BlockPos pos) {
		if (level.getRandom().nextDouble() >= 0.3D
			|| !level.getBlockState(pos.above()).isAir()
			|| !level.getBlockState(pos.above(3)).isAir()
			|| !level.getBlockState(pos.above(5)).isAir()
			|| !level.getBlockState(pos.above(12)).isAir()
			|| !level.getBlockState(pos.above(15)).isAir()) {
			return;
		}
		switch (level.getRandom().nextInt(4)) {
			case 0 -> PasterDreamStructureBlockPlacer.placeNamed(
				level,
				pos.offset(-2, 0, -5),
				"dyedreamtree0",
				net.minecraft.world.level.block.Rotation.NONE
			);
			case 1 -> PasterDreamStructureBlockPlacer.placeNamed(
				level,
				pos.offset(5, 0, -2),
				"dyedreamtree0",
				net.minecraft.world.level.block.Rotation.CLOCKWISE_90
			);
			case 2 -> PasterDreamStructureBlockPlacer.placeNamed(
				level,
				pos.offset(2, 0, 5),
				"dyedreamtree0",
				net.minecraft.world.level.block.Rotation.CLOCKWISE_180
			);
			default -> PasterDreamStructureBlockPlacer.placeNamed(
				level,
				pos.offset(-5, 0, 2),
				"dyedreamtree0",
				net.minecraft.world.level.block.Rotation.COUNTERCLOCKWISE_90
			);
		}
	}

	private static void summon(ServerLevel level, String entityId, BlockPos pos) {
		Entity entity = PasterDreamEntities.get(entityId).create(level);
		if (entity != null) {
			entity.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, level.getRandom().nextFloat() * 360.0F, 0.0F);
			level.addFreshEntity(entity);
		}
	}

	private static void schedule(
		ServerLevel level,
		int delay,
		Consumer<ServerLevel> action
	) {
		SCHEDULED.add(new ScheduledBlockAction(
			level,
			level.getGameTime() + delay,
			action
		));
	}

	private record ScheduledBlockAction(
		ServerLevel level,
		long executeAt,
		Consumer<ServerLevel> action
	) {
	}
}
