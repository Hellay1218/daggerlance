package net.hellay.daggerlance;

import net.fabricmc.api.ModInitializer;

import net.hellay.daggerlance.innit.ModEnchantments;
import net.hellay.daggerlance.innit.ModItemGroups;
import net.hellay.daggerlance.innit.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaggerLance implements ModInitializer {
	public static final String MOD_ID = "daggerlance";
	public static final String MOD_NAME = "DaggerLance";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerFarmingExtensionsItems();
		ModItemGroups.registerModItemGroups();
		LOGGER.info("Hello fabric from " + MOD_NAME + "!");
	}
}