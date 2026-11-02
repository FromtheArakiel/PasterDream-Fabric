package dev.arakiel.pasterdream.content;

import dev.arakiel.pasterdream.gameplay.PasterDreamEffectIdentified;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public final class PasterDreamBlockEffect extends MobEffect implements PasterDreamEffectIdentified {
	private final String effectId;

	public PasterDreamBlockEffect(String effectId, MobEffectCategory category, int color) {
		super(category, color);
		this.effectId = effectId;
	}

	@Override
	public String effectId() {
		return effectId;
	}
}
