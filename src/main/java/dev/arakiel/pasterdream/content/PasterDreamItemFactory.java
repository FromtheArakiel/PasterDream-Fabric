package dev.arakiel.pasterdream.content;

import dev.arakiel.pasterdream.item.PasterDreamFoodItem;
import dev.arakiel.pasterdream.item.PasterDreamArmorItem;
import dev.arakiel.pasterdream.item.PasterDreamCurioItem;
import dev.arakiel.pasterdream.item.PasterDreamGeoItem;
import dev.arakiel.pasterdream.item.PasterDreamProjectileItem;
import dev.arakiel.pasterdream.item.PasterDreamToolTier;
import dev.arakiel.pasterdream.PasterDream;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;

public final class PasterDreamItemFactory {
	private PasterDreamItemFactory() {
	}

	public static Item create(String id) {
		return switch (id) {
		case "meltdream_crystal" -> item(16, Rarity.EPIC);
		case "titanium_ingot" -> item(64, Rarity.UNCOMMON);
		case "dyedream_ingot" -> item(64, Rarity.UNCOMMON);
		case "pink_egg" -> projectile("projectile_pinkegg_projectile", 16, 1.2F, 1.0F, null);
		case "red_dew" -> food(64, Rarity.COMMON, 0, 0.0F, true, 32, UseAnim.DRINK);
		case "blue_dew" -> food(64, Rarity.COMMON, 0, 0.0F, true, 32, UseAnim.DRINK);
		case "jungle_spore" -> food(64, Rarity.COMMON, 1, 0.0F, false, 32, UseAnim.EAT);
		case "meltdream_liquid_bucket" -> item(1, Rarity.COMMON);
		case "shadow_liquid_bucket" -> item(1, Rarity.COMMON);
		case "roots_pale_boneneedle" -> item(1, Rarity.COMMON);
		case "mortar" -> item(1, Rarity.COMMON);
		case "sword_embryo" -> item(1, Rarity.COMMON);
		case "shadow_dungeon_key" -> item(1, Rarity.COMMON);
		case "dream_wand_embryo" -> item(1, Rarity.COMMON);
		case "terra_sword_embryo" -> item(1, Rarity.COMMON);
		case "shadow_erosion_sword_embryo" -> item(1, Rarity.COMMON);
		case "shadow_erosion_pickaxe_embryo" -> item(1, Rarity.COMMON);
		case "shadow_erosion_axe_embryo" -> item(1, Rarity.COMMON);
		case "shadow_erosion_hoe_embryo" -> item(1, Rarity.COMMON);
		case "shadow_erosion_shovel_embryo" -> item(1, Rarity.COMMON);
		case "iceshadow_hammer_embryo" -> item(1, Rarity.COMMON);
		case "white_sword_embryo" -> item(1, Rarity.COMMON);
		case "shadow_sword_embryo" -> item(1, Rarity.COMMON);
		case "star_wish_rod_embryo" -> item(1, Rarity.COMMON);
		case "deep_treasure_map_0" -> item(16, Rarity.COMMON);
		case "deep_treasure_map_1" -> item(16, Rarity.COMMON);
		case "enhancement_stone_0" -> item(16, Rarity.COMMON);
		case "enhancement_stone_1" -> item(16, Rarity.COMMON);
		case "turn_pale_cecilia" -> item(1, Rarity.COMMON);
		case "calle_card_0" -> item(1, Rarity.COMMON);
		case "calle_card_1" -> item(1, Rarity.COMMON);
		case "calle_card_2" -> item(1, Rarity.COMMON);
		case "calle_card_3" -> item(1, Rarity.COMMON);
		case "calle_card_4" -> item(1, Rarity.COMMON);
		case "calle_card_5" -> item(1, Rarity.COMMON);
		case "calle_card_6" -> item(1, Rarity.COMMON);
		case "calle_card_7" -> item(1, Rarity.COMMON);
		case "calle_card_8" -> item(1, Rarity.COMMON);
		case "calle_card_9" -> item(1, Rarity.COMMON);
		case "sweet_dream_music_disc" -> item(1, Rarity.RARE);
		case "snowfall_dream_music_disc" -> item(1, Rarity.RARE);
		case "aaroncos_music_disc" -> item(1, Rarity.RARE);
		case "wind_journey_music_disc" -> item(1, Rarity.RARE);
		case "lightning_spell" -> projectile("projectile_lightning_spell_projectile", 8, 1.0F, 0.0F, null);
		case "poison_spell" -> projectile("projectile_poison_spell_projectile", 8, 1.0F, 0.0F, null);
		case "healing_spell" -> projectile("projectile_healing_spell_projectile", 8, 1.0F, 0.0F, null);
		case "fury_spell" -> projectile("projectile_fury_spell_projectile", 8, 1.0F, 0.0F, null);
		case "ice_spell" -> projectile("projectile_ice_spell_projectile", 8, 1.0F, 0.0F, null);
		case "dough" -> food(64, Rarity.COMMON, 1, 0.1F, false, 32, UseAnim.EAT);
		case "bread_slice" -> food(64, Rarity.COMMON, 3, 0.4F, false, 32, UseAnim.EAT);
		case "cake_base" -> food(64, Rarity.COMMON, 4, 0.4F, false, 32, UseAnim.EAT);
		case "wafer_biscuit" -> food(64, Rarity.COMMON, 3, 0.3F, false, 32, UseAnim.EAT);
		case "dyedream_fruit" -> food(64, Rarity.COMMON, 3, 0.4F, false, 32, UseAnim.EAT);
		case "fig" -> food(64, Rarity.COMMON, 3, 0.2F, false, 32, UseAnim.EAT);
		case "dream_juice" -> food(8, Rarity.COMMON, 0, 0.0F, true, 32, UseAnim.DRINK);
		case "amber_candy" -> food(64, Rarity.COMMON, 1, 0.1F, true, 32, UseAnim.EAT);
		case "popping_candy" -> food(64, Rarity.COMMON, 2, 0.3F, true, 32, UseAnim.EAT);
		case "heart_chocolate_0" -> food(64, Rarity.COMMON, 4, 0.4F, false, 32, UseAnim.EAT);
		case "heart_chocolate_1" -> food(64, Rarity.COMMON, 4, 0.4F, false, 32, UseAnim.EAT);
		case "heart_chocolate_2" -> food(64, Rarity.COMMON, 4, 0.4F, false, 32, UseAnim.EAT);
		case "dyedream_perfume" -> food(16, Rarity.COMMON, 0, 0.0F, false, 32, UseAnim.DRINK);
		case "creative_sword" -> sword(100, 4.0F, 6.0F, 1, 2, "", 3F, 6.0F);
		case "excavator" -> item(1, Rarity.COMMON);
		case "loot_table_creator_0" -> item(1, Rarity.COMMON);
		case "loot_table_creator_1" -> item(1, Rarity.COMMON);
		case "loot_table_creator_2" -> item(1, Rarity.COMMON);
		case "loot_table_creator_3" -> item(1, Rarity.COMMON);
		case "loot_table_creator_4" -> item(1, Rarity.COMMON);
		case "loot_table_creator_5" -> item(1, Rarity.COMMON);
		case "loot_table_creator_6" -> item(1, Rarity.COMMON);
		case "loot_table_creator_7" -> item(1, Rarity.COMMON);
		case "loot_table_creator_8" -> item(1, Rarity.COMMON);
		case "loot_table_creator_9" -> item(1, Rarity.COMMON);
		case "aaroncos_arena_creator" -> item(1, Rarity.COMMON);
		case "dream_meter" -> geoItem(1, Rarity.COMMON);
		case "copper_sword" -> sword(225, 1.0F, 1.5F, 0, 12, "", 3F, -2.4F);
		case "copper_pickaxe" -> pickaxe(225, 4.0F, 1.5F, 1, 12, "", 1F, -2.8F);
		case "copper_axe" -> axe(225, 5.0F, 7.0F, 1, 12, "", 1.0F, -3.15F);
		case "copper_shovel" -> shovel(225, 5.0F, 2.0F, 1, 12, "", 1.0F, -3.0F);
		case "copper_hoe" -> hoe(225, 5.0F, 0.0F, 1, 12, "", 0F, -1.5F);
		case "copper_armor_helmet" -> armor("copper_armor", ArmorItem.Type.HELMET, 14, false, false, false);
		case "copper_armor_chestplate" -> armor("copper_armor", ArmorItem.Type.CHESTPLATE, 14, false, false, false);
		case "copper_armor_leggings" -> armor("copper_armor", ArmorItem.Type.LEGGINGS, 14, false, false, false);
		case "copper_armor_boots" -> armor("copper_armor", ArmorItem.Type.BOOTS, 14, false, false, false);
		case "titanium_sword" -> sword(1721, 1.0F, 3.5F, 1, 17, "titanium_ingot", 3F, -2.4F);
		case "titanium_pickaxe" -> pickaxe(1721, 9.0F, 3.5F, 4, 17, "titanium_ingot", 1F, -2.8F);
		case "titanium_axe" -> axe(1721, 9.0F, 8.0F, 4, 17, "titanium_ingot", 1.0F, -3.0F);
		case "titanium_shovel" -> shovel(1721, 9.0F, 4.5F, 4, 17, "titanium_ingot", 1.0F, -3.0F);
		case "titanium_hoe" -> hoe(1721, 9.0F, 0.5F, 4, 17, "titanium_ingot", 0F, 0.0F);
		case "titanium_armor_helmet" -> armor("titanium_armor", ArmorItem.Type.HELMET, 25, false, false, false);
		case "titanium_armor_chestplate" -> armor("titanium_armor", ArmorItem.Type.CHESTPLATE, 25, false, false, false);
		case "titanium_armor_leggings" -> armor("titanium_armor", ArmorItem.Type.LEGGINGS, 25, false, false, false);
		case "titanium_armor_boots" -> armor("titanium_armor", ArmorItem.Type.BOOTS, 25, false, false, false);
		case "titanium_upgrade" -> item(16, Rarity.COMMON);
		case "sculk_armor_helmet" -> armor("sculk_armor", ArmorItem.Type.HELMET, 42, true, false, false);
		case "sculk_armor_chestplate" -> armor("sculk_armor", ArmorItem.Type.CHESTPLATE, 42, true, false, false);
		case "sculk_armor_leggings" -> armor("sculk_armor", ArmorItem.Type.LEGGINGS, 42, true, false, false);
		case "sculk_armor_boots" -> armor("sculk_armor", ArmorItem.Type.BOOTS, 42, true, false, false);
		case "sculk_upgrade" -> item(16, Rarity.COMMON);
		case "dyedream_sword" -> sword(1314, 1.0F, 4.0F, 1, 22, "dyedream_ingot", 3F, -2.4F);
		case "dyedream_sword_0" -> sword(1314, 1.0F, 5.0F, 1, 22, "dyedream_ingot", 3F, -2.4F);
		case "dyedream_pickaxe" -> pickaxe(1314, 11.0F, 4.0F, 4, 22, "dyedream_ingot", 1F, -2.8F);
		case "dyedream_axe" -> axe(1314, 11.0F, 8.5F, 4, 22, "dyedream_ingot", 1.0F, -3.0F);
		case "dyedream_shovel" -> shovel(1314, 11.0F, 4.5F, 4, 22, "dyedream_ingot", 1.0F, -3.0F);
		case "dyedream_hoe" -> hoe(1314, 11.0F, 1.0F, 4, 22, "dyedream_ingot", 0F, 0.0F);
		case "dyedream_hammer" -> pickaxe(6570, 3.0F, 10.0F, 4, 22, "dyedream_ingot", 0F, -3.3F);
		case "dyedream_armor_helmet" -> armor("dyedream_armor", ArmorItem.Type.HELMET, 40, false, false, false);
		case "dyedream_armor_chestplate" -> armor("dyedream_armor", ArmorItem.Type.CHESTPLATE, 40, false, false, false);
		case "dyedream_armor_leggings" -> armor("dyedream_armor", ArmorItem.Type.LEGGINGS, 40, false, false, false);
		case "dyedream_armor_boots" -> armor("dyedream_armor", ArmorItem.Type.BOOTS, 40, false, false, false);
		case "dyedream_upgrade" -> item(16, Rarity.UNCOMMON);
		case "moltengold_sword" -> sword(251, 1.0F, 2.0F, 1, 23, "moltengold_ingot", 3F, -2.3F);
		case "true_moltengold_sword" -> sword(1255, 1.0F, 3.0F, 1, 23, "moltengold_ingot", 3F, -2.2F);
		case "truest_moltengold_sword" -> sword(1255, 1.0F, 3.0F, 1, 23, "moltengold_ingot", 3F, -2.15F);
		case "moltengold_pickaxe" -> pickaxe(251, 14.0F, 2.0F, 1, 23, "moltengold_ingot", 1F, -2.7F);
		case "true_moltengold_pickaxe" -> pickaxe(1255, 16.0F, 3.0F, 1, 23, "moltengold_ingot", 1F, -2.6F);
		case "moltengold_axe" -> axe(251, 14.0F, 7.0F, 1, 23, "moltengold_ingot", 1.0F, -3.0F);
		case "moltengold_shovel" -> shovel(251, 14.0F, 2.5F, 1, 23, "moltengold_ingot", 1.0F, -2.9F);
		case "moltengold_hoe" -> hoe(251, 14.0F, 0.0F, 1, 23, "moltengold_ingot", 0F, -0.5F);
		case "meltdream_pickaxe" -> pickaxe(1728, 14.0F, 4.5F, 5, 12, "meltdream_crystal", 1F, -2.8F);
		case "meltdream_axe" -> axe(1728, 14.0F, 9.0F, 5, 12, "meltdream_crystal", 1.0F, -3.0F);
		case "meltdream_shovel" -> shovel(1728, 14.0F, 5.0F, 5, 12, "meltdream_crystal", 1.0F, -3.0F);
		case "meltdream_hoe" -> hoe(1728, 14.0F, 1.0F, 5, 12, "meltdream_crystal", 0F, 0.5F);
		case "grass_sword" -> sword(874, 1.0F, 3.0F, 1, 16, "jungle_spore", 3F, -2.5F);
		case "true_grass_sword" -> sword(1311, 1.0F, 3.5F, 1, 16, "jungle_spore", 3F, -2.5F);
		case "tide_sword" -> sword(1561, 4.0F, 4.5F, 1, 11, "", 3F, -2.8F);
		case "true_tide_sword" -> sword(1561, 4.0F, 5.0F, 1, 11, "", 3F, -2.8F);
		case "desert_sword" -> sword(1561, 1.0F, 7.0F, 1, 8, "", 3F, -3.1F);
		case "true_desert_sword" -> sword(1561, 1.0F, 8.0F, 1, 8, "", 3F, -3.1F);
		case "broken_hero_sword" -> sword(100, 1.0F, 3.0F, 1, 0, "", 3F, -2.4F);
		case "shadow_erosion_sword" -> sword(1725, 4.0F, 2.5F, 1, 2, "black_metal_ingot", 3F, -1.0F);
		case "shadow_erosion_pickaxe" -> pickaxe(1725, 13.0F, 4.0F, 4, 16, "black_metal_ingot", 1F, -2.8F);
		case "shadow_erosion_axe" -> axe(1725, 13.0F, 9.0F, 4, 16, "black_metal_ingot", 1.0F, -3.0F);
		case "shadow_erosion_hoe" -> hoe(1725, 13.0F, 1.5F, 4, 16, "black_metal_ingot", 0F, 0.0F);
		case "shadow_erosion_shovel" -> shovel(1725, 13.0F, 5.0F, 4, 16, "black_metal_ingot", 1.0F, -3.0F);
		case "white_sword" -> sword(1771, 0.0F, 5.0F, 1, 10, "", 3F, -2.4F);
		case "shadow_sword" -> sword(1771, 0.0F, 8.0F, 1, 10, "nightmare_fuel", 3F, -2.4F);
		case "iceshadow_hammer" -> sword(835, 4.0F, 9.0F, 1, 2, "", 3F, -3.3F);
		case "moltengold_wand" -> projectile("projectile_moltengold_wand_projectile", 1, 1.5F, 5.0F, "magic_stone");
		case "true_moltengold_wand" -> projectile("projectile_true_moltengold_wand_projectile", 1, 1.6F, 7.0F, "magic_stone");
		case "truest_moltengold_wand" -> projectile("projectile_truest_moltengold_wand_projectile", 1, 1.7F, 10.0F, "magic_stone");
		case "squeal_wave_wand" -> projectile("projectile_squeal_wave_wand_projectile", 1, 1.2F, 2.0F, "magic_stone");
		case "shadow_vortex_book" -> projectile("projectile_shadow_vortex_book_projectile", 1, 1.0F, 3.0F, "magic_stone");
		case "storage_bag" -> item(1, Rarity.COMMON);
		case "storage_bag_0" -> item(1, Rarity.COMMON);
		case "embryo_ring" -> curio("embryo_ring", 1, Rarity.COMMON);
		case "embryo_necklace" -> curio("embryo_necklace", 1, Rarity.COMMON);
		case "embryo_belt" -> curio("embryo_belt", 1, Rarity.COMMON);
		case "embryo_charm" -> curio("embryo_charm", 1, Rarity.COMMON);
		case "red_dew_0_ring" -> curio("red_dew_0_ring", 1, Rarity.COMMON);
		case "red_dew_1_ring" -> curio("red_dew_1_ring", 1, Rarity.COMMON);
		case "red_dew_2_ring" -> curio("red_dew_2_ring", 1, Rarity.COMMON);
		case "red_dew_3_ring" -> curio("red_dew_3_ring", 1, Rarity.COMMON);
		case "hithard_0_ring" -> curio("hithard_0_ring", 1, Rarity.COMMON);
		case "hithard_1_ring" -> curio("hithard_1_ring", 1, Rarity.COMMON);
		case "counter_ring" -> curio("counter_ring", 1, Rarity.COMMON);
		case "meltdream_energy_0_ring" -> curio("meltdream_energy_0_ring", 1, Rarity.COMMON);
		case "rabbit_0_necklace" -> curio("rabbit_0_necklace", 1, Rarity.COMMON);
		case "feather_necklace" -> curio("feather_necklace", 1, Rarity.COMMON);
		case "health_0_necklace" -> curio("health_0_necklace", 1, Rarity.COMMON);
		case "fire_0_necklace" -> curio("fire_0_necklace", 1, Rarity.COMMON);
		case "cross_necklace" -> curio("cross_necklace", 1, Rarity.COMMON);
		case "nature_belt" -> curio("nature_belt", 1, Rarity.COMMON);
		case "traveler_belt" -> curio("traveler_belt", 1, Rarity.COMMON);
		case "dream_traveler_belt" -> curio("dream_traveler_belt", 1, Rarity.COMMON);
		case "gold_charm" -> curio("gold_charm", 1, Rarity.COMMON);
		case "end_eye_charm" -> curio("end_eye_charm", 1, Rarity.COMMON);
		case "sea_charm" -> curio("sea_charm", 1, Rarity.COMMON);
		case "carapax_charm" -> curio("carapax_charm", 1, Rarity.COMMON);
		case "world_tree_seedpod" -> curio("world_tree_seedpod", 1, Rarity.COMMON);
		case "garland" -> curio("garland", 1, Rarity.COMMON);
		case "ghost_face_head" -> curio("ghost_face_head", 1, Rarity.COMMON);
		case "qym_head" -> curio("qym_head", 1, Rarity.COMMON);
		case "white_flower_body" -> curio("white_flower_body", 1, Rarity.COMMON);
		case "degenerate_bodies" -> curio("degenerate_bodies", 1, Rarity.COMMON);
		case "machine_wing_chestplate" -> armor("machine_wing", ArmorItem.Type.CHESTPLATE, 34, false, false, false);
		case "dark_allegory_curio" -> curio("dark_allegory_curio", 1, Rarity.COMMON);
		case "calais_spice_bottle_curio" -> curio("calais_spice_bottle_curio", 64, Rarity.COMMON);
		case "four_leaf_clover_curio" -> curio("four_leaf_clover_curio", 1, Rarity.COMMON);
		case "snow_vow_head" -> curio("snow_vow_head", 1, Rarity.COMMON);
		case "bright_butterfly_curio" -> curio("bright_butterfly_curio", 1, Rarity.COMMON);
		case "iceshadow_curio" -> curio("iceshadow_curio", 1, Rarity.COMMON);
		case "boboji_curio" -> curio("boboji_curio", 1, Rarity.COMMON);
		case "paper_plane" -> curio("paper_plane", 1, Rarity.COMMON);
		case "wind_knight_flag" -> curio("wind_knight_flag", 1, Rarity.COMMON);
		case "qym_armor_helmet" -> armor("qym_armor", ArmorItem.Type.HELMET, 1, false, true, true);
		case "qym_armor_chestplate" -> armor("qym_armor", ArmorItem.Type.CHESTPLATE, 1, false, true, true);
		case "qym_armor_leggings" -> armor("qym_armor", ArmorItem.Type.LEGGINGS, 1, false, true, true);
		case "qym_armor_boots" -> armor("qym_armor", ArmorItem.Type.BOOTS, 1, false, true, true);
		case "bubble_tea" -> food(64, Rarity.COMMON, 5, 0.7F, false, 32, UseAnim.DRINK);
		case "all_kinds_ring" -> curio("all_kinds_ring", 1, Rarity.COMMON);
		case "terra_sword" -> sword(2011, 4.0F, 4.0F, 1, 2, "", 3F, -2.4F);
		case "terra_charm" -> curio("terra_charm", 1, Rarity.COMMON);
		case "hiyori_head" -> curio("hiyori_head", 1, Rarity.COMMON);
		case "cecilia_care_charm" -> curio("cecilia_care_charm", 1, Rarity.COMMON);
		case "thermal_dagger" -> sword(1721, 1.0F, 2.5F, 0, 2, "titanium_nugget", 3F, -2.3F);
		case "duke_coin_curio" -> curio("duke_coin_curio", 1, Rarity.COMMON);
		case "turnback_cloak" -> curio("turnback_cloak", 1, Rarity.COMMON);
		case "evasion_cloak" -> curio("evasion_cloak", 1, Rarity.COMMON);
		case "strawberry_heart" -> projectile("projectile_strawberry_heart_project", 1, 1.0F, 0.0F, "magic_stone");
		case "cradle_in_ones_arms" -> item(1, Rarity.COMMON);
		case "forsakens_wing_chestplate" -> armor("forsakens_wing", ArmorItem.Type.CHESTPLATE, 30, false, false, false);
		case "angel_wing_chestplate" -> armor("angel_wing", ArmorItem.Type.CHESTPLATE, 30, false, false, false);
		case "dream_note_0" -> item(1, Rarity.COMMON);
		case "dream_note_1" -> item(1, Rarity.COMMON);
		case "dream_note_2" -> item(1, Rarity.COMMON);
		case "dream_note_3" -> item(1, Rarity.COMMON);
		case "dream_note_4" -> item(1, Rarity.COMMON);
		case "dream_note_5" -> item(1, Rarity.COMMON);
		case "dream_note_6" -> item(1, Rarity.COMMON);
		case "dream_note_7" -> item(1, Rarity.COMMON);
		case "dream_note_8" -> item(1, Rarity.COMMON);
		case "dream_note_9" -> item(1, Rarity.COMMON);
		case "dream_note_10" -> item(1, Rarity.COMMON);
		case "dream_note_11" -> item(1, Rarity.COMMON);
		case "dream_note_12" -> item(1, Rarity.COMMON);
		case "dream_note_13" -> item(1, Rarity.COMMON);
		case "dream_note_14" -> item(1, Rarity.COMMON);
		case "shadow_blast_furnace_blueprint" -> item(1, Rarity.COMMON);
		case "weapon_workshop_blueprint" -> item(1, Rarity.COMMON);
		case "talent_shadow" -> item(1, Rarity.COMMON);
		case "talent_light" -> item(1, Rarity.COMMON);
		case "squeal_wave" -> projectile("projectile_squeal_wave_projectile", 16, 1.4F, 1.0F, null);
		case "white_sword_rain" -> projectile("projectile_white_sword_rain_projectile", 8, 1.2F, 4.0F, null);
		case "bone_wing_fireball" -> projectile("projectile_bone_wing_fire_ball_projectile", 16, 1.2F, 2.0F, "minecraft:fire_charge");
		case "lightning_strike_item" -> projectile("projectile_lightning_projectile", 16, 1.2F, 1.0F, null);
		case "moss_phantom_membrane" -> curio("moss_phantom_membrane", 1, Rarity.COMMON);
		case "light_moss_phantom_membrane" -> curio("light_moss_phantom_membrane", 1, Rarity.COMMON);
		case "shadow_breath" -> curio("shadow_breath", 1, Rarity.COMMON);
		case "light_butterfly_curio" -> curio("light_butterfly_curio", 1, Rarity.COMMON);
		case "shadow_hand_lantern" -> geoItem(1, Rarity.COMMON);
			default -> new Item(new Item.Properties());
		};
	}

