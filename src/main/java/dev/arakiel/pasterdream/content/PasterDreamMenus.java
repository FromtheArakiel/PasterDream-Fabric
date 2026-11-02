package dev.arakiel.pasterdream.content;

import dev.arakiel.gemini.network.RecordCodecs;
import dev.arakiel.gemini.registration.AutoRegistryContainer;
import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuData;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuSpecs;
import java.util.List;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamMenus implements AutoRegistryContainer<MenuType<?>> {
	@AssignedName("dream_accumulator_gui")
	public static final MenuType<PasterDreamMenu> DREAM_ACCUMULATOR_GUI =
		create(PasterDreamMenuSpecs.DREAM_ACCUMULATOR);
	@AssignedName("meltdream_chest_gui_0")
	public static final MenuType<PasterDreamMenu> MELTDREAM_CHEST_GUI_0 =
		create(PasterDreamMenuSpecs.MELTDREAM_CHEST);
	@AssignedName("dyedream_desk_gui")
	public static final MenuType<PasterDreamMenu> DYEDREAM_DESK_GUI =
		create(PasterDreamMenuSpecs.DYEDREAM_DESK);
	@AssignedName("storage_bag_gui")
	public static final MenuType<PasterDreamMenu> STORAGE_BAG_GUI =
		create(PasterDreamMenuSpecs.STORAGE_BAG);
	@AssignedName("storage_bag_0_gui")
	public static final MenuType<PasterDreamMenu> STORAGE_BAG_0_GUI =
		create(PasterDreamMenuSpecs.STORAGE_BAG_LARGE);
	@AssignedName("shadow_desk_gui")
	public static final MenuType<PasterDreamMenu> SHADOW_DESK_GUI =
		create(PasterDreamMenuSpecs.SHADOW_DESK);
	@AssignedName("blueprint_gui_0")
	public static final MenuType<PasterDreamMenu> BLUEPRINT_GUI_0 =
		create(PasterDreamMenuSpecs.BLUEPRINT);
	@AssignedName("shadow_blast_furnace_gui")
	public static final MenuType<PasterDreamMenu> SHADOW_BLAST_FURNACE_GUI =
		create(PasterDreamMenuSpecs.SHADOW_BLAST_FURNACE);
	@AssignedName("research_table_gui")
	public static final MenuType<PasterDreamMenu> RESEARCH_TABLE_GUI =
		create(PasterDreamMenuSpecs.RESEARCH_TABLE);
	@AssignedName("shadow_chest_gui")
	public static final MenuType<PasterDreamMenu> SHADOW_CHEST_GUI =
		create(PasterDreamMenuSpecs.SHADOW_CHEST);
	@AssignedName("weapon_workshop_gui")
	public static final MenuType<PasterDreamMenu> WEAPON_WORKSHOP_GUI =
		create(PasterDreamMenuSpecs.WEAPON_WORKSHOP);
	@AssignedName("workshop_blast_gui")
	public static final MenuType<PasterDreamMenu> WORKSHOP_BLAST_GUI =
		create(PasterDreamMenuSpecs.WORKSHOP_BLAST);
	@AssignedName("workshop_anvil_gui")
	public static final MenuType<PasterDreamMenu> WORKSHOP_ANVIL_GUI =
		create(PasterDreamMenuSpecs.WORKSHOP_ANVIL);
	@AssignedName("dreamnotes_gui_0")
	public static final MenuType<PasterDreamMenu> DREAMNOTES_GUI_0 =
		create(PasterDreamMenuSpecs.DREAMNOTES);
	@AssignedName("dream_cauldron_gui")
	public static final MenuType<PasterDreamMenu> DREAM_CAULDRON_GUI =
		create(PasterDreamMenuSpecs.DREAM_CAULDRON);
	@AssignedName("shadow_select_end")
	public static final MenuType<PasterDreamMenu> SHADOW_SELECT_END =
		create(PasterDreamMenuSpecs.SHADOW_SELECT_END);
	@AssignedName("windmoor_crate_gui")
	public static final MenuType<PasterDreamMenu> WINDMOOR_CRATE_GUI =
		create(PasterDreamMenuSpecs.WINDMOOR_CRATE);
	@AssignedName("the_endless_book_of_dream_seekers_gui")
	public static final MenuType<PasterDreamMenu> THE_ENDLESS_BOOK_OF_DREAM_SEEKERS_GUI =
		create(PasterDreamMenuSpecs.ENDLESS_BOOK);
	@AssignedName("picnic_basket_gui")
	public static final MenuType<PasterDreamMenu> PICNIC_BASKET_GUI =
		create(PasterDreamMenuSpecs.PICNIC_BASKET);

	private PasterDreamMenus() {
	}

	public static List<MenuType<PasterDreamMenu>> all() {
		return List.of(
			DREAM_ACCUMULATOR_GUI,
			MELTDREAM_CHEST_GUI_0,
			DYEDREAM_DESK_GUI,
			STORAGE_BAG_GUI,
			STORAGE_BAG_0_GUI,
			SHADOW_DESK_GUI,
			BLUEPRINT_GUI_0,
			SHADOW_BLAST_FURNACE_GUI,
			RESEARCH_TABLE_GUI,
			SHADOW_CHEST_GUI,
			WEAPON_WORKSHOP_GUI,
			WORKSHOP_BLAST_GUI,
			WORKSHOP_ANVIL_GUI,
			DREAMNOTES_GUI_0,
			DREAM_CAULDRON_GUI,
			SHADOW_SELECT_END,
			WINDMOOR_CRATE_GUI,
			THE_ENDLESS_BOOK_OF_DREAM_SEEKERS_GUI,
			PICNIC_BASKET_GUI
		);
	}

	public static void register() {
		GeminiRegistration.register(PasterDreamMenus.class, PasterDream.MOD_ID);
	}

	@Override
	public Registry<MenuType<?>> getRegistry() {
		return BuiltInRegistries.MENU;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<MenuType<?>> getTargetFieldType() {
		return (Class<MenuType<?>>) (Class<?>) MenuType.class;
	}

	@SuppressWarnings("unchecked")
	private static MenuType<PasterDreamMenu> create(String id) {
		MenuType<PasterDreamMenu>[] holder = new MenuType[1];
		var spec = PasterDreamMenuSpecs.get(id);
		holder[0] = new ExtendedScreenHandlerType<>(
			(syncId, inventory, data) -> new PasterDreamMenu(
				holder[0],
				syncId,
				inventory,
				spec,
				data
			),
			RecordCodecs.create(PasterDreamMenuData.class)
		);
		return holder[0];
	}
}
