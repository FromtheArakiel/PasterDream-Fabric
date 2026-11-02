package dev.arakiel.pasterdream.gameplay.recipe;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record WorkshopRecipeInput(List<ItemStack> items) implements RecipeInput {
	public WorkshopRecipeInput {
		items = List.copyOf(items);
	}

	@Override
	public ItemStack getItem(int slot) {
		return slot >= 0 && slot < items.size() ? items.get(slot) : ItemStack.EMPTY;
	}

	@Override
	public int size() {
		return items.size();
	}
}
