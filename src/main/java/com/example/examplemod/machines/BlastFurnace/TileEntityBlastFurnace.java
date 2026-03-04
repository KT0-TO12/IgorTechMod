package com.example.examplemod.machines.BlastFurnace;

import com.example.examplemod.main.ExampleMod;
import com.example.examplemod.main.ModBlocks;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import com.example.examplemod.main.EcompItems;

import javax.annotation.Nullable;

public class TileEntityBlastFurnace extends TileEntity implements ITickable, IInventory {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    private final IEStorage energyStorage = new IEStorage(100);
    public int cookTime;
    public int totalCookTime = 200;
    private int batteryTimer = 0;

    @Override
    public void update() {
        if (this.world.isRemote) return;

        ItemStack fuelStack = inventory.get(5);
        if (!fuelStack.isEmpty() && fuelStack.getItem() == ExampleMod.INFINITE_BATTERY) {
            if (energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
                batteryTimer++;
                if (batteryTimer >= 20) {
                    energyStorage.receiveEnergy(10, false);
                    batteryTimer = 0;
                }
            }
        } else {
            batteryTimer = 0;
            if (!fuelStack.isEmpty()) {
                if (fuelStack.getItem() == Items.COAL && energyStorage.getEnergyStored() <= 98) {
                    energyStorage.receiveEnergy(2, false);
                    fuelStack.shrink(1);
                    this.markDirty();
                } else if (fuelStack.getItem() == Item.getItemFromBlock(Blocks.COAL_BLOCK) && energyStorage.getEnergyStored() <= 80) {
                    energyStorage.receiveEnergy(20, false);
                    fuelStack.shrink(1);
                    this.markDirty();
                }
            }
        }

        if (energyStorage.getEnergyStored() >= 1 && canSmelt()) {
            cookTime++;
            if (cookTime >= totalCookTime) {
                smeltItem();
                if (fuelStack.isEmpty() || fuelStack.getItem() != ExampleMod.INFINITE_BATTERY) {
                    energyStorage.extractEnergy(1, false);
                }
                cookTime = 0;
                this.markDirty();
            }
        } else {
            cookTime = 0;
        }
    }

    private boolean canSmelt() {
        ItemStack input = inventory.get(0);
        ItemStack coalReagent = inventory.get(2);
        ItemStack output = inventory.get(3);

        if (input.isEmpty() || coalReagent.isEmpty() || coalReagent.getItem() != Items.COAL) return false;

        ItemStack result = getResult(input);
        if (result.isEmpty()) return false;

        if (output.isEmpty()) return true;
        if (!output.isItemEqual(result)) return false;
        return (output.getCount() + result.getCount() <= getInventoryStackLimit());
    }

    private void smeltItem() {
        ItemStack input = inventory.get(0);
        ItemStack coalReagent = inventory.get(2);
        ItemStack result = getResult(input);

        if (!result.isEmpty()) {
            input.shrink(1);
            coalReagent.shrink(1);
            if (inventory.get(3).isEmpty()) inventory.set(3, result.copy());
            else inventory.get(3).grow(result.getCount());
        }
    }

    private ItemStack getResult(ItemStack input) {
        if (input.getItem() == Items.IRON_INGOT) {
            return new ItemStack(EcompItems.STEEL_INGOT);
        }
        if (Block.getBlockFromItem(input.getItem()) == ModBlocks.BAKHMUTIUM_ORE) {
            return new ItemStack(EcompItems.SILICON_PURE);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == ExampleMod.IE_ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == ExampleMod.IE_ENERGY) return (T) this.energyStorage;
        return super.getCapability(capability, facing);
    }

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

    @Override public int getField(int id) { if (id == 0) return cookTime; if (id == 1) return totalCookTime; if (id == 2) return energyStorage.getEnergyStored(); return 0; }
    @Override public void setField(int id, int value) { if (id == 0) this.cookTime = value; else if (id == 1) this.totalCookTime = value; else if (id == 2) this.energyStorage.setEnergy(value); }
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
    @Override public ITextComponent getDisplayName() { return new TextComponentTranslation(this.getName()); }
    @Override public void openInventory(EntityPlayer p) {}
    @Override public void closeInventory(EntityPlayer p) {}
    @Override public boolean isItemValidForSlot(int i, ItemStack stack) { if (i == 3) return false; return true; }
    @Override public SPacketUpdateTileEntity getUpdatePacket() { return new SPacketUpdateTileEntity(pos, 1, getUpdateTag()); }
    @Override public NBTTagCompound getUpdateTag() { return writeToNBT(new NBTTagCompound()); }
}
