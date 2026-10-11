package player1.ultramod;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player1.ultramod.block.ModBlocks;
import player1.ultramod.creativemodetab.ModCreativeModeTabs;
import player1.ultramod.item.ModItems;

public class UltraMod implements ModInitializer {
	public static final String MOD_ID = "ultramod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModCreativeModeTabs.registerModCreativeModeTabs();
		ModBlocks.registerModBlocks();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
