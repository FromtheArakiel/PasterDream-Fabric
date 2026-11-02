package dev.arakiel.pasterdream.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedSlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public final class ComponentItemStorage extends CombinedSlottedStorage<ItemVariant, SingleSlotStorage<ItemVariant>> {
	private final ContainerItemContext context;
	private final Item originalItem;
	private final int size;

	public ComponentItemStorage(ContainerItemContext context, int size) {
		super(Collections.emptyList());
		this.context = context;
		this.originalItem = context.getItemVariant().getItem();
		this.size = size;
		List<Slot> slotStorage = new ArrayList<>(size);
		for (int slot = 0; slot < size; slot++) {
			slotStorage.add(new Slot(slot));
		}
		this.parts = Collections.unmodifiableList(slotStorage);
	}

	private NonNullList<ItemStack> contents() {
		NonNullList<ItemStack> items = NonNullList.withSize(size, ItemStack.EMPTY);
		context.getItemVariant()
			.getComponentMap()
			.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
			.copyInto(items);
		return items;
	}

	private boolean stillValid() {
		return context.getItemVariant().getItem() == originalItem;
	}

	private boolean setStack(int slot, ItemStack stack, TransactionContext transaction) {
		NonNullList<ItemStack> items = contents();
		items.set(slot, stack);
		ItemVariant updated = context.getItemVariant().withComponentChanges(
			DataComponentPatch.builder()
				.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items))
				.build()
		);
		return context.exchange(updated, 1, transaction) == 1;
	}

	private final class Slot implements SingleSlotStorage<ItemVariant> {
		private final int index;

		private Slot(int index) {
			this.index = index;
		}

		private ItemStack stack() {
			return contents().get(index);
		}

		@Override
		public long insert(ItemVariant variant, long maxAmount, TransactionContext transaction) {
			StoragePreconditions.notBlankNotNegative(variant, maxAmount);
			if (!stillValid()) {
				return 0;
			}
			ItemStack current = stack();
			if ((current.isEmpty() || variant.matches(current))
				&& variant.getItem() != originalItem
				&& variant.getItem().canFitInsideContainerItems()) {
				int inserted = (int) Math.min(maxAmount, current.getMaxStackSize() - current.getCount());
				if (inserted > 0) {
					ItemStack updated = current.copy();
					if (updated.isEmpty()) {
						updated = variant.toStack(inserted);
					} else {
						updated.grow(inserted);
					}
					if (!setStack(index, updated, transaction)) {
						return 0;
					}
					return inserted;
				}
			}
			return 0;
		}

		@Override
		public long extract(ItemVariant variant, long maxAmount, TransactionContext transaction) {
			StoragePreconditions.notBlankNotNegative(variant, maxAmount);
			if (!stillValid()) {
				return 0;
			}
			ItemStack current = stack();
			if (!variant.matches(current)) {
				return 0;
			}
			int extracted = (int) Math.min(maxAmount, current.getCount());
			if (extracted <= 0) {
				return 0;
			}
			ItemStack updated = current.copy();
			updated.shrink(extracted);
			return setStack(index, updated, transaction) ? extracted : 0;
		}

		@Override
		public boolean isResourceBlank() {
			return stack().isEmpty();
		}

		@Override
		public ItemVariant getResource() {
			return ItemVariant.of(stack());
		}

		@Override
		public long getAmount() {
			return stack().getCount();
		}

		@Override
		public long getCapacity() {
			return stack().getMaxStackSize();
		}
	}
}
