package com.example.examplemod.EnergyBlocks;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiTransformer extends GuiContainer {
    private final TileEntityTransformer tileEntity;

    public GuiTransformer(InventoryPlayer playerInv, TileEntityTransformer te) {
        super(new ContainerTransformer(playerInv, te));
        this.tileEntity = te;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, guiLeft + 40, guiTop + 40, 20, 20, "-"));
        this.buttonList.add(new GuiButton(2, guiLeft + 116, guiTop + 40, 20, 20, "+"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, button.id);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = "Transformer";
        this.fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        String volt = "Output: " + tileEntity.getField(2) + " FE/t";
        this.fontRenderer.drawString(volt, xSize / 2 - fontRenderer.getStringWidth(volt) / 2, 45, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.disableTexture2D();
        drawRect(guiLeft, guiTop, guiLeft + xSize, guiTop + ySize, 0xFFC6C6C6);
        GlStateManager.enableTexture2D();
    }
}