package dev.arakiel.pasterdream.content;

import dev.arakiel.pasterdream.gameplay.PasterDreamBlockBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class PasterDreamBehaviorBlock extends Block {
	private final String blockId;

	public PasterDreamBehaviorBlock(Properties properties, String blockId) {
		super(properties);
		this.blockId = blockId;
	}

	@Override
	public void onPlace(
		BlockState state,
		Level level,
		BlockPos pos,
		BlockState oldState,
		boolean movedByPiston
	) {
		super.onPlace(state, level, pos, oldState, movedByPiston);
		if (!level.isClientSide()) {
			level.scheduleTick(pos, this, 1);
			PasterDreamBlockBehavior.onPlaced(level, pos, state);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		PasterDreamBlockBehavior.scheduledTick(level, pos, state);
		if (level.getBlockState(pos).is(this)) {
			level.scheduleTick(pos, this, 20 + random.nextInt(40));
		}
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
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		super.entityInside(state, level, pos, entity);
		PasterDreamBlockBehavior.entityInside(level, pos, state, entity);
	}

	public String blockId() {
		return blockId;
	}
}
