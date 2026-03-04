package com.example.examplemod.main;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.google.common.collect.ImmutableList;

@SideOnly(Side.CLIENT)
public class ModelMapper implements ICustomModelLoader {

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {

        return modelLocation.getResourceDomain().equals(ExampleMod.examplemod)
                && !modelLocation.getResourcePath().contains("block/");
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) {

        String name = modelLocation.getResourcePath();
        ResourceLocation texture = new ResourceLocation(ExampleMod.examplemod, "items/" + name);
        return new ItemLayerModel(ImmutableList.of(texture));
    }
}
