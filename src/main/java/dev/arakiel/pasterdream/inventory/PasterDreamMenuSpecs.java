package dev.arakiel.pasterdream.inventory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class PasterDreamMenuSpecs {
	public static final String BLUEPRINT = "blueprint_gui_0";
	public static final String DREAM_ACCUMULATOR = "dream_accumulator_gui";
	public static final String DREAM_CAULDRON = "dream_cauldron_gui";
	public static final String DREAMNOTES = "dreamnotes_gui_0";
	public static final String DYEDREAM_DESK = "dyedream_desk_gui";
	public static final String MELTDREAM_CHEST = "meltdream_chest_gui_0";
	public static final String PICNIC_BASKET = "picnic_basket_gui";
	public static final String RESEARCH_TABLE = "research_table_gui";
	public static final String SHADOW_BLAST_FURNACE = "shadow_blast_furnace_gui";
	public static final String SHADOW_CHEST = "shadow_chest_gui";
	public static final String SHADOW_DESK = "shadow_desk_gui";
	public static final String SHADOW_SELECT_END = "shadow_select_end";
	public static final String STORAGE_BAG = "storage_bag_gui";
	public static final String STORAGE_BAG_LARGE = "storage_bag_0_gui";
	public static final String ENDLESS_BOOK = "the_endless_book_of_dream_seekers_gui";
	public static final String WEAPON_WORKSHOP = "weapon_workshop_gui";
	public static final String WINDMOOR_CRATE = "windmoor_crate_gui";
	public static final String WORKSHOP_ANVIL = "workshop_anvil_gui";
	public static final String WORKSHOP_BLAST = "workshop_blast_gui";

	public enum SlotAccess {
		NORMAL,
		OUTPUT,
		DISPLAY,
		PEN_AND_INK,
		DREAMNOTES,
		PERGAMYN,
		UNKNOWN_NOTES,
		SORBENT
	}

	public record SlotSpec(int index, int x, int y, SlotAccess access) {
	}

	public record MenuSpec(
		String id,
		PasterDreamMenuBacking backing,
		List<SlotSpec> slots,
		int playerX,
		int playerY,
		int hotbarX,
		int hotbarY,
		boolean playerInventory
	) {
		public int capacity() {
			return slots.isEmpty() ? 0 : slots.stream().mapToInt(SlotSpec::index).max().orElse(-1) + 1;
		}
	}

	private static final Map<String, MenuSpec> SPECS = new LinkedHashMap<>();
	private static final Map<String, String> BLOCK_MENU_IDS = Map.ofEntries(
		Map.entry("dream_accumulator", DREAM_ACCUMULATOR),
		Map.entry("dream_cauldron", DREAM_CAULDRON),
		Map.entry("dyedream_desk", DYEDREAM_DESK),
		Map.entry("meltdream_chest", MELTDREAM_CHEST),
		Map.entry("meltdream_chest_open", MELTDREAM_CHEST),
		Map.entry("picnic_basket", PICNIC_BASKET),
		Map.entry("research_table", RESEARCH_TABLE),
		Map.entry("shadow_blast_furnace", SHADOW_BLAST_FURNACE),
		Map.entry("shadow_blast_furnace_core", SHADOW_BLAST_FURNACE),
		Map.entry("shadow_chest", SHADOW_CHEST),
		Map.entry("shadow_desk", SHADOW_DESK),
		Map.entry("the_endless_book_of_dream_seekers", ENDLESS_BOOK),
		Map.entry("weapon_workshop", WEAPON_WORKSHOP),
		Map.entry("windmoor_crate", WINDMOOR_CRATE),
		Map.entry("workshop_anvil", WORKSHOP_ANVIL),
		Map.entry("workshop_blast", WORKSHOP_BLAST)
	);

	static {
		add(new MenuSpec(
			BLUEPRINT,
			PasterDreamMenuBacking.VIRTUAL,
			displayGrid(25, 5, 53, 24),
			18, 134, 18, 192, true
		));
		add(block(
			DREAM_ACCUMULATOR, "0:78:28,1:78:82", 8, 124, 8, 182,
			Map.of(1, SlotAccess.SORBENT)
		));
		add(block(
			DREAM_CAULDRON, "0:17:50,1:61:19,2:89:19,3:117:19,4:170:23,5:170:77,6:89:50",
			18, 114, 18, 172, Map.of(5, SlotAccess.OUTPUT, 6, SlotAccess.OUTPUT)
		));
		add(new MenuSpec(
			DREAMNOTES, PasterDreamMenuBacking.VIRTUAL, List.of(),
			8, 84, 8, 142, false
		));
		add(block(DYEDREAM_DESK, "0:79:26", 8, 84, 8, 142, Map.of()));
		add(block(MELTDREAM_CHEST, gridLayout(9, 3, 61, 16), 8, 100, 8, 158, Map.of()));
		add(block(PICNIC_BASKET, chestLayout(43, 23), 8, 114, 8, 172, Map.of()));
		add(block(
			RESEARCH_TABLE, "0:175:19,1:40:28,2:40:46,3:139:37,4:40:82,5:139:82",
			23, 124, 23, 182,
			Map.of(
				0, SlotAccess.PEN_AND_INK,
				1, SlotAccess.DREAMNOTES,
				2, SlotAccess.PERGAMYN,
				3, SlotAccess.OUTPUT,
				4, SlotAccess.UNKNOWN_NOTES,
				5, SlotAccess.OUTPUT
			)
		));
		add(block(
			SHADOW_BLAST_FURNACE, "0:25:24,1:25:69,2:61:105,3:97:105,4:133:24,5:133:69",
			8, 134, 8, 192,
			Map.of(2, SlotAccess.OUTPUT, 3, SlotAccess.OUTPUT, 5, SlotAccess.OUTPUT)
		));
		add(block(SHADOW_CHEST, chestLayout(43, 15), 8, 84, 8, 142, Map.of()));
		add(block(SHADOW_DESK, "0:79:26", 8, 84, 8, 142, Map.of()));
		add(new MenuSpec(
			SHADOW_SELECT_END, PasterDreamMenuBacking.VIRTUAL, List.of(),
			8, 84, 8, 142, false
		));
		add(new MenuSpec(
			STORAGE_BAG, PasterDreamMenuBacking.PLAYER_SLOT, slots(gridLayout(9, 3, 61, 17)),
			8, 84, 8, 142, true
		));
		add(new MenuSpec(
			STORAGE_BAG_LARGE, PasterDreamMenuBacking.PLAYER_SLOT, slots(gridLayout(25, 5, 43, 16)),
			8, 118, 8, 176, true
		));
		add(block(ENDLESS_BOOK, "0:115:26,1:43:26", 8, 84, 8, 142, Map.of()));
		add(block(
			WEAPON_WORKSHOP, "0:12:18,1:30:18,2:48:18,3:66:18,4:84:18,5:138:18,6:138:63",
			13, 104, 13, 162, Map.of(6, SlotAccess.OUTPUT)
		));
		add(block(WINDMOOR_CRATE, chestLayout(43, 23), 8, 114, 8, 172, Map.of()));
		add(block(
			WORKSHOP_ANVIL, "0:34:78,1:124:78", 8, 134, 8, 192,
			Map.of(1, SlotAccess.OUTPUT)
		));
		add(block(
			WORKSHOP_BLAST, "0:79:23,1:25:50,2:133:32,3:133:68,4:79:77",
			8, 114, 8, 172, Map.of(3, SlotAccess.OUTPUT, 4, SlotAccess.OUTPUT)
		));
	}

	private PasterDreamMenuSpecs() {
	}

	public static MenuSpec get(String id) {
		MenuSpec spec = SPECS.get(id);
		if (spec == null) {
			throw new IllegalArgumentException("Unknown PasterDream menu spec: " + id);
		}
		return spec;
	}

	public static MenuSpec blockSpec(String blockId) {
		String menuId = BLOCK_MENU_IDS.get(blockId);
		return menuId == null ? null : get(menuId);
	}

	private static void add(MenuSpec spec) {
		SPECS.put(spec.id(), spec);
	}

	private static MenuSpec block(
		String id,
		String layout,
		int playerX,
		int playerY,
		int hotbarX,
		int hotbarY,
		Map<Integer, SlotAccess> access
	) {
		List<SlotSpec> slots = slots(layout);
		List<SlotSpec> configured = new ArrayList<>(slots.size());
		for (SlotSpec slot : slots) {
			configured.add(new SlotSpec(
				slot.index(),
				slot.x(),
				slot.y(),
				access.getOrDefault(slot.index(), SlotAccess.NORMAL)
			));
		}
		return new MenuSpec(
			id,
			PasterDreamMenuBacking.BLOCK,
			List.copyOf(configured),
			playerX,
			playerY,
			hotbarX,
			hotbarY,
			true
		);
	}

	private static List<SlotSpec> slots(String layout) {
		List<SlotSpec> result = new ArrayList<>();
		for (String entry : layout.split(",")) {
			String[] parts = entry.split(":");
			result.add(new SlotSpec(
				Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1]),
				Integer.parseInt(parts[2]),
				SlotAccess.NORMAL
			));
		}
		return result;
	}

	private static List<SlotSpec> slots(List<SlotSpec> source) {
		return List.copyOf(source);
	}

	private static List<SlotSpec> displayGrid(int count, int columns, int startX, int startY) {
		List<SlotSpec> result = new ArrayList<>(count);
		for (int index = 0; index < count; index++) {
			result.add(new SlotSpec(
				index,
				startX + index % columns * 18,
				startY + index / columns * 18,
				SlotAccess.DISPLAY
			));
		}
		return List.copyOf(result);
	}

	private static String chestLayout(int startX, int startY) {
		return gridLayout(15, 5, startX, startY);
	}

	private static String gridLayout(int count, int columns, int startX, int startY) {
		StringBuilder result = new StringBuilder();
		for (int index = 0; index < count; index++) {
			if (index > 0) {
				result.append(',');
			}
			result.append(index)
				.append(':')
				.append(startX + index % columns * 18)
				.append(':')
				.append(startY + index / columns * 18);
		}
		return result.toString();
	}
}
