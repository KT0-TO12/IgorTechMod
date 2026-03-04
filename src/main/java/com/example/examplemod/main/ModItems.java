package com.example.examplemod.main;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static com.example.examplemod.main.EcompItems.STATUE_BLOCK_2;
import static com.example.examplemod.main.ModBlocks.STATUE_BLOCK;

@Mod.EventBusSubscriber(modid = ExampleMod.examplemod)
public class ModItems {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        // Если это БЛОК (как первая статуя), регистрируем его через ItemBlock
        if (STATUE_BLOCK instanceof Block) {
            registerItemBlock(registry, (Block) STATUE_BLOCK);
        }

        // Если это ПРЕДМЕТ (как вторая статуя), регистрируем его просто как Item
        if (STATUE_BLOCK_2 instanceof Item) {
            registry.register((Item) STATUE_BLOCK_2);
        }
    }

    private static void registerItemBlock(IForgeRegistry<Item> registry, Block block) {
        if (block != null && block.getRegistryName() != null) {
            ItemBlock itemBlock = new ItemBlock(block);
            itemBlock.setRegistryName(block.getRegistryName());
            registry.register(itemBlock);
        }
    }
}
