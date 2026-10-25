package player1.ultramod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import player1.ultramod.block.ModBlocks;


import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.getRK(ModBlocks.FLUORITE_BLOCK))
                .add(ModBlocks.getRK(ModBlocks.RAW_FLUORITE_BLOCK))
                .add(ModBlocks.getRK(ModBlocks.FLUORITE_ORE))
                .add(ModBlocks.getRK(ModBlocks.FLUORITE_DEEPSLATE_ORE))
                .add(ModBlocks.getRK(ModBlocks.FLUORITE_NETHER_ORE))

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.getRK(ModBlocks.RAW_FLUORITE_BLOCK))
                .add(ModBlocks.getRK(ModBlocks.FLUORITE_BLOCK))
                .add(ModBlocks.getRK(ModBlocks.FLUORITE_ORE))
                .add(ModBlocks.getRK(ModBlocks.FLUORITE_DEEPSLATE_ORE))
                .add(ModBlocks.getRK(ModBlocks.FLUORITE_NETHER_ORE))

    }
}
