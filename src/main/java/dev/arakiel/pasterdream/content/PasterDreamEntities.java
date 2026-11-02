package dev.arakiel.pasterdream.content;

import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.gemini.registration.container.EntityRegistryContainer;
import dev.arakiel.pasterdream.PasterDream;
import java.lang.reflect.Field;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamEntities implements EntityRegistryContainer {
    @AssignedName("pink_chicken")
    public static final EntityType<PasterDreamMob> PINK_CHICKEN = create("pink_chicken", MobCategory.CREATURE, false, 0.4F, 0.7F);
    @AssignedName("pink_slime")
    public static final EntityType<PasterDreamMob> PINK_SLIME = create("pink_slime", MobCategory.CREATURE, false, 0.5F, 0.5F);
    @AssignedName("basalt_snail")
    public static final EntityType<PasterDreamMob> BASALT_SNAIL = create("basalt_snail", MobCategory.CREATURE, true, 0.6F, 1.8F);
    @AssignedName("spore_entity")
    public static final EntityType<PasterDreamMob> SPORE_ENTITY = create("spore_entity", MobCategory.CREATURE, false, 0.2F, 0.2F);
    @AssignedName("shadow_golem")
    public static final EntityType<PasterDreamMob> SHADOW_GOLEM = create("shadow_golem", MobCategory.MONSTER, false, 2.2F, 3.5F);
    @AssignedName("terrorbeak")
    public static final EntityType<PasterDreamMob> TERRORBEAK = create("terrorbeak", MobCategory.MONSTER, true, 1.8F, 4.0F);
    @AssignedName("shadow_hand")
    public static final EntityType<PasterDreamMob> SHADOW_HAND = create("shadow_hand", MobCategory.MONSTER, true, 0.6F, 0.8F);
    @AssignedName("crazy_terrorbeak")
    public static final EntityType<PasterDreamMob> CRAZY_TERRORBEAK = create("crazy_terrorbeak", MobCategory.MONSTER, true, 1.8F, 4.0F);
    @AssignedName("shadow_ghost")
    public static final EntityType<PasterDreamMob> SHADOW_GHOST = create("shadow_ghost", MobCategory.MONSTER, true, 0.7F, 1.2F);
    @AssignedName("shadow_squeal_ghost")
    public static final EntityType<PasterDreamMob> SHADOW_SQUEAL_GHOST = create("shadow_squeal_ghost", MobCategory.MONSTER, true, 0.7F, 1.2F);
    @AssignedName("shadow_squeal_ghost_0")
    public static final EntityType<PasterDreamMob> SHADOW_SQUEAL_GHOST_0 = create("shadow_squeal_ghost_0", MobCategory.MONSTER, true, 0.8F, 1.3F);
    @AssignedName("friendly_ghost")
    public static final EntityType<PasterDreamMob> FRIENDLY_GHOST = create("friendly_ghost", MobCategory.MONSTER, true, 0.7F, 1.2F);
    @AssignedName("black_beetle")
    public static final EntityType<PasterDreamMob> BLACK_BEETLE = create("black_beetle", MobCategory.MONSTER, false, 0.6F, 0.5F);
    @AssignedName("black_beetle_mother")
    public static final EntityType<PasterDreamMob> BLACK_BEETLE_MOTHER = create("black_beetle_mother", MobCategory.MONSTER, false, 2.0F, 1.0F);
    @AssignedName("shadow_npc_0")
    public static final EntityType<PasterDreamMob> SHADOW_NPC_0 = create("shadow_npc_0", MobCategory.CREATURE, true, 0.6F, 1.8F);
    @AssignedName("weakeness_terrorbeak")
    public static final EntityType<PasterDreamMob> WEAKENESS_TERRORBEAK = create("weakeness_terrorbeak", MobCategory.MONSTER, true, 1.5F, 3.0F);
    @AssignedName("aaroncos_lefthand_0")
    public static final EntityType<PasterDreamMob> AARONCOS_LEFTHAND_0 = create("aaroncos_lefthand_0", MobCategory.MONSTER, true, 3.5F, 3.5F);
    @AssignedName("aaroncos_righthand_0")
    public static final EntityType<PasterDreamMob> AARONCOS_RIGHTHAND_0 = create("aaroncos_righthand_0", MobCategory.MONSTER, true, 3.5F, 3.5F);
    @AssignedName("shadow_tune_totem")
    public static final EntityType<PasterDreamMob> SHADOW_TUNE_TOTEM = create("shadow_tune_totem", MobCategory.MONSTER, true, 2.0F, 8.0F);
    @AssignedName("shaking_crystal")
    public static final EntityType<PasterDreamMob> SHAKING_CRYSTAL = create("shaking_crystal", MobCategory.MONSTER, true, 0.6F, 1.8F);
    @AssignedName("bone_wing")
    public static final EntityType<PasterDreamMob> BONE_WING = create("bone_wing", MobCategory.MONSTER, false, 1.7F, 0.8F);
    @AssignedName("ash_bone_wing")
    public static final EntityType<PasterDreamMob> ASH_BONE_WING = create("ash_bone_wing", MobCategory.MONSTER, false, 1.7F, 0.8F);
    @AssignedName("firefly")
    public static final EntityType<PasterDreamMob> FIREFLY = create("firefly", MobCategory.MONSTER, false, 0.5F, 0.5F);
    @AssignedName("thundercloud")
    public static final EntityType<PasterDreamMob> THUNDERCLOUD = create("thundercloud", MobCategory.MONSTER, false, 1.0F, 0.8F);
    @AssignedName("highvoltage_thundercloud")
    public static final EntityType<PasterDreamMob> HIGHVOLTAGE_THUNDERCLOUD = create("highvoltage_thundercloud", MobCategory.MONSTER, true, 1.4F, 1.1F);
    @AssignedName("jellyfish")
    public static final EntityType<PasterDreamMob> JELLYFISH = create("jellyfish", MobCategory.MONSTER, false, 0.7F, 1.4F);
    @AssignedName("wind_knight")
    public static final EntityType<PasterDreamMob> WIND_KNIGHT = create("wind_knight", MobCategory.MONSTER, true, 1.0F, 2.0F);
    @AssignedName("golden_fox")
    public static final EntityType<PasterDreamMob> GOLDEN_FOX = create("golden_fox", MobCategory.MONSTER, true, 0.6F, 0.6F);
    @AssignedName("meltdream_crystal_entity")
    public static final EntityType<PasterDreamMob> MELTDREAM_CRYSTAL_ENTITY = create("meltdream_crystal_entity", MobCategory.CREATURE, true, 0.6F, 1.0F);
    @AssignedName("terrasword_wave")
    public static final EntityType<PasterDreamMob> TERRASWORD_WAVE = create("terrasword_wave", MobCategory.MONSTER, true, 0.1F, 0.1F);
    @AssignedName("shadow_magicball")
    public static final EntityType<PasterDreamMob> SHADOW_MAGICBALL = create("shadow_magicball", MobCategory.MONSTER, true, 1.0F, 1.5F);
    @AssignedName("healing_spell_entity")
    public static final EntityType<PasterDreamMob> HEALING_SPELL_ENTITY = create("healing_spell_entity", MobCategory.MONSTER, true, 0.1F, 0.1F);
    @AssignedName("projectile_pinkegg_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_PINKEGG_PROJECTILE = createProjectile("projectile_pinkegg_projectile");
    @AssignedName("projectile_moltengold_wand_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_MOLTENGOLD_WAND_PROJECTILE = createProjectile("projectile_moltengold_wand_projectile");
    @AssignedName("projectile_true_moltengold_wand_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_TRUE_MOLTENGOLD_WAND_PROJECTILE = createProjectile("projectile_true_moltengold_wand_projectile");
    @AssignedName("projectile_truest_moltengold_wand_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_TRUEST_MOLTENGOLD_WAND_PROJECTILE = createProjectile("projectile_truest_moltengold_wand_projectile");
    @AssignedName("projectile_squeal_wave_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_SQUEAL_WAVE_PROJECTILE = createProjectile("projectile_squeal_wave_projectile");
    @AssignedName("projectile_white_sword_rain_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_WHITE_SWORD_RAIN_PROJECTILE = createProjectile("projectile_white_sword_rain_projectile");
    @AssignedName("projectile_squeal_wave_wand_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_SQUEAL_WAVE_WAND_PROJECTILE = createProjectile("projectile_squeal_wave_wand_projectile");
    @AssignedName("projectile_lightning_spell_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_LIGHTNING_SPELL_PROJECTILE = createProjectile("projectile_lightning_spell_projectile");
    @AssignedName("projectile_shadow_vortex_book_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_SHADOW_VORTEX_BOOK_PROJECTILE = createProjectile("projectile_shadow_vortex_book_projectile");
    @AssignedName("projectile_poison_spell_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_POISON_SPELL_PROJECTILE = createProjectile("projectile_poison_spell_projectile");
    @AssignedName("projectile_healing_spell_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_HEALING_SPELL_PROJECTILE = createProjectile("projectile_healing_spell_projectile");
    @AssignedName("projectile_bone_wing_fire_ball_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_BONE_WING_FIRE_BALL_PROJECTILE = createProjectile("projectile_bone_wing_fire_ball_projectile");
    @AssignedName("projectile_lightning_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_LIGHTNING_PROJECTILE = createProjectile("projectile_lightning_projectile");
    @AssignedName("projectile_fury_spell_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_FURY_SPELL_PROJECTILE = createProjectile("projectile_fury_spell_projectile");
    @AssignedName("fury_spell_entity")
    public static final EntityType<PasterDreamMob> FURY_SPELL_ENTITY = create("fury_spell_entity", MobCategory.MISC, true, 2.0F, 0.1F);
    @AssignedName("projectile_ice_spell_projectile")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_ICE_SPELL_PROJECTILE = createProjectile("projectile_ice_spell_projectile");
    @AssignedName("projectile_strawberry_heart_project")
    public static final EntityType<PasterDreamProjectile> PROJECTILE_STRAWBERRY_HEART_PROJECT = createProjectile("projectile_strawberry_heart_project");
    @AssignedName("fox_fire")
    public static final EntityType<PasterDreamMob> FOX_FIRE = create("fox_fire", MobCategory.MONSTER, true, 1.0F, 0.1F);
    @AssignedName("small_stone_spirit")
    public static final EntityType<PasterDreamMob> SMALL_STONE_SPIRIT = create("small_stone_spirit", MobCategory.MONSTER, false, 0.7F, 1.0F);

    private PasterDreamEntities() {
    }

    public static void register() {
        GeminiRegistration.entities(PasterDreamEntities.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void postProcessField(String namespace, EntityType<?> value, String identifier, Field field) {
        if (identifier.startsWith("projectile_")) {
            return;
        }
        FabricDefaultAttributeRegistry.register(
            (EntityType<? extends LivingEntity>) (EntityType<?>) value,
            PasterDreamEntityAttributes.create(identifier)
        );
    }

    public static EntityType<?> get(String id) {
        return BuiltInRegistries.ENTITY_TYPE.getOptional(PasterDream.id(id))
            .orElseThrow(() -> new IllegalArgumentException("Unknown PasterDream entity: " + id));
    }

    private static EntityType<PasterDreamMob> create(
        String id, MobCategory category, boolean fireImmune, float width, float height
    ) {
        EntityType.Builder<PasterDreamMob> builder = EntityType.Builder
            .of(PasterDreamMob::new, category)
            .clientTrackingRange(64)
            .updateInterval(3)
            .sized(width, height);
        if (fireImmune) {
            builder.fireImmune();
        }
        return builder.build(null);
    }

    private static EntityType<PasterDreamProjectile> createProjectile(String id) {
        return EntityType.Builder
            .of(PasterDreamProjectile::new, MobCategory.MISC)
            .clientTrackingRange(64)
            .updateInterval(1)
            .sized(0.5F, 0.5F)
            .build(null);
    }
}