	private static Item.Properties properties(int stack, Rarity rarity) {
		return new Item.Properties().stacksTo(stack).rarity(rarity);
	}

	private static Item item(int stack, Rarity rarity) {
		return new Item(properties(stack, rarity));
	}

	private static Item curio(String id, int stack, Rarity rarity) {
		Item.Properties properties = new Item.Properties().stacksTo(stack).rarity(rarity);
		if (id.equals("garland") || id.equals("nature_belt")) {
			properties.durability(200);
		}
		return new PasterDreamCurioItem(id, properties);
	}

	private static Item geoItem(int stack, Rarity rarity) {
		return new PasterDreamGeoItem(properties(stack, rarity));
	}

	private static Item projectile(
		String projectileId,
		int stack,
		float velocity,
		float damage,
		String ammoId
	) {
		return new PasterDreamProjectileItem(
			properties(stack, Rarity.COMMON),
			projectileId,
			velocity,
			damage,
			ammoId
		);
	}

	private static Item armor(
		String materialId,
		ArmorItem.Type type,
		int durability,
		boolean fireResistant,
		boolean unbreakable,
		boolean sanArmor
	) {
		Item.Properties properties = new Item.Properties().durability(type.getDurability(durability));
		if (fireResistant) {
			properties.fireResistant();
		}
		if (unbreakable) {
			properties.component(DataComponents.UNBREAKABLE, new Unbreakable(true));
		}
		ArmorMaterial material = BuiltInRegistries.ARMOR_MATERIAL
			.getHolder(PasterDream.id(materialId))
			.orElseThrow()
			.value();
		return new PasterDreamArmorItem(
			BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(material),
			type,
			properties,
			sanArmor
		);
	}

