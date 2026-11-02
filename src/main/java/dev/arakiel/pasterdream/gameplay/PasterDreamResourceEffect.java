package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public final class PasterDreamResourceEffect extends InstantenousMobEffect {
	public enum Resource {
		SAN,
		MELT_DREAM_ENERGY
	}

	private final Resource resource;
	private final boolean increase;

	public PasterDreamResourceEffect(Resource resource, boolean increase, int color) {
		super(increase ? MobEffectCategory.BENEFICIAL : MobEffectCategory.HARMFUL, color);
		this.resource = resource;
		this.increase = increase;
	}

	@Override
	public void applyInstantenousEffect(
		Entity source,
		Entity indirectSource,
		LivingEntity target,
		int amplifier,
		double health
	) {
		if (!(target instanceof ServerPlayer player)) {
			return;
		}
		double amount = amplifier + 1.0D;
		if (!increase) {
			amount = -amount;
		}
		if (resource == Resource.SAN) {
			PasterDreamPlayerState.addSan(player, amount);
		} else {
			PasterDreamPlayerState.addMeltDreamEnergy(player, amount);
		}
	}
}
