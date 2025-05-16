package me.rileycalhoun.landclaim.citizens;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.storage.CitizensFile;
import me.rileycalhoun.landclaim.towns.Town;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CitizensCache {

    private final LandClaim plugin;
    private final CitizensFile citizensFile;
    public final LinkedHashMap<UUID, Citizen> citizens;

    // TODO: Pass in citizens file
    public CitizensCache(LandClaim plugin, int max_size) {
        this.plugin = plugin;
        this.citizensFile = plugin.getCitizensFile();
        this.citizens = new LinkedHashMap<>(max_size+1, 0.75f, true) {

            public boolean removeEldestEntry(Map.Entry eldest) {
                return size() > max_size;
            }

            @Override
            public boolean remove(Object key, Object value) {
                Citizen citizen = (Citizen) value;
                UUID uuid = citizen.getPlayer().getUniqueId();
                try {
                    plugin.getLogger().info("Saving citizen with UUID " + uuid);
                    citizensFile.saveCitizen(citizen);
                } catch (IOException e) {
                    plugin.getLogger().info("Could not save citizen with UUID " + uuid + ": " + e.getMessage());
                }

                return super.remove(key, value);
            }
        };
    }

    @Nullable
    public Citizen getCitizenByUUID(UUID uniqueId) {
        if (citizens.containsKey(uniqueId)) {
            return citizens.get(uniqueId);
        }

        Citizen citizen = citizensFile.getCitizenByUniqueId(uniqueId);
        if (citizen != null) {
            citizens.put(uniqueId, citizen);
        }

        return citizen;
    }

    @NotNull
    public List<Citizen> getCitizensInTown(Town town) {
        List<Citizen> citizens = citizensFile.getCitizens();
        return citizens
                .stream()
                .filter(c -> {
                    UUID townUniqueId = c.getTownUniqueId();
                    return townUniqueId != null && townUniqueId.equals(town.getUniqueId());
                })
                .collect(Collectors.toList());
    }

    public void saveCitizens() {
        try {
            Collection<Citizen> citizenSet = citizens.values();
            citizensFile.saveAllCitizens(citizenSet);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save towns!");
        }
    }

}