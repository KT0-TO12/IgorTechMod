package com.example.examplemod.main;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ExampleMod.examplemod)
public class EcompItems {

    public static final List<Item> ITEMS = new ArrayList<>();

    // ЭЛЕКТРОНИКА
    public static final Item TRANZISTOR_BASE_ECOMP = reg("tranzistor");
    public static final Item MICROCONTROLLER_BASE_ECOMP = reg("microcontrollerbase");
    public static final Item MICROCHIP_BASE_ECOMP = reg("microchipbase");
    public static final Item ADVANCED_MICROCHIP_BASE_ECOMP = reg("advancedmicrochipbase");
    public static final Item SMD_BASE_ECOMP = reg("smdbase");
    // ОРУЖИЕ
    public static final Item SVAGAMET = reg("svagamet");
    // ОСНОВА
    public static final Item SILICON_PURE = reg("silicon_pure");
    public static final Item CAPACITOR_ECOMP = reg("capacitor");
    public static final Item RESISTOR_ECOMP = reg("resistor");
    public static final Item VACUM_TUBE_ECOMP = reg("vacum_tube");
    public static final Item INFINITE_BATTERY = reg("infinite_battery");
    // СНАРЯЖЕНИЕ
    public static final Item MEGAKNIGHT_ARMOR = reg("megaknight_armor");
    public static final Item DOG_ARMOR = reg("new_dog_armor");
    public static final Item DOG_HELMET = reg("new_dog_helmet");
    public static final Item DOG_CHESTPLATE = reg("new_dog_chestplate");
    public static final Item DOG_TAIL = reg("new_dog_tail");
    public static final Item GAS_FILTER = reg("new_gas_filter");
    public static final Item ARMOR_PLATE = reg("new_armor_plate");

    // МЕТАЛЛЫ И СТАТУЯ
    public static final Item STEEL_INGOT = reg("steel_ingot");
    public static final Item ALUMINIUM_INGOT = reg("aluminium_ingot");
    public static final Item TITANIUM_INGOT = reg("new_titanium_ingot");
    public static final Item BAKHMUTIUM_INGOT = reg("bakhmutium_ingot");
    public static final Item URANIUM_INGOT = reg("uranium_ingot");
    public static final Item ITEM_STATUE = reg("item_statue");

    // ПРОЧИЕ
    public static final Item SILICON_PLATE_ECOMP = reg("silicon_plate_ecomp");
    public static final Item TRANZISTOR_ECOMP = reg("tranzistor_ecomp");
    public static final Item COPPER_PLATE_ECOMP = reg("copper_plate_ecomp");
    public static final Item TEXTOLITE_PLATE_ECOMP = reg("textolite_plate_ecomp");

    private static Item reg(String name) {
        Item item = new Item()
                .setRegistryName(ExampleMod.examplemod, name)
                .setUnlocalizedName(ExampleMod.examplemod + "." + name)
                .setCreativeTab(ExampleMod.MOD_TAB);
        ITEMS.add(item);
        return item;
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();
        for (Item item : ITEMS) {
            r.register(item);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item : ITEMS) {
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }
}
