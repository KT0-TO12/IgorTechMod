package com.example.examplemod.main;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init() {
        System.out.println("ZOV Mod: Начало регистрации рецептов плавки!");
        GameRegistry.addSmelting(Items.FLINT,new ItemStack(EcompItems.SILICON_PURE), 0.7F);
        GameRegistry.addSmelting(ModBlocks.BAKHMUTIUM_ORE, new ItemStack(EcompItems.SILICON_PURE), 0.7F);
        GameRegistry.addSmelting(ModBlocks.TITANIUM_ORE, new ItemStack(EcompItems.TITANIUM_INGOT), 0.7f);
        GameRegistry.addSmelting(ModBlocks.URANIUM_ORE, new ItemStack(EcompItems.URANIUM_INGOT), 1.0f);

        System.out.println("ZOV Mod: Рецепты успешно добавлены!");
    }
}