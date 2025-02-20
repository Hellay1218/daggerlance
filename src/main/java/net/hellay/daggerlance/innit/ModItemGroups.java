package net.hellay.daggerlance.innit;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.hellay.daggerlance.DaggerLance;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.hellay.daggerlance.DaggerLance.MOD_NAME;

public class ModItemGroups {
	static {
		ItemGroup itemGroup = FabricItemGroup.builder()
				.displayName(Text.translatable("itemgroup.daggerlance.daggerlance_group"))
				.icon(() -> new ItemStack(ModItems.DAGGERLANCE))
				.entries((displayContext, entries) -> {
					entries.add(ModItems.DAGGERLANCE);
//					entries.add(ModItems.XP_BOTTLE);
//					entries.add(ModItems.XP_JAR);
//					entries.add(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.VOLUMINOUS, ModEnchantments.VOLUMINOUS.getMaxLevel())) ,ItemGroup.StackVisibility.PARENT_TAB_ONLY);
//					displayContext.lookup().getOptionalWrapper(RegistryKeys.ENCHANTMENT).ifPresent((wrapper) -> {
//						addAllLevelEnchantedBooks(entries , ItemGroup.StackVisibility.SEARCH_TAB_ONLY);
//					});

				})
				.build();

		Registry.register(
				Registries.ITEM_GROUP,
				Identifier.of(DaggerLance.MOD_ID, "daggerlance_group"),
				itemGroup
		);
	}

	//picked this up from the ingredients item group , so useful! (adds every level enchantment)
//	private static void addAllLevelEnchantedBooks(ItemGroup.Entries entries, ItemGroup.StackVisibility stackVisibility) {
//		IntStream.rangeClosed(ModEnchantments.VOLUMINOUS.getMinLevel(), ModEnchantments.VOLUMINOUS.getMaxLevel()).mapToObj((level) -> {
//			return EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.VOLUMINOUS, level));
//		}).forEach((stack) -> {entries.add(stack, stackVisibility);});
//	}

	public static void registerModItemGroups() {
		DaggerLance.LOGGER.info("Registering items groups for " + MOD_NAME + "...");
	}
}

