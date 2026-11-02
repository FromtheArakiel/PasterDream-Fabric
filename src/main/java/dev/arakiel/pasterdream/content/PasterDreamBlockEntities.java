package dev.arakiel.pasterdream.content;

import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.gemini.registration.container.BlockEntityRegistryContainer;
import dev.arakiel.pasterdream.PasterDream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamBlockEntities implements BlockEntityRegistryContainer {
    @AssignedName("aaroncos_hand_chest")
    public static final BlockEntityType<?> AARONCOS_HAND_CHEST = create("aaroncos_hand_chest");
    @AssignedName("aaroncos_hand_spawn_block")
    public static final BlockEntityType<?> AARONCOS_HAND_SPAWN_BLOCK = create("aaroncos_hand_spawn_block");
    @AssignedName("birds_nest")
    public static final BlockEntityType<?> BIRDS_NEST = create("birds_nest");
    @AssignedName("broken_shadow_dungeon_portal")
    public static final BlockEntityType<?> BROKEN_SHADOW_DUNGEON_PORTAL = create("broken_shadow_dungeon_portal");
    @AssignedName("claypan_1")
    public static final BlockEntityType<?> CLAYPAN_1 = create("claypan_1");
    @AssignedName("desert_hero_tomb")
    public static final BlockEntityType<?> DESERT_HERO_TOMB = create("desert_hero_tomb");
    @AssignedName("dream_accumulator")
    public static final BlockEntityType<?> DREAM_ACCUMULATOR = create("dream_accumulator");
    @AssignedName("dream_cauldron")
    public static final BlockEntityType<?> DREAM_CAULDRON = create("dream_cauldron");
    @AssignedName("dream_spawner_0")
    public static final BlockEntityType<?> DREAM_SPAWNER_0 = create("dream_spawner_0");
    @AssignedName("dyedream_crack")
    public static final BlockEntityType<?> DYEDREAM_CRACK = create("dyedream_crack");
    @AssignedName("dyedream_desk")
    public static final BlockEntityType<?> DYEDREAM_DESK = create("dyedream_desk");
    @AssignedName("dyedream_sapling")
    public static final BlockEntityType<?> DYEDREAM_SAPLING = create("dyedream_sapling");
    @AssignedName("ecology_glass_jar")
    public static final BlockEntityType<?> ECOLOGY_GLASS_JAR = create("ecology_glass_jar");
    @AssignedName("firefly_glass_jar")
    public static final BlockEntityType<?> FIREFLY_GLASS_JAR = create("firefly_glass_jar");
    @AssignedName("firefly_nest")
    public static final BlockEntityType<?> FIREFLY_NEST = create("firefly_nest");
    @AssignedName("frost_edelweiss")
    public static final BlockEntityType<?> FLOWER_17 = create("frost_edelweiss");
    @AssignedName("forced_tower")
    public static final BlockEntityType<?> FORCED_TOWER = create("forced_tower");
    @AssignedName("golden_fox_sculpture")
    public static final BlockEntityType<?> GOLDEN_FOX_SCULPTURE = create("golden_fox_sculpture");
    @AssignedName("windcut_grass")
    public static final BlockEntityType<?> GRASS_14 = create("windcut_grass");
    @AssignedName("guard_block")
    public static final BlockEntityType<?> GUARD_BLOCK = create("guard_block");
    @AssignedName("guard_crystal")
    public static final BlockEntityType<?> GUARD_CRYSTAL = create("guard_crystal");
    @AssignedName("life_crystal")
    public static final BlockEntityType<?> LIFE_CRYSTAL = create("life_crystal");
    @AssignedName("light_firefly_glass_jar")
    public static final BlockEntityType<?> LIGHT_FIREFLY_GLASS_JAR = create("light_firefly_glass_jar");
    @AssignedName("lost_sword_block")
    public static final BlockEntityType<?> LOST_SWORD_BLOCK = create("lost_sword_block");
    @AssignedName("meltdream_chest")
    public static final BlockEntityType<?> MELTDREAM_CHEST = create("meltdream_chest");
    @AssignedName("meltdream_chest_open")
    public static final BlockEntityType<?> MELTDREAM_CHEST_OPEN = create("meltdream_chest_open");
    @AssignedName("picnic_basket")
    public static final BlockEntityType<?> PICNIC_BASKET = create("picnic_basket");
    @AssignedName("qym_doll_0")
    public static final BlockEntityType<?> QYM_DOLL_0 = create("qym_doll_0");
    @AssignedName("research_table")
    public static final BlockEntityType<?> RESEARCH_TABLE = create("research_table");
    @AssignedName("restrainmove_block")
    public static final BlockEntityType<?> RESTRAINMOVE_BLOCK = create("restrainmove_block");
    @AssignedName("shadow_bed")
    public static final BlockEntityType<?> SHADOW_BED = create("shadow_bed");
    @AssignedName("shadow_blast_furnace")
    public static final BlockEntityType<?> SHADOW_BLAST_FURNACE = create("shadow_blast_furnace");
    @AssignedName("shadow_brazier")
    public static final BlockEntityType<?> SHADOW_BRAZIER = create("shadow_brazier");
    @AssignedName("shadow_chest")
    public static final BlockEntityType<?> SHADOW_CHEST = create("shadow_chest");
    @AssignedName("shadow_desk")
    public static final BlockEntityType<?> SHADOW_DESK = create("shadow_desk");
    @AssignedName("shadow_dungeon_portal")
    public static final BlockEntityType<?> SHADOW_DUNGEON_PORTAL = create("shadow_dungeon_portal");
    @AssignedName("shadow_fungus")
    public static final BlockEntityType<?> SHADOW_FUNGUS = create("shadow_fungus");
    @AssignedName("shadow_trap_0")
    public static final BlockEntityType<?> SHADOW_TRAP_0 = create("shadow_trap_0");
    @AssignedName("shadow_vortex")
    public static final BlockEntityType<?> SHADOW_VORTEX = create("shadow_vortex");
    @AssignedName("dream_church_west")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_0 = create("dream_church_west");
    @AssignedName("dyedream_laboratory")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_1 = create("dyedream_laboratory");
    @AssignedName("shadow_tomb")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_10 = create("shadow_tomb");
    @AssignedName("shadow_chain")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_11 = create("shadow_chain");
    @AssignedName("dream_church_annex")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_12 = create("dream_church_annex");
    @AssignedName("shadow_shelter")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_13 = create("shadow_shelter");
    @AssignedName("shadow_fungus_nest")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_14 = create("shadow_fungus_nest");
    @AssignedName("shadow_foundry")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_15 = create("shadow_foundry");
    @AssignedName("desert_fortress")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_16 = create("desert_fortress");
    @AssignedName("shadow_dungeon_passage")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_17 = create("shadow_dungeon_passage");
    @AssignedName("shadow_fungus_house")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_18 = create("shadow_fungus_house");
    @AssignedName("shadow_underground_workroom")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_19 = create("shadow_underground_workroom");
    @AssignedName("pinkagaric_house")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_2 = create("pinkagaric_house");
    @AssignedName("dyedream_worldtree")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_20 = create("dyedream_worldtree");
    @AssignedName("windmoor_tree")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_21 = create("windmoor_tree");
    @AssignedName("hot_air_balloon")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_22 = create("hot_air_balloon");
    @AssignedName("christmas_tree")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_23 = create("christmas_tree");
    @AssignedName("dream_wishing_tree")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_3 = create("dream_wishing_tree");
    @AssignedName("dream_church_east")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_4 = create("dream_church_east");
    @AssignedName("traveler_house")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_5 = create("traveler_house");
    @AssignedName("crystal_ball_ruin")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_6 = create("crystal_ball_ruin");
    @AssignedName("meltdream_liquid_well")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_7 = create("meltdream_liquid_well");
    @AssignedName("sky_stone_pillar")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_8 = create("sky_stone_pillar");
    @AssignedName("shadow_world_gate")
    public static final BlockEntityType<?> STRUCTURE_BLOCK_9 = create("shadow_world_gate");
    @AssignedName("the_endless_book_of_dream_seekers")
    public static final BlockEntityType<?> THE_ENDLESS_BOOK_OF_DREAM_SEEKERS = create("the_endless_book_of_dream_seekers");
    @AssignedName("true_shadow_bed")
    public static final BlockEntityType<?> TRUE_SHADOW_BED = create("true_shadow_bed");
    @AssignedName("twilight_lantern")
    public static final BlockEntityType<?> TWILIGHT_LANTERN = create("twilight_lantern");
    @AssignedName("uuz_doll_0")
    public static final BlockEntityType<?> UUZ_DOLL_0 = create("uuz_doll_0");
    @AssignedName("weapon_table")
    public static final BlockEntityType<?> WEAPON_TABLE = create("weapon_table");
    @AssignedName("weapon_workshop")
    public static final BlockEntityType<?> WEAPON_WORKSHOP = create("weapon_workshop");
    @AssignedName("wind_knight_spawnblock_0")
    public static final BlockEntityType<?> WIND_KNIGHT_SPAWNBLOCK_0 = create("wind_knight_spawnblock_0");
    @AssignedName("wind_knight_spawnblock_1")
    public static final BlockEntityType<?> WIND_KNIGHT_SPAWNBLOCK_1 = create("wind_knight_spawnblock_1");
    @AssignedName("wind_knight_spawnblock_2")
    public static final BlockEntityType<?> WIND_KNIGHT_SPAWNBLOCK_2 = create("wind_knight_spawnblock_2");
    @AssignedName("wind_knight_spawnblock_3")
    public static final BlockEntityType<?> WIND_KNIGHT_SPAWNBLOCK_3 = create("wind_knight_spawnblock_3");
    @AssignedName("wind_knight_spawnblock_4")
    public static final BlockEntityType<?> WIND_KNIGHT_SPAWNBLOCK_4 = create("wind_knight_spawnblock_4");
    @AssignedName("windmoor_crate")
    public static final BlockEntityType<?> WINDMOOR_CRATE = create("windmoor_crate");
    @AssignedName("workshop_anvil")
    public static final BlockEntityType<?> WORKSHOP_ANVIL = create("workshop_anvil");
    @AssignedName("workshop_blast")
    public static final BlockEntityType<?> WORKSHOP_BLAST = create("workshop_blast");
    @AssignedName("workshop_cauldron")
    public static final BlockEntityType<?> WORKSHOP_CAULDRON = create("workshop_cauldron");
    @AssignedName("workshop_grind")
    public static final BlockEntityType<?> WORKSHOP_GRIND = create("workshop_grind");

    private PasterDreamBlockEntities() {
    }

    public static void register() {
        GeminiRegistration.blockEntities(PasterDreamBlockEntities.class);
    }

    public static BlockEntityType<?> get(String id) {
        return BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(PasterDream.id(id))
            .orElseThrow(() -> new IllegalArgumentException("Unknown PasterDream block entity: " + id));
    }

    static BlockEntityType<?> getType(BlockState state) {
        return BuiltInRegistries.BLOCK_ENTITY_TYPE.stream()
            .filter(type -> type.isValid(state))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No block entity type for state " + state));
    }

    private static BlockEntityType<PasterDreamBlockEntity> create(String id) {
        return BlockEntityType.Builder.of(
            PasterDreamBlockEntity::new,
            PasterDreamBlocks.get(id)
        ).build(null);
    }
}
