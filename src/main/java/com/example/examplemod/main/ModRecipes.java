package com.example.examplemod.main;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init() {
        System.out.println("ZOV Mod: Начало регистрации рецептов плавки!");

        GameRegistry.addSmelting(ModBlocks.BAKHMUTIUM_ORE, new ItemStack(ExampleMod.SILICON_PURE), 0.7F);
        GameRegistry.addSmelting(ModBlocks.TITANIUM_ORE, new ItemStack(ExampleMod.TITANIUM_INGOT), 0.7f);
        GameRegistry.addSmelting(ModBlocks.URANIUM_ORE, new ItemStack(ExampleMod.URANIUM_INGOT), 1.0f);

        System.out.println("ZOV Mod: Рецепты успешно добавлены!");
    }
}