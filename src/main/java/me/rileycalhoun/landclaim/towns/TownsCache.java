package me.rileycalhoun.landclaim.towns;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
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

            @Override
            public boolean remove(Object key, Object value) {
                Town town = (Town) value;

                try {
                    plugin.getLogger().info("Saving town with UUID " + town.getUniqueId());
                    plugin.getTownsFile().saveTown(town);
                } catch (IOException e) {
                    plugin.getLogger().info("Could not save citizen with UUID " + town.getUniqueId() + ": " + e.getMessage());
                }

                return super.remove(key, value);
            }

        };
    }

    public LinkedHashMap<UUID, Town> getTowns() {
        return towns;
    }

    public @Nullable Town getTownByName(String name) {
        for (Town town : towns.values()) {
            if (town.getName().equalsIgnoreCase(name)) {
                return town;
            }
        }

        return plugin.getTownsFile().getTownByName(name);
    }

    public @Nullable Town getTownByUUID(UUID uniqueId) {
        if (towns.containsKey(uniqueId)) {
            return towns.get(uniqueId);
        }

        Town town = plugin.getTownsFile().getTownByUniqueId(uniqueId);
        if (town != null) {
            towns.put(uniqueId, town);
        }

        return town;
    }

    private UUID generateUniqueId() {
        UUID uuid = UUID.randomUUID();
        while (getTownByUUID(uuid) != null) {
            uuid = UUID.randomUUID();
        }

        return uuid;
    }

    public Town createTown(String name) {
        if (getTownByName(name) != null) return null;
        Town town = new Town(generateUniqueId(), name);
        // TODO: TownCreateEvent

        towns.put(town.getUniqueId(), town);
        return town;
    }

    public void disbandTown(Town town) {
        List<Citizen> citizens = plugin.getCitizensCache()
                        .getCitizensInTown(town);

        citizens.forEach(c -> {
            c.setCitizenRank(null);
            c.setTownUniqueId(null);
        });

        towns.remove(town.getUniqueId());
    }

    public void saveTowns() {
        try {
            Collection<Town> townSet = towns.values();
            plugin.getTownsFile().saveAllTowns(townSet);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save towns!");
        }
    }

}
