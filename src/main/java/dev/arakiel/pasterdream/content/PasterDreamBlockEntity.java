package dev.arakiel.pasterdream.content;

import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuSpecs;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuSpecs.MenuSpec;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuSpecs.SlotAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Collections;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class PasterDreamBlockEntity extends BlockEntity
	implements WorldlyContainer, SidedStorageBlockEntity, GeoAnimatable
{
	public static final int SLOT_COUNT = 27;
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
	private final NonNullList<ItemStack> items;
	private final CompoundTag stateData = new CompoundTag();
	private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);

	public PasterDreamBlockEntity(BlockPos pos, BlockState state) {
		super(PasterDreamBlockEntities.getType(state), pos, state);
		MenuSpec spec = menuSpec();
		int capacity = spec == null ? SLOT_COUNT : Math.max(1, spec.capacity());
		items = NonNullList.withSize(capacity, ItemStack.EMPTY);
	}

	@Override
	public int getContainerSize() {
		return items.size();
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
		return ContainerHelper.takeItem(items, slot);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		items.set(slot, stack);
		int max = getMaxStackSize(stack);
		if (stack.getCount() > max) {
			stack.setCount(max);
		}
		setChanged();
	}

	@Override
	public boolean stillValid(Player player) {
		return Container.stillValidBlockEntity(this, player);
	}

	@Override
	public void clearContent() {
		Collections.fill(items, ItemStack.EMPTY);
		setChanged();
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		MenuSpec spec = menuSpec();
		if (spec != null) {
			return spec.slots().stream().mapToInt(slot -> slot.index()).toArray();
		}
		int[] slots = new int[items.size()];
		for (int index = 0; index < slots.length; index++) {
			slots[index] = index;
		}
		return slots;
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
		return canPlaceItem(slot, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
		return true;
	}

	@Override
	public Storage<ItemVariant> getItemStorage(Direction side) {
		return InventoryStorage.of(this, side);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(
			this,
			"idle",
			5,
			state -> state.setAndContinue(IDLE)
		));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return animationCache;
	}

	@Override
	public double getTick(Object object) {
		return level == null ? 0.0D : level.getGameTime();
	}

	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		MenuSpec spec = menuSpec();
		if (spec == null) {
			return true;
		}
		for (var slotSpec : spec.slots()) {
			if (slotSpec.index() == slot) {
				return slotSpec.access() != SlotAccess.OUTPUT && slotSpec.access() != SlotAccess.DISPLAY;
			}
		}
		return false;
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		Collections.fill(items, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag, items, registries);
		if (tag.contains("pasterdream_state", CompoundTag.TAG_COMPOUND)) {
			CompoundTag state = tag.getCompound("pasterdream_state").copy();
			stateData.getAllKeys().forEach(stateData::remove);
			stateData.merge(state);
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		ContainerHelper.saveAllItems(tag, items, registries);
		tag.put("pasterdream_state", stateData.copy());
	}

	public CompoundTag stateData() {
		return stateData;
	}

	public void markStateChanged() {
		setChanged();
		if (level != null) {
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
		}
	}

	private MenuSpec menuSpec() {
		var blockId = BuiltInRegistries.BLOCK.getKey(getBlockState().getBlock());
		return PasterDreamMenuSpecs.blockSpec(blockId.getPath());
	}
}
