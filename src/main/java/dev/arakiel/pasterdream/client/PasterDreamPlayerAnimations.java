package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.content.PasterDreamContent;
import dev.arakiel.pasterdream.content.PasterDreamEffects;
import dev.kosmx.playerAnim.api.IPlayable;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import java.util.Optional;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;

public final class PasterDreamPlayerAnimations {
	private static final ResourceLocation LAYER = PasterDream.id("player_animation");

	private PasterDreamPlayerAnimations() {
	}

	public static void initialize() {
		PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
			LAYER,
			1000,
			player -> new ModifierLayer<>()
		);
		ClientTickEvents.END_CLIENT_TICK.register(client -> tick(client.player));
	}

	private static void tick(AbstractClientPlayer player) {
		if (player == null) {
			return;
		}
		IAnimation animation = PlayerAnimationAccess.getPlayerAssociatedData(player).get(LAYER);
		if (!(animation instanceof ModifierLayer<?> layer)) {
			return;
		}
		boolean evading = player.hasEffect(
			PasterDreamContent.effectHolder(PasterDreamEffects.EVASION_BUFF)
		);
		if (evading && !layer.isActive()) {
			Optional<IPlayable> source = PlayerAnimationRegistry.getAnimationOptional(
				PasterDream.id("evasion")
			);
			source.ifPresent(value -> setAnimation(layer, value));
		}
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private static void setAnimation(ModifierLayer<?> layer, IPlayable source) {
		((ModifierLayer) layer).setAnimation(new KeyframeAnimationPlayer(
			(dev.kosmx.playerAnim.core.data.KeyframeAnimation) source
		));
	}
}
