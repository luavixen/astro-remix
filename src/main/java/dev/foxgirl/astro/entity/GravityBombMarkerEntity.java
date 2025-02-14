package dev.foxgirl.astro.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GravityBombMarkerEntity extends MarkerEntity {

    private static final ArrayList<GravityBombMarkerEntity> INSTANCES = new ArrayList<>();

    public static void removeDeadInstances() {
        INSTANCES.removeIf(GravityBombMarkerEntity::isRemoved);
    }

    public static List<GravityBombMarkerEntity> getMarkersAffecting(Entity entity) {
        return INSTANCES.isEmpty() ? List.of() : getMarkersAffectingImpl(entity);
    }

    private static List<GravityBombMarkerEntity> getMarkersAffectingImpl(Entity entity) {
        ArrayList<GravityBombMarkerEntity> markerEntities = null;

        for (GravityBombMarkerEntity markerEntity : INSTANCES) {
            if (markerEntity.getWorld() != entity.getWorld()) continue;
            if (markerEntity.squaredDistanceTo(entity) > 25.0) continue;
            if (markerEntities == null) {
                markerEntities = new ArrayList<>(2);
            }
            markerEntities.add(markerEntity);
        }

        return markerEntities == null ? List.of() : markerEntities;
    }

    private boolean isUp;

    public GravityBombMarkerEntity(EntityType<?> type, World world) {
        super(type, world);
        INSTANCES.add(this);
    }

    public boolean isUp() {
        return isUp;
    }
    public void setUp(boolean up) {
        isUp = up;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        isUp = nbt.getBoolean("isUp");
    }
    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("isUp", isUp);
    }

    @Override
    public void tick() {
        if (getWorld() instanceof ServerWorld world) {
            if (age >= 200) {
                discard();
            } else {
                world.spawnParticles(
                    new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.AMETHYST_BLOCK.getDefaultState()),
                    getX(), getY(), getZ(), 30, 4.0, 4.0, 4.0, 1.0
                );
                if (world.getRandom().nextFloat() <= 0.2) {
                    world.playSound(
                        (PlayerEntity) null,
                        getX() - 4.0 + world.getRandom().nextFloat() * 8.0,
                        getY() - 4.0 + world.getRandom().nextFloat() * 8.0,
                        getZ() - 4.0 + world.getRandom().nextFloat() * 8.0,
                        SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                        SoundCategory.NEUTRAL,
                        1.0F,
                        world.getRandom().nextFloat() * 0.4F + 0.8F
                    );
                }
            }
        }
    }

}
