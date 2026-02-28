package com.example.examplemod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityTitaniumFurnace extends TileEntityLockable implements ITickable, IInventory {
    public NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    public int burnTime;
    public int currentBurnTime;
    public int cookTime;
    public int totalCookTime = 200;

    @Override
    public void update() {
        // Логика плавки (здесь должен быть твой код обработки предметов)
        if (this.burnTime > 0) --this.burnTime;
    }

    public boolean isBurning() { return this.burnTime > 0; }

    // Взаимодействие с Container и GUI через поля
    @Override
    public int getField(int id) {
        switch (id) {
            case 0: return this.burnTime;
            case 1: return this.currentBurnTime;
            case 2: return this.cookTime;
            case 3: return this.totalCookTime;
            default: return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0: this.burnTime = value; break;
            case 1: this.currentBurnTime = value; break;
            case 2: this.cookTime = value; break;
            case 3: this.totalCookTime = value; break;
        }
    }

    @Override
    public int getFieldCount() { return 4; }

    // Методы IInventory
    @Override public int getSizeInventory() { return 4; }
    @Override public boolean isEmpty() { for(ItemStack s : inventory) if(!s.isEmpty()) return false; return true; }
    @Override public ItemStack getStackInSlot(int index) { return inventory.get(index); }
    @Override public ItemStack decrStackSize(int index, int count) { return ItemStackHelper.getAndSplit(inventory, index, count); }
    @Override public ItemStack removeStackFromSlot(int index) { return ItemStackHelper.getAndRemove(inventory, index); }
    @Override public void setInventorySlotContents(int index, ItemStack stack) { inventory.set(index, stack); }
    @Override public int getInventoryStackLimit() { return 64; }
    @Override public boolean isUsableByPlayer(EntityPlayer player) { return true; }
    @Override public void openInventory(EntityPlayer player) {}
    @Override public void closeInventory(EntityPlayer player) {}
    @Override public boolean isItemValidForSlot(int index, ItemStack stack) { return true; }
    @Override public void clear() { inventory.clear(); }
    @Override public String getName() { return "container.titanium_furnace"; }
    @Override public boolean hasCustomName() { return false; }
    @Override public ITextComponent getDisplayName() { return new TextComponentString(getName()); }
    @Override public String getGuiID() { return "examplemod:titanium_furnace"; }
    @Override public net.minecraft.inventory.Container createContainer(net.minecraft.entity.player.InventoryPlayer playerInventory, EntityPlayer playerIn) { return new ContainerTitaniumFurnace(playerInventory, this); }
}