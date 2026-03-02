package com.example.examplemod.IFE;

import net.minecraft.nbt.NBTTagCompound;

public class IEStorage implements IIEStorage {
    private int energy;
    private int capacity;
    private int maxVoltage;    //voltage1
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public IEStorage(int capacity) {
        this.capacity = capacity;
        this.maxVoltage = maxVoltage;    //voltage2
    }
    //voltage3
    public int receiveEnergy(int amount, int voltage, boolean simulate) {
        if (voltage > this.maxVoltage) {
            return 0;
        }
        int energyReceived = Math.min(capacity - energy, amount);
        if (!simulate) energy += energyReceived;
        return energyReceived;
    }
    @Override
    public int getMaxVoltage() { return maxVoltage; }
//other
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(capacity - energy, maxReceive);
        if (!simulate) energy += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(energy, maxExtract);
        if (!simulate) energy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() { return energy; }

    @Override
    public int getMaxEnergyStored() { return capacity; }

    @Override
    public boolean canExtract() { return true; }

    @Override
    public boolean canReceive() { return true; }

    public void readFromNBT(NBTTagCompound nbt) { this.energy = nbt.getInteger("IEEnergy"); }
    public void writeToNBT(NBTTagCompound nbt) { nbt.setInteger("IEEnergy", this.energy); }
}