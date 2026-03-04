package com.example.examplemod.main;

import com.example.examplemod.EnergyBlocks.TileEntityCable;
import com.example.examplemod.EnergyBlocks.TileEntityTransformer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraft.util.EnumFacing;
import com.example.examplemod.IFE.IEStorage;
import com.example.examplemod.IFE.IIEStorage;
import com.example.examplemod.machines.EnergyStorage.TileEntityStatue;
import com.example.examplemod.discord.DiscordManager;
import com.example.examplemod.machines.EnergyStorage.TileEntityEnergyStorage;
import com.example.examplemod.machines.handler.GuiHandler;
import com.example.examplemod.machines.BlastFurnace.TileEntityBlastFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import static com.example.examplemod.main.EcompItems.*;

@Mod(modid = ExampleMod.examplemod, name = ExampleMod.NAME, version = ExampleMod.VERSION)
@Mod.EventBusSubscriber
public class ExampleMod {
    public static SimpleNetworkWrapper network;
    public static Item INFINITE_BATTERY;
    public static final String examplemod = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.1";

    @Mod.Instance(examplemod)
    public static ExampleMod instance;

    public static final CreativeTabs MOD_TAB = new CreativeTabs("tab_pespatron") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(EcompItems.DOG_ARMOR);
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        // УДАЛЕНО: Лишние присваивания (STEEL_INGOT = ...), так как они теперь в EcompItems

        GameRegistry.registerTileEntity(TileEntityCable.class, new ResourceLocation(examplemod, "tile_cable"));
        GameRegistry.registerTileEntity(TileEntityTransformer.class, new ResourceLocation(examplemod, "tile_transformer"));
        GameRegistry.registerTileEntity(TileEntityBlastFurnace.class, new ResourceLocation(examplemod, "blast_furnace"));
        GameRegistry.registerTileEntity(TileEntityEnergyStorage.class, new ResourceLocation(examplemod, "energy_storage"));
        GameRegistry.registerTileEntity(TileEntityStatue.class, new ResourceLocation(examplemod, "statue_tile"));

        CapabilityManager.INSTANCE.register(IIEStorage.class, new Capability.IStorage<IIEStorage>() {
            @Override
            public net.minecraft.nbt.NBTBase writeNBT(Capability<IIEStorage> capability, IIEStorage instance, EnumFacing side) {
                return new net.minecraft.nbt.NBTTagInt(instance.getEnergyStored());
            }
            @Override
            public void readNBT(Capability<IIEStorage> capability, IIEStorage instance, EnumFacing side, net.minecraft.nbt.NBTBase nbt) {
                if (instance instanceof IEStorage) {
                    ((IEStorage) instance).setEnergy(((net.minecraft.nbt.NBTTagInt) nbt).getInt());
                }
            }
        }, () -> new IEStorage(1000));

        if (event.getSide().isClient()) {
            DiscordManager.start();
        }
    }

    @CapabilityInject(IIEStorage.class)
    public static Capability<IIEStorage> IE_ENERGY = null;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModRecipes.init();
        GameRegistry.registerWorldGenerator(new OreGen(), 0);

        // Использование EcompItems для регистрации в OreDictionary
        net.minecraftforge.oredict.OreDictionary.registerOre("ingotSteel", EcompItems.STEEL_INGOT);
        net.minecraftforge.oredict.OreDictionary.registerOre("ingotTitanium", EcompItems.TITANIUM_INGOT);
        net.minecraftforge.oredict.OreDictionary.registerOre("ingotUranium", EcompItems.URANIUM_INGOT);
    }

    @SideOnly(Side.CLIENT)
    private void registerDogLayer() {
        RenderWolf render = (RenderWolf) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntityWolf.class);
        if (render != null) render.addLayer(new LayerDogArmor(render));
    }

    @SideOnly(Side.CLIENT)
    private static void initClientRenders() {
        EcompItems.ITEM_STATUE.setTileEntityItemStackRenderer(new RenderStatueItem());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStatue.class, new RenderStatueBlock());
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof EntityWolf) {
            EntityWolf wolf = (EntityWolf) event.getTarget();
            if (wolf.isTamed() && event.getItemStack().getItem() == EcompItems.DOG_ARMOR) {
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


@SideOnly(Side.CLIENT)
class RenderStatueBlock extends TileEntitySpecialRenderer<TileEntityStatue> {

    private final ModelPlayer model = new ModelPlayer(0.0F, false);
    private static final ResourceLocation SKIN = new ResourceLocation(ExampleMod.examplemod, "textures/entity/my_skin.png");

    @Override
    public void render(TileEntityStatue te, double x, double y, double z, float pt, int ds, float a) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(x + 0.5, y, z + 0.5);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);

        GlStateManager.scale(0.3F, 0.3F, 0.3F);

        bindTexture(SKIN);

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
    private static final ResourceLocation SKIN = new ResourceLocation(ExampleMod.examplemod , "textures/entity/my_skin.png");

    @Override
    public void renderByItem(ItemStack stack) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5F, 0.2F, 0.5F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.35F, 0.35F, 0.35F);
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
    private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.examplemod, "textures/entity/new_dog_armor.png");
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

            super(net.minecraft.block.material.Material.CLAY);
            this.setRegistryName(name);
            this.setHardness(3.0F);
            this.setResistance(10.0F);
            this.setSoundType(SoundType.METAL);
        }
    }
}