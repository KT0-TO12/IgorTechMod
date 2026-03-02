package com.example.examplemod.main;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.List;
import static com.example.examplemod.main.ExampleMod.*;
import static com.example.examplemod.main.ModBlocks.ENERGY_STORAGE;

@Mod.EventBusSubscriber(modid = "examplemod")
public class ModItems {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        DOG_ARMOR = new Item() {
            @Override
            @SideOnly(Side.CLIENT)
            public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
                tooltip.add(TextFormatting.GRAY + "Легендарная броня рейнжера НКР но теперь для бурмалдаки");
                if (GuiScreen.isShiftKeyDown()) {
                    tooltip.add(TextFormatting.GOLD + "пиздатость: " + TextFormatting.WHITE + "10/10");
                    tooltip.add(TextFormatting.RED + "ну и конечно: " + TextFormatting.WHITE + "ГДЕ БЛЯДЬ СЕРВОПРИВОДЫ!?");
                } else {
                    tooltip.add(TextFormatting.DARK_GRAY + "Нажми " + TextFormatting.AQUA + "Shift" + TextFormatting.DARK_GRAY + " для инфо");
                }
            }
        };

        registerItem(registry, DOG_ARMOR, "new_dog_armor");
        INFINITE_BATTERY = registerItem(registry, new Item().setMaxStackSize(1), "infinite_battery");
        STEEL_INGOT = registerItem(registry, new Item(), "steel_ingot");
        URANIUM_INGOT = registerItem(registry, new Item(), "uranium_ingot");
        TITANIUM_INGOT = registerItem(registry, new Item(), "new_titanium_ingot");
        GAS_FILTER = registerItem(registry, new Item(), "new_gas_filter");
        ARMOR_PLATE = registerItem(registry, new Item(), "new_armor_plate");
        DOG_HELMET = registerItem(registry, new Item(), "new_dog_helmet");
        DOG_CHESTPLATE = registerItem(registry, new Item(), "new_dog_chestplate");
        DOG_TAIL = registerItem(registry, new Item(), "new_dog_tail");
        bakhmutium_ingot = registerItem(registry, new Item(), "bakhmutium_ingot");

        if (STATUE_BLOCK != null) registerItemBlock(registry, STATUE_BLOCK, "new_statue_block");
        if (STATUE_BLOCK_2 != null) registerItemBlock(registry, STATUE_BLOCK_2, "new_statue_block_2");
    }

    private static Item registerItem(IForgeRegistry<Item> registry, Item item, String name) {
        item.setRegistryName(name);
        item.setUnlocalizedName(name);
        item.setCreativeTab(ExampleMod.MOD_TAB);
        registry.register(item);
        return item;
    }

    private static void registerItemBlock(IForgeRegistry<Item> registry, Block block, String name) {
        registry.register(new ItemBlock(ENERGY_STORAGE).setRegistryName(ENERGY_STORAGE.getRegistryName()));
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(name);
        registry.register(itemBlock);
    }
}