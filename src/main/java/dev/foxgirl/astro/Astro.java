package dev.foxgirl.astro;

import dev.foxgirl.astro.entity.AstroEntities;
import dev.foxgirl.astro.entity.GravityBombMarkerEntity;
import dev.foxgirl.astro.item.AstroItems;
import dev.foxgirl.astro.item.MauveineArmorItem;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

@Mod(Astro.MODID)
public class Astro {

    public static final String MODID = "astro_remix";

    public Astro(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        AstroItems.ITEMS.register(modEventBus);
        AstroItems.ITEM_GROUPS.register(modEventBus);
        AstroEntities.ENTITIES.register(modEventBus);

        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);

        forgeEventBus.addListener(this::onServerTick);
        forgeEventBus.addListener(this::onLivingTick);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(AstroEntities.GRAVITY_BOMB_DOWN.get(), FlyingItemEntityRenderer::new);
        EntityRenderers.register(AstroEntities.GRAVITY_BOMB_UP.get(), FlyingItemEntityRenderer::new);
    }

    private static final RegistryObject<EntityAttribute> GRAVITY_ATTRIBUTE = ForgeMod.ENTITY_GRAVITY;

    private static final EntityAttributeModifier GRAVITY_MODIFIER_ARMOR_2;
    private static final EntityAttributeModifier GRAVITY_MODIFIER_ARMOR_3;
    private static final EntityAttributeModifier GRAVITY_MODIFIER_ARMOR_4;
    private static final EntityAttributeModifier GRAVITY_MODIFIER_BOMB_DOWN;
    private static final EntityAttributeModifier GRAVITY_MODIFIER_BOMB_UP;

    static {
        GRAVITY_MODIFIER_ARMOR_2 = new EntityAttributeModifier(UUID.fromString("15ad6497-6956-42fe-94cb-9d479f3348da"), "astro_remix:entity_gravity_armor_2", 0.7 - 1.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        GRAVITY_MODIFIER_ARMOR_3 = new EntityAttributeModifier(UUID.fromString("41cd179d-f57f-42c4-9dec-abdd8fd93e7f"), "astro_remix:entity_gravity_armor_3", 0.5 - 1.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        GRAVITY_MODIFIER_ARMOR_4 = new EntityAttributeModifier(UUID.fromString("1de3605f-50f9-48e7-a501-52eb28f40c2a"), "astro_remix:entity_gravity_armor_4", 0.3 - 1.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        GRAVITY_MODIFIER_BOMB_DOWN = new EntityAttributeModifier(UUID.fromString("3ef1835c-07a6-4317-9aa7-4dbf1929518f"), "astro_remix:entity_gravity_bomb_down", -0.9, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        GRAVITY_MODIFIER_BOMB_UP = new EntityAttributeModifier(UUID.fromString("c256aba9-7144-4413-aa9f-fa8f0f58b553"), "astro_remix:entity_gravity_bomb_up", 1.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    private static void setModifierActive(EntityAttributeInstance attributeInstance, EntityAttributeModifier attributeModifier, boolean isActive) {
        if (isActive) {
            if (!attributeInstance.hasModifier(attributeModifier)) {
                attributeInstance.addPersistentModifier(attributeModifier);
            }
        } else {
            if (attributeInstance.hasModifier(attributeModifier)) {
                attributeInstance.removeModifier(attributeModifier);
            }
        }
    }

    private void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            GravityBombMarkerEntity.removeDeadInstances();
        }
    }

    private void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getWorld().isClient()) return;

        int armorCount = 0;
        for (ItemStack stack : entity.getArmorItems()) {
            var item = stack.getItem();
            if (item instanceof MauveineArmorItem) armorCount++;
        }

        var gravityAttribute = GRAVITY_ATTRIBUTE.get();

        AttributeContainer attributes = entity.getAttributes();

        if (armorCount == 0) {
            if (attributes.hasAttribute(gravityAttribute)) {
                EntityAttributeInstance attributeInstance = attributes.getCustomInstance(gravityAttribute);
                setModifierActive(attributeInstance, GRAVITY_MODIFIER_ARMOR_2, false);
                setModifierActive(attributeInstance, GRAVITY_MODIFIER_ARMOR_3, false);
                setModifierActive(attributeInstance, GRAVITY_MODIFIER_ARMOR_4, false);
            }
        } else {
            EntityAttributeInstance attributeInstance = attributes.getCustomInstance(gravityAttribute);
            setModifierActive(attributeInstance, GRAVITY_MODIFIER_ARMOR_2, armorCount == 2);
            setModifierActive(attributeInstance, GRAVITY_MODIFIER_ARMOR_3, armorCount == 3);
            setModifierActive(attributeInstance, GRAVITY_MODIFIER_ARMOR_4, armorCount >= 4);
        }

        if (armorCount >= 4) {
            entity.limitFallDistance();
        }

        var markerEntities = GravityBombMarkerEntity.getMarkersAffecting(entity);
        boolean isAffectedDown = false;
        boolean isAffectedUp = false;
        if (!markerEntities.isEmpty()) {
            for (GravityBombMarkerEntity markerEntity : markerEntities) {
                if (markerEntity.isUp()) {
                    isAffectedUp = true;
                } else {
                    isAffectedDown = true;
                }
            }
        }

        if (armorCount < 2 && (isAffectedDown || isAffectedUp)) {
            EntityAttributeInstance attributeInstance = attributes.getCustomInstance(gravityAttribute);
            setModifierActive(attributeInstance, GRAVITY_MODIFIER_BOMB_DOWN, isAffectedDown);
            setModifierActive(attributeInstance, GRAVITY_MODIFIER_BOMB_UP, isAffectedUp);
        } else {
            if (attributes.hasAttribute(gravityAttribute)) {
                EntityAttributeInstance attributeInstance = attributes.getCustomInstance(gravityAttribute);
                setModifierActive(attributeInstance, GRAVITY_MODIFIER_BOMB_DOWN, false);
                setModifierActive(attributeInstance, GRAVITY_MODIFIER_BOMB_UP, false);
            }
        }
    }

}
