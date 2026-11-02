package dev.arakiel.pasterdream.item;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import dev.arakiel.curios.api.CuriosApi;
import dev.arakiel.curios.api.SlotContext;
import dev.arakiel.curios.api.type.capability.ICurioItem;
import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.component.PasterDreamWorldStateComponent;
import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import dev.arakiel.pasterdream.config.PasterDreamConfig;
import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.content.PasterDreamBlocks;
import dev.arakiel.pasterdream.content.PasterDreamEffects;
import dev.arakiel.pasterdream.content.PasterDreamItems;
import dev.arakiel.pasterdream.content.PasterDreamParticles;
import dev.arakiel.pasterdream.gameplay.PasterDreamAdvancements;
import dev.arakiel.pasterdream.gameplay.PasterDreamAttributes;
import dev.arakiel.pasterdream.gameplay.PasterDreamItemData;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public final class PasterDreamCurioItem extends Item implements ICurioItem {
	private static final ResourceKey<Level> DYEDREAM =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("dyedream_world"));
	private static final ResourceKey<Level> LAMP_SHADOW =
		ResourceKey.create(Registries.DIMENSION, PasterDream.id("lamp_shadow_world"));

	private final String itemId;

	public PasterDreamCurioItem(String itemId, Properties properties) {
		super(properties);
		this.itemId = itemId;
	}

	@Override
	public boolean canEquip(SlotContext context, ItemStack stack) {
		LivingEntity entity = context.entity();
		if (entity == null) {
			return true;
		}
		return CuriosApi.getCuriosInventory(entity)
			.map(inventory -> inventory.findFirstCurio(stack.getItem()).isEmpty())
			.orElse(true);
	}

	@Override
	public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
		SlotContext context,
		ResourceLocation id,
		ItemStack stack
	) {
		Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();
		switch (itemId) {
			case "red_dew_0_ring" -> add(modifiers, id, Attributes.MAX_HEALTH, 1.0D);
			case "red_dew_1_ring" -> add(modifiers, id, Attributes.MAX_HEALTH, 2.0D);
			case "red_dew_2_ring" -> add(modifiers, id, Attributes.MAX_HEALTH, 3.0D);
			case "red_dew_3_ring" -> add(modifiers, id, Attributes.MAX_HEALTH, 4.0D);
			case "hithard_0_ring" -> add(modifiers, id, Attributes.ATTACK_DAMAGE, 0.5D);
			case "hithard_1_ring" -> add(modifiers, id, Attributes.ATTACK_DAMAGE, 1.0D);
			case "all_kinds_ring" -> {
				add(modifiers, id, Attributes.MAX_HEALTH, 4.0D);
				add(modifiers, id, Attributes.ATTACK_DAMAGE, 2.0D);
				add(modifiers, id, Attributes.ATTACK_SPEED, 0.1D);
				add(modifiers, id, Attributes.ENTITY_INTERACTION_RANGE, 0.2D);
				add(modifiers, id, Attributes.BLOCK_INTERACTION_RANGE, 0.5D);
				add(modifiers, id, Attributes.MOVEMENT_SPEED, 0.05D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_COOLDOWN), -0.05D);
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.SKILL_MULTIPLIER), 0.05D);
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), -0.05D);
			}
			case "boboji_curio" -> {
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), -0.2D);
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_RANGE), 0.1D);
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COST), -0.4D);
				add(modifiers, id, Attributes.MOVEMENT_SPEED, 0.05D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
			}
			case "carapax_charm" -> {
				add(modifiers, id, Attributes.MOVEMENT_SPEED, -0.08D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
				add(modifiers, id, Attributes.ARMOR, 2.0D);
				add(modifiers, id, Attributes.ARMOR_TOUGHNESS, 1.0D);
			}
			case "dream_traveler_belt" -> {
				add(modifiers, id, Attributes.MOVEMENT_SPEED, 0.03D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COOLDOWN), -0.1D);
			}
			case "dark_allegory_curio" -> {
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.MAGIC_COOLDOWN), -0.1D);
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.MAGIC_POWER), 1.0D);
			}
			case "duke_coin_curio" -> add(
				modifiers,
				id,
				PasterDreamAttributes.holder(PasterDreamAttributes.LUCK),
				7.0D
			);
			case "hiyori_head" -> {
				add(modifiers, id, Attributes.MAX_HEALTH, 2.0D);
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.LUCK), 15.0D);
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.SAN_VARIABILITY), 0.96D);
			}
			case "garland", "nature_belt" -> add(
				modifiers,
				id,
				PasterDreamAttributes.holder(PasterDreamAttributes.SAN_VARIABILITY),
				0.48D
			);
			case "four_leaf_clover_curio" -> {
				add(modifiers, id, Attributes.MAX_HEALTH, 1.0D);
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.LUCK), 6.0D);
			}
			case "traveler_belt" -> add(
				modifiers,
				id,
				PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COST),
				-0.5D
			);
			case "sea_charm" -> {
				add(modifiers, id, Attributes.WATER_MOVEMENT_EFFICIENCY, 0.3D);
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.SAN_VARIABILITY), 0.96D);
			}
			case "feather_necklace" -> {
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_COST), -0.05D);
				add(modifiers, id, PasterDreamAttributes.holder(PasterDreamAttributes.TELEPORTATION_RANGE), 0.2D);
			}
			case "health_0_necklace" -> add(modifiers, id, Attributes.MAX_HEALTH, 2.0D);
			case "degenerate_bodies" -> {
				add(modifiers, id, Attributes.MAX_HEALTH, -4.0D);
				add(modifiers, id, Attributes.ENTITY_INTERACTION_RANGE, 0.2D);
				add(modifiers, id, Attributes.BLOCK_INTERACTION_RANGE, 1.0D);
				add(modifiers, id, Attributes.ATTACK_DAMAGE, 2.0D);
			}
			case "shadow_breath" -> {
				int level = PasterDreamItemData.read(stack).getInt("shadow_breath_level");
				if (level > 0) {
					add(modifiers, id, Attributes.ATTACK_DAMAGE, 1 << level - 1);
				}
			}
			default -> {
			}
		}
		return modifiers;
	}

	@Override
	public void curioTick(SlotContext context, ItemStack stack) {
		LivingEntity entity = context.entity();
		if (entity == null || entity.level().isClientSide()) {
			return;
		}
		if ("meltdream_energy_0_ring".equals(itemId)
			&& entity instanceof ServerPlayer player
			&& player.tickCount % 20 == 0
			&& (player.level().dimension().equals(DYEDREAM) || player.level().dimension().equals(LAMP_SHADOW))) {
			PasterDreamPlayerState.addMeltDreamEnergy(player, 0.0025D);
		}
		if ("rabbit_0_necklace".equals(itemId) && entity.tickCount % 20 == 0) {
			entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 2, 0, false, false));
		}
		if ("light_butterfly_curio".equals(itemId)
			&& entity instanceof ServerPlayer player
			&& player.tickCount % 20 == 0
			&& player.level().getMaxLocalRawBrightness(player.blockPosition()) <= 7
			&& PasterDreamPlayerState.consumeMeltDreamEnergy(player, 0.004D)) {
			player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 240, 0, false, false));
		}
		if ("fire_0_necklace".equals(itemId) && entity instanceof ServerPlayer player) {
			if (PasterDreamConfig.common().disableFireNecklace) {
				player.displayClientMessage(
					net.minecraft.network.chat.Component.literal("§4此物品已被禁用"),
					true
				);
			} else {
				if (player.level().getBlockState(player.blockPosition()).isAir()) {
					player.level().setBlock(
						player.blockPosition(),
						net.minecraft.world.level.block.Blocks.FIRE.defaultBlockState(),
						3
					);
				}
				if (player.isOnFire()) {
					player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 2, 0, false, false));
				}
			}
		}
		if ("world_tree_seedpod".equals(itemId)
			&& entity instanceof ServerPlayer player
			&& player.level().dimension().equals(DYEDREAM)
			&& player.level().canSeeSkyFromBelowWater(player.blockPosition())
			&& player.level().isDay()
			&& player.getY() > 160.0D
			&& player.tickCount % 20 == 0
			&& (player.level().getBlockState(player.blockPosition().below())
					.is(PasterDreamBlocks.DYEDREAM_WORLDTREE_LEAVES)
				|| player.level().getBlockState(player.blockPosition().below(2))
					.is(PasterDreamBlocks.DYEDREAM_WORLDTREE_LEAVES))) {
			player.causeFoodExhaustion(0.4F);
			PasterDreamPlayerState.addMeltDreamEnergy(player, 0.1D);
		}
		if ("bright_butterfly_curio".equals(itemId)) {
			entity.removeEffect(MobEffects.DARKNESS);
			if (entity.level().getMaxLocalRawBrightness(entity.blockPosition()) <= 7) {
				entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 240, 0, false, false));
			}
		}
		if ("shadow_breath".equals(itemId) && entity instanceof ServerPlayer player) {
			double san = PasterDreamPlayerState.san(player).value();
			int level = san <= 20.0D ? 3 : san <= 40.0D ? 2 : san <= 60.0D ? 1 : 0;
			PasterDreamItemData.update(stack, tag -> {
				if (level == 0) {
					tag.remove("shadow_breath_level");
				} else {
					tag.putInt("shadow_breath_level", level);
				}
			});
		}
		if ("snow_vow_head".equals(itemId) && entity instanceof ServerPlayer player) {
			for (Player target : player.level().getEntitiesOfClass(
				Player.class,
				player.getBoundingBox().inflate(1.75D),
				candidate -> true
			)) {
				target.addEffect(new MobEffectInstance(
					PasterDreamContent.effectHolder(PasterDreamEffects.SNOW_VOW_BUFF),
					20,
					0,
					false,
					false
				));
			}
		}
		if ("paper_plane".equals(itemId) && entity instanceof ServerPlayer player) {
			PasterDreamWorldStateComponent state = PasterDreamPlayerState.worldState(player);
			state.setTailwindForce(1.0D);
			state.setDeadwindForce(1.0D);
		}
		if ("white_flower_body".equals(itemId) && entity instanceof ServerPlayer player
			&& !PasterDreamAdvancements.completed(player, "achievement_talent_light")) {
			ItemStack dropStack = stack.copyWithCount(1);
			ItemEntity drop = new ItemEntity(
				player.serverLevel(),
				player.getX(),
				player.getY(),
				player.getZ(),
				dropStack
			);
			drop.setPickUpDelay(40);
			player.serverLevel().addFreshEntity(drop);
			stack.shrink(1);
			player.hurt(player.level().damageSources().generic(), 1.0F);
			player.displayClientMessage(
				net.minecraft.network.chat.Component.literal("这件饰品拒绝了你"),
				true
			);
		}
		if ("cecilia_care_charm".equals(itemId) && entity instanceof ServerPlayer player
			&& player.getHealth() <= player.getMaxHealth() * 0.15F) {
			stack.shrink(1);
			player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4, false, false));
			player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 3, false, false));
			player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 2, false, false));
			player.addEffect(new MobEffectInstance(MobEffects.JUMP, 200, 1, false, false));
			player.removeEffect(PasterDreamContent.effectHolder(PasterDreamEffects.TELEPORTATION_BUFF));
			player.serverLevel().playSound(
				null,
				player.blockPosition(),
				SoundEvents.TOTEM_USE,
				SoundSource.NEUTRAL,
				1.0F,
				1.0F
			);
			player.serverLevel().sendParticles(
				(net.minecraft.core.particles.ParticleOptions) PasterDreamParticles.MELTDREAM_CRYSTAL_BIG_PARTICLE,
				player.getX(),
				player.getY() + 1.0D,
				player.getZ(),
				32,
				1.0D,
				1.0D,
				1.0D,
				0.2D
			);
			player.getInventory().placeItemBackInInventory(
				new ItemStack(PasterDreamItems.TURN_PALE_CECILIA),
				true
			);
		}
		if (("moss_phantom_membrane".equals(itemId)
			|| "light_moss_phantom_membrane".equals(itemId))
			&& entity instanceof ServerPlayer player) {
			int threshold = "light_moss_phantom_membrane".equals(itemId) ? 60 : 80;
			int elapsed = PasterDreamItemData.read(stack).getInt("membrane_ticks") + 1;
			if (elapsed >= threshold) {
				ItemStack chest = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST);
				if (player.level().getMaxLocalRawBrightness(player.blockPosition()) >= 7
					&& chest.is(Items.ELYTRA)) {
					chest.hurtAndBreak(1, player, net.minecraft.world.entity.EquipmentSlot.CHEST);
					if ("light_moss_phantom_membrane".equals(itemId) && !chest.isEmpty()) {
						chest.hurtAndBreak(1, player, net.minecraft.world.entity.EquipmentSlot.CHEST);
					}
				}
				elapsed = 0;
			}
			int nextElapsed = elapsed;
			PasterDreamItemData.update(stack, tag -> tag.putInt("membrane_ticks", nextElapsed));
		}
	}

	@Override
	public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
		if ("paper_plane".equals(itemId)
			&& context.entity() instanceof ServerPlayer player) {
			PasterDreamWorldStateComponent state = PasterDreamPlayerState.worldState(player);
			state.setTailwindForce(0.0D);
			state.setDeadwindForce(0.0D);
		}
	}

	@Override
	public boolean isEnderMask(SlotContext context, EnderMan enderMan, ItemStack stack) {
		return "end_eye_charm".equals(itemId);
	}

	@Override
	public boolean makesPiglinsNeutral(SlotContext context, ItemStack stack) {
		return "gold_charm".equals(itemId);
	}

	private static void add(
		Multimap<Holder<Attribute>, AttributeModifier> modifiers,
		ResourceLocation source,
		Holder<Attribute> attribute,
		double amount
	) {
		add(modifiers, source, attribute, amount, AttributeModifier.Operation.ADD_VALUE);
	}

	private static void add(
		Multimap<Holder<Attribute>, AttributeModifier> modifiers,
		ResourceLocation source,
		Holder<Attribute> attribute,
		double amount,
		AttributeModifier.Operation operation
	) {
		modifiers.put(
			attribute,
			new AttributeModifier(
				source.withPath(path -> path + "/" + attribute.unwrapKey().orElseThrow().location().getPath()),
				amount,
				operation
			)
		);
	}
}
