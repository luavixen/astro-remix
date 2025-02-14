package dev.foxgirl.astro.entity;

import dev.foxgirl.astro.Astro;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AstroEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Astro.MODID);

    public static final RegistryObject<EntityType<GravityBombDownEntity>> GRAVITY_BOMB_DOWN;
    public static final RegistryObject<EntityType<GravityBombUpEntity>> GRAVITY_BOMB_UP;
    public static final RegistryObject<EntityType<GravityBombMarkerEntity>> GRAVITY_BOMB_MARKER;

    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, Supplier<EntityType<T>> entityType) {
        return ENTITIES.register(name, entityType);
    }

    static {
        GRAVITY_BOMB_DOWN = registerEntity("gravity_bomb_down", () -> EntityType.Builder.create(GravityBombDownEntity::new, SpawnGroup.MISC).setDimensions(0.25F, 0.25F).build("gravity_bomb_down"));
        GRAVITY_BOMB_UP = registerEntity("gravity_bomb_up", () -> EntityType.Builder.create(GravityBombUpEntity::new, SpawnGroup.MISC).setDimensions(0.25F, 0.25F).build("gravity_bomb_up"));
        GRAVITY_BOMB_MARKER = registerEntity("gravity_bomb_marker", () -> EntityType.Builder.create(GravityBombMarkerEntity::new, SpawnGroup.MISC).setDimensions(0.0F, 0.0F).maxTrackingRange(0).build("gravity_bomb_marker"));
    }

}
