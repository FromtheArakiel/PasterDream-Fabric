package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.content.PasterDreamMob;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public final class PasterDreamGeoEntityRenderer extends GeoEntityRenderer<PasterDreamMob> {
	public PasterDreamGeoEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new PasterDreamGeoEntityModel());
	}
}
