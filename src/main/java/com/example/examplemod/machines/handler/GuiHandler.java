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
import com.example.examplemod.machines.vacuum_deposition_unit.ContainerVacuumDepositionUnit;
import com.example.examplemod.machines.vacuum_deposition_unit.TileEntityVacuumDepositionUnit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import com.example.examplemod.machines.vacuum_deposition_unit.GuiVacuum_Deposition_Unit;
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
        if (ID == 4 && te instanceof TileEntityVacuumDepositionUnit) return new ContainerVacuumDepositionUnit(player.inventory, (TileEntityVacuumDepositionUnit) te);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (ID == 1 && te instanceof TileEntityBlastFurnace) return new GuiBlastFurnace(player.inventory, (TileEntityBlastFurnace) te);
        if (ID == 2 && te instanceof TileEntityEnergyStorage) return new GuiEnergyStorage(player.inventory, (TileEntityEnergyStorage) te);
        if (ID == 3 && te instanceof TileEntityTransformer) return new GuiTransformer(player.inventory, (TileEntityTransformer) te);
        if (ID==4 && te instanceof TileEntityVacuumDepositionUnit) return new GuiVacuum_Deposition_Unit(player.inventory,(TileEntityVacuumDepositionUnit)te);
        return null;
    }
}
