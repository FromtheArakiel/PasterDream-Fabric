package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.content.PasterDreamProjectile;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public final class PasterDreamProjectileRenderer extends ArrowRenderer<PasterDreamProjectile> {
	private static final ResourceLocation TEXTURE =
		ResourceLocation.withDefaultNamespace("textures/entity/projectiles/arrow.png");

	public PasterDreamProjectileRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(PasterDreamProjectile projectile) {
		return TEXTURE;
	}
}
