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
    // 0: Вход, 1: Топливо, 2: Выход
    private NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    public int cookTime;
    public int totalCookTime = 200;

    @Override
    public void update() {
        ItemStack input = inventory.get(0);
        ItemStack fuel = inventory.get(1);

        if (canSmelt()) {
            cookTime++;
            if (cookTime >= totalCookTime) {
                smeltItem();
                cookTime = 0;
            }
        } else {
            cookTime = 0;
        }
    }

    private boolean canSmelt() {
        if (inventory.get(0).isEmpty() || inventory.get(1).isEmpty()) return false;
        if (inventory.get(0).getItem() != Items.IRON_INGOT || inventory.get(1).getItem() != Items.COAL) return false;

        ItemStack result = new ItemStack(ExampleMod.STEEL_INGOT);
        ItemStack output = inventory.get(2);

        if (output.isEmpty()) return true;
        if (!output.isItemEqual(result)) return false;
        return output.getCount() + result.getCount() <= getInventoryStackLimit() && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void smeltItem() {
        ItemStack result = new ItemStack(ExampleMod.STEEL_INGOT);
        if (inventory.get(2).isEmpty()) inventory.set(2, result.copy());
        else inventory.get(2).grow(result.getCount());

        inventory.get(0).shrink(1);
        inventory.get(1).shrink(1);
    }

    // Методы IInventory
    @Override public int getSizeInventory() { return 3; }
    @Override public boolean isEmpty() { for(ItemStack s : inventory) if(!s.isEmpty()) return false; return true; }
    @Override public ItemStack getStackInSlot(int i) { return inventory.get(i); }
    @Override public ItemStack decrStackSize(int i, int count) { return ItemStackHelper.getAndSplit(inventory, i, count); }
    @Override public ItemStack removeStackFromSlot(int i) { return ItemStackHelper.getAndRemove(inventory, i); }
    @Override public void setInventorySlotContents(int i, ItemStack s) { inventory.set(i, s); }
    @Override public int getInventoryStackLimit() { return 64; }
    @Override public boolean isUsableByPlayer(EntityPlayer p) { return true; }
    @Override public void openInventory(EntityPlayer p) {}
    @Override public void closeInventory(EntityPlayer p) {}
    @Override public boolean isItemValidForSlot(int i, ItemStack s) { return true; }
    @Override public int getField(int id) { return id == 0 ? cookTime : totalCookTime; }
    @Override public void setField(int id, int value) { if(id == 0) cookTime = value; else totalCookTime = value; }
    @Override public int getFieldCount() { return 2; }
    @Override public void clear() { inventory.clear(); }
    @Override public String getName() { return "container.blast_furnace"; }
    @Override public boolean hasCustomName() { return false; }
}