package dev.foxgirl.astro.item;

import dev.foxgirl.astro.entity.GravityBombEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class GravityBombItem extends Item {

    public GravityBombItem(Settings settings) {
        super(settings);
    }

    protected abstract GravityBombEntity createBombEntity(World world);

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);

        user.playSound(SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!world.isClient() && !user.getItemCooldownManager().isCoolingDown(this)) {
            var bombEntity = createBombEntity(world);
            bombEntity.setOwner(user);
            bombEntity.setItem(stack);
            bombEntity.setPosition(user.getEyePos());
            bombEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 3.0F, 0.0F);
            world.spawnEntity(bombEntity);
            user.getItemCooldownManager().set(this, 250);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));

        if (!user.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        return TypedActionResult.success(stack, world.isClient());
    }

}
