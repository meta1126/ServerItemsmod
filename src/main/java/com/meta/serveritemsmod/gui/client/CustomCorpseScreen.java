package com.meta.serveritemsmod.gui.client;

import com.meta.serveritemsmod.gui.CustomCorpseMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;

public class CustomCorpseScreen extends AbstractContainerScreen<CustomCorpseMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");

    public CustomCorpseScreen(CustomCorpseMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 250;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // **タイトルの位置を変更 (X: 8, Y: 6)**
        guiGraphics.drawString(this.font, this.title, 8, 6, 0x404040, false);

        // **プレイヤーのインベントリラベルの位置を変更 (X: 8, Y: 130)**
        guiGraphics.drawString(this.font, this.playerInventoryTitle, 8, 130, 0x404040, false);
    }
}

