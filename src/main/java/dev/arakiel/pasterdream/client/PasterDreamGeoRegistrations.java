package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.content.PasterDreamBlockEntities;
import dev.arakiel.pasterdream.content.PasterDreamBlockEntity;
import dev.arakiel.pasterdream.content.PasterDreamEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class PasterDreamGeoRegistrations {
	private PasterDreamGeoRegistrations() {
	}

	@SuppressWarnings("unchecked")
	public static void register() {
		EntityRendererRegistry.register(PasterDreamEntities.AARONCOS_LEFTHAND_0, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.AARONCOS_RIGHTHAND_0, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.ASH_BONE_WING, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.BASALT_SNAIL, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.BLACK_BEETLE, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.BLACK_BEETLE_MOTHER, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.BONE_WING, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.CRAZY_TERRORBEAK, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.FIREFLY, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.FOX_FIRE, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.FRIENDLY_GHOST, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.FURY_SPELL_ENTITY, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.GOLDEN_FOX, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.HEALING_SPELL_ENTITY, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.HIGHVOLTAGE_THUNDERCLOUD, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.JELLYFISH, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.MELTDREAM_CRYSTAL_ENTITY, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_FURY_SPELL_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_HEALING_SPELL_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_SHADOW_VORTEX_BOOK_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_SQUEAL_WAVE_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_SQUEAL_WAVE_WAND_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_WHITE_SWORD_RAIN_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.SHADOW_GHOST, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.SHADOW_GOLEM, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.SHADOW_HAND, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.SHADOW_MAGICBALL, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.SHADOW_NPC_0, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.SHADOW_SQUEAL_GHOST, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.SHADOW_SQUEAL_GHOST_0, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.SHADOW_TUNE_TOTEM, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.SHAKING_CRYSTAL, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.SMALL_STONE_SPIRIT, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.TERRASWORD_WAVE, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.TERRORBEAK, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.THUNDERCLOUD, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.WEAKENESS_TERRORBEAK, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.WIND_KNIGHT, PasterDreamGeoEntityRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PINK_CHICKEN, NoopRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PINK_SLIME, NoopRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_BONE_WING_FIRE_BALL_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_ICE_SPELL_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_LIGHTNING_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_LIGHTNING_SPELL_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_MOLTENGOLD_WAND_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_PINKEGG_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_POISON_SPELL_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_STRAWBERRY_HEART_PROJECT, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_TRUE_MOLTENGOLD_WAND_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.PROJECTILE_TRUEST_MOLTENGOLD_WAND_PROJECTILE, PasterDreamProjectileRenderer::new);
		EntityRendererRegistry.register(PasterDreamEntities.SPORE_ENTITY, NoopRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.AARONCOS_HAND_CHEST, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.AARONCOS_HAND_SPAWN_BLOCK, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.BIRDS_NEST, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.BROKEN_SHADOW_DUNGEON_PORTAL, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.DESERT_HERO_TOMB, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.DREAM_ACCUMULATOR, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.DREAM_CAULDRON, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.ECOLOGY_GLASS_JAR, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.FORCED_TOWER, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.GOLDEN_FOX_SCULPTURE, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.GUARD_CRYSTAL, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.LIFE_CRYSTAL, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.PICNIC_BASKET, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.QYM_DOLL_0, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.RESEARCH_TABLE, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.SHADOW_BLAST_FURNACE, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.SHADOW_BRAZIER, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.SHADOW_CHEST, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.SHADOW_DUNGEON_PORTAL, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.SHADOW_TRAP_0, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.SHADOW_VORTEX, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.TWILIGHT_LANTERN, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.UUZ_DOLL_0, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.WEAPON_TABLE, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.WEAPON_WORKSHOP, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.WIND_KNIGHT_SPAWNBLOCK_0, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.WIND_KNIGHT_SPAWNBLOCK_1, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.WIND_KNIGHT_SPAWNBLOCK_2, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.WIND_KNIGHT_SPAWNBLOCK_3, PasterDreamGeoBlockRenderer::new);
		BlockEntityRenderers.register((BlockEntityType<PasterDreamBlockEntity>) (BlockEntityType<?>) PasterDreamBlockEntities.WIND_KNIGHT_SPAWNBLOCK_4, PasterDreamGeoBlockRenderer::new);
	}
}
