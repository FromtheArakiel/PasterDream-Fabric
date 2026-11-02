package dev.arakiel.pasterdream.item;

import dev.arakiel.pasterdream.client.PasterDreamGeoItemRenderer;
import java.util.function.Consumer;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class PasterDreamGeoItem extends Item implements GeoItem {
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
	private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);

	public PasterDreamGeoItem(Properties properties) {
		super(properties);
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
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new GeoRenderProvider() {
			private PasterDreamGeoItemRenderer renderer;

			@Override
			public net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
				if (renderer == null) {
					renderer = new PasterDreamGeoItemRenderer();
				}
				return renderer;
			}
		});
	}
}
