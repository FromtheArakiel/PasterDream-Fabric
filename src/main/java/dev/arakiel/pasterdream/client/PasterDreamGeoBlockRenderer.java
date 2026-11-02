package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.content.PasterDreamBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public final class PasterDreamGeoBlockRenderer extends GeoBlockRenderer<PasterDreamBlockEntity> {
	public PasterDreamGeoBlockRenderer(BlockEntityRendererProvider.Context context) {
		super(new PasterDreamGeoBlockModel());
	}
}
