package dev.foxgirl.astro;

import com.google.common.base.Suppliers;
import dev.foxgirl.astro.item.AstroItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum AstroArmorMaterials implements ArmorMaterial {
    MAUVEINE("astro_remix:mauveine", 25, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 19, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 2.0F, -1.0F, Suppliers.memoize(() -> Ingredient.ofItems(AstroItems.MAUVEINE_INGOT.get())));

    private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 13);
        map.put(ArmorItem.Type.LEGGINGS, 15);
        map.put(ArmorItem.Type.CHESTPLATE, 16);
        map.put(ArmorItem.Type.HELMET, 11);
    });

    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredientSupplier;

    AstroArmorMaterials(
        String name,
        int durabilityMultiplier,
        EnumMap<ArmorItem.Type, Integer> protectionAmounts,
        int enchantability,
        SoundEvent equipSound,
        float toughness,
        float knockbackResistance,
        Supplier<Ingredient> repairIngredientSupplier
    ) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = repairIngredientSupplier;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY.get(type) * durabilityMultiplier;
    }
    @Override
    public int getProtection(ArmorItem.Type type) {
        return protectionAmounts.get(type);
    }
    @Override
    public int getEnchantability() {
        return enchantability;
    }
    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredientSupplier.get();
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public float getToughness() {
        return toughness;
    }
    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }

}
