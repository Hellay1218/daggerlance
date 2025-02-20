package net.hellay.daggerlance.util;

import net.hellay.daggerlance.item.DaggerLanceItem;
import net.minecraft.item.ItemStack;

public class SkinUtil {

    public static void setSkin(ItemStack stack, String skin) {
        stack.getOrCreateNbt().putString("skin" , skin);
    }

    public static String getSkin(ItemStack stack) {
        boolean isSkin = false;
        for (int i = 0; i < DaggerLanceItem.Skin.values().length; i++) {
            if(DaggerLanceItem.Skin.values()[i].getName().equals(stack.getOrCreateNbt().getString("skin"))){
                isSkin = true;
            }
        }
        if (!isSkin){
            setSkin(stack,"daggerlance:daggerlance");
        }
        return stack.getOrCreateNbt().getString("skin");
    }

}
