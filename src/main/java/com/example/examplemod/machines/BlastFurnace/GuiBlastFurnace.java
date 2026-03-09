package com.example.examplemod.machines.BlastFurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.client.resources.I18n;

public class GuiBlastFurnace extends GuiContainer {
    private final TileEntityBlastFurnace tileEntity;

    public GuiBlastFurnace(InventoryPlayer playerInv, TileEntityBlastFurnace tileEntity) {
        super(new ContainerBlastFurnace(playerInv, tileEntity));
        this.tileEntity = tileEntity;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(" Input 1", 38, 7, 0xBABABA);
        this.fontRenderer.drawString("Input 2", 43, 43, 0xBABABA);
        this.fontRenderer.drawString("fuel", 70, 24, 0xBABABA);
        this.fontRenderer.drawString("Output", 120, 24, 0xBABABA);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 0x404040);

        int energy = this.tileEntity.getField(2);
        this.fontRenderer.drawString(energy + "%", 10, 10, 0xFFAA00);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        drawRect(i, j, i + xSize, j + ySize, 0xFF2D2D2D);
        drawRect(i + 5, j + 80, i + xSize - 5, j + 82, 0xFF121212);

        drawSlotBack(i + 44, j + 17);
        drawSlotBack(i + 44, j + 53);
        drawSlotBack(i + 71, j + 35);
        drawSlotBack(i + 126, j + 35);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                drawSlotBack(i + 8 + col * 18, j + 84 + row * 18);
            }
        }
        for (int col = 0; col < 9; col++) {
            drawSlotBack(i + 8 + col * 18, j + 142);
        }

        int energy = this.tileEntity.getField(2);
        int energyBarHeight = (int) (energy * 0.45);
        drawRect(i + 12, j + 20, i + 20, j + 65, 0xFF121212);
        if (energy > 0) {
            drawRect(i + 13, j + 64 - energyBarHeight, i + 19, j + 64, 0xFFFF8C00);
        }

        int cookTime = this.tileEntity.getField(0);
        int totalTime = this.tileEntity.getField(1);
        if (cookTime > 0) {
            int progressWidth = (int) ((double) cookTime / totalTime * 24);
            drawRect(i + 95, j + 41, i + 120, j + 45, 0xFF121212);
            drawRect(i + 95, j + 41, i + 95 + progressWidth, j + 45, 0xFF00FF00);
        }
    }

    private void drawSlotBack(int x, int y) {
        drawRect(x - 1, y - 1, x + 17, y + 17, 0xFF121212);
        drawRect(x, y, x + 16, y + 16, 0xFF8B8B8B);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}