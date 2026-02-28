package com.example.examplemod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int TITANIUM_FURNACE_GUI_ID = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        if (ID == TITANIUM_FURNACE_GUI_ID) {
            if (te instanceof TileEntityTitaniumFurnace) {
                return new ContainerTitaniumFurnace(player.inventory, (TileEntityTitaniumFurnace) te);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        if (ID == TITANIUM_FURNACE_GUI_ID) {
            if (te instanceof TileEntityTitaniumFurnace) {

                return new GuiTitaniumFurnace(player.inventory, (TileEntityTitaniumFurnace) te);
            }
        }
        return null;
    }
}