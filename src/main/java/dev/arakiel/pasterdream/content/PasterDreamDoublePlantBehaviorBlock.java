package dev.arakiel.pasterdream.content;

import dev.arakiel.pasterdream.gameplay.PasterDreamBlockBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;

public final class PasterDreamDoublePlantBehaviorBlock extends DoublePlantBlock {
	private final String blockId;

	public PasterDreamDoublePlantBehaviorBlock(Properties properties, String blockId) {
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
		PasterDreamBlockBehavior.onPlaced(level, pos, state);
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
