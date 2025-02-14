package dev.foxgirl.astro.item;

import dev.foxgirl.astro.entity.AstroEntities;
import dev.foxgirl.astro.entity.GravityBombEntity;
import net.minecraft.world.World;

public class GravityBombUpItem extends GravityBombItem {

    public GravityBombUpItem(Settings settings) {
        super(settings);
    }

    @Override
    protected GravityBombEntity createBombEntity(World world) {
        return AstroEntities.GRAVITY_BOMB_UP.get().create(world);
    }

}
