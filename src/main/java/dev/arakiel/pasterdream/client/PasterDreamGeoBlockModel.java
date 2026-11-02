package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.content.PasterDreamBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("deprecation")
public final class PasterDreamGeoBlockModel extends GeoModel<PasterDreamBlockEntity> {
	@Override
	public ResourceLocation getModelResource(PasterDreamBlockEntity animatable) {
		return PasterDream.id("geo/" + path(animatable) + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PasterDreamBlockEntity animatable) {
		return PasterDream.id("textures/block/" + path(animatable) + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(PasterDreamBlockEntity animatable) {
		return PasterDream.id("animations/" + path(animatable) + ".animation.json");
	}

	private static String path(PasterDreamBlockEntity animatable) {
		return BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock()).getPath();
	}
}
