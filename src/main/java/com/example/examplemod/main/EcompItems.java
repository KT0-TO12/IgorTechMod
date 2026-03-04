package com.example.examplemod.main;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ExampleMod.examplemod)
public class EcompItems {

    public static final List<Item> ITEMS = new ArrayList<>();

    // ЭЛЕКТРОНИКА (БАЗЫ)
    public static final Item TRANZISTOR_BASE_ECOMP = reg("tranzistor_base_ecomp");
    public static final Item MICROCONTROLLER_BASE_ECOMP = reg("microcontroller_base_ecomp");
    public static final Item MICROCHIP_BASE_ECOMP = reg("microchip_base_ecomp");
    public static final Item ADVANCED_MICROCHIP_BASE_ECOMP = reg("advanced_microchip_base_ecomp");
    public static final Item SMD_BASE_ECOMP = reg("smd_base_ecomp");

    // ОСНОВА
    public static final Item SILICON_PURE = reg("silicon_pure");
    public static final Item SILICON_PLATE_ECOMP = reg("silicon_plate_ecomp");
    public static final Item TRANZISTOR_ECOMP = reg("tranzistor_ecomp");
    public static final Item VACUM_TUBE_ECOMP = reg("vacum_tube_ecomp");
    public static final Item COPPER_PLATE_ECOMP = reg("copper_plate_ecomp");
    public static final Item TEXTOLITE_PLATE_ECOMP = reg("textolite_plate_ecomp");
    public static final Item CAPACITOR_ECOMP = reg("capacitor_ecomp");
    public static final Item RESISTOR_ECOMP = reg("resistor_ecomp");

    // ЧИПЫ
    public static final Item MICROCHIP_100k_ECOMP = reg("microchip_100k_ecomp");
    public static final Item MICROCHIP_500k_ECOMP = reg("microchip_500k_ecomp");
    public static final Item MICROCHIP_1m_ECOMP = reg("microchip_1m_ecomp");
    public static final Item MICROCHIP_10m_ECOMP = reg("microchip_10m_ecomp");
    public static final Item MICROCHIP_ADVANCED_100m_ECOMP = reg("microchip_advanced_100m_ecomp");
    public static final Item MICROCHIP_ADVANCED_500m_ECOMP = reg("microchip_advanced_500m_ecomp");
    public static final Item MICROCHIP_ADVANCED_1b_ECOMP = reg("microchip_advanced_1b_ecomp");

    // ПЛАСТИНЫ С ТРАНЗИСТОРАМИ
    public static final Item SILICON_PLATE_WITH_1_TR = reg("silicon_plate_1_tr");
    public static final Item SILICON_PLATE_WITH_5_TR = reg("silicon_plate_5_tr");
    public static final Item SILICON_PLATE_WITH_10_TR = reg("silicon_plate_10_tr");
    public static final Item SILICON_PLATE_WITH_50_TR = reg("silicon_plate_50_tr");
    public static final Item SILICON_PLATE_WITH_100_TR = reg("silicon_plate_100_tr");
    public static final Item SILICON_PLATE_WITH_500_TR = reg("silicon_plate_500_tr");
    public static final Item SILICON_PLATE_WITH_1k_TR = reg("silicon_plate_1k_tr");
    public static final Item SILICON_PLATE_WITH_5k_TR = reg("silicon_plate_5k_tr");
    public static final Item SILICON_PLATE_WITH_10k_TR = reg("silicon_plate_10k_tr");
    public static final Item SILICON_PLATE_WITH_50k_TR = reg("silicon_plate_50k_tr");
    public static final Item SILICON_PLATE_WITH_100k_TR = reg("silicon_plate_100k_tr");
    public static final Item SILICON_PLATE_WITH_500k_TR = reg("silicon_plate_500k_tr");
    public static final Item SILICON_PLATE_WITH_1m_TR = reg("silicon_plate_1m_tr");
    public static final Item SILICON_PLATE_WITH_10m_TR = reg("silicon_plate_10m_tr");
    public static final Item SILICON_PLATE_WITH_100m_TR = reg("silicon_plate_100m_tr");
    public static final Item SILICON_PLATE_WITH_500m_TR = reg("silicon_plate_500m_tr");
    public static final Item SILICON_PLATE_WITH_1b_TR = reg("silicon_plate_1b_tr");

    // СМД И СНАРЯЖЕНИЕ
    public static final Item SMD_TRANZISTOR_ECOMP = reg("smd_tranzistor_ecomp");
    public static final Item SMD_RESISTOR_ECOMP = reg("smd_resistor_ecomp");
    public static final Item SMD_CAPACITOR_ECOMP = reg("smd_capacitor_ecomp");
    public static final Item STEEL_INGOT = reg("steel_ingot");
    public static final Item TITANIUM_INGOT = reg("titanium_ingot");
    public static final Item URANIUM_INGOT = reg("uranium_ingot");
    public static final Item INFINITE_BATTERY = reg("infinite_battery");
    public static final Item STATUE_BLOCK_2 = reg("statue_block_2");

    private static Item reg(String name) {
        Item item = new Item().setRegistryName(name).setUnlocalizedName(name).setCreativeTab(ExampleMod.MOD_TAB);
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
        ModelLoaderRegistry.registerLoader(new ModelMapper());
        for (Item item : ITEMS) {
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ModelMapper implements ICustomModelLoader {
        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {}

        @Override
        public boolean accepts(ResourceLocation modelLocation) {
            return modelLocation.getResourceDomain().equals(ExampleMod.examplemod)
                    && !modelLocation.getResourcePath().contains("block/");
        }

        @Override
        public IModel loadModel(ResourceLocation modelLocation) {
            String name = modelLocation.getResourcePath();
            ResourceLocation texture = new ResourceLocation(ExampleMod.examplemod, "items/" + name);
            return new ItemLayerModel(ImmutableList.of(texture));
        }
    }
}
