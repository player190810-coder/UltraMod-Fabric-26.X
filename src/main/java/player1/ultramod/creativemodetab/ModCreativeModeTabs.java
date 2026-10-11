package player1.ultramod.creativemodetab;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import player1.ultramod.UltraMod;
import player1.ultramod.block.ModBlocks;
import player1.ultramod.item.ModItems;

public class ModCreativeModeTabs {
    public static final CreativeModeTab FLUORITE_ITEM_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(UltraMod.MOD_ID, "fluorite_items"),
            FabricCreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FLUORITE))
                    .title(Component.translatable("creativemodetab.ultramod.fluorite_items"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.FLUORITE);
                        output.accept(ModItems.RAW_FLUORITE);

                        output.accept(ModItems.CHISEL);


                    }).build());


        public static final CreativeModeTab FLUORITE_BLOCK_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                Identifier.fromNamespaceAndPath(UltraMod.MOD_ID, "fluorite_blocks"),
                FabricCreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.FLUORITE_BLOCK))
                        .title(Component.translatable("creativemodetab.ultramod.fluorite_blocks"))
                        .displayItems((parameters, output) -> {
                            output.accept(ModBlocks.FLUORITE_BLOCK);
                            output.accept(ModBlocks.RAW_FLUORITE_BLOCK);
                            output.accept(ModBlocks.FLUORITE_ORE);
                            output.accept(ModBlocks.FLUORITE_DEEPSLATE_ORE);
                            output.accept(ModBlocks.FLUORITE_NETHER_ORE);
                            output.accept(ModBlocks.FLUORITE_END_ORE);


                        }).build());

    public static void registerModCreativeModeTabs() {
        UltraMod.LOGGER.info("Registering Creative Mode Tabs for " + UltraMod.MOD_ID);
    }
}
