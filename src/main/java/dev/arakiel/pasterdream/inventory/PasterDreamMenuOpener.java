package dev.arakiel.pasterdream.inventory;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.content.PasterDreamItems;
import dev.arakiel.pasterdream.content.PasterDreamMenu;
import dev.arakiel.pasterdream.content.PasterDreamMenus;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public final class PasterDreamMenuOpener {
	private static final Map<String, ExtendedScreenHandlerType<PasterDreamMenu, PasterDreamMenuData>> BLOCK_MENUS =
		new HashMap<>();
	private static final Map<Item, String> ITEM_MENUS = new HashMap<>();

	private PasterDreamMenuOpener() {
	}

	public static void register() {
		registerBlock("dream_accumulator", PasterDreamMenus.DREAM_ACCUMULATOR_GUI);
		registerBlock("dream_cauldron", PasterDreamMenus.DREAM_CAULDRON_GUI);
		registerBlock("dyedream_desk", PasterDreamMenus.DYEDREAM_DESK_GUI);
		registerBlock("meltdream_chest", PasterDreamMenus.MELTDREAM_CHEST_GUI_0);
		registerBlock("meltdream_chest_open", PasterDreamMenus.MELTDREAM_CHEST_GUI_0);
		registerBlock("picnic_basket", PasterDreamMenus.PICNIC_BASKET_GUI);
		registerBlock("research_table", PasterDreamMenus.RESEARCH_TABLE_GUI);
		registerBlock("shadow_blast_furnace", PasterDreamMenus.SHADOW_BLAST_FURNACE_GUI);
		registerBlock("shadow_blast_furnace_core", PasterDreamMenus.SHADOW_BLAST_FURNACE_GUI);
		registerBlock("shadow_chest", PasterDreamMenus.SHADOW_CHEST_GUI);
		registerBlock("shadow_desk", PasterDreamMenus.SHADOW_DESK_GUI);
		registerBlock("the_endless_book_of_dream_seekers", PasterDreamMenus.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS_GUI);
		registerBlock("weapon_workshop", PasterDreamMenus.WEAPON_WORKSHOP_GUI);
		registerBlock("windmoor_crate", PasterDreamMenus.WINDMOOR_CRATE_GUI);
		registerBlock("workshop_anvil", PasterDreamMenus.WORKSHOP_ANVIL_GUI);
		registerBlock("workshop_blast", PasterDreamMenus.WORKSHOP_BLAST_GUI);

		ITEM_MENUS.put(PasterDreamItems.STORAGE_BAG, PasterDreamMenuSpecs.STORAGE_BAG);
		ITEM_MENUS.put(PasterDreamItems.STORAGE_BAG_0, PasterDreamMenuSpecs.STORAGE_BAG_LARGE);
		ITEM_MENUS.put(PasterDreamItems.SHADOW_BLAST_FURNACE_BLUEPRINT, PasterDreamMenuSpecs.BLUEPRINT);
		ITEM_MENUS.put(PasterDreamItems.WEAPON_WORKSHOP_BLUEPRINT, PasterDreamMenuSpecs.BLUEPRINT);
		for (int index = 0; index < 15; index++) {
			ITEM_MENUS.put(PasterDreamItems.get("dream_note_" + index), PasterDreamMenuSpecs.DREAMNOTES);
		}

		UseBlockCallback.EVENT.register(PasterDreamMenuOpener::useBlock);
		UseItemCallback.EVENT.register(PasterDreamMenuOpener::useItem);
	}

	public static void openBlock(ServerPlayer player, BlockPos pos, String menuId) {
		ExtendedScreenHandlerType<PasterDreamMenu, PasterDreamMenuData> type = menuType(menuId);
		player.openMenu(new Factory(
			player.serverLevel().getBlockState(pos).getBlock().getName(),
			type,
			PasterDreamMenuData.block(pos)
		));
	}

	public static void openItem(ServerPlayer player, InteractionHand hand, String menuId) {
		int slot = hand == InteractionHand.MAIN_HAND
			? player.getInventory().selected
			: Inventory.SLOT_OFFHAND;
		ExtendedScreenHandlerType<PasterDreamMenu, PasterDreamMenuData> type = menuType(menuId);
		PasterDreamMenuData data = PasterDreamMenuSpecs.get(menuId).backing()
			== PasterDreamMenuBacking.PLAYER_SLOT
			? PasterDreamMenuData.playerSlot(slot)
			: PasterDreamMenuData.virtualMenu(slot);
		player.openMenu(new Factory(
			player.getInventory().getItem(slot).getHoverName(),
			type,
			data
		));
	}

	private static InteractionResult useBlock(
		Player player,
		Level world,
		InteractionHand hand,
		BlockHitResult hit
	) {
		if (hand != InteractionHand.MAIN_HAND || player.isSpectator()) {
			return InteractionResult.PASS;
		}
		ResourceLocation id = net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(
			world.getBlockState(hit.getBlockPos()).getBlock()
		);
		if (!PasterDream.MOD_ID.equals(id.getNamespace())) {
			return InteractionResult.PASS;
		}
		ExtendedScreenHandlerType<PasterDreamMenu, PasterDreamMenuData> type = BLOCK_MENUS.get(id.getPath());
		if (type == null) {
			return InteractionResult.PASS;
		}
		if (player instanceof ServerPlayer serverPlayer) {
			player.openMenu(new Factory(
				world.getBlockState(hit.getBlockPos()).getBlock().getName(),
				type,
				PasterDreamMenuData.block(hit.getBlockPos())
			));
		}
		return InteractionResult.sidedSuccess(world.isClientSide());
	}

	private static InteractionResultHolder<ItemStack> useItem(
		Player player,
		Level world,
		InteractionHand hand
	) {
		ItemStack stack = player.getItemInHand(hand);
		String menuId = ITEM_MENUS.get(stack.getItem());
		if (menuId == null || player.isSpectator()) {
			return InteractionResultHolder.pass(stack);
		}
		if (player instanceof ServerPlayer serverPlayer) {
			openItem(serverPlayer, hand, menuId);
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

	@SuppressWarnings("unchecked")
	private static void registerBlock(
		String blockId,
		MenuType<PasterDreamMenu> menu
	) {
		BLOCK_MENUS.put(
			blockId,
			(ExtendedScreenHandlerType<PasterDreamMenu, PasterDreamMenuData>) menu
		);
	}

	@SuppressWarnings("unchecked")
	private static ExtendedScreenHandlerType<PasterDreamMenu, PasterDreamMenuData> menuType(String id) {
		return (ExtendedScreenHandlerType<PasterDreamMenu, PasterDreamMenuData>)
			net.minecraft.core.registries.BuiltInRegistries.MENU
			.getOptional(PasterDream.id(id))
			.orElseThrow(() -> new IllegalArgumentException("Unknown PasterDream menu: " + id));
	}

	private record Factory(
		Component title,
		ExtendedScreenHandlerType<PasterDreamMenu, PasterDreamMenuData> type,
		PasterDreamMenuData data
	) implements ExtendedScreenHandlerFactory<PasterDreamMenuData> {
		@Override
		public Component getDisplayName() {
			return title;
		}

		@Override
		public PasterDreamMenu createMenu(int syncId, Inventory inventory, Player player) {
			return type.create(syncId, inventory, data);
		}

		@Override
		public PasterDreamMenuData getScreenOpeningData(ServerPlayer player) {
			return data;
		}
	}
}
