package com.example.examplemod;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ExampleMod.PESPATRON, name = ExampleMod.NAME, version = ExampleMod.VERSION)
@Mod.EventBusSubscriber
public class ExampleMod {

    public static Item INFINITE_BATTERY;
    public static final String PESPATRON = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.1";

    @Mod.Instance(PESPATRON)
    public static ExampleMod instance;

    public static final CreativeTabs MOD_TAB = new CreativeTabs("tab_pespatron") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(DOG_ARMOR);
        }
    };
    public static Item TITANIUM_INGOT;
    public static Item URANIUM_INGOT;
    public static Item GAS_FILTER;
    public static Item ARMOR_PLATE;
    public static Item DOG_HELMET;
    public static Item DOG_CHESTPLATE;
    public static Item DOG_TAIL;
    public static Item DOG_ARMOR;

    public static Block STATUE_BLOCK;
    public static Block STATUE_BLOCK_2;
    public static Item ITEM_STATUE;
    public static Item ITEM_STATUE_2;
    public static Block TITANIUM_FURNACE;
    public static Item ITEM_FURNACE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (event.getSide().isClient()) {
            DiscordManager.start();
        }
        // БЛОКИ
        STATUE_BLOCK = new LayerDogArmor.Statue("new_statue_block");
        STATUE_BLOCK_2 = new LayerDogArmor.Statue("new_statue_block_2");
        TITANIUM_FURNACE = new BlockTitaniumFurnace("new_titanium_furnace");

        // ПРЕДМЕТЫ
        INFINITE_BATTERY = new ItemInfiniteBattery();
        URANIUM_INGOT = new ItemBase("uranium_ingot");
        TITANIUM_INGOT = new ItemBase("new_titanium_ingot");
        GAS_FILTER = new ItemBase("new_gas_filter");
        ARMOR_PLATE = new ItemBase("new_armor_plate");
        DOG_HELMET = new ItemBase("new_dog_helmet");
        DOG_CHESTPLATE = new ItemBase("new_dog_chestplate");
        DOG_TAIL = new ItemBase("new_dog_tail");
        DOG_ARMOR = new ItemBase("new_dog_armor") {

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

        // ПРЕДМЕТНЫЕ ФОРМЫ БЛОКОВ
        ITEM_STATUE = new net.minecraft.item.ItemBlock(STATUE_BLOCK).setRegistryName("new_statue_block");
        ITEM_STATUE_2 = new net.minecraft.item.ItemBlock(STATUE_BLOCK_2).setRegistryName("new_statue_block_2");
        ITEM_FURNACE = new net.minecraft.item.ItemBlock(TITANIUM_FURNACE).setRegistryName("new_titanium_furnace");

        GameRegistry.registerTileEntity(TileEntityStatue.class, new ResourceLocation(PESPATRON, "new_statue_tile"));
        GameRegistry.registerTileEntity(TileEntityTitaniumFurnace.class, new ResourceLocation(PESPATRON, "new_titanium_furnace_tile"));
    }
    //init
    @EventHandler
    public void init(FMLInitializationEvent event) {
        ModRecipes.init();
        GameRegistry.registerWorldGenerator(new OreGen(), 0);
        net.minecraftforge.oredict.OreDictionary.registerOre("ingotTitanium",TITANIUM_INGOT);
        net.minecraftforge.oredict.OreDictionary.registerOre("ingotUranium",URANIUM_INGOT);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        if (event.getSide() == Side.CLIENT) {
            registerDogLayer();
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("ZOV Mod: Post-Initialization завершена.");
    }

    @Mod.EventHandler
    public void stop(FMLServerStoppingEvent event) {
        if (event.getSide().isClient()) {
            DiscordManager.stop();
            System.out.println("Discord RPC успешно остановлен.");
        }
    }

    @SideOnly(Side.CLIENT)
    private void registerDogLayer() {
        RenderWolf render = (RenderWolf) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntityWolf.class);
        if (render != null) render.addLayer(new LayerDogArmor(render));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(TITANIUM_FURNACE, STATUE_BLOCK_2, STATUE_BLOCK);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(INFINITE_BATTERY, DOG_ARMOR, DOG_HELMET, DOG_CHESTPLATE, DOG_TAIL, ARMOR_PLATE, TITANIUM_INGOT,URANIUM_INGOT,ITEM_STATUE, ITEM_STATUE_2, ITEM_FURNACE, GAS_FILTER);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerModel(DOG_ARMOR);
        registerModel(DOG_HELMET);
        registerModel(DOG_CHESTPLATE);
        registerModel(DOG_TAIL);
        registerModel(ARMOR_PLATE);
        registerModel(TITANIUM_INGOT);
        registerModel(URANIUM_INGOT);
        registerModel(ITEM_STATUE);
        registerModel(ITEM_STATUE_2);
        registerModel(GAS_FILTER);
        registerModel(ITEM_FURNACE);

        if (Side.CLIENT == net.minecraftforge.fml.common.FMLCommonHandler.instance().getSide()) {
            initClientRenders();
        }
    }

    @SideOnly(Side.CLIENT)
    private static void initClientRenders() {
        ITEM_STATUE.setTileEntityItemStackRenderer(new RenderStatueItem());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStatue.class, new RenderStatueBlock());
    }

    public static void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof EntityWolf) {
            EntityWolf wolf = (EntityWolf) event.getTarget();
            if (wolf.isTamed() && event.getItemStack().getItem() == DOG_ARMOR) {
                if (!wolf.getEntityData().getBoolean("has_dog_armor")) {
                    wolf.getEntityData().setBoolean("has_dog_armor", true);
                    if (!event.getEntityPlayer().capabilities.isCreativeMode) event.getItemStack().shrink(1);
                    wolf.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F, 1.0F);
                    event.setCanceled(true);
                }
            }
        }
    }

    public static class ItemInfiniteBattery extends Item {

        public ItemInfiniteBattery() {
            setUnlocalizedName("infinite_battery");
            setRegistryName("infinite_battery");
            setCreativeTab(ExampleMod.MOD_TAB);
            setMaxStackSize(1);
        }

        //        /**
//         * Логика бесконечной энергии через NBT.
//         * Вызывается каждый тик, пока предмет в инвентаре.
//         */
//        @Override
//        public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
//            if (!stack.hasTagCompound()) {
//                stack.setTagCompound(new NBTTagCompound());
//            }
//
//            NBTTagCompound nbt = stack.getTagCompound();
//            nbt.setLong("energy", Long.MAX_VALUE);
//            nbt.setLong("max_energy", Long.MAX_VALUE);
//            nbt.setInteger("charge", Integer.MAX_VALUE);
//        }
        //описание
        @Override
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

            tooltip.add(TextFormatting.GRAY + "мини-реактор на америции-242");

            if (GuiScreen.isShiftKeyDown()) {
                tooltip.add(TextFormatting.RED + "ТОНИ СТАРК СОБРАЛ МИНИ РЕАКТОР СИДЯ В ЯМЕ!ИЗ МЕТАЛОЛОМА!");
            } else {
                tooltip.add(TextFormatting.DARK_GRAY + "Нажми " + TextFormatting.AQUA + "Shift" + TextFormatting.DARK_GRAY + " для инфо");

            }
        }

