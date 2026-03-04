package com.example.examplemod.EnergyBlocks;

import com.example.examplemod.IFE.IEStorage;
import com.example.examplemod.main.ExampleMod;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityCable extends TileEntity implements ITickable {
    private final IEStorage storage = new IEStorage(1000);

    @Override
    public void update() {
        if (world.isRemote || storage.getEnergyStored() <= 0) return;

        for (EnumFacing facing : EnumFacing.values()) {
            BlockPos targetPos = pos.offset(facing);
            TileEntity te = world.getTileEntity(targetPos);

            if (te != null && te.hasCapability(ExampleMod.IE_ENERGY, facing.getOpposite())) {
                com.example.examplemod.IFE.IIEStorage cap = te.getCapability(ExampleMod.IE_ENERGY, facing.getOpposite());
                int canSend = Math.min(storage.getEnergyStored(), 100);
                int received = cap.receiveEnergy(canSend, false);
                storage.extractEnergy(received, false);
                if (storage.getEnergyStored() <= 0) break;
            }
        }
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