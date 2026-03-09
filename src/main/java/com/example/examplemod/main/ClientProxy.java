package com.example.examplemod.main;

import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ModelLoaderRegistry.registerLoader(new ModelMapper());
    }
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        EcompItems.ITEM_STATUE.setTileEntityItemStackRenderer(new RenderStatueItem());
    }

}
