package com.example.examplemod;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ExampleMod.PESPATRON)
public class WolfArmorEffects {

    // Уникальные ID для модификаторов атрибутов
    private static final UUID HEALTH_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-1234567890ab");
    private static final UUID DAMAGE_UUID = UUID.fromString("b2c3d4e5-f6a7-8901-bcde-2345678901bc");

    @SubscribeEvent
    public static void onWolfUpdate(LivingUpdateEvent event) {
        if (!(event.getEntityLiving() instanceof EntityWolf) || event.getEntityLiving().world.isRemote) return;

        EntityWolf wolf = (EntityWolf) event.getEntityLiving();
        boolean hasArmor = wolf.getEntityData().getBoolean("has_dog_armor");

        if (wolf.isTamed() && hasArmor) {
            // Применяем усиление характеристик
            applyAttributes(wolf);

            // основные эффекты брони
            wolf.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 210, 2, false, false));
            wolf.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 210, 2, false, false));
            wolf.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 210, 0, false, false));

            // Очистка от паразитов и дебаффов
            List<Potion> toRemove = new ArrayList<>();
            for (PotionEffect effect : wolf.getActivePotionEffects()) {
                String name = effect.getPotion().getRegistryName().toString();
                if (name.contains("srparasites") || effect.getPotion().isBadEffect()) {
                    toRemove.add(effect.getPotion());
                }
            }
            for (Potion p : toRemove) wolf.removePotionEffect(p);
        } else {

            removeAttributes(wolf);
        }

        //Применяем наследственные эффекты
        applyInheritance(wolf);
    }

    private static void applyAttributes(EntityWolf wolf) {
        //Здоровье
        IAttributeInstance maxHealth = wolf.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        if (maxHealth.getModifier(HEALTH_UUID) == null) {
            maxHealth.applyModifier(new AttributeModifier(HEALTH_UUID, "Armor Health Boost", 3.5D, 3));
            wolf.setHealth(wolf.getMaxHealth());
        }

        //Урон
        IAttributeInstance attackDamage = wolf.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        if (attackDamage != null && attackDamage.getModifier(DAMAGE_UUID) == null) {
            attackDamage.applyModifier(new AttributeModifier(DAMAGE_UUID, "Armor Damage Boost", 3.5D, 3));
        }
    }

    private static void removeAttributes(EntityWolf wolf) {
        IAttributeInstance maxHealth = wolf.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        if (maxHealth.getModifier(HEALTH_UUID) != null) {
            maxHealth.removeModifier(HEALTH_UUID);
            if (wolf.getHealth() > wolf.getMaxHealth()) wolf.setHealth(wolf.getMaxHealth());
        }

        IAttributeInstance attackDamage = wolf.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        if (attackDamage != null && attackDamage.getModifier(DAMAGE_UUID) != null) {
            attackDamage.removeModifier(DAMAGE_UUID);
        }
    }

    private static void applyInheritance(EntityWolf wolf) {
        String inherited = wolf.getEntityData().getString("inherited_potions");
        if (!inherited.isEmpty()) {
            String[] ids = inherited.split(",");
            for (String idStr : ids) {
                try {
                    Potion p = Potion.getPotionById(Integer.parseInt(idStr));
                    if (p != null) {
                        wolf.addPotionEffect(new PotionEffect(p, 210, 0, false, false));
                    }
                } catch (Exception e) {}
            }
        }
    }

    @SubscribeEvent
    public static void onBabySpawn(BabyEntitySpawnEvent event) {
        if (event.getParentA() instanceof EntityWolf && event.getParentB() instanceof EntityWolf) {
            EntityWolf pA = (EntityWolf) event.getParentA();
            EntityWolf pB = (EntityWolf) event.getParentB();
            EntityWolf child = (EntityWolf) event.getChild();

            List<Integer> inheritedList = new ArrayList<>();
            for (PotionEffect eff : pA.getActivePotionEffects()) {
                if (!eff.getPotion().isBadEffect()) inheritedList.add(Potion.getIdFromPotion(eff.getPotion()));
            }
            for (PotionEffect eff : pB.getActivePotionEffects()) {
                int id = Potion.getIdFromPotion(eff.getPotion());
                if (!eff.getPotion().isBadEffect() && !inheritedList.contains(id)) inheritedList.add(id);
            }

            if (!inheritedList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < inheritedList.size(); i++) {
                    sb.append(inheritedList.get(i)).append(i < inheritedList.size() - 1 ? "," : "");
                }
                child.getEntityData().setString("inherited_potions", sb.toString());
            }
        }
    }

    @SubscribeEvent
    public static void onWolfDeath(LivingDeathEvent event) {
        if (!(event.getEntityLiving() instanceof EntityWolf) || event.getEntityLiving().world.isRemote) return;
        EntityWolf wolf = (EntityWolf) event.getEntityLiving();

        if (wolf.getEntityData().getBoolean("has_dog_armor")) {
            event.setCanceled(true);
            wolf.getEntityData().setBoolean("has_dog_armor", false);
            removeAttributes(wolf); // Сбрасываем статы
            wolf.setHealth(10.0F);

            wolf.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 2, false, true));
            wolf.addPotionEffect(new PotionEffect(MobEffects.SPEED, 100, 1, false, true));

            wolf.world.playSound(null, wolf.getPosition(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            wolf.world.playSound(null, wolf.getPosition(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.NEUTRAL, 0.5F, 1.2F);

            if (wolf.world instanceof net.minecraft.world.WorldServer) {
                ((net.minecraft.world.WorldServer)wolf.world).spawnParticle(
                        net.minecraft.util.EnumParticleTypes.SMOKE_LARGE,
                        wolf.posX, wolf.posY + 0.5D, wolf.posZ,
                        15, 0.2D, 0.2D, 0.2D, 0.05D
                );
            }
        }
    }
}