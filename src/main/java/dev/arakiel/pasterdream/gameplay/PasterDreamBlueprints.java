package dev.arakiel.pasterdream.gameplay;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.content.PasterDreamItems;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class PasterDreamBlueprints {
	private static final Map<ResourceLocation, Blueprint> BLUEPRINTS = new HashMap<>();

	private PasterDreamBlueprints() {
	}

	public static void initialize() {
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new Listener());
	}

	public static Blueprint get(ResourceLocation id) {
		return BLUEPRINTS.get(id);
	}

	public static ResourceLocation blueprintId(Item item) {
		if (item == PasterDreamItems.SHADOW_BLAST_FURNACE_BLUEPRINT) {
			return PasterDream.id("shadow_blast_furnace");
		}
		if (item == PasterDreamItems.WEAPON_WORKSHOP_BLUEPRINT) {
			return PasterDream.id("weapon_workshop");
		}
		return null;
	}

	public record Blueprint(List<List<ItemStack>> pages) {
		public List<ItemStack> page(int index) {
			if (pages.isEmpty()) {
				return List.of();
			}
			return pages.get(Math.max(0, Math.min(index, pages.size() - 1)));
		}
	}

	private static final class Listener implements SimpleResourceReloadListener<Map<ResourceLocation, Blueprint>> {
		private static final ResourceLocation ID = PasterDream.id("blueprints");

		@Override
		public ResourceLocation getFabricId() {
			return ID;
		}

		@Override
		public CompletableFuture<Map<ResourceLocation, Blueprint>> load(
			ResourceManager manager,
			ProfilerFiller profiler,
			Executor executor
		) {
			return CompletableFuture.supplyAsync(() -> read(manager), executor);
		}

		@Override
		public CompletableFuture<Void> apply(
			Map<ResourceLocation, Blueprint> data,
			ResourceManager manager,
			ProfilerFiller profiler,
			Executor executor
		) {
			BLUEPRINTS.clear();
			BLUEPRINTS.putAll(data);
			return CompletableFuture.completedFuture(null);
		}

		private static Map<ResourceLocation, Blueprint> read(ResourceManager manager) {
			Map<ResourceLocation, Blueprint> result = new HashMap<>();
			for (Map.Entry<ResourceLocation, Resource> entry
				: manager.listResources("blueprints", path -> path.getPath().endsWith(".json")).entrySet()) {
				try (InputStreamReader reader = new InputStreamReader(entry.getValue().open())) {
					List<List<ItemStack>> pages = new ArrayList<>();
					for (JsonElement pageElement : JsonParser.parseReader(reader).getAsJsonArray()) {
						JsonObject page = pageElement.getAsJsonObject();
						List<ItemStack> stacks = new ArrayList<>(25);
						for (int index = 0; index < 25; index++) {
							JsonElement itemElement = page.get(Integer.toString(index));
							if (itemElement == null) {
								stacks.add(ItemStack.EMPTY);
								continue;
							}
							ResourceLocation itemId = ResourceLocation.parse(itemElement.getAsString());
							Item item = BuiltInRegistries.ITEM.getOptional(itemId).orElse(Items.AIR);
							stacks.add(item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item));
						}
						pages.add(List.copyOf(stacks));
					}
					String path = entry.getKey().getPath();
					ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
						entry.getKey().getNamespace(),
						path.substring("blueprints/".length(), path.length() - ".json".length())
					);
					result.put(id, new Blueprint(List.copyOf(pages)));
				} catch (Exception exception) {
					PasterDream.LOGGER.warn("Failed to load blueprint {}", entry.getKey(), exception);
				}
			}
			return result;
		}
	}
}
