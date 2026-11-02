package dev.arakiel.pasterdream.item;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.client.PasterDreamGeoArmorRenderer;
import dev.arakiel.pasterdream.gameplay.PasterDreamAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import java.util.function.Consumer;

public final class PasterDreamArmorItem extends ArmorItem implements GeoItem {
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
	private final boolean sanArmor;
	private final boolean geoArmor;
	private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);

	public PasterDreamArmorItem(
		Holder<ArmorMaterial> material,
		Type type,
		Properties properties,
		boolean sanArmor
	) {
		super(material, type, properties);
		this.sanArmor = sanArmor;
		this.geoArmor = material.unwrapKey()
			.map(key -> {
				String path = key.location().getPath();
				return path.equals("machine_wing")
					|| path.equals("forsakens_wing")
					|| path.equals("angel_wing");
			})
			.orElse(false);
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers() {
		ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers();
		if (!sanArmor) {
			return modifiers;
		}
		return modifiers.withModifierAdded(
			PasterDreamAttributes.holder(PasterDreamAttributes.SAN_VARIABILITY),
			new AttributeModifier(
				PasterDream.id("qym_armor/" + getType().getName()),
				-1.0D,
				AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
			),
			EquipmentSlotGroup.bySlot(getEquipmentSlot())
		);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(
			this,
			"idle",
			5,
			state -> state.setAndContinue(IDLE)
		));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return animationCache;
	}

	@Override
	@SuppressWarnings("removal")
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		if (!geoArmor) {
			return;
		}
		consumer.accept(new GeoRenderProvider() {
			private PasterDreamGeoArmorRenderer renderer;

			@Override
			public <T extends LivingEntity> net.minecraft.client.model.HumanoidModel<?> getGeoArmorRenderer(
				T livingEntity,
				ItemStack itemStack,
				net.minecraft.world.entity.EquipmentSlot equipmentSlot,
				net.minecraft.client.model.HumanoidModel<T> original
			) {
				if (renderer == null) {
					renderer = new PasterDreamGeoArmorRenderer();
				}
				renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
				return renderer;
			}
		});
	}
}
