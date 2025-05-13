package me.rileycalhoun.landclaim.towns;

import me.rileycalhoun.landclaim.LandClaim;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TownsCache {

    private final LandClaim plugin;
    private final LinkedHashMap<UUID, Town> towns;

    public TownsCache(
            LandClaim plugin,
            int max_size
    ) {
        this.plugin = plugin;
        this.towns = new LinkedHashMap<>(max_size+1, 0.75f, true) {

            @Override
            protected boolean removeEldestEntry(Map.Entry<UUID, Town> eldest) {
                return size() > max_size;
            }

        };
    }

    public LinkedHashMap<UUID, Town> getTowns() {
        return towns;
    }

    public @Nullable Town getTownByName(String name) {
        return getTowns()
                .values()
                .stream()
                .filter(t -> t
                        .getName()
                        .equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public @Nullable Town getTownByUUID(UUID uniqueId) {
        return getTowns().get(uniqueId);
    }

    public @Nullable Town getTownByPlayer(OfflinePlayer player) {
        return getTowns()
                .values()
                .stream()
                .filter(t -> plugin
                                .getCitizensCache()
                                .getCitizensInTown(t)
                                .stream()
                                .anyMatch(c -> c
                                        .getPlayer()
                                        .getUniqueId()
                                        .equals(player.getUniqueId()))
                )
                .findFirst()
                .orElse(null);
    }

}
