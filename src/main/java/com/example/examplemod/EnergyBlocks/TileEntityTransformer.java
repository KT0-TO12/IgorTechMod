package com.example.examplemod.EnergyBlocks;

import com.example.examplemod.IFE.IEStorage;
import com.example.examplemod.main.ExampleMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityTransformer extends TileEntity implements ITickable {
    private final IEStorage storage = new IEStorage(10000);
    private int outputLimit = 100;

    @Override
    public void update() {
        if (world.isRemote || storage.getEnergyStored() <= 0) return;

        IBlockState state = world.getBlockState(pos);
        if (!(state.getBlock() instanceof BlockTransformer)) return;

        EnumFacing facing = state.getValue(BlockTransformer.FACING);
        TileEntity te = world.getTileEntity(pos.offset(facing));

        if (te != null && te.hasCapability(ExampleMod.IE_ENERGY, facing.getOpposite())) {
            com.example.examplemod.IFE.IIEStorage cap = te.getCapability(ExampleMod.IE_ENERGY, facing.getOpposite());
            int received = cap.receiveEnergy(Math.min(storage.getEnergyStored(), outputLimit), false);
            storage.extractEnergy(received, false);
        }
    }

    public int getField(int id) {
        switch (id) {
            case 0: return storage.getEnergyStored();
            case 1: return storage.getMaxEnergyStored();
            case 2: return outputLimit;
            default: return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
            case 0: storage.setEnergy(value); break;
            case 2: outputLimit = value; break;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.storage.readFromNBT(compound);
        this.outputLimit = compound.getInteger("OutputLimit");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.storage.writeToNBT(compound);
        compound.setInteger("OutputLimit", outputLimit);
        return compound;
    }

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing) {
        return capability == ExampleMod.IE_ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing) {
        if (capability == ExampleMod.IE_ENERGY) return (T) storage;
        return super.getCapability(capability, facing);
    }
}