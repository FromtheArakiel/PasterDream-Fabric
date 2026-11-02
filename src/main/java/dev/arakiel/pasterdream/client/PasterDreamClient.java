package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.content.PasterDreamMenus;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public final class PasterDreamClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		PasterDreamPlayerAnimations.initialize();
		PasterDreamGeoRegistrations.register();
		PasterDreamParticleRegistration.register();
		PasterDreamHud.register();
		PasterDreamKeyMappings.register();
		for (var menu : PasterDreamMenus.all()) {
			MenuScreens.register(menu, PasterDreamMenuScreen::new);
		}
	}
}
