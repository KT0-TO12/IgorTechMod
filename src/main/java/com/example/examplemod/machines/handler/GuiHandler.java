package com.example.examplemod.machines.handler;

import com.example.examplemod.EnergyBlocks.ContainerTransformer;
import com.example.examplemod.EnergyBlocks.GuiTransformer;
import com.example.examplemod.EnergyBlocks.TileEntityTransformer;
import com.example.examplemod.machines.BlastFurnace.ContainerBlastFurnace;
import com.example.examplemod.machines.BlastFurnace.GuiBlastFurnace;
import com.example.examplemod.machines.BlastFurnace.TileEntityBlastFurnace;
import com.example.examplemod.machines.EnergyStorage.ContainerEnergyStorage;
import com.example.examplemod.machines.EnergyStorage.GuiEnergyStorage;
import com.example.examplemod.machines.EnergyStorage.TileEntityEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        if (ID == 1 && te instanceof TileEntityBlastFurnace) return new ContainerBlastFurnace(player.inventory, (TileEntityBlastFurnace) te);
        if (ID == 2 && te instanceof TileEntityEnergyStorage) return new ContainerEnergyStorage(player.inventory, (TileEntityEnergyStorage) te);
        if (ID == 3 && te instanceof TileEntityTransformer) return new ContainerTransformer(player.inventory, (TileEntityTransformer) te);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        if (ID == 1 && te instanceof TileEntityBlastFurnace) return new GuiBlastFurnace(player.inventory, (TileEntityBlastFurnace) te);
        if (ID == 2 && te instanceof TileEntityEnergyStorage) return new GuiEnergyStorage(player.inventory, (TileEntityEnergyStorage) te);
        if (ID == 3 && te instanceof TileEntityTransformer) return new GuiTransformer(player.inventory, (TileEntityTransformer) te);
        return null;
    }
}
