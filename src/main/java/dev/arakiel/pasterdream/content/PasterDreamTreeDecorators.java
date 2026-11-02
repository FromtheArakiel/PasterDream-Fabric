package dev.arakiel.pasterdream.content;

import com.mojang.serialization.MapCodec;
import dev.arakiel.gemini.registration.AutoRegistryContainer;
import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.pasterdream.PasterDream;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamTreeDecorators implements AutoRegistryContainer<TreeDecoratorType<?>> {
	@AssignedName("biome_dyedream_0_tree_leave_decorator")
	public static final TreeDecoratorType<NoopTreeDecorator> DYEDREAM_0_LEAVES = create();
	@AssignedName("biome_dyedream_0_tree_trunk_decorator")
	public static final TreeDecoratorType<NoopTreeDecorator> DYEDREAM_0_TRUNK = create();
	@AssignedName("biome_dyedream_1_tree_leave_decorator")
	public static final TreeDecoratorType<NoopTreeDecorator> DYEDREAM_1_LEAVES = create();
	@AssignedName("biome_dyedream_1_tree_trunk_decorator")
	public static final TreeDecoratorType<NoopTreeDecorator> DYEDREAM_1_TRUNK = create();
	@AssignedName("biome_dyedream_2_tree_leave_decorator")
	public static final TreeDecoratorType<NoopTreeDecorator> DYEDREAM_2_LEAVES = create();
	@AssignedName("biome_dyedream_2_tree_trunk_decorator")
	public static final TreeDecoratorType<NoopTreeDecorator> DYEDREAM_2_TRUNK = create();

	private PasterDreamTreeDecorators() {
	}

	public static void register() {
		GeminiRegistration.register(PasterDreamTreeDecorators.class, PasterDream.MOD_ID);
	}

	@Override
	public Registry<TreeDecoratorType<?>> getRegistry() {
		return BuiltInRegistries.TREE_DECORATOR_TYPE;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<TreeDecoratorType<?>> getTargetFieldType() {
		return (Class<TreeDecoratorType<?>>) (Class<?>) TreeDecoratorType.class;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static TreeDecoratorType<NoopTreeDecorator> create() {
		TreeDecoratorType[] holder = new TreeDecoratorType[1];
		holder[0] = new TreeDecoratorType(MapCodec.unit(new NoopTreeDecorator(() -> holder[0])));
		return holder[0];
	}

	private static final class NoopTreeDecorator extends TreeDecorator {
		private final java.util.function.Supplier<TreeDecoratorType<?>> type;

		private NoopTreeDecorator(java.util.function.Supplier<TreeDecoratorType<?>> type) {
			this.type = type;
		}

		@Override
		protected TreeDecoratorType<?> type() {
			return type.get();
		}

		@Override
		public void place(TreeDecorator.Context context) {
		}
	}
}
