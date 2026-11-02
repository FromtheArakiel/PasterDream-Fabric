package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.content.PasterDreamMob;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import java.util.Map;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("deprecation")
public final class PasterDreamGeoEntityModel extends GeoModel<PasterDreamMob> {
	private static final Map<String, String> ALIASES = Map.ofEntries(
		Map.entry("friendly_ghost", "shadow_ghost"),
		Map.entry("healing_spell_entity", "healing_spell"),
		Map.entry("shadow_squeal_ghost", "shadow_squeal_wave"),
		Map.entry("shadow_squeal_ghost_0", "shadow_squeal_wave"),
		Map.entry("shadow_tune_totem", "shadow_rune_totem"),
		Map.entry("weakeness_terrorbeak", "weakness_terrorbeak"),
		Map.entry("projectile_fury_spell_projectile", "fury_spell_entity"),
		Map.entry("projectile_healing_spell_projectile", "healing_spell"),
		Map.entry("projectile_shadow_vortex_book_projectile", "shadow_vortex"),
		Map.entry("projectile_squeal_wave_projectile", "shadow_squeal_wave"),
		Map.entry("projectile_squeal_wave_wand_projectile", "shadow_squeal_wave"),
		Map.entry("projectile_white_sword_rain_projectile", "terrasword_wave")
	);
	@Override
	public ResourceLocation getModelResource(PasterDreamMob animatable) {
		return PasterDream.id("geo/" + path(animatable) + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PasterDreamMob animatable) {
		return PasterDream.id("textures/entities/" + path(animatable) + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(PasterDreamMob animatable) {
		return PasterDream.id("animations/" + path(animatable) + ".animation.json");
	}

	private static String path(PasterDreamMob animatable) {
		String path = BuiltInRegistries.ENTITY_TYPE.getKey(animatable.getType()).getPath();
		return ALIASES.getOrDefault(path, path);
	}
}
