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
    //ХИМИЯ
    public static final Item BOTTLED_PHOTORESIST = reg("bottled_photoresist").setCreativeTab(ExampleMod.MOD_CHEMETRY_TAB);
    public static final Item SILICON_PURE_PHOTORESISTED = reg("silicon_plate_photoresisted").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item FERROUS_CHLORIDE_3 = reg("ferric_chloride_3").setCreativeTab(ExampleMod.MOD_CHEMETRY_TAB);
    public static final Item PHOTORESIST = reg("photoresist").setCreativeTab(ExampleMod.MOD_CHEMETRY_TAB);
    public static final Item SOLVENT = reg("solvent").setCreativeTab(ExampleMod.MOD_CHEMETRY_TAB);
    public static final Item SILICON_PURE_ALUMINIED = reg("silicon_plate_aluminium");
    public static final Item SILICON_PLATE = reg("silicon_plate").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item QUARZ_SAND = reg("quarz_sand").setCreativeTab(ExampleMod.MOD_CHEMETRY_TAB);
    public static final Item FIBERGLASS = reg ("fiberglass").setCreativeTab(ExampleMod.MOD_CHEMETRY_TAB);
    public static final Item COPPERED_TEXTOLITE = reg("coppered_textolite").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    // ЭЛЕКТРОНИКА
    public static final Item SILICON_WITH_HARDENED_PHOTORESIST = reg("silicon_with_hardened_photoresist").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item HEATING_ELECTRODE = reg("heating_electrode").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item TRANZISTOR = reg("tranzistor").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item SVAGAMET = reg("svagamet").setCreativeTab(ExampleMod.MOD_TAB);
    public static final Item SMD_TRANZISTOR = reg("smd_tranzistor").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item SMD_RESISTOR = reg ("smd_resistor").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item SMD_CAPACITOR = reg("smd_capasitor").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item CHIP = reg("chip").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item MICROCHIP = reg("microchip").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item SILICON_PURE = reg("silicon_dioxide").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item CAPACITOR_ECOMP = reg("capacitor").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item RESISTOR_ECOMP = reg("resistor").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item VACUM_TUBE_ECOMP = reg("vacum_tube").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item INFINITE_BATTERY = reg("infinite_battery").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    // СНАРЯЖЕНИЕ
    public static final Item MEGAKNIGHT_ARMOR = reg("megaknight_armor").setCreativeTab(ExampleMod.MOD_TAB);
    public static final Item DOG_ARMOR = reg("new_dog_armor").setCreativeTab(ExampleMod.MOD_TAB);
    public static final Item DOG_HELMET = reg("new_dog_helmet").setCreativeTab(ExampleMod.MOD_TAB);
    public static final Item DOG_CHESTPLATE = reg("new_dog_chestplate").setCreativeTab(ExampleMod.MOD_TAB);
    public static final Item DOG_TAIL = reg("new_dog_tail").setCreativeTab(ExampleMod.MOD_TAB);
    public static final Item GAS_FILTER = reg("new_gas_filter").setCreativeTab(ExampleMod.MOD_TAB);
    public static final Item ARMOR_PLATE = reg("new_armor_plate").setCreativeTab(ExampleMod.MOD_TAB);

    // МЕТАЛЛЫ И СТАТУЯ
    public static final Item STEEL_INGOT = reg("steel_ingot").setCreativeTab(ExampleMod.MOD_RESOURCES_TAB);
    public static final Item ALUMINIUM_INGOT = reg("aluminium_ingot").setCreativeTab(ExampleMod.MOD_RESOURCES_TAB);
    public static final Item TITANIUM_INGOT = reg("new_titanium_ingot").setCreativeTab(ExampleMod.MOD_RESOURCES_TAB);
    public static final Item BAKHMUTIUM_INGOT = reg("bakhmutium_ingot").setCreativeTab(ExampleMod.MOD_RESOURCES_TAB);
    public static final Item URANIUM_INGOT = reg("uranium_ingot").setCreativeTab(ExampleMod.MOD_RESOURCES_TAB);
    public static final Item ITEM_STATUE = reg("item_statue").setCreativeTab(ExampleMod.MOD_RESOURCES_TAB);
    // ПРОЧИЕ
    public static final Item SILICON_PLATE_ECOMP = reg("silicon_plate_ecomp").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item COPPER_PLATE_ECOMP = reg("copper_plate_ecomp").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);
    public static final Item TEXTOLITE_PLATE_ECOMP = reg("textolite_plate_ecomp").setCreativeTab(ExampleMod.MOD_ELECTRONIC_TAB);

    private static Item reg(String name) {
        Item item = new Item()
                .setRegistryName(ExampleMod.examplemod, name)
                .setUnlocalizedName(ExampleMod.examplemod + "." + name);
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
