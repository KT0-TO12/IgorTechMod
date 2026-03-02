package com.example.examplemod.machines.EnergyStorage;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnergyStorage extends GuiContainer {

    private final TileEntityEnergyStorage tileEntity;

    public GuiEnergyStorage(InventoryPlayer playerInventory, TileEntityEnergyStorage tileEntity) {
        super(new ContainerEnergyStorage(playerInventory, tileEntity));
        this.tileEntity = tileEntity;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = "Energy Storage";
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 4210752);

        if (this.tileEntity != null) {
            String energyText = this.tileEntity.getField(0) + " / " + this.tileEntity.getField(1) + " FE";
            this.fontRenderer.drawString(energyText, 8, 20, 4210752);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int guiLeft = (this.width - this.xSize) / 2;
        int guiTop = (this.height - this.ySize) / 2;

        drawRect(guiLeft, guiTop, guiLeft + xSize, guiTop + ySize, 0xFFC6C6C6);

        int energyBarX = guiLeft + 63;
        int energyBarY = guiTop + 40;
        drawRect(energyBarX - 1, energyBarY - 1, energyBarX + 51, energyBarY + 11, 0xFF000000);
        drawRect(energyBarX, energyBarY, energyBarX + 50, energyBarY + 10, 0xFF333333);

        if (this.tileEntity != null) {
            int energyWidth = getEnergyScaled(50);
            if (energyWidth > 0) {
                drawRect(energyBarX, energyBarY, energyBarX + energyWidth, energyBarY + 10, 0xFF00FF00);
            }
        }

        drawSlot(guiLeft + 79, guiTop + 52);

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                drawSlot(guiLeft + 7 + col * 18, guiTop + 83 + row * 18);
            }
        }

        for (int col = 0; col < 9; ++col) {
            drawSlot(guiLeft + 7 + col * 18, guiTop + 141);
        }
    }

    private void drawSlot(int x, int y) {
        drawRect(x, y, x + 18, y + 18, 0xFF8B8B8B);
        drawRect(x + 1, y + 1, x + 17, y + 17, 0xFF373737);
        drawRect(x + 1, y + 1, x + 16, y + 16, 0xFF8B8B8B);
    }

    private int getEnergyScaled(int pixels) {
        if (this.tileEntity == null) return 0;
        int current = this.tileEntity.getField(0);
        int max = this.tileEntity.getField(1);
        return (max != 0 && current != 0) ? (current * pixels / max) : 0;
    }
}