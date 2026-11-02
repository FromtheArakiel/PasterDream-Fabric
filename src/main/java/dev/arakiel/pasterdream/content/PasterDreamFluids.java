package dev.arakiel.pasterdream.content;

import dev.arakiel.gemini.registration.AutoRegistryContainer;
import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.pasterdream.PasterDream;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.EmptyFluid;
import net.minecraft.world.level.material.Fluid;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamFluids implements AutoRegistryContainer<Fluid> {
    @AssignedName("meltdream_liquid")
    public static final Fluid MELTDREAM_LIQUID = new EmptyFluid();
    @AssignedName("flowing_meltdream_liquid")
    public static final Fluid FLOWING_MELTDREAM_LIQUID = new EmptyFluid();
    @AssignedName("shadow_liquid")
    public static final Fluid SHADOW_LIQUID = new EmptyFluid();
    @AssignedName("flowing_shadow_liquid")
    public static final Fluid FLOWING_SHADOW_LIQUID = new EmptyFluid();

    private PasterDreamFluids() {
    }

    public static void register() {
        GeminiRegistration.register(PasterDreamFluids.class, PasterDream.MOD_ID);
    }

    @Override
    public Registry<Fluid> getRegistry() {
        return BuiltInRegistries.FLUID;
    }

    @Override
    public Class<Fluid> getTargetFieldType() {
        return Fluid.class;
    }
}
