package dev.arakiel.pasterdream.content;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.inventory.ItemStackContainer;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuBacking;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuData;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuSpecs.MenuSpec;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuSpecs.SlotAccess;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuSpecs.SlotSpec;
import dev.arakiel.pasterdream.gameplay.PasterDreamBlueprints;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class PasterDreamMenu extends AbstractContainerMenu {
	private static final TagKey<Item> DREAMNOTES =
		TagKey.create(Registries.ITEM, PasterDream.id("dreamnotes"));

	private final Inventory playerInventory;
	private final MenuSpec spec;
	private final PasterDreamMenuData data;
	private final Container container;
	private final Item expectedItem;
	private final BlockEntity boundBlockEntity;
	private final ContainerLevelAccess access;

	public PasterDreamMenu(
		MenuType<?> type,
		int syncId,
		Inventory playerInventory,
		MenuSpec spec,
		PasterDreamMenuData data
	) {
		super(type, syncId);
		this.playerInventory = playerInventory;
		this.spec = spec;
		this.data = data;
		this.access = data.backing() == PasterDreamMenuBacking.BLOCK
			? ContainerLevelAccess.create(playerInventory.player.level(), data.pos())
			: ContainerLevelAccess.NULL;
		this.boundBlockEntity = resolveBlockEntity(playerInventory, data);
		this.container = resolveContainer(playerInventory, data, spec);
		this.expectedItem = data.backing() == PasterDreamMenuBacking.PLAYER_SLOT
			? playerInventory.getItem(data.playerSlot()).getItem()
			: null;

		for (SlotSpec slotSpec : spec.slots()) {
			addSlot(new PasterDreamSlot(container, slotSpec));
		}
		if (spec.playerInventory()) {
			addPlayerInventory(playerInventory, spec);
		}
		if (!playerInventory.player.level().isClientSide()) {
			container.startOpen(playerInventory.player);
		}
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		int containerSlots = spec.slots().size();
		if (containerSlots == 0 || index < 0 || index >= slots.size()) {
			return ItemStack.EMPTY;
		}
		Slot slot = slots.get(index);
		if (!slot.hasItem()) {
			return ItemStack.EMPTY;
		}
		ItemStack original = slot.getItem().copy();
		ItemStack moving = slot.getItem();
		if (index < containerSlots) {
			if (!moveItemStackTo(moving, containerSlots, slots.size(), true)) {
				return ItemStack.EMPTY;
			}
		} else if (!moveItemStackTo(moving, 0, containerSlots, false)) {
			if (index < containerSlots + 27) {
				if (!moveItemStackTo(moving, containerSlots + 27, slots.size(), false)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(moving, containerSlots, containerSlots + 27, false)) {
				return ItemStack.EMPTY;
			}
		}
		if (moving.isEmpty()) {
			slot.setByPlayer(ItemStack.EMPTY);
		} else {
			slot.setChanged();
		}
		return moving.getCount() == original.getCount() ? ItemStack.EMPTY : original;
	}

	@Override
	public boolean stillValid(Player player) {
		if (data.backing() == PasterDreamMenuBacking.BLOCK) {
			if (boundBlockEntity == null || boundBlockEntity.isRemoved()) {
				return false;
			}
			return AbstractContainerMenu.stillValid(
				access,
				player,
				boundBlockEntity.getBlockState().getBlock()
			);
		}
		if (data.backing() == PasterDreamMenuBacking.PLAYER_SLOT) {
			ItemStack current = playerInventory.getItem(data.playerSlot());
			return expectedItem != null && current.is(expectedItem);
		}
		return player.isAlive();
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		if (!player.level().isClientSide()) {
			container.stopOpen(player);
		}
	}

	public MenuSpec spec() {
		return spec;
	}

	public Container container() {
		return container;
	}

	public BlockPos pos() {
		return data.pos();
	}

	private static BlockEntity resolveBlockEntity(Inventory inventory, PasterDreamMenuData data) {
		if (data.backing() != PasterDreamMenuBacking.BLOCK) {
			return null;
		}
		BlockPos pos = data.pos();
		return inventory.player.level().getBlockEntity(pos);
	}

	private static Container resolveContainer(
		Inventory inventory,
		PasterDreamMenuData data,
		MenuSpec spec
	) {
		if (data.backing() == PasterDreamMenuBacking.BLOCK) {
			BlockEntity blockEntity = resolveBlockEntity(inventory, data);
			if (blockEntity instanceof Container blockContainer) {
				return blockContainer;
			}
			return new SimpleContainer(spec.capacity());
		}
		if (data.backing() == PasterDreamMenuBacking.PLAYER_SLOT) {
			ItemStack stack = inventory.getItem(data.playerSlot());
			return new ItemStackContainer(stack, spec.capacity(), inventory::setChanged);
		}
		if (spec.id().equals(dev.arakiel.pasterdream.inventory.PasterDreamMenuSpecs.BLUEPRINT)) {
			ItemStack blueprintStack = inventory.getItem(data.playerSlot());
			var blueprintId = PasterDreamBlueprints.blueprintId(blueprintStack.getItem());
			var blueprint = blueprintId == null ? null : PasterDreamBlueprints.get(blueprintId);
			SimpleContainer preview = new SimpleContainer(spec.capacity());
			if (blueprint != null) {
				var page = blueprint.page(0);
				for (int index = 0; index < Math.min(page.size(), preview.getContainerSize()); index++) {
					preview.setItem(index, page.get(index));
				}
			}
			return preview;
		}
		return new SimpleContainer(spec.capacity());
	}

	private void addPlayerInventory(Inventory inventory, MenuSpec spec) {
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 9; column++) {
				int index = column + row * 9 + 9;
				int x = spec.playerX() + column * 18;
				int y = spec.playerY() + row * 18;
				addSlot(new Slot(inventory, index, x, y));
			}
		}
		for (int column = 0; column < 9; column++) {
			int x = spec.hotbarX() + column * 18;
			addSlot(new Slot(inventory, column, x, spec.hotbarY()));
		}
	}

	private static final class PasterDreamSlot extends Slot {
		private final SlotAccess access;

		private PasterDreamSlot(Container container, SlotSpec spec) {
			super(container, spec.index(), spec.x(), spec.y());
			this.access = spec.access();
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			boolean allowed = switch (access) {
				case OUTPUT, DISPLAY -> false;
				case PEN_AND_INK -> stack.is(PasterDreamItems.PEN_AND_INK);
				case DREAMNOTES -> stack.is(DREAMNOTES);
				case PERGAMYN -> stack.is(PasterDreamItems.PERGAMYN);
				case UNKNOWN_NOTES -> stack.is(PasterDreamItems.UNKNOWN_NOTES);
				case SORBENT -> stack.is(PasterDreamItems.SORBENT);
				case NORMAL -> true;
			};
			return allowed && container.canPlaceItem(getContainerSlot(), stack);
		}

		@Override
		public boolean mayPickup(Player player) {
			return access != SlotAccess.DISPLAY;
		}
	}
}
