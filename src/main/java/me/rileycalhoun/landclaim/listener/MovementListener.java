package me.rileycalhoun.landclaim.listener;

import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MovementListener implements Listener {

    private final Map<Player, Optional<Town>> movementTracker = new HashMap<>();
    private final TownsCache townsCache;

    public MovementListener(final TownsCache townsCache) {
        this.townsCache = townsCache;
    }

//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent event) {
//        if (event.getTo() == null) return;
//
//        Player player = event.getPlayer();
//        Chunk chunk = event.getTo().getChunk();
//        Optional<Town> currentTown = townsCache.getClaimManager().getChunkOwner(chunk);
//
//        if (!movementTracker.containsKey(player)) {
//            movementTracker.put(player, currentTown);
//            return;
//        }
//
//        Optional<Town> storedTown = movementTracker.get(player);
//
//        if(!storedTown.equals(currentTown)) {
//            movementTracker.replace(player, currentTown);
//            if (currentTown.isPresent()) {
//                PlayerEnterTownEvent newEvent = new PlayerEnterTownEvent(
//                        event.getPlayer(), currentTown.get()
//                );
//
//                Bukkit.getServer().getPluginManager().callEvent(newEvent);
//                if (newEvent.isCancelled()) {
//                    event.setCancelled(true);
//                    return;
//                }
//
//                player.sendTitle(ChatColor.GREEN + currentTown.get().getName(),
//                        ChatColor.GRAY + currentTown.get().getDescription(),
//                        10, 60, 10);
//            } else {
//                // Stored tow must be present if they are not equal; Leaving town.
//                assert storedTown.isPresent();
//
//                PlayerExitTownEvent newEvent = new PlayerExitTownEvent(
//                        event.getPlayer(), storedTown.get()
//                );
//
//                Bukkit.getServer().getPluginManager().callEvent(newEvent);
//                if (newEvent.isCancelled()) {
//                    event.setCancelled(true);
//                    return;
//                }
//
//                player.sendTitle(ChatColor.DARK_GREEN + "Wilderness",
//                        ChatColor.GRAY + "A vast, unexplored territory",
//                        10, 60, 10);
//            }
//        }
//    }

}
