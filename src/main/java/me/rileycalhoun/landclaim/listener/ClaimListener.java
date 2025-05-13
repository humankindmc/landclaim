package me.rileycalhoun.landclaim.listener;

import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Optional;

public class ClaimListener implements Listener {

    private final TownsCache townsCache;

    public ClaimListener(final TownsCache townsCache) {
        this.townsCache = townsCache;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();

        Optional<Town> town = townsCache.getClaimManager().getChunkOwner(chunk);
        if (town.isPresent() && !town.get().isCitizen(player)) {
            player.sendMessage(ChatColor.RED + "You must be a member of this town to break in their claim.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Action action = event.getAction();
        if (action != Action.LEFT_CLICK_BLOCK && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        assert event.getClickedBlock() != null;

        Player player = event.getPlayer();
        Chunk chunk = event.getClickedBlock().getChunk();
        Optional<Town> chunkOwner = townsCache.getClaimManager().getChunkOwner(chunk);

        if (chunkOwner.isEmpty()) {
            return;
        }

        if(chunkOwner.get().isCitizen(player)) {
            return;
        }

        event.setCancelled(true);
        player.sendMessage(ChatColor.RED + "You can't do that here!");
    }

}