	private static Item food(
		int stack,
		Rarity rarity,
		int nutrition,
		float saturation,
		boolean alwaysEdible,
		int useDuration,
		UseAnim animation
	) {
		FoodProperties.Builder food = new FoodProperties.Builder()
			.nutrition(nutrition)
			.saturationModifier(saturation);
		if (alwaysEdible) {
			food.alwaysEdible();
		}
		return new PasterDreamFoodItem(
			properties(stack, rarity).food(food.build()),
			useDuration,
			animation
		);
	}

	private static Tier tier(
		int uses,
		float speed,
		float damage,
		int level,
		int enchantment,
		String repairItem
	) {
		return new PasterDreamToolTier(
			uses,
			speed,
			damage,
			level >= 4 ? BlockTags.INCORRECT_FOR_NETHERITE_TOOL
				: level == 3 ? BlockTags.INCORRECT_FOR_DIAMOND_TOOL
				: level == 2 ? BlockTags.INCORRECT_FOR_IRON_TOOL
				: level == 1 ? BlockTags.INCORRECT_FOR_STONE_TOOL
				: BlockTags.INCORRECT_FOR_WOODEN_TOOL,
			enchantment,
			repairItem
		);
	}

	private static Item sword(
		int uses, float speed, float damage, int level, int enchantment,
		String repairItem, float attack, float attackSpeed
	) {
		Tier material = tier(uses, speed, damage, level, enchantment, repairItem);
		return new SwordItem(
			material,
			new Item.Properties().attributes(SwordItem.createAttributes(material, Math.round(attack), attackSpeed))
		);
	}

