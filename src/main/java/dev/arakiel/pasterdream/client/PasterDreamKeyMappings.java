package dev.arakiel.pasterdream.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.arakiel.pasterdream.network.PasterDreamActions;
import dev.arakiel.pasterdream.network.PasterDreamNetworking;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public final class PasterDreamKeyMappings {
	public static final KeyMapping TELEPORTATION = new KeyMapping(
		"key.pasterdream.teleportation",
		InputConstants.Type.KEYSYM,
		GLFW.GLFW_KEY_C,
		"key.categories.pasterdream"
	);
	public static final KeyMapping CLOAK_ACTIVATION = new KeyMapping(
		"key.pasterdream.cloak_activate",
		InputConstants.Type.KEYSYM,
		GLFW.GLFW_KEY_Z,
		"key.categories.pasterdream"
	);

	private PasterDreamKeyMappings() {
	}

	public static void register() {
		KeyBindingHelper.registerKeyBinding(TELEPORTATION);
		KeyBindingHelper.registerKeyBinding(CLOAK_ACTIVATION);
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (TELEPORTATION.consumeClick()) {
				PasterDreamNetworking.MAIN.sendToServer(new PasterDreamActions.TeleportRequest());
			}
			while (CLOAK_ACTIVATION.consumeClick()) {
				PasterDreamNetworking.MAIN.sendToServer(new PasterDreamActions.CloakRequest());
			}
		});
	}
}
