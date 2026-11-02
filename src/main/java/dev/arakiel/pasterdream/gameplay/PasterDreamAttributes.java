package dev.arakiel.pasterdream.gameplay;

import dev.arakiel.gemini.registration.AutoRegistryContainer;
import dev.arakiel.gemini.registration.GeminiRegistration;
import dev.arakiel.gemini.registration.annotations.AssignedName;
import dev.arakiel.gemini.registration.annotations.RegistryNamespace;
import dev.arakiel.pasterdream.PasterDream;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

@RegistryNamespace(PasterDream.MOD_ID)
public final class PasterDreamAttributes implements AutoRegistryContainer<Attribute> {
	@AssignedName("skill_multiplier")
	public static final Attribute SKILL_MULTIPLIER = ranged("skill_multiplier", 1.0D, 0.0D, 10.0D);
	@AssignedName("skill_cooldown")
	public static final Attribute SKILL_COOLDOWN = ranged("skill_cooldown", 1.0D, 0.0D, 2.0D);
	@AssignedName("teleportation_cooldown")
	public static final Attribute TELEPORTATION_COOLDOWN = ranged("teleportation_cooldown", 1.0D, 0.0D, 10.0D);
	@AssignedName("teleportation_cost")
	public static final Attribute TELEPORTATION_COST = ranged("teleportation_cost", 1.0D, 0.0D, 10.0D);
	@AssignedName("teleportation_range")
	public static final Attribute TELEPORTATION_RANGE = ranged("teleportation_range", 1.0D, 0.0D, 10.0D);
	@AssignedName("san_variability")
	public static final Attribute SAN_VARIABILITY = ranged("san_variability", 0.0D, -120000.0D, 120000.0D);
	@AssignedName("luck")
	public static final Attribute LUCK = ranged("luck", 0.0D, -100.0D, 1000.0D);
	@AssignedName("magic_cooldown")
	public static final Attribute MAGIC_COOLDOWN = ranged("magic_cooldown", 1.0D, 0.0D, 10.0D);
	@AssignedName("magic_power")
	public static final Attribute MAGIC_POWER = ranged("magic_power", 0.0D, -10.0D, 10.0D);
	@AssignedName("total_healing")
	public static final Attribute TOTAL_HEALING = ranged("total_healing", 1.0D, 0.0D, 10.0D);

	private PasterDreamAttributes() {
	}

	public static void register() {
		GeminiRegistration.register(PasterDreamAttributes.class, PasterDream.MOD_ID);
	}

	public static void addPlayerAttributes(AttributeSupplier.Builder builder) {
		builder.add(holder(SKILL_MULTIPLIER));
		builder.add(holder(SKILL_COOLDOWN));
		builder.add(holder(TELEPORTATION_COOLDOWN));
		builder.add(holder(TELEPORTATION_COST));
		builder.add(holder(TELEPORTATION_RANGE));
		builder.add(holder(SAN_VARIABILITY));
		builder.add(holder(LUCK));
		builder.add(holder(MAGIC_COOLDOWN));
		builder.add(holder(MAGIC_POWER));
		builder.add(holder(TOTAL_HEALING));
	}

	public static Attribute get(String id) {
		return BuiltInRegistries.ATTRIBUTE.getOptional(PasterDream.id(id))
			.orElseThrow(() -> new IllegalArgumentException("Unknown PasterDream attribute: " + id));
	}

	public static Holder<Attribute> holder(Attribute attribute) {
		return BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attribute);
	}

	@Override
	public Registry<Attribute> getRegistry() {
		return BuiltInRegistries.ATTRIBUTE;
	}

	@Override
	public Class<Attribute> getTargetFieldType() {
		return Attribute.class;
	}

	private static Attribute ranged(String id, double defaultValue, double minimum, double maximum) {
		return new RangedAttribute("attribute.pasterdream." + id, defaultValue, minimum, maximum)
			.setSyncable(true);
	}
}
