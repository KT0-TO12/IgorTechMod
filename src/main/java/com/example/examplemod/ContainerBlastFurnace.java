package com.example.examplemod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBlastFurnace extends Container {
    private final TileEntityBlastFurnace tileEntity;

    public ContainerBlastFurnace(InventoryPlayer playerInv, TileEntityBlastFurnace tile) {
        this.tileEntity = tile;

        this.addSlotToContainer(new Slot(tile, 0, 56, 17)); // Вход (Железо)
        this.addSlotToContainer(new Slot(tile, 1, 56, 53)); // Топливо (Уголь)
        this.addSlotToContainer(new Slot(tile, 2, 116, 35)); // Выход (Сталь)

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileEntity.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 3) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) return ItemStack.EMPTY;
            } else if (!this.mergeItemStack(itemstack1, 0, 3, false)) { // Из инвентаря в печь
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
        }
        return itemstack;
    }
}