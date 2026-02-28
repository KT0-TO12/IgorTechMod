package com.example.examplemod;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiTitaniumFurnace extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("examplemod:textures/gui/titanium_furnace.png");
    private final TileEntityTitaniumFurnace te;

    public GuiTitaniumFurnace(InventoryPlayer playerInv, TileEntityTitaniumFurnace te) {
        super(new ContainerTitaniumFurnace(playerInv, te));
        this.te = te;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        if (te.isBurning()) {
            int k = getBurnLeftScaled(13);
            this.drawTexturedModalRect(x + 58, y + 37 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = getCookProgressScaled(24);
        this.drawTexturedModalRect(x + 90, y + 35, 176, 14, l + 1, 16);
    }

    private int getCookProgressScaled(int pixels) {
        int i = te.getField(2); // cookTime
        int j = te.getField(3); // totalCookTime
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeftScaled(int pixels) {
        int i = te.getField(1); // currentBurnTime
        if (i == 0) i = 200;
        return te.getField(0) * pixels / i;
    }
}