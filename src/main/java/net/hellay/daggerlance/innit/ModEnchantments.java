package net.hellay.daggerlance.innit;

import net.hellay.daggerlance.DaggerLance;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.hellay.daggerlance.DaggerLance.MOD_ID;

public class ModEnchantments {
//	public static final Enchantment VOLUMINOUS = registerEnchantment("voluminous" , new VoluminousEnchantment());

	private static Enchantment registerEnchantment(String name , Enchantment enchantment) {
		return Registry.register(Registries.ENCHANTMENT , new Identifier(MOD_ID , name) , enchantment);
	}

	public static void init() {
		DaggerLance.LOGGER.info("Registering enchantments for" + DaggerLance.MOD_NAME + "...");
	}
}
