package com.example.examplemod.main;

import com.example.examplemod.machines.BlastFurnace.BlockBlastFurnace;
import com.example.examplemod.machines.EnergyStorage.BlockEnergyStorage;
import com.example.examplemod.machines.EnergyStorage.TileEntityStatue;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = ExampleMod.examplemod)
public class ModBlocks {

    public static final Block ENERGY_STORAGE = new BlockEnergyStorage("energy_storage").setCreativeTab(ExampleMod.MOD_TAB);
    public static final Block BLAST_FURNACE = new BlockBlastFurnace().setCreativeTab(ExampleMod.MOD_TAB);
    public static final Block CABLE_EBLOCK = new com.example.examplemod.EnergyBlocks.BlockCable("cable_eblock").setCreativeTab(ExampleMod.MOD_TAB);
    public static final Block TRANSFORMATOR_EBLOCK = new com.example.examplemod.EnergyBlocks.BlockTransformer("transformer_eblock").setCreativeTab(ExampleMod.MOD_TAB);

    public static final Block BAKHMUTIUM_ORE = new Block(Material.ROCK)
            .setRegistryName("bakhmutium_ore").setUnlocalizedName("bakhmutium_ore")
            .setHardness(3.0F).setResistance(5.0F).setCreativeTab(ExampleMod.MOD_TAB);

    public static final Block URANIUM_ORE = new Block(Material.ROCK)
            .setRegistryName("uranium_ore").setUnlocalizedName("uranium_ore")
            .setHardness(3.0F).setResistance(10.0f).setCreativeTab(ExampleMod.MOD_TAB);

    public static final Block TITANIUM_ORE = new Block(Material.ROCK)
            .setRegistryName("titanium_ore").setUnlocalizedName("titanium_ore")
            .setHardness(3.0F).setResistance(10.0f).setCreativeTab(ExampleMod.MOD_TAB);

    public static final Block STATUE_BLOCK = new BlockStatueCustom("statue_block");

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();
        r.registerAll(ENERGY_STORAGE, CABLE_EBLOCK, TRANSFORMATOR_EBLOCK, BLAST_FURNACE, BAKHMUTIUM_ORE, URANIUM_ORE, TITANIUM_ORE, STATUE_BLOCK);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();

        r.register(new ItemBlock(ENERGY_STORAGE).setRegistryName(ENERGY_STORAGE.getRegistryName()));
        r.register(new ItemBlock(BLAST_FURNACE).setRegistryName(BLAST_FURNACE.getRegistryName()));
        r.register(new ItemBlock(BAKHMUTIUM_ORE).setRegistryName(BAKHMUTIUM_ORE.getRegistryName()));
        r.register(new ItemBlock(URANIUM_ORE).setRegistryName(URANIUM_ORE.getRegistryName()));
        r.register(new ItemBlock(TITANIUM_ORE).setRegistryName(TITANIUM_ORE.getRegistryName()));
        r.register(new ItemBlock(CABLE_EBLOCK).setRegistryName(CABLE_EBLOCK.getRegistryName()));
        r.register(new ItemBlock(TRANSFORMATOR_EBLOCK).setRegistryName(TRANSFORMATOR_EBLOCK.getRegistryName()));

        // Специальная регистрация для стака статуи с описанием
        ItemBlock itemStatue = new ItemBlock(STATUE_BLOCK) {
            @Override
            @SideOnly(Side.CLIENT)
            public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
                tooltip.add(I18n.format("statue.tooltip.name"));
                if (GuiScreen.isShiftKeyDown()) {
                    tooltip.add(I18n.format("statue.tooltip.desc"));
                    tooltip.add(TextFormatting.DARK_AQUA + "Style: NTM / HBM");
                } else {
                    tooltip.add(I18n.format("statue.tooltip.hold_shift"));
                }
            }
        };
        r.register(itemStatue.setRegistryName(STATUE_BLOCK.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        // РЕГИСТРИРУЕМ ЛОАДЕР ТОЛЬКО ОДИН РАЗ ЗДЕСЬ
        ModelLoaderRegistry.registerLoader(new EcompItems.ModelMapper());

        // Регистрируем модели для всех блоков
        registerBlockModel(ENERGY_STORAGE);
        registerBlockModel(BLAST_FURNACE);
        registerBlockModel(BAKHMUTIUM_ORE);
        registerBlockModel(URANIUM_ORE);
        registerBlockModel(TITANIUM_ORE);
        registerBlockModel(STATUE_BLOCK);
        registerBlockModel(CABLE_EBLOCK);
        registerBlockModel(TRANSFORMATOR_EBLOCK);
    }

    @SideOnly(Side.CLIENT)
    private static void registerBlockModel(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    public static class BlockStatueCustom extends Block {
        protected static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.9D, 0.8D);

        public BlockStatueCustom(String name) {
            super(Material.IRON);
            setRegistryName(name);
            setUnlocalizedName(name);
            setCreativeTab(ExampleMod.MOD_TAB);
        }

        @Override public boolean isOpaqueCube(IBlockState s) { return false; }
        @Override public boolean isFullCube(IBlockState s) { return false; }
        @Override public EnumBlockRenderType getRenderType(IBlockState s) { return EnumBlockRenderType.ENTITYBLOCK_ANIMATED; }
        @Override public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) { return BOUNDS; }
        @Override public boolean hasTileEntity(IBlockState s) { return true; }

        @Nullable
        @Override public TileEntity createTileEntity(World w, IBlockState s) {
            return new TileEntityStatue();
        }
    }
}
