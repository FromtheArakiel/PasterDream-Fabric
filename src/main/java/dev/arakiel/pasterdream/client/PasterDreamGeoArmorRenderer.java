package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.item.PasterDreamArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public final class PasterDreamGeoArmorRenderer extends GeoArmorRenderer<PasterDreamArmorItem> {
	public PasterDreamGeoArmorRenderer() {
		super(new PasterDreamGeoArmorModel());
	}
}
