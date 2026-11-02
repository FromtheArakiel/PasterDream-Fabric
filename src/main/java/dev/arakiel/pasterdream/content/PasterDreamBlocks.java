package dev.arakiel.pasterdream.content;

import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.gemini.registration.container.BlockRegistryContainer;
import dev.arakiel.pasterdream.PasterDream;
import java.lang.reflect.Field;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamBlocks implements BlockRegistryContainer {
    private static final Set<String> SCHEDULED_BEHAVIOR_BLOCKS = Set.of(
        "meltdream_liquid",
        "shadow_liquid",
        "shadow_candle",
        "dyedream_ice",
        "pebble_0",
        "big_bubble",
        "breakwind_curtain",
        "ejection_pressure_plate"
    );
    private static final Set<String> FLOWER_BLOCKS = Set.of(
        "devil_star_orchid",
        "lisianthus",
        "orchid_wheat",
        "mallow",
        "flame_flower",
        "crimson_thorn",
        "pink_cap_mushroom",
        "lantern_flower",
        "tall_pink_cap_mushroom",
        "dreamforge_lotus",
        "lost_dream_forge_lotus",
        "dyedream_lily_of_the_valley",
        "strange_fern",
        "white_calamity_flower",
        "edelweiss",
        "frost_edelweiss",
        "wind_island_reed",
        "common_reed",
        "orchid_wheat_grass",
        "stalk_grass",
        "tall_stalk_grass",
        "calcite_spike",
        "tall_calcite_spike",
        "dyedream_moss",
        "shadow_mycelium",
        "tall_shadow_mycelium",
        "shadow_stem_fern",
        "shadow_fern",
        "tall_shadow_fern",
        "fuzzy_moss",
        "windcut_grass",
        "windfeather_grass",
        "dyedream_camellia_crop",
        "dyedream_camellia_seed",
        "pale_snow_lotus_crop",
        "pale_snow_lotus_seed",
        "luminous_violet_crop",
        "luminous_violet_seed",
        "ringing_cloud_flower_crop",
        "ringing_cloud_flower_seed",
        "cotton_crop",
        "cotton_crop_seed"
    );
    private static final Set<String> DOUBLE_PLANT_BLOCKS = Set.of(
        "crimson_thorn",
        "tall_pink_cap_mushroom",
        "dreamforge_lotus",
        "lost_dream_forge_lotus",
        "wind_island_reed",
        "tall_stalk_grass",
        "tall_shadow_mycelium",
        "shadow_stem_fern",
        "tall_shadow_fern",
        "windfeather_grass"
    );
    @AssignedName("lightball")
    public static final Block LIGHTBALL = create("lightball", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("pebble_0")
    public static final Block PEBBLE_0 = create("pebble_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("meltdream_liquid")
    public static final Block MELTDREAM_LIQUID = create("meltdream_liquid", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_liquid")
    public static final Block SHADOW_LIQUID = create("shadow_liquid", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_candle")
    public static final Block SHADOW_CANDLE = create("shadow_candle", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("clay_pot_0")
    public static final Block CLAY_POT_0 = create("clay_pot_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("meltdream_chest")
    public static final Block MELTDREAM_CHEST = create("meltdream_chest", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("meltdream_chest_open")
    public static final Block MELTDREAM_CHEST_OPEN = create("meltdream_chest_open", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dream_accumulator")
    public static final Block DREAM_ACCUMULATOR = create("dream_accumulator", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("claypan_0")
    public static final Block CLAYPAN_0 = create("claypan_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("research_table")
    public static final Block RESEARCH_TABLE = create("research_table", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("forced_tower")
    public static final Block FORCED_TOWER = create("forced_tower", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("weapon_table")
    public static final Block WEAPON_TABLE = create("weapon_table", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("weapon_workshop")
    public static final Block WEAPON_WORKSHOP = create("weapon_workshop", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_desk")
    public static final Block DYEDREAM_DESK = create("dyedream_desk", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("lost_sword_block")
    public static final Block LOST_SWORD_BLOCK = create("lost_sword_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("desert_hero_tomb")
    public static final Block DESERT_HERO_TOMB = create("desert_hero_tomb", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("qym_doll_0")
    public static final Block QYM_DOLL_0 = create("qym_doll_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("uuz_doll_0")
    public static final Block UUZ_DOLL_0 = create("uuz_doll_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("twilight_lantern")
    public static final Block TWILIGHT_LANTERN = create("twilight_lantern", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_bed")
    public static final Block SHADOW_BED = create("shadow_bed", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_desk")
    public static final Block SHADOW_DESK = create("shadow_desk", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_chest")
    public static final Block SHADOW_CHEST = create("shadow_chest", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_blast_furnace_core")
    public static final Block SHADOW_BLAST_FURNACE_CORE = create("shadow_blast_furnace_core", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_blast_furnace")
    public static final Block SHADOW_BLAST_FURNACE = create("shadow_blast_furnace", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("calle_card_block")
    public static final Block CALLE_CARD_BLOCK = create("calle_card_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("guard_crystal")
    public static final Block GUARD_CRYSTAL = create("guard_crystal", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("broken_shadow_dungeon_portal")
    public static final Block BROKEN_SHADOW_DUNGEON_PORTAL = create("broken_shadow_dungeon_portal", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_portal")
    public static final Block SHADOW_DUNGEON_PORTAL = create("shadow_dungeon_portal", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_door_0")
    public static final Block SHADOW_DUNGEON_DOOR_0 = create("shadow_dungeon_door_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_door_2")
    public static final Block SHADOW_DUNGEON_DOOR_2 = create("shadow_dungeon_door_2", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_key_0")
    public static final Block SHADOW_DUNGEON_KEY_0 = create("shadow_dungeon_key_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_key_1")
    public static final Block SHADOW_DUNGEON_KEY_1 = create("shadow_dungeon_key_1", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_trap_0")
    public static final Block SHADOW_TRAP_0 = create("shadow_trap_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_brazier")
    public static final Block SHADOW_BRAZIER = create("shadow_brazier", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("aaroncos_hand_chest")
    public static final Block AARONCOS_HAND_CHEST = create("aaroncos_hand_chest", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dream_cauldron")
    public static final Block DREAM_CAULDRON = create("dream_cauldron", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("birds_nest")
    public static final Block BIRDS_NEST = create("birds_nest", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("windmoor_crate")
    public static final Block WINDMOOR_CRATE = create("windmoor_crate", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("the_endless_book_of_dream_seekers")
    public static final Block THE_ENDLESS_BOOK_OF_DREAM_SEEKERS = create("the_endless_book_of_dream_seekers", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("christmas_lights")
    public static final Block CHRISTMAS_LIGHTS = create("christmas_lights", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("firefly_nest")
    public static final Block FIREFLY_NEST = create("firefly_nest", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("ecology_glass_jar")
    public static final Block ECOLOGY_GLASS_JAR = create("ecology_glass_jar", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("firefly_glass_jar")
    public static final Block FIREFLY_GLASS_JAR = create("firefly_glass_jar", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("life_crystal")
    public static final Block LIFE_CRYSTAL = create("life_crystal", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("picnic_basket")
    public static final Block PICNIC_BASKET = create("picnic_basket", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("wind_knight_spawnblock_0")
    public static final Block WIND_KNIGHT_SPAWNBLOCK_0 = create("wind_knight_spawnblock_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("golden_fox_sculpture")
    public static final Block GOLDEN_FOX_SCULPTURE = create("golden_fox_sculpture", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_grass")
    public static final Block DYEDREAM_GRASS = create("dyedream_grass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_dirt")
    public static final Block DYEDREAM_DIRT = create("dyedream_dirt", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_sand")
    public static final Block DYEDREAM_SAND = create("dyedream_sand", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_log")
    public static final Block DYEDREAM_LOG = create("dyedream_log", ContentCatalog.BlockKind.PILLAR);
    @AssignedName("dyedream_wood")
    public static final Block DYEDREAM_WOOD = create("dyedream_wood", ContentCatalog.BlockKind.PILLAR);
    @AssignedName("dyedream_leaves")
    public static final Block DYEDREAM_LEAVES = create("dyedream_leaves", ContentCatalog.BlockKind.LEAVES);
    @AssignedName("dyedream_worldtree_leaves")
    public static final Block DYEDREAM_WORLDTREE_LEAVES = create("dyedream_worldtree_leaves", ContentCatalog.BlockKind.LEAVES);
    @AssignedName("dyedream_planks")
    public static final Block DYEDREAM_PLANKS = create("dyedream_planks", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_planks_stairs")
    public static final Block DYEDREAM_PLANKS_STAIRS = create("dyedream_planks_stairs", ContentCatalog.BlockKind.STAIRS);
    @AssignedName("dyedream_planks_slab")
    public static final Block DYEDREAM_PLANKS_SLAB = create("dyedream_planks_slab", ContentCatalog.BlockKind.SLAB);
    @AssignedName("dyedream_planks_fence")
    public static final Block DYEDREAM_PLANKS_FENCE = create("dyedream_planks_fence", ContentCatalog.BlockKind.FENCE);
    @AssignedName("dyedream_planks_fencegate")
    public static final Block DYEDREAM_PLANKS_FENCEGATE = create("dyedream_planks_fencegate", ContentCatalog.BlockKind.FENCE_GATE);
    @AssignedName("dyedream_planks_pane")
    public static final Block DYEDREAM_PLANKS_PANE = create("dyedream_planks_pane", ContentCatalog.BlockKind.PANE);
    @AssignedName("dyedream_planks_door")
    public static final Block DYEDREAM_PLANKS_DOOR = create("dyedream_planks_door", ContentCatalog.BlockKind.DOOR);
    @AssignedName("dyedream_planks_trapdoor")
    public static final Block DYEDREAM_PLANKS_TRAPDOOR = create("dyedream_planks_trapdoor", ContentCatalog.BlockKind.TRAP_DOOR);
    @AssignedName("dyedream_planks_pressure_plate")
    public static final Block DYEDREAM_PLANKS_PRESSURE_PLATE = create("dyedream_planks_pressure_plate", ContentCatalog.BlockKind.PRESSURE_PLATE);
    @AssignedName("dyedream_planks_button")
    public static final Block DYEDREAM_PLANKS_BUTTON = create("dyedream_planks_button", ContentCatalog.BlockKind.BUTTON);
    @AssignedName("dyedream_quartz_ore")
    public static final Block DYEDREAM_QUARTZ_ORE = create("dyedream_quartz_ore", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_dust_ore")
    public static final Block DYEDREAM_DUST_ORE = create("dyedream_dust_ore", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("amber_candy_ore")
    public static final Block AMBER_CANDY_ORE = create("amber_candy_ore", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_block")
    public static final Block DYEDREAM_BLOCK = create("dyedream_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("pink_slime_block")
    public static final Block PINK_SLIME_BLOCK = create("pink_slime_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("pinkagaric_0")
    public static final Block PINKAGARIC_0 = create("pinkagaric_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("pinkagaric_1")
    public static final Block PINKAGARIC_1 = create("pinkagaric_1", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("pinkagaric_2")
    public static final Block PINKAGARIC_2 = create("pinkagaric_2", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("pinkagaric_3")
    public static final Block PINKAGARIC_3 = create("pinkagaric_3", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_ice")
    public static final Block DYEDREAM_ICE = create("dyedream_ice", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_packed_ice")
    public static final Block DYEDREAM_PACKED_ICE = create("dyedream_packed_ice", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("big_bubble")
    public static final Block BIG_BUBBLE = create("big_bubble", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_quartz_block")
    public static final Block DYEDREAM_QUARTZ_BLOCK = create("dyedream_quartz_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("smooth_dyedream_quartz_block")
    public static final Block SMOOTH_DYEDREAM_QUARTZ_BLOCK = create("smooth_dyedream_quartz_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("bricks_dyedream_quartz_block")
    public static final Block BRICKS_DYEDREAM_QUARTZ_BLOCK = create("bricks_dyedream_quartz_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("pillar_dyedream_quartz_block")
    public static final Block PILLAR_DYEDREAM_QUARTZ_BLOCK = create("pillar_dyedream_quartz_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("chiseled_dyedream_quartz_block")
    public static final Block CHISELED_DYEDREAM_QUARTZ_BLOCK = create("chiseled_dyedream_quartz_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_quartz_block_stairs")
    public static final Block DYEDREAM_QUARTZ_BLOCK_STAIRS = create("dyedream_quartz_block_stairs", ContentCatalog.BlockKind.STAIRS);
    @AssignedName("dyedream_quartz_block_slab")
    public static final Block DYEDREAM_QUARTZ_BLOCK_SLAB = create("dyedream_quartz_block_slab", ContentCatalog.BlockKind.SLAB);
    @AssignedName("dyedream_quartz_block_wall")
    public static final Block DYEDREAM_QUARTZ_BLOCK_WALL = create("dyedream_quartz_block_wall", ContentCatalog.BlockKind.WALL);
    @AssignedName("dyedream_lantern")
    public static final Block DYEDREAM_LANTERN = create("dyedream_lantern", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_glass")
    public static final Block DYEDREAM_GLASS = create("dyedream_glass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_glasspane")
    public static final Block DYEDREAM_GLASSPANE = create("dyedream_glasspane", ContentCatalog.BlockKind.PANE);
    @AssignedName("carve_dyedream_glass")
    public static final Block CARVE_DYEDREAM_GLASS = create("carve_dyedream_glass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("carve_dyedream_glasspane")
    public static final Block CARVE_DYEDREAM_GLASSPANE = create("carve_dyedream_glasspane", ContentCatalog.BlockKind.PANE);
    @AssignedName("gold_carve_dyedream_glass")
    public static final Block GOLD_CARVE_DYEDREAM_GLASS = create("gold_carve_dyedream_glass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("gold_carve_dyedream_glasspane")
    public static final Block GOLD_CARVE_DYEDREAM_GLASSPANE = create("gold_carve_dyedream_glasspane", ContentCatalog.BlockKind.PANE);
    @AssignedName("dyedream_bud_0")
    public static final Block DYEDREAM_BUD_0 = create("dyedream_bud_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_bud_1")
    public static final Block DYEDREAM_BUD_1 = create("dyedream_bud_1", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_bud_2")
    public static final Block DYEDREAM_BUD_2 = create("dyedream_bud_2", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_bud_block")
    public static final Block DYEDREAM_BUD_BLOCK = create("dyedream_bud_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_bud_stairs")
    public static final Block DYEDREAM_BUD_STAIRS = create("dyedream_bud_stairs", ContentCatalog.BlockKind.STAIRS);
    @AssignedName("dyedream_bud_slab")
    public static final Block DYEDREAM_BUD_SLAB = create("dyedream_bud_slab", ContentCatalog.BlockKind.SLAB);
    @AssignedName("dyedream_bud_wall")
    public static final Block DYEDREAM_BUD_WALL = create("dyedream_bud_wall", ContentCatalog.BlockKind.WALL);
    @AssignedName("icestone")
    public static final Block ICESTONE = create("icestone", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("ice_bud_0")
    public static final Block ICE_BUD_0 = create("ice_bud_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("cloud")
    public static final Block CLOUD = create("cloud", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dark_cloud")
    public static final Block DARK_CLOUD = create("dark_cloud", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("polished_calcite")
    public static final Block POLISHED_CALCITE = create("polished_calcite", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("polished_calcite_stairs")
    public static final Block POLISHED_CALCITE_STAIRS = create("polished_calcite_stairs", ContentCatalog.BlockKind.STAIRS);
    @AssignedName("polished_calcite_slab")
    public static final Block POLISHED_CALCITE_SLAB = create("polished_calcite_slab", ContentCatalog.BlockKind.SLAB);
    @AssignedName("polished_calcite_wall")
    public static final Block POLISHED_CALCITE_WALL = create("polished_calcite_wall", ContentCatalog.BlockKind.WALL);
    @AssignedName("calcite_tiles")
    public static final Block CALCITE_TILES = create("calcite_tiles", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("calcite_tiles_stairs")
    public static final Block CALCITE_TILES_STAIRS = create("calcite_tiles_stairs", ContentCatalog.BlockKind.STAIRS);
    @AssignedName("calcite_tiles_slab")
    public static final Block CALCITE_TILES_SLAB = create("calcite_tiles_slab", ContentCatalog.BlockKind.SLAB);
    @AssignedName("calcite_tiles_wall")
    public static final Block CALCITE_TILES_WALL = create("calcite_tiles_wall", ContentCatalog.BlockKind.WALL);
    @AssignedName("titanium_ore")
    public static final Block TITANIUM_ORE = create("titanium_ore", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("deepslate_titanium_ore")
    public static final Block DEEPSLATE_TITANIUM_ORE = create("deepslate_titanium_ore", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("moltengold_ore")
    public static final Block MOLTENGOLD_ORE = create("moltengold_ore", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("soul_ore")
    public static final Block SOUL_ORE = create("soul_ore", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("raw_titanium_block")
    public static final Block RAW_TITANIUM_BLOCK = create("raw_titanium_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("salt_block")
    public static final Block SALT_BLOCK = create("salt_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("titanium_block")
    public static final Block TITANIUM_BLOCK = create("titanium_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("moltengold_block")
    public static final Block MOLTENGOLD_BLOCK = create("moltengold_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("charged_amethyst_block")
    public static final Block CHARGED_AMETHYST_BLOCK = create("charged_amethyst_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_block")
    public static final Block SHADOW_BLOCK = create("shadow_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("thick_shadow_block")
    public static final Block THICK_SHADOW_BLOCK = create("thick_shadow_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_stone")
    public static final Block SHADOW_STONE = create("shadow_stone", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_stone_brick")
    public static final Block SHADOW_STONE_BRICK = create("shadow_stone_brick", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_stone_brick_stairs")
    public static final Block SHADOW_STONE_BRICK_STAIRS = create("shadow_stone_brick_stairs", ContentCatalog.BlockKind.STAIRS);
    @AssignedName("shadow_stone_brick_wall")
    public static final Block SHADOW_STONE_BRICK_WALL = create("shadow_stone_brick_wall", ContentCatalog.BlockKind.WALL);
    @AssignedName("shadow_stone_brick_slab")
    public static final Block SHADOW_STONE_BRICK_SLAB = create("shadow_stone_brick_slab", ContentCatalog.BlockKind.SLAB);
    @AssignedName("shadow_stone_bricks")
    public static final Block SHADOW_STONE_BRICKS = create("shadow_stone_bricks", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_stone_bricks_stairs")
    public static final Block SHADOW_STONE_BRICKS_STAIRS = create("shadow_stone_bricks_stairs", ContentCatalog.BlockKind.STAIRS);
    @AssignedName("shadow_stone_bricks_slab")
    public static final Block SHADOW_STONE_BRICKS_SLAB = create("shadow_stone_bricks_slab", ContentCatalog.BlockKind.SLAB);
    @AssignedName("shadow_stone_bricks_wall")
    public static final Block SHADOW_STONE_BRICKS_WALL = create("shadow_stone_bricks_wall", ContentCatalog.BlockKind.WALL);
    @AssignedName("shadow_stone_tiles")
    public static final Block SHADOW_STONE_TILES = create("shadow_stone_tiles", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_stone_tiles_stairs")
    public static final Block SHADOW_STONE_TILES_STAIRS = create("shadow_stone_tiles_stairs", ContentCatalog.BlockKind.STAIRS);
    @AssignedName("shadow_stone_tiles_slab")
    public static final Block SHADOW_STONE_TILES_SLAB = create("shadow_stone_tiles_slab", ContentCatalog.BlockKind.SLAB);
    @AssignedName("shadow_stone_tiles_wall")
    public static final Block SHADOW_STONE_TILES_WALL = create("shadow_stone_tiles_wall", ContentCatalog.BlockKind.WALL);
    @AssignedName("cracked_shadow_stone_brick")
    public static final Block CRACKED_SHADOW_STONE_BRICK = create("cracked_shadow_stone_brick", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("chiseled_shadow_stone_brick")
    public static final Block CHISELED_SHADOW_STONE_BRICK = create("chiseled_shadow_stone_brick", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_light_0")
    public static final Block SHADOW_LIGHT_0 = create("shadow_light_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_shroomlight")
    public static final Block SHADOW_SHROOMLIGHT = create("shadow_shroomlight", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_nylium")
    public static final Block SHADOW_NYLIUM = create("shadow_nylium", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_wart_block")
    public static final Block SHADOW_WART_BLOCK = create("shadow_wart_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_stem")
    public static final Block SHADOW_STEM = create("shadow_stem", ContentCatalog.BlockKind.PILLAR);
    @AssignedName("shadow_hyphae")
    public static final Block SHADOW_HYPHAE = create("shadow_hyphae", ContentCatalog.BlockKind.PILLAR);
    @AssignedName("stripped_shadow_stem")
    public static final Block STRIPPED_SHADOW_STEM = create("stripped_shadow_stem", ContentCatalog.BlockKind.PILLAR);
    @AssignedName("stripped_shadow_hyphae")
    public static final Block STRIPPED_SHADOW_HYPHAE = create("stripped_shadow_hyphae", ContentCatalog.BlockKind.PILLAR);
    @AssignedName("shadow_planks")
    public static final Block SHADOW_PLANKS = create("shadow_planks", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_planks_stairs")
    public static final Block SHADOW_PLANKS_STAIRS = create("shadow_planks_stairs", ContentCatalog.BlockKind.STAIRS);
    @AssignedName("shadow_planks_slab")
    public static final Block SHADOW_PLANKS_SLAB = create("shadow_planks_slab", ContentCatalog.BlockKind.SLAB);
    @AssignedName("shadow_planks_fence")
    public static final Block SHADOW_PLANKS_FENCE = create("shadow_planks_fence", ContentCatalog.BlockKind.FENCE);
    @AssignedName("shadow_planks_fencegate")
    public static final Block SHADOW_PLANKS_FENCEGATE = create("shadow_planks_fencegate", ContentCatalog.BlockKind.FENCE_GATE);
    @AssignedName("shadow_planks_pane")
    public static final Block SHADOW_PLANKS_PANE = create("shadow_planks_pane", ContentCatalog.BlockKind.PANE);
    @AssignedName("shadow_planks_door")
    public static final Block SHADOW_PLANKS_DOOR = create("shadow_planks_door", ContentCatalog.BlockKind.DOOR);
    @AssignedName("shadow_planks_trapdoor")
    public static final Block SHADOW_PLANKS_TRAPDOOR = create("shadow_planks_trapdoor", ContentCatalog.BlockKind.TRAP_DOOR);
    @AssignedName("shadow_planks_pressure_plate")
    public static final Block SHADOW_PLANKS_PRESSURE_PLATE = create("shadow_planks_pressure_plate", ContentCatalog.BlockKind.PRESSURE_PLATE);
    @AssignedName("shadow_planks_button")
    public static final Block SHADOW_PLANKS_BUTTON = create("shadow_planks_button", ContentCatalog.BlockKind.BUTTON);
    @AssignedName("rust_black_metal_block")
    public static final Block RUST_BLACK_METAL_BLOCK = create("rust_black_metal_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("rust_black_metal_block_wall")
    public static final Block RUST_BLACK_METAL_BLOCK_WALL = create("rust_black_metal_block_wall", ContentCatalog.BlockKind.WALL);
    @AssignedName("rust_black_metal_block_bars")
    public static final Block RUST_BLACK_METAL_BLOCK_BARS = create("rust_black_metal_block_bars", ContentCatalog.BlockKind.PANE);
    @AssignedName("black_metal_block")
    public static final Block BLACK_METAL_BLOCK = create("black_metal_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("armor_wreck_block_0")
    public static final Block ARMOR_WRECK_BLOCK_0 = create("armor_wreck_block_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("armor_wreck_block_1")
    public static final Block ARMOR_WRECK_BLOCK_1 = create("armor_wreck_block_1", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("armor_wreck_block_2")
    public static final Block ARMOR_WRECK_BLOCK_2 = create("armor_wreck_block_2", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("armor_wreck_block_3")
    public static final Block ARMOR_WRECK_BLOCK_3 = create("armor_wreck_block_3", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("armor_wreck_block_4")
    public static final Block ARMOR_WRECK_BLOCK_4 = create("armor_wreck_block_4", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_block_0")
    public static final Block SHADOW_DUNGEON_BLOCK_0 = create("shadow_dungeon_block_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_block_1")
    public static final Block SHADOW_DUNGEON_BLOCK_1 = create("shadow_dungeon_block_1", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_block_2")
    public static final Block SHADOW_DUNGEON_BLOCK_2 = create("shadow_dungeon_block_2", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_block_3")
    public static final Block SHADOW_DUNGEON_BLOCK_3 = create("shadow_dungeon_block_3", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_block_4")
    public static final Block SHADOW_DUNGEON_BLOCK_4 = create("shadow_dungeon_block_4", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_block_5")
    public static final Block SHADOW_DUNGEON_BLOCK_5 = create("shadow_dungeon_block_5", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_block_6")
    public static final Block SHADOW_DUNGEON_BLOCK_6 = create("shadow_dungeon_block_6", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("loose_shadow_dungeon_block")
    public static final Block LOOSE_SHADOW_DUNGEON_BLOCK = create("loose_shadow_dungeon_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadowshelf_0")
    public static final Block SHADOWSHELF_0 = create("shadowshelf_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadowshelf_1")
    public static final Block SHADOWSHELF_1 = create("shadowshelf_1", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadowshelf_2")
    public static final Block SHADOWSHELF_2 = create("shadowshelf_2", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadowshelf_3")
    public static final Block SHADOWSHELF_3 = create("shadowshelf_3", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_arena_block_0")
    public static final Block SHADOW_ARENA_BLOCK_0 = create("shadow_arena_block_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_fissure_0")
    public static final Block SHADOW_FISSURE_0 = create("shadow_fissure_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_fissure_1")
    public static final Block SHADOW_FISSURE_1 = create("shadow_fissure_1", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_fissure_2")
    public static final Block SHADOW_FISSURE_2 = create("shadow_fissure_2", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_fissure_3")
    public static final Block SHADOW_FISSURE_3 = create("shadow_fissure_3", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_fissure_4")
    public static final Block SHADOW_FISSURE_4 = create("shadow_fissure_4", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_fissure_5")
    public static final Block SHADOW_FISSURE_5 = create("shadow_fissure_5", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("cyan_moss_stone")
    public static final Block CYAN_MOSS_STONE = create("cyan_moss_stone", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("cyan_stone")
    public static final Block CYAN_STONE = create("cyan_stone", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("white_sand")
    public static final Block WHITE_SAND = create("white_sand", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("thick_cloud")
    public static final Block THICK_CLOUD = create("thick_cloud", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("windmoor_log")
    public static final Block WINDMOOR_LOG = create("windmoor_log", ContentCatalog.BlockKind.PILLAR);
    @AssignedName("windmoor_wood")
    public static final Block WINDMOOR_WOOD = create("windmoor_wood", ContentCatalog.BlockKind.PILLAR);
    @AssignedName("windmoor_leaves_0")
    public static final Block WINDMOOR_LEAVES_0 = create("windmoor_leaves_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("windmoor_leaves_1")
    public static final Block WINDMOOR_LEAVES_1 = create("windmoor_leaves_1", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("windmoor_leaves_2")
    public static final Block WINDMOOR_LEAVES_2 = create("windmoor_leaves_2", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("cyan_stone_bricks")
    public static final Block CYAN_STONE_BRICKS = create("cyan_stone_bricks", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("cyan_stone_brick_stairs")
    public static final Block CYAN_STONE_BRICK_STAIRS = create("cyan_stone_brick_stairs", ContentCatalog.BlockKind.STAIRS);
    @AssignedName("cyan_stone_brick_slab")
    public static final Block CYAN_STONE_BRICK_SLAB = create("cyan_stone_brick_slab", ContentCatalog.BlockKind.SLAB);
    @AssignedName("cyan_stone_brick_wall")
    public static final Block CYAN_STONE_BRICK_WALL = create("cyan_stone_brick_wall", ContentCatalog.BlockKind.WALL);
    @AssignedName("mossy_cyan_stone_bricks")
    public static final Block MOSSY_CYAN_STONE_BRICKS = create("mossy_cyan_stone_bricks", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("mossy_cyan_stone_brick_stairs")
    public static final Block MOSSY_CYAN_STONE_BRICK_STAIRS = create("mossy_cyan_stone_brick_stairs", ContentCatalog.BlockKind.STAIRS);
    @AssignedName("mossy_cyan_stone_brick_slab")
    public static final Block MOSSY_CYAN_STONE_BRICK_SLAB = create("mossy_cyan_stone_brick_slab", ContentCatalog.BlockKind.SLAB);
    @AssignedName("mossy_cyan_stone_brick_wall")
    public static final Block MOSSY_CYAN_STONE_BRICK_WALL = create("mossy_cyan_stone_brick_wall", ContentCatalog.BlockKind.WALL);
    @AssignedName("chiseled_cyan_stone_bricks")
    public static final Block CHISELED_CYAN_STONE_BRICKS = create("chiseled_cyan_stone_bricks", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("cyan_stone_pillar")
    public static final Block CYAN_STONE_PILLAR = create("cyan_stone_pillar", ContentCatalog.BlockKind.PILLAR);
    @AssignedName("windmoor_planks")
    public static final Block WINDMOOR_PLANKS = create("windmoor_planks", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("windmoor_stairs")
    public static final Block WINDMOOR_STAIRS = create("windmoor_stairs", ContentCatalog.BlockKind.STAIRS);
    @AssignedName("windmoor_slab")
    public static final Block WINDMOOR_SLAB = create("windmoor_slab", ContentCatalog.BlockKind.SLAB);
    @AssignedName("windmoor_fence")
    public static final Block WINDMOOR_FENCE = create("windmoor_fence", ContentCatalog.BlockKind.FENCE);
    @AssignedName("windmoor_fence_gate")
    public static final Block WINDMOOR_FENCE_GATE = create("windmoor_fence_gate", ContentCatalog.BlockKind.FENCE_GATE);
    @AssignedName("windmoor_door")
    public static final Block WINDMOOR_DOOR = create("windmoor_door", ContentCatalog.BlockKind.DOOR);
    @AssignedName("windmoor_trapdoor")
    public static final Block WINDMOOR_TRAPDOOR = create("windmoor_trapdoor", ContentCatalog.BlockKind.TRAP_DOOR);
    @AssignedName("windmoor_pressure_plate")
    public static final Block WINDMOOR_PRESSURE_PLATE = create("windmoor_pressure_plate", ContentCatalog.BlockKind.PRESSURE_PLATE);
    @AssignedName("windmoor_button")
    public static final Block WINDMOOR_BUTTON = create("windmoor_button", ContentCatalog.BlockKind.BUTTON);
    @AssignedName("cyan_stone_pressure_plate")
    public static final Block CYAN_STONE_PRESSURE_PLATE = create("cyan_stone_pressure_plate", ContentCatalog.BlockKind.PRESSURE_PLATE);
    @AssignedName("cyan_stone_button")
    public static final Block CYAN_STONE_BUTTON = create("cyan_stone_button", ContentCatalog.BlockKind.BUTTON);
    @AssignedName("clarity_glass")
    public static final Block CLARITY_GLASS = create("clarity_glass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("carve_clarity_glass")
    public static final Block CARVE_CLARITY_GLASS = create("carve_clarity_glass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("frame_clarity_glass")
    public static final Block FRAME_CLARITY_GLASS = create("frame_clarity_glass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("clarity_glasspane")
    public static final Block CLARITY_GLASSPANE = create("clarity_glasspane", ContentCatalog.BlockKind.PANE);
    @AssignedName("carve_clarity_glasspane")
    public static final Block CARVE_CLARITY_GLASSPANE = create("carve_clarity_glasspane", ContentCatalog.BlockKind.PANE);
    @AssignedName("frame_clarity_glasspane")
    public static final Block FRAME_CLARITY_GLASSPANE = create("frame_clarity_glasspane", ContentCatalog.BlockKind.PANE);
    @AssignedName("breakwind_curtain")
    public static final Block BREAKWIND_CURTAIN = create("breakwind_curtain", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("stripped_windmoor_log")
    public static final Block STRIPPED_WINDMOOR_LOG = create("stripped_windmoor_log", ContentCatalog.BlockKind.PILLAR);
    @AssignedName("stripped_windmoor_wood")
    public static final Block STRIPPED_WINDMOOR_WOOD = create("stripped_windmoor_wood", ContentCatalog.BlockKind.PILLAR);
    @AssignedName("windrunner_crystal_ore")
    public static final Block WINDRUNNER_CRYSTAL_ORE = create("windrunner_crystal_ore", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("windrunner_crystal_block")
    public static final Block WINDRUNNER_CRYSTAL_BLOCK = create("windrunner_crystal_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("congeal_wind_block")
    public static final Block CONGEAL_WIND_BLOCK = create("congeal_wind_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("congeal_wind_ore")
    public static final Block CONGEAL_WIND_ORE = create("congeal_wind_ore", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("wind_iron_block")
    public static final Block WIND_IRON_BLOCK = create("wind_iron_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("ejection_pressure_plate")
    public static final Block EJECTION_PRESSURE_PLATE = create("ejection_pressure_plate", ContentCatalog.BlockKind.PRESSURE_PLATE);
    @AssignedName("wind_iron_bars")
    public static final Block WIND_IRON_BARS = create("wind_iron_bars", ContentCatalog.BlockKind.PANE);
    @AssignedName("ejection_pressure_block")
    public static final Block EJECTION_PRESSURE_BLOCK = create("ejection_pressure_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("starcall_block")
    public static final Block STARCALL_BLOCK = create("starcall_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_crack")
    public static final Block DYEDREAM_CRACK = create("dyedream_crack", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("starcall_crack")
    public static final Block STARCALL_CRACK = create("starcall_crack", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("guard_block")
    public static final Block GUARD_BLOCK = create("guard_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("restrainmove_block")
    public static final Block RESTRAINMOVE_BLOCK = create("restrainmove_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dream_spawner_0")
    public static final Block DREAM_SPAWNER_0 = create("dream_spawner_0", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dream_spawner_1")
    public static final Block DREAM_SPAWNER_1 = create("dream_spawner_1", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_vortex")
    public static final Block SHADOW_VORTEX = create("shadow_vortex", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("aaroncos_arena_portals")
    public static final Block AARONCOS_ARENA_PORTALS = create("aaroncos_arena_portals", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("workshop_cauldron")
    public static final Block WORKSHOP_CAULDRON = create("workshop_cauldron", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("workshop_blast")
    public static final Block WORKSHOP_BLAST = create("workshop_blast", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("workshop_anvil")
    public static final Block WORKSHOP_ANVIL = create("workshop_anvil", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("workshop_grind")
    public static final Block WORKSHOP_GRIND = create("workshop_grind", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_sapling")
    public static final Block DYEDREAM_SAPLING = create("dyedream_sapling", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_camellia_crop")
    public static final Block CROP_0A = create("dyedream_camellia_crop", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_camellia_seed")
    public static final Block CROP_0B = create("dyedream_camellia_seed", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("luminous_violet_crop")
    public static final Block CROP_2A = create("luminous_violet_crop", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("luminous_violet_seed")
    public static final Block CROP_2B = create("luminous_violet_seed", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("ringing_cloud_flower_crop")
    public static final Block CROP_3A = create("ringing_cloud_flower_crop", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("ringing_cloud_flower_seed")
    public static final Block CROP_3B = create("ringing_cloud_flower_seed", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("stalk_grass")
    public static final Block GRASS_3 = create("stalk_grass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("tall_stalk_grass")
    public static final Block GRASS_4 = create("tall_stalk_grass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("pink_cap_mushroom")
    public static final Block FLOWER_8 = create("pink_cap_mushroom", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("tall_pink_cap_mushroom")
    public static final Block FLOWER_10 = create("tall_pink_cap_mushroom", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("calcite_spike")
    public static final Block GRASS_5 = create("calcite_spike", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("tall_calcite_spike")
    public static final Block GRASS_6 = create("tall_calcite_spike", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_moss")
    public static final Block GRASS_7 = create("dyedream_moss", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("lantern_flower")
    public static final Block FLOWER_9 = create("lantern_flower", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_lily_of_the_valley")
    public static final Block FLOWER_13 = create("dyedream_lily_of_the_valley", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("strange_fern")
    public static final Block FLOWER_14 = create("strange_fern", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_vine")
    public static final Block VINE_0 = create("dyedream_vine", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_lily_pad")
    public static final Block DYEDREAM_LILY_PAD = create("dyedream_lily_pad", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_seagrass")
    public static final Block DYEDREAM_SEAGRASS = create("dyedream_seagrass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_lotus")
    public static final Block DYEDREAM_LOTUS = create("dyedream_lotus", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dreamforge_lotus")
    public static final Block FLOWER_11 = create("dreamforge_lotus", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("lost_dream_forge_lotus")
    public static final Block FLOWER_12 = create("lost_dream_forge_lotus", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("edelweiss")
    public static final Block FLOWER_16 = create("edelweiss", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("frost_edelweiss")
    public static final Block FLOWER_17 = create("frost_edelweiss", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("pale_snow_lotus_crop")
    public static final Block CROP_1A = create("pale_snow_lotus_crop", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("pale_snow_lotus_seed")
    public static final Block CROP_1B = create("pale_snow_lotus_seed", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("cotton_crop")
    public static final Block CROP_4A = create("cotton_crop", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("cotton_crop_seed")
    public static final Block CROP_4B = create("cotton_crop_seed", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("devil_star_orchid")
    public static final Block FLOWER_1 = create("devil_star_orchid", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("lisianthus")
    public static final Block FLOWER_2 = create("lisianthus", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("jungle_spore_plant")
    public static final Block JUNGLE_SPORE_PLANT = create("jungle_spore_plant", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("mallow")
    public static final Block FLOWER_5 = create("mallow", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("common_reed")
    public static final Block GRASS_1 = create("common_reed", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("orchid_wheat")
    public static final Block FLOWER_3 = create("orchid_wheat", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("orchid_wheat_grass")
    public static final Block GRASS_2 = create("orchid_wheat_grass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("goldenrod")
    public static final Block GOLDENROD = create("goldenrod", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("fourleaf_clover")
    public static final Block FOURLEAF_CLOVER = create("fourleaf_clover", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("flame_flower")
    public static final Block FLOWER_6 = create("flame_flower", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("crimson_thorn")
    public static final Block FLOWER_7 = create("crimson_thorn", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_fungus")
    public static final Block SHADOW_FUNGUS = create("shadow_fungus", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_mycelium")
    public static final Block GRASS_8 = create("shadow_mycelium", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("tall_shadow_mycelium")
    public static final Block GRASS_9 = create("tall_shadow_mycelium", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_stem_fern")
    public static final Block GRASS_10 = create("shadow_stem_fern", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_fern")
    public static final Block GRASS_11 = create("shadow_fern", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("tall_shadow_fern")
    public static final Block GRASS_12 = create("tall_shadow_fern", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("white_calamity_flower")
    public static final Block FLOWER_15 = create("white_calamity_flower", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("fuzzy_moss")
    public static final Block GRASS_13 = create("fuzzy_moss", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("windcut_grass")
    public static final Block GRASS_14 = create("windcut_grass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("windfeather_grass")
    public static final Block GRASS_15 = create("windfeather_grass", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("wind_island_reed")
    public static final Block FLOWER_18 = create("wind_island_reed", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("fig_vine")
    public static final Block FIG_VINE = create("fig_vine", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("memento_11")
    public static final Block MEMENTO_11 = create("memento_11", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dream_church_west")
    public static final Block STRUCTURE_BLOCK_0 = create("dream_church_west", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_laboratory")
    public static final Block STRUCTURE_BLOCK_1 = create("dyedream_laboratory", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("pinkagaric_house")
    public static final Block STRUCTURE_BLOCK_2 = create("pinkagaric_house", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dream_wishing_tree")
    public static final Block STRUCTURE_BLOCK_3 = create("dream_wishing_tree", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dream_church_east")
    public static final Block STRUCTURE_BLOCK_4 = create("dream_church_east", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("traveler_house")
    public static final Block STRUCTURE_BLOCK_5 = create("traveler_house", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("crystal_ball_ruin")
    public static final Block STRUCTURE_BLOCK_6 = create("crystal_ball_ruin", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("meltdream_liquid_well")
    public static final Block STRUCTURE_BLOCK_7 = create("meltdream_liquid_well", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("sky_stone_pillar")
    public static final Block STRUCTURE_BLOCK_8 = create("sky_stone_pillar", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_world_gate")
    public static final Block STRUCTURE_BLOCK_9 = create("shadow_world_gate", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_tomb")
    public static final Block STRUCTURE_BLOCK_10 = create("shadow_tomb", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_chain")
    public static final Block STRUCTURE_BLOCK_11 = create("shadow_chain", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dream_church_annex")
    public static final Block STRUCTURE_BLOCK_12 = create("dream_church_annex", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_shelter")
    public static final Block STRUCTURE_BLOCK_13 = create("shadow_shelter", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_fungus_nest")
    public static final Block STRUCTURE_BLOCK_14 = create("shadow_fungus_nest", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_foundry")
    public static final Block STRUCTURE_BLOCK_15 = create("shadow_foundry", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("claypan_1")
    public static final Block CLAYPAN_1 = create("claypan_1", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("claypan_2")
    public static final Block CLAYPAN_2 = create("claypan_2", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("desert_fortress")
    public static final Block STRUCTURE_BLOCK_16 = create("desert_fortress", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_passage")
    public static final Block STRUCTURE_BLOCK_17 = create("shadow_dungeon_passage", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_door_1")
    public static final Block SHADOW_DUNGEON_DOOR_1 = create("shadow_dungeon_door_1", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("true_shadow_bed")
    public static final Block TRUE_SHADOW_BED = create("true_shadow_bed", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_dungeon_door_3")
    public static final Block SHADOW_DUNGEON_DOOR_3 = create("shadow_dungeon_door_3", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("aaroncos_hand_spawn_block")
    public static final Block AARONCOS_HAND_SPAWN_BLOCK = create("aaroncos_hand_spawn_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_fungus_house")
    public static final Block STRUCTURE_BLOCK_18 = create("shadow_fungus_house", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("angel_block")
    public static final Block ANGEL_BLOCK = create("angel_block", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("shadow_underground_workroom")
    public static final Block STRUCTURE_BLOCK_19 = create("shadow_underground_workroom", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dyedream_worldtree")
    public static final Block STRUCTURE_BLOCK_20 = create("dyedream_worldtree", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("windmoor_tree")
    public static final Block STRUCTURE_BLOCK_21 = create("windmoor_tree", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("hot_air_balloon")
    public static final Block STRUCTURE_BLOCK_22 = create("hot_air_balloon", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("christmas_tree")
    public static final Block STRUCTURE_BLOCK_23 = create("christmas_tree", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("light_firefly_glass_jar")
    public static final Block LIGHT_FIREFLY_GLASS_JAR = create("light_firefly_glass_jar", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("dream_train_structure")
    public static final Block DREAM_TRAIN_STRUCTURE = create("dream_train_structure", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("wind_knight_spawnblock_1")
    public static final Block WIND_KNIGHT_SPAWNBLOCK_1 = create("wind_knight_spawnblock_1", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("wind_knight_spawnblock_2")
    public static final Block WIND_KNIGHT_SPAWNBLOCK_2 = create("wind_knight_spawnblock_2", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("wind_knight_spawnblock_3")
    public static final Block WIND_KNIGHT_SPAWNBLOCK_3 = create("wind_knight_spawnblock_3", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("wind_knight_spawnblock_4")
    public static final Block WIND_KNIGHT_SPAWNBLOCK_4 = create("wind_knight_spawnblock_4", ContentCatalog.BlockKind.GENERIC);
    @AssignedName("small_stone_spirit_block")
    public static final Block SMALL_STONE_SPIRIT_BLOCK = create("small_stone_spirit_block", ContentCatalog.BlockKind.GENERIC);

    private PasterDreamBlocks() {
    }

    public static void register() {
        GeminiRegistration.blocks(PasterDreamBlocks.class);
    }

    @Override
    public void postProcessField(String namespace, Block value, String identifier, Field field) {
        ContentCatalog.BlockItemKind kind = ContentCatalog.blockItemKind(identifier);
        if (kind == ContentCatalog.BlockItemKind.NONE) {
            return;
        }
        Item item = kind == ContentCatalog.BlockItemKind.DOUBLE_BLOCK_ITEM
            ? new DoubleHighBlockItem(value, new Item.Properties())
            : new BlockItem(value, new Item.Properties());
        getItemSink().register(ResourceLocation.fromNamespaceAndPath(namespace, identifier), item);
    }

    public static Block get(String id) {
        return BuiltInRegistries.BLOCK.getOptional(PasterDream.id(id))
            .orElseThrow(() -> new IllegalArgumentException("Unknown PasterDream block: " + id));
    }

    private static Block create(String id, ContentCatalog.BlockKind kind) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
            .strength(1.0F, 1.0F)
            .sound(SoundType.STONE);
        BlockBehaviour.Properties plantProperties = BlockBehaviour.Properties.of()
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .noOcclusion();
        ContentCatalog.BlockKind semanticKind = semanticKind(id, kind);
        if (semanticKind == ContentCatalog.BlockKind.GENERIC
            && ContentCatalog.BLOCK_ENTITIES.contains(id)) {
            return new PasterDreamEntityBlock(properties, id);
        }
        if (semanticKind == ContentCatalog.BlockKind.GENERIC
            && SCHEDULED_BEHAVIOR_BLOCKS.contains(id)) {
            return new PasterDreamBehaviorBlock(properties, id);
        }
        return switch (semanticKind) {
            case STAIRS -> new StairBlock(Blocks.STONE.defaultBlockState(), properties);
            case SLAB -> new SlabBlock(properties);
            case WALL -> new WallBlock(properties);
            case FENCE -> new FenceBlock(properties);
            case FENCE_GATE -> new FenceGateBlock(WoodType.OAK, properties);
            case DOOR -> new DoorBlock(BlockSetType.OAK, properties);
            case TRAP_DOOR -> new TrapDoorBlock(BlockSetType.OAK, properties);
            case PRESSURE_PLATE -> new PressurePlateBlock(BlockSetType.OAK, properties);
            case BUTTON -> new ButtonBlock(BlockSetType.OAK, 20, properties);
            case PANE -> new IronBarsBlock(properties.noOcclusion());
            case LEAVES -> new PasterDreamLeafBehaviorBlock(
                properties.noOcclusion(),
                id
            );
            case PILLAR -> new RotatedPillarBlock(properties);
            case FLOWER -> new PasterDreamFlowerBehaviorBlock(
                MobEffects.SATURATION,
                4.0F,
                plantProperties,
                id
            );
            case DOUBLE_PLANT -> new PasterDreamDoublePlantBehaviorBlock(
                plantProperties,
                id
            );
            case SEAGRASS -> new SeagrassBlock(plantProperties);
            case SUGAR_CANE -> new SugarCaneBlock(plantProperties.randomTicks());
            case LILY_PAD -> new WaterlilyBlock(plantProperties);
            case SAND -> new Block(properties);
            case GENERIC -> new Block(properties);
        };
    }

    private static ContentCatalog.BlockKind semanticKind(
        String id,
        ContentCatalog.BlockKind fallback
    ) {
        if (DOUBLE_PLANT_BLOCKS.contains(id)) {
            return ContentCatalog.BlockKind.DOUBLE_PLANT;
        }
        if (FLOWER_BLOCKS.contains(id)
            || id.equals("jungle_spore_plant") || id.equals("goldenrod")
            || id.equals("fourleaf_clover") || id.equals("shadow_fungus")
            || id.equals("dyedream_sapling")) {
            return ContentCatalog.BlockKind.FLOWER;
        }
        if (id.equals("dyedream_lily_pad")) {
            return ContentCatalog.BlockKind.LILY_PAD;
        }
        if (id.equals("dyedream_seagrass")) {
            return ContentCatalog.BlockKind.SEAGRASS;
        }
        if (id.equals("dyedream_vine") || id.equals("fig_vine")) {
            return ContentCatalog.BlockKind.SUGAR_CANE;
        }
        if (id.startsWith("windmoor_leaves_")) {
            return ContentCatalog.BlockKind.LEAVES;
        }
        if (id.equals("dyedream_sand") || id.equals("white_sand")) {
            return ContentCatalog.BlockKind.SAND;
        }
        return fallback;
    }
}
