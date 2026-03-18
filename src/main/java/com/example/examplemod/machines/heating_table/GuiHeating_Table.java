package com.example.examplemod.machines.heating_table;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
public class GuiHeating_Table extends GuiContainer {
    private final TileEntityHeatingTable tileEntity;

    public GuiHeating_Table(InventoryPlayer playerInv, TileEntityHeatingTable tileEntity) {
        super(new ContainerHeatingTable(playerInv, tileEntity));
        this.tileEntity = tileEntity;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 0x404040);

        int energy = this.tileEntity.getField(1);
        int maxEnergy = this.tileEntity.getField(2);
        int energyPercent = (maxEnergy > 0) ? (energy * 100 / maxEnergy) : 0;
        this.fontRenderer.drawString(energyPercent + "%", 10, 10, 0x00AAFF);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        drawRect(i, j, i + xSize, j + ySize, 0xFF2D2D2D);
        drawRect(i + 5, j + 80, i + xSize - 5, j + 82, 0xFF121212);
        drawSlotBack(i + 35, j + 18);  //0:1
        drawSlotBack(i + 65, j + 18);  //1:2
        drawSlotBack(i + 125, j + 35); //2:Выход
        drawSlotBack(i + 50, j + 55);  //3:топливо
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                drawSlotBack(i + 8 + col * 18, j + 84 + row * 18);
            }
        }
        for (int col = 0; col < 9; col++) {
            drawSlotBack(i + 8 + col * 18, j + 142);
        }
        int energy = this.tileEntity.getField(1);
        int maxEnergy = this.tileEntity.getField(2);
        int energyBarHeight = (maxEnergy > 0) ? (int)((double)energy / maxEnergy * 45) : 0;
        drawRect(i + 12, j + 20, i + 20, j + 65, 0xFF121212);
        if (energy > 0) {
            drawRect(i + 13, j + 64 - energyBarHeight, i + 19, j + 64, 0xFF0055FF);
        }
        int cookTime = this.tileEntity.getField(0);
        int totalTime = 100;
        if (cookTime > 0) {
            int progressWidth = (int) ((double) cookTime / totalTime * 24);
            drawRect(i + 95, j + 41, i + 120, j + 45, 0xFF121212);
            drawRect(i + 95, j + 41, i + 95 + progressWidth, j + 45, 0xFFFF0044);
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