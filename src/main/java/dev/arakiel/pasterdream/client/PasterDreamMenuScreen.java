package dev.arakiel.pasterdream.client;

import dev.arakiel.pasterdream.PasterDream;
import dev.arakiel.pasterdream.content.PasterDreamMenu;
import dev.arakiel.pasterdream.inventory.PasterDreamMenuSpecs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class PasterDreamMenuScreen extends AbstractContainerScreen<PasterDreamMenu> {
	private final GuiTexture texture;

	public PasterDreamMenuScreen(PasterDreamMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.texture = texture(PasterDreamMenuSpecs.get(menu.spec().id()));
		this.imageWidth = texture.width();
		this.imageHeight = texture.height();
		this.titleLabelX = 8;
		this.titleLabelY = 6;
		this.inventoryLabelX = menu.spec().playerX();
		this.inventoryLabelY = menu.spec().playerY() - 11;
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(
			texture.location(),
			leftPos,
			topPos,
			0,
			0,
			imageWidth,
			imageHeight,
			imageWidth,
			imageHeight
		);
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawString(font, title, titleLabelX, titleLabelY, 0x404040, false);
		if (menu.spec().playerInventory()) {
			graphics.drawString(
				font,
				playerInventoryTitle,
				inventoryLabelX,
				inventoryLabelY,
				0x404040,
				false
			);
		}
	}

	private static GuiTexture texture(PasterDreamMenuSpecs.MenuSpec spec) {
		return switch (spec.id()) {
			case PasterDreamMenuSpecs.BLUEPRINT ->
				new GuiTexture("blueprint_gui_0", 196, 216);
			case PasterDreamMenuSpecs.DREAM_ACCUMULATOR ->
				new GuiTexture("dream_accumulator_gui0", 177, 206);
			case PasterDreamMenuSpecs.DREAM_CAULDRON ->
				new GuiTexture("dream_cauldron_gui", 196, 196);
			case PasterDreamMenuSpecs.DREAMNOTES ->
				new GuiTexture("dreamnotes_test", 151, 189);
			case PasterDreamMenuSpecs.DYEDREAM_DESK ->
				new GuiTexture("dyedream_desk_gui_0", 176, 182);
			case PasterDreamMenuSpecs.MELTDREAM_CHEST ->
				new GuiTexture("meltdream_chest_0", 176, 182);
			case PasterDreamMenuSpecs.PICNIC_BASKET ->
				new GuiTexture("picnic_basket_gui", 176, 196);
			case PasterDreamMenuSpecs.RESEARCH_TABLE ->
				new GuiTexture("research_table_gui", 206, 206);
			case PasterDreamMenuSpecs.SHADOW_BLAST_FURNACE ->
				new GuiTexture("shadow_blast_furnace_gui", 236, 216);
			case PasterDreamMenuSpecs.SHADOW_CHEST ->
				new GuiTexture("shadow_chest_gui", 176, 166);
			case PasterDreamMenuSpecs.SHADOW_DESK ->
				new GuiTexture("shadow_desk_gui", 176, 182);
			case PasterDreamMenuSpecs.SHADOW_SELECT_END ->
				new GuiTexture("shadow_select_end_gui", 320, 200);
			case PasterDreamMenuSpecs.STORAGE_BAG ->
				new GuiTexture("storage_bag_gui_", 176, 166);
			case PasterDreamMenuSpecs.STORAGE_BAG_LARGE ->
				new GuiTexture("storage_bag_0_gui_", 176, 200);
			case PasterDreamMenuSpecs.ENDLESS_BOOK ->
				new GuiTexture("the_endless_book_of_dream_seekers_gui", 176, 166);
			case PasterDreamMenuSpecs.WEAPON_WORKSHOP ->
				new GuiTexture("weapon_workshop_gui", 186, 186);
			case PasterDreamMenuSpecs.WINDMOOR_CRATE ->
				new GuiTexture("windmoor_crate_gui", 176, 196);
			case PasterDreamMenuSpecs.WORKSHOP_ANVIL ->
				new GuiTexture("workshop_anvil_gui", 176, 216);
			case PasterDreamMenuSpecs.WORKSHOP_BLAST ->
				new GuiTexture("workshop_blast_gui", 176, 196);
			default -> throw new IllegalArgumentException("Missing GUI texture for " + spec.id());
		};
	}

	private record GuiTexture(ResourceLocation location, int width, int height) {
		private GuiTexture(String path, int width, int height) {
			this(PasterDream.id("textures/screens/" + path + ".png"), width, height);
		}
	}
}
