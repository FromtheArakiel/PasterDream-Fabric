package dev.arakiel.pasterdream.item;

import dev.arakiel.pasterdream.PasterDream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public record PasterDreamToolTier(
	int uses,
	float speed,
	float attackDamageBonus,
	TagKey<Block> incorrectBlocksForDrops,
	int enchantmentValue,
	String repairItemId
) implements Tier {
	@Override
	public int getUses() {
		return uses;
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public float getAttackDamageBonus() {
		return attackDamageBonus;
	}

	@Override
	public TagKey<Block> getIncorrectBlocksForDrops() {
		return incorrectBlocksForDrops;
	}

	@Override
	public int getEnchantmentValue() {
		return enchantmentValue;
	}

	@Override
	public Ingredient getRepairIngredient() {
		if (repairItemId == null || repairItemId.isBlank()) {
			return Ingredient.EMPTY;
		}
		Item item = BuiltInRegistries.ITEM.getOptional(PasterDream.id(repairItemId)).orElse(null);
		return item == null ? Ingredient.EMPTY : Ingredient.of(item);
	}
}
