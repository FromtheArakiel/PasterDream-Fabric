package dev.arakiel.pasterdream.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import java.util.Collections;

public final class ItemStackContainer implements Container {
	private final ItemStack owner;
	private final int size;
	private final Runnable changed;
	private final NonNullList<ItemStack> items;

	public ItemStackContainer(ItemStack owner, int size, Runnable changed) {
		this.owner = owner;
		this.size = size;
		this.changed = changed;
		this.items = NonNullList.withSize(size, ItemStack.EMPTY);
		owner.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(items);
	}

	@Override
	public int getContainerSize() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return items.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public ItemStack getItem(int slot) {
		return items.get(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		ItemStack removed = ContainerHelper.removeItem(items, slot, amount);
		if (!removed.isEmpty()) {
			setChanged();
		}
		return removed;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		ItemStack removed = ContainerHelper.takeItem(items, slot);
		setChanged();
		return removed;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		if (stack == owner) {
			return;
		}
		items.set(slot, stack);
		setChanged();
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}

	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		return stack.isEmpty() || !ItemStack.isSameItemSameComponents(owner, stack);
	}

	@Override
	public void setChanged() {
		owner.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
		changed.run();
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		Collections.fill(items, ItemStack.EMPTY);
		setChanged();
	}
}
