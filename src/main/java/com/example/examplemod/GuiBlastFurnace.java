package com.example.examplemod;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBlastFurnace extends GuiContainer {
    private final TileEntityBlastFurnace tileEntity;

    public GuiBlastFurnace(InventoryPlayer playerInv, TileEntityBlastFurnace tile) {
        super(new ContainerBlastFurnace(playerInv, tile));
        this.tileEntity = tile;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        drawRect(x, y, x + xSize, y + ySize, 0xFF404040);


        drawRect(x, y, x + xSize, y + 2, 0xFF8B8B8B); // Верх
        drawRect(x, y + ySize - 2, x + xSize, y + ySize, 0xFF8B8B8B); // Низ
        drawRect(x, y, x + 2, y + ySize, 0xFF8B8B8B); // Лево
        drawRect(x + xSize - 2, y, x + xSize, y + ySize, 0xFF8B8B8B); // Право

        drawCustomSlot(x + 55, y + 16);
        drawCustomSlot(x + 55, y + 52);
        drawCustomSlot(x + 115, y + 34);


        int progress = (int)(this.tileEntity.getField(0) * 24 / this.tileEntity.getField(1));
        drawRect(x + 79, y + 34, x + 79 + 24, y + 34 + 17, 0xFF222222);
        drawRect(x + 79, y + 34, x + 79 + progress, y + 34 + 17, 0xFFE52E2E);
    }

    private void drawCustomSlot(int x, int y) {

        drawRect(x, y, x + 18, y + 18, 0xFF111111);
        drawRect(x + 1, y + 1, x + 17, y + 17, 0xFF8B8B8B); // Внутренность
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}