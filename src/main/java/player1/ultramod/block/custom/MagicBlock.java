
package player1.ultramod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Shapes;
import net.minecraft.world.phys.VoxelShape;
import player1.ultramod.item.ModItems;

public class MagicBlock extends Block {

    public MagicBlock(Properties properties) {
        super(properties);
    }

    // Expand the entity detection area above the block
    @Override
    protected VoxelShape getEntityInsideCollisionShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            Entity entity) {

        return Shapes.box(0, 0, 0, 1, 1.5, 1);
    }

    // Detect entities inside the block area
    @Override
    protected void entityInside(
            BlockState state,
            Level level,
            BlockPos pos,
            Entity entity,
            InsideBlockEffectApplier effectApplier,
            boolean isPrecise) {

        // Add glowing effect to players
        if (entity instanceof Player player) {
            if (!level.isClientSide()) {
                player.addEffect(
                        new MobEffectInstance(MobEffects.GLOWING, 300)
                );
            }
        }

        // Check dropped items on top of the block
        if (!level.isClientSide() && entity instanceof ItemEntity) {
            transformItems(level, pos);
        }

        super.entityInside(
                state,
                level,
                pos,
                entity,
                effectApplier,
                isPrecise
        );
    }

    // Particles and sound when clicking the block
    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult) {

        if (level.isClientSide()) {
            level.addParticle(
                    ParticleTypes.GLOW,
                    pos.getX() + 0.5,
                    pos.getY() + 1,
                    pos.getZ() + 0.5,
                    0, 1, 0
            );
        }

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

    // Transform diamond and netherite into fluorite
    private void transformItems(Level level, BlockPos pos) {

        // Search only on top of the block
        AABB searchArea = new AABB(
                pos.getX(),
                pos.getY() + 0.95,
                pos.getZ(),
                pos.getX() + 1,
                pos.getY() + 1.5,
                pos.getZ() + 1
        );

        var items = level.getEntitiesOfClass(
                ItemEntity.class,
                searchArea
        );

        ItemEntity diamond = null;
        ItemEntity netherite = null;

        // Find both ingredients
        for (ItemEntity item : items) {

            if (item.getItem().is(Items.DIAMOND)) {
                diamond = item;
            }

            if (item.getItem().is(Items.NETHERITE_INGOT)) {
                netherite = item;
            }
        }

        // Both ingredients must be present
        if (diamond == null || netherite == null) {
            return;
        }

        // Consume one of each ingredient
        diamond.getItem().shrink(1);
        netherite.getItem().shrink(1);

        if (diamond.getItem().isEmpty()) {
            diamond.discard();
        }

        if (netherite.getItem().isEmpty()) {
            netherite.discard();
        }

        // Create one fluorite
        ItemEntity fluorite = new ItemEntity(
                level,
                pos.getX() + 0.5,
                pos.getY() + 1.1,
                pos.getZ() + 0.5,
                new ItemStack(ModItems.FLUORITE)
        );

        level.addFreshEntity(fluorite);

        // Play transformation sound
        level.playSound(
                null,
                pos,
                SoundEvents.AMETHYST_BLOCK_RESONATE,
                SoundSource.BLOCKS,
                2f,
                1f
        );

        // Spawn transformation particles
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
}
