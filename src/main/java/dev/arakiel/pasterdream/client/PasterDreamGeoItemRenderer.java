package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.item.PasterDreamGeoItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public final class PasterDreamGeoItemRenderer extends GeoItemRenderer<PasterDreamGeoItem> {
	public PasterDreamGeoItemRenderer() {
		super(new PasterDreamGeoItemModel());
	}
}
