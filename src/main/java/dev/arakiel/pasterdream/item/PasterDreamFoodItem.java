package dev.arakiel.pasterdream.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public final class PasterDreamFoodItem extends Item {
	private final int useDuration;
	private final UseAnim animation;

	public PasterDreamFoodItem(Properties properties, int useDuration, UseAnim animation) {
		super(properties);
		this.useDuration = useDuration;
		this.animation = animation;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return useDuration;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return animation;
	}
}
