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
        //пластины из кремния
        SILICON_PLATE_WITH_1_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_1_tranzistors_ecomp");
        SILICON_PLATE_WITH_5_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_5_tranzistors_ecomp");
        SILICON_PLATE_WITH_10_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_10_tranzistors_ecomp");
        SILICON_PLATE_WITH_50_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_50_tranzistors_ecomp");
        SILICON_PLATE_WITH_100_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_100_tranzistors_ecomp");
        SILICON_PLATE_WITH_500_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_500_tranzistors_ecomp");
        SILICON_PLATE_WITH_1k_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_1k_tranzistors_ecomp");
        SILICON_PLATE_WITH_5k_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_5k_tranzistors_ecomp");
        SILICON_PLATE_WITH_10k_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_10k_tranzistors_ecomp");
        SILICON_PLATE_WITH_50k_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_50k_tranzistors_ecomp");
        SILICON_PLATE_WITH_100k_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_100k_tranzistors_ecomp");
        SILICON_PLATE_WITH_500k_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_500k_tranzistors_ecomp");
        SILICON_PLATE_WITH_1m_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_1m_tranzistors_ecomp");
        SILICON_PLATE_WITH_10m_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_10m_tranzistors_ecomp");
        SILICON_PLATE_WITH_100m_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_100m_tranzistors_ecomp");
        SILICON_PLATE_WITH_500m_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_500m_tranzistors_ecomp");
        SILICON_PLATE_WITH_1b_TRANZISTORS_ECOMP = registerItem(registry, new Item(),"SI_plate_with_1b_tranzistors_ecomp");
        //SMD
        SMD_BASE_ECOMP = registerItem(registry, new Item(),"smd_base_ecomp");
        SMD_RESISTOR_ECOMP= registerItem(registry, new Item(),"smd_resistore_ecomp");
        SMD_CAPACITOR_ECOMP= registerItem(registry, new Item(),"smd_capacitor_ecomp");
        SMD_TRANZISTOR_ECOMP= registerItem(registry, new Item(),"smd_tranzistor_ecomp");
        //основа
        RESISTOR_ECOMP = registerItem(registry, new Item(),"resistore_ecomp");
        CAPACITOR_ECOMP = registerItem(registry, new Item(),"capacitor_ecomp");
        TRANZISTOR_ECOMP = registerItem(registry, new Item(),"tranzistor_ecomp");
        TEXTOLITE_PLATE_ECOMP = registerItem(registry, new Item(),"textolite_plate_ecomp");
        COPPER_PLATE_ECOMP = registerItem(registry, new Item(),"copper_plate_ecomp");
        SILICON_PLATE_ECOMP = registerItem(registry, new Item(),"silicon_plate_ecomp");
        VACUM_TUBE_ECOMP = registerItem(registry, new Item(),"vacum_tube_ecomp");
        INFINITE_BATTERY = registerItem(registry, new Item().setMaxStackSize(1), "infinite_battery");
        //чипы и контроллеры
        ADVANCED_MICROCHIP_BASE_ECOMP = registerItem(registry, new Item(),"advanced_microchip_base_ecomp");
        MICROCHIP_BASE_ECOMP = registerItem(registry, new Item(),"microchip_base_ecomp");
        MICROCONTROLLER_BASE_ECOMP = registerItem(registry, new Item(),"microcontroller_base_ecomp");
        MICROCHIP_100k_ECOMP = registerItem(registry, new Item(),"Microchip_100k_ecomp");
        MICROCHIP_500k_ECOMP = registerItem(registry, new Item(),"Microchip_500k_ecomp");
        MICROCHIP_1m_ECOMP = registerItem(registry, new Item(),"Microchip_1m_ecomp");
        MICROCHIP_10m_ECOMP = registerItem(registry, new Item(),"Microchip_10m_ecomp");
        MICROCHIP_ADVANCED_100m_ECOMP = registerItem(registry, new Item(),"Microchip_advanced_100m_ecomp");
        MICROCHIP_ADVANCED_500m_ECOMP = registerItem(registry, new Item(),"Microchip_advanced_500m_ecomp");
        MICROCHIP_ADVANCED_1b_ECOMP = registerItem(registry, new Item(),"MicroChip_1b_ecomp");
        //другое
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