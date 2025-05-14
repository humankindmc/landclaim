package me.rileycalhoun.landclaim.towns;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
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

}
