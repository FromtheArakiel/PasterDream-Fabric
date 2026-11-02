package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.item.PasterDreamArmorItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("deprecation")
public final class PasterDreamGeoArmorModel extends GeoModel<PasterDreamArmorItem> {
	@Override
	public ResourceLocation getModelResource(PasterDreamArmorItem animatable) {
		return PasterDream.id("geo/" + path(animatable) + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PasterDreamArmorItem animatable) {
		return PasterDream.id("textures/item/" + path(animatable) + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(PasterDreamArmorItem animatable) {
		return PasterDream.id("animations/" + path(animatable) + ".animation.json");
	}

	private static String path(PasterDreamArmorItem animatable) {
		String path = BuiltInRegistries.ITEM.getKey(animatable).getPath();
		return path.endsWith("_chestplate")
			? path.substring(0, path.length() - "_chestplate".length())
			: path;
	}
}