//        /**
//         * Визуальная полоска заряда (всегда полная)
//         */
//        @Override
//        public boolean showDurabilityBar(ItemStack stack) {
//            return true;
//        }
//
//        @Override
//        public double getDurabilityForDisplay(ItemStack stack) {
//            // 0.0 - полная полоска, 1.0 - пустая.
//            return 0.0;
//        }
    }
}



//печь
class BlockTitaniumFurnace extends Block {
    public BlockTitaniumFurnace(String name) {
        super(Material.IRON);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ExampleMod.MOD_TAB);
        setHardness(3.5F);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(ExampleMod.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override public boolean hasTileEntity(IBlockState s) { return true; }
    @Override public TileEntity createTileEntity(World w, IBlockState s) { return new TileEntityTitaniumFurnace(); }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityTitaniumFurnace) {
            TileEntityTitaniumFurnace furnace = (TileEntityTitaniumFurnace) te;
            for (int i = 0; i < furnace.getSizeInventory(); i++) {
                ItemStack stack = furnace.getStackInSlot(i);
                if (!stack.isEmpty()) InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
        super.breakBlock(world, pos, state);
    }
}


class BlockStatue extends Block {
    protected static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.5D, 0.7D);
    public BlockStatue(String name) {
        super(Material.IRON);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ExampleMod.MOD_TAB);
    }
    @Override public boolean isOpaqueCube(IBlockState s) { return false; }
    @Override public boolean isFullCube(IBlockState s) { return false; }
    @Override public net.minecraft.util.EnumBlockRenderType getRenderType(IBlockState s) { return net.minecraft.util.EnumBlockRenderType.ENTITYBLOCK_ANIMATED; }
    @Override public AxisAlignedBB getBoundingBox(IBlockState s, IBlockAccess w, BlockPos p) { return BOUNDS; }
    @Override public boolean hasTileEntity(IBlockState s) { return true; }
    @Override public TileEntity createTileEntity(World w, IBlockState s) { return new TileEntityStatue(); }
}


