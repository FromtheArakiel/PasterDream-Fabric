package dev.arakiel.pasterdream.component;

import dev.arakiel.gemini.component.ComponentFactoryRegistries;
import dev.arakiel.gemini.component.ComponentInitializers;
import dev.arakiel.gemini.component.ComponentKey;
import dev.arakiel.gemini.component.GeminiComponents;
import dev.arakiel.gemini.component.RespawnCopyStrategy;
import dev.arakiel.gemini.component.StaticComponentInitializer;
import dev.arakiel.pasterdream.PasterDream;
import java.util.Collection;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public final class PasterDreamComponentRegistration implements
	StaticComponentInitializer,
	ComponentInitializers.Entity
{
	public static final ResourceLocation SAN_ID = PasterDream.id("san");
	public static final ResourceLocation MELT_DREAM_ENERGY_ID = PasterDream.id("meltdream_energy");
	public static final ResourceLocation COOLDOWNS_ID = PasterDream.id("cooldowns");
	public static final ResourceLocation WORLD_STATE_ID = PasterDream.id("world_state");

	private static ComponentKey<SanComponent> san;
	private static ComponentKey<MeltDreamEnergyComponent> meltDreamEnergy;
	private static ComponentKey<CooldownComponent> cooldowns;
	private static ComponentKey<PasterDreamWorldStateComponent> worldState;

	public static ComponentKey<SanComponent> san() {
		if (san == null) {
			san = GeminiComponents.key(SAN_ID, SanComponent.class);
		}
		return san;
	}

	public static ComponentKey<MeltDreamEnergyComponent> meltDreamEnergy() {
		if (meltDreamEnergy == null) {
			meltDreamEnergy = GeminiComponents.key(MELT_DREAM_ENERGY_ID, MeltDreamEnergyComponent.class);
		}
		return meltDreamEnergy;
	}

	public static ComponentKey<CooldownComponent> cooldowns() {
		if (cooldowns == null) {
			cooldowns = GeminiComponents.key(COOLDOWNS_ID, CooldownComponent.class);
		}
		return cooldowns;
	}

	public static ComponentKey<PasterDreamWorldStateComponent> worldState() {
		if (worldState == null) {
			worldState = GeminiComponents.key(WORLD_STATE_ID, PasterDreamWorldStateComponent.class);
		}
		return worldState;
	}

	@Override
	public Collection<ResourceLocation> getSupportedComponentKeys() {
		return List.of(SAN_ID, MELT_DREAM_ENERGY_ID, COOLDOWNS_ID, WORLD_STATE_ID);
	}

	@Override
	public void finalizeStaticBootstrap() {
		san();
		meltDreamEnergy();
		cooldowns();
		worldState();
	}

	@Override
	public void registerEntityComponentFactories(ComponentFactoryRegistries.Entity registry) {
		registry.registerForPlayers(
			san(),
			SanComponent::new,
			RespawnCopyStrategy.INVENTORY
		);
		registry.registerForPlayers(
			meltDreamEnergy(),
			MeltDreamEnergyComponent::new,
			RespawnCopyStrategy.INVENTORY
		);
		registry.registerForPlayers(
			cooldowns(),
			CooldownComponent::new,
			RespawnCopyStrategy.NEVER_COPY
		);
		registry.registerForPlayers(
			worldState(),
			PasterDreamWorldStateComponent::new,
			RespawnCopyStrategy.NEVER_COPY
		);
	}
}
