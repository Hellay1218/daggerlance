package net.hellay.daggerlance.innit;

import net.hellay.daggerlance.DaggerLance;
import net.hellay.daggerlance.item.DaggerLanceItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.hellay.daggerlance.DaggerLance.MOD_ID;

public class ModItems {
//	public static final Item XP_SCROLL = registerItem("xp_scroll", new Item(new Item.Settings().rarity(Rarity.RARE)));
//	public static final Item XP_BOTTLE = registerItem("xp_bottle", new XpContainerItemClass(new Item.Settings(),
//			ModConfig.xpBottleMaxXp));
//	public static final Item XP_JAR = registerItem("xp_jar", new XpContainerItemClass(new Item.Settings(),
//			ModConfig.xpJarMaxXp));

	public static final Item DAGGERLANCE = registerItem("daggerlance", new DaggerLanceItem(ToolMaterials.NETHERITE , 3, -2.7F , 1.0F , new Item.Settings()));

	private static Item registerItem(String name, Item item) {
		return Registry.register(Registries.ITEM, new Identifier(MOD_ID , name), item);
	}

	public static void registerFarmingExtensionsItems() {
		DaggerLance.LOGGER.info("Registering items for" + DaggerLance.MOD_NAME + "...");
	}
}
