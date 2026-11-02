package dev.arakiel.pasterdream.content;

import dev.arakiel.pasterdream.gameplay.PasterDreamBlockBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class PasterDreamLeafBehaviorBlock extends LeavesBlock {
	private final String blockId;

	public PasterDreamLeafBehaviorBlock(Properties properties, String blockId) {
		super(properties);
		this.blockId = blockId;
	}

	@Override
	public void neighborChanged(
		BlockState state,
		Level level,
		BlockPos pos,
		Block neighborBlock,
		BlockPos neighborPos,
		boolean movedByPiston
	) {
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
		PasterDreamBlockBehavior.neighborChanged(level, pos, state);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		PasterDreamBlockBehavior.scheduledTick(level, pos, state);
	}

	public String blockId() {
		return blockId;
	}
}
