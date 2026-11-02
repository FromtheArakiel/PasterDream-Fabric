package dev.arakiel.pasterdream.gameplay.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.arakiel.pasterdream.content.PasterDreamBlocks;
import dev.arakiel.pasterdream.content.PasterDreamRecipeSerializers;
import dev.arakiel.pasterdream.content.PasterDreamRecipeTypes;
import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record DreamCauldronRecipe(
	String group,
	List<ItemStack> inputs,
	ItemStack result,
	int processingTime,
	int fluidCost
) implements Recipe<WorkshopRecipeInput> {
	public DreamCauldronRecipe {
		inputs = List.copyOf(inputs);
		processingTime = Math.max(1, processingTime);
		fluidCost = Math.max(0, fluidCost);
	}

	@Override
	public boolean matches(WorkshopRecipeInput input, Level level) {
		for (int slot = 0; slot < inputs.size(); slot++) {
			ItemStack required = inputs.get(slot);
			if (required.isEmpty() || required.getCount() <= 0) {
				continue;
			}
			ItemStack available = input.getItem(slot);
			if (available.isEmpty()
				|| !ItemStack.isSameItemSameComponents(available, required)
				|| available.getCount() < required.getCount()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack assemble(WorkshopRecipeInput input, HolderLookup.Provider registries) {
		return result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		for (ItemStack stack : inputs) {
			ingredients.add(stack.isEmpty() || stack.getCount() <= 0
				? Ingredient.EMPTY
				: Ingredient.of(stack.copyWithCount(1)));
		}
		return ingredients;
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider registries) {
		return result;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(PasterDreamBlocks.DREAM_CAULDRON);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return PasterDreamRecipeSerializers.DREAM_CAULDRON;
	}

	@Override
	public RecipeType<?> getType() {
		return PasterDreamRecipeTypes.DREAM_CAULDRON;
	}

	public static final class Serializer implements RecipeSerializer<DreamCauldronRecipe> {
		private static final MapCodec<DreamCauldronRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
			instance.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(DreamCauldronRecipe::group),
				ItemStack.CODEC.listOf().fieldOf("inputs").forGetter(DreamCauldronRecipe::inputs),
				ItemStack.CODEC.fieldOf("result").forGetter(DreamCauldronRecipe::result),
				Codec.INT.optionalFieldOf("processing_time", 20).forGetter(DreamCauldronRecipe::processingTime),
				Codec.INT.optionalFieldOf("fluid_cost", 100).forGetter(DreamCauldronRecipe::fluidCost)
			).apply(instance, DreamCauldronRecipe::new)
		);

		private static final StreamCodec<RegistryFriendlyByteBuf, DreamCauldronRecipe> STREAM_CODEC =
			StreamCodec.of(Serializer::write, Serializer::read);

		@Override
		public MapCodec<DreamCauldronRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, DreamCauldronRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static void write(RegistryFriendlyByteBuf buffer, DreamCauldronRecipe recipe) {
			buffer.writeUtf(recipe.group);
			buffer.writeVarInt(recipe.inputs.size());
			for (ItemStack stack : recipe.inputs) {
				ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, stack);
			}
			ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.result);
			buffer.writeVarInt(recipe.processingTime);
			buffer.writeVarInt(recipe.fluidCost);
		}

		private static DreamCauldronRecipe read(RegistryFriendlyByteBuf buffer) {
			String group = buffer.readUtf();
			int size = buffer.readVarInt();
			List<ItemStack> inputs = java.util.stream.IntStream.range(0, size)
				.mapToObj(index -> ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer))
				.toList();
			ItemStack result = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
			int processingTime = buffer.readVarInt();
			int fluidCost = buffer.readVarInt();
			return new DreamCauldronRecipe(group, inputs, result, processingTime, fluidCost);
		}
	}
}
