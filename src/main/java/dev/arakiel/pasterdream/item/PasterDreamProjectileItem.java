package dev.arakiel.pasterdream.item;

import dev.arakiel.curios.api.CuriosApi;
import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import dev.arakiel.pasterdream.content.PasterDreamEntities;
import dev.arakiel.pasterdream.content.PasterDreamItems;
import dev.arakiel.pasterdream.content.PasterDreamProjectile;
import dev.arakiel.pasterdream.gameplay.PasterDreamAdvancements;
import dev.arakiel.pasterdream.gameplay.PasterDreamAttributes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class PasterDreamProjectileItem extends Item {
	private final String projectileId;
	private final float velocity;
	private final float damage;
	private final String ammoId;

	public PasterDreamProjectileItem(
		Properties properties,
		String projectileId,
		float velocity,
		float damage,
		String ammoId
	) {
		super(properties);
		this.projectileId = projectileId;
		this.velocity = velocity;
		this.damage = damage;
		this.ammoId = ammoId;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		player.startUsingItem(hand);
		return InteractionResultHolder.success(player.getItemInHand(hand));
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remainingUseTicks) {
		if (level.isClientSide() || !(entity instanceof ServerPlayer player)) {
			return;
		}
		if (projectileId.equals("projectile_strawberry_heart_project") && player.isShiftKeyDown()) {
			useStrawberryHeart(player);
			return;
		}
		if (!canCast(player)) {
			return;
		}
		if (ammoId != null && !player.getAbilities().instabuild && !consumeAmmo(player)) {
			return;
		}
		@SuppressWarnings("unchecked")
		EntityType<PasterDreamProjectile> type =
			(EntityType<PasterDreamProjectile>) PasterDreamEntities.get(projectileId);
		PasterDreamProjectile.shoot(type, player, velocity, damage);
		if (ammoId == null && !player.getAbilities().instabuild) {
			stack.shrink(1);
		}
		applyMagicCooldown(player, stack);
	}

	private boolean canCast(ServerPlayer player) {
		if (player.getAbilities().instabuild) {
			return true;
		}
		if (projectileId.equals("projectile_squeal_wave_wand_projectile")) {
			return PasterDreamPlayerState.meltDreamEnergy(player).value() >= 0.01D
				&& (PasterDreamPlayerState.san(player).value() >= 0.02D
					|| !PasterDreamPlayerState.san(player).enabled());
		}
		if (projectileId.equals("projectile_shadow_vortex_book_projectile")) {
			return PasterDreamAdvancements.completed(player, "achievement_talent_shadow")
				&& PasterDreamPlayerState.meltDreamEnergy(player).value() >= 0.01D
				&& (PasterDreamPlayerState.san(player).value() >= 0.05D
					|| !PasterDreamPlayerState.san(player).enabled());
		}
		return true;
	}

	private void applyMagicCooldown(ServerPlayer player, ItemStack stack) {
		boolean ghostFace = CuriosApi.getCuriosInventory(player)
			.map(inventory -> inventory.findFirstCurio(PasterDreamItems.GHOST_FACE_HEAD).isPresent())
			.orElse(false);
		double magicCooldown = player.getAttributeValue(
			PasterDreamAttributes.holder(PasterDreamAttributes.MAGIC_COOLDOWN)
		);
		if (projectileId.equals("projectile_squeal_wave_wand_projectile")) {
			if (!player.getAbilities().instabuild && !ghostFace) {
				PasterDreamPlayerState.consumeMeltDreamEnergy(player, 0.01D);
				PasterDreamPlayerState.addSan(player, -0.02D);
			}
			player.getCooldowns().addCooldown(stack.getItem(), (int) (18.0D + magicCooldown));
		} else if (projectileId.equals("projectile_shadow_vortex_book_projectile")) {
			PasterDreamPlayerState.consumeMeltDreamEnergy(player, 0.01D);
			PasterDreamPlayerState.addSan(player, -0.05D);
			player.getCooldowns().addCooldown(stack.getItem(), (int) (24.0D + magicCooldown));
		} else if (ammoId != null && !ghostFace) {
			player.getCooldowns().addCooldown(stack.getItem(), (int) (20.0D + magicCooldown));
		}
	}

	private boolean consumeAmmo(ServerPlayer player) {
		Item ammo = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(
			net.minecraft.resources.ResourceLocation.parse(
				ammoId.contains(":") ? ammoId : "pasterdream:" + ammoId
			)
		);
		for (int index = 0; index < player.getInventory().getContainerSize(); index++) {
			ItemStack candidate = player.getInventory().getItem(index);
			if (candidate.is(ammo)) {
				candidate.shrink(1);
				player.getInventory().setChanged();
				return true;
			}
		}
		return false;
	}

	private static void useStrawberryHeart(ServerPlayer player) {
		if (!player.getAbilities().instabuild
			&& !PasterDreamPlayerState.consumeMeltDreamEnergy(player, 0.25D)) {
			player.displayClientMessage(net.minecraft.network.chat.Component.literal("融梦能量不足"), true);
			return;
		}
		for (Player target : player.level().getEntitiesOfClass(
			Player.class,
			player.getBoundingBox().inflate(4.0D),
			candidate -> true
		)) {
			target.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 0));
			target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
			target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0));
			target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0));
		}
		player.level().playSound(
			null,
			player.blockPosition(),
			SoundEvents.NOTE_BLOCK_GUITAR.value(),
			SoundSource.PLAYERS,
			1.2F,
			1.0F
		);
		player.getCooldowns().addCooldown(
			PasterDreamItems.STRAWBERRY_HEART,
			(int) (100.0D * player.getAttributeValue(
				PasterDreamAttributes.holder(PasterDreamAttributes.MAGIC_COOLDOWN)
			))
		);
	}
}
