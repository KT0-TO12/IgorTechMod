package com.example.examplemod.machines.vacuum_deposition_unit;

import com.example.examplemod.main.EcompItems;
import com.example.examplemod.main.ExampleMod;
import com.example.examplemod.IFE.IEStorage;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityVacuumDepositionUnit extends TileEntity implements ITickable, IInventory {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    private final IEStorage energy = new IEStorage(50000);
    public int cookTime;
    public final int totalTime = 100;

    @Override
    public void update() {
        if (world.isRemote) return;

        ItemStack fuelStack = inventory.get(3);
        if (!fuelStack.isEmpty() && fuelStack.getItem() == Item.getItemFromBlock(Blocks.COAL_BLOCK)) {
            if (energy.getEnergyStored() <= energy.getMaxEnergyStored() - 2000) {
                energy.receiveEnergy(2000, false);
                fuelStack.shrink(1);
                this.markDirty();
            }
        }
        ItemStack silicon = inventory.get(0);
        ItemStack alum = inventory.get(1);

        if (canProcess()) {
            if (energy.getEnergyStored() >= 40) {
                energy.extractEnergy(40, false);
                cookTime++;

                if (cookTime >= totalTime) {
                    process();
                    cookTime = 0;
                }
                this.markDirty();
            }
        } else {
            if (cookTime > 0) {
                cookTime = 0;
                this.markDirty();
            }
        }
    }

    private boolean canProcess() {
        ItemStack inputSilicon = this.inventory.get(0);
        ItemStack inputAlum = this.inventory.get(1);

        if (inputSilicon.isEmpty() || inputSilicon.getItem() != EcompItems.SILICON_PURE) return false;
        if (inputAlum.isEmpty() || inputAlum.getItem() != EcompItems.ALUMINIUM_INGOT) return false;

        ItemStack outputStack = this.inventory.get(2);
        if (outputStack.isEmpty()) return true;

        if (outputStack.getItem() != EcompItems.SILICON_PLATE_ECOMP) return false;

        int resultCount = outputStack.getCount() + 1;
        return resultCount <= getInventoryStackLimit() && resultCount <= outputStack.getMaxStackSize();
    }

    private void process() {
        if (this.canProcess()) {
            ItemStack inputSilicon = this.inventory.get(0);
            ItemStack inputAlum = this.inventory.get(1);
            ItemStack resultStack = this.inventory.get(2);
            ItemStack recipeOutput = new ItemStack(EcompItems.SILICON_PLATE_ECOMP);

            inputSilicon.shrink(1);
            inputAlum.shrink(1);

            if (resultStack.isEmpty()) {
                this.inventory.set(2, recipeOutput.copy());
            } else if (resultStack.getItem() == recipeOutput.getItem()) {
                resultStack.grow(1);
            }

            this.markDirty();
        }
    }

    @Override public int getField(int id) {
        switch(id) {
            case 0: return cookTime;
            case 1: return energy.getEnergyStored();
            case 2: return energy.getMaxEnergyStored();
            default: return 0;
        }
    }
    @Override public void setField(int id, int value) {
        if (id == 0) cookTime = value;
        if (id == 1) energy.setEnergy(value);
    }
    @Override public int getFieldCount() { return 3; }

    //IInventory
    @Override public int getSizeInventory() { return 4; }
    @Override public boolean isEmpty() { for(ItemStack s : inventory) if(!s.isEmpty()) return false; return true; }
    @Override public ItemStack getStackInSlot(int i) { return inventory.get(i); }
    @Override public ItemStack decrStackSize(int i, int c) { return ItemStackHelper.getAndSplit(inventory, i, c); }
    @Override public ItemStack removeStackFromSlot(int i) { return ItemStackHelper.getAndRemove(inventory, i); }
    @Override public void setInventorySlotContents(int i, ItemStack s) { inventory.set(i, s); this.markDirty(); }
    @Override public int getInventoryStackLimit() { return 64; }
    @Override public boolean isUsableByPlayer(net.minecraft.entity.player.EntityPlayer p) { return true; }
    @Override public void openInventory(net.minecraft.entity.player.EntityPlayer p) {}
    @Override public void closeInventory(net.minecraft.entity.player.EntityPlayer p) {}
    @Override public boolean isItemValidForSlot(int i, ItemStack s) {
        if (i == 3) return s.getItem() == Item.getItemFromBlock(Blocks.COAL_BLOCK);
        return true;
    }
    @Override public void clear() { inventory.clear(); }
    @Override public String getName() { return "container.vacuum_deposition_unit"; }
    @Override public boolean hasCustomName() { return false; }
    @Override public ITextComponent getDisplayName() { return new TextComponentString("Vacuum Deposition Unit"); }
    @Override public void readFromNBT(NBTTagCompound c) { super.readFromNBT(c); ItemStackHelper.loadAllItems(c, inventory); energy.readFromNBT(c); cookTime = c.getInteger("CookTime"); }
    @Override public NBTTagCompound writeToNBT(NBTTagCompound c) { super.writeToNBT(c); ItemStackHelper.saveAllItems(c, inventory); energy.writeToNBT(c); c.setInteger("CookTime", cookTime); return c; }
}