	private static Item pickaxe(
		int uses, float speed, float damage, int level, int enchantment,
		String repairItem, float attack, float attackSpeed
	) {
		Tier material = tier(uses, speed, damage, level, enchantment, repairItem);
		return new PickaxeItem(
			material,
			new Item.Properties().attributes(PickaxeItem.createAttributes(material, attack, attackSpeed))
		);
	}

	private static Item axe(
		int uses, float speed, float damage, int level, int enchantment,
		String repairItem, float attack, float attackSpeed
	) {
		Tier material = tier(uses, speed, damage, level, enchantment, repairItem);
		return new AxeItem(
			material,
			new Item.Properties().attributes(AxeItem.createAttributes(material, attack, attackSpeed))
		);
	}

	private static Item shovel(
		int uses, float speed, float damage, int level, int enchantment,
		String repairItem, float attack, float attackSpeed
	) {
		Tier material = tier(uses, speed, damage, level, enchantment, repairItem);
		return new ShovelItem(
			material,
			new Item.Properties().attributes(ShovelItem.createAttributes(material, attack, attackSpeed))
		);
	}

	private static Item hoe(
		int uses, float speed, float damage, int level, int enchantment,
		String repairItem, float attack, float attackSpeed
	) {
		Tier material = tier(uses, speed, damage, level, enchantment, repairItem);
		return new HoeItem(
			material,
			new Item.Properties().attributes(HoeItem.createAttributes(material, attack, attackSpeed))
		);
	}
}
