package dev.arakiel.pasterdream.content;

import dev.arakiel.gemini.registration.AutoRegistryContainer;
import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.pasterdream.PasterDream;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamArmorMaterials implements AutoRegistryContainer<ArmorMaterial> {
	@AssignedName("copper_armor")
	public static final ArmorMaterial COPPER = create(
		8,
		0.0F,
		0.0F,
		"copper_ingot",
		"tong__",
		SoundEvents.ARMOR_EQUIP_IRON,
		1,
		3,
		5,
		2
	);

	@AssignedName("titanium_armor")
	public static final ArmorMaterial TITANIUM = create(
		17,
		3.0F,
		0.1F,
		"titanium_ingot",
		"tai__",
		SoundEvents.ARMOR_EQUIP_DIAMOND,
		3,
		6,
		8,
		3
	);

	@AssignedName("sculk_armor")
	public static final ArmorMaterial SCULK = create(
		9,
		3.5F,
		0.15F,
		"sculk_heart",
		"hui_xiang__",
		SoundEvents.ARMOR_EQUIP_GENERIC,
		3,
		6,
		8,
		3
	);

	@AssignedName("dyedream_armor")
	public static final ArmorMaterial DYEDREAM = create(
		22,
		3.0F,
		0.1F,
		"dyedream_ingot",
		"ran_meng__",
		SoundEvents.ARMOR_EQUIP_DIAMOND,
		3,
		6,
		8,
		3
	);

	@AssignedName("qym_armor")
	public static final ArmorMaterial QYM = create(
		99,
		10.0F,
		1.0F,
		"memory_gem_0",
		"qym_",
		SoundEvents.ARMOR_EQUIP_GENERIC,
		2,
		10,
		10,
		10
	);

	@AssignedName("machine_wing")
	public static final ArmorMaterial MACHINE_WING = create(
		9,
		1.0F,
		0.0F,
		"",
		"",
		SoundEvents.ARMOR_EQUIP_NETHERITE,
		2,
		5,
		7,
		2
	);

	@AssignedName("forsakens_wing")
	public static final ArmorMaterial FORSAKENS_WING = create(
		7,
		3.0F,
		0.0F,
		"nightmare_fuel",
		"",
		SoundEvents.ARMOR_EQUIP_GENERIC,
		2,
		5,
		7,
		2
	);

	@AssignedName("angel_wing")
	public static final ArmorMaterial ANGEL_WING = create(
		9,
		2.0F,
		0.0F,
		"minecraft:feather",
		"",
		SoundEvents.ARMOR_EQUIP_GENERIC,
		2,
		5,
		6,
		2
	);

	private PasterDreamArmorMaterials() {
	}

	public static void register() {
		GeminiRegistration.register(PasterDreamArmorMaterials.class, PasterDream.MOD_ID);
	}

	@Override
	public Registry<ArmorMaterial> getRegistry() {
		return BuiltInRegistries.ARMOR_MATERIAL;
	}

	@Override
	public Class<ArmorMaterial> getTargetFieldType() {
		return ArmorMaterial.class;
	}

	private static ArmorMaterial create(
		int enchantment,
		float toughness,
		float knockbackResistance,
		String repairItem,
		String texture,
		net.minecraft.core.Holder<net.minecraft.sounds.SoundEvent> sound,
		int boots,
		int leggings,
		int chestplate,
		int helmet
	) {
		return new ArmorMaterial(
			Map.of(
				ArmorItem.Type.HELMET,
				helmet,
				ArmorItem.Type.CHESTPLATE,
				chestplate,
				ArmorItem.Type.LEGGINGS,
				leggings,
				ArmorItem.Type.BOOTS,
				boots
			),
			enchantment,
			sound,
			() -> repair(repairItem),
			texture.isBlank()
				? List.of()
				: List.of(new ArmorMaterial.Layer(PasterDream.id(texture))),
			toughness,
			knockbackResistance
		);
	}

	private static Ingredient repair(String itemId) {
		if (itemId.isBlank()) {
			return Ingredient.EMPTY;
		}
		net.minecraft.resources.ResourceLocation id = itemId.contains(":")
			? net.minecraft.resources.ResourceLocation.parse(itemId)
			: PasterDream.id(itemId);
		return BuiltInRegistries.ITEM.getOptional(id)
			.map(Ingredient::of)
			.orElse(Ingredient.EMPTY);
	}
}
