package dev.arakiel.pasterdream.content;

import dev.arakiel.gemini.registration.AutoRegistryContainer;
import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.pasterdream.PasterDream;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamParticles implements AutoRegistryContainer<ParticleType<?>> {
    @AssignedName("leaves_particle")
    public static final ParticleType<?> LEAVES_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("buff_0_particle")
    public static final ParticleType<?> BUFF_0_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("dyedream_0_particle")
    public static final ParticleType<?> DYEDREAM_0_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("dust_0_particle")
    public static final ParticleType<?> DUST_0_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("crack_0_particle")
    public static final ParticleType<?> CRACK_0_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("dreamfertiliter_particle")
    public static final ParticleType<?> DREAMFERTILITER_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("sculk_particle")
    public static final ParticleType<?> SCULK_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("attack_0_particle")
    public static final ParticleType<?> ATTACK_0_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("meltdream_crystal_particle")
    public static final ParticleType<?> MELTDREAM_CRYSTAL_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("meltdream_crystal_big_particle")
    public static final ParticleType<?> MELTDREAM_CRYSTAL_BIG_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("starcall_particle")
    public static final ParticleType<?> STARCALL_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("spore_particle")
    public static final ParticleType<?> SPORE_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("soul_particle")
    public static final ParticleType<?> SOUL_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("squeal_wave_particle")
    public static final ParticleType<?> SQUEAL_WAVE_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("shadow_stone_particle")
    public static final ParticleType<?> SHADOW_STONE_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("calle_particle")
    public static final ParticleType<?> CALLE_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("terrasword_wave_particle")
    public static final ParticleType<?> TERRASWORD_WAVE_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("poison_gas_particle")
    public static final ParticleType<?> POISON_GAS_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("poison_soul_particle")
    public static final ParticleType<?> POISON_SOUL_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("healing_spell_particle")
    public static final ParticleType<?> HEALING_SPELL_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("golden_particle")
    public static final ParticleType<?> GOLDEN_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("silver_particle")
    public static final ParticleType<?> SILVER_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("firefly_particle")
    public static final ParticleType<?> FIREFLY_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("firefly_glass_jar_particle")
    public static final ParticleType<?> FIREFLY_GLASS_JAR_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("light_firefly_glass_jar_particle")
    public static final ParticleType<?> LIGHT_FIREFLY_GLASS_JAR_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("lightning_particle")
    public static final ParticleType<?> LIGHTNING_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("yellow_smoke_particle")
    public static final ParticleType<?> YELLOW_SMOKE_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("fury_spell_particle")
    public static final ParticleType<?> FURY_SPELL_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("snowflake_0_particle")
    public static final ParticleType<?> SNOWFLAKE_0_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("snowflake_1_particle")
    public static final ParticleType<?> SNOWFLAKE_1_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("fox_fire_0_particle")
    public static final ParticleType<?> FOX_FIRE_0_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("fox_fire_1_particle")
    public static final ParticleType<?> FOX_FIRE_1_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("feather_white_particle")
    public static final ParticleType<?> FEATHER_WHITE_PARTICLE = new PasterDreamParticleType(false);
    @AssignedName("white_star_particle")
    public static final ParticleType<?> WHITE_STAR_PARTICLE = new PasterDreamParticleType(false);

    private PasterDreamParticles() {
    }

    public static void register() {
        GeminiRegistration.register(PasterDreamParticles.class, PasterDream.MOD_ID);
    }

    @Override
    public Registry<ParticleType<?>> getRegistry() {
        return BuiltInRegistries.PARTICLE_TYPE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<ParticleType<?>> getTargetFieldType() {
        return (Class<ParticleType<?>>) (Class<?>) ParticleType.class;
    }
}
