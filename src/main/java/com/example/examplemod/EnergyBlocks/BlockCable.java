package com.example.examplemod.EnergyBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import javax.annotation.Nullable;

public class BlockCable extends Block {
    // Размеры тонкого кабеля (от 0.0 до 1.0)
    protected static final AxisAlignedBB CABLE_AABB = new AxisAlignedBB(0.375D, 0.375D, 0.375D, 0.625D, 0.625D, 0.625D);

    public BlockCable(String name) {
        super(Material.CLOTH);
        setRegistryName(name);
        setUnlocalizedName(name);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) { return false; }

    @Override
    public boolean isFullCube(IBlockState state) { return false; }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CABLE_AABB; // Делает кабель маленьким квадратиком в центре блока
    }

    @Override
    public boolean hasTileEntity(IBlockState state) { return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityCable();
    }
}