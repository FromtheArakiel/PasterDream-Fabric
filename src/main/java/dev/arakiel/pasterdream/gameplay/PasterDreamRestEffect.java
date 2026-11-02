package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.content.PasterDreamEffects;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public final class PasterDreamRestEffect extends net.minecraft.world.effect.MobEffect
	implements PasterDreamEffectIdentified
{
	private final String effectId;
	private final boolean darkVariant;

	public PasterDreamRestEffect(String effectId, boolean darkVariant, int color) {
		super(MobEffectCategory.BENEFICIAL, color);
		this.effectId = effectId;
		this.darkVariant = darkVariant;
	}

	@Override
	public String effectId() {
		return effectId;
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
		boolean shouldBeDark = !entity.level().canSeeSkyFromBelowWater(entity.blockPosition())
			&& entity.level().getMaxLocalRawBrightness(entity.blockPosition()) >= 7;
		if (shouldBeDark == darkVariant) {
			return true;
		}
		MobEffectInstance current = entity.getEffect(
			PasterDreamContent.effectHolder(
				darkVariant ? PasterDreamEffects.REST_BUFF_IN_DARK : PasterDreamEffects.REST_BUFF
			)
		);
		if (current == null) {
			return true;
		}
		entity.removeEffect(current.getEffect());
		entity.addEffect(new MobEffectInstance(
			PasterDreamContent.effectHolder(
				darkVariant ? PasterDreamEffects.REST_BUFF : PasterDreamEffects.REST_BUFF_IN_DARK
			),
			current.getDuration(),
			current.getAmplifier(),
			current.isAmbient(),
			current.isVisible(),
			current.showIcon()
		));
		return true;
	}
}
