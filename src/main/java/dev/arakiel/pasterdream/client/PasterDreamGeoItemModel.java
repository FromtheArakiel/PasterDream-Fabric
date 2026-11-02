package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.item.PasterDreamGeoItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("deprecation")
public final class PasterDreamGeoItemModel extends GeoModel<PasterDreamGeoItem> {
	@Override
	public ResourceLocation getModelResource(PasterDreamGeoItem animatable) {
		return PasterDream.id("geo/" + path(animatable) + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PasterDreamGeoItem animatable) {
		return PasterDream.id("textures/item/" + path(animatable) + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(PasterDreamGeoItem animatable) {
		return PasterDream.id("animations/" + path(animatable) + ".animation.json");
	}

	private static String path(PasterDreamGeoItem animatable) {
		return BuiltInRegistries.ITEM.getKey(animatable).getPath();
	}
}
