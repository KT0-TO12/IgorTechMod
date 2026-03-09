package com.example.examplemod.main;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import com.google.common.collect.ImmutableList;

public class ModelMapper implements ICustomModelLoader {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {}

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals("examplemod")
                && !modelLocation.getResourcePath().contains("block/");
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) {
        String name = modelLocation.getResourcePath();
        ResourceLocation texture = new ResourceLocation("examplemod", "items/" + name);
        return new ItemLayerModel(ImmutableList.of(texture));
    }
}
