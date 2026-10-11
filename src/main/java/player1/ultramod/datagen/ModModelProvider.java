package player1.ultramod.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import player1.ultramod.block.ModBlocks;
import player1.ultramod.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createTrivialCube(ModBlocks.FLUORITE_BLOCK);
        blockModelGenerators.createTrivialCube(ModBlocks.RAW_FLUORITE_BLOCK);
        blockModelGenerators.createTrivialCube(ModBlocks.FLUORITE_ORE);
        blockModelGenerators.createTrivialCube(ModBlocks.FLUORITE_DEEPSLATE_ORE);
        blockModelGenerators.createTrivialCube(ModBlocks.FLUORITE_NETHER_ORE);
        blockModelGenerators.createTrivialCube(ModBlocks.FLUORITE_END_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModItems.FLUORITE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.RAW_FLUORITE, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.CHISEL, ModelTemplates.FLAT_HANDHELD_ITEM );

    }
}
