package me.rileycalhoun.landclaim.citizens;

import me.rileycalhoun.landclaim.towns.Town;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CitizensCache {

    public final LinkedHashMap<UUID, Citizen> citizens;

    // TODO: Pass in citizens file
    public CitizensCache(int max_size) {
        this.citizens = new LinkedHashMap<>(max_size+1, 0.75f, true) {

            public boolean removeEldestEntry(Map.Entry eldest) {
                return size() > max_size;
            }

        };
    }

    @Nullable
    public Citizen getCitizenByUUID(UUID uniqueId) {
        return citizens.get(uniqueId);
    }

    @Nullable
    public Citizen getCitizenByUsername(String username) {
        return citizens
                .values()
                .stream()
                .filter(c -> Objects.requireNonNull(c
                        .getPlayer()
                        .getName())
                        .equalsIgnoreCase(username)
                ).findFirst()
                .orElse(null);
    }

    @NotNull
    public List<Citizen> getCitizensInTown(Town town) {
        return citizens
                .values()
                .stream()
                .filter(c -> Objects.requireNonNull(c
                        .getTownUniqueId())
                        .equals(town.getUniqueId()))
                .collect(Collectors.toList());
    }

}
