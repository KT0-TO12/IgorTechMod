package com.example.examplemod.EnergyBlocks;

import com.example.examplemod.IFE.IEStorage;
import com.example.examplemod.IFE.IIEStorage;
import com.example.examplemod.main.ExampleMod;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileEntityCable extends TileEntity implements ITickable {

    private final IEStorage storage = new IEStorage(1000);

    @Override
    public void update() {
        if (world.isRemote || storage.getEnergyStored() <= 0) return;

        for (EnumFacing facing : EnumFacing.values()) {
            BlockPos targetPos = pos.offset(facing);
            TileEntity te = world.getTileEntity(targetPos);

            if (te != null && te.hasCapability(ExampleMod.IE_ENERGY, facing.getOpposite())) {
                IIEStorage cap = te.getCapability(ExampleMod.IE_ENERGY, facing.getOpposite());
                if (cap != null && cap.canReceive()) {
                    int canSend = Math.min(storage.getEnergyStored(), 100);
                    int received = cap.receiveEnergy(canSend, false);
                    storage.extractEnergy(received, false);
                    if (storage.getEnergyStored() <= 0) break;
                }
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == ExampleMod.IE_ENERGY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == ExampleMod.IE_ENERGY) return (T) storage;
        return super.getCapability(capability, facing);
    }
}
