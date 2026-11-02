package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import dev.arakiel.pasterdream.config.PasterDreamConfig;
import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.content.PasterDreamEffects;
import dev.arakiel.pasterdream.content.PasterDreamEntities;
import dev.arakiel.pasterdream.content.PasterDreamItems;
import dev.arakiel.pasterdream.content.PasterDreamParticles;
import dev.arakiel.pasterdream.content.PasterDreamSounds;
import dev.arakiel.pasterdream.content.PasterDreamBlocks;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class PasterDreamItemBehavior {
	private static final ResourceKey<Level> DYEDREAM_WORLD =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("dyedream_world"));
	private static final ResourceKey<Level> LAMP_SHADOW_WORLD =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("lamp_shadow_world"));
	private static final ResourceKey<Level> AARONCOS_ARENA_WORLD =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("aaroncos_arena_world"));
	private static final String[] WIND_DIRECTIONS = {
		"北风", "东北风", "东风", "东南风", "南风", "西南风", "西风", "西北风"
	};
	private static final List<ScheduledAction> SCHEDULED_ACTIONS = new ArrayList<>();

	private PasterDreamItemBehavior() {
	}

	public static void register() {
		UseItemCallback.EVENT.register(PasterDreamItemBehavior::useItem);
		UseBlockCallback.EVENT.register(PasterDreamItemBehavior::useBlock);
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			if (!SCHEDULED_ACTIONS.isEmpty()) {
				long time = server.overworld().getGameTime();
				SCHEDULED_ACTIONS.removeIf(action -> {
					if (time >= action.executeAt()) {
						action.action().accept(action.level());
						return true;
					}
					return false;
				});
			}
			for (ServerPlayer player : server.getPlayerList().getPlayers()) {
				tickPlayer(player);
			}
		});
		ServerLivingEntityEvents.AFTER_DAMAGE.register(
			(entity, source, baseDamage, damageTaken, blocked) -> {
				if (source.getEntity() instanceof ServerPlayer player && !blocked) {
					afterAttack(player, entity);
				}
			}
		);
	}

	private static InteractionResultHolder<ItemStack> useItem(
		Player player,
		Level level,
		InteractionHand hand
	) {
		ItemStack stack = player.getItemInHand(hand);
		String id = itemId(stack);
		if (id == null || player.isSpectator()) {
			return InteractionResultHolder.pass(stack);
		}
		boolean acted = true;
		switch (id) {
			case "dream_coin" -> {
				if (stack.getCount() >= 10) {
					consume(player, stack, 10);
					give(player, "dream_coin_bundle", 1);
					level.playSound(null, player.blockPosition(), PasterDreamSounds.COIN1, SoundSource.PLAYERS, 1.0F, 1.0F);
				}
			}
			case "dream_coin_bundle" -> {
				consume(player, stack, 1);
				give(player, "dream_coin", 10);
				level.playSound(null, player.blockPosition(), PasterDreamSounds.COIN1, SoundSource.PLAYERS, 1.0F, 1.0F);
			}
			case "angel_block_placer" -> {
				BlockPos below = player.blockPosition().below();
				if (level.getBlockState(below).isAir()) {
					level.setBlock(below, PasterDreamBlocks.ANGEL_BLOCK.defaultBlockState(), 3);
					consume(player, stack, 1);
				}
			}
			case "pale_boneneedle", "roots_pale_boneneedle" -> {
				if (!(player instanceof ServerPlayer serverPlayer)) {
					return InteractionResultHolder.sidedSuccess(stack, true);
				}
				if (id.equals("roots_pale_boneneedle") && player.isShiftKeyDown()) {
					BlockHitResult hit = (BlockHitResult) player.pick(5.0D, 1.0F, false);
					PasterDreamItemData.update(stack, tag -> {
						tag.putBoolean("switch", true);
						tag.putDouble("x", hit.getBlockPos().getX() + 0.5D);
						tag.putDouble("y", hit.getBlockPos().getY() + 1.0D);
						tag.putDouble("z", hit.getBlockPos().getZ() + 0.5D);
					});
					serverPlayer.getCooldowns().addCooldown(stack.getItem(), 20);
					serverPlayer.displayClientMessage(Component.literal("已记录坐标点"), true);
				} else {
					usePaleBoneneedle(serverPlayer, stack);
				}
			}
			case "time_hourglass" -> {
				if (PasterDreamConfig.common().disableTimeHourglass) {
					player.displayClientMessage(Component.literal("§4此物品已被禁用"), true);
				} else if (level instanceof ServerLevel serverLevel) {
					consume(player, stack, 1);
					serverLevel.setDayTime(serverLevel.getDayTime() + 12_000L);
					LightningBolt lightning = net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(serverLevel);
					if (lightning != null) {
						lightning.moveTo(Vec3.atBottomCenterOf(player.blockPosition().above(4)));
						lightning.setVisualOnly(true);
						serverLevel.addFreshEntity(lightning);
					}
					serverLevel.sendParticles(ParticleTypes.END_ROD, player.getX(), player.getY(), player.getZ(), 64, 1.0D, 1.0D, 1.0D, 0.2D);
					serverLevel.sendParticles((ParticleOptions) PasterDreamParticles.DUST_0_PARTICLE, player.getX(), player.getY(), player.getZ(), 64, 1.0D, 1.0D, 1.0D, 0.2D);
				}
			}
			case "wind_vane" -> {
				player.displayClientMessage(
					Component.literal(
						"角度：" + String.format(Locale.ROOT, "%.2f", player.getXRot())
							+ " 方向：" + String.format(Locale.ROOT, "%.2f", player.getYRot())
					),
					true
				);
				int direction = level.getGameRules().getInt(PasterDreamGameRules.WIND_DIRECTION);
				player.displayClientMessage(
					Component.literal("当前风向：§a" + WIND_DIRECTIONS[Math.max(0, Math.min(direction, 7))]),
					false
				);
				level.playSound(null, player.blockPosition(), PasterDreamSounds.DING_0, SoundSource.PLAYERS, 1.0F, 1.0F);
			}
			case "deep_treasure_map_0", "deep_treasure_map_1" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					openTreasure(serverPlayer, stack, id);
				}
			}
			case "turn_pale_cecilia" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					useTurnPaleCecilia(serverPlayer, stack);
				}
			}
			case "glass_jar" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					useGlassJar(serverPlayer, stack);
				}
			}
			case "pliers" -> player.level().playSound(
				null,
				player.blockPosition(),
				PasterDreamSounds.PLIERS0,
				SoundSource.PLAYERS,
				0.5F,
				1.0F
			);
			case "dream_wand", "mana_wand", "dream_wand_embryo" -> {
				if (level.getBlockState(player.blockPosition()).is(PasterDreamBlocks.DYEDREAM_DESK)) {
					PasterDreamItemData.update(stack, tag -> tag.putBoolean("switch", false));
					level.playSound(
						null,
						player.blockPosition(),
						PasterDreamSounds.DREAM1,
						SoundSource.NEUTRAL,
						1.0F,
						1.0F
					);
					player.displayClientMessage(Component.literal("法杖数据已清空"), true);
				} else {
					acted = false;
				}
			}
			case "elixir_bottle" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					useElixirBottle(serverPlayer, stack);
				}
			}
			case "aaroncos_arena_creator" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					enterArena(serverPlayer);
				}
			}
			case "moltengold_pickaxe", "moltengold_axe", "moltengold_shovel",
				"moltengold_hoe" -> {
				player.causeFoodExhaustion(4.0F);
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 400, 0));
				player.getCooldowns().addCooldown(stack.getItem(), 200);
			}
			case "true_moltengold_pickaxe" -> {
				player.causeFoodExhaustion(4.0F);
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0));
				player.getCooldowns().addCooldown(stack.getItem(), 160);
			}
			case "dyedream_sword_0", "truest_moltengold_sword" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					prepareSkillDamage(stack, serverPlayer);
					player.swing(InteractionHand.MAIN_HAND, true);
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 0, false, false));
					applySkillCooldown(serverPlayer, 80);
					serverPlayer.level().playSound(
						null,
						serverPlayer.blockPosition(),
						PasterDreamSounds.SWORD1,
						SoundSource.PLAYERS,
						0.8F,
						1.0F
					);
				}
			}
			case "true_grass_sword" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					prepareSkillDamage(stack, serverPlayer);
					player.swing(InteractionHand.MAIN_HAND, true);
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120, 1, false, false));
					applySkillCooldown(serverPlayer, 80);
					serverPlayer.level().playSound(
						null,
						serverPlayer.blockPosition(),
						PasterDreamSounds.SWORD1,
						SoundSource.PLAYERS,
						0.8F,
						1.0F
					);
				}
			}
			case "true_tide_sword" -> {
				if (player instanceof ServerPlayer serverPlayer && player.isInWaterOrBubble()) {
					if (!PasterDreamItemData.read(stack).getBoolean("skill")) {
						prepareSkillDamage(stack, serverPlayer);
					}
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 18, 3, false, false));
					player.setDeltaMovement(player.getLookAngle().scale(2.0D));
					player.hurtMarked = true;
					player.swing(InteractionHand.MAIN_HAND, true);
					applySkillCooldown(serverPlayer, 50);
				}
			}
			case "true_desert_sword" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					prepareSkillDamage(stack, serverPlayer);
					PasterDreamItemData.update(stack, tag -> {
						tag.putDouble(
							"missing_health",
							(player.getMaxHealth() - player.getHealth()) / player.getMaxHealth()
						);
					});
					player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 60, 0, false, false));
					player.swing(InteractionHand.MAIN_HAND, true);
					applySkillCooldown(serverPlayer, 80);
				}
			}
			case "terra_sword" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					useTerraSword(serverPlayer);
				}
			}
			case "white_sword" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					useWhiteSword(serverPlayer);
				}
			}
			case "star_wish_rod" -> {
				PasterDreamItemData.update(stack, tag ->
					tag.putBoolean("cast", !tag.getBoolean("cast"))
				);
				player.swing(InteractionHand.MAIN_HAND, true);
			}
			case "dream_harp_of_wanderer" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					useDreamHarp(serverPlayer, stack);
				}
			}
			case "shadow_hand_lantern" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					useShadowHandLantern(serverPlayer, stack);
				}
			}
			case "calle_card_0" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					drawCalleCard(serverPlayer, stack);
				}
			}
			case "calle_card_1", "calle_card_2", "calle_card_3", "calle_card_4",
				"calle_card_5", "calle_card_6", "calle_card_7", "calle_card_8",
				"calle_card_9" -> {
				if (player instanceof ServerPlayer serverPlayer) {
					useCalleCard(serverPlayer, stack, id);
				}
			}
			case "memento_01", "memento_02", "memento_03", "memento_04",
				"memento_05", "memento_06", "memento_07", "memento_08",
				"memento_09", "memento_10" -> useMemento(player, stack, id);
			default -> {
				acted = false;
			}
		}
		return acted
			? InteractionResultHolder.sidedSuccess(stack, level.isClientSide())
			: InteractionResultHolder.pass(stack);
	}

	private static InteractionResult useBlock(
		Player player,
		Level level,
		InteractionHand hand,
		BlockHitResult hit
	) {
		ItemStack stack = player.getItemInHand(hand);
		String id = itemId(stack);
		if (id == null || player.isSpectator()) {
			return InteractionResult.PASS;
		}
		if (id.equals("roots_pale_boneneedle") && player.isShiftKeyDown()) {
			PasterDreamItemData.update(stack, tag -> {
				tag.putBoolean("switch", true);
				tag.putDouble("x", hit.getBlockPos().getX() + 0.5D);
				tag.putDouble("y", hit.getBlockPos().getY() + 1.0D);
				tag.putDouble("z", hit.getBlockPos().getZ() + 0.5D);
			});
			if (player instanceof ServerPlayer serverPlayer) {
				serverPlayer.getCooldowns().addCooldown(stack.getItem(), 20);
				serverPlayer.displayClientMessage(Component.literal("已记录坐标点"), true);
			}
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		if (id.equals("time_hourglass")) {
			BlockEntity blockEntity = level.getBlockEntity(hit.getBlockPos());
			if (blockEntity != null && level instanceof ServerLevel serverLevel) {
				if (blockEntity instanceof dev.arakiel.pasterdream.content.PasterDreamBlockEntity machine
					&& machine.stateData().getDouble("time") >= 0.0D) {
					machine.stateData().putDouble(
						"time",
						machine.stateData().getDouble("time") + 1_000_000.0D
					);
					machine.markStateChanged();
				}
				serverLevel.sendParticles(ParticleTypes.END_ROD, hit.getLocation().x, hit.getLocation().y, hit.getLocation().z, 16, 0.5D, 0.5D, 0.5D, 0.05D);
				return InteractionResult.sidedSuccess(level.isClientSide());
			}
		}
		if (id.equals("pliers")
			&& level.getBlockState(hit.getBlockPos()).is(
				net.minecraft.tags.TagKey.create(
					Registries.BLOCK,
					PasterDream.id("plier_plants")
				)
			)) {
			if (!player.getAbilities().instabuild) {
				stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
			}
			if (level instanceof ServerLevel serverLevel) {
				ItemEntity drop = new ItemEntity(
					serverLevel,
					hit.getBlockPos().getX() + 0.5D,
					hit.getBlockPos().getY() + 0.5D,
					hit.getBlockPos().getZ() + 0.5D,
					new ItemStack(level.getBlockState(hit.getBlockPos()).getBlock())
				);
				drop.setPickUpDelay(10);
				serverLevel.addFreshEntity(drop);
			}
			level.destroyBlock(hit.getBlockPos(), false);
			level.playSound(null, hit.getBlockPos(), PasterDreamSounds.PLIERS0, SoundSource.PLAYERS, 0.5F, 1.0F);
			return InteractionResult.SUCCESS;
		}
		if ((id.equals("dream_wand") || id.equals("mana_wand") || id.equals("dream_wand_embryo"))
			&& level.getBlockState(hit.getBlockPos()).is(PasterDreamBlocks.DYEDREAM_DESK)
			&& level instanceof ServerLevel serverLevel) {
			PasterDreamItemData.update(stack, tag -> tag.putBoolean("switch", false));
			serverLevel.playSound(
				null,
				hit.getBlockPos(),
				PasterDreamSounds.DREAM1,
				SoundSource.NEUTRAL,
				1.0F,
				1.0F
			);
			player.displayClientMessage(Component.literal("法杖数据已清空"), true);
			return InteractionResult.SUCCESS;
		}
		if (id.equals("elixir_bottle")) {
			if (level.getFluidState(hit.getBlockPos()).createLegacyBlock().is(PasterDreamBlocks.MELTDREAM_LIQUID)) {
				consume(player, stack, 1);
				level.setBlock(hit.getBlockPos(), Blocks.AIR.defaultBlockState(), 3);
				give(player, "meltdream_elixir_bottle", 1);
				player.swing(hand, true);
				level.playSound(null, hit.getBlockPos(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
				return InteractionResult.sidedSuccess(level.isClientSide());
			}
		}
		if (id.equals("iceshadow_hammer") && player instanceof ServerPlayer serverPlayer) {
			useIceshadowHammer(serverPlayer, stack);
			return InteractionResult.SUCCESS;
		}
		if (id.equals("cradle_in_ones_arms") && player instanceof ServerPlayer serverPlayer) {
			useCradle(serverPlayer);
			return InteractionResult.SUCCESS;
		}
		if ((id.equals("shadow_blast_furnace_blueprint")
			|| id.equals("weapon_workshop_blueprint"))
			&& level.getBlockState(hit.getBlockPos()).isAir()) {
			level.playSound(
				null,
				hit.getBlockPos(),
				PasterDreamSounds.NOTES,
				SoundSource.NEUTRAL,
				1.0F,
				1.0F
			);
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		if (id.equals("dream_fertilizer")) {
			if (level instanceof ServerLevel serverLevel) {
				BlockPos position = hit.getBlockPos();
				ItemStack boneMeal = new ItemStack(Items.BONE_MEAL);
				if (net.minecraft.world.item.BoneMealItem.growCrop(boneMeal, level, position)
					|| net.minecraft.world.item.BoneMealItem.growWaterPlant(boneMeal, level, position, null)) {
					level.levelEvent(2005, position, 0);
				}
				serverLevel.sendParticles(
					(ParticleOptions) PasterDreamParticles.DREAMFERTILITER_PARTICLE,
					position.getX() + 0.5D,
					position.getY() + 0.7D,
					position.getZ() + 0.5D,
					16,
					0.6D,
					0.5D,
					0.6D,
					0.25D
				);
			}
			player.swing(InteractionHand.MAIN_HAND, true);
			consume(player, stack, 1);
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		if (id.equals("meltdream_crystal")) {
			if (level.getBlockState(hit.getBlockPos().above()).isAir()
				&& level instanceof ServerLevel serverLevel) {
				consume(player, stack, 1);
				summon(serverLevel, "meltdream_crystal_entity", hit.getBlockPos().above());
				level.playSound(null, hit.getBlockPos(), SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.NEUTRAL, 0.8F, 1.0F);
				return InteractionResult.SUCCESS;
			}
		}
		if (id.startsWith("loot_table_creator_") && player.isShiftKeyDown()) {
			int index = Integer.parseInt(id.substring(id.length() - 1));
			String tablePath = index == 1
				? "loots_relic_1"
				: "chests/loots_relic_" + index;
			BlockEntity blockEntity = level.getBlockEntity(hit.getBlockPos());
			if (blockEntity instanceof RandomizableContainerBlockEntity container) {
				container.setLootTable(
					ResourceKey.create(Registries.LOOT_TABLE, PasterDream.id(tablePath)),
					level.getRandom().nextLong()
				);
				container.setChanged();
				player.displayClientMessage(Component.literal("战利品已生成"), true);
				return InteractionResult.SUCCESS;
			}
		}
		if (id.equals("paster_block_reset_tool")) {
			BlockEntity blockEntity = level.getBlockEntity(hit.getBlockPos());
			if (blockEntity != null && level instanceof ServerLevel serverLevel) {
				if (blockEntity instanceof dev.arakiel.pasterdream.content.PasterDreamBlockEntity machine) {
					machine.stateData().putBoolean("switch", false);
					machine.markStateChanged();
				}
				serverLevel.playSound(null, hit.getBlockPos(), PasterDreamSounds.DING_0, SoundSource.NEUTRAL, 1.0F, 1.0F);
				player.displayClientMessage(Component.literal("§e方块数据已重置"), false);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

	private static void tickPlayer(ServerPlayer player) {
		for (ItemStack stack : player.getInventory().items) {
			tickInventoryStack(player, stack);
		}
		tickInventoryStack(player, player.getOffhandItem());
	}

	private static void tickInventoryStack(ServerPlayer player, ItemStack stack) {
		String id = itemId(stack);
		if (id == null) {
			return;
		}
		switch (id) {
			case "meltdream_pickaxe", "meltdream_axe", "meltdream_shovel", "meltdream_hoe" -> {
				if (player.tickCount % 10 == 0
					&& stack.isDamaged()
					&& PasterDreamPlayerState.consumeMeltDreamEnergy(player, 0.01D)) {
					stack.setDamageValue(Math.max(0, stack.getDamageValue() - 1));
				}
			}
			case "grass_sword" -> {
				if (player.tickCount % 20 == 0 && player.isOnFire()) {
					player.clearFire();
				}
			}
			case "tide_sword", "true_tide_sword" -> {
				if (player.isInWater() && player.tickCount % 20 == 0) {
					player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 40, 0, false, false));
				}
			}
			case "desert_sword", "true_desert_sword" -> {
				if (player.tickCount % 20 == 0 && player.isOnFire()) {
					player.clearFire();
				}
			}
			case "dream_note_8", "dream_note_9" -> {
				if (player.tickCount % 200 == 0) {
					player.addEffect(new MobEffectInstance(MobEffects.LUCK, 220, 0, false, false));
				}
			}
			case "star_wish_rod" -> {
				if (!(player.getMainHandItem() == stack || player.getOffhandItem() == stack)) {
					PasterDreamItemData.update(stack, tag -> tag.putBoolean("cast", false));
				}
			}
			default -> {
			}
		}
	}

	private static void afterAttack(ServerPlayer player, Entity target) {
		String id = itemId(player.getMainHandItem());
		if (id == null) {
			return;
		}
		switch (id) {
			case "creative_sword" -> {
				target.hurt(player.level().damageSources().fellOutOfWorld(), 100_000_000.0F);
				schedule(player.serverLevel(), 1, level -> {
					if (!target.isAlive()) {
						target.discard();
					}
				});
			}
			case "thermal_dagger" -> {
				target.igniteForSeconds(4.0F);
				player.level().playSound(
					null,
					target.blockPosition(),
					PasterDreamSounds.THERMAL_DAGGER,
					SoundSource.PLAYERS,
					1.0F,
					1.0F
				);
				if (player.level() instanceof ServerLevel level) {
					level.sendParticles(ParticleTypes.BUBBLE, target.getX(), target.getY(), target.getZ(), 64, 1.2D, 1.5D, 1.2D, 0.15D);
				}
			}
			case "grass_sword", "true_grass_sword" -> {
				if (target instanceof net.minecraft.world.entity.LivingEntity living) {
					living.addEffect(new MobEffectInstance(MobEffects.POISON, 60, id.equals("true_grass_sword") ? 1 : 0));
					if (id.equals("true_grass_sword")) {
						var data = PasterDreamItemData.read(player.getMainHandItem());
						if (data.getBoolean("skill")) {
							living.hurt(
								player.level().damageSources().generic(),
								(float) (data.getDouble("paster_atk")
									* data.getDouble("skill_multiplier"))
							);
							PasterDreamItemData.update(player.getMainHandItem(), tag -> tag.putBoolean("skill", false));
						}
					}
					if (id.equals("grass_sword") && !target.isAlive()) {
						PasterDreamItemData.update(player.getMainHandItem(), tag ->
							tag.putInt("killed", tag.getInt("killed") + 1)
						);
						if (PasterDreamItemData.read(player.getMainHandItem()).getInt("killed") >= 200) {
							player.getMainHandItem().shrink(1);
							give(player, "true_grass_sword", 1);
							player.displayClientMessage(Component.literal("武器成长到了新的阶段"), true);
						}
					}
				}
			}
			case "true_tide_sword" -> {
				if (target instanceof net.minecraft.world.entity.LivingEntity living) {
					living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 1));
					var data = PasterDreamItemData.read(player.getMainHandItem());
					if (data.getBoolean("skill")) {
						float damage = living.isInWaterOrBubble()
							? (float) ((3.0D + 1.2D * data.getDouble("paster_atk"))
								* data.getDouble("skill_multiplier"))
							: (float) data.getDouble("paster_atk");
						living.hurt(player.level().damageSources().generic(), damage);
						PasterDreamItemData.update(player.getMainHandItem(), tag -> tag.putBoolean("skill", false));
					}
				}
			}
			case "true_desert_sword" -> {
				target.igniteForSeconds(5.0F);
				var data = PasterDreamItemData.read(player.getMainHandItem());
				if (data.getBoolean("skill")) {
					float damage = (float) (
						(5.0D + (data.getDouble("missing_health") * 2.0D + 1.0D)
							* data.getDouble("paster_atk"))
							* data.getDouble("skill_multiplier")
					);
					target.hurt(player.level().damageSources().generic(), damage);
					PasterDreamItemData.update(player.getMainHandItem(), tag -> tag.putBoolean("skill", false));
				}
			}
			case "dyedream_sword_0" -> {
				var data = PasterDreamItemData.read(player.getMainHandItem());
				if (data.getBoolean("skill")) {
					float damage = (float) ((2.0D + data.getDouble("paster_atk") * 1.2D)
						* data.getDouble("skill_multiplier"));
					target.hurt(player.level().damageSources().generic(), damage);
					target.setDeltaMovement(0.0D, 0.8D, 0.0D);
					PasterDreamItemData.update(player.getMainHandItem(), tag -> tag.putBoolean("skill", false));
				}
			}
			case "moltengold_sword" -> target.igniteForSeconds(3.0F);
			case "true_moltengold_sword" -> {
				int burnTicks = target.getRemainingFireTicks() > 0
					? (int) (target.getRemainingFireTicks() * 0.05D + 2.0D)
					: 4;
				target.igniteForSeconds(burnTicks);
			}
			case "truest_moltengold_sword" -> {
				var data = PasterDreamItemData.read(player.getMainHandItem());
				if (data.getBoolean("skill")) {
					float damage = (float) (
						(2.0D + data.getDouble("paster_atk")
							+ target.getRemainingFireTicks() * 0.03D)
							* data.getDouble("skill_multiplier")
					);
					target.hurt(player.level().damageSources().lava(), damage);
					if (target instanceof net.minecraft.world.entity.LivingEntity living
						&& target.getRemainingFireTicks() >= 10) {
						living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
					}
					target.clearFire();
					PasterDreamItemData.update(player.getMainHandItem(), tag -> tag.putBoolean("skill", false));
				} else {
					target.igniteForSeconds(
						target.getRemainingFireTicks() > 0
							? (int) (target.getRemainingFireTicks() * 0.05D + 3.0D)
							: 4
					);
				}
			}
			default -> {
			}
		}
	}

	private static void usePaleBoneneedle(ServerPlayer player, ItemStack stack) {
		player.hurt(player.level().damageSources().generic(), 1.0F);
		Level level = player.level();
		if (level.dimension().equals(DYEDREAM_WORLD)
			|| level.dimension().equals(LAMP_SHADOW_WORLD)
			|| level.dimension().equals(AARONCOS_ARENA_WORLD)) {
			level.playSound(null, player.blockPosition(), PasterDreamSounds.DREAM0, SoundSource.NEUTRAL, 0.5F, 1.0F);
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles((ParticleOptions) PasterDreamParticles.DUST_0_PARTICLE, player.getX(), player.getY(), player.getZ(), 64, 0.1D, 1.0D, 0.1D, 0.2D);
			}
			PasterDreamAdvancements.award(player, "achievement_b_2");
			if (stack.is(PasterDreamItems.ROOTS_PALE_BONENEEDLE)) {
				var tag = PasterDreamItemData.read(stack);
				if (tag.getBoolean("switch")) {
					teleport(player, Level.OVERWORLD, tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"));
				} else {
					teleportToRespawn(player);
				}
			} else {
				teleportToRespawn(player);
			}
			player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0));
			player.getCooldowns().addCooldown(stack.getItem(), 100);
			stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
		}
	}

	private static void openTreasure(ServerPlayer player, ItemStack stack, String id) {
		if (!(player.level() instanceof ServerLevel level)) {
			return;
		}
		level.playSound(null, player.blockPosition(), SoundEvents.CHEST_OPEN, SoundSource.PLAYERS, 1.0F, 1.0F);
		boolean excellent = PasterDreamItemData.read(stack).getBoolean("deep_treasure_super");
		String suffix = id.endsWith("_1") ? "1" : "0";
		String path = "chests/loots_deep_treasure_" + suffix + (excellent ? "_super" : "");
		ResourceKey<LootTable> key = ResourceKey.create(Registries.LOOT_TABLE, PasterDream.id(path));
		LootParams params = new LootParams.Builder(level)
			.withParameter(LootContextParams.ORIGIN, player.position())
			.create(LootContextParamSets.EMPTY);
		List<ItemStack> loot = level.getServer().reloadableRegistries().getLootTable(key).getRandomItems(params);
		for (ItemStack result : loot) {
			ItemEntity drop = new ItemEntity(level, player.getX(), player.getY() + 0.5D, player.getZ(), result);
			drop.setPickUpDelay(5);
			level.addFreshEntity(drop);
		}
		consume(player, stack, 1);
	}

	private static void useTurnPaleCecilia(ServerPlayer player, ItemStack stack) {
		BlockHitResult hit = (BlockHitResult) player.pick(5.0D, 1.0F, true);
		if (player.level().getFluidState(hit.getBlockPos()).createLegacyBlock().is(PasterDreamBlocks.MELTDREAM_LIQUID)) {
			player.level().setBlock(hit.getBlockPos(), Blocks.AIR.defaultBlockState(), 3);
			consume(player, stack, 1);
			give(player, "cecilia_care_charm", 1);
			player.swing(InteractionHand.MAIN_HAND, true);
			player.level().playSound(null, player.blockPosition(), PasterDreamSounds.DREAM0, SoundSource.NEUTRAL, 1.0F, 1.0F);
		}
	}

	private static void useGlassJar(ServerPlayer player, ItemStack stack) {
		BlockHitResult hit = (BlockHitResult) player.pick(5.0D, 1.0F, true);
		if (player.level().getBlockState(hit.getBlockPos()).is(Blocks.WATER)) {
			consume(player, stack, 1);
			give(player, "water_glass_jar", 1);
			player.swing(InteractionHand.MAIN_HAND, true);
			player.level().playSound(null, player.blockPosition(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
		}
	}

	private static void useElixirBottle(ServerPlayer player, ItemStack stack) {
		BlockHitResult hit = (BlockHitResult) player.pick(5.0D, 1.0F, true);
		if (player.level().getFluidState(hit.getBlockPos()).createLegacyBlock().is(PasterDreamBlocks.MELTDREAM_LIQUID)) {
			consume(player, stack, 1);
			player.level().setBlock(hit.getBlockPos(), Blocks.AIR.defaultBlockState(), 3);
			give(player, "meltdream_elixir_bottle", 1);
			player.swing(InteractionHand.MAIN_HAND, true);
			player.level().playSound(null, player.blockPosition(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
		}
	}

	private static void enterArena(ServerPlayer player) {
		if (!PasterDreamAdvancements.completed(player, "achievement_shadow_d_0")
			&& !player.isCreative()) {
			player.displayClientMessage(Component.literal("尚未完成前置进度"), true);
			return;
		}
		teleport(player, AARONCOS_ARENA_WORLD, 0.0D, 70.0D, 0.0D);
		player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120, 0));
		player.setGameMode(GameType.ADVENTURE);
	}

	private static void useMemento(Player player, ItemStack stack, String id) {
		if (!(player instanceof ServerPlayer serverPlayer)) {
			return;
		}
		if (id.equals("memento_01") || id.equals("memento_05")
			|| id.equals("memento_06") || id.equals("memento_10")) {
			PasterDreamPlayerState.addSan(serverPlayer, 10.0D);
			PasterDreamPlayerState.addMeltDreamEnergy(serverPlayer, 10.0D);
		}
		if (id.equals("memento_07")) {
			PasterDreamPlayerState.addSan(
				serverPlayer,
				serverPlayer.getRandom().nextDouble() <= 0.01D ? -100.0D : 20.0D
			);
		}
		player.addEffect(new MobEffectInstance(
			PasterDreamContent.effectHolder(PasterDreamEffects.MEMENTO_BUFF),
			3600,
			0
		));
		consume(player, stack, 1);
		player.level().playSound(null, player.blockPosition(), PasterDreamSounds.DING_0, SoundSource.PLAYERS, 1.0F, 1.0F);
	}

	private static void useDreamHarp(ServerPlayer player, ItemStack stack) {
		if (!PasterDreamPlayerState.consumeMeltDreamEnergy(player, 2.0D)) {
			player.displayClientMessage(Component.literal("融梦能量不足"), true);
			return;
		}
		player.level().playSound(
			null,
			player.blockPosition(),
			PasterDreamSounds.DREAMHARP_OF_WANDERER,
			SoundSource.PLAYERS,
			0.7F,
			1.0F
		);
		if (player.level() instanceof ServerLevel level) {
			level.sendParticles(ParticleTypes.HEART, player.getX(), player.getY() + 1.0D, player.getZ(), 7, 0.45D, 0.8D, 0.45D, 0.5D);
		}
		player.getCooldowns().addCooldown(stack.getItem(), 200);
		for (Player target : player.level().getEntitiesOfClass(
			Player.class,
			player.getBoundingBox().inflate(2.5D),
			candidate -> true
		)) {
			target.addEffect(new MobEffectInstance(
				PasterDreamContent.effectHolder(PasterDreamEffects.DREAMHARP_OF_WANDERER_BUFF),
				1200,
				0
			));
		}
	}

	private static void useIceshadowHammer(ServerPlayer player, ItemStack stack) {
		if (!PasterDreamPlayerState.consumeMeltDreamEnergy(player, 0.1D)) {
			player.displayClientMessage(Component.literal("融梦能量不足"), true);
			return;
		}
		player.getCooldowns().addCooldown(stack.getItem(), 80);
		player.setDeltaMovement(player.getDeltaMovement().add(0.0D, 0.4D, 0.0D));
		if (player.level() instanceof ServerLevel level) {
			summon(level, "shaking_crystal", player.blockPosition().above());
			float damage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
			damageArea(level, player, 2.5D, damage * 0.5F);
			schedule(level, 10, delayed -> {
				for (Entity target : delayed.getEntities(
					player,
					player.getBoundingBox().inflate(2.5D),
					candidate -> candidate instanceof net.minecraft.world.entity.LivingEntity
				)) {
					target.setDeltaMovement(0.0D, 1.0D, 0.0D);
				}
			});
			schedule(level, 11, delayed ->
				damageArea(delayed, player, 2.5D, damage * 0.7F)
			);
			schedule(level, 22, delayed ->
				damageArea(delayed, player, 2.5D, damage * 0.4F)
			);
			level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.NEUTRAL, 0.7F, 0.5F);
		}
	}

	private static void useCradle(ServerPlayer player) {
		if (!PasterDreamPlayerState.consumeMeltDreamEnergy(player, 5.0D)) {
			player.displayClientMessage(Component.literal("融梦能量不足"), true);
			return;
		}
		player.swing(InteractionHand.MAIN_HAND, true);
		if (player.level() instanceof ServerLevel level) {
			summon(level, "fox_fire", player.blockPosition().above());
		}
	}

	private static void useShadowHandLantern(ServerPlayer player, ItemStack stack) {
		player.getCooldowns().addCooldown(stack.getItem(), 160);
		PasterDreamItemData.update(stack, tag -> tag.putString("animation", "activate"));
		player.level().playSound(
			null,
			player.blockPosition(),
			PasterDreamSounds.SHADOW_HAND_LANTERN,
			SoundSource.PLAYERS,
			1.0F,
			1.0F
		);
		if (player.level() instanceof ServerLevel level) {
			level.sendParticles(ParticleTypes.END_ROD, player.getX(), player.getY(), player.getZ(), 128, 4.0D, 0.8D, 4.0D, 0.03D);
			level.sendParticles(ParticleTypes.ASH, player.getX(), player.getY(), player.getZ(), 256, 4.0D, 0.8D, 4.0D, 0.03D);
			for (Entity target : level.getEntities(
				player,
				player.getBoundingBox().inflate(7.5D),
				candidate -> candidate instanceof net.minecraft.world.entity.LivingEntity
					&& candidate.getType().is(net.minecraft.tags.TagKey.create(
						Registries.ENTITY_TYPE,
						PasterDream.id("shadow_mob")
					))
			)) {
				((net.minecraft.world.entity.LivingEntity) target).addEffect(new MobEffectInstance(
					PasterDreamContent.effectHolder(PasterDreamEffects.VULNERABILITY_BUFF),
					300,
					0,
					false,
					false
				));
			}
		}
		PasterDreamPlayerState.addSan(player, -1.0D);
	}

	private static void useTerraSword(ServerPlayer player) {
		if (!PasterDreamPlayerState.consumeMeltDreamEnergy(player, 0.1D)) {
			player.displayClientMessage(Component.literal("融梦能量不足"), false);
			return;
		}
		player.swing(InteractionHand.MAIN_HAND, true);
		applySkillCooldown(player, 84);
		Vec3 look = player.getLookAngle();
		for (int wave = 0; wave < 3; wave++) {
			int delay = wave * 5;
			int distance = wave;
			schedule(player.serverLevel(), delay, level -> {
				Vec3 center = player.position().add(look.scale(2.0D + distance * 2.0D));
				float damage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0F;
				for (Entity target : level.getEntities(
					player,
					new net.minecraft.world.phys.AABB(center, center).inflate(3.0D),
					candidate -> !(candidate instanceof Player)
						&& candidate instanceof net.minecraft.world.entity.LivingEntity
				)) {
					target.hurt(level.damageSources().playerAttack(player), damage);
				}
				level.sendParticles(
					(ParticleOptions) PasterDreamParticles.TERRASWORD_WAVE_PARTICLE,
					center.x,
					center.y,
					center.z,
					24,
					1.0D,
					0.5D,
					1.0D,
					0.1D
				);
			});
		}
	}

	private static void useWhiteSword(ServerPlayer player) {
		if (!player.isCreative()
			&& !PasterDreamAdvancements.completed(player, "achievement_talent_light")) {
			player.displayClientMessage(Component.literal("未选则<明灯> 无法使用此剑"), false);
			return;
		}
		if (!PasterDreamPlayerState.consumeMeltDreamEnergy(player, 0.1D)) {
			player.displayClientMessage(Component.literal("融梦能量不足"), false);
			return;
		}
		player.swing(InteractionHand.MAIN_HAND, true);
		player.level().playSound(
			null,
			player.blockPosition(),
			PasterDreamSounds.WHITE_SWORD_RAIN,
			SoundSource.PLAYERS,
			0.7F,
			1.0F
		);
		applySkillCooldown(player, 84);
		BlockHitResult target = (BlockHitResult) player.pick(9.0D, 1.0F, false);
		BlockPos center = target.getBlockPos();
		for (int wave = 0; wave < 4; wave++) {
			int delay = 8 + wave * 5;
			schedule(player.serverLevel(), delay, level -> {
				for (int index = 0; index < 10; index++) {
					double x = center.getX() + level.getRandom().nextDouble() * 7.0D - 3.5D;
					double z = center.getZ() + level.getRandom().nextDouble() * 7.0D - 3.5D;
					spawnProjectile(
						level,
						"projectile_white_sword_rain_projectile",
						player,
						x,
						player.getY() + 14.0D,
						z,
						0.0D,
						-1.0D,
						0.0D,
						1.0F,
						(float) (3.0D + player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.4D)
					);
				}
			});
		}
	}

	private static void applySkillCooldown(ServerPlayer player, int baseTicks) {
		if (player.isCreative()) {
			return;
		}
		double skillCooldown = player.getAttributeValue(
			PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN)
		);
		int cooldown = (int) (baseTicks * skillCooldown);
		var skillTag = net.minecraft.tags.TagKey.create(
			Registries.ITEM,
			PasterDream.id("skill")
		);
		for (ItemStack stack : player.getInventory().items) {
			if (stack.is(skillTag)) {
				player.getCooldowns().addCooldown(stack.getItem(), cooldown);
			}
		}
		if (player.getOffhandItem().is(skillTag)) {
			player.getCooldowns().addCooldown(player.getOffhandItem().getItem(), cooldown);
		}
	}

	private static void prepareSkillDamage(ItemStack stack, ServerPlayer player) {
		PasterDreamItemData.update(stack, tag -> {
			tag.putBoolean("skill", true);
			tag.putDouble(
				"paster_atk",
				player.getAttributeValue(Attributes.ATTACK_DAMAGE)
			);
			tag.putDouble(
				"skill_multiplier",
				player.getAttributeValue(
					PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_MULTIPLIER)
				)
			);
		});
	}

	private static void spawnProjectile(
		ServerLevel level,
		String projectileId,
		ServerPlayer owner,
		double x,
		double y,
		double z,
		double directionX,
		double directionY,
		double directionZ,
		float velocity,
		float damage
	) {
		@SuppressWarnings("unchecked")
		net.minecraft.world.entity.EntityType<dev.arakiel.pasterdream.content.PasterDreamProjectile> type =
			(net.minecraft.world.entity.EntityType<dev.arakiel.pasterdream.content.PasterDreamProjectile>)
				PasterDreamEntities.get(projectileId);
		var projectile = new dev.arakiel.pasterdream.content.PasterDreamProjectile(type, level);
		projectile.setOwner(owner);
		projectile.setPos(x, y, z);
		projectile.shoot(directionX, directionY, directionZ, velocity, 0.1F);
		projectile.setBaseDamage(damage);
		projectile.setSilent(true);
		level.addFreshEntity(projectile);
	}

	private static void damageArea(ServerLevel level, ServerPlayer player, double radius, float amount) {
		for (Entity target : level.getEntities(
			player,
			player.getBoundingBox().inflate(radius),
			candidate -> !(candidate instanceof Player)
				&& candidate instanceof net.minecraft.world.entity.LivingEntity
		)) {
			target.hurt(level.damageSources().playerAttack(player), amount);
		}
	}

	private static void summon(ServerLevel level, String entityId, BlockPos position) {
		Entity entity = PasterDreamEntities.get(entityId).create(level);
		if (entity != null) {
			entity.moveTo(position.getX() + 0.5D, position.getY(), position.getZ() + 0.5D, level.getRandom().nextFloat() * 360.0F, 0.0F);
			level.addFreshEntity(entity);
		}
	}

	private static void drawCalleCard(ServerPlayer player, ItemStack stack) {
		int card = player.getRandom().nextIntBetweenInclusive(1, 9);
		String[] names = {
			"墓园", "执剑", "疾行", "守护", "对立", "罪恶", "均衡", "圣杯", "混乱"
		};
		player.level().playSound(
			null,
			player.blockPosition(),
			PasterDreamSounds.CARD0,
			SoundSource.PLAYERS,
			1.5F,
			1.0F
		);
		if (player.level() instanceof ServerLevel level) {
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.CALLE_PARTICLE,
				player.getX(),
				player.getY() + 1.0D,
				player.getZ(),
				128,
				1.0D,
				2.0D,
				1.0D,
				0.1D
			);
		}
		player.displayClientMessage(Component.literal("§6你手中的卡莱占卜牌已显示所预言的一面"), false);
		player.displayClientMessage(Component.literal("§6预言卡牌为：『" + names[card - 1] + "』"), false);
		player.displayClientMessage(Component.literal("§6§l『" + names[card - 1] + "』"), true);
		consume(player, stack, 1);
		give(player, "calle_card_" + card, 1);
	}

	private static void useCalleCard(ServerPlayer player, ItemStack stack, String id) {
		int card = Integer.parseInt(id.substring(id.length() - 1));
		player.swing(InteractionHand.MAIN_HAND, true);
		consume(player, stack, 1);
		if (player.level() instanceof ServerLevel level) {
			level.sendParticles(
				(ParticleOptions) PasterDreamParticles.CALLE_PARTICLE,
				player.getX(),
				player.getY() + 1.0D,
				player.getZ(),
				64,
				2.0D,
				1.5D,
				2.0D,
				0.1D
			);
		}
		player.level().playSound(
			null,
			player.blockPosition(),
			SoundEvents.TOTEM_USE,
			SoundSource.NEUTRAL,
			1.0F,
			1.0F
		);
		switch (card) {
			case 1 -> {
				for (Entity target : player.level().getEntities(
					player,
					player.getBoundingBox().inflate(3.0D),
					candidate -> !(candidate instanceof Player)
				)) {
					target.hurt(player.level().damageSources().magic(), 100.0F);
				}
				if (player.level() instanceof ServerLevel level) {
					level.sendParticles(ParticleTypes.LARGE_SMOKE, player.getX(), player.getY(), player.getZ(), 128, 3.0D, 0.0D, 3.0D, 0.25D);
				}
			}
			case 2 -> player.addEffect(new MobEffectInstance(
				PasterDreamContent.effectHolder(PasterDreamEffects.FLAREUP_BUFF),
				2400,
				0
			));
			case 3 -> {
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2400, 2));
				player.addEffect(new MobEffectInstance(MobEffects.JUMP, 2400, 0));
				player.addEffect(new MobEffectInstance(
					PasterDreamContent.effectHolder(PasterDreamEffects.RAPID_REACTION),
					2400,
					0
				));
			}
			case 4 -> {
				player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 4));
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2400, 0));
			}
			case 5 -> {
				if (player.getHealth() >= player.getMaxHealth()) {
					player.hurt(player.level().damageSources().fellOutOfWorld(), player.getMaxHealth());
					PasterDreamAdvancements.award(player, "achievement_special_0");
				}
				if (player.isAlive()) {
					player.setHealth(Math.max(0.0F, player.getMaxHealth() - player.getHealth()));
				}
			}
			case 6 -> {
				for (Entity target : player.level().getEntities(
					player,
					player.getBoundingBox().inflate(9.5D),
					candidate -> candidate.getType().getCategory() == net.minecraft.world.entity.MobCategory.MONSTER
				)) {
					if (target instanceof net.minecraft.world.entity.monster.ZombieVillager zombieVillager) {
						if (player.level() instanceof ServerLevel level) {
							level.sendParticles(ParticleTypes.HEART, zombieVillager.getX(), zombieVillager.getY(), zombieVillager.getZ(), 6, 0.5D, 0.5D, 0.5D, 2.0D);
							BlockPos position = zombieVillager.blockPosition();
							zombieVillager.discard();
							Entity villager = net.minecraft.world.entity.EntityType.VILLAGER.create(level);
							if (villager != null) {
								villager.moveTo(position.getX() + 0.5D, position.getY(), position.getZ() + 0.5D, level.getRandom().nextFloat() * 360.0F, 0.0F);
								level.addFreshEntity(villager);
							}
						}
					} else if (target.getType().is(net.minecraft.tags.EntityTypeTags.UNDEAD) && target.isAlive()) {
						target.igniteForSeconds(15.0F);
						target.hurt(player.level().damageSources().inFire(), 20.0F);
					}
				}
				if (player.level() instanceof ServerLevel level) {
					level.sendParticles(ParticleTypes.FIREWORK, player.getX(), player.getY(), player.getZ(), 256, 9.0D, 0.5D, 9.0D, 0.25D);
				}
			}
			case 7 -> invertCommonEffects(player);
			case 8 -> player.addEffect(new MobEffectInstance(
				PasterDreamContent.effectHolder(PasterDreamEffects.GRAIL_BUFF),
				2400,
				0
			));
			case 9 -> {
				for (Entity target : player.level().getEntities(
					player,
					player.getBoundingBox().inflate(3.5D),
					candidate -> !(candidate instanceof Player)
						&& candidate instanceof net.minecraft.world.entity.LivingEntity
				)) {
					if (target instanceof net.minecraft.world.entity.LivingEntity living) {
						living.addEffect(new MobEffectInstance(
							PasterDreamContent.effectHolder(PasterDreamEffects.CONFUSION_BUFF),
							200,
							0
						));
						living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 3));
					}
				}
				if (player.level() instanceof ServerLevel level) {
					level.sendParticles(ParticleTypes.ASH, player.getX(), player.getY(), player.getZ(), 128, 3.0D, 0.5D, 3.0D, 0.25D);
				}
			}
			default -> {
			}
		}
	}

	private static void invertCommonEffects(ServerPlayer player) {
		for (var effect : List.of(
			MobEffects.MOVEMENT_SPEED,
			MobEffects.MOVEMENT_SLOWDOWN,
			MobEffects.DIG_SPEED,
			MobEffects.DIG_SLOWDOWN,
			MobEffects.DAMAGE_BOOST,
			MobEffects.JUMP,
			MobEffects.REGENERATION,
			MobEffects.HUNGER,
			MobEffects.POISON,
			MobEffects.WITHER,
			MobEffects.DAMAGE_RESISTANCE
		)) {
			MobEffectInstance current = player.getEffect(effect);
			if (current != null) {
				player.addEffect(new MobEffectInstance(
					effect,
					current.getDuration() / 2,
					current.getAmplifier() * 2 + 1,
					current.isAmbient(),
					current.isVisible(),
					current.showIcon()
				));
			}
		}
	}

	private static void teleportToRespawn(ServerPlayer player) {
		if (player.getRespawnPosition() != null
			&& player.getRespawnDimension().equals(player.level().dimension())) {
			BlockPos position = player.getRespawnPosition();
			player.teleportTo(
				(ServerLevel) player.level(),
				position.getX() + 0.5D,
				position.getY(),
				position.getZ() + 0.5D,
				player.getYRot(),
				player.getXRot()
			);
			return;
		}
		ServerLevel overworld = player.server.overworld();
		BlockPos spawn = overworld.getSharedSpawnPos();
		player.teleportTo(
			overworld,
			spawn.getX() + 0.5D,
			spawn.getY(),
			spawn.getZ() + 0.5D,
			player.getYRot(),
			player.getXRot()
		);
	}

	private static void teleport(
		ServerPlayer player,
		ResourceKey<Level> dimension,
		double x,
		double y,
		double z
	) {
		ServerLevel destination = player.server.getLevel(dimension);
		if (destination != null) {
			player.teleportTo(destination, x, y, z, player.getYRot(), player.getXRot());
		}
	}

	private static void consume(Player player, ItemStack stack, int amount) {
		if (!player.getAbilities().instabuild) {
			stack.shrink(amount);
		}
		player.getInventory().setChanged();
	}

	private static void give(Player player, String itemId, int count) {
		Item item = PasterDreamItems.get(itemId);
		player.getInventory().placeItemBackInInventory(new ItemStack(item, count), true);
	}

	private static String itemId(ItemStack stack) {
		if (stack.isEmpty()) {
			return null;
		}
		var key = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem());
		return PasterDream.MOD_ID.equals(key.getNamespace()) ? key.getPath() : null;
	}

	private static void schedule(
		ServerLevel level,
		int delay,
		Consumer<ServerLevel> action
	) {
		SCHEDULED_ACTIONS.add(new ScheduledAction(
			level,
			level.getGameTime() + delay,
			action
		));
	}

	private record ScheduledAction(
		ServerLevel level,
		long executeAt,
		Consumer<ServerLevel> action
	) {
	}
}