class TileEntityStatue extends TileEntity {}

@SideOnly(Side.CLIENT)
class RenderStatueBlock extends TileEntitySpecialRenderer<TileEntityStatue> {

    private final ModelPlayer model = new ModelPlayer(0.0F, false);
    private static final ResourceLocation SKIN = new ResourceLocation(ExampleMod.PESPATRON, "textures/entity/my_skin.png");

    @Override
    public void render(TileEntityStatue te, double x, double y, double z, float pt, int ds, float a) {
        GlStateManager.pushMatrix();
        //Позиционирование
        GlStateManager.translate(x + 0.5, y, z + 0.5);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F); // Переворачиваем модель ногами вниз

        //Масштаб
        GlStateManager.scale(0.3F, 0.3F, 0.3F);
        GlStateManager.translate(0.0F, -1.5F, 0.0F); // Центрируем по вертикали

        //Текстура
        bindTexture(SKIN);

        //Отрисовка всех частей
        float s = 0.0625F;
        renderModel(s);

        GlStateManager.popMatrix();
    }


    private void renderModel(float s) {
        model.bipedHead.render(s);
        model.bipedBody.render(s);
        model.bipedRightArm.render(s);
        model.bipedLeftArm.render(s);
        model.bipedRightLeg.render(s);
        model.bipedLeftLeg.render(s);
        model.bipedHeadwear.render(s);


        model.bipedBodyWear.render(s);
        model.bipedRightArmwear.render(s);
        model.bipedLeftArmwear.render(s);
        model.bipedRightLegwear.render(s);
        model.bipedLeftLegwear.render(s);
    }
}

@SideOnly(Side.CLIENT)
class RenderStatueItem extends TileEntityItemStackRenderer {
    private final ModelPlayer model = new ModelPlayer(0.0F, false);
    private static final ResourceLocation SKIN = new ResourceLocation(ExampleMod.PESPATRON, "textures/entity/my_skin.png");

    @Override
    public void renderByItem(ItemStack stack) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5F, 0.2F, 0.5F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.35F, 0.35F, 0.35F); // Чуть крупнее для инвентаря
        GlStateManager.translate(0.0F, -1.5F, 0.0F);

        Minecraft.getMinecraft().getTextureManager().bindTexture(SKIN);

        float s = 0.0625F;
        model.bipedHead.render(s);
        model.bipedBody.render(s);
        model.bipedRightArm.render(s);
        model.bipedLeftArm.render(s);
        model.bipedRightLeg.render(s);
        model.bipedLeftLeg.render(s);
        model.bipedHeadwear.render(s);

        model.bipedBodyWear.render(s);
        model.bipedRightArmwear.render(s);
        model.bipedLeftArmwear.render(s);
        model.bipedRightLegwear.render(s);
        model.bipedLeftLegwear.render(s);

        GlStateManager.popMatrix();
    }
}

class ItemBase extends Item {
    public ItemBase(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ExampleMod.MOD_TAB);
    }
}


@SideOnly(Side.CLIENT)
class LayerDogArmor implements LayerRenderer<EntityWolf> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.PESPATRON, "textures/entity/new_dog_armor.png");
    private final RenderWolf renderer;
    private final ModelWolf modelArmor = new ModelWolf();

    public LayerDogArmor(RenderWolf r) {
        this.renderer = r;
    }

    @Override
    public void doRenderLayer(EntityWolf wolf, float f1, float f2, float f3, float f4, float f5, float f6, float scale) {
        if (wolf.isTamed() && !wolf.isInvisible() && wolf.getEntityData().getBoolean("has_dog_armor")) {
            this.renderer.bindTexture(TEXTURE);
            this.modelArmor.setModelAttributes(this.renderer.getMainModel());
            this.modelArmor.setLivingAnimations(wolf, f1, f2, f3);
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.01F, 1.01F, 1.01F);
            if (wolf.isSitting()) GlStateManager.translate(0.0F, -0.015F, 0.0F);
            this.modelArmor.render(wolf, f1, f2, f4, f5, f6, scale);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }


    public static class Statue extends Block {
        public Statue(String name) {
            super(Material.IRON);
            setUnlocalizedName(name);
            setRegistryName(name);
            if (!name.equals("statue_block_2")) {
                setCreativeTab(ExampleMod.MOD_TAB);
            }
        }
    }
}