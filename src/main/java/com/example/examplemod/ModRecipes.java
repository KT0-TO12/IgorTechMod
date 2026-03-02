package com.example.examplemod;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init() {
        GameRegistry.addSmelting(ModBlocks.TITANIUM_ORE, new ItemStack(ExampleMod.TITANIUM_INGOT), 0.7f);
        GameRegistry.addSmelting(ModBlocks.URANIUM_ORE, new ItemStack(ExampleMod.URANIUM_INGOT), 1.0f);
    }
}
