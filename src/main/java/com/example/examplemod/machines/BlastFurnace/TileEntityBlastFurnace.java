package com.example.examplemod.machines.BlastFurnace;

import com.example.examplemod.main.ExampleMod;
import com.example.examplemod.main.ModBlocks;
import com.example.examplemod.main.EcompItems;
import com.example.examplemod.IFE.IEStorage;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileEntityBlastFurnace extends TileEntity implements ITickable, IInventory {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(5, ItemStack.EMPTY);
    private final IEStorage energyStorage = new IEStorage(100000);
    public int cookTime;
    public int totalCookTime = 200;
    private int batteryTimer = 0;

    @Override
    public void update() {
        if (this.world.isRemote) return;

        ItemStack fuelStack = inventory.get(4);
        if (!fuelStack.isEmpty() && fuelStack.getItem() == EcompItems.INFINITE_BATTERY) {
            if (energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
                batteryTimer++;
                if (batteryTimer >= 1) {
                    energyStorage.receiveEnergy(10, false);
                    batteryTimer = 0;
                    this.markDirty();
                }
            }
        } else if (!fuelStack.isEmpty()) {
            if (fuelStack.getItem() == Items.COAL && energyStorage.getEnergyStored() <= energyStorage.getMaxEnergyStored() - 100) {
                energyStorage.receiveEnergy(100, false);
                fuelStack.shrink(1);
                this.markDirty();
            }
        }

        // --- ЛОГИКА ПЛАВКИ ---
        ItemStack input1 = inventory.get(0);
        ItemStack input2 = inventory.get(1);

        BlastRecipes.BlastRecipe recipe = BlastRecipes.instance().getRecipe(input1, input2);

        if (recipe != null && canSmelt(recipe)) {
            if (energyStorage.getEnergyStored() >= recipe.energyPerTick) {
                energyStorage.extractEnergy(recipe.energyPerTick, false);
                cookTime++;

                if (cookTime >= totalCookTime) {
                    smeltItem(recipe);
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

    private boolean canSmelt(BlastRecipes.BlastRecipe recipe) {
        if (recipe == null) return false;

        ItemStack result = recipe.output;
        ItemStack outputSlot = inventory.get(3);

        if (outputSlot.isEmpty()) return true;
        if (!outputSlot.isItemEqual(result)) return false;

        int count = outputSlot.getCount() + result.getCount();
        return count <= getInventoryStackLimit() && count <= outputSlot.getMaxStackSize();
    }

    private void smeltItem(BlastRecipes.BlastRecipe recipe) {
        ItemStack result = recipe.output.copy();
        ItemStack outputSlot = inventory.get(3);

        if (outputSlot.isEmpty()) {
            inventory.set(3, result);
        } else {
            outputSlot.grow(result.getCount());
        }

        inventory.get(0).shrink(1);
        if (!recipe.input2.isEmpty()) {
            inventory.get(1).shrink(1);
        }
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0: return cookTime;
            case 1: return totalCookTime;
            case 2: return energyStorage.getEnergyStored();
            case 3: return energyStorage.getMaxEnergyStored();
            default: return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0: this.cookTime = value; break;
            case 1: this.totalCookTime = value; break;
            case 2: this.energyStorage.setEnergy(value); break;
        }
    }

    @Override
    public int getFieldCount() { return 4; }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        this.cookTime = compound.getInteger("CookTime");
        this.energyStorage.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("CookTime", this.cookTime);
        this.energyStorage.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        return compound;
    }

    @Override public int getSizeInventory() { return 5; }
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
    @Override public ITextComponent getDisplayName() { return new TextComponentTranslation(this.getName()); }
    @Override public void openInventory(EntityPlayer p) {}
    @Override public void closeInventory(EntityPlayer p) {}
    @Override public boolean isItemValidForSlot(int i, ItemStack stack) { return i != 3; }
    @Override public SPacketUpdateTileEntity getUpdatePacket() { return new SPacketUpdateTileEntity(pos, 1, getUpdateTag()); }
    @Override public NBTTagCompound getUpdateTag() { return writeToNBT(new NBTTagCompound()); }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == ExampleMod.IE_ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == ExampleMod.IE_ENERGY) return (T) energyStorage;
        return super.getCapability(capability, facing);
    }
}