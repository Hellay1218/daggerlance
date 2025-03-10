package net.hellay.daggerlance;

import com.mojang.datafixers.types.templates.Tag;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.hellay.daggerlance.innit.ModEnchantments;
import net.hellay.daggerlance.innit.ModItemGroups;
import net.hellay.daggerlance.innit.ModItems;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaggerLance implements ModInitializer {
	public static final String MOD_ID = "daggerlance";
	public static final String MOD_NAME = "DaggerLance";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final TagKey<Item> DropingItems = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "drops_daggerlance"));

	public static final GameRules.Key<GameRules.BooleanRule> SHOULD_REQUIRE_NETHERITE_TO_DROP_DAGGERLANCE =GameRuleRegistry.register("requireNetheriteToDropDaggerlance" , GameRules.Category.DROPS , GameRuleFactory.createBooleanRule(true));

	@Override
	public void onInitialize() {
		ModItems.registerFarmingExtensionsItems();
		ModItemGroups.registerModItemGroups();
		LOGGER.info("Hello fabric from " + MOD_NAME + "!");

		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
			if(killedEntity instanceof WitherSkeletonEntity witherSkeleton && entity instanceof PlayerEntity player && witherSkeleton.getMainHandStack().getItem() == ModItems.DAGGERLANCE){
				ItemStack playerWeapon = player.getMainHandStack();
				if((!world.isClient() && world.getGameRules().getBoolean(SHOULD_REQUIRE_NETHERITE_TO_DROP_DAGGERLANCE)) && (playerWeapon.isIn(DropingItems))){
					witherSkeleton.dropStack(witherSkeleton.getMainHandStack());
				}

				else if ((!world.isClient() && !world.getGameRules().getBoolean(SHOULD_REQUIRE_NETHERITE_TO_DROP_DAGGERLANCE))){
					witherSkeleton.dropStack(witherSkeleton.getMainHandStack());
				}
			}
		});

	}
}