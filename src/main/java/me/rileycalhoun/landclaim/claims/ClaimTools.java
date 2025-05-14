package me.rileycalhoun.landclaim.claims;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClaimTools {

    private static ItemStack claimWand = createItem(
            Material.GOLDEN_SHOVEL,
            ChatColor.GREEN + "Claim Wand",
            new String[] {
                    ChatColor.GREEN + "Right click " + ChatColor.GRAY + "to select one corner.",
                    ChatColor.GREEN + "Left click " + ChatColor.GRAY + " to select the other."
            },
            true
    );


    private static ItemStack createItem(Material material, String name, String[] lore, boolean unbreakable) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return null;

        meta.setDisplayName(name);
        meta.setLore(Arrays.stream(lore).toList());
        meta.setUnbreakable(unbreakable);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getClaimWand() {
        return claimWand;
    }

}
