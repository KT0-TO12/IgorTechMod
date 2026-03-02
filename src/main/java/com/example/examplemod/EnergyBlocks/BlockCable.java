package com.example.examplemod.EnergyBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import javax.annotation.Nullable;

public class BlockCable extends Block {
    public BlockCable(String name) {
        super(Material.CLOTH); // Кабель мягкий
        setRegistryName(name);
        setUnlocalizedName(name);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityCable(); // Привязываем твой TileEntity
    }
}