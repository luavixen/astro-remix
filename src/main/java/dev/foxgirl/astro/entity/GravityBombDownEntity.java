package dev.foxgirl.astro.entity;

import dev.foxgirl.astro.item.AstroItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class GravityBombDownEntity extends GravityBombEntity {

    public GravityBombDownEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected Item getDefaultItem() {
        return AstroItems.GRAVITY_BOMB_DOWN.get();
    }

}
