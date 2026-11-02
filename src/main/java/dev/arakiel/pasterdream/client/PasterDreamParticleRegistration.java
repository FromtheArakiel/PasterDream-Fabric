package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.PasterDream;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public final class PasterDreamParticleRegistration {
	private PasterDreamParticleRegistration() {
	}

	public static void register() {
		for (var particleType : BuiltInRegistries.PARTICLE_TYPE) {
			var id = BuiltInRegistries.PARTICLE_TYPE.getKey(particleType);
			if (id != null
				&& PasterDream.MOD_ID.equals(id.getNamespace())
				&& particleType instanceof SimpleParticleType simpleParticle) {
				ParticleFactoryRegistry.getInstance().register(
					simpleParticle,
					PasterDreamParticle::provider
				);
			}
		}
	}
}
