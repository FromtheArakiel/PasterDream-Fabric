package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.pasterdream.PasterDream;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public final class PasterDreamBlockEvents {
	private static final TagKey<Block> SHEARABLE =
		TagKey.create(Registries.BLOCK, PasterDream.id("mineable/shears"));

	private PasterDreamBlockEvents() {
	}

	public static void register() {
		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
			if (world.isClientSide() || player.isCreative()) {
				return;
			}
			if (state.is(SHEARABLE) && player.getMainHandItem().is(Items.SHEARS)) {
				Block.popResource(world, pos, new ItemStack(state.getBlock()));
			}
		});
	}
}
