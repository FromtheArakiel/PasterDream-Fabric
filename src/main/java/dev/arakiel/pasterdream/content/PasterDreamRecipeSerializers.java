package dev.arakiel.pasterdream.content;

import dev.arakiel.gemini.registration.AutoRegistryContainer;
import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.gameplay.recipe.ShadowBlastingRecipe;
import dev.arakiel.pasterdream.gameplay.recipe.WeaponWorkshopRecipe;
import dev.arakiel.pasterdream.gameplay.recipe.DreamCauldronRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamRecipeSerializers implements AutoRegistryContainer<RecipeSerializer<?>> {
	@AssignedName("shadow_blasting")
	public static final RecipeSerializer<ShadowBlastingRecipe> SHADOW_BLASTING =
		new ShadowBlastingRecipe.Serializer();
	@AssignedName("weapon_workshop")
	public static final RecipeSerializer<WeaponWorkshopRecipe> WEAPON_WORKSHOP =
		new WeaponWorkshopRecipe.Serializer();
	@AssignedName("dream_cauldron")
	public static final RecipeSerializer<DreamCauldronRecipe> DREAM_CAULDRON =
		new DreamCauldronRecipe.Serializer();

	private PasterDreamRecipeSerializers() {
	}

	public static void register() {
		GeminiRegistration.register(PasterDreamRecipeSerializers.class, PasterDream.MOD_ID);
	}

	@Override
	public Registry<RecipeSerializer<?>> getRegistry() {
		return BuiltInRegistries.RECIPE_SERIALIZER;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<RecipeSerializer<?>> getTargetFieldType() {
		return (Class<RecipeSerializer<?>>) (Class<?>) RecipeSerializer.class;
	}
}
