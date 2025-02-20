package net.hellay.daggerlance;

import net.fabricmc.api.ClientModInitializer;
import net.hellay.daggerlance.innit.ModItems;
import net.hellay.daggerlance.item.DaggerLanceItem;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;

import static net.hellay.daggerlance.DaggerLance.MOD_ID;

public class DaggerLanceClient implements ClientModInitializer {
	public static DefaultedList<Pair<Item, Pair<ModelIdentifier , ModelIdentifier>>> SKIN_MODELS = DefaultedList.of();

	@Override
	public void onInitializeClient() {
		DaggerLanceItem.Skin[] values = DaggerLanceItem.Skin.values();
		for (int i = 0; i < DaggerLanceItem.Skin.values().length; i++) {
			DaggerLanceItem.Skin skin = values[i];
			addSkinModel(ModItems.DAGGERLANCE , skin.getName());
		}
//	addSkinModel(ModItems.DAGGERLANCE , "daggerlance");
//	addSkinModel(ModItems.DAGGERLANCE , "gold_daggerlance");
	}

	public static void addSkinModel(Item item, String name) {
		Pair<Item, Pair<ModelIdentifier , ModelIdentifier>> pair = new Pair<>(item, new Pair<>(new ModelIdentifier(new Identifier(name), "inventory") , new ModelIdentifier(new Identifier(name + "_handheld"), "inventory")));
		SKIN_MODELS.add(pair);
	}


}