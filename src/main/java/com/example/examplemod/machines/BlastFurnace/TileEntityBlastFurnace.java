package com.example.examplemod.machines.BlastFurnace;

import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

public class TileEntityBlastFurnace extends TileEntity implements ITickable, IInventory {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    public int cookTime;
    public int totalCookTime = 200;
    public int energy = 0;
    private int batteryTimer = 0;

    @Override
    public void update() {
        if (this.world.isRemote) return;

        ItemStack fuelStack = inventory.get(1);

        if (!fuelStack.isEmpty() && fuelStack.getItem() == com.example.examplemod.main.ExampleMod.INFINITE_BATTERY) {
            if (this.energy < 100) {
                batteryTimer++;
                if (batteryTimer >= 20) {
                    this.energy = Math.min(100, this.energy + 10);
                    batteryTimer = 0;
                    this.markDirty();
                }
            }
        } else {
            batteryTimer = 0;
            if (!fuelStack.isEmpty()) {
                if (fuelStack.getItem() == Items.COAL && energy <= 98) {
                    energy += 2;
                    fuelStack.shrink(1);
                    this.markDirty();
                } else if (fuelStack.getItem() == Item.getItemFromBlock(Blocks.COAL_BLOCK) && energy <= 80) {
                    energy += 20;
                    fuelStack.shrink(1);
                    this.markDirty();
                }
            }
        }

        if (energy >= 1 && canSmelt()) {
            cookTime++;
            if (cookTime >= totalCookTime) {
                smeltItem();
                if (fuelStack.isEmpty() || fuelStack.getItem() != com.example.examplemod.main.ExampleMod.INFINITE_BATTERY) {
                    energy -= 1;
                }
                cookTime = 0;
                this.markDirty();
            }
        } else {
            cookTime = 0;
        }
    }

    private boolean canSmelt() {
        ItemStack iron = findInputStack(Items.IRON_INGOT);
        ItemStack coalReagent = findInputStack(Items.COAL);
        ItemStack output = inventory.get(3);
        if (iron.isEmpty() || coalReagent.isEmpty()) return false;
        ItemStack result = new ItemStack(com.example.examplemod.main.ExampleMod.STEEL_INGOT);
        if (output.isEmpty()) return true;
        if (!output.isItemEqual(result)) return false;
        return (output.getCount() + result.getCount() <= getInventoryStackLimit());
    }

    private void smeltItem() {
        ItemStack iron = findInputStack(Items.IRON_INGOT);
        ItemStack coalReagent = findInputStack(Items.COAL);
        ItemStack result = new ItemStack(com.example.examplemod.main.ExampleMod.STEEL_INGOT);
        if (!iron.isEmpty() && !coalReagent.isEmpty()) {
            iron.shrink(1);
            coalReagent.shrink(1);
            if (inventory.get(3).isEmpty()) inventory.set(3, result.copy());
            else inventory.get(3).grow(1);
        }
    }

    private ItemStack findInputStack(Item item) {
        if (inventory.get(0).getItem() == item) return inventory.get(0);
        if (inventory.get(2).getItem() == item) return inventory.get(2);
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 3) return false;
        if (index == 1) return stack.getItem() == Items.COAL || stack.getItem() == Item.getItemFromBlock(Blocks.COAL_BLOCK) || stack.getItem() == com.example.examplemod.main.ExampleMod.INFINITE_BATTERY;
        return stack.getItem() != Item.getItemFromBlock(Blocks.COAL_BLOCK) && stack.getItem() != com.example.examplemod.main.ExampleMod.INFINITE_BATTERY;
    }

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

    @Override public int getField(int id) { if (id == 0) return cookTime; if (id == 1) return totalCookTime; if (id == 2) return energy; return 0; }
    @Override public void setField(int id, int value) { if (id == 0) this.cookTime = value; else if (id == 1) this.totalCookTime = value; else if (id == 2) this.energy = value; }
    @Override public int getFieldCount() { return 3; }
    @Override public int getSizeInventory() { return 4; }
    @Override public boolean isEmpty() { for(ItemStack s : inventory) if(!s.isEmpty()) return false; return true; }
    @Override public ItemStack getStackInSlot(int i) { return inventory.get(i); }
    @Override public void setInventorySlotContents(int i, ItemStack s) { inventory.set(i, s); this.markDirty(); }
    @Override public int getInventoryStackLimit() { return 64; }
    @Override public boolean isUsableByPlayer(EntityPlayer p) { return this.world.getTileEntity(this.pos) == this && p.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D; }
    @Override public ItemStack decrStackSize(int i, int count) { return ItemStackHelper.getAndSplit(inventory, i, count); }
    @Override public ItemStack removeStackFromSlot(int i) { return ItemStackHelper.getAndRemove(inventory, i); }
    @Override public void clear() { inventory.clear(); }
    @Override public String getName() { return "container.blast_furnace"; }
    @Override public boolean hasCustomName() { return false; }
    @Override public void openInventory(EntityPlayer p) {}
    @Override public void closeInventory(EntityPlayer p) {}
    @Override public SPacketUpdateTileEntity getUpdatePacket() { return new SPacketUpdateTileEntity(pos, 1, getUpdateTag()); }
    @Override public NBTTagCompound getUpdateTag() { return writeToNBT(new NBTTagCompound()); }
}