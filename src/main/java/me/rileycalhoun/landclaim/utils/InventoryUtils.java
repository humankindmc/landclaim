package me.rileycalhoun.landclaim.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    public static boolean isInInventory(Inventory inventory, ItemStack itemStack) {
        for (ItemStack stack : inventory) {
            if (stack.isSimilar(itemStack)) {
                return true;
            }
        }

        return false;
    }

}
