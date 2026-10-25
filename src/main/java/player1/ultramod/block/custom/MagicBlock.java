package player1.ultramod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
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

import java.util.List;

public class MagicBlock extends Block {
    public MagicBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        level.addParticle(ParticleTypes.GLOW, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 1, 0);
        level.playSound(player, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 2f, 1f);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState onState, Entity entity) {
        // 1. Se quem pisou foi um Jogador, aplica o efeito de Glow
        if (entity instanceof Player player) {
            player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 300));
        }

        // 2. Se quem caiu em cima foi um Item, verifica a combinação dos dois itens
        if (!level.isClientSide() && entity instanceof ItemEntity) {
            AABB box = new AABB(pos.above());
            List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, box);

            boolean hasFluorite = false;
            boolean hasIron = false;

            for (ItemEntity itemEntity : items) {
                if (itemEntity.getItem().is(ModItems.FLUORITE)) {
                    hasFluorite = true;
                }
                if (itemEntity.getItem().is(Items.IRON_INGOT)) {
                    hasIron = true;
                }
            }

            // Se ambos os itens estiverem reunidos, dispara a partícula
            if (hasFluorite && hasIron) {
                level.addParticle(ParticleTypes.HAPPY_VILLAGER,
                        pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5,
                        0.0, 0.1, 0.0);
            }
        }

        super.stepOn(level, pos, onState, entity);
    }
}
