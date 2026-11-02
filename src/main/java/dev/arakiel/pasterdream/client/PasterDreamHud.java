package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.component.PasterDreamPlayerState;
import dev.arakiel.pasterdream.config.PasterDreamClientConfig;
import dev.arakiel.pasterdream.config.PasterDreamConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public final class PasterDreamHud {
	private PasterDreamHud() {
	}

	public static void register() {
		HudRenderCallback.EVENT.register(PasterDreamHud::render);
	}

	private static void render(GuiGraphics graphics, DeltaTracker delta) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.player == null || minecraft.options.hideGui || minecraft.player.isSpectator()) {
			return;
		}
		PasterDreamClientConfig config = PasterDreamConfig.client();
		if (config.showHudOnlyWhileSneaking && !minecraft.player.isShiftKeyDown()) {
			return;
		}
		var san = PasterDreamPlayerState.san(minecraft.player);
		if (!san.enabled()) {
			return;
		}
		int center = graphics.guiWidth() / 2;
		int sanX = center + (int) config.sanX;
		int sanY = graphics.guiHeight() + (int) config.sanY;
		int energyX = center + (int) config.meltdreamEnergyX;
		int energyY = graphics.guiHeight() + (int) config.meltdreamEnergyY;
		drawBar(
			graphics,
			sanX,
			sanY,
			san.value(),
			0xFFF4D35E,
			0xFF392F18,
			"S"
		);
		drawBar(
			graphics,
			energyX,
			energyY,
			PasterDreamPlayerState.meltDreamEnergy(minecraft.player).value(),
			0xFF4CC9F0,
			0xFF17323B,
			"E"
		);
	}

	private static void drawBar(
		GuiGraphics graphics,
		int x,
		int y,
		double value,
		int fillColor,
		int backgroundColor,
		String label
	) {
		int width = 52;
		int height = 5;
		int filled = (int) Math.round(width * Math.max(0.0D, Math.min(value, 100.0D)) / 100.0D);
		graphics.fill(x, y, x + width, y + height, backgroundColor);
		graphics.fill(x, y, x + filled, y + height, fillColor);
		graphics.drawString(
			Minecraft.getInstance().font,
			label + Math.round(value),
			x,
			y - 9,
			0xFFFFFFFF,
			true
		);
	}
}
