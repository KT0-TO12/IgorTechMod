package com.example.examplemod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    // Вызывается на стороне сервера для обработки данных предметов
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        if (ID == 1) { 
            if (te instanceof TileEntityBlastFurnace) {
                return new ContainerBlastFurnace(player.inventory, (TileEntityBlastFurnace) te);
            }
        }
        return null;
    }


    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        if (ID == 1) {
            if (te instanceof TileEntityBlastFurnace) {
                return new GuiBlastFurnace(player.inventory, (TileEntityBlastFurnace) te);
            }
        }
        return null;
    }
}