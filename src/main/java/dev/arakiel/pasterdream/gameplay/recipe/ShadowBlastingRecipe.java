package dev.arakiel.pasterdream.gameplay.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.arakiel.pasterdream.content.PasterDreamBlocks;
import dev.arakiel.pasterdream.content.PasterDreamRecipeSerializers;
import dev.arakiel.pasterdream.content.PasterDreamRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public record ShadowBlastingRecipe(
	String group,
	Ingredient input,
	ItemStack result,
	ItemStack byproduct,
	double byproductChance,
	int blastingTicks,
	int fluidFuelCost,
	int itemFuelCost
) implements Recipe<SingleRecipeInput> {
	public ShadowBlastingRecipe {
		byproductChance = Mth.clamp(byproductChance, 0.0D, 1.0D);
		blastingTicks = Math.max(1, blastingTicks);
		fluidFuelCost = Math.max(0, fluidFuelCost);
		itemFuelCost = Math.max(0, itemFuelCost);
	}

	@Override
	public boolean matches(SingleRecipeInput input, Level level) {
		return this.input.test(input.item());
	}

	@Override
	public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
		return result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.withSize(1, input);
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider registries) {
		return result;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(PasterDreamBlocks.SHADOW_BLAST_FURNACE);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return PasterDreamRecipeSerializers.SHADOW_BLASTING;
	}

	@Override
	public RecipeType<?> getType() {
		return PasterDreamRecipeTypes.SHADOW_BLASTING;
	}

	public static final class Serializer implements RecipeSerializer<ShadowBlastingRecipe> {
		private static final MapCodec<ShadowBlastingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
			instance.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(ShadowBlastingRecipe::group),
				Ingredient.CODEC_NONEMPTY.fieldOf("input_ingredient").forGetter(ShadowBlastingRecipe::input),
				ItemStack.CODEC.fieldOf("result").forGetter(ShadowBlastingRecipe::result),
				ItemStack.CODEC.optionalFieldOf("by_result", ItemStack.EMPTY)
					.forGetter(ShadowBlastingRecipe::byproduct),
				Codec.DOUBLE.optionalFieldOf("by_result_probability", 1.0D)
					.forGetter(ShadowBlastingRecipe::byproductChance),
				Codec.INT.optionalFieldOf("blasting_tick", 100)
					.forGetter(ShadowBlastingRecipe::blastingTicks),
				Codec.INT.optionalFieldOf("spend_fluid_fuel", 500)
					.forGetter(ShadowBlastingRecipe::fluidFuelCost),
				Codec.INT.optionalFieldOf("spend_fuel", 1)
					.forGetter(ShadowBlastingRecipe::itemFuelCost)
			)
			.apply(instance, ShadowBlastingRecipe::new)
		);

		private static final StreamCodec<RegistryFriendlyByteBuf, ShadowBlastingRecipe> STREAM_CODEC =
			StreamCodec.of(Serializer::write, Serializer::read);

		@Override
		public MapCodec<ShadowBlastingRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ShadowBlastingRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static void write(RegistryFriendlyByteBuf buffer, ShadowBlastingRecipe recipe) {
			buffer.writeUtf(recipe.group);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
			ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.result);
			ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.byproduct);
			buffer.writeDouble(recipe.byproductChance);
			buffer.writeVarInt(recipe.blastingTicks);
			buffer.writeVarInt(recipe.fluidFuelCost);
			buffer.writeVarInt(recipe.itemFuelCost);
		}

		private static ShadowBlastingRecipe read(RegistryFriendlyByteBuf buffer) {
			return new ShadowBlastingRecipe(
				buffer.readUtf(),
				Ingredient.CONTENTS_STREAM_CODEC.decode(buffer),
				ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer),
				ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer),
				buffer.readDouble(),
				buffer.readVarInt(),
				buffer.readVarInt(),
				buffer.readVarInt()
			);
		}
	}
}
