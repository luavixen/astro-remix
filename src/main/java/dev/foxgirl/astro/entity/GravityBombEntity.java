package dev.foxgirl.astro.entity;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.minecraftforge.network.NetworkHooks;

public abstract class GravityBombEntity extends ThrownItemEntity {

    public GravityBombEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);

        var world = getWorld();
        if (world.isClient()) return;

        world.sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);

        var markerEntity = AstroEntities.GRAVITY_BOMB_MARKER.get().create(world);
        markerEntity.setUp(this instanceof GravityBombUpEntity);
        markerEntity.setPosition(getPos());
        world.spawnEntity(markerEntity);

        discard();
    }

}
