package dev.arakiel.pasterdream.content;

import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.gemini.registration.container.MobEffectRegistryContainer;
import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.gameplay.PasterDreamEffectFactory;
import dev.arakiel.pasterdream.gameplay.PasterDreamEffectBehavior;
import dev.arakiel.pasterdream.gameplay.PasterDreamResourceEffect;
import dev.arakiel.pasterdream.gameplay.PasterDreamRestEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamEffects implements MobEffectRegistryContainer {
    @AssignedName("dreamwish_buff")
    public static final MobEffect DREAMWISH_BUFF = create("dreamwish_buff");
    @AssignedName("dyedreamup_buff")
    public static final MobEffect DYEDREAMUP_BUFF = create("dyedreamup_buff");
    @AssignedName("sculk_armor_buff")
    public static final MobEffect SCULK_ARMOR_BUFF = create("sculk_armor_buff");
    @AssignedName("dyedream_armor_buff")
    public static final MobEffect DYEDREAM_ARMOR_BUFF = create("dyedream_armor_buff");
    @AssignedName("expup_buff")
    public static final MobEffect EXPUP_BUFF = create("expup_buff");
    @AssignedName("teleportation_buff")
    public static final MobEffect TELEPORTATION_BUFF = create("teleportation_buff");
    @AssignedName("fondillusion_buff")
    public static final MobEffect FONDILLUSION_BUFF = create("fondillusion_buff");
    @AssignedName("flareup_buff")
    public static final MobEffect FLAREUP_BUFF = create("flareup_buff");
    @AssignedName("rapid_reaction")
    public static final MobEffect RAPID_REACTION = create("rapid_reaction");
    @AssignedName("grail_buff")
    public static final MobEffect GRAIL_BUFF = create("grail_buff");
    @AssignedName("confusion_buff")
    public static final MobEffect CONFUSION_BUFF = create("confusion_buff");
    @AssignedName("insand_buff")
    public static final MobEffect INSAND_BUFF = create("insand_buff");
    @AssignedName("trance_buff")
    public static final MobEffect TRANCE_BUFF = create("trance_buff");
    @AssignedName("lethargy_buff")
    public static final MobEffect LETHARGY_BUFF = create("lethargy_buff");
    @AssignedName("cheerup_buff")
    public static final MobEffect CHEERUP_BUFF = create("cheerup_buff");
    @AssignedName("rest_buff")
    public static final MobEffect REST_BUFF = create("rest_buff");
    @AssignedName("rest_buff_in_dark")
    public static final MobEffect REST_BUFF_IN_DARK = create("rest_buff_in_dark");
    @AssignedName("cook_buff")
    public static final MobEffect COOK_BUFF = create("cook_buff");
    @AssignedName("guard_block_buff")
    public static final MobEffect GUARD_BLOCK_BUFF = create("guard_block_buff");
    @AssignedName("restrainmove_block_buff")
    public static final MobEffect RESTRAINMOVE_BLOCK_BUFF = create("restrainmove_block_buff");
    @AssignedName("goldenrod_tea_buff")
    public static final MobEffect GOLDENROD_TEA_BUFF = create("goldenrod_tea_buff");
    @AssignedName("shadow_silence_buff")
    public static final MobEffect SHADOW_SILENCE_BUFF = create("shadow_silence_buff");
    @AssignedName("boboji_buff")
    public static final MobEffect BOBOJI_BUFF = create("boboji_buff");
    @AssignedName("memento_buff")
    public static final MobEffect MEMENTO_BUFF = create("memento_buff");
    @AssignedName("snow_vow_buff")
    public static final MobEffect SNOW_VOW_BUFF = create("snow_vow_buff");
    @AssignedName("dyedream_perfume_buff")
    public static final MobEffect DYEDREAM_PERFUME_BUFF = create("dyedream_perfume_buff");
    @AssignedName("shadow_spyon_buff")
    public static final MobEffect SHADOW_SPYON_BUFF = create("shadow_spyon_buff");
    @AssignedName("cloudmist_buff")
    public static final MobEffect CLOUDMIST_BUFF = create("cloudmist_buff");
    @AssignedName("evasion_buff")
    public static final MobEffect EVASION_BUFF = create("evasion_buff");
    @AssignedName("turnback_cloak_buff")
    public static final MobEffect TURNBACK_CLOAK_BUFF = create("turnback_cloak_buff");
    @AssignedName("evasion_cloak_buff")
    public static final MobEffect EVASION_CLOAK_BUFF = create("evasion_cloak_buff");
    @AssignedName("machine_wing_effect")
    public static final MobEffect MACHINE_WING_EFFECT = create("machine_wing_effect");
    @AssignedName("counterattack_buff")
    public static final MobEffect COUNTERATTACK_BUFF = create("counterattack_buff");
    @AssignedName("dreamharp_of_wanderer_buff")
    public static final MobEffect DREAMHARP_OF_WANDERER_BUFF = create("dreamharp_of_wanderer_buff");
    @AssignedName("fury_spell_buff")
    public static final MobEffect FURY_SPELL_BUFF = create("fury_spell_buff");
    @AssignedName("ice_spell_buff")
    public static final MobEffect ICE_SPELL_BUFF = create("ice_spell_buff");
    @AssignedName("vulnerability_buff")
    public static final MobEffect VULNERABILITY_BUFF = create("vulnerability_buff");
    @AssignedName("tailwind_buff")
    public static final MobEffect TAILWIND_BUFF = create("tailwind_buff");
    @AssignedName("deadwind_buff")
    public static final MobEffect DEADWIND_BUFF = create("deadwind_buff");
    @AssignedName("windproof_buff")
    public static final MobEffect WINDPROOF_BUFF = create("windproof_buff");
    @AssignedName("bind_buff")
    public static final MobEffect BIND_BUFF = create("bind_buff");
    @AssignedName("oppression_buff")
    public static final MobEffect OPPRESSION_BUFF = create("oppression_buff");
    @AssignedName("san_increase")
    public static final MobEffect SAN_INCREASE = create("san_increase");
    @AssignedName("san_decrease")
    public static final MobEffect SAN_DECREASE = create("san_decrease");
    @AssignedName("melt_dream_energy_increase")
    public static final MobEffect MELT_DREAM_ENERGY_INCREASE = create("melt_dream_energy_increase");
    @AssignedName("melt_dream_energy_decrease")
    public static final MobEffect MELT_DREAM_ENERGY_DECREASE = create("melt_dream_energy_decrease");

    private PasterDreamEffects() {
    }

    public static void register() {
        GeminiRegistration.mobEffects(PasterDreamEffects.class);
    }

    private static MobEffect create(String id) {
        MobEffectCategory category = harmful(id)
            ? MobEffectCategory.HARMFUL
            : MobEffectCategory.BENEFICIAL;
        if (id.equals("san_increase") || id.equals("san_decrease")) {
            return new PasterDreamResourceEffect(
                PasterDreamResourceEffect.Resource.SAN,
                id.equals("san_increase"),
                color(id)
            );
        }
        if (id.equals("melt_dream_energy_increase") || id.equals("melt_dream_energy_decrease")) {
            return new PasterDreamResourceEffect(
                PasterDreamResourceEffect.Resource.MELT_DREAM_ENERGY,
                id.equals("melt_dream_energy_increase"),
                color(id)
            );
        }
        if (id.equals("rest_buff") || id.equals("rest_buff_in_dark")) {
            return PasterDreamEffectFactory.configure(
                new PasterDreamRestEffect(id, id.equals("rest_buff_in_dark"), color(id)),
                id
            );
        }
        return PasterDreamEffectFactory.configure(
            new PasterDreamEffectBehavior(id, category, color(id)),
            id
        );
    }

    private static boolean harmful(String id) {
        return id.contains("decrease")
            || id.contains("silence")
            || id.contains("lethargy")
            || id.contains("confusion")
            || id.contains("vulnerability")
            || id.contains("oppression")
            || id.contains("deadwind")
            || id.contains("bind");
    }

    private static int color(String id) {
        return switch (id) {
            case "san_increase", "san_decrease", "cheerup_buff" -> 0xF4D35E;
            case "melt_dream_energy_increase", "melt_dream_energy_decrease" -> 0x4CC9F0;
            case "evasion_buff", "evasion_cloak_buff", "turnback_cloak_buff" -> 0xC77DFF;
            case "fury_spell_buff", "flareup_buff" -> 0xEF476F;
            case "ice_spell_buff" -> 0x89C2FF;
            default -> 0xB799FF;
        };
    }
}
