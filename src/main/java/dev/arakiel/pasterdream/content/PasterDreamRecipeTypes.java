package dev.arakiel.pasterdream.content;

import dev.arakiel.gemini.registration.AutoRegistryContainer;
import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.gameplay.recipe.DreamCauldronRecipe;
import dev.arakiel.pasterdream.gameplay.recipe.ShadowBlastingRecipe;
import dev.arakiel.pasterdream.gameplay.recipe.WeaponWorkshopRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamRecipeTypes implements AutoRegistryContainer<RecipeType<?>> {
    @AssignedName("shadow_blasting")
    public static final RecipeType<ShadowBlastingRecipe> SHADOW_BLASTING =
        create("shadow_blasting");
    @AssignedName("weapon_workshop")
    public static final RecipeType<WeaponWorkshopRecipe> WEAPON_WORKSHOP =
        create("weapon_workshop");
    @AssignedName("dream_cauldron")
    public static final RecipeType<DreamCauldronRecipe> DREAM_CAULDRON =
        create("dream_cauldron");

    private PasterDreamRecipeTypes() {
    }

    public static void register() {
        GeminiRegistration.register(PasterDreamRecipeTypes.class, PasterDream.MOD_ID);
    }

    @Override
    public Registry<RecipeType<?>> getRegistry() {
        return BuiltInRegistries.RECIPE_TYPE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<RecipeType<?>> getTargetFieldType() {
        return (Class<RecipeType<?>>) (Class<?>) RecipeType.class;
    }

    private static <T extends net.minecraft.world.item.crafting.Recipe<?>> RecipeType<T> create(
        String id
    ) {
        return new RecipeType<>() {
            @Override
            public String toString() {
                return PasterDream.id(id).toString();
            }
        };
    }
}
