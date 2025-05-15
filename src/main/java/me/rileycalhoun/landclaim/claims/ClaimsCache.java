package me.rileycalhoun.landclaim.claims;

import me.rileycalhoun.landclaim.towns.Town;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ClaimsCache {

    @NotNull
    public final LinkedHashMap<UUID, ClaimedArea> primaryClaimsCache;

    public ClaimsCache(int max_size) {
        this.primaryClaimsCache = new LinkedHashMap<>(max_size+1, 0.75f, true) {

            @Override
            protected boolean removeEldestEntry(Map.Entry<UUID, ClaimedArea> eldest) {
                return size() > max_size;
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
        if(primaryClaimsCache.get(townUniqueId) == null) {
            primaryClaimsCache.put(townUniqueId, new ClaimedArea(townUniqueId, new ArrayList<>()));
        }

        return primaryClaimsCache.get(townUniqueId);
    }

    public boolean isChunkClaimed(Chunk chunk) {
        return isChunkClaimed(chunk.getWorld().getUID(), chunk.getX(), chunk.getZ());
    }

    public boolean isChunkClaimed(UUID worldUniqueId, int x, int z) {
        for (ClaimedArea area : primaryClaimsCache.values()) {
            if (area.hasChunk(worldUniqueId, x, z)) {
                return true;
            }
        }

        return false;
    }

}
