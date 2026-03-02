package com.example.examplemod;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;

public class TileEntityBlastFurnace extends TileEntity implements ITickable, IInventory {
    // 0:вход 1, 1: Топливо, 2:вход 2, 3: Выход
    private NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

    public int cookTime;
    public int totalCookTime = 200;
    public int energy = 0;

    @Override
    public void update() {
        if (this.world.isRemote) return;


        ItemStack fuelForEnergy = inventory.get(1);
        if (!fuelForEnergy.isEmpty() && energy <= 98) {

            if (fuelForEnergy.getItem() == Items.COAL) {
                energy += 2;
                fuelForEnergy.shrink(1);
                this.markDirty();
            }
        }


        if (energy >= 1 && canSmelt()) {
            cookTime++;
            if (cookTime >= totalCookTime) {
                smeltItem();
                energy -= 1;
                cookTime = 0;
                this.markDirty();
            }
        } else {
            cookTime = 0;
        }
    }

    private boolean canSmelt() {
        ItemStack iron = inventory.get(0);
        ItemStack coalReagent = inventory.get(2);
        ItemStack output = inventory.get(3);

        if (iron.isEmpty() || iron.getItem() != Items.IRON_INGOT) return false;
        if (coalReagent.isEmpty() || coalReagent.getItem() != Items.COAL) return false;

        ItemStack result = new ItemStack(ExampleMod.STEEL_INGOT);

        if (output.isEmpty()) return true;
        if (!output.isItemEqual(result)) return false;

        return (output.getCount() + result.getCount() <= getInventoryStackLimit());
    }

    private void smeltItem() {
        ItemStack result = new ItemStack(ExampleMod.STEEL_INGOT);


        inventory.get(0).shrink(1);
        inventory.get(2).shrink(1);


        if (inventory.get(3).isEmpty()) {
            inventory.set(3, result.copy());
        } else {
            inventory.get(3).grow(1);
        }
    }

    //МЕТОДЫ IINVENTORY

    @Override public int getSizeInventory() { return 4; }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        this.cookTime = compound.getInteger("CookTime");
        this.energy = compound.getInteger("EnergyPercentage");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("CookTime", this.cookTime);
        compound.setInteger("EnergyPercentage", this.energy);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        return compound;
    }

    @Override public ItemStack getStackInSlot(int i) { return inventory.get(i); }
    @Override public void setInventorySlotContents(int i, ItemStack s) { inventory.set(i, s); this.markDirty(); }
    @Override public int getInventoryStackLimit() { return 64; }
    @Override public boolean isUsableByPlayer(EntityPlayer p) { return true; }
    @Override public boolean isEmpty() { for(ItemStack s : inventory) if(!s.isEmpty()) return false; return true; }
    @Override public ItemStack decrStackSize(int i, int count) { return ItemStackHelper.getAndSplit(inventory, i, count); }
    @Override public ItemStack removeStackFromSlot(int i) { return ItemStackHelper.getAndRemove(inventory, i); }

    @Override public int getField(int id) {
        if (id == 0) return cookTime;
        if (id == 1) return totalCookTime;
        if (id == 2) return energy;
        return 0;
    }
    @Override public void setField(int id, int value) {
        if (id == 0) cookTime = value;
        else if (id == 1) totalCookTime = value;
        else if (id == 2) energy = value;
    }
    @Override public int getFieldCount() { return 3; }
    @Override public void clear() { inventory.clear(); }
    @Override public String getName() { return "container.blast_furnace"; }
    @Override public boolean hasCustomName() { return false; }
    @Override public void openInventory(EntityPlayer p) {}
    @Override public void closeInventory(EntityPlayer p) {}
    @Override public boolean isItemValidForSlot(int i, ItemStack s) { return i != 3; }
}