package player1.ultramod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class MagicBlock extends Block {
    public MagicBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        // Verifica se a entidade que pisou é um ser vivo (como o Jogador)
        if (entity instanceof LivingEntity livingEntity) {
            // Aplica o efeito de Glowing (com brilho)
            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, false, false, true));
        }

        // Lógica de partículas e deteção na área superior do bloco
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            AABB box = new AABB(pos.above());
            List<Item> itemsOnBlock = level.getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class, box)
                    .stream().map(net.minecraft.world.entity.item.ItemEntity::getItem).map(ItemStack::getItem).toList();

            // Exemplo de verificação combinada de itens
            if (itemsOnBlock.contains(Items.IRON_INGOT)) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, 
                    pos.getX() + 0.5D, pos.getY() + 1.1D, pos.getZ() + 0.5D, 
                    5, 0.2D, 0.1D, 0.2D, 0.0D);
            }
        }

        super.stepOn(level, pos, state, entity);
    }
}
