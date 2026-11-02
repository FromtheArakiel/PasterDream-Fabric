package dev.arakiel.pasterdream.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public final class PasterDreamParticle extends TextureSheetParticle {
	private PasterDreamParticle(
		ClientLevel level,
		double x,
		double y,
		double z,
		double velocityX,
		double velocityY,
		double velocityZ,
		SpriteSet sprites
	) {
		super(level, x, y, z, velocityX, velocityY, velocityZ);
		this.pickSprite(sprites);
		this.lifetime = 20 + random.nextInt(20);
		this.gravity = 0.01F;
		this.friction = 0.96F;
		this.quadSize *= 0.75F + random.nextFloat() * 0.5F;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	public static ParticleProvider<SimpleParticleType> provider(SpriteSet sprites) {
		return (type, level, x, y, z, velocityX, velocityY, velocityZ) ->
			new PasterDreamParticle(level, x, y, z, velocityX, velocityY, velocityZ, sprites);
	}
}
