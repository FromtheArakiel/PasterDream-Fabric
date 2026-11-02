package dev.arakiel.pasterdream.content;

import dev.arakiel.gemini.creative.GeminiCreativeTabs;
import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.gameplay.PasterDreamAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class PasterDreamContent {
	private PasterDreamContent() {
	}

	public static void initialize() {
		PasterDreamAttributes.register();
		PasterDreamBlocks.register();
		PasterDreamTreeDecorators.register();
		PasterDreamEntities.register();
		PasterDreamArmorMaterials.register();
		PasterDreamItems.register();
		PasterDreamBlockEntities.register();
		PasterDreamEffects.register();
		PasterDreamSounds.register();
		PasterDreamParticles.register();
		PasterDreamFluids.register();
		PasterDreamMenus.register();
		PasterDreamRecipeSerializers.register();
		PasterDreamRecipeTypes.register();
		registerCreativeTab();
	}

	public static Holder<MobEffect> effectHolder(MobEffect effect) {
		return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
	}

	private static void registerCreativeTab() {
		Item icon = PasterDreamItems.get("meltdream_crystal");
		GeminiCreativeTabs.register(
			PasterDream.id("main"),
			Component.translatable("itemGroup.pasterdream"),
			() -> new ItemStack(icon),
			output -> {
				for (Item item : PasterDreamItems.all().values()) {
					output.accept(item);
				}
			}
		);
	}
}
