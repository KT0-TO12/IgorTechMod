package com.example.examplemod;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init() {

        // Проверяем, что объекты существуют, чтобы избежать NullPointerException
        if (ModBlocks.TITANIUM_ORE != null && ExampleMod.TITANIUM_INGOT != null) {
            GameRegistry.addSmelting(
                    ModBlocks.TITANIUM_ORE,
                    new ItemStack(ExampleMod.TITANIUM_INGOT),
                    0.7F
            );
        }

        // 2. Регистрация генератора руды (нужна, чтобы руда появлялась в мире)
        GameRegistry.registerWorldGenerator(new OreGen(), 0);
    }
}