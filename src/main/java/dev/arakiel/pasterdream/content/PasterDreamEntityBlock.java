package dev.arakiel.pasterdream.content;

import dev.arakiel.pasterdream.gameplay.PasterDreamBlockBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;

public final class PasterDreamEntityBlock extends Block implements EntityBlock {
	private final String blockId;

	public PasterDreamEntityBlock(Properties properties, String blockId) {
		super(properties);
		this.blockId = blockId;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PasterDreamBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
		Level level,
		BlockState state,
		BlockEntityType<T> type
	) {
		if (level.isClientSide()) {
			return null;
		}
		return (tickLevel, pos, blockState, blockEntity) -> {
			if (blockEntity instanceof PasterDreamBlockEntity machine) {
				PasterDreamBlockBehavior.tick(machine);
			}
		};
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.INVISIBLE;
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

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		PasterDreamBlockBehavior.scheduledTick(level, pos, state);
	}

	public String blockId() {
		return blockId;
	}
}
