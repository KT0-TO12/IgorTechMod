package com.example.examplemod.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        for (Render<? extends Entity> render : Minecraft.getMinecraft().getRenderManager().entityRenderMap.values()) {
            if (render instanceof RenderWolf) {
                RenderWolf renderWolf = (RenderWolf) render;
                renderWolf.addLayer(new LayerDogArmor(renderWolf));
            }
        }
    }
}
