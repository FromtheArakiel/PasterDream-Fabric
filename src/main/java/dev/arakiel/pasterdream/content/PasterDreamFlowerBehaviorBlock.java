package dev.arakiel.pasterdream.content;

import dev.arakiel.pasterdream.gameplay.PasterDreamBlockBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class PasterDreamFlowerBehaviorBlock extends FlowerBlock {
	private final String blockId;

	public PasterDreamFlowerBehaviorBlock(
		Holder<MobEffect> effect,
		float seconds,
		Properties properties,
		String blockId
	) {
		super(effect, seconds, properties);
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
			level.scheduleTick(pos, this, 20);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		PasterDreamBlockBehavior.scheduledTick(level, pos, state);
		level.scheduleTick(pos, this, 20 + random.nextInt(20));
	}

	@Override
	public void randomTick(
		BlockState state,
		ServerLevel level,
		BlockPos pos,
		RandomSource random
	) {
		PasterDreamBlockBehavior.scheduledTick(level, pos, state);
	}

	public String blockId() {
		return blockId;
	}
}
