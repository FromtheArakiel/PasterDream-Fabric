package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.pasterdream.PasterDream;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

final class PasterDreamStructureBlockPlacer {
	private PasterDreamStructureBlockPlacer() {
	}

	static void place(ServerLevel level, BlockPos pos, String blockId) {
		if (blockId.equals("dream_church_west")) {
			int index = level.getRandom().nextInt(4);
			String[] templates = {"dream_church_0", "dream_church_1", "dream_church_2", "dream_church_3"};
			Rotation[] rotations = {Rotation.NONE, Rotation.CLOCKWISE_90, Rotation.CLOCKWISE_180, Rotation.COUNTERCLOCKWISE_90};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("shadow_tomb")) {
			int index = level.getRandom().nextInt(6);
			String[] templates = {"shadow_tomb_0", "shadow_tomb_1", "shadow_tomb_2", "shadow_tomb_3", "shadow_tomb_4", "shadow_tomb_5"};
			Rotation[] rotations = {Rotation.NONE, Rotation.CLOCKWISE_90, Rotation.CLOCKWISE_90, Rotation.CLOCKWISE_90, Rotation.CLOCKWISE_90, Rotation.CLOCKWISE_90};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("shadow_chain")) {
			int index = level.getRandom().nextInt(2);
			String[] templates = {"shadow_chain_0", "shadow_chain_1"};
			Rotation[] rotations = {Rotation.NONE, Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("dream_church_annex")) {
			int index = level.getRandom().nextInt(2);
			String[] templates = {"dream_church_9", "dream_church_8"};
			Rotation[] rotations = {Rotation.NONE, Rotation.CLOCKWISE_90};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("shadow_shelter")) {
			int index = level.getRandom().nextInt(2);
			String[] templates = {"shadow_shelter_0", "shadow_shelter_1"};
			Rotation[] rotations = {Rotation.NONE, Rotation.CLOCKWISE_90};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("shadow_fungus_nest")) {
			int index = level.getRandom().nextInt(3);
			String[] templates = {"shadow_fungus_nest_0", "shadow_fungus_nest_1", "shadow_fungus_nest_2"};
			Rotation[] rotations = {Rotation.NONE, Rotation.CLOCKWISE_90, Rotation.CLOCKWISE_90};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("shadow_foundry")) {
			int index = level.getRandom().nextInt(3);
			String[] templates = {"shadow_foundry_0", "shadow_foundry_1", "shadow_foundry_2"};
			Rotation[] rotations = {Rotation.NONE, Rotation.NONE, Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("desert_fortress")) {
			int index = level.getRandom().nextInt(1);
			String[] templates = {"desert_fortress"};
			Rotation[] rotations = {Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("shadow_dungeon_passage")) {
			int index = level.getRandom().nextInt(4);
			String[] templates = {"shadow_dungeon_door", "shadow_dungeon_wall_1", "shadow_dungeon_wall_0", "shadow_dungeon_wall_2"};
			Rotation[] rotations = {Rotation.NONE, Rotation.NONE, Rotation.NONE, Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("shadow_fungus_house")) {
			int index = level.getRandom().nextInt(2);
			String[] templates = {"shadow_fungus_house_0", "shadow_fungus_house_1"};
			Rotation[] rotations = {Rotation.NONE, Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("shadow_underground_workroom")) {
			int index = level.getRandom().nextInt(2);
			String[] templates = {"shadow_underground_workroom", "shadow_underground_workroom_1"};
			Rotation[] rotations = {Rotation.NONE, Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("dyedream_laboratory")) {
			int index = level.getRandom().nextInt(1);
			String[] templates = {"dyedream_laboratory_0"};
			Rotation[] rotations = {Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("dyedream_worldtree")) {
			int index = level.getRandom().nextInt(2);
			String[] templates = {"dyedream_worldtree_true", "dyedream_worldtree"};
			Rotation[] rotations = {Rotation.NONE, Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("windmoor_tree")) {
			int index = level.getRandom().nextInt(4);
			String[] templates = {"windmoor_tree_0", "windmoor_tree_0", "windmoor_tree_0", "windmoor_tree_0"};
			Rotation[] rotations = {Rotation.NONE, Rotation.CLOCKWISE_90, Rotation.CLOCKWISE_180, Rotation.COUNTERCLOCKWISE_90};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("hot_air_balloon")) {
			int index = level.getRandom().nextInt(3);
			String[] templates = {"hot_air_balloon_0", "hot_air_balloon_1", "hot_air_balloon_2"};
			Rotation[] rotations = {Rotation.NONE, Rotation.NONE, Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("christmas_tree")) {
			int index = level.getRandom().nextInt(1);
			String[] templates = {"christmas_tree_0"};
			Rotation[] rotations = {Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("pinkagaric_house")) {
			int index = level.getRandom().nextInt(4);
			String[] templates = {"pinkagaric_house_0", "pinkagaric_house_1", "pinkagaric_house_2", "pinkagaric_house_3"};
			Rotation[] rotations = {Rotation.NONE, Rotation.NONE, Rotation.NONE, Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("dream_wishing_tree")) {
			int index = level.getRandom().nextInt(2);
			String[] templates = {"dream_wishingtree_0", "dream_wishingtree_1"};
			Rotation[] rotations = {Rotation.NONE, Rotation.CLOCKWISE_90};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("dream_church_east")) {
			int index = level.getRandom().nextInt(4);
			String[] templates = {"dream_church_4", "dream_church_5", "dream_church_6", "dream_church_7"};
			Rotation[] rotations = {Rotation.NONE, Rotation.CLOCKWISE_90, Rotation.CLOCKWISE_180, Rotation.COUNTERCLOCKWISE_90};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("traveler_house")) {
			int index = level.getRandom().nextInt(3);
			String[] templates = {"traveler_house_1", "traveler_house_0", "traveler_house_2"};
			Rotation[] rotations = {Rotation.NONE, Rotation.CLOCKWISE_90, Rotation.CLOCKWISE_180};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("crystal_ball_ruin")) {
			int index = level.getRandom().nextInt(2);
			String[] templates = {"crystal_ball_0", "crystal_ball_1"};
			Rotation[] rotations = {Rotation.NONE, Rotation.CLOCKWISE_90};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("meltdream_liquid_well")) {
			int index = level.getRandom().nextInt(2);
			String[] templates = {"meltdream_liquid_well", "meltdream_liquid_well_0"};
			Rotation[] rotations = {Rotation.NONE, Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("sky_stone_pillar")) {
			int index = level.getRandom().nextInt(3);
			String[] templates = {"stone_pillar_sky0", "stone_pillar_sky1", "stone_pillar_sky2"};
			Rotation[] rotations = {Rotation.NONE, Rotation.NONE, Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
		if (blockId.equals("shadow_world_gate")) {
			int index = level.getRandom().nextInt(1);
			String[] templates = {"shadow_world_door"};
			Rotation[] rotations = {Rotation.NONE};
			StructureTemplate template = level.getStructureManager()
				.getOrCreate(PasterDream.id(templates[index]));
			if (template != null) {
				template.placeInWorld(
					level,
					pos,
					pos,
					new StructurePlaceSettings().setRotation(rotations[index]).setMirror(Mirror.NONE).setIgnoreEntities(false),
					level.getRandom(),
					3
				);
			}
		}
	}

	static boolean supports(String blockId) {
		return switch (blockId) {
			case "dream_church_west", "shadow_tomb", "shadow_chain", "dream_church_annex", "shadow_shelter", "shadow_fungus_nest", "shadow_foundry", "desert_fortress", "shadow_dungeon_passage", "shadow_fungus_house", "shadow_underground_workroom", "dyedream_laboratory", "dyedream_worldtree", "windmoor_tree", "hot_air_balloon", "christmas_tree", "pinkagaric_house", "dream_wishing_tree", "dream_church_east", "traveler_house", "crystal_ball_ruin", "meltdream_liquid_well", "sky_stone_pillar", "shadow_world_gate" -> true;
			default -> false;
		};
	}

	static void placeNamed(ServerLevel level, BlockPos pos, String templateName) {
		placeNamed(level, pos, templateName, Rotation.NONE);
	}

	static void placeNamed(
		ServerLevel level,
		BlockPos pos,
		String templateName,
		Rotation rotation
	) {
		StructureTemplate template = level.getStructureManager()
			.getOrCreate(PasterDream.id(templateName));
		if (template != null) {
			template.placeInWorld(
				level,
				pos,
				pos,
				new StructurePlaceSettings().setRotation(rotation).setMirror(Mirror.NONE).setIgnoreEntities(false),
				level.getRandom(),
				3
			);
		}
	}
}
