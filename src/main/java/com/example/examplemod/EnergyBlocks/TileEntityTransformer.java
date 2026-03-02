package com.example.examplemod.EnergyBlocks;

import com.example.examplemod.IFE.IEStorage;
import com.example.examplemod.main.ExampleMod;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockHorizontal;

public class TileEntityTransformer extends TileEntity implements ITickable {
    private final IEStorage storage = new IEStorage(5000);

    @Override
    public void update() {
        if (world.isRemote) return;

        IBlockState state = world.getBlockState(pos);
        if (!(state.getBlock() instanceof BlockHorizontal)) return;

        EnumFacing outputFacing = state.getValue(BlockHorizontal.FACING);
        BlockPos targetPos = pos.offset(outputFacing);
        TileEntity te = world.getTileEntity(targetPos);

        if (te != null && te.hasCapability(ExampleMod.IE_ENERGY, outputFacing.getOpposite())) {
            Object cap = te.getCapability(ExampleMod.IE_ENERGY, outputFacing.getOpposite());
            if (cap instanceof IEStorage) {
                IEStorage targetStorage = (IEStorage) cap;
                int transfer = Math.min(storage.getEnergyStored(), 500);
                int received = targetStorage.receiveEnergy(transfer, false);
                storage.extractEnergy(received, false);
            }
        }
    }

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing) {
        if (capability == ExampleMod.IE_ENERGY) {
            IBlockState state = world.getBlockState(pos);
            EnumFacing outputFacing = state.getValue(BlockHorizontal.FACING);
            return facing != outputFacing;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing) {
        if (capability == ExampleMod.IE_ENERGY) return (T) storage;
        return super.getCapability(capability, facing);
    }
}