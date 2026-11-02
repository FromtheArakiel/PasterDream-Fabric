package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.pasterdream.PasterDream;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.level.ServerPlayer;

public final class PasterDreamAdvancements {
	private PasterDreamAdvancements() {
	}

	public static boolean completed(ServerPlayer player, String id) {
		AdvancementHolder advancement = player.server.getAdvancements().get(PasterDream.id(id));
		return advancement != null
			&& player.getAdvancements().getOrStartProgress(advancement).isDone();
	}

	public static void award(ServerPlayer player, String id) {
		AdvancementHolder advancement = player.server.getAdvancements().get(PasterDream.id(id));
		if (advancement == null) {
			return;
		}
		var progress = player.getAdvancements().getOrStartProgress(advancement);
		for (String criterion : progress.getRemainingCriteria()) {
			player.getAdvancements().award(advancement, criterion);
		}
	}
}
