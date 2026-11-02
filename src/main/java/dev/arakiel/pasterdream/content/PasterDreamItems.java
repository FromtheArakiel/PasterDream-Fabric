package dev.arakiel.pasterdream.content;

import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.gemini.registration.container.ItemRegistryContainer;
import dev.arakiel.pasterdream.PasterDream;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamItems implements ItemRegistryContainer {
    @AssignedName("meltdream_crystal")
    public static final Item MELTDREAM_CRYSTAL = PasterDreamItemFactory.create("meltdream_crystal");
    @AssignedName("dyedream_dust")
    public static final Item DYEDREAM_DUST = PasterDreamItemFactory.create("dyedream_dust");
    @AssignedName("dyedream_dust_piece")
    public static final Item DYEDREAM_DUST_PIECE = PasterDreamItemFactory.create("dyedream_dust_piece");
    @AssignedName("dyedream_dye")
    public static final Item DYEDREAM_DYE = PasterDreamItemFactory.create("dyedream_dye");
    @AssignedName("dyedream_bud_nugget")
    public static final Item DYEDREAM_BUD_NUGGET = PasterDreamItemFactory.create("dyedream_bud_nugget");
    @AssignedName("dyedream_quartz")
    public static final Item DYEDREAM_QUARTZ = PasterDreamItemFactory.create("dyedream_quartz");
    @AssignedName("dream_coin")
    public static final Item DREAM_COIN = PasterDreamItemFactory.create("dream_coin");
    @AssignedName("dream_coin_bundle")
    public static final Item DREAM_COIN_BUNDLE = PasterDreamItemFactory.create("dream_coin_bundle");
    @AssignedName("raw_titanium")
    public static final Item RAW_TITANIUM = PasterDreamItemFactory.create("raw_titanium");
    @AssignedName("raw_moltengold")
    public static final Item RAW_MOLTENGOLD = PasterDreamItemFactory.create("raw_moltengold");
    @AssignedName("titanium_ingot")
    public static final Item TITANIUM_INGOT = PasterDreamItemFactory.create("titanium_ingot");
    @AssignedName("moltengold_ingot")
    public static final Item MOLTENGOLD_INGOT = PasterDreamItemFactory.create("moltengold_ingot");
    @AssignedName("dyedream_base")
    public static final Item DYEDREAM_BASE = PasterDreamItemFactory.create("dyedream_base");
    @AssignedName("dyedream_ingot")
    public static final Item DYEDREAM_INGOT = PasterDreamItemFactory.create("dyedream_ingot");
    @AssignedName("wind_iron_ingot")
    public static final Item WIND_IRON_INGOT = PasterDreamItemFactory.create("wind_iron_ingot");
    @AssignedName("titanium_nugget")
    public static final Item TITANIUM_NUGGET = PasterDreamItemFactory.create("titanium_nugget");
    @AssignedName("moltengold_nugget")
    public static final Item MOLTENGOLD_NUGGET = PasterDreamItemFactory.create("moltengold_nugget");
    @AssignedName("dyedream_nugget")
    public static final Item DYEDREAM_NUGGET = PasterDreamItemFactory.create("dyedream_nugget");
    @AssignedName("moltengold_dust")
    public static final Item MOLTENGOLD_DUST = PasterDreamItemFactory.create("moltengold_dust");
    @AssignedName("soul_dust")
    public static final Item SOUL_DUST = PasterDreamItemFactory.create("soul_dust");
    @AssignedName("soul_essence")
    public static final Item SOUL_ESSENCE = PasterDreamItemFactory.create("soul_essence");
    @AssignedName("magic_stone")
    public static final Item MAGIC_STONE = PasterDreamItemFactory.create("magic_stone");
    @AssignedName("windrunner_crystal")
    public static final Item WINDRUNNER_CRYSTAL = PasterDreamItemFactory.create("windrunner_crystal");
    @AssignedName("pulse_windrunner_crystal")
    public static final Item PULSE_WINDRUNNER_CRYSTAL = PasterDreamItemFactory.create("pulse_windrunner_crystal");
    @AssignedName("congeal_wind")
    public static final Item CONGEAL_WIND = PasterDreamItemFactory.create("congeal_wind");
    @AssignedName("pink_slime_ball")
    public static final Item PINK_SLIME_BALL = PasterDreamItemFactory.create("pink_slime_ball");
    @AssignedName("dream_fertilizer")
    public static final Item DREAM_FERTILIZER = PasterDreamItemFactory.create("dream_fertilizer");
    @AssignedName("pink_egg")
    public static final Item PINK_EGG = PasterDreamItemFactory.create("pink_egg");
    @AssignedName("dyedream_corolla")
    public static final Item DYEDREAM_COROLLA = PasterDreamItemFactory.create("dyedream_corolla");
    @AssignedName("white_corolla")
    public static final Item WHITE_COROLLA = PasterDreamItemFactory.create("white_corolla");
    @AssignedName("red_dew")
    public static final Item RED_DEW = PasterDreamItemFactory.create("red_dew");
    @AssignedName("blue_dew")
    public static final Item BLUE_DEW = PasterDreamItemFactory.create("blue_dew");
    @AssignedName("blue_heart_of_the_sea")
    public static final Item BLUE_HEART_OF_THE_SEA = PasterDreamItemFactory.create("blue_heart_of_the_sea");
    @AssignedName("elder_guardian_scale")
    public static final Item ELDER_GUARDIAN_SCALE = PasterDreamItemFactory.create("elder_guardian_scale");
    @AssignedName("black_beetle_carapace")
    public static final Item BLACK_BEETLE_CARAPACE = PasterDreamItemFactory.create("black_beetle_carapace");
    @AssignedName("black_beetle_vocalcord")
    public static final Item BLACK_BEETLE_VOCALCORD = PasterDreamItemFactory.create("black_beetle_vocalcord");
    @AssignedName("charged_amethyst")
    public static final Item CHARGED_AMETHYST = PasterDreamItemFactory.create("charged_amethyst");
    @AssignedName("jungle_spore")
    public static final Item JUNGLE_SPORE = PasterDreamItemFactory.create("jungle_spore");
    @AssignedName("protect_deck")
    public static final Item PROTECT_DECK = PasterDreamItemFactory.create("protect_deck");
    @AssignedName("cotton")
    public static final Item COTTON = PasterDreamItemFactory.create("cotton");
    @AssignedName("spool")
    public static final Item SPOOL = PasterDreamItemFactory.create("spool");
    @AssignedName("fabric")
    public static final Item FABRIC = PasterDreamItemFactory.create("fabric");
    @AssignedName("pergamyn")
    public static final Item PERGAMYN = PasterDreamItemFactory.create("pergamyn");
    @AssignedName("pen_and_ink")
    public static final Item PEN_AND_INK = PasterDreamItemFactory.create("pen_and_ink");
    @AssignedName("angel_block_placer")
    public static final Item ANGEL_BLOCK_PLACER = PasterDreamItemFactory.create("angel_block_placer");
    @AssignedName("reed_rod")
    public static final Item REED_ROD = PasterDreamItemFactory.create("reed_rod");
    @AssignedName("rye_seed")
    public static final Item RYE_SEED = PasterDreamItemFactory.create("rye_seed");
    @AssignedName("flour")
    public static final Item FLOUR = PasterDreamItemFactory.create("flour");
    @AssignedName("salt")
    public static final Item SALT = PasterDreamItemFactory.create("salt");
    @AssignedName("coarse_salt")
    public static final Item COARSE_SALT = PasterDreamItemFactory.create("coarse_salt");
    @AssignedName("sorbent")
    public static final Item SORBENT = PasterDreamItemFactory.create("sorbent");
    @AssignedName("wind_plant_extract")
    public static final Item WIND_PLANT_EXTRACT = PasterDreamItemFactory.create("wind_plant_extract");
    @AssignedName("meltdream_liquid_bucket")
    public static final Item MELTDREAM_LIQUID_BUCKET = PasterDreamItemFactory.create("meltdream_liquid_bucket");
    @AssignedName("shadow_liquid_bucket")
    public static final Item SHADOW_LIQUID_BUCKET = PasterDreamItemFactory.create("shadow_liquid_bucket");
    @AssignedName("pale_boneneedle")
    public static final Item PALE_BONENEEDLE = PasterDreamItemFactory.create("pale_boneneedle");
    @AssignedName("roots_pale_boneneedle")
    public static final Item ROOTS_PALE_BONENEEDLE = PasterDreamItemFactory.create("roots_pale_boneneedle");
    @AssignedName("mortar")
    public static final Item MORTAR = PasterDreamItemFactory.create("mortar");
    @AssignedName("basalt_snail_shell")
    public static final Item BASALT_SNAIL_SHELL = PasterDreamItemFactory.create("basalt_snail_shell");
    @AssignedName("sculk_heart")
    public static final Item SCULK_HEART = PasterDreamItemFactory.create("sculk_heart");
    @AssignedName("black_stick")
    public static final Item BLACK_STICK = PasterDreamItemFactory.create("black_stick");
    @AssignedName("sword_embryo")
    public static final Item SWORD_EMBRYO = PasterDreamItemFactory.create("sword_embryo");
    @AssignedName("nightmare_fuel")
    public static final Item NIGHTMARE_FUEL = PasterDreamItemFactory.create("nightmare_fuel");
    @AssignedName("rust_black_metal_grain")
    public static final Item RUST_BLACK_METAL_GRAIN = PasterDreamItemFactory.create("rust_black_metal_grain");
    @AssignedName("black_metal_grain")
    public static final Item BLACK_METAL_GRAIN = PasterDreamItemFactory.create("black_metal_grain");
    @AssignedName("black_metal_ingot")
    public static final Item BLACK_METAL_INGOT = PasterDreamItemFactory.create("black_metal_ingot");
    @AssignedName("broken_notes")
    public static final Item BROKEN_NOTES = PasterDreamItemFactory.create("broken_notes");
    @AssignedName("unknown_notes")
    public static final Item UNKNOWN_NOTES = PasterDreamItemFactory.create("unknown_notes");
    @AssignedName("shadow_dungeon_key")
    public static final Item SHADOW_DUNGEON_KEY = PasterDreamItemFactory.create("shadow_dungeon_key");
    @AssignedName("pure_horror")
    public static final Item PURE_HORROR = PasterDreamItemFactory.create("pure_horror");
    @AssignedName("white_crystal")
    public static final Item WHITE_CRYSTAL = PasterDreamItemFactory.create("white_crystal");
    @AssignedName("shadow_hilt")
    public static final Item SHADOW_HILT = PasterDreamItemFactory.create("shadow_hilt");
    @AssignedName("time_hourglass")
    public static final Item TIME_HOURGLASS = PasterDreamItemFactory.create("time_hourglass");
    @AssignedName("wind_vane")
    public static final Item WIND_VANE = PasterDreamItemFactory.create("wind_vane");
    @AssignedName("dream_wand_embryo")
    public static final Item DREAM_WAND_EMBRYO = PasterDreamItemFactory.create("dream_wand_embryo");
    @AssignedName("terra_sword_embryo")
    public static final Item TERRA_SWORD_EMBRYO = PasterDreamItemFactory.create("terra_sword_embryo");
    @AssignedName("shadow_erosion_sword_embryo")
    public static final Item SHADOW_EROSION_SWORD_EMBRYO = PasterDreamItemFactory.create("shadow_erosion_sword_embryo");
    @AssignedName("shadow_erosion_pickaxe_embryo")
    public static final Item SHADOW_EROSION_PICKAXE_EMBRYO = PasterDreamItemFactory.create("shadow_erosion_pickaxe_embryo");
    @AssignedName("shadow_erosion_axe_embryo")
    public static final Item SHADOW_EROSION_AXE_EMBRYO = PasterDreamItemFactory.create("shadow_erosion_axe_embryo");
    @AssignedName("shadow_erosion_hoe_embryo")
    public static final Item SHADOW_EROSION_HOE_EMBRYO = PasterDreamItemFactory.create("shadow_erosion_hoe_embryo");
    @AssignedName("shadow_erosion_shovel_embryo")
    public static final Item SHADOW_EROSION_SHOVEL_EMBRYO = PasterDreamItemFactory.create("shadow_erosion_shovel_embryo");
    @AssignedName("iceshadow_hammer_embryo")
    public static final Item ICESHADOW_HAMMER_EMBRYO = PasterDreamItemFactory.create("iceshadow_hammer_embryo");
    @AssignedName("white_sword_embryo")
    public static final Item WHITE_SWORD_EMBRYO = PasterDreamItemFactory.create("white_sword_embryo");
    @AssignedName("shadow_sword_embryo")
    public static final Item SHADOW_SWORD_EMBRYO = PasterDreamItemFactory.create("shadow_sword_embryo");
    @AssignedName("star_wish_rod_embryo")
    public static final Item STAR_WISH_ROD_EMBRYO = PasterDreamItemFactory.create("star_wish_rod_embryo");
    @AssignedName("deep_treasure_map_0")
    public static final Item DEEP_TREASURE_MAP_0 = PasterDreamItemFactory.create("deep_treasure_map_0");
    @AssignedName("deep_treasure_map_1")
    public static final Item DEEP_TREASURE_MAP_1 = PasterDreamItemFactory.create("deep_treasure_map_1");
    @AssignedName("enhancement_stone_0")
    public static final Item ENHANCEMENT_STONE_0 = PasterDreamItemFactory.create("enhancement_stone_0");
    @AssignedName("enhancement_stone_1")
    public static final Item ENHANCEMENT_STONE_1 = PasterDreamItemFactory.create("enhancement_stone_1");
    @AssignedName("turn_pale_cecilia")
    public static final Item TURN_PALE_CECILIA = PasterDreamItemFactory.create("turn_pale_cecilia");
    @AssignedName("calle_card_0")
    public static final Item CALLE_CARD_0 = PasterDreamItemFactory.create("calle_card_0");
    @AssignedName("calle_card_1")
    public static final Item CALLE_CARD_1 = PasterDreamItemFactory.create("calle_card_1");
    @AssignedName("calle_card_2")
    public static final Item CALLE_CARD_2 = PasterDreamItemFactory.create("calle_card_2");
    @AssignedName("calle_card_3")
    public static final Item CALLE_CARD_3 = PasterDreamItemFactory.create("calle_card_3");
    @AssignedName("calle_card_4")
    public static final Item CALLE_CARD_4 = PasterDreamItemFactory.create("calle_card_4");
    @AssignedName("calle_card_5")
    public static final Item CALLE_CARD_5 = PasterDreamItemFactory.create("calle_card_5");
    @AssignedName("calle_card_6")
    public static final Item CALLE_CARD_6 = PasterDreamItemFactory.create("calle_card_6");
    @AssignedName("calle_card_7")
    public static final Item CALLE_CARD_7 = PasterDreamItemFactory.create("calle_card_7");
    @AssignedName("calle_card_8")
    public static final Item CALLE_CARD_8 = PasterDreamItemFactory.create("calle_card_8");
    @AssignedName("calle_card_9")
    public static final Item CALLE_CARD_9 = PasterDreamItemFactory.create("calle_card_9");
    @AssignedName("sweet_dream_music_disc")
    public static final Item SWEET_DREAM_MUSIC_DISC = PasterDreamItemFactory.create("sweet_dream_music_disc");
    @AssignedName("snowfall_dream_music_disc")
    public static final Item SNOWFALL_DREAM_MUSIC_DISC = PasterDreamItemFactory.create("snowfall_dream_music_disc");
    @AssignedName("aaroncos_music_disc")
    public static final Item AARONCOS_MUSIC_DISC = PasterDreamItemFactory.create("aaroncos_music_disc");
    @AssignedName("wind_journey_music_disc")
    public static final Item WIND_JOURNEY_MUSIC_DISC = PasterDreamItemFactory.create("wind_journey_music_disc");
    @AssignedName("lightning_spell")
    public static final Item LIGHTNING_SPELL = PasterDreamItemFactory.create("lightning_spell");
    @AssignedName("poison_spell")
    public static final Item POISON_SPELL = PasterDreamItemFactory.create("poison_spell");
    @AssignedName("healing_spell")
    public static final Item HEALING_SPELL = PasterDreamItemFactory.create("healing_spell");
    @AssignedName("fury_spell")
    public static final Item FURY_SPELL = PasterDreamItemFactory.create("fury_spell");
    @AssignedName("ice_spell")
    public static final Item ICE_SPELL = PasterDreamItemFactory.create("ice_spell");
    @AssignedName("glass_jar")
    public static final Item GLASS_JAR = PasterDreamItemFactory.create("glass_jar");
    @AssignedName("water_glass_jar")
    public static final Item WATER_GLASS_JAR = PasterDreamItemFactory.create("water_glass_jar");
    @AssignedName("milk_glass_jar")
    public static final Item MILK_GLASS_JAR = PasterDreamItemFactory.create("milk_glass_jar");
    @AssignedName("yeast")
    public static final Item YEAST = PasterDreamItemFactory.create("yeast");
    @AssignedName("guiding_drug")
    public static final Item GUIDING_DRUG = PasterDreamItemFactory.create("guiding_drug");
    @AssignedName("dough")
    public static final Item DOUGH = PasterDreamItemFactory.create("dough");
    @AssignedName("egg_dough")
    public static final Item EGG_DOUGH = PasterDreamItemFactory.create("egg_dough");
    @AssignedName("bread_slice")
    public static final Item BREAD_SLICE = PasterDreamItemFactory.create("bread_slice");
    @AssignedName("cake_base")
    public static final Item CAKE_BASE = PasterDreamItemFactory.create("cake_base");
    @AssignedName("wafer_biscuit")
    public static final Item WAFER_BISCUIT = PasterDreamItemFactory.create("wafer_biscuit");
    @AssignedName("dyedream_fruit")
    public static final Item DYEDREAM_FRUIT = PasterDreamItemFactory.create("dyedream_fruit");
    @AssignedName("fig")
    public static final Item FIG = PasterDreamItemFactory.create("fig");
    @AssignedName("dream_juice")
    public static final Item DREAM_JUICE = PasterDreamItemFactory.create("dream_juice");
    @AssignedName("amber_candy")
    public static final Item AMBER_CANDY = PasterDreamItemFactory.create("amber_candy");
    @AssignedName("glass_cup")
    public static final Item GLASS_CUP = PasterDreamItemFactory.create("glass_cup");
    @AssignedName("queer_soup")
    public static final Item QUEER_SOUP = PasterDreamItemFactory.create("queer_soup");
    @AssignedName("uncooked_dyedream_flower_tea")
    public static final Item UNCOOKED_DYEDREAM_FLOWER_TEA = PasterDreamItemFactory.create("uncooked_dyedream_flower_tea");
    @AssignedName("dyedream_flower_tea")
    public static final Item DYEDREAM_FLOWER_TEA = PasterDreamItemFactory.create("dyedream_flower_tea");
    @AssignedName("dyedream_juice")
    public static final Item DYEDREAM_JUICE = PasterDreamItemFactory.create("dyedream_juice");
    @AssignedName("apple_juice")
    public static final Item APPLE_JUICE = PasterDreamItemFactory.create("apple_juice");
    @AssignedName("honey_juice")
    public static final Item HONEY_JUICE = PasterDreamItemFactory.create("honey_juice");
    @AssignedName("watermelon_juice")
    public static final Item WATERMELON_JUICE = PasterDreamItemFactory.create("watermelon_juice");
    @AssignedName("dyedream_popsicle")
    public static final Item DYEDREAM_POPSICLE = PasterDreamItemFactory.create("dyedream_popsicle");
    @AssignedName("fried_egg")
    public static final Item FRIED_EGG = PasterDreamItemFactory.create("fried_egg");
    @AssignedName("bacon_egg")
    public static final Item BACON_EGG = PasterDreamItemFactory.create("bacon_egg");
    @AssignedName("rice_cake")
    public static final Item RICE_CAKE = PasterDreamItemFactory.create("rice_cake");
    @AssignedName("sandwich")
    public static final Item SANDWICH = PasterDreamItemFactory.create("sandwich");
    @AssignedName("chocolate")
    public static final Item CHOCOLATE = PasterDreamItemFactory.create("chocolate");
    @AssignedName("popping_candy")
    public static final Item POPPING_CANDY = PasterDreamItemFactory.create("popping_candy");
    @AssignedName("chocolate_matcha_cake")
    public static final Item CHOCOLATE_MATCHA_CAKE = PasterDreamItemFactory.create("chocolate_matcha_cake");
    @AssignedName("swiss_roll")
    public static final Item SWISS_ROLL = PasterDreamItemFactory.create("swiss_roll");
    @AssignedName("stuffed_wafer_cookies")
    public static final Item STUFFED_WAFER_COOKIES = PasterDreamItemFactory.create("stuffed_wafer_cookies");
    @AssignedName("cream_buncake")
    public static final Item CREAM_BUNCAKE = PasterDreamItemFactory.create("cream_buncake");
    @AssignedName("berry_buncake")
    public static final Item BERRY_BUNCAKE = PasterDreamItemFactory.create("berry_buncake");
    @AssignedName("potato_buncake")
    public static final Item POTATO_BUNCAKE = PasterDreamItemFactory.create("potato_buncake");
    @AssignedName("melon_buncake")
    public static final Item MELON_BUNCAKE = PasterDreamItemFactory.create("melon_buncake");
    @AssignedName("pumpkin_buncake")
    public static final Item PUMPKIN_BUNCAKE = PasterDreamItemFactory.create("pumpkin_buncake");
    @AssignedName("glow_berry_buncake")
    public static final Item GLOW_BERRY_BUNCAKE = PasterDreamItemFactory.create("glow_berry_buncake");
    @AssignedName("dyedream_fruit_buncake")
    public static final Item DYEDREAM_FRUIT_BUNCAKE = PasterDreamItemFactory.create("dyedream_fruit_buncake");
    @AssignedName("heart_chocolate_0")
    public static final Item HEART_CHOCOLATE_0 = PasterDreamItemFactory.create("heart_chocolate_0");
    @AssignedName("heart_chocolate_1")
    public static final Item HEART_CHOCOLATE_1 = PasterDreamItemFactory.create("heart_chocolate_1");
    @AssignedName("heart_chocolate_2")
    public static final Item HEART_CHOCOLATE_2 = PasterDreamItemFactory.create("heart_chocolate_2");
    @AssignedName("gingerbread_man")
    public static final Item GINGERBREAD_MAN = PasterDreamItemFactory.create("gingerbread_man");
    @AssignedName("candy_cane")
    public static final Item CANDY_CANE = PasterDreamItemFactory.create("candy_cane");
    @AssignedName("bubble_gum")
    public static final Item BUBBLE_GUM = PasterDreamItemFactory.create("bubble_gum");
    @AssignedName("dream_cotton_candy")
    public static final Item DREAM_COTTON_CANDY = PasterDreamItemFactory.create("dream_cotton_candy");
    @AssignedName("yinhul_cotton_candy")
    public static final Item YINHUL_COTTON_CANDY = PasterDreamItemFactory.create("yinhul_cotton_candy");
    @AssignedName("jellyfish_mud")
    public static final Item JELLYFISH_MUD = PasterDreamItemFactory.create("jellyfish_mud");
    @AssignedName("jellyfish_jello")
    public static final Item JELLYFISH_JELLO = PasterDreamItemFactory.create("jellyfish_jello");
    @AssignedName("elixir_bottle")
    public static final Item ELIXIR_BOTTLE = PasterDreamItemFactory.create("elixir_bottle");
    @AssignedName("meltdream_elixir_bottle")
    public static final Item MELTDREAM_ELIXIR_BOTTLE = PasterDreamItemFactory.create("meltdream_elixir_bottle");
    @AssignedName("rage_elixir_0")
    public static final Item RAGE_ELIXIR_0 = PasterDreamItemFactory.create("rage_elixir_0");
    @AssignedName("pineapple_love_sea")
    public static final Item PINEAPPLE_LOVE_SEA = PasterDreamItemFactory.create("pineapple_love_sea");
    @AssignedName("goldenrod_tea")
    public static final Item GOLDENROD_TEA = PasterDreamItemFactory.create("goldenrod_tea");
    @AssignedName("legend_dragon_horn_ice_cream")
    public static final Item LEGEND_DRAGON_HORN_ICE_CREAM = PasterDreamItemFactory.create("legend_dragon_horn_ice_cream");
    @AssignedName("dyedream_perfume")
    public static final Item DYEDREAM_PERFUME = PasterDreamItemFactory.create("dyedream_perfume");
    @AssignedName("dreamwish")
    public static final Item DREAMWISH = PasterDreamItemFactory.create("dreamwish");
    @AssignedName("creative_sword")
    public static final Item CREATIVE_SWORD = PasterDreamItemFactory.create("creative_sword");
    @AssignedName("excavator")
    public static final Item EXCAVATOR = PasterDreamItemFactory.create("excavator");
    @AssignedName("loot_table_creator_0")
    public static final Item LOOT_TABLE_CREATOR_0 = PasterDreamItemFactory.create("loot_table_creator_0");
    @AssignedName("loot_table_creator_1")
    public static final Item LOOT_TABLE_CREATOR_1 = PasterDreamItemFactory.create("loot_table_creator_1");
    @AssignedName("loot_table_creator_2")
    public static final Item LOOT_TABLE_CREATOR_2 = PasterDreamItemFactory.create("loot_table_creator_2");
    @AssignedName("loot_table_creator_3")
    public static final Item LOOT_TABLE_CREATOR_3 = PasterDreamItemFactory.create("loot_table_creator_3");
    @AssignedName("loot_table_creator_4")
    public static final Item LOOT_TABLE_CREATOR_4 = PasterDreamItemFactory.create("loot_table_creator_4");
    @AssignedName("loot_table_creator_5")
    public static final Item LOOT_TABLE_CREATOR_5 = PasterDreamItemFactory.create("loot_table_creator_5");
    @AssignedName("loot_table_creator_6")
    public static final Item LOOT_TABLE_CREATOR_6 = PasterDreamItemFactory.create("loot_table_creator_6");
    @AssignedName("loot_table_creator_7")
    public static final Item LOOT_TABLE_CREATOR_7 = PasterDreamItemFactory.create("loot_table_creator_7");
    @AssignedName("loot_table_creator_8")
    public static final Item LOOT_TABLE_CREATOR_8 = PasterDreamItemFactory.create("loot_table_creator_8");
    @AssignedName("loot_table_creator_9")
    public static final Item LOOT_TABLE_CREATOR_9 = PasterDreamItemFactory.create("loot_table_creator_9");
    @AssignedName("pink_chicken_spawn_egg")
    public static final Item PINK_CHICKEN_SPAWN_EGG = spawnEgg(PasterDreamEntities.PINK_CHICKEN);
    @AssignedName("pink_slime_spawn_egg")
    public static final Item PINK_SLIME_SPAWN_EGG = spawnEgg(PasterDreamEntities.PINK_SLIME);
    @AssignedName("basalt_snail_spawn_egg")
    public static final Item BASALT_SNAIL_SPAWN_EGG = spawnEgg(PasterDreamEntities.BASALT_SNAIL);
    @AssignedName("spore_entity_spawn_egg")
    public static final Item SPORE_ENTITY_SPAWN_EGG = spawnEgg(PasterDreamEntities.SPORE_ENTITY);
    @AssignedName("shadow_golem_spawn_egg")
    public static final Item SHADOW_GOLEM_SPAWN_EGG = spawnEgg(PasterDreamEntities.SHADOW_GOLEM);
    @AssignedName("terrorbeak_spawn_egg")
    public static final Item TERRORBEAK_SPAWN_EGG = spawnEgg(PasterDreamEntities.TERRORBEAK);
    @AssignedName("shadow_hand_spawn_egg")
    public static final Item SHADOW_HAND_SPAWN_EGG = spawnEgg(PasterDreamEntities.SHADOW_HAND);
    @AssignedName("crazy_terrorbeak_spawn_egg")
    public static final Item CRAZY_TERRORBEAK_SPAWN_EGG = spawnEgg(PasterDreamEntities.CRAZY_TERRORBEAK);
    @AssignedName("shadow_ghost_spawn_egg")
    public static final Item SHADOW_GHOST_SPAWN_EGG = spawnEgg(PasterDreamEntities.SHADOW_GHOST);
    @AssignedName("shadow_squeal_ghost_spawn_egg")
    public static final Item SHADOW_SQUEAL_GHOST_SPAWN_EGG = spawnEgg(PasterDreamEntities.SHADOW_SQUEAL_GHOST);
    @AssignedName("shadow_squeal_ghost_0_spawn_egg")
    public static final Item SHADOW_SQUEAL_GHOST_0_SPAWN_EGG = spawnEgg(PasterDreamEntities.SHADOW_SQUEAL_GHOST_0);
    @AssignedName("friendly_ghost_spawn_egg")
    public static final Item FRIENDLY_GHOST_SPAWN_EGG = spawnEgg(PasterDreamEntities.FRIENDLY_GHOST);
    @AssignedName("black_beetle_spawn_egg")
    public static final Item BLACK_BEETLE_SPAWN_EGG = spawnEgg(PasterDreamEntities.BLACK_BEETLE);
    @AssignedName("black_beetle_mother_spawn_egg")
    public static final Item BLACK_BEETLE_MOTHER_SPAWN_EGG = spawnEgg(PasterDreamEntities.BLACK_BEETLE_MOTHER);
    @AssignedName("shadow_npc_0_spawn_egg")
    public static final Item SHADOW_NPC_0_SPAWN_EGG = spawnEgg(PasterDreamEntities.SHADOW_NPC_0);
    @AssignedName("weakness_terrorbeak_spawn_egg")
    public static final Item WEAKNESS_TERRORBEAK_SPAWN_EGG = spawnEgg(PasterDreamEntities.WEAKENESS_TERRORBEAK);
    @AssignedName("aaroncos_left_hand_spawn_egg")
    public static final Item AARONCOS_LEFT_HAND_SPAWN_EGG = spawnEgg(PasterDreamEntities.AARONCOS_LEFTHAND_0);
    @AssignedName("aaroncos_right_hand_spawn_egg")
    public static final Item AARONCOS_RIGHT_HAND_SPAWN_EGG = spawnEgg(PasterDreamEntities.AARONCOS_RIGHTHAND_0);
    @AssignedName("shadow_tune_totem_spawn_egg")
    public static final Item SHADOW_TUNE_TOTEM_SPAWN_EGG = spawnEgg(PasterDreamEntities.SHADOW_TUNE_TOTEM);
    @AssignedName("shaking_crystal_spawn_egg")
    public static final Item SHAKING_CRYSTAL_SPAWN_EGG = spawnEgg(PasterDreamEntities.SHAKING_CRYSTAL);
    @AssignedName("bone_wing_spawn_egg")
    public static final Item BONE_WING_SPAWN_EGG = spawnEgg(PasterDreamEntities.BONE_WING);
    @AssignedName("ash_bone_wing_spawn_egg")
    public static final Item ASH_BONE_WING_SPAWN_EGG = spawnEgg(PasterDreamEntities.ASH_BONE_WING);
    @AssignedName("firefly_spawn_egg")
    public static final Item FIREFLY_SPAWN_EGG = spawnEgg(PasterDreamEntities.FIREFLY);
    @AssignedName("thundercloud_spawn_egg")
    public static final Item THUNDERCLOUD_SPAWN_EGG = spawnEgg(PasterDreamEntities.THUNDERCLOUD);
    @AssignedName("highvoltage_thundercloud_spawn_egg")
    public static final Item HIGHVOLTAGE_THUNDERCLOUD_SPAWN_EGG = spawnEgg(PasterDreamEntities.HIGHVOLTAGE_THUNDERCLOUD);
    @AssignedName("jellyfish_spawn_egg")
    public static final Item JELLYFISH_SPAWN_EGG = spawnEgg(PasterDreamEntities.JELLYFISH);
    @AssignedName("wind_knight_spawn_egg")
    public static final Item WIND_KNIGHT_SPAWN_EGG = spawnEgg(PasterDreamEntities.WIND_KNIGHT);
    @AssignedName("golden_fox_spawn_egg")
    public static final Item GOLDEN_FOX_SPAWN_EGG = spawnEgg(PasterDreamEntities.GOLDEN_FOX);
    @AssignedName("aaroncos_arena_creator")
    public static final Item AARONCOS_ARENA_CREATOR = PasterDreamItemFactory.create("aaroncos_arena_creator");
    @AssignedName("dream_meter")
    public static final Item DREAM_METER = PasterDreamItemFactory.create("dream_meter");
    @AssignedName("copper_sword")
    public static final Item COPPER_SWORD = PasterDreamItemFactory.create("copper_sword");
    @AssignedName("copper_pickaxe")
    public static final Item COPPER_PICKAXE = PasterDreamItemFactory.create("copper_pickaxe");
    @AssignedName("copper_axe")
    public static final Item COPPER_AXE = PasterDreamItemFactory.create("copper_axe");
    @AssignedName("copper_shovel")
    public static final Item COPPER_SHOVEL = PasterDreamItemFactory.create("copper_shovel");
    @AssignedName("copper_hoe")
    public static final Item COPPER_HOE = PasterDreamItemFactory.create("copper_hoe");
    @AssignedName("copper_armor_helmet")
    public static final Item COPPER_ARMOR_HELMET = PasterDreamItemFactory.create("copper_armor_helmet");
    @AssignedName("copper_armor_chestplate")
    public static final Item COPPER_ARMOR_CHESTPLATE = PasterDreamItemFactory.create("copper_armor_chestplate");
    @AssignedName("copper_armor_leggings")
    public static final Item COPPER_ARMOR_LEGGINGS = PasterDreamItemFactory.create("copper_armor_leggings");
    @AssignedName("copper_armor_boots")
    public static final Item COPPER_ARMOR_BOOTS = PasterDreamItemFactory.create("copper_armor_boots");
    @AssignedName("titanium_sword")
    public static final Item TITANIUM_SWORD = PasterDreamItemFactory.create("titanium_sword");
    @AssignedName("titanium_pickaxe")
    public static final Item TITANIUM_PICKAXE = PasterDreamItemFactory.create("titanium_pickaxe");
    @AssignedName("titanium_axe")
    public static final Item TITANIUM_AXE = PasterDreamItemFactory.create("titanium_axe");
    @AssignedName("titanium_shovel")
    public static final Item TITANIUM_SHOVEL = PasterDreamItemFactory.create("titanium_shovel");
    @AssignedName("titanium_hoe")
    public static final Item TITANIUM_HOE = PasterDreamItemFactory.create("titanium_hoe");
    @AssignedName("titanium_armor_helmet")
    public static final Item TITANIUM_ARMOR_HELMET = PasterDreamItemFactory.create("titanium_armor_helmet");
    @AssignedName("titanium_armor_chestplate")
    public static final Item TITANIUM_ARMOR_CHESTPLATE = PasterDreamItemFactory.create("titanium_armor_chestplate");
    @AssignedName("titanium_armor_leggings")
    public static final Item TITANIUM_ARMOR_LEGGINGS = PasterDreamItemFactory.create("titanium_armor_leggings");
    @AssignedName("titanium_armor_boots")
    public static final Item TITANIUM_ARMOR_BOOTS = PasterDreamItemFactory.create("titanium_armor_boots");
    @AssignedName("titanium_upgrade")
    public static final Item TITANIUM_UPGRADE = PasterDreamItemFactory.create("titanium_upgrade");
    @AssignedName("sculk_armor_helmet")
    public static final Item SCULK_ARMOR_HELMET = PasterDreamItemFactory.create("sculk_armor_helmet");
    @AssignedName("sculk_armor_chestplate")
    public static final Item SCULK_ARMOR_CHESTPLATE = PasterDreamItemFactory.create("sculk_armor_chestplate");
    @AssignedName("sculk_armor_leggings")
    public static final Item SCULK_ARMOR_LEGGINGS = PasterDreamItemFactory.create("sculk_armor_leggings");
    @AssignedName("sculk_armor_boots")
    public static final Item SCULK_ARMOR_BOOTS = PasterDreamItemFactory.create("sculk_armor_boots");
    @AssignedName("sculk_upgrade")
    public static final Item SCULK_UPGRADE = PasterDreamItemFactory.create("sculk_upgrade");
    @AssignedName("dyedream_sword")
    public static final Item DYEDREAM_SWORD = PasterDreamItemFactory.create("dyedream_sword");
    @AssignedName("dyedream_sword_0")
    public static final Item DYEDREAM_SWORD_0 = PasterDreamItemFactory.create("dyedream_sword_0");
    @AssignedName("dyedream_pickaxe")
    public static final Item DYEDREAM_PICKAXE = PasterDreamItemFactory.create("dyedream_pickaxe");
    @AssignedName("dyedream_axe")
    public static final Item DYEDREAM_AXE = PasterDreamItemFactory.create("dyedream_axe");
    @AssignedName("dyedream_shovel")
    public static final Item DYEDREAM_SHOVEL = PasterDreamItemFactory.create("dyedream_shovel");
    @AssignedName("dyedream_hoe")
    public static final Item DYEDREAM_HOE = PasterDreamItemFactory.create("dyedream_hoe");
    @AssignedName("dyedream_hammer")
    public static final Item DYEDREAM_HAMMER = PasterDreamItemFactory.create("dyedream_hammer");
    @AssignedName("dyedream_armor_helmet")
    public static final Item DYEDREAM_ARMOR_HELMET = PasterDreamItemFactory.create("dyedream_armor_helmet");
    @AssignedName("dyedream_armor_chestplate")
    public static final Item DYEDREAM_ARMOR_CHESTPLATE = PasterDreamItemFactory.create("dyedream_armor_chestplate");
    @AssignedName("dyedream_armor_leggings")
    public static final Item DYEDREAM_ARMOR_LEGGINGS = PasterDreamItemFactory.create("dyedream_armor_leggings");
    @AssignedName("dyedream_armor_boots")
    public static final Item DYEDREAM_ARMOR_BOOTS = PasterDreamItemFactory.create("dyedream_armor_boots");
    @AssignedName("dyedream_upgrade")
    public static final Item DYEDREAM_UPGRADE = PasterDreamItemFactory.create("dyedream_upgrade");
    @AssignedName("moltengold_sword")
    public static final Item MOLTENGOLD_SWORD = PasterDreamItemFactory.create("moltengold_sword");
    @AssignedName("true_moltengold_sword")
    public static final Item TRUE_MOLTENGOLD_SWORD = PasterDreamItemFactory.create("true_moltengold_sword");
    @AssignedName("truest_moltengold_sword")
    public static final Item TRUEST_MOLTENGOLD_SWORD = PasterDreamItemFactory.create("truest_moltengold_sword");
    @AssignedName("moltengold_pickaxe")
    public static final Item MOLTENGOLD_PICKAXE = PasterDreamItemFactory.create("moltengold_pickaxe");
    @AssignedName("true_moltengold_pickaxe")
    public static final Item TRUE_MOLTENGOLD_PICKAXE = PasterDreamItemFactory.create("true_moltengold_pickaxe");
    @AssignedName("moltengold_axe")
    public static final Item MOLTENGOLD_AXE = PasterDreamItemFactory.create("moltengold_axe");
    @AssignedName("moltengold_shovel")
    public static final Item MOLTENGOLD_SHOVEL = PasterDreamItemFactory.create("moltengold_shovel");
    @AssignedName("moltengold_hoe")
    public static final Item MOLTENGOLD_HOE = PasterDreamItemFactory.create("moltengold_hoe");
    @AssignedName("meltdream_pickaxe")
    public static final Item MELTDREAM_PICKAXE = PasterDreamItemFactory.create("meltdream_pickaxe");
    @AssignedName("meltdream_axe")
    public static final Item MELTDREAM_AXE = PasterDreamItemFactory.create("meltdream_axe");
    @AssignedName("meltdream_shovel")
    public static final Item MELTDREAM_SHOVEL = PasterDreamItemFactory.create("meltdream_shovel");
    @AssignedName("meltdream_hoe")
    public static final Item MELTDREAM_HOE = PasterDreamItemFactory.create("meltdream_hoe");
    @AssignedName("grass_sword")
    public static final Item GRASS_SWORD = PasterDreamItemFactory.create("grass_sword");
    @AssignedName("true_grass_sword")
    public static final Item TRUE_GRASS_SWORD = PasterDreamItemFactory.create("true_grass_sword");
    @AssignedName("tide_sword")
    public static final Item TIDE_SWORD = PasterDreamItemFactory.create("tide_sword");
    @AssignedName("true_tide_sword")
    public static final Item TRUE_TIDE_SWORD = PasterDreamItemFactory.create("true_tide_sword");
    @AssignedName("desert_sword")
    public static final Item DESERT_SWORD = PasterDreamItemFactory.create("desert_sword");
    @AssignedName("true_desert_sword")
    public static final Item TRUE_DESERT_SWORD = PasterDreamItemFactory.create("true_desert_sword");
    @AssignedName("broken_hero_sword")
    public static final Item BROKEN_HERO_SWORD = PasterDreamItemFactory.create("broken_hero_sword");
    @AssignedName("shadow_erosion_sword")
    public static final Item SHADOW_EROSION_SWORD = PasterDreamItemFactory.create("shadow_erosion_sword");
    @AssignedName("shadow_erosion_pickaxe")
    public static final Item SHADOW_EROSION_PICKAXE = PasterDreamItemFactory.create("shadow_erosion_pickaxe");
    @AssignedName("shadow_erosion_axe")
    public static final Item SHADOW_EROSION_AXE = PasterDreamItemFactory.create("shadow_erosion_axe");
    @AssignedName("shadow_erosion_hoe")
    public static final Item SHADOW_EROSION_HOE = PasterDreamItemFactory.create("shadow_erosion_hoe");
    @AssignedName("shadow_erosion_shovel")
    public static final Item SHADOW_EROSION_SHOVEL = PasterDreamItemFactory.create("shadow_erosion_shovel");
    @AssignedName("white_sword")
    public static final Item WHITE_SWORD = PasterDreamItemFactory.create("white_sword");
    @AssignedName("shadow_sword")
    public static final Item SHADOW_SWORD = PasterDreamItemFactory.create("shadow_sword");
    @AssignedName("iceshadow_hammer")
    public static final Item ICESHADOW_HAMMER = PasterDreamItemFactory.create("iceshadow_hammer");
    @AssignedName("star_wish_rod")
    public static final Item STAR_WISH_ROD = PasterDreamItemFactory.create("star_wish_rod");
    @AssignedName("moltengold_wand")
    public static final Item MOLTENGOLD_WAND = PasterDreamItemFactory.create("moltengold_wand");
    @AssignedName("true_moltengold_wand")
    public static final Item TRUE_MOLTENGOLD_WAND = PasterDreamItemFactory.create("true_moltengold_wand");
    @AssignedName("truest_moltengold_wand")
    public static final Item TRUEST_MOLTENGOLD_WAND = PasterDreamItemFactory.create("truest_moltengold_wand");
    @AssignedName("squeal_wave_wand")
    public static final Item SQUEAL_WAVE_WAND = PasterDreamItemFactory.create("squeal_wave_wand");
    @AssignedName("shadow_vortex_book")
    public static final Item SHADOW_VORTEX_BOOK = PasterDreamItemFactory.create("shadow_vortex_book");
    @AssignedName("dream_harp_of_wanderer")
    public static final Item DREAM_HARP_OF_WANDERER = PasterDreamItemFactory.create("dream_harp_of_wanderer");
    @AssignedName("pliers")
    public static final Item PLIERS = PasterDreamItemFactory.create("pliers");
    @AssignedName("storage_bag")
    public static final Item STORAGE_BAG = PasterDreamItemFactory.create("storage_bag");
    @AssignedName("storage_bag_0")
    public static final Item STORAGE_BAG_0 = PasterDreamItemFactory.create("storage_bag_0");
    @AssignedName("embryo_ring")
    public static final Item EMBRYO_RING = PasterDreamItemFactory.create("embryo_ring");
    @AssignedName("embryo_necklace")
    public static final Item EMBRYO_NECKLACE = PasterDreamItemFactory.create("embryo_necklace");
    @AssignedName("embryo_belt")
    public static final Item EMBRYO_BELT = PasterDreamItemFactory.create("embryo_belt");
    @AssignedName("embryo_charm")
    public static final Item EMBRYO_CHARM = PasterDreamItemFactory.create("embryo_charm");
    @AssignedName("red_dew_0_ring")
    public static final Item RED_DEW_0_RING = PasterDreamItemFactory.create("red_dew_0_ring");
    @AssignedName("red_dew_1_ring")
    public static final Item RED_DEW_1_RING = PasterDreamItemFactory.create("red_dew_1_ring");
    @AssignedName("red_dew_2_ring")
    public static final Item RED_DEW_2_RING = PasterDreamItemFactory.create("red_dew_2_ring");
    @AssignedName("red_dew_3_ring")
    public static final Item RED_DEW_3_RING = PasterDreamItemFactory.create("red_dew_3_ring");
    @AssignedName("hithard_0_ring")
    public static final Item HITHARD_0_RING = PasterDreamItemFactory.create("hithard_0_ring");
    @AssignedName("hithard_1_ring")
    public static final Item HITHARD_1_RING = PasterDreamItemFactory.create("hithard_1_ring");
    @AssignedName("counter_ring")
    public static final Item COUNTER_RING = PasterDreamItemFactory.create("counter_ring");
    @AssignedName("meltdream_energy_0_ring")
    public static final Item MELTDREAM_ENERGY_0_RING = PasterDreamItemFactory.create("meltdream_energy_0_ring");
    @AssignedName("rabbit_0_necklace")
    public static final Item RABBIT_0_NECKLACE = PasterDreamItemFactory.create("rabbit_0_necklace");
    @AssignedName("feather_necklace")
    public static final Item FEATHER_NECKLACE = PasterDreamItemFactory.create("feather_necklace");
    @AssignedName("health_0_necklace")
    public static final Item HEALTH_0_NECKLACE = PasterDreamItemFactory.create("health_0_necklace");
    @AssignedName("fire_0_necklace")
    public static final Item FIRE_0_NECKLACE = PasterDreamItemFactory.create("fire_0_necklace");
    @AssignedName("cross_necklace")
    public static final Item CROSS_NECKLACE = PasterDreamItemFactory.create("cross_necklace");
    @AssignedName("nature_belt")
    public static final Item NATURE_BELT = PasterDreamItemFactory.create("nature_belt");
    @AssignedName("traveler_belt")
    public static final Item TRAVELER_BELT = PasterDreamItemFactory.create("traveler_belt");
    @AssignedName("dream_traveler_belt")
    public static final Item DREAM_TRAVELER_BELT = PasterDreamItemFactory.create("dream_traveler_belt");
    @AssignedName("gold_charm")
    public static final Item GOLD_CHARM = PasterDreamItemFactory.create("gold_charm");
    @AssignedName("end_eye_charm")
    public static final Item END_EYE_CHARM = PasterDreamItemFactory.create("end_eye_charm");
    @AssignedName("sea_charm")
    public static final Item SEA_CHARM = PasterDreamItemFactory.create("sea_charm");
    @AssignedName("carapax_charm")
    public static final Item CARAPAX_CHARM = PasterDreamItemFactory.create("carapax_charm");
    @AssignedName("world_tree_seedpod")
    public static final Item WORLD_TREE_SEEDPOD = PasterDreamItemFactory.create("world_tree_seedpod");
    @AssignedName("garland")
    public static final Item GARLAND = PasterDreamItemFactory.create("garland");
    @AssignedName("ghost_face_head")
    public static final Item GHOST_FACE_HEAD = PasterDreamItemFactory.create("ghost_face_head");
    @AssignedName("qym_head")
    public static final Item QYM_HEAD = PasterDreamItemFactory.create("qym_head");
    @AssignedName("white_flower_body")
    public static final Item WHITE_FLOWER_BODY = PasterDreamItemFactory.create("white_flower_body");
    @AssignedName("degenerate_bodies")
    public static final Item DEGENERATE_BODIES = PasterDreamItemFactory.create("degenerate_bodies");
    @AssignedName("machine_wing_chestplate")
    public static final Item MACHINE_WING_CHESTPLATE = PasterDreamItemFactory.create("machine_wing_chestplate");
    @AssignedName("dark_allegory_curio")
    public static final Item DARK_ALLEGORY_CURIO = PasterDreamItemFactory.create("dark_allegory_curio");
    @AssignedName("calais_spice_bottle_curio")
    public static final Item CALAIS_SPICE_BOTTLE_CURIO = PasterDreamItemFactory.create("calais_spice_bottle_curio");
    @AssignedName("four_leaf_clover_curio")
    public static final Item FOUR_LEAF_CLOVER_CURIO = PasterDreamItemFactory.create("four_leaf_clover_curio");
    @AssignedName("snow_vow_head")
    public static final Item SNOW_VOW_HEAD = PasterDreamItemFactory.create("snow_vow_head");
    @AssignedName("bright_butterfly_curio")
    public static final Item BRIGHT_BUTTERFLY_CURIO = PasterDreamItemFactory.create("bright_butterfly_curio");
    @AssignedName("iceshadow_curio")
    public static final Item ICESHADOW_CURIO = PasterDreamItemFactory.create("iceshadow_curio");
    @AssignedName("boboji_curio")
    public static final Item BOBOJI_CURIO = PasterDreamItemFactory.create("boboji_curio");
    @AssignedName("paper_plane")
    public static final Item PAPER_PLANE = PasterDreamItemFactory.create("paper_plane");
    @AssignedName("wind_knight_flag")
    public static final Item WIND_KNIGHT_FLAG = PasterDreamItemFactory.create("wind_knight_flag");
    @AssignedName("dream_wand")
    public static final Item DREAM_WAND = PasterDreamItemFactory.create("dream_wand");
    @AssignedName("mana_wand")
    public static final Item MANA_WAND = PasterDreamItemFactory.create("mana_wand");
    @AssignedName("qym_armor_helmet")
    public static final Item QYM_ARMOR_HELMET = PasterDreamItemFactory.create("qym_armor_helmet");
    @AssignedName("qym_armor_chestplate")
    public static final Item QYM_ARMOR_CHESTPLATE = PasterDreamItemFactory.create("qym_armor_chestplate");
    @AssignedName("qym_armor_leggings")
    public static final Item QYM_ARMOR_LEGGINGS = PasterDreamItemFactory.create("qym_armor_leggings");
    @AssignedName("qym_armor_boots")
    public static final Item QYM_ARMOR_BOOTS = PasterDreamItemFactory.create("qym_armor_boots");
    @AssignedName("odd_bacon_egg")
    public static final Item ODD_BACON_EGG = PasterDreamItemFactory.create("odd_bacon_egg");
    @AssignedName("mana_dust")
    public static final Item MANA_DUST = PasterDreamItemFactory.create("mana_dust");
    @AssignedName("bubble_tea")
    public static final Item BUBBLE_TEA = PasterDreamItemFactory.create("bubble_tea");
    @AssignedName("all_kinds_ring")
    public static final Item ALL_KINDS_RING = PasterDreamItemFactory.create("all_kinds_ring");
    @AssignedName("terra_sword")
    public static final Item TERRA_SWORD = PasterDreamItemFactory.create("terra_sword");
    @AssignedName("terra_charm")
    public static final Item TERRA_CHARM = PasterDreamItemFactory.create("terra_charm");
    @AssignedName("hiyori_head")
    public static final Item HIYORI_HEAD = PasterDreamItemFactory.create("hiyori_head");
    @AssignedName("cecilia_care_charm")
    public static final Item CECILIA_CARE_CHARM = PasterDreamItemFactory.create("cecilia_care_charm");
    @AssignedName("thermal_dagger")
    public static final Item THERMAL_DAGGER = PasterDreamItemFactory.create("thermal_dagger");
    @AssignedName("duke_coin_curio")
    public static final Item DUKE_COIN_CURIO = PasterDreamItemFactory.create("duke_coin_curio");
    @AssignedName("dream_aurorian_steel")
    public static final Item DREAM_AURORIAN_STEEL = PasterDreamItemFactory.create("dream_aurorian_steel");
    @AssignedName("turnback_cloak")
    public static final Item TURNBACK_CLOAK = PasterDreamItemFactory.create("turnback_cloak");
    @AssignedName("evasion_cloak")
    public static final Item EVASION_CLOAK = PasterDreamItemFactory.create("evasion_cloak");
    @AssignedName("strawberry_heart")
    public static final Item STRAWBERRY_HEART = PasterDreamItemFactory.create("strawberry_heart");
    @AssignedName("silver_bell")
    public static final Item SILVER_BELL = PasterDreamItemFactory.create("silver_bell");
    @AssignedName("cradle_in_ones_arms")
    public static final Item CRADLE_IN_ONES_ARMS = PasterDreamItemFactory.create("cradle_in_ones_arms");
    @AssignedName("forsakens_wing_chestplate")
    public static final Item FORSAKENS_WING_CHESTPLATE = PasterDreamItemFactory.create("forsakens_wing_chestplate");
    @AssignedName("angel_wing_chestplate")
    public static final Item ANGEL_WING_CHESTPLATE = PasterDreamItemFactory.create("angel_wing_chestplate");
    @AssignedName("dream_note_0")
    public static final Item DREAM_NOTE_0 = PasterDreamItemFactory.create("dream_note_0");
    @AssignedName("dream_note_1")
    public static final Item DREAM_NOTE_1 = PasterDreamItemFactory.create("dream_note_1");
    @AssignedName("dream_note_2")
    public static final Item DREAM_NOTE_2 = PasterDreamItemFactory.create("dream_note_2");
    @AssignedName("dream_note_3")
    public static final Item DREAM_NOTE_3 = PasterDreamItemFactory.create("dream_note_3");
    @AssignedName("dream_note_4")
    public static final Item DREAM_NOTE_4 = PasterDreamItemFactory.create("dream_note_4");
    @AssignedName("dream_note_5")
    public static final Item DREAM_NOTE_5 = PasterDreamItemFactory.create("dream_note_5");
    @AssignedName("dream_note_6")
    public static final Item DREAM_NOTE_6 = PasterDreamItemFactory.create("dream_note_6");
    @AssignedName("dream_note_7")
    public static final Item DREAM_NOTE_7 = PasterDreamItemFactory.create("dream_note_7");
    @AssignedName("dream_note_8")
    public static final Item DREAM_NOTE_8 = PasterDreamItemFactory.create("dream_note_8");
    @AssignedName("dream_note_9")
    public static final Item DREAM_NOTE_9 = PasterDreamItemFactory.create("dream_note_9");
    @AssignedName("dream_note_10")
    public static final Item DREAM_NOTE_10 = PasterDreamItemFactory.create("dream_note_10");
    @AssignedName("dream_note_11")
    public static final Item DREAM_NOTE_11 = PasterDreamItemFactory.create("dream_note_11");
    @AssignedName("dream_note_12")
    public static final Item DREAM_NOTE_12 = PasterDreamItemFactory.create("dream_note_12");
    @AssignedName("dream_note_13")
    public static final Item DREAM_NOTE_13 = PasterDreamItemFactory.create("dream_note_13");
    @AssignedName("dream_note_14")
    public static final Item DREAM_NOTE_14 = PasterDreamItemFactory.create("dream_note_14");
    @AssignedName("shadow_blast_furnace_blueprint")
    public static final Item SHADOW_BLAST_FURNACE_BLUEPRINT = PasterDreamItemFactory.create("shadow_blast_furnace_blueprint");
    @AssignedName("weapon_workshop_blueprint")
    public static final Item WEAPON_WORKSHOP_BLUEPRINT = PasterDreamItemFactory.create("weapon_workshop_blueprint");
    @AssignedName("memory_gem_0")
    public static final Item MEMORY_GEM_0 = PasterDreamItemFactory.create("memory_gem_0");
    @AssignedName("memento_01")
    public static final Item MEMENTO_01 = PasterDreamItemFactory.create("memento_01");
    @AssignedName("memento_05")
    public static final Item MEMENTO_05 = PasterDreamItemFactory.create("memento_05");
    @AssignedName("memento_06")
    public static final Item MEMENTO_06 = PasterDreamItemFactory.create("memento_06");
    @AssignedName("memento_02")
    public static final Item MEMENTO_02 = PasterDreamItemFactory.create("memento_02");
    @AssignedName("memento_03")
    public static final Item MEMENTO_03 = PasterDreamItemFactory.create("memento_03");
    @AssignedName("memento_04")
    public static final Item MEMENTO_04 = PasterDreamItemFactory.create("memento_04");
    @AssignedName("memento_07")
    public static final Item MEMENTO_07 = PasterDreamItemFactory.create("memento_07");
    @AssignedName("memento_08")
    public static final Item MEMENTO_08 = PasterDreamItemFactory.create("memento_08");
    @AssignedName("memento_09")
    public static final Item MEMENTO_09 = PasterDreamItemFactory.create("memento_09");
    @AssignedName("memento_10")
    public static final Item MEMENTO_10 = PasterDreamItemFactory.create("memento_10");
    @AssignedName("meltdream_crystal_spawn_egg")
    public static final Item MELTDREAM_CRYSTAL_SPAWN_EGG = spawnEgg(PasterDreamEntities.MELTDREAM_CRYSTAL_ENTITY);
    @AssignedName("terra_sword_wave_spawn_egg")
    public static final Item TERRA_SWORD_WAVE_SPAWN_EGG = spawnEgg(PasterDreamEntities.TERRASWORD_WAVE);
    @AssignedName("shadow_magic_ball_spawn_egg")
    public static final Item SHADOW_MAGIC_BALL_SPAWN_EGG = spawnEgg(PasterDreamEntities.SHADOW_MAGICBALL);
    @AssignedName("talent_shadow")
    public static final Item TALENT_SHADOW = PasterDreamItemFactory.create("talent_shadow");
    @AssignedName("talent_light")
    public static final Item TALENT_LIGHT = PasterDreamItemFactory.create("talent_light");
    @AssignedName("shadow_vortex_spawn_item")
    public static final Item SHADOW_VORTEX_SPAWN_ITEM = PasterDreamItemFactory.create("shadow_vortex_spawn_item");
    @AssignedName("healing_spell_entity_spawn_egg")
    public static final Item HEALING_SPELL_ENTITY_SPAWN_EGG = spawnEgg(PasterDreamEntities.HEALING_SPELL_ENTITY);
    @AssignedName("squeal_wave")
    public static final Item SQUEAL_WAVE = PasterDreamItemFactory.create("squeal_wave");
    @AssignedName("white_sword_rain")
    public static final Item WHITE_SWORD_RAIN = PasterDreamItemFactory.create("white_sword_rain");
    @AssignedName("bone_wing_fireball")
    public static final Item BONE_WING_FIREBALL = PasterDreamItemFactory.create("bone_wing_fireball");
    @AssignedName("lightning_strike_item")
    public static final Item LIGHTNING_STRIKE_ITEM = PasterDreamItemFactory.create("lightning_strike_item");
    @AssignedName("fox_fire_spawn_egg")
    public static final Item FOX_FIRE_SPAWN_EGG = spawnEgg(PasterDreamEntities.FOX_FIRE);
    @AssignedName("moss_phantom_membrane")
    public static final Item MOSS_PHANTOM_MEMBRANE = PasterDreamItemFactory.create("moss_phantom_membrane");
    @AssignedName("light_moss_phantom_membrane")
    public static final Item LIGHT_MOSS_PHANTOM_MEMBRANE = PasterDreamItemFactory.create("light_moss_phantom_membrane");
    @AssignedName("light_organ")
    public static final Item LIGHT_ORGAN = PasterDreamItemFactory.create("light_organ");
    @AssignedName("shadow_breath")
    public static final Item SHADOW_BREATH = PasterDreamItemFactory.create("shadow_breath");
    @AssignedName("small_stone_spirit_spawn_egg")
    public static final Item SMALL_STONE_SPIRIT_SPAWN_EGG = spawnEgg(PasterDreamEntities.SMALL_STONE_SPIRIT);
    @AssignedName("light_butterfly_curio")
    public static final Item LIGHT_BUTTERFLY_CURIO = PasterDreamItemFactory.create("light_butterfly_curio");
    @AssignedName("shadow_hand_lantern")
    public static final Item SHADOW_HAND_LANTERN = PasterDreamItemFactory.create("shadow_hand_lantern");
    @AssignedName("paster_block_reset_tool")
    public static final Item PASTER_BLOCK_RESET_TOOL = PasterDreamItemFactory.create("paster_block_reset_tool");

    private PasterDreamItems() {
    }

    public static void register() {
        GeminiRegistration.items(PasterDreamItems.class);
    }

    public static Item get(String id) {
        return BuiltInRegistries.ITEM.getOptional(PasterDream.id(id))
            .orElseThrow(() -> new IllegalArgumentException("Unknown PasterDream item: " + id));
    }

    public static Map<String, Item> all() {
        Map<String, Item> result = new LinkedHashMap<>();
        for (Item item : BuiltInRegistries.ITEM) {
            var key = BuiltInRegistries.ITEM.getKey(item);
            if (key != null && key.getNamespace().equals(PasterDream.MOD_ID)) {
                result.put(key.getPath(), item);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static Item spawnEgg(EntityType<?> type) {
        return new SpawnEggItem(
            (EntityType<? extends Mob>) (EntityType<?>) type,
            0xFFFFFF,
            0x333333,
            new Item.Properties()
        );
    }
}
