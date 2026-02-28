package com.example.examplemod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;

public class ContainerTitaniumFurnace extends Container {
    private final TileEntityTitaniumFurnace te;

    public ContainerTitaniumFurnace(InventoryPlayer playerInv, TileEntityTitaniumFurnace te) {
        this.te = te;

        //вход 1
        this.addSlotToContainer(new Slot(te,  0, 26, 53));
        //вход 2
        this.addSlotToContainer(new Slot(te, 1, 80, 17));
        //уголь
        this.addSlotToContainer(new Slot(te, 2, 80, 53));
        //выход
        this.addSlotToContainer(new SlotFurnaceOutput(playerInv.player, te, 3, 140, 35));
        // Инвентарь
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        for (int k = 0; k < 9; ++k)
            this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) { return te.isUsableByPlayer(playerIn); }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

        return ItemStack.EMPTY;
    }
}