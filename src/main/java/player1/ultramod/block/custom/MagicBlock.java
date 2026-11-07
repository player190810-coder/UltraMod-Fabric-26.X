
package player1.ultramod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import player1.ultramod.item.ModItems;

public class MagicBlock extends Block {

    public MagicBlock(Properties properties) {
        super(properties);
    }

    // Particles and sound when clicking the block
    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult) {

        level.addParticle(
                ParticleTypes.GLOW,
                pos.getX() + 0.5,
                pos.getY() + 1,
                pos.getZ() + 0.5,
                0, 1, 0
        );

        level.playSound(
                player,
                pos,
                SoundEvents.AMETHYST_BLOCK_RESONATE,
                SoundSource.BLOCKS,
                2f,
                1f
        );

        return InteractionResult.SUCCESS;
    }

    @Override
    public void stepOn(
            Level level,
            BlockPos pos,
            BlockState onState,
            Entity entity) {

        // Add Effect to player
        if (entity instanceof Player player) {
            player.addEffect(
                    new MobEffectInstance(MobEffects.GLOWING, 300)
            );
        }

        // Change Item(s) to other Items
        if (!level.isClientSide && entity instanceof ItemEntity itemEntity) {

            if (isValidItem(itemEntity.getItem())) {
                transformItems(level, pos);
            }
        }

        super.stepOn(level, pos, onState, entity);
    }

    // Transform iron and fluorite into diamonds
    private void transformItems(Level level, BlockPos pos) {

        // Search for nearby items
        AABB searchArea = new AABB(
                pos.getX() - 0.2,
                pos.getY() + 0.7,
                pos.getZ() - 0.2,
                pos.getX() + 1.2,
                pos.getY() + 1.5,
                pos.getZ() + 1.2
        );

        var items = level.getEntitiesOfClass(
                ItemEntity.class,
                searchArea
        );

        ItemEntity iron = null;
        ItemEntity fluorite = null;

        for (ItemEntity item : items) {

            if (item.getItem().is(Items.IRON_INGOT)) {
                iron = item;
            }

            if (item.getItem().is(ModItems.FLUORITE)) {
                fluorite = item;
            }
        }

        // Check if both ingredients were found
        if (iron == null || fluorite == null) {
            return;
        }

        // Consume ingredients
        iron.getItem().shrink(1);
        fluorite.getItem().shrink(1);

        if (iron.getItem().isEmpty()) {
            iron.discard();
        }

        if (fluorite.getItem().isEmpty()) {
            fluorite.discard();
        }

        // Create diamond
        ItemEntity diamond = new ItemEntity(
                level,
                pos.getX() + 0.5,
                pos.getY() + 1,
                pos.getZ() + 0.5,
                new ItemStack(Items.DIAMOND)
        );

        level.addFreshEntity(diamond);

        // Play transformation sound
        level.playSound(
                null,
                pos,
                SoundEvents.AMETHYST_BLOCK_RESONATE,
                SoundSource.BLOCKS,
                2f,
                1f
        );

        // Spawn the same particles used when clicking the block
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.GLOW,
                    pos.getX() + 0.5,
                    pos.getY() + 1,
                    pos.getZ() + 0.5,
                    20,
                    0.3, 0.3, 0.3,
                    0.1
            );
        }
    }

    // Check valid items
    private boolean isValidItem(ItemStack item) {
        return item.is(ModItems.FLUORITE)
                || item.is(Items.IRON_INGOT);
    }
}
