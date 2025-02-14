package dev.foxgirl.astro;

import com.google.common.base.Suppliers;
import dev.foxgirl.astro.item.AstroItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public enum AstroToolMaterials implements ToolMaterial {
    MAUVEINE(1, 1561, 1.0F, 1.0F, 1, Suppliers.memoize(() -> Ingredient.ofItems(AstroItems.MAUVEINE_INGOT.get())));

    private final int miningLevel;
    private final int durability;
    private final float miningSpeedMultiplier;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    AstroToolMaterials(int miningLevel, int durability, float miningSpeedMultiplier, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.durability = durability;
        this.miningSpeedMultiplier = miningSpeedMultiplier;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }
    @Override
    public int getDurability() {
        return durability;
    }
    @Override
    public float getMiningSpeedMultiplier() {
        return miningSpeedMultiplier;
    }
    @Override
    public float getAttackDamage() {
        return attackDamage;
    }
    @Override
    public int getEnchantability() {
        return enchantability;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }

}
