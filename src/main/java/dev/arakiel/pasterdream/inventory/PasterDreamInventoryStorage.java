package dev.arakiel.pasterdream.inventory;

import dev.arakiel.pasterdream.content.PasterDreamItems;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;

public final class PasterDreamInventoryStorage {
	private PasterDreamInventoryStorage() {
	}

	public static void register() {
		ItemStorage.ITEM.registerForItems(
			(stack, context) -> (Storage<ItemVariant>) new ComponentItemStorage(context, 9),
			PasterDreamItems.STORAGE_BAG
		);
		ItemStorage.ITEM.registerForItems(
			(stack, context) -> (Storage<ItemVariant>) new ComponentItemStorage(context, 25),
			PasterDreamItems.STORAGE_BAG_0
		);
	}
}
