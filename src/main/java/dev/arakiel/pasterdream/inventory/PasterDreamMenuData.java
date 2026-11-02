package dev.arakiel.pasterdream.inventory;

import net.minecraft.core.BlockPos;

public record PasterDreamMenuData(
	BlockPos pos,
	int playerSlot,
	PasterDreamMenuBacking backing
) {
	public static PasterDreamMenuData block(BlockPos pos) {
		return new PasterDreamMenuData(pos, -1, PasterDreamMenuBacking.BLOCK);
	}

	public static PasterDreamMenuData playerSlot(int slot) {
		return new PasterDreamMenuData(BlockPos.ZERO, slot, PasterDreamMenuBacking.PLAYER_SLOT);
	}

	public static PasterDreamMenuData virtualMenu() {
		return new PasterDreamMenuData(BlockPos.ZERO, -1, PasterDreamMenuBacking.VIRTUAL);
	}

	public static PasterDreamMenuData virtualMenu(int playerSlot) {
		return new PasterDreamMenuData(BlockPos.ZERO, playerSlot, PasterDreamMenuBacking.VIRTUAL);
	}
}
