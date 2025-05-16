package me.rileycalhoun.landclaim.claims;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.storage.ClaimsFile;
import me.rileycalhoun.landclaim.towns.Town;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public class ClaimsCache {

    private final LandClaim plugin;
    private final ClaimsFile claimsFile;

    @NotNull
    public final LinkedHashMap<UUID, ClaimedArea> primaryClaimsCache;

    public ClaimsCache(LandClaim plugin, int max_size) {
        this.plugin = plugin;
        this.claimsFile = plugin.getClaimsFile();
        this.primaryClaimsCache = new LinkedHashMap<>(max_size+1, 0.75f, true) {

            @Override
            protected boolean removeEldestEntry(Map.Entry<UUID, ClaimedArea> eldest) {
                return size() > max_size;
            }

            @Override
            public boolean remove(Object key, Object value) {
                ClaimedArea claimedArea = (ClaimedArea) value;

                try {
                    plugin.getLogger().info("Saving claim with town UUID " + claimedArea.townUniqueId());
                    claimsFile.saveClaim(claimedArea);
                } catch (IOException e) {
                    plugin.getLogger().info("Could not save citizen with UUID " + claimedArea.townUniqueId()+ ": " + e.getMessage());
                }

                return super.remove(key, value);
            }
        };
    }

    public @NotNull LinkedHashMap<UUID, ClaimedArea> getPrimaryClaimsCache() {
        return primaryClaimsCache;
    }

    public ClaimedArea getClaimByTown(Town town) {
        return getClaimByUUID(town.getUniqueId());
    }

    public ClaimedArea getClaimByUUID(UUID townUniqueId) {
        if(primaryClaimsCache.containsKey(townUniqueId)) {
            return primaryClaimsCache.get(townUniqueId);
        }

        ClaimedArea area = claimsFile.getClaimByTownUniqueId(townUniqueId);
        if(area == null) {
            primaryClaimsCache.put(townUniqueId, new ClaimedArea(townUniqueId, new ArrayList<>()));
        } else {
            primaryClaimsCache.put(townUniqueId, area);
        }

        return primaryClaimsCache.get(townUniqueId);
    }

    public boolean isChunkClaimed(Chunk chunk) {
        return isChunkClaimed(chunk.getWorld().getUID(), chunk.getX(), chunk.getZ());
    }

    public boolean isChunkClaimed(UUID worldUniqueId, int x, int z) {
        List<ClaimedArea> claimedAreas = claimsFile.getClaimedAreas();
        for (ClaimedArea area : claimedAreas) {
            if (area.hasChunk(worldUniqueId, x, z)) {
                return true;
            }
        }

        return false;
    }

    public void saveClaims() {
        try {
            Collection<ClaimedArea> claimSet = primaryClaimsCache.values();
            claimsFile.saveAllClaims(claimSet);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save claims!");
        }
    }

}
