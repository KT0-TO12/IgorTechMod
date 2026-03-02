package com.example.examplemod.machines.EnergyStorage;

import com.example.examplemod.IFE.IEStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityEnergyStorage extends TileEntity implements ITickable, IInventory {
    private final IEStorage storage = new IEStorage(100000);
    private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    @Override
    public void update() {
        if (!world.isRemote) {
            // Здесь будет логика зарядки/разрядки батарейки из слота 0
        }
    }

    @Override
    public int getSizeInventory() { return 1; }

    @Override
    public boolean isEmpty() { return inventory.get(0).isEmpty(); }

    @Override
    public ItemStack getStackInSlot(int index) { return inventory.get(index); }

    @Override
    public ItemStack decrStackSize(int index, int count) { return ItemStackHelper.getAndSplit(inventory, index, count); }

    @Override
    public ItemStack removeStackFromSlot(int index) { return ItemStackHelper.getAndRemove(inventory, index); }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.set(index, stack);
        if (stack.getCount() > getInventoryStackLimit()) stack.setCount(getInventoryStackLimit());
        markDirty();
    }

    @Override
    public int getInventoryStackLimit() { return 64; }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) { return true; }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) { return true; }

    @Override
    public int getField(int id) {
        if (id == 0) return storage.getEnergyStored();
        if (id == 1) return storage.getMaxEnergyStored();
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        if (id == 0) storage.setEnergy(value);
    }

    @Override
    public int getFieldCount() { return 2; }

    @Override
    public void clear() { inventory.clear(); }

    @Override
    public String getName() { return "Energy Storage"; }

    @Override
    public boolean hasCustomName() { return false; }

    @Override
    public ITextComponent getDisplayName() { return new TextComponentString(getName()); }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        ItemStackHelper.loadAllItems(compound, inventory);
        storage.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, inventory);
        storage.writeToNBT(compound);
        return compound;
    }
}