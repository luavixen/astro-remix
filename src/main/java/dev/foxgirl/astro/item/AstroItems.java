package dev.foxgirl.astro.item;

import dev.foxgirl.astro.Astro;
import dev.foxgirl.astro.AstroArmorMaterials;
import dev.foxgirl.astro.AstroToolMaterials;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AstroItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Astro.MODID);

    public static final RegistryObject<Item> MAUVEINE_INGOT;
    public static final RegistryObject<ArmorItem> MAUVEINE_HELMET;
    public static final RegistryObject<ArmorItem> MAUVEINE_CHESTPLATE;
    public static final RegistryObject<ArmorItem> MAUVEINE_LEGGINGS;
    public static final RegistryObject<ArmorItem> MAUVEINE_BOOTS;
    public static final RegistryObject<SwordItem> MAUVEINE_SABRE;
    public static final RegistryObject<GravityBombDownItem> GRAVITY_BOMB_DOWN;
    public static final RegistryObject<GravityBombUpItem> GRAVITY_BOMB_UP;

    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    static {
        MAUVEINE_INGOT = registerItem("mauveine_ingot", () -> new Item(new Item.Settings()));
        MAUVEINE_HELMET = registerItem("mauveine_helmet", () -> new MauveineArmorItem(AstroArmorMaterials.MAUVEINE, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1)));
        MAUVEINE_CHESTPLATE = registerItem("mauveine_chestplate", () -> new MauveineArmorItem(AstroArmorMaterials.MAUVEINE, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1)));
        MAUVEINE_LEGGINGS = registerItem("mauveine_leggings", () -> new MauveineArmorItem(AstroArmorMaterials.MAUVEINE, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1)));
        MAUVEINE_BOOTS = registerItem("mauveine_boots", () -> new MauveineArmorItem(AstroArmorMaterials.MAUVEINE, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1)));
        MAUVEINE_SABRE = registerItem("mauveine_sabre", () -> new SwordItem(AstroToolMaterials.MAUVEINE, 4, -1.8F, new Item.Settings().maxCount(1)));
        GRAVITY_BOMB_DOWN = registerItem("gravity_bomb_down", () -> new GravityBombDownItem(new Item.Settings()));
        GRAVITY_BOMB_UP = registerItem("gravity_bomb_up", () -> new GravityBombUpItem(new Item.Settings()));
    }

    public static final DeferredRegister<ItemGroup> ITEM_GROUPS = DeferredRegister.create(Registries.ITEM_GROUP.getKey(), Astro.MODID);

    public static final RegistryObject<ItemGroup> ASTRO_ITEM_GROUP;

    static {
        ASTRO_ITEM_GROUP = ITEM_GROUPS.register("astro", () -> {
            return ItemGroup
                .builder()
                .displayName(Text.translatable("itemGroup.astro_remix"))
                .icon(() -> MAUVEINE_INGOT.get().getDefaultStack())
                .entries((context, collector) -> {
                    collector.add(MAUVEINE_INGOT.get());
                    collector.add(MAUVEINE_HELMET.get());
                    collector.add(MAUVEINE_CHESTPLATE.get());
                    collector.add(MAUVEINE_LEGGINGS.get());
                    collector.add(MAUVEINE_BOOTS.get());
                    collector.add(MAUVEINE_SABRE.get());
                    collector.add(GRAVITY_BOMB_DOWN.get());
                    collector.add(GRAVITY_BOMB_UP.get());
                })
                .build();
        });
    }

}
