package com.example.examplemod.machines.vacuum_deposition_unit;

import com.example.examplemod.machines.vacuum_deposition_unit.TileEntityVacuumDepositionUnit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerVacuumDepositionUnit extends Container {
    private final TileEntityVacuumDepositionUnit te;
    private int cookTime, energy;

    public ContainerVacuumDepositionUnit(InventoryPlayer playerInv, TileEntityVacuumDepositionUnit te) {
        this.te = te;

        // Слоты механизма
        this.addSlotToContainer(new Slot(te, 0, 35, 26));  // Кремний
        this.addSlotToContainer(new Slot(te, 1, 65, 26));  // Алюминий
        this.addSlotToContainer(new Slot(te, 2, 125, 41)); // Выход
        this.addSlotToContainer(new Slot(te, 3, 50, 61) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() == Item.getItemFromBlock(Blocks.COAL_BLOCK);
            }
        });
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
        for (int k = 0; k < 9; ++k)
            this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : this.listeners) {
            if (this.cookTime != this.te.getField(0)) listener.sendWindowProperty(this, 0, this.te.getField(0));
            if (this.energy != this.te.getField(1)) listener.sendWindowProperty(this, 1, this.te.getField(1));
        }
        this.cookTime = this.te.getField(0);
        this.energy = this.te.getField(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        this.te.setField(id, data);
    }

    @Override public boolean canInteractWith(EntityPlayer p) { return true; }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < 4) {
                if (!this.mergeItemStack(itemstack1, 4, 40, true)) return ItemStack.EMPTY;
            } else if (!this.mergeItemStack(itemstack1, 0, 2, false)) return ItemStack.EMPTY;

            if (itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
        }
        return itemstack;
    }
